package com.board_games_shop.board_games_shop.model;

import javax.persistence.*;

@Table(name = "puzzle")

@Entity
public class Puzzle extends Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private int prod_id;

    @Column(name = "number")
    private String number;

    @Column(name = "dimensions")
    private String dimensions;

    @Override
    public int getProd_id() {
        return prod_id;
    }

    @Override
    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }
}
