package com.bioimpedance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Envelope de resposta para a busca paginada de avaliações.
 *
 * Não expõe o objeto Page do Spring diretamente — apenas os campos
 * necessários para o frontend renderizar paginação sem acoplar ao
 * modelo interno do Spring Data.
 */
@Getter
@AllArgsConstructor
public class PagedAssessmentResponseDTO {

    /** Avaliações da página atual, já mapeadas para DTO seguro. */
    private List<AssessmentResponseDTO> content;

    /** Número da página atual (0-based). */
    private int page;

    /** Tamanho da página solicitado. */
    private int size;

    /** Total de avaliações que satisfazem os filtros. */
    private long totalElements;

    /** Total de páginas disponíveis. */
    private int totalPages;

    /** Indica se é a última página (evita o frontend calcular). */
    private boolean last;
}