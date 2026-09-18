package com.bioimpedance.backend.domain.contracts;

/**
 * Classificações documentadas nos 5 documentos-base:
 * <ul>
 *   <li>EXACT / HIGH / MODERATE / PARTIAL / LOW — graus de correspondência
 *       (especificacao_cientifica.md §4.2–4.3)</li>
 *   <li>OUTSIDE_VALIDATED_RANGE — fora de faixa com restrição científica
 *       explícita (idade)</li>
 *   <li>NOT_DOCUMENTED — ausência de evidência específica (NUNCA deve
 *       virar INELIGIBLE por si só — ver especificacao_cientifica.md
 *       §4.4, §4.6)</li>
 *   <li>UNKNOWN — dados insuficientes para classificar</li>
 * </ul>
 * <p>
 * Nem toda classificação é válida para toda {@link MatchDimension} —
 * quem decide qual subconjunto usar em cada dimensão é
 * {@code domain.applicability}, não este contrato.
 */
public enum MatchClassification {
    EXACT,
    HIGH,
    MODERATE,
    PARTIAL,
    LOW,
    OUTSIDE_VALIDATED_RANGE,
    NOT_DOCUMENTED,
    UNKNOWN
}
