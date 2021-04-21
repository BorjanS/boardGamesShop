//package com.baby_shop.baby_shop.presentation.controller;
//
//
//import com.baby_shop.baby_shop.model.Cart;
//import com.baby_shop.baby_shop.model.Product;
//import com.baby_shop.baby_shop.model.dto.ChargeRequest;
//import com.baby_shop.baby_shop.service.AuthService;
//import com.baby_shop.baby_shop.service.CartService;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/payments")
//public class PaymentController {
//
//    @Value("${STRIPE_P_KEY}")
//    private String publicKey;
//
//    private final CartService shoppingCartService;
//    private final AuthService authService;
//
//    public PaymentController(CartService shoppingCartService,
//                             AuthService authService) {
//        this.shoppingCartService = shoppingCartService;
//        this.authService = authService;
//    }
//
//
//    @GetMapping("/charge")
//    public String getCheckoutPage(Model model) {
//        try {
//            Cart shoppingCart = this.shoppingCartService.findActiveShoppingCartByUsername(this.authService.getCurrentUserId());
//            model.addAttribute("shoppingCart", shoppingCart);
//            model.addAttribute("currency", "eur");
//            model.addAttribute("amount", (int) (shoppingCart.getProducts().stream().mapToDouble(Product::getPrice).sum() * 100));
//            model.addAttribute("stripePublicKey", this.publicKey);
//            return "Cart";
//        } catch (RuntimeException ex) {
//            return "redirect:/products?error=" + ex.getLocalizedMessage();
//        }
//    }
//
//    @PostMapping("/charge")
//    public String checkout(ChargeRequest chargeRequest, Model model) {
//        try {
//            Cart shoppingCart = this.shoppingCartService.checkoutShoppingCart(this.authService.getCurrentUserId(), chargeRequest);
//            return "redirect:/products?message=Successful Payment";
//        } catch (RuntimeException ex) {
//            return "redirect:/payments/charge?error=" + ex.getLocalizedMessage();
//        }
//    }
//}
