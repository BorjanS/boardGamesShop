package com.board_games_shop.board_games_shop.presentation.controller;

import com.board_games_shop.board_games_shop.model.Cards;
import com.board_games_shop.board_games_shop.repository.CardsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/cards")
public class CardsController {
    private String prod_name;
    private String thumbnail;
    private int price;
    private String availability;
    private String description;
    private String game;

    private final CardsRepository cardsRepository;

    public CardsController(CardsRepository cardsRepository) {
        this.cardsRepository = cardsRepository;
    }


    @GetMapping(path = "")
    public String getSeats(Model model){
        List<Cards> cards = cardsRepository.findAll();
        model.addAttribute("cards", cards);
        return "Cards";
    }

    @GetMapping(path = "/add-new")
    public String addCards(){
        return "CardsAddNew";
    }

    @PostMapping(path = "/add-new")
    public String saveCards(Model model, @RequestParam(name = "prod_name") String prod_name, @RequestParam(name = "thumbnail") String thumbnail, @RequestParam(name = "game") String game, @RequestParam(name = "price") int price, @RequestParam(name = "availability") String availability, @RequestParam(name = "description") String description){

        this.availability=availability;
        this.description=description;
        this.price=price;
        this.thumbnail=thumbnail;
        this.prod_name=prod_name;
        this.game=game;

        Cards cards = new Cards();

        cards.setAvailability(availability);
        cards.setThumbnail(thumbnail);
        cards.setDescription(description);
        cards.setProd_name(prod_name);
        cards.setPrice(price);
        cards.setGame(game);

        cardsRepository.save(cards);

        return "redirect:/cards/";
    }


    @GetMapping(path = "/{id}/edit")
    public String editCards(Model model, @PathVariable("id") Integer id){
        try{
            Cards cards = this.cardsRepository.findById(id).orElseThrow();
            model.addAttribute("cards", cards);
            return "cardsEdit";
        } catch (RuntimeException ex){
            return "error";
        }
    }

    @PostMapping(path = "/{id}/edit")
    public String saveCards(@PathVariable Integer id, @Valid Cards seat,
                               BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "Cards";
        }
        Cards cards = cardsRepository.findById(id).orElseThrow();

        cards.setAvailability(seat.getAvailability());
        cards.setThumbnail(seat.getThumbnail());
        cards.setDescription(seat.getDescription());
        cards.setProd_name(seat.getProd_name());
        cards.setPrice(seat.getPrice());
        cards.setGame(seat.getGame());

        cardsRepository.save(cards);

        return "redirect:/cards/";
    }

    @GetMapping(path = "/{id}")
    public String getSingleProduct(@PathVariable Integer id, Model model){
        Cards cards = cardsRepository.getOne(id);
        model.addAttribute("cards", cards);
        return "SingleCards";
    }


}





