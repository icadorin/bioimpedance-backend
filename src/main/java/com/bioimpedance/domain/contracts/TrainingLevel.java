package com.bioimpedance.backend.domain.contracts;

/**
 * Escala única de nível de treinamento usada em toda a plataforma.
 * <p>
 * Fonte: especificacao_cientifica.md §4.5 — "A escala utilizada pelo
 * sistema deve ser única e definida pela especificação de Context."
 * Não redefinir esta escala em outro lugar.
 */
public enum TrainingLevel {
    SEDENTARY,
    RECREATIONAL,
    TRAINED,
    COMPETITIVE,
    ELITE
}
