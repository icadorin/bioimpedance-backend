package com.bioimpedance.library.equations;

/**
 * Fonte: architecture.md §4.2.
 * Define o esqueleto matemático da equação. O motor usa isso para saber
 * como combinar os coeficientes e inputs, sem precisar saber o nome da variante.
 */
public enum FormulaTemplate {
    /** c0 + c1 * S (ex: Faulkner) */
    LINEAR_SUM,
    /** c0 + c1 * log10(S) (ex: Guedes sem idade) */
    LOG10_SUM,
    /** c0 + c1*S + c2*S² + c3*AGE (ex: Jackson & Pollock) */
    QUADRATIC_SUM_WITH_AGE,
    /** c0 + c1*S + c2*S² + c3*AGE + c4*CIRC1 + c5*CIRC2 (ex: Petroski M com circunferências) */
    QUADRATIC_SUM_WITH_AGE_AND_CIRC,
    /** c0 + c1*S + c2*S² + c3*AGE + c4*MASS + c5*HEIGHT (ex: Petroski F com massa/altura) */
    QUADRATIC_SUM_WITH_AGE_MASS_HEIGHT,
    /** c0 + c1*log10(S) + c2*AGE (ex: Petroski F log) */
    LOG10_SUM_WITH_AGE,
    /** c0 + c1*log10(S) + c2*AGE + c3*CIRC (ex: Petroski F log com circunferência) */
    LOG10_SUM_WITH_AGE_AND_CIRC
}