package com.bioimpedance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Catálogo de tipos de medida (Fase 13 / B2 — DEC-59, doc.md §17.8).
 * DTO próprio: não vaza InputTypeDefinition (library) para o front.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputTypeCatalogDTO {

    private String inputId;
    private String label;
    /** DECIMAL | INTEGER */
    private String valueType;
    /** MM | KG | CM | YEARS */
    private String unit;
    private Integer precision;
    private Double minPlausible;
    private Double maxPlausible;
    private String measurementProtocolNotes;
    /** BASIC | SKINFOLD | CIRCUMFERENCE (DEC-59) — agrupamento da coleta. */
    private String group;
}