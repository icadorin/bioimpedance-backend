package com.bioimpedance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Painel de estado da avaliação (doc.md §17): o que é aplicável,
 * o que está pronto, o que falta e o que o sistema sugere.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentFlowResponseDTO {

    /** SUGGESTED | NO_ELIGIBLE_METHOD | NO_ENABLED_METHOD | NO_READY_METHOD (DEC-18). */
    private String suggestionStatus;

    private List<String> suggestedVariantIds;

    /** READY + MISSING_INPUTS (DEC-20). */
    private List<VariantStatusDTO> candidateVariants;

    /** INELIGIBLE + DISABLED, com motivos (DEC-20). */
    private List<VariantStatusDTO> excludedVariants;

    /**
     * União dos requiredInputs das variantes habilitadas —
     * campos que a tela de coleta deve exibir (doc.md §10).
     */
    private List<String> requiredInputUnion;
}