package com.board_games_shop.board_games_shop.service;

import com.board_games_shop.board_games_shop.model.Cart;
import com.board_games_shop.board_games_shop.model.CartItem;
import com.board_games_shop.board_games_shop.model.Product;

import java.util.List;

public interface CartItemService {
    List<CartItem> findAllByShoppingCart(int shoppingCartId);
    void deleteAllByShoppingCart(int shoppingCartId);
    List<CartItem> findByProdId(int prodId);
    CartItem createItem(int prodId, int shoppingCartId);
    void deleteItem(int cartItemId);
    CartItem saveItem(CartItem item);
    CartItem findByShoppingCartAndProduct(Cart shoppingCart, Product product);
    List<CartItem> findAll();
    void removeById(int cartItemId);
}

