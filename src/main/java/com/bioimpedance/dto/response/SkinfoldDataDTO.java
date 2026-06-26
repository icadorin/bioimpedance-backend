package com.bioimpedance.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkinfoldDataDTO {
    private String protocol;
    private Double biceps;
    private Double chest;
    private Double midaxillary;
    private Double triceps;
    private Double subscapular;
    private Double abdominal;
    private Double suprailiac;
    private Double thigh;
}