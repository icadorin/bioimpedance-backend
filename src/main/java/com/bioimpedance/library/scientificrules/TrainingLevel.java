package com.bioimpedance.backend.library.scientificrules;

/**
 * Escala única de nível de treinamento — deve corresponder à definição
 * oficial de Context.trainingLevel usada em toda a plataforma.
 * <p>
 * Fonte: schema_cientifico.md §2.5.
 * Ver nota de fronteira no package-info sobre duplicação com domain.contracts.TrainingLevel.
 */
public enum TrainingLevel {
    SEDENTARY,
    RECREATIONAL,
    TRAINED,
    COMPETITIVE,
    ELITE
}
