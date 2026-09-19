package com.bioimpedance.library.scientificrules;

import java.util.List;

/**
 * Fonte: schema_cientifico.md §1.
 * <p>
 * {@code variantId} nunca deve ser reutilizado para representar outra
 * definição matemática ou científica (§1, "variantId").
 *
 * @param variantId   identificador estável da variante (ex.: "P-M6")
 * @param familyId    identificador da família (ex.: "petroski")
 * @param displayName nome usado na interface e relatórios
 * @param aliasNames  nomes alternativos encontrados na literatura
 */
public record VariantIdentity(
        String variantId,
        String familyId,
        String displayName,
        List<String> aliasNames
) {
    public VariantIdentity {
        aliasNames = List.copyOf(aliasNames);
    }
}
