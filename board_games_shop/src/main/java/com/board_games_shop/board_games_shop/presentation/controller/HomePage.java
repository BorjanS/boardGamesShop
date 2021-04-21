package com.board_games_shop.board_games_shop.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
public class HomePage {

    @GetMapping(path = "")
    public String goToHomePage(){
        return "HomePage";
    }


}
