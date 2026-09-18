package com.bioimpedance.backend.domain.contracts;

/**
 * Combinação de Perfil do Cliente + Contexto da Avaliação.
 * <p>
 * Fonte: especificacao_cientifica.md §4 ("A análise de uma variante
 * depende do perfil do cliente e do contexto da avaliação. O perfil
 * contém dados relativamente estáveis. O contexto contém informações
 * específicas da avaliação.")
 *
 * @param client    perfil estável do cliente
 * @param objective objetivo desta avaliação específica, ou null se não informado
 */
public record AssessmentContext(
        ClientProfile client,
        AssessmentObjective objective
) {
}
