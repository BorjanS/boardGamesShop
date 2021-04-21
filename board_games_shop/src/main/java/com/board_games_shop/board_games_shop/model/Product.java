package com.board_games_shop.board_games_shop.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Table(name="product")

@Entity
@Inheritance(strategy=InheritanceType.JOINED)

public abstract class Product {
    @Id
    @Column(name="prod_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prod_id;

    @Column(name = "prod_name")
    private String prod_name;

    private Integer copies;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "price")
    private int price;

    private int quantity;

    @Column(name = "availability")
    private String availability;

    @Column(name = "description")
    private String description;

    public Product() {}

    public Product(int prod_id, int copies, String prod_name, String thumbnail, int price, String availability, String description, int quantity, String origin) {
        this.prod_id=prod_id;
        this.copies=copies;
        this.prod_name=prod_name;
        this.thumbnail=thumbnail;
        this.price=price;
        this.availability=availability;
        this.description=description;
        this.quantity=quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }
}
