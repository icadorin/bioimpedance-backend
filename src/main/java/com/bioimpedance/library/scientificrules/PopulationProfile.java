package com.bioimpedance.backend.library.scientificrules;

import java.util.List;

/**
 * Fonte: schema_cientifico.md §2.3 — PopulationProfile.
 * <p>
 * Regra sobre país e região (schema §2.3): são características
 * descritivas da população. O motor NÃO deve inferir automaticamente
 * "país = Brasil → população brasileira compatível", nem "desenvolvida
 * no Brasil → melhor para qualquer brasileiro".
 *
 * @param description              descrição da população (ex.: "Homens adultos")
 * @param country                  país associado, quando documentado
 * @param region                   região/estado/área, quando documentado
 * @param sexCoverage              sexo(s) presentes na população descrita
 * @param ageCoverage              faixa etária da população descrita
 * @param sampleSize               tamanho da amostra, quando conhecido
 * @param bodyCharacteristicsNotes características corporais relevantes
 * @param sampleCharacteristics    características específicas da amostra
 * @param source                   fonte que sustenta a descrição
 */
public record PopulationProfile(
        String description,
        String country,
        String region,
        List<Sex> sexCoverage,
        Range<Integer> ageCoverage,
        Integer sampleSize,
        String bodyCharacteristicsNotes,
        String sampleCharacteristics,
        Reference source
) {
    public PopulationProfile {
        sexCoverage = List.copyOf(sexCoverage);
    }
}
