package com.bioimpedance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Última medida de um input em avaliações anteriores do cliente
 * (Fase 13 / B2 — DEC-57, doc.md §11). Alimenta o botão "Usar X de dd/MM".
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreviousMeasurementDTO {

    private Double value;
    private LocalDate date;
    private String assessmentId;
}