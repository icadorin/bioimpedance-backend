package com.bioimpedance.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AssessmentMethod {
    NAVY("navy"),
    BIOIMPEDANCE("bioimpedance"),
    SKINFOLD("skinfold"),
    IMC("imc");

    private final String value;

    AssessmentMethod(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static AssessmentMethod fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (AssessmentMethod method : values()) {
            if (method.name().equalsIgnoreCase(value) ||
                method.value.equalsIgnoreCase(value)) {
                return method;
            }
        }

        throw new IllegalArgumentException("Metodo de avaliacao invalido: " + value);
    }
}