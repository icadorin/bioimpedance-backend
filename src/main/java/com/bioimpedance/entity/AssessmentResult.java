package com.bioimpedance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

/**
 * Resultado de uma avaliação física (Fase 12 / Chunk 4 — DEC-35/DEC-36).
 * <p>
 * Contém tanto o resultado científico (variante + conversão) quanto as
 * métricas derivadas (IMC, BMR, TDEE, etc.) aplicadas DEPOIS do resultado
 * final do orchestrator (DEC-35).
 * <p>
 * Os campos derivados (imc, bodyFat, leanMass, etc.) são mantidos para
 * compatibilidade com ClientProgressService/DashboardService legados.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentResult {

    // ── Resultado científico (orchestrator) ──

    /** variantId usado no cálculo (ex: "JP7-M"). */
    @Column(name = "result_variant_id", length = 64)
    private String variantId;

    /** conversionId usado (ex: "siri"), ou null se a variante produz %G direto. */
    @Column(name = "result_conversion_id", length = 64)
    private String conversionId;

    /** BODY_DENSITY ou BODY_FAT_PERCENTAGE. */
    @Column(name = "result_output_type", length = 32)
    private String outputType;

    /** Valor final da avaliação (ex: 14.7 para %G, 1.065 para densidade). */
    @Column(name = "result_final_value")
    private Double finalValue;

    /** id do AuditSnapshot append-only. */
    @Column(name = "result_audit_id", length = 64)
    private String auditId;

    // ── Métricas derivadas (DEC-35: aplicadas DEPOIS do resultado final) ──

    /** Índice de Massa Corporal (peso / altura²). */
    private Double imc;

    /** Percentual de gordura corporal (mesmo valor de finalValue quando outputType = BODY_FAT_PERCENTAGE). */
    private Double bodyFat;

    /** Massa magra em kg (peso × (1 - bodyFat/100)). */
    private Double leanMass;

    /** Massa gorda em kg (peso × bodyFat/100). */
    private Double fatMass;

    /** Fat-Free Mass Index (leanMass / altura²). */
    private Double ffmi;

    /** Basal Metabolic Rate (kcal/dia). */
    private Double bmr;

    /** Total Daily Energy Expenditure (kcal/dia). */
    private Double tdee;

    /** Calorias alvo baseado no objetivo nutricional. */
    private Double targetCalories;

    /** Classificação do %G (ex: "Atleta", "Saudável", "Acima da média"). */
    private String bodyFatLevel;
}