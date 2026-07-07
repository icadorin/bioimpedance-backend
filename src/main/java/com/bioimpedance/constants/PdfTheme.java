package com.bioimpedance.constants;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PdfTheme {
    LIGHT("light"),
    DARK("dark"),
    PURPLE("purple");

    @JsonValue
    private final String value;
}