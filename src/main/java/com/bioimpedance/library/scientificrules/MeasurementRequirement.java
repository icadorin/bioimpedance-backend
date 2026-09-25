package com.bioimpedance.library.scientificrules;

import java.util.List;

/**
 * Fonte: schema_cientifico.md §4.
 * <p>
 * Descreve APENAS quais tipos de dado a variante exige — o que cada
 * inputId significa (unidade, precisão, faixa plausível) pertence ao
 * catálogo global em library.measurements (InputTypeCatalog), nunca
 * repetido aqui (regra explícita do schema §4.2, "Regra de separação").
 * <p>
 * {@code inputId} é modelado como {@code String} (não um enum fechado
 * neste pacote) porque o catálogo de inputs é definido e evolui em
 * library.measurements — este pacote só referencia o identificador.
 *
 * @param requiredInputs inputId obrigatórios para executar a variante
 * @param optionalInputs inputId opcionais, não bloqueiam execução se ausentes
 */
public record MeasurementRequirement(
    List<String> requiredInputs,
    List<String> optionalInputs
) {
    public MeasurementRequirement {
        requiredInputs = List.copyOf(requiredInputs);
        optionalInputs = List.copyOf(optionalInputs);
    }
}
