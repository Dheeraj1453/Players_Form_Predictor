package com.playerFormPredictor.controller;

import com.playerFormPredictor.DTO.PlayerResponseDTO;
import com.playerFormPredictor.Service.PlayerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlayerRestController {

    private PlayerService playerService;
    public PlayerRestController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    public List<PlayerResponseDTO> tempPage() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/players/{id}")
    public PlayerResponseDTO getPlayerById(@PathVariable Long id) {
        return playerService.getPlayerById(id);
    }

}
