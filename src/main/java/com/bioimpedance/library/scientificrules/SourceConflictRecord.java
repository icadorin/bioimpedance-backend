package com.bioimpedance.backend.library.scientificrules;

import java.util.List;

/**
 * Fonte: schema_cientifico.md §3.4.
 * <p>
 * Distinção obrigatória: NOT_DOCUMENTED (informação ausente) é
 * diferente de SOURCE_CONFLICT (informações existentes divergem) — não
 * usar este tipo para simples ausência de dado.
 * <p>
 * Enquanto {@code resolutionStatus = UNRESOLVED}, a variante não deve
 * ser considerada cientificamente confirmada para uso (regra do
 * schema §3.4; reforçada em documento-mestre-revisao.md §3: uma
 * variante ACTIVE não pode ter sourceConflict UNRESOLVED).
 *
 * @param field             qual campo diverge entre as fontes
 * @param conflictingValues os valores divergentes e suas fontes
 * @param resolutionStatus  estado de resolução do conflito
 * @param notes             observações adicionais
 */
public record SourceConflictRecord(
        String field,
        List<ConflictingValue> conflictingValues,
        ResolutionStatus resolutionStatus,
        String notes
) {
    public SourceConflictRecord {
        conflictingValues = List.copyOf(conflictingValues);
    }
}
