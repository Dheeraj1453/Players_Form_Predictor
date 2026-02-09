package com.playerFormPredictor.external.Service;

import com.playerFormPredictor.external.dto.ApiPlayerDTO;
import com.playerFormPredictor.external.dto.ApiResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.List;

@Service
public class CricketDataApiService {

    private final RestTemplate restTemplate;

    @Value("${cricketdata.api.key}")
    private String apiKey;

    @Value("${cricketdata.base.url}")
    private String baseUrl;

    public CricketDataApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings({ "null", "unchecked" })
    public String getPlayerIdByName(String name) {
        String url = String.format("%s/players?apikey=%s&offset=0&search=%s", baseUrl, apiKey, name);

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response != null && response.get("data") != null) {
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) response.get("data");
            if (!dataList.isEmpty()) {
                return (String) dataList.get(0).get("id");
            }
        }
        throw new RuntimeException("API could not find a player named " + name);
    }

    @SuppressWarnings("null")
    public ApiPlayerDTO fetchPlayerById(String playerId) {
        String url = String.format("%s/players_info?apikey=%s&id=%s", baseUrl, apiKey, playerId);
        ApiResponseDTO response = restTemplate.getForObject(url, ApiResponseDTO.class);

        if (response == null || response.getData() == null) {
            throw new RuntimeException("Player details not found");
        }
        return response.getData();
    }
}