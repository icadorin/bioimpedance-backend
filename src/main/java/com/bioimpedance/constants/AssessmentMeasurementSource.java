package com.bioimpedance.constants;

/**
 * Origem da medida (Fase 13 — DEC-57, doc.md §11).
 * MANUAL: digitada pelo profissional.
 * AVALIACAO_ANTERIOR: botão "Usar X de dd/MM"; se o valor for editado,
 * o front regrava como MANUAL (doc.md §11).
 */
public enum AssessmentMeasurementSource {
    MANUAL,
    AVALIACAO_ANTERIOR
}