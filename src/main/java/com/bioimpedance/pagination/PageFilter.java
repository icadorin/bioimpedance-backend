package com.bioimpedance.pagination;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Filtro base para endpoints paginados.
 * Contém apenas os parâmetros universais de paginação/ordenação.
 *
 * SEGURANÇA: O campo `sort` é validado contra uma whitelist na subclasse
 * para evitar que o frontend ordene por campos sensíveis (ex: password).
 */
@Getter
@Setter
public abstract class PageFilter {

    public static final int DEFAULT_SIZE = 20;
    public static final int MAX_SIZE = 100;

    @Min(value = 0, message = "page deve ser >= 0")
    private int page = 0;

    @Min(value = 1, message = "size deve ser >= 1")
    @Max(value = MAX_SIZE, message = "size deve ser <= " + MAX_SIZE)
    private int size = DEFAULT_SIZE;

    private String sort = "createdAt";
    private SortDirection direction = SortDirection.DESC;

    /**
     * Cada subclasse define os campos permitidos para ordenação.
     * Padrão seguro: retorna apenas o campo default.
     */
    protected Set<String> allowedSortFields() {
        return Set.of(defaultSortField());
    }

    protected String defaultSortField() {
        return "createdAt";
    }

    /**
     * Retorna o campo de ordenação efetivo (validado contra whitelist).
     * Aplica .trim() para evitar falhas por espaços acidentais na URL.
     */
    public String getEffectiveSort() {
        if (sort == null || sort.isBlank()) {
            return defaultSortField();
        }
        String trimmed = sort.trim();
        return allowedSortFields().contains(trimmed) ? trimmed : defaultSortField();
    }
}