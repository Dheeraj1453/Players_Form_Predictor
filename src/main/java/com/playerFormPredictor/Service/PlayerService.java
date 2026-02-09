package com.playerFormPredictor.Service;

import com.playerFormPredictor.DTO.PlayerResponseDTO;
import com.playerFormPredictor.Entity.Player;

import java.util.List;

public interface PlayerService {

    PlayerResponseDTO getPlayerById(long id);
    List<PlayerResponseDTO> getAllPlayers();
    Player savePlayer(Player player);

    PlayerResponseDTO getPlayerFormByName(String name);

}
