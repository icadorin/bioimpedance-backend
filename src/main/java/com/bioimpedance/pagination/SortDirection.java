package com.bioimpedance.pagination;

public enum SortDirection {
    ASC, DESC;

    public static SortDirection fromString(String value) {
        if (value == null || value.isBlank()) return DESC;
        try {
            return SortDirection.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return DESC;
        }
    }
}