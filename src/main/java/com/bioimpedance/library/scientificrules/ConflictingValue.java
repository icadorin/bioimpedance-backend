package com.bioimpedance.library.scientificrules;

/** Fonte: schema_cientifico.md §3.4. */
public record ConflictingValue(
        String value,
        Reference source
) {
}
