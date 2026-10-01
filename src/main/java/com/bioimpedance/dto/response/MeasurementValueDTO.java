package com.bioimpedance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Medida do rascunho com origem (DEC-57). */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeasurementValueDTO {
    private Double value;
    /** MANUAL | AVALIACAO_ANTERIOR */
    private String source;
}