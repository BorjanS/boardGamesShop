package com.board_games_shop.board_games_shop.service;

import com.board_games_shop.board_games_shop.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findById(String userId);
    User registerUser(User user);
}
