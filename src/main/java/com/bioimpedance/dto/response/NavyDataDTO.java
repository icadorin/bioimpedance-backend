package com.bioimpedance.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NavyDataDTO {
    private Double waist;
    private Double neck;
    private Double hip;
}