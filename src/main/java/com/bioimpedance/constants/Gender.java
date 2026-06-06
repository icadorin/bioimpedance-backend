package com.bioimpedance.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {

    MALE("male"),
    FEMALE("female");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Gender fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (Gender gender : values()) {
            if (gender.name().equalsIgnoreCase(value) ||
                gender.value.equalsIgnoreCase(value)) {
                return gender;
            }
        }

        throw new IllegalArgumentException("Gender inválido: " + value);
    }
}