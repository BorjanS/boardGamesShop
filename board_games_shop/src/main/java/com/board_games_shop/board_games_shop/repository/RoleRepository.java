package com.board_games_shop.board_games_shop.repository;

import com.board_games_shop.board_games_shop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String role_user);
}
