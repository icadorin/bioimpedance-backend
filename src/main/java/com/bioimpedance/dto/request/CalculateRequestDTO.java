package com.bioimpedance.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Request do POST /api/assessments/{id}/calculate (Fase 13 / B4 — DEC-58).
 * Cliente, data e medidas vêm da Assessment persistida — aqui vão apenas
 * as decisões profissionais (doc.md §25: cálculo é ação explícita).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalculateRequestDTO {

    /** Nulo no preview; aqui é a escolha explícita da variante. */
    private String selectedVariantId;

    @Size(max = 200)
    private String selectionReason;

    /** Nulo → política AUTO (preferência → default → fallback; doc.md §25.3). */
    private String selectedConversionId;

    @Size(max = 200)
    private String conversionSelectionReason;
}