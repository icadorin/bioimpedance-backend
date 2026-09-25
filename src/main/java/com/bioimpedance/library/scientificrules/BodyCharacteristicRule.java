package com.bioimpedance.library.scientificrules;

/**
 * Fonte: schema_cientifico.md §2.7.
 * <p>
 * Uma característica só deve ser adicionada quando houver fundamento
 * científico suficiente (regra explícita do schema) — não inventar
 * regra a partir de observação isolada.
 *
 * @param characteristic    categoria da característica corporal
 * @param otherDescription  descrição livre quando characteristic = OTHER
 * @param effect            efeito documentado dessa condição
 * @param description       descrição textual do efeito
 * @param source            fonte, quando disponível
 */
public record BodyCharacteristicRule(
    BodyCharacteristic characteristic,
    String otherDescription,
    BodyCharacteristicEffect effect,
    String description,
    Reference source
) {}
