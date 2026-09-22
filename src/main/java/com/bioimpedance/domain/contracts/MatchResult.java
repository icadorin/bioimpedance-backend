package com.bioimpedance.domain.contracts;

/**
 * Resultado de uma comparação entre o perfil/contexto do cliente e a
 * evidência documentada de uma EquationVariant, em uma única dimensão.
 * <p>
 * Fonte: especificacao_cientifica.md §4.2 (idade), §4.3 (população).
 * <p>
 * IMPORTANTE — este tipo representa correspondência com a evidência
 * documentada, NUNCA uma medida de precisão científica. Ver
 * especificacao_cientifica.md §4.3: "Essa classificação representa
 * correspondência com a população documentada, não uma medida de
 * precisão científica."
 *
 * @param dimension      qual dimensão foi comparada
 * @param classification resultado da comparação
 * @param notes          explicação legível, ou {@code null}
 */
public record MatchResult(
    MatchDimension dimension,
    MatchClassification classification,
    String notes
) {}
