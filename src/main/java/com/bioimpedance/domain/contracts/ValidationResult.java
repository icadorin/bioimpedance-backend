package com.bioimpedance.backend.domain.contracts;

import java.util.List;

/**
 * Resultado da validação dos dados de um Assessment, antes de qualquer
 * avaliação de aplicabilidade ou prontidão.
 * <p>
 * Fonte: {@code architecture.md} §2, §2.1 — "A validação dos dados deve
 * ser tratada como responsabilidade própria" e não pertence ao motor
 * matemático nem à camada de aplicabilidade científica.
 * <p>
 * IMPORTANTE: validação de dados é diferente de aplicabilidade
 * científica. Um valor pode ser válido (idade = 70 anos, uma dobra
 * plausível) e ainda assim a variante ser cientificamente inaplicável
 * para esse cliente. O inverso também vale: um dado pode ser inválido
 * independentemente de qualquer regra científica (idade = -5, dobra =
 * "abc").
 * <p>
 * {@code valid = true} não significa "pronto para cálculo" — só
 * significa que os dados presentes são estruturalmente válidos.
 * Prontidão é decidida depois, por {@code domain.eligibility},
 * combinando isto com aplicabilidade e disponibilidade de inputs.
 *
 * @param valid  se os dados são estruturalmente válidos
 * @param issues lista de problemas encontrados (vazia quando valid = true)
 */
public record ValidationResult(
        boolean valid,
        List<ValidationIssue> issues
) {
    public ValidationResult {
        issues = List.copyOf(issues);
    }
}
