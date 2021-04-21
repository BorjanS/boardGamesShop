package com.board_games_shop.board_games_shop.presentation.controller;

import com.board_games_shop.board_games_shop.model.Monopoly;
import com.board_games_shop.board_games_shop.repository.MonopolyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/monopoly")
public class MonopolyController {

    private final MonopolyRepository monopolyRepository;

    private String prod_name;
    private String thumbnail;
    private int price;
    private String availability;
    private String description;
    private String theme;

    public MonopolyController(MonopolyRepository monopolyRepository) {
        this.monopolyRepository = monopolyRepository;
    }

    @GetMapping(path = "")
    public String getSeats(Model model){
        List<Monopoly> monopolies = monopolyRepository.findAll();
        model.addAttribute("monopolies", monopolies);
        return "Monopoly";
    }

    @GetMapping(path = "/add-new")
    public String addMonopoly(){
        return "MonopolyAddNew";
    }

    @PostMapping(path = "/add-new")
    public String saveMonopoly(Model model, @RequestParam(name = "prod_name") String prod_name, @RequestParam(name = "thumbnail") String thumbnail, @RequestParam(name = "theme") String theme, @RequestParam(name = "price") int price, @RequestParam(name = "availability") String availability, @RequestParam(name = "description") String description){

        this.availability=availability;
        this.description=description;
        this.price=price;
        this.thumbnail=thumbnail;
        this.prod_name=prod_name;
        this.theme=theme;

        Monopoly monopoly = new Monopoly();

        monopoly.setAvailability(availability);
        monopoly.setThumbnail(thumbnail);
        monopoly.setDescription(description);
        monopoly.setProd_name(prod_name);
        monopoly.setPrice(price);
        monopoly.setTheme(theme);

        monopolyRepository.save(monopoly);

        return "redirect:/monopoly/";
    }


    @GetMapping(path = "/{id}/edit")
    public String editMonopoly(Model model, @PathVariable("id") Integer id){
        try{
            Monopoly monopoly = this.monopolyRepository.findById(id).orElseThrow();
            model.addAttribute("monopoly", monopoly);
            return "MonopolyEdit";
        } catch (RuntimeException ex){
            return "error";
        }
    }

    @PostMapping(path = "/{id}/edit")
    public String saveMonopoly(@PathVariable Integer id, @Valid Monopoly seat,
                           BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "Monopoly";
        }
        Monopoly monopoly = monopolyRepository.findById(id).orElseThrow();

        monopoly.setAvailability(seat.getAvailability());
        monopoly.setThumbnail(seat.getThumbnail());
        monopoly.setDescription(seat.getDescription());
        monopoly.setProd_name(seat.getProd_name());
        monopoly.setPrice(seat.getPrice());
        monopoly.setTheme(seat.getTheme());

        monopolyRepository.save(monopoly);

        return "redirect:/monopoly/";
    }

    @GetMapping(path = "/{id}")
    public String getSingleProduct(@PathVariable Integer id, Model model){
        Monopoly monopoly = monopolyRepository.getOne(id);
        model.addAttribute("monopoly", monopoly);
        return "SingleMonopoly";
    }


}
