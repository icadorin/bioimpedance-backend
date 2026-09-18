package com.bioimpedance.backend.domain.contracts;

/**
 * Dados relativamente estáveis do cliente, usados como entrada para
 * applicability, eligibility e suggestion.
 * <p>
 * Fonte: especificacao_cientifica.md §4.
 * <p>
 * {@code athlete} usa {@link Boolean} (não {@code boolean} primitivo)
 * porque {@code null} é um terceiro estado válido e semanticamente
 * diferente de {@code false}: significa "não documentado pelo
 * profissional", não "documentado como não atleta"
 * (especificacao_cientifica.md §4.4).
 *
 * @param sex           sexo do cliente
 * @param age           idade em anos
 * @param athlete       true/false = documentado; null = não documentado
 * @param trainingLevel nível de treinamento, ou null se não documentado
 * @param modality      vocabulário controlado da plataforma (ex.: SOCCER), ou null
 */
public record ClientProfile(
        Sex sex,
        int age,
        Boolean athlete,
        TrainingLevel trainingLevel,
        String modality
) {
}
