package com.board_games_shop.board_games_shop.model;

import com.board_games_shop.board_games_shop.model.enumerations.CartStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "cart")

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "cart_id")
    private int cart_id;

    @Enumerated(EnumType.STRING)
    private CartStatus status = CartStatus.CREATED;

    private LocalDateTime createDate = LocalDateTime.now();
    private LocalDateTime endDate;


    @ManyToOne
    @NotNull
//    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "products_in_cart",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "prod_id")
    )
    private List<Product> products = new ArrayList<>();


    @OneToMany(mappedBy = "shoppingCart")
    private List<CartItem> items;


    public Cart(){}

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public int getId() {
        return cart_id;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }


    public List<Product> getProducts() {
        return products;
    }
//
//    public void setProducts(List<Product> products) {
//        this.products = products;
//    }
}
