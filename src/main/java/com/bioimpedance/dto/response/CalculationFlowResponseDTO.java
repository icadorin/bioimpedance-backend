package com.bioimpedance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response do calculate: painel + decisão profissional + cadeia de resultados
 + auditoria (doc.md §24/§28).
 * <p>
 * Métricas derivadas (IMC/BMR/TDEE/FFMI/%G level) e recomendação entram
 * no Chunk 4 (DEC-35), aplicadas DEPOIS do finalResult.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalculationFlowResponseDTO {

    /** id da Assessment persistida pelo fluxo (Chunk 3). */
    private String assessmentId;

    /** id do AuditSnapshot append-only (architecture.md §6). */
    private String auditId;

    /** Painel de estado no momento da execução (doc.md §17). */
    private AssessmentFlowResponseDTO flow;

    // ── Decisão profissional da variante (doc.md §24) ──
    private String selectedVariantId;
    private boolean variantOverride;
    private String variantOverrideReason;


    /** Saída da variante (pode ser intermediária, ex.: BODY_DENSITY). */
    private PredictionDTO prediction;

    /** Nulo quando a variante produz resultado final direto (doc.md §22.2). */
    private ConversionSuggestionDTO conversionSuggestion;

    // ── Decisão profissional da conversão (doc.md §24.1) ──
    private String selectedConversionId;
    private boolean conversionOverride;
    private String conversionOverrideReason;

    /** Resultado final da avaliação (pós-conversão, quando aplicável). */
    private PredictionDTO finalResult;
}