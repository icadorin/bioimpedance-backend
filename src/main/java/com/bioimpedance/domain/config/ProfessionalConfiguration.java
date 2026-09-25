package com.bioimpedance.domain.config;

import java.util.List;

/**
 * Overlay do profissional sobre a biblioteca global.
 * <p>
 * Fonte: architecture.md §7 + doc.md §5–6.
 * <p>
 * O profissional NUNCA altera /library/* (§7) — esta configuração apenas
 * determina quais variantes/conversões estão disponíveis para o fluxo dele.
 * Os conjuntos enabledVariantIds/enabledConversionIds representam o estado
 * efetivo, independentemente do mode (DEC-26).
 */
public record ProfessionalConfiguration(
    String professionalId,
    ConfigurationMode mode,
    List<String> enabledVariantIds,
    List<String> enabledConversionIds,
    String preferredConversionId   // null = sem preferência (usa default)
) {
    public ProfessionalConfiguration {
        enabledVariantIds = List.copyOf(enabledVariantIds);
        enabledConversionIds = List.copyOf(enabledConversionIds);
    }

    public boolean isVariantEnabled(String variantId) {
        return enabledVariantIds.contains(variantId);
    }

    public boolean isConversionEnabled(String conversionId) {
        return enabledConversionIds.contains(conversionId);
    }

    /**
     * §7.4: restaurar o padrão de conversão remove a preferência
     * personalizada (preferredConversionId = null). A biblioteca não é
     * alterada; as habilitações permanecem.
     */
    public ProfessionalConfiguration withConversionPreferenceCleared() {
        return new ProfessionalConfiguration(professionalId, mode,
            enabledVariantIds, enabledConversionIds, null);
    }
}