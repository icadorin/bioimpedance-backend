package com.bioimpedance.backend.library.scientificrules;

/**
 * Fonte: schema_cientifico.md §5.
 * <p>
 * Toda restrição deve possuir fonte (regra explícita) — isso evita
 * transformar "não documentado" em "não permitido". Estas são as
 * únicas regras que podem produzir INELIGIBLE quando sua condição for
 * satisfeita; ausência de evidência (NOT_DOCUMENTED) nunca basta.
 */
public record ScientificRestriction(
        RestrictionType type,
        String condition,
        RestrictionSeverity severity,
        String description,
        Reference source
) {
}
