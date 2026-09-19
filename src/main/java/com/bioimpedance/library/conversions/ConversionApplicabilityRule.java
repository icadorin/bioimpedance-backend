package com.bioimpedance.library.conversions;

/** Fonte: doc.md §2 (ConversionApplicabilityRule). */
public record ConversionApplicabilityRule(
    String conversionId,
    String ruleType,
    String condition,
    String action,
    String severity,
    String explanation
) {
}