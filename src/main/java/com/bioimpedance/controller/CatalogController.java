package com.bioimpedance.controller;

import com.bioimpedance.dto.response.ConversionCatalogDTO;
import com.bioimpedance.dto.response.InputTypeCatalogDTO;
import com.bioimpedance.dto.response.VariantCatalogDTO;
import com.bioimpedance.library.conversions.ConversionDefinition;
import com.bioimpedance.library.conversions.ConversionDefinitionRegistry;
import com.bioimpedance.library.equations.EquationVariantRegistry;
import com.bioimpedance.library.measurements.InputTypeCatalog;
import com.bioimpedance.library.measurements.InputTypeDefinition;
import com.bioimpedance.library.scientificrules.EquationVariantScientificProfile;
import com.bioimpedance.library.scientificrules.ScientificRuleRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;

/**
 * Catálogos somente-leitura (Fase 13 / B2 — DEC-59, doc.md §17.8).
 * <p>
 * As bibliotecas são imutáveis por versão (carregadas no boot, fail-fast),
 * então as respostas recebem Cache-Control público. O front (F1) complementa
 * com staleTime longo no react-query.
 * <p>
 * Nenhum estado científico é exposto aqui: aplicabilidade/prontidão/sugestão
 * vivem no painel (doc.md §17), recalculados pelo back a cada gravação.
 */
@RestController
@RequestMapping("/api/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private static final CacheControl CATALOG_CACHE =
        CacheControl.maxAge(Duration.ofDays(1)).cachePublic();

    private final InputTypeCatalog inputTypeCatalog;
    private final ScientificRuleRegistry scientificRuleRegistry;
    private final EquationVariantRegistry equationVariantRegistry;
    private final ConversionDefinitionRegistry conversionDefinitionRegistry;

    @GetMapping("/inputs")
    public ResponseEntity<List<InputTypeCatalogDTO>> inputs() {
        List<InputTypeCatalogDTO> body = inputTypeCatalog.all().stream()
            .map(this::toInputDTO)
            .toList();
        return ResponseEntity.ok().cacheControl(CATALOG_CACHE).body(body);
    }

    @GetMapping("/variants")
    public ResponseEntity<List<VariantCatalogDTO>> variants() {
        List<VariantCatalogDTO> body = scientificRuleRegistry.all().stream()
            .sorted(Comparator.comparing(p -> p.identity().variantId()))
            .map(this::toVariantDTO)
            .toList();
        return ResponseEntity.ok().cacheControl(CATALOG_CACHE).body(body);
    }

    @GetMapping("/conversions")
    public ResponseEntity<List<ConversionCatalogDTO>> conversions() {
        List<ConversionCatalogDTO> body = conversionDefinitionRegistry.getAll().values().stream()
            .sorted(Comparator.comparing(ConversionDefinition::id))
            .map(this::toConversionDTO)
            .toList();
        return ResponseEntity.ok().cacheControl(CATALOG_CACHE).body(body);
    }

    // ==================== MAPPING (library → DTO) ====================

    private InputTypeCatalogDTO toInputDTO(InputTypeDefinition def) {
        return InputTypeCatalogDTO.builder()
            .inputId(def.inputId())
            .label(def.label())
            .valueType(def.valueType().name())
            .unit(def.unit().name())
            .precision(def.precision())
            .minPlausible(def.plausibleRange() != null ? def.plausibleRange().min() : null)
            .maxPlausible(def.plausibleRange() != null ? def.plausibleRange().max() : null)
            .measurementProtocolNotes(def.measurementProtocolNotes())
            .group(def.group().name())
            .build();
    }

    private VariantCatalogDTO toVariantDTO(EquationVariantScientificProfile profile) {
        String variantId = profile.identity().variantId();
        return VariantCatalogDTO.builder()
            .variantId(variantId)
            .familyId(profile.identity().familyId())
            .displayName(profile.identity().displayName())
            .aliasNames(profile.identity().aliasNames())
            .supportedSexes(profile.applicability().sex().supportedSexes().stream()
                .map(Enum::name)
                .toList())
            .requiredInputs(profile.inputs().requiredInputs())
            .outputType(equationVariantRegistry.resolve(variantId).outputType())
            .build();
    }

    private ConversionCatalogDTO toConversionDTO(ConversionDefinition def) {
        return ConversionCatalogDTO.builder()
            .conversionId(def.id())
            .name(def.name())
            .inputType(def.inputType())
            .outputType(def.outputType())
            .build();
    }
}