package com.bioimpedance.library.conversions;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Fonte: architecture.md §4.3.
 * Resolve conversionId → ConversionDefinition a partir de
 * classpath:library/conversions/*.yaml. Fail-fast no boot.
 * Não contém regra de recomendação (isso é domain/conversion-suggestion).
 */
@Component
public class ConversionDefinitionRegistry {

    private static final String LOCATION = "classpath:library/conversions/*.yaml";

    private final Map<String, ConversionDefinition> conversions;

    public ConversionDefinitionRegistry() {
        this(new PathMatchingResourcePatternResolver());
    }

    public ConversionDefinitionRegistry(ResourcePatternResolver resolver) {
        YAMLMapper mapper = new YAMLMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        Resource[] resources;
        try {
            resources = resolver.getResources(LOCATION);
        } catch (IOException e) {
            throw new IllegalStateException("Falha ao varrer " + LOCATION, e);
        }
        if (resources.length == 0) {
            throw new IllegalStateException("Nenhuma conversão encontrada em " + LOCATION);
        }

        Map<String, ConversionDefinition> loaded = new LinkedHashMap<>();
        for (Resource resource : resources) {
            String fileName = resource.getFilename();
            try (InputStream in = resource.getInputStream()) {
                ConversionDefinition def = mapper.readValue(in, ConversionDefinition.class);
                String expectedId = fileName == null ? null : fileName.replaceFirst("\\.yaml$", "");
                if (!def.id().equals(expectedId)) {
                    throw new IllegalStateException(
                        "conversionId '" + def.id() + "' não bate com o nome do arquivo '" + fileName + "'");
                }
                if (loaded.putIfAbsent(def.id(), def) != null) {
                    throw new IllegalStateException("conversionId duplicado: " + def.id());
                }
            } catch (IOException e) {
                throw new IllegalStateException("Falha ao ler conversão: " + fileName, e);
            }
        }
        this.conversions = Collections.unmodifiableMap(loaded);
    }

    public ConversionDefinition resolve(String conversionId) {
        ConversionDefinition def = conversions.get(conversionId);
        if (def == null) {
            throw new IllegalArgumentException("Conversão não encontrada: " + conversionId);
        }
        return def;
    }

    public Map<String, ConversionDefinition> getAll() {
        return conversions;
    }

    public boolean contains(String conversionId) {
        return conversions.containsKey(conversionId);
    }

    public int size() {
        return conversions.size();
    }
}