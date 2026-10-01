package com.bioimpedance.domain.contracts;

import lombok.Getter;

import java.util.List;

/**
 * Violação do guard de seleção (DEC-53) ou da regra de motivo (DEC-54).
 * <p>
 * Lançada pela orchestration quando a variante/conversão escolhida não está
 * em condição de execução, ou quando o motivo obrigatório está ausente na
 * finalização. Mapeada para HTTP 422 com {@code code} estável (doc.md §17.8).
 * <p>
 * Códigos: {@code SELECTION_NOT_ALLOWED} (DEC-53) e {@code REASON_REQUIRED} (DEC-54).
 * <p>
 * Regra central (especificacao_cientifica.md §14 / architecture.md §10.1):
 * a sugestão nunca bloqueia a escolha profissional; quem restringe é a
 * ELEGIBILIDADE. Uma variante INELIGIBLE/DISABLED nunca executa por override,
 * e uma MISSING_INPUTS só executa quando as medidas estiverem completas.
 */
@Getter
public class SelectionNotAllowedException extends RuntimeException {

    public static final String CODE_SELECTION_NOT_ALLOWED = "SELECTION_NOT_ALLOWED";
    public static final String CODE_REASON_REQUIRED = "REASON_REQUIRED";

    private final String code;
    /** variantId ou conversionId alvo da recusa. */
    private final String targetId;
    /** Estado operacional no momento da recusa, ou null. */
    private final String status;
    private final List<String> reasons;

    public SelectionNotAllowedException(String code, String targetId, String status, List<String> reasons) {
        super(code + " — " + targetId + (status != null ? " (" + status + ")" : ""));
        this.code = code;
        this.targetId = targetId;
        this.status = status;
        this.reasons = List.copyOf(reasons);
    }

}