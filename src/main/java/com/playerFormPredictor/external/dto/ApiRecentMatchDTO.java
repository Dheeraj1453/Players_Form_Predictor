package com.playerFormPredictor.external.dto;

import lombok.Data;

@Data
public class ApiRecentMatchDTO {

    private Integer runs; // For batsman
    private Integer wickets; // For bowlers

}
