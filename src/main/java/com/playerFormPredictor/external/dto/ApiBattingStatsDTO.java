package com.playerFormPredictor.external.dto;

import lombok.Data;

@Data
public class ApiBattingStatsDTO {

    private int matches;
    private int runs;
    private double average;
    private double strikeRate;
    private int highestScore;

}
