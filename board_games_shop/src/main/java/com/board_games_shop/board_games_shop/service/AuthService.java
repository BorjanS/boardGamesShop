package com.board_games_shop.board_games_shop.service;

import com.board_games_shop.board_games_shop.model.User;

public interface AuthService {
    User getCurrentUser();
    String getCurrentUserId();
    User signUpUser(String username, String password, String repeatedPassword);

}
