package com.board_games_shop.board_games_shop.repository;

import com.board_games_shop.board_games_shop.model.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardsRepository extends JpaRepository<Cards, Integer>{
}

