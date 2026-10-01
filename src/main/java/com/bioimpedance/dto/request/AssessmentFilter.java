package com.bioimpedance.dto.request;

import com.bioimpedance.pagination.PageFilter;
import jakarta.validation.constraints.PastOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AssessmentFilter extends PageFilter {

    private String clientId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent(message = "from não pode ser data futura")
    private LocalDate from;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate to;

    private static final Set<String> ALLOWED_SORTS = Set.of(
        "createdAt", "date", "weight", "protocol"
    );

    @Override
    protected Set<String> allowedSortFields() {
        return ALLOWED_SORTS;
    }

    @Override
    protected String defaultSortField() {
        return "date";
    }
}