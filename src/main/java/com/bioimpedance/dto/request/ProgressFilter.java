package com.bioimpedance.dto.request;

import com.bioimpedance.constants.ClientStatus;
import com.bioimpedance.pagination.PageFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ProgressFilter extends PageFilter {

    private String search;
    private ClientStatus status;

    private static final Set<String> ALLOWED_SORTS = Set.of(
        "createdAt", "clientName", "weightDiff", "bodyFatDiff", "leanMassDiff"
    );

    @Override
    protected Set<String> allowedSortFields() {
        return ALLOWED_SORTS;
    }

    @Override
    protected String defaultSortField() {
        return "bodyFatDiff";
    }
}