package com.bioimpedance.backend.library.scientificrules;

import java.util.List;

/**
 * Fonte: schema_cientifico.md §3.
 * <p>
 * {@code sourceConflict} é {@code null} na esmagadora maioria das
 * variantes — só é populado quando existe divergência material entre
 * fontes primárias para o MESMO parâmetro da MESMA variante (ver
 * SourceConflictRecord).
 */
public record ValidationEvidence(
        DevelopmentEvidence development,
        List<ValidationStudy> validationStudies,
        List<ValidationStudy> crossValidationStudies,
        List<ValidationStudy> externalValidationStudies,
        SourceConflictRecord sourceConflict
) {
    public ValidationEvidence {
        validationStudies = List.copyOf(validationStudies);
        crossValidationStudies = List.copyOf(crossValidationStudies);
        externalValidationStudies = List.copyOf(externalValidationStudies);
    }
}
