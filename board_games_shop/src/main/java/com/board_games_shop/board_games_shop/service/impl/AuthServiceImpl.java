package com.board_games_shop.board_games_shop.service.impl;

import com.board_games_shop.board_games_shop.model.Role;
import com.board_games_shop.board_games_shop.model.User;
import com.board_games_shop.board_games_shop.model.exceptions.PasswordDoesntMatchException;
import com.board_games_shop.board_games_shop.repository.RoleRepository;
import com.board_games_shop.board_games_shop.repository.UserRepository;
import com.board_games_shop.board_games_shop.service.AuthService;
import com.board_games_shop.board_games_shop.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserService userService;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository,
                           UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }


    @Override
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public String getCurrentUserId() {
        return this.getCurrentUser().getUsername();
    }

    @Override
    public User signUpUser(String username,
                           String password,
                           String repeatedPassword) {
        if (!password.equals(repeatedPassword)) {
            throw new PasswordDoesntMatchException();
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        Role userRole = this.roleRepository.findByName("ROLE_USER");
        user.setRoles(Collections.singletonList(userRole));
        return this.userService.registerUser(user);

    }

    @PostConstruct
    public void init() {
        if (!this.userRepository.existsById("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(this.passwordEncoder.encode("admin"));
            admin.setRoles(this.roleRepository.findAll());
            this.userRepository.save(admin);
        }
    }
}
