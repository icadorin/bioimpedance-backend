package com.bioimpedance.backend.domain.contracts;

/**
 * Um problema específico encontrado durante a validação.
 * <p>
 * {@code inputId} fica {@code null} quando o problema não é atribuível
 * a um único campo (ex.: INCONSISTENCY entre dois campos, CONFLICT
 * entre duas medições do mesmo tipo).
 *
 * @param type    tipo do problema
 * @param inputId identificador do input afetado, ou {@code null}
 * @param message mensagem legível para o profissional
 */
public record ValidationIssue(
        ValidationIssueType type,
        String inputId,
        String message
) {
}
