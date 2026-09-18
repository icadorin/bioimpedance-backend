package com.bioimpedance.backend.library.scientificrules;

/**
 * Fonte: schema_cientifico.md §3.2.
 * <p>
 * Usado para validationStudies, crossValidationStudies e
 * externalValidationStudies — a categoria (qual dessas três) é dada
 * pelo campo em que a instância está, não por um atributo interno.
 * <p>
 * Regra de classificação (documento-mestre-revisao.md §5):
 * subamostra independente do MESMO estudo original → validationStudies;
 * dado/equação de OUTRO estudo/pesquisador → crossValidationStudies;
 * validação totalmente independente do programa original → externalValidationStudies.
 */
public record ValidationStudy(
        Reference studyReference,
        String population,
        String criterionMethod,
        ValidationMetrics metrics,
        String limitations
) {
}
