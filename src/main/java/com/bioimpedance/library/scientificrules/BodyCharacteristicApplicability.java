package com.bioimpedance.library.scientificrules;

import java.util.List;

/** Fonte: schema_cientifico.md §2.7. */
public record BodyCharacteristicApplicability(List<BodyCharacteristicRule> rules) {
    public BodyCharacteristicApplicability {
        rules = List.copyOf(rules);
    }
}
