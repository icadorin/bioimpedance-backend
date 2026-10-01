package com.bioimpedance.library.measurements;

/**
 * Grupo de exibição de um input na coleta (Fase 13 — DEC-59).
 * <p>
 * O front agrupa os campos por este valor, nunca deduzindo o grupo pelo
 * prefixo do inputId (architecture.md §4: nenhuma seleção/dedução por nome).
 */
public enum InputGroup {
    /** Idade, massa corporal, estatura. */
    BASIC,
    /** Dobras cutâneas. */
    SKINFOLD,
    /** Circunferências. */
    CIRCUMFERENCE
}