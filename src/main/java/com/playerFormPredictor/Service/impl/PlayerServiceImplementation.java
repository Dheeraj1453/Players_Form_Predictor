package com.playerFormPredictor.Service.impl;

import com.playerFormPredictor.DTO.PlayerResponseDTO;
import com.playerFormPredictor.Entity.Player;
import com.playerFormPredictor.Repository.PlayerRepository;
import com.playerFormPredictor.Service.PlayerService;
import com.playerFormPredictor.external.Service.CricketDataApiService;
import com.playerFormPredictor.external.dto.ApiPlayerDTO;
import com.playerFormPredictor.external.dto.ApiStatDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImplementation implements PlayerService {

    private final PlayerRepository playerRepository;
    private final CricketDataApiService cricketDataApiService;

    public PlayerServiceImplementation(PlayerRepository playerRepository, CricketDataApiService cricketDataApiService) {
        this.playerRepository = playerRepository;
        this.cricketDataApiService = cricketDataApiService;
    }

    @Override
    public PlayerResponseDTO getPlayerFormByName(String name) {
        String cleanedName = name.trim();

        // 1. Check local database first
        return playerRepository.findByNameIgnoreCase(cleanedName)
                .map(player -> mapToDTO(player, calculateFormStatus(player)))
                .orElseGet(() -> {
                    // 2. Fetch from API if not in DB
                    try {
                        String apiId = cricketDataApiService.getPlayerIdByName(cleanedName);
                        Player savedPlayer = fetchFromApiAndSave(apiId);
                        return mapToDTO(savedPlayer, savedPlayer.getFormStatus());
                    } catch (Exception e) {
                        throw new RuntimeException("Player not found: " + e.getMessage());
                    }
                });
    }

    private Player fetchFromApiAndSave(String apiId) {
        ApiPlayerDTO apiPlayer = cricketDataApiService.fetchPlayerById(apiId);
        Player player = new Player();
        player.setName(apiPlayer.getName());
        player.setCountry(apiPlayer.getCountry());
        player.setRole(apiPlayer.getRole());

        if (apiPlayer.getStats() != null) {
            for (ApiStatDTO stat : apiPlayer.getStats()) {
                String matchType = (stat.getMatchtype() != null) ? stat.getMatchtype().toLowerCase() : "";
                String function = (stat.getFn() != null) ? stat.getFn().toLowerCase() : "";

                // Filtering for ODI as the baseline for performance
                if (matchType.equals("odi") || matchType.contains("one-day")) {
                    if ("batting".equals(function)) {
                        parseBatting(player, stat);
                    } else if ("bowling".equals(function)) {
                        parseBowling(player, stat);
                    }
                }
            }
        }

        // Apply the new Optimal Form Logic
        player.setFormStatus(calculateFormStatus(player));
        return playerRepository.save(player);
    }

    private void parseBatting(Player p, ApiStatDTO s) {
        String val = s.getValue();
        switch (s.getStat().toLowerCase()) {
            case "m" -> p.setMatches(parseInt(val));
            case "runs" -> p.setRuns(parseInt(val));
            case "avg" -> p.setBattingAverage(parseDouble(val));
            case "sr" -> p.setStrikeRate(parseDouble(val));
            case "hs" -> p.setHighestScore(parseInt(val));
        }
    }

    private void parseBowling(Player p, ApiStatDTO s) {
        String val = s.getValue();
        switch (s.getStat().toLowerCase()) {
            case "wkts" -> p.setWickets(parseInt(val));
            case "econ" -> p.setEconomy(parseDouble(val));
            case "avg" -> p.setBowlingAverage(parseDouble(val));
        }
    }

    // --- OPTIMAL FORM FORMULA (RECENT VS CAREER) ---
    private String calculateFormStatus(Player player) {
        boolean isBowler = player.getRole() != null && player.getRole().toUpperCase().contains("BOWLER");

        if (isBowler) {
            List<Integer> wicketsList = player.getLastFiveWickets();
            if (wicketsList != null && !wicketsList.isEmpty()) {
                // Formula: Total wickets in last 5 matches
                int totalWickets = wicketsList.stream().mapToInt(Integer::intValue).sum();
                if (totalWickets >= 10) return "EXCELLENT FORM (🔥)";
                if (totalWickets >= 6) return "IN FORM";
                if (totalWickets >= 3) return "AVERAGE FORM";
                return "OUT OF FORM";
            }
            // Fallback to career
            return (player.getBowlingAverage() > 0 && player.getBowlingAverage() <= 24) ? "IN FORM" : "OUT OF FORM";
        } else {
            List<Integer> scoresList = player.getLastFiveScores();
            if (scoresList != null && !scoresList.isEmpty()) {
                // Formula: Average of last 5 scores
                double recentAvg = scoresList.stream().mapToInt(Integer::intValue).average().orElse(0.0);
                if (recentAvg >= 55) return "EXCELLENT FORM (🔥)";
                if (recentAvg >= 35) return "IN FORM";
                if (recentAvg >= 20) return "AVERAGE FORM";
                return "OUT OF FORM";
            }
            // Fallback to career
            if (player.getBattingAverage() >= 45) return "EXCELLENT FORM";
            if (player.getBattingAverage() >= 35) return "IN FORM";
            if (player.getBattingAverage() >= 25) return "AVERAGE FORM";
            return "OUT OF FORM";
        }
    }

    private PlayerResponseDTO mapToDTO(Player player, String formStatus) {
        // Decide whether to show Batting or Bowling average based on Role
        double displayStat = player.getBattingAverage();
        if (player.getRole() != null && player.getRole().toUpperCase().contains("BOWLER")) {
            displayStat = player.getBowlingAverage();
        }

        return new PlayerResponseDTO(
                player.getName(),
                player.getRole(),
                player.getCountry(),
                Math.round(displayStat * 100.0) / 100.0,
                formStatus
        );
    }


    // --- UPDATED PARSERS (Handles Commas like "13,848") ---
    private int parseInt(String v) {
        if (v == null || v.equals("-") || v.isEmpty()) return 0;
        try {
            return Integer.parseInt(v.replace(",", "").trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private double parseDouble(String v) {
        if (v == null || v.equals("-") || v.isEmpty()) return 0.0;
        try {
            return Double.parseDouble(v.replace(",", "").trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Override
    public List<PlayerResponseDTO> getAllPlayers() {
        return List.of();
    }

    @Override
    public PlayerResponseDTO getPlayerById(long id) {
        return null;
    }

    @SuppressWarnings("null")
    @Override
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }
}