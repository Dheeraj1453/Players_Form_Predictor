package com.playerFormPredictor.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayerResponseDTO {
    private String name;
    private String role;
    private String country;
    private double average;
    private String formStatus;
}
