package com.bioimpedance.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Converte PageFilter em Pageable do Spring Data.
 *
 * Separação de responsabilidades: o DTO representa apenas dados,
 * a conversão para infraestrutura fica aqui.
 */
public final class PageableUtils {

    private PageableUtils() {}

    public static Pageable of(PageFilter filter) {
        Sort.Direction springDirection = filter.getDirection() == SortDirection.ASC
            ? Sort.Direction.ASC
            : Sort.Direction.DESC;

        return PageRequest.of(
            filter.getPage(),
            filter.getSize(),
            Sort.by(springDirection, filter.getEffectiveSort())
        );
    }
}