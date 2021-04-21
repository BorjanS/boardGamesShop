package com.board_games_shop.board_games_shop.model;

import javax.persistence.*;

@Table(name = "cards")

@Entity
public class Cards extends Product {
    @Id
    @Column(name="prod_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prod_id;

    @Column(name = "game")
    private String game;

    @Override
    public int getProd_id() {
        return prod_id;
    }

    @Override
    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
}
