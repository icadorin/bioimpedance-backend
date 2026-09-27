package com.bioimpedance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Sugestão de conversão sobre um PredictionResult (doc.md §9.4/§16.4).
 * Nulo no response quando a variante produz resultado final direto (FALK4).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversionSuggestionDTO {

    /** Estado do motor de conversão (ConversionSuggestionStatus). */
    private String status;

    private List<String> suggestedConversionIds;

    private List<String> candidateConversionIds;

    private List<String> excludedConversionIds;
}