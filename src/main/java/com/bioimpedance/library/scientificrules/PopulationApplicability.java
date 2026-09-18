package com.bioimpedance.backend.library.scientificrules;

import java.util.List;

/** Fonte: schema_cientifico.md §2.3. */
public record PopulationApplicability(
        PopulationProfile originalPopulation,
        List<PopulationProfile> validationPopulations
) {
    public PopulationApplicability {
        validationPopulations = List.copyOf(validationPopulations);
    }
}
