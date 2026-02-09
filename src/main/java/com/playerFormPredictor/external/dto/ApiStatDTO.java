package com.playerFormPredictor.external.dto;

import lombok.Data;

@Data
public class ApiStatDTO {

    private String fn;        // batting / bowling
    private String matchtype; // odi, test, t20
    private String stat;      // runs, avg, wkts, econ
    private String value;     // value as String
}
