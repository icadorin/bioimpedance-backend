package com.bioimpedance.backend.library.scientificrules;

import java.time.LocalDate;

/** Fonte: schema_cientifico.md §6. */
public record ChangeLogEntry(
        String version,
        LocalDate date,
        String summary
) {
}
