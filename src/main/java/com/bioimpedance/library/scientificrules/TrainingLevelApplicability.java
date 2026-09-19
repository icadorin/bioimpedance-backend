package com.bioimpedance.library.scientificrules;

import java.util.List;

/**
 * Fonte: schema_cientifico.md §2.5.
 * <p>
 * Uma lista vazia significa "não documentado" — NUNCA "todos os
 * níveis". O nível de treinamento só deve participar da avaliação
 * quando houver evidência correspondente.
 */
public record TrainingLevelApplicability(
        List<TrainingLevel> supportedLevels,
        String notes
) {
    public TrainingLevelApplicability {
        supportedLevels = List.copyOf(supportedLevels);
    }
}
