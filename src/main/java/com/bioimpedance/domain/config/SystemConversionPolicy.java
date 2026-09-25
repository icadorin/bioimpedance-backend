package com.bioimpedance.domain.config;

/**
 * Política padrão de conversão da plataforma.
 * <p>
 * Fonte: architecture.md §7.3 + DEC-3.
 * <p>
 * defaultConversionId é um IDENTIFICADOR OPACO (§4), resolvido pela
 * orchestration via ConversionDefinitionRegistry. Nunca
 * {@code if (conversion.name == "Siri")} no motor.
 * <p>
 * DEC-25: o valor "siri" retornado por platformDefault() é DADO DE
 * POLÍTICA (default operacional), não condição de seleção. Ele existe
 * aqui exatamente porque a DEC-3 determina que o default viva em
 * SystemConversionPolicy.defaultConversionId.
 */
public record SystemConversionPolicy(
    String defaultConversionId
) {
    /** Default operacional da plataforma (DEC-3): Siri. */
    public static SystemConversionPolicy platformDefault() {
        return new SystemConversionPolicy("siri");
    }
}