package com.board_games_shop.board_games_shop.service.impl;

import com.board_games_shop.board_games_shop.model.Cart;
import com.board_games_shop.board_games_shop.model.CartItem;
import com.board_games_shop.board_games_shop.model.Product;
import com.board_games_shop.board_games_shop.model.exceptions.ShoppingCartIsNotActiveException;
import com.board_games_shop.board_games_shop.repository.CartItemRepository;
import com.board_games_shop.board_games_shop.repository.CartRepository;
import com.board_games_shop.board_games_shop.service.CartItemService;
import com.board_games_shop.board_games_shop.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CartRepository shoppingCartRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, ProductService productService, CartRepository shoppingCartRepository){
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public List<CartItem> findAllByShoppingCart(int shoppingCartId) {
        Cart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow(() -> new ShoppingCartIsNotActiveException("aa"));
        //List<CartItem> items = cartItemRepository.findAllByShoppingCartId(soppingCartId);
        if(shoppingCart != null)
        {
            List<CartItem> items = cartItemRepository.findALlByShoppingCart(shoppingCart);
            return items;
        }
        return null;

    }

    @Override
    public void deleteAllByShoppingCart(int shoppingCartId) {
        Cart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow(() -> new ShoppingCartIsNotActiveException("aa"));
        if(shoppingCart != null){
            this.cartItemRepository.deleteCartItemsByShoppingCart(shoppingCart);
        }

    }

    @Override
    public List<CartItem> findByProdId(int prodId) {
        Product product = productService.findById(prodId);
        return cartItemRepository.findAllByProduct(product);
    }

    @Override
    public CartItem createItem(int prodId, int shoppingCartId) {
        CartItem item = new CartItem();
        Cart shoppingCart = this.shoppingCartRepository.findById(shoppingCartId).orElseThrow(() -> new ShoppingCartIsNotActiveException("aa"));
        Product product = productService.findById(prodId);

        if(shoppingCart != null && product != null){
            item.setProduct(product);
            item.setShoppingCart(shoppingCart);
            return cartItemRepository.save(item);
        }
        return null;
    }

    @Override
    public void deleteItem(int cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public CartItem saveItem(CartItem item){
        return this.cartItemRepository.save(item);
    }

    @Override
    public CartItem findByShoppingCartAndProduct(Cart shoppingCart, Product product) {
        return this.cartItemRepository.findCartItemByShoppingCartAndProduct(shoppingCart, product);
    }

    @Override
    public List<CartItem> findAll() {
        return this.cartItemRepository.findAll();
    }

    @Override
    public void removeById(int cartItemId) {
        this.cartItemRepository.deleteById(cartItemId);
    }
}
