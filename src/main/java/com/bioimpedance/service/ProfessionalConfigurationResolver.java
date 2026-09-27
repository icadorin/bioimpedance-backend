package com.bioimpedance.service;

import com.bioimpedance.domain.config.ConfigurationMode;
import com.bioimpedance.domain.config.ProfessionalConfiguration;
import com.bioimpedance.library.conversions.ConversionDefinitionRegistry;
import com.bioimpedance.library.scientificrules.ScientificRuleRegistry;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Monta a ProfessionalConfiguration EFETIVA de um profissional
 * (doc.md §5–6, DEC-24/DEC-26).
 * <p>
 * V1: ainda não existe persistência de configuração customizada, então
 * TODO profissional roda em DEFAULT = biblioteca inteira habilitada e
 * SEM preferência de conversão (o default operacional Siri vem do
 * SystemConversionPolicy, não daqui — DEC-3/DEC-25).
 * <p>
 * Ponto de plug futuro: quando existir tabela de configuração custom,
 * este resolver é o ÚNICO lugar que muda; chamadores não sentem.
 * <p>
 * Nenhum id de variante/conversão é hardcoded (architecture.md §4):
 * a lista de habilitados vem dos registries da biblioteca.
 */
@Component
public class ProfessionalConfigurationResolver {

    private final ScientificRuleRegistry scientificRuleRegistry;
    private final ConversionDefinitionRegistry conversionDefinitionRegistry;

    public ProfessionalConfigurationResolver(
        ScientificRuleRegistry scientificRuleRegistry,
        ConversionDefinitionRegistry conversionDefinitionRegistry) {
        this.scientificRuleRegistry = scientificRuleRegistry;
        this.conversionDefinitionRegistry = conversionDefinitionRegistry;
    }

    /**
     * Configuração efetiva do profissional.
     * V1: sempre DEFAULT (DEC-26). sorted() garante determinismo (architecture.md §20).
     */
    public ProfessionalConfiguration resolve(String professionalId) {
        List<String> enabledVariantIds = scientificRuleRegistry.all().stream()
            .map(profile -> profile.identity().variantId())
            .sorted()
            .toList();

        List<String> enabledConversionIds = conversionDefinitionRegistry.getAll().keySet().stream()
            .sorted()
            .toList();

        return new ProfessionalConfiguration(
            professionalId,
            ConfigurationMode.DEFAULT,
            enabledVariantIds,
            enabledConversionIds,
            null // sem preferência: default operacional vem do SystemConversionPolicy (DEC-3)
        );
    }
}