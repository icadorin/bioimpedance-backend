package com.bioimpedance.backend.domain.contracts;

/**
 * Tipo de problema encontrado durante a validação dos dados de um
 * Assessment.
 * <p>
 * Fonte: {@code architecture.md} §2, §2.1.
 */
public enum ValidationIssueType {
    INVALID_VALUE,
    IMPOSSIBLE_VALUE,
    UNIT_MISMATCH,
    PRECISION_MISMATCH,
    DUPLICATE,
    CONFLICT,
    INCONSISTENCY,
    MISSING_REQUIRED_INPUT
}
