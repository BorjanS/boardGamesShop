package com.board_games_shop.board_games_shop.presentation.controller;

import com.board_games_shop.board_games_shop.model.Puzzle;
import com.board_games_shop.board_games_shop.repository.PuzzleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/puzzle")
public class PuzzleController {

    private String prod_name;
    private String thumbnail;
    private int price;
    private String availability;
    private String description;

    private String number;


    private final PuzzleRepository puzzleRepository;

    public PuzzleController(PuzzleRepository puzzleRepository) {
        this.puzzleRepository = puzzleRepository;
    }

    @GetMapping(path = "")
    public String getPuzzles(Model model){
        List<Puzzle> puzzle = puzzleRepository.findAll();
        model.addAttribute("puzzle", puzzle);
        return "Puzzle";
    }

    @GetMapping(path = "/add-new")
    public String addPuzzle(){
        return "PuzzleAddNew";
    }


    @PostMapping(path = "/add-new")
    public String savePuzzle(Model model, @RequestParam(name = "prod_name") String prod_name, @RequestParam(name = "thumbnail") String thumbnail, @RequestParam(name = "number") String number, @RequestParam(name = "price") int price, @RequestParam(name = "availability") String availability, @RequestParam(name = "description") String description){

        this.availability=availability;
        this.description=description;
        this.price=price;
        this.thumbnail=thumbnail;
        this.prod_name=prod_name;
        this.number=number;

        Puzzle puzzle = new Puzzle();

        puzzle.setAvailability(availability);
        puzzle.setThumbnail(thumbnail);
        puzzle.setDescription(description);
        puzzle.setProd_name(prod_name);
        puzzle.setPrice(price);
        puzzle.setNumber(number);

        puzzleRepository.save(puzzle);

        return "redirect:/puzzle/";
    }

    @GetMapping(path = "/{id}/edit")
    public String editPuzzle(Model model, @PathVariable("id") Integer id){
        try{
            Puzzle puzzle = this.puzzleRepository.findById(id).orElseThrow();
            model.addAttribute("puzzle", puzzle);
            return "PuzzleEdit";
        } catch (RuntimeException ex){
            return "error";
        }
    }

    @PostMapping(path = "/{id}/edit")
    public String savePuzzle(@PathVariable Integer id, @Valid Puzzle puzzle,
                           BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "Puzzle";
        }
        Puzzle puzzle1 = puzzleRepository.findById(id).orElseThrow();

        puzzle1.setAvailability(puzzle.getAvailability());
        puzzle1.setThumbnail(puzzle.getThumbnail());
        puzzle1.setDescription(puzzle.getDescription());
        puzzle1.setProd_name(puzzle.getProd_name());
        puzzle1.setPrice(puzzle.getPrice());
        puzzle1.setNumber(puzzle.getNumber());

        puzzleRepository.save(puzzle1);

        return "redirect:/puzzle/";
    }

    @GetMapping(path = "/{id}")
    public String getSingleProduct(@PathVariable Integer id, Model model){
        Puzzle puzzle = puzzleRepository.getOne(id);
        model.addAttribute("puzzle", puzzle);
        return "SinglePuzzle";
    }
}



