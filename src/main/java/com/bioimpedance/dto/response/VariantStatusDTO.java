package com.bioimpedance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Estado operacional de uma variante no painel da avaliação (doc.md §12/§17).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantStatusDTO {

    private String variantId;

    /** READY | MISSING_INPUTS | INELIGIBLE | DISABLED */
    private String status;

    private List<String> warnings;

    private List<String> missingInputIds;

    /** Motivos/explicações estruturadas (SuggestionExplanationBuilder). */
    private List<String> reasons;
}