package com.bioimpedance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Resultado numérico de uma execução (variante ou conversão).
 * Espelha PredictionResult do domain/calculation sem vazar o tipo de domínio.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PredictionDTO {

    /** variantId ou conversionId que produziu este valor. */
    private String sourceId;

    /** BODY_DENSITY | BODY_FAT_PERCENTAGE */
    private String outputType;

    private Double value;
}