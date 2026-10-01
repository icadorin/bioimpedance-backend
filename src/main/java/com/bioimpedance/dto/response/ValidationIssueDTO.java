package com.bioimpedance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Issue de validação do autosave (DEC-56). */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidationIssueDTO {
    /** INVALID_VALUE | IMPOSSIBLE_VALUE | PRECISION_MISMATCH | ... */
    private String type;
    private String inputId;
    private String message;
}