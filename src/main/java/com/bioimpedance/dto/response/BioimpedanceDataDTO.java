package com.bioimpedance.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BioimpedanceDataDTO {
    private Double resistance;
    private Double reactance;
}