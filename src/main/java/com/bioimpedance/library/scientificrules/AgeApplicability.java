package com.bioimpedance.library.scientificrules;

import java.util.List;

/**
 * Fonte: schema_cientifico.md §2.2.
 * <p>
 * IMPORTANTE (regra explícita do schema): {@code developmentSampleMeanAge}
 * é dado descritivo, NUNCA normativo. O motor nunca deve usá-lo como
 * limite de elegibilidade.
 * <p>
 * {@code explicitAgeRestriction} só deve ser preenchido quando existir
 * restrição explícita documentada na literatura — diferente de
 * simplesmente não possuir validação em determinada idade.
 *
 * @param originalDevelopmentAgeRange faixa etária da população de desenvolvimento
 * @param developmentSampleMeanAge    média de idade da amostra de desenvolvimento; descritivo, nunca normativo
 * @param validatedAgeRanges          faixas documentadas em estudos de validação
 * @param explicitAgeRestriction      restrição explícita documentada, ou null
 */
public record AgeApplicability(
        Range<Integer> originalDevelopmentAgeRange,
        Double developmentSampleMeanAge,
        List<AgeValidationEntry> validatedAgeRanges,
        Range<Integer> explicitAgeRestriction
) {
    public AgeApplicability {
        validatedAgeRanges = List.copyOf(validatedAgeRanges);
    }
}
