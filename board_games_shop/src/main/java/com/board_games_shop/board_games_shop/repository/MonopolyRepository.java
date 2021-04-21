package com.board_games_shop.board_games_shop.repository;

import com.board_games_shop.board_games_shop.model.Monopoly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonopolyRepository extends JpaRepository<Monopoly, Integer> {

}
