package com.bioimpedance.domain.conversionsuggestion.explanation;

import com.bioimpedance.domain.contracts.ConversionCandidateInput;
import com.bioimpedance.domain.contracts.ConversionStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Fonte: doc.md §16.4 + architecture.md §12.
 * <p>
 * Produz justificativas legíveis SOMENTE a partir do estado resolvido da
 * conversão. Não inventa justificativa que não esteja presente nos dados.
 */
@Component
public class ConversionSuggestionExplanationBuilder {

    public List<String> buildReasons(ConversionCandidateInput input, ConversionStatus status) {
        List<String> reasons = new ArrayList<>();
        switch (status) {
            case READY -> reasons.add("Conversão compatível com o tipo de resultado ("
                + input.inputType() + ").");
            case DISABLED -> reasons.add("Conversão desabilitada pelo profissional.");
            case INELIGIBLE -> reasons.add("Conversão inelegível para este tipo de resultado.");
            default -> { /* SELECTED / CALCULATED não são produzidos pela sugestão */ }
        }
        return reasons;
    }
}