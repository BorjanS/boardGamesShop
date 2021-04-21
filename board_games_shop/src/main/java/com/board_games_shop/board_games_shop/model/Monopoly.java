package com.board_games_shop.board_games_shop.model;

import javax.persistence.*;

@Table(name = "monopoly")

@Entity
public class Monopoly extends Product {
    @Id
    @Column(name="prod_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prod_id;

    @Column(name = "theme")
    private String theme;

    @Override
    public int getProd_id() {
        return prod_id;
    }

    @Override
    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
