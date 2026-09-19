package com.bioimpedance.library.scientificrules;

import java.util.List;

/**
 * Fonte: schema_cientifico.md §2.1.
 * <p>
 * {@code supportedSexes} descreve a população documentada. Uma lista
 * vazia não é permitida (regra explícita do schema). Uma incompatibilidade
 * explícita é tratada posteriormente pelo mecanismo de
 * aplicabilidade/restrição — este tipo só registra o dado.
 */
public record SexApplicability(List<Sex> supportedSexes) {
    public SexApplicability {
        if (supportedSexes == null || supportedSexes.isEmpty()) {
            throw new IllegalArgumentException("supportedSexes não pode ser vazio (schema_cientifico.md §2.1)");
        }
        supportedSexes = List.copyOf(supportedSexes);
    }
}
