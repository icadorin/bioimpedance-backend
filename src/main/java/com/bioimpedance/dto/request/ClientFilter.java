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
public class ClientFilter extends PageFilter {

    private String search;
    private ClientStatus status;

    private static final Set<String> ALLOWED_SORTS = Set.of(
        "createdAt", "updatedAt", "name", "email", "status"
    );

    @Override
    protected Set<String> allowedSortFields() {
        return ALLOWED_SORTS;
    }
}