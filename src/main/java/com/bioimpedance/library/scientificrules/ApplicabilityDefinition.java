package com.bioimpedance.backend.library.scientificrules;

/**
 * Fonte: schema_cientifico.md §2.
 * <p>
 * Representa os dados documentados sobre para quem a variante foi
 * desenvolvida ou validada. O motor compara esses dados com o perfil
 * do cliente — a classificação resultante NÃO é armazenada aqui.
 */
public record ApplicabilityDefinition(
        SexApplicability sex,
        AgeApplicability age,
        PopulationApplicability population,
        AthleteApplicability athlete,
        TrainingLevelApplicability trainingLevel,
        ModalityApplicability modality,
        BodyCharacteristicApplicability bodyCharacteristics
) {
}
