package com.board_games_shop.board_games_shop.repository;

import com.board_games_shop.board_games_shop.model.Cart;
import com.board_games_shop.board_games_shop.model.CartItem;
import com.board_games_shop.board_games_shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findAllByProduct(Product product);
    List<CartItem> findALlByShoppingCart(Cart cart);
    void deleteCartItemsByShoppingCart(Cart cart);
    CartItem findCartItemByShoppingCartAndProduct(Cart cart, Product product);
}
