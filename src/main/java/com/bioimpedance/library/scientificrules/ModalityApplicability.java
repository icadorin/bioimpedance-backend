package com.bioimpedance.library.scientificrules;

import java.util.List;

/**
 * Fonte: schema_cientifico.md §2.6.
 * <p>
 * Lista vazia = "não documentado", nunca incompatibilidade.
 * O vocabulário de modalidade é controlado pela plataforma quando
 * definido (ex.: SOCCER, BODYBUILDING, SWIMMING).
 */
public record ModalityApplicability(
        List<String> supportedModalities,
        String notes
) {
    public ModalityApplicability {
        supportedModalities = List.copyOf(supportedModalities);
    }
}
