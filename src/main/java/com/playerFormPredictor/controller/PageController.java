package com.playerFormPredictor.controller;

import com.playerFormPredictor.DTO.PlayerResponseDTO;
import com.playerFormPredictor.Service.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    private final PlayerService playerService;

    public PageController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/home")
    public String homePage() {
        return "Home";
    }

    @GetMapping("/predict")
    public String predictPlayerForm(@RequestParam String name, Model model) {
        String cleanedName = name.trim();
        try {
            PlayerResponseDTO player = playerService.getPlayerFormByName(cleanedName);
            model.addAttribute("player", player);
        } catch (Exception e) {
            // This prints the REAL error to your IntelliJ console
            System.err.println("CRITICAL ERROR: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Player not found: " + e.getMessage());
        }
        return "Home";
    }

}
