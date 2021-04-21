package com.board_games_shop.board_games_shop.repository;

import com.board_games_shop.board_games_shop.model.Cart;
import com.board_games_shop.board_games_shop.model.enumerations.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUserUsernameAndStatus(String username, CartStatus status);
    boolean existsByUserUsernameAndStatus(String username, CartStatus status);
    List<Cart> findAllByUserUsername(String username);
}
