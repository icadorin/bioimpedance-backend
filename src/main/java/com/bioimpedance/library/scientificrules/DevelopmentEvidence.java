package com.bioimpedance.library.scientificrules;

/**
 * Fonte: schema_cientifico.md §3.1.
 * <p>
 * NOTA DE FIDELIDADE AO DADO REAL: o schema define {@code population}
 * como {@code PopulationProfile}, mas nas 44 fichas reais esse campo
 * sempre aparece como resumo textual (ex.: "Homens adultos, n = 391,
 * 18–66 anos") — a PopulationProfile completa mora em
 * {@code applicability.population.originalPopulation}. Modelado aqui
 * como {@code String} para bater com o dado real; se as fichas forem
 * atualizadas para o formato estrito do schema, este tipo precisa
 * mudar junto.
 */
public record DevelopmentEvidence(
    Reference studyReference,
    String population,
    String criterionMethod,
    Integer year,
    ValidationMetrics metrics
) {}
