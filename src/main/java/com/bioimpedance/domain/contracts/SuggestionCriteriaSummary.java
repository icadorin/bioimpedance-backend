package com.bioimpedance.backend.domain.contracts;

/**
 * Conjunto de critérios usados para EXPLICAR uma indicação — nunca para
 * transformá-la em pontuação.
 * <p>
 * Fonte: especificacao_cientifica.md §11.2 (Compatibilidade):
 * "Podem ser consideradas: sexMatch, ageMatch, populationMatch,
 * contextMatch, evidenceCoverage, dataAvailability, measurementQuality.
 * Essas informações devem servir para explicar por que uma variante é
 * indicada ou não. Não devem ser tratadas automaticamente como uma
 * pontuação científica."
 * <p>
 * Ver também §2 (Princípio Central): o motor não deve transformar
 * validade científica em pontuação arbitrária.
 */
public record SuggestionCriteriaSummary(
        MatchResult sexMatch,
        MatchResult ageMatch,
        MatchResult populationMatch,
        MatchResult contextMatch,
        EvidenceSummary evidenceCoverage,
        CandidateStatus dataAvailability,
        MeasurementQuality measurementQuality
) {
}
