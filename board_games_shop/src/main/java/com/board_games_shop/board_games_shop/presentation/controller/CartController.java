package com.board_games_shop.board_games_shop.presentation.controller;

import com.board_games_shop.board_games_shop.model.Cart;
import com.board_games_shop.board_games_shop.model.CartItem;
import com.board_games_shop.board_games_shop.model.Product;
import com.board_games_shop.board_games_shop.model.dto.ChargeRequest;
import com.board_games_shop.board_games_shop.service.AuthService;
import com.board_games_shop.board_games_shop.service.CartItemService;
import com.board_games_shop.board_games_shop.service.CartService;
import com.board_games_shop.board_games_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/cart")
public class CartController {

    @Value("${STRIPE_P_KEY}")
    private String publicKey;

    private final CartService cartService;
    private final AuthService authService;
    private CartItemService cartItemService;
    private final ProductService productService;


    public CartController(ProductService productService, AuthService authService, CartService cartService, CartItemService cartItemService) {
        this.cartService = cartService;
        this.authService = authService;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @GetMapping
    public String getCart(Model model){
        Cart shoppingCart = this.cartService.getActiveShoppingCartOrCreateOne(this.authService.getCurrentUserId());
        model.addAttribute("currency", "mkd");
        List<CartItem> items = this.cartItemService.findAllByShoppingCart(shoppingCart.getCart_id());

        List<Product> prodItems = new ArrayList<>();
        for(CartItem item:items){
            prodItems.add(item.getProduct());
        }

        model.addAttribute("items", prodItems);
        float price = this.cartService.getFullPrice(shoppingCart.getId());

        model.addAttribute("amount", (int) (price));
        model.addAttribute("stripePublicKey", this.publicKey);
        return "Cart";
    }

    @PostMapping("/charge")
    public String checkout(ChargeRequest chargeRequest, Model model) {
        try {
            Cart shoppingCart = this.cartService.checkoutShoppingCartStripe(this.authService.getCurrentUserId(), chargeRequest);
            return "redirect:/?message=Successful Payment";
        } catch (RuntimeException ex) {
            return "redirect:/cart?message=" + ex.getLocalizedMessage();
        }
    }

    @PostMapping(value="/{productId}/add-product")
    public String addProductToShoppingCart(@PathVariable int productId) {
        try {
            CartItem cartItem = this.cartService.addProductToShoppingCart(this.authService.getCurrentUserId(), productId);
        } catch (RuntimeException ex) {
            return "redirect:/?error=" + ex.getLocalizedMessage();
        }
        return "redirect:/";
    }


    @PostMapping("/{productId}/remove-product")
    public String removeProductToShoppingCart(@PathVariable int productId) {
        List<CartItem> items = this.cartService.removeProductFromShoppingCart(this.authService.getCurrentUserId(), productId);
        return "redirect:/Cart";
    }

}
