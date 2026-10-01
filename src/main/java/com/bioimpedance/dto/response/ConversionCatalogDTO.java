package com.bioimpedance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Catálogo de conversões (Fase 13 / B2 — DEC-59, doc.md §17.8).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversionCatalogDTO {

    private String conversionId;
    private String name;
    private String inputType;
    private String outputType;
}