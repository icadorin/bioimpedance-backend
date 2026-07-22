package com.bioimpedance.config;

import org.jspecify.annotations.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class CaseInsensitiveEnumConverterFactory implements ConverterFactory<String, Enum<?>> {

    @Override
    public <T extends Enum<?>> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnumConverter<>(targetType);
    }

    private static class StringToEnumConverter<T extends Enum<?>> implements Converter<String, T> {
        private final Class<T> enumType;

        StringToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public @Nullable T convert(String source) {
            if (source.isBlank()) return null;
            for (T constant : enumType.getEnumConstants()) {
                if (constant.name().equalsIgnoreCase(source.trim())) {
                    return constant;
                }
            }
            throw new IllegalArgumentException(
                "Valor inválido '" + source + "' para " + enumType.getSimpleName()
            );
        }
    }
}