package com.bioimpedance.library.scientificrules;

/**
 * Fonte: schema_cientifico.md §9 (Ficha Completa) — a raiz que agrega
 * todo o resto deste pacote. Espelha 1:1 a estrutura da seção 17
 * ("Ficha Consolidada") das 44 fichas científicas.
 * <p>
 * Este é o objeto que {@code ScientificRuleRegistry.resolve(variantId)}
 * retorna (equation_family_especificacao_formal.md §7).
 * <p>
 * Regra crítica (schema_cientifico.md, "Convenção fundamental"): este
 * tipo NUNCA deve conter ageMatch, populationMatch, sexMatch,
 * contextMatch, evidenceCoverage, READY, WARNING ou INELIGIBLE — esses
 * são sempre calculados por domain.applicability/domain.eligibility a
 * partir destes dados, nunca armazenados aqui.
 * <p>
 * Regra de campos essenciais para status ACTIVE (schema §6.1):
 * identity, mathematicalDefinition (em library.equations, não aqui),
 * applicability.sex.supportedSexes, applicability.age.originalDevelopmentAgeRange,
 * applicability.population.originalPopulation, inputs.requiredInputs,
 * e a referência da definição matemática devem estar preenchidos.
 * Validar isso é responsabilidade do loader (Fase 2c/2d), não deste
 * record — este tipo só representa o dado, não valida regra de negócio.
 *
 * @param identity      identificação da variante
 * @param applicability para quem a variante se aplica
 * @param evidence      evidência científica documentada
 * @param inputs        dados matemáticos exigidos pela fórmula
 * @param restrictions  restrições científicas explícitas
 * @param lifecycle     estado de ciclo de vida e versionamento
 */
public record EquationVariantScientificProfile(
    VariantIdentity identity,
    ApplicabilityDefinition applicability,
    ValidationEvidence evidence,
    MeasurementRequirement inputs,
    java.util.List<ScientificRestriction> restrictions,
    VariantLifecycle lifecycle
) {
    public EquationVariantScientificProfile {
        restrictions = java.util.List.copyOf(restrictions);
    }
}
