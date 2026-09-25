package com.bioimpedance.library.scientificrules;

/**
 * Fonte: schema_cientifico.md §2.4.
 * <p>
 * Semântica explícita do schema:
 * <pre>
 * true  = documentado como sim
 * false = documentado como não
 * null  = não documentado (NUNCA significa "não aplicável" ou "proibido")
 * </pre>
 * Ausência de evidência específica é NOT_DOCUMENTED, nunca INELIGIBLE.
 * A inelegibilidade só pode vir de restrição científica explícita.
 * <p>
 * Usa {@link Boolean} (boxed), não {@code boolean} primitivo — o
 * terceiro estado {@code null} é semanticamente obrigatório aqui.
 */
public record AthleteApplicability(
    Boolean developedInAthletes,
    Boolean validatedInAthletes,
    Boolean developedInNonAthletes,
    Boolean validatedInNonAthletes,
    String explicitAthleteRestriction
) {}
