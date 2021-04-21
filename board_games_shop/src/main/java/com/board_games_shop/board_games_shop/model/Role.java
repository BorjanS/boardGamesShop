package com.board_games_shop.board_games_shop.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Table(name = "roles")

@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer role_id;

    @Column(name = "role_name")
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }

    public Integer getId() {
        return role_id;
    }

    public void setRoleId(Integer role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}