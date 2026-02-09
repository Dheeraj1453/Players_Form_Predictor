package com.playerFormPredictor.external.dto;

import lombok.Data;

@Data
public class ApiResponseDTO {
    private String status;
    private ApiPlayerDTO data;
}
