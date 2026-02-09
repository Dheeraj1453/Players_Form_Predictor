package com.playerFormPredictor.external.dto;

import lombok.Data;
import java.util.List;

@Data
public class ApiPlayerDTO {

    private String id;
    private String name;
    private String role;
    private String country;

    private List<ApiStatDTO> stats;
}
