package com.bioimpedance.dto.request;

import com.bioimpedance.domain.contracts.AssessmentObjective;
import com.bioimpedance.domain.contracts.TrainingLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

/**
 * Request do fluxo novo (Fase 12 / Chunk 2 — DEC-32/DEC-39).
 * <p>
 * Substitui o CalculateRequestDTO legacy (campos fixos navy/bio/skinfold)
 * pelo modelo inputId → valor (doc.md §3/§10/§11). O legacy permanece
 * vivo até o Chunk 4 (DEC-39).
 * <p>
 * Sexo e idade NÃO vêm aqui: são resolvidos do Client (birthDate + date)
 * na camada de serviço (doc.md §4.1, DEC-31).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentFlowRequestDTO {

    @NotBlank
    private String clientId;

    /** Data da avaliação — idade é derivada de Client.birthDate (doc.md §4.1). */
    @NotNull
    private LocalDate date;

    // ── Contexto da avaliação (doc.md §4.2) — opcionais; profissional preenche ──
    private AssessmentObjective objective;
    private TrainingLevel trainingLevel;
    private Boolean athlete;
    @Size(max = 80)
    private String modality;

    /**
     * Objetivo nutricional p/ recomendação (DEC-35):
     * "cutting" | "bulking" | "maintenance". Não confundir com objective
     * do contexto (guia §1.1).
     */
    @Size(max = 20)
    private String nutritionObjective;

    /**
     * Medidas no formato inputId canônico → valor (InputTypeCatalog).
     * Pode estar parcial: variantes sem dados ficam MISSING_INPUTS (doc.md §12).
     * Validação de qualidade/ids ocorre no domain/validation, não aqui.
     */
    @NotNull
    private Map<String, Double> measurements;

    // ── Decisões profissionais (doc.md §15/§24) ──
    /** Nulo no preview; obrigatório no calculate (doc.md §25: cálculo é ação explícita). */
    private String selectedVariantId;
    @Size(max = 200)
    private String selectionReason;

    /** Nulo → política AUTO (preferência → default Siri → Brozek; doc.md §25.3). */
    private String selectedConversionId;
    @Size(max = 200)
    private String conversionSelectionReason;
}