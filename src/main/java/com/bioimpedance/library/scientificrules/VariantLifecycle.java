package com.bioimpedance.library.scientificrules;

import java.time.LocalDate;
import java.util.List;

/**
 * Fonte: schema_cientifico.md §6.
 * <p>
 * O ciclo de vida da variante é separado da evidência científica —
 * uma variante DRAFT pode ter evidência completa, e uma ACTIVE não
 * precisa ter todo campo preenchido (só os essenciais, ver §6.1).
 */
public record VariantLifecycle(
    LifecycleStatus status,
    String version,
    String supersedes,
    String supersededBy,
    LocalDate effectiveFrom,
    List<ChangeLogEntry> changeLog
) {
    public VariantLifecycle {
        changeLog = List.copyOf(changeLog);
    }
}
