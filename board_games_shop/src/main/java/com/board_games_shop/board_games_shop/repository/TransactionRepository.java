package com.board_games_shop.board_games_shop.repository;

import com.board_games_shop.board_games_shop.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}

