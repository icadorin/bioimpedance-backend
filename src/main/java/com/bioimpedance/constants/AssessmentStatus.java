package com.bioimpedance.constants;

/**
 * Ciclo de vida da avaliação (Fase 13 — DEC-52/58/61).
 * DRAFT: rascunho editável (autosave); invisível para leitores legados.
 * FINALIZED: travada, com resultado + auditoria; fonte de histórico,
 * dashboard e gráficos. O CALCULATED cogitado não existe (DEC-58).
 */
public enum AssessmentStatus {
    DRAFT,
    FINALIZED
}