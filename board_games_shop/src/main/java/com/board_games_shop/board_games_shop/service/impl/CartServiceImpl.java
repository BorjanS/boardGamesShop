package com.board_games_shop.board_games_shop.service.impl;

import com.board_games_shop.board_games_shop.model.dto.ChargeRequest;
import com.board_games_shop.board_games_shop.model.enumerations.CartStatus;
import com.board_games_shop.board_games_shop.repository.CartRepository;
import com.board_games_shop.board_games_shop.model.*;
import com.board_games_shop.board_games_shop.model.exceptions.ProductOutOfStockException;
import com.board_games_shop.board_games_shop.model.exceptions.ShoppingCartIsAlreadyCreated;
import com.board_games_shop.board_games_shop.model.exceptions.ShoppingCartIsNotActiveException;
import com.board_games_shop.board_games_shop.model.exceptions.TransactionFailedException;
import com.board_games_shop.board_games_shop.service.*;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final UserService userService;
    private final ProductService productService;
    private final PaymentService paymentService;
    private final CartRepository cartRepository;
    private final CartItemService cartItemService;
    private final TransactionService transactionService;

    public CartServiceImpl(TransactionService transactionService , PaymentService paymentService , CartRepository cartRepository, UserService userService, ProductService productService, CartItemService cartItemService){
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.productService = productService;
        this.cartItemService = cartItemService;
        this.paymentService = paymentService;
        this.transactionService = transactionService;
    }

    @Override
    public Cart createShoppingCart(String userId) {
        User user = this.userService.findById(userId);
        if (this.cartRepository.existsByUserUsernameAndStatus(userId, CartStatus.CREATED)){
            throw new ShoppingCartIsAlreadyCreated(userId);
        }
        Cart shoppingCart = new Cart();
        shoppingCart.setUser(user);
        return this.cartRepository.save(shoppingCart);
    }

    @Override
    public Cart getActiveShoppingCartOrCreateOne(String userId){
        Cart shoppingCart = this.cartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED);

        if (shoppingCart == null) {
            shoppingCart = new Cart();
            shoppingCart.setUser(this.userService.findById(userId));
            shoppingCart = this.cartRepository.save(shoppingCart);
        }
        return shoppingCart;
    }



    @Override
    public Float getFullPrice(int shoppingCartId) {
        List<CartItem> items = this.cartItemService.findAllByShoppingCart(shoppingCartId);
        float price = 0;
        for (CartItem item : items){
            float temp = item.getQuantity() * item.getProduct().getPrice();
            price = price + temp;
        }
        return price;
    }

    @Override
    @Transactional
    public CartItem addProductToShoppingCart(String userId, int productId) {
        Cart cart = this.getActiveShoppingCartOrCreateOne(userId);
        Product product = this.productService.findById(productId);

        CartItem item = cartItemService.findByShoppingCartAndProduct(cart, product);
        if (item == null){
            item = new CartItem();
            item.setQuantity(1);
            item.setProduct(product);
            item.setShoppingCart(cart);
        }
        else
        {
            item.setQuantity(item.getQuantity() + 1);
        }
        return cartItemService.saveItem(item);

    }


    @Override
    public List<CartItem> removeProductFromShoppingCart(String userId, int prodId) {

        if(!this.cartRepository.existsByUserUsernameAndStatus(userId, CartStatus.CREATED)){
            throw new ShoppingCartIsNotActiveException(userId);
        }
        else
        {
            Cart shoppingCart = this.getActiveShoppingCartOrCreateOne(userId);

            List<CartItem> items = this.findShoppingCartItems(shoppingCart.getId());

            for (CartItem item : items){
                if(item.getProduct().getProd_id()==(prodId)){
                    cartItemService.removeById(item.getId());
                    break;
                }
            }
            return this.findShoppingCartItems(shoppingCart.getId());
        }

    }

    @Override
    public Cart cancelActiveShoppingCart(String userId) {
        Cart shoppingCart = this.cartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED);

        if(shoppingCart == null){
            throw new ShoppingCartIsNotActiveException(userId);
        }
        else
        {
            shoppingCart.setStatus(CartStatus.CANCELED);
            shoppingCart.setEndDate(LocalDateTime.now());
            return this.cartRepository.save(shoppingCart);
        }
    }

    @Override
    @Transactional
    public Cart checkoutShoppingCart(String userId) {
        Cart shoppingCart = this.cartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED);
        if (shoppingCart == null){
            throw new ShoppingCartIsNotActiveException(userId);
        }

        else {
            List<CartItem> items = this.findShoppingCartItems(shoppingCart.getId());
            for (CartItem item : items){
                if(item.getProduct().getCopies() < item.getQuantity()){
                    throw new ProductOutOfStockException(item.getProduct().getProd_id());
                }
                if (! (item.getProduct().getCopies() < item.getQuantity())){
                    item.getProduct().setCopies(item.getProduct().getCopies() - item.getQuantity());
                }
            }
            //shoppingCart.setItems(items);
            shoppingCart.setStatus(CartStatus.FINISHED);
            shoppingCart.setEndDate(LocalDateTime.now());
            this.cartItemService.deleteAllByShoppingCart(shoppingCart.getId());
            return this.cartRepository.save(shoppingCart);

        }
    }

    @Override
    @Transactional
    public Cart checkoutShoppingCartStripe(String userId, ChargeRequest chargeRequest) {

        Cart shoppingCart = this.cartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED);
        if (shoppingCart == null){
            throw new ShoppingCartIsNotActiveException(userId);
        }

        else {
            List<CartItem> items = this.findShoppingCartItems(shoppingCart.getId());
            for (CartItem item : items){
                if(item.getProduct().getCopies() < item.getQuantity()){
                    throw new ProductOutOfStockException(item.getProduct().getProd_id());
                }
                if (! (item.getProduct().getCopies() < item.getQuantity())){
                    item.getProduct().setCopies(item.getProduct().getCopies() - item.getQuantity());
                }
            }

            float price = this.getFullPrice(shoppingCart.getId());
            Charge charge = null;

            try {
                charge = this.paymentService.pay(chargeRequest);
                Transaction transaction = new Transaction();
                transaction.setId(charge.getId());
                transaction.setBalanceTransaction(charge.getBalanceTransaction());
                transaction.setUserId(userId);
                transaction.setStatus(charge.getStatus());

                this.transactionService.saveTransaction(transaction);
            }
            catch(CardException | APIException | AuthenticationException | APIConnectionException | InvalidRequestException e){
                throw new TransactionFailedException(userId, e.getMessage());
            }


            //shoppingCart.setItems(items);
            shoppingCart.setStatus(CartStatus.FINISHED);
            shoppingCart.setEndDate(LocalDateTime.now());

            this.cartItemService.deleteAllByShoppingCart(shoppingCart.getId());
            return this.cartRepository.save(shoppingCart);

        }
    }

    @Override
    public Cart findById(int shoppingCartId) {
        return this.cartRepository.findById(shoppingCartId).orElseThrow(() -> new ShoppingCartIsNotActiveException("ee"));
    }

    @Override
    public List<CartItem> findShoppingCartItems(int shoppingCartId) {
        List<CartItem> items = this.cartItemService.findAll();

        List<CartItem> finalItems = new ArrayList<CartItem>();

        for(CartItem item : items){
            if(item.getShoppingCart().getId()==shoppingCartId)
            {
                finalItems.add(item);
            }
        }

        return finalItems;
    }

//    @Override
//    public Cart findActiveShoppingCartByUsername(String userId) {
//        return this.cartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED)
//                .orElseThrow(() -> new ShoppingCartIsNotActiveException(userId));
//    }

    @Override
    public List<Cart> findAllByUsername(String userId) {
        return this.cartRepository.findAllByUserUsername(userId);
    }

    @Override
    public Cart createNewShoppingCart(String userId) {
        User user = this.userService.findById(userId);
        if (this.cartRepository.existsByUserUsernameAndStatus(
                user.getUsername(),
                CartStatus.CREATED
        ))
        {
            throw new ShoppingCartIsAlreadyCreated(userId);
        }
        Cart cart = new Cart();
        cart.setUser(user);
        return this.cartRepository.save(cart);
    }
}
