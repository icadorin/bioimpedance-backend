package com.bioimpedance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Catálogo de variantes científicas (Fase 13 / B2 — DEC-59, doc.md §17.8):
 * nome, família, aliases, sexos, requiredInputs e outputType.
 * O front usa para rótulos/destaques; nenhum estado científico vem daqui
 * (estado vem do painel, sempre recalculado pelo back).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantCatalogDTO {

    private String variantId;
    private String familyId;
    private String displayName;
    private List<String> aliasNames;
    /** MALE | FEMALE */
    private List<String> supportedSexes;
    private List<String> requiredInputs;
    /** BODY_DENSITY | BODY_FAT_PERCENTAGE */
    private String outputType;
}