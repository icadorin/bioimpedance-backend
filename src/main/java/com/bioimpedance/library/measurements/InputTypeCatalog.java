package com.bioimpedance.library.measurements;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Fonte: schema_cientifico.md §7.2 / architecture.md §4.
 * <p>
 * Catálogo global de tipos de medida. Não decide qual fórmula usar
 * nem quem é compatível — só resolve inputId → definição.
 */
@Component
public class InputTypeCatalog {

    private static final String LOCATION = "classpath:library/measurements/input-types.yaml";

    private final Map<String, InputTypeDefinition> definitions;

    public InputTypeCatalog() {
        this(new PathMatchingResourcePatternResolver());
    }

    public InputTypeCatalog(ResourcePatternResolver resolver) {
        YAMLMapper mapper = new YAMLMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        Resource[] resources;
        try {
            resources = resolver.getResources(LOCATION);
        } catch (IOException e) {
            throw new IllegalStateException("Falha ao varrer " + LOCATION, e);
        }
        if (resources.length != 1) {
            throw new IllegalStateException(
                "Esperado exatamente 1 arquivo de catálogo em " + LOCATION + ", encontrados: " + resources.length);
        }

        Map<String, InputTypeDefinition> loaded = new LinkedHashMap<>();
        try (InputStream in = resources[0].getInputStream()) {
            InputTypeCatalogDocument doc = mapper.readValue(in, InputTypeCatalogDocument.class);
            for (InputTypeDefinition def : doc.inputTypes()) {
                if (loaded.putIfAbsent(def.inputId(), def) != null) {
                    throw new IllegalStateException("inputId duplicado no catálogo: " + def.inputId());
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Falha ao ler catálogo de inputs: " + LOCATION, e);
        }
        this.definitions = Collections.unmodifiableMap(loaded);
    }

    public InputTypeDefinition resolve(String inputId) {
        InputTypeDefinition def = definitions.get(inputId);
        if (def == null) {
            throw new IllegalArgumentException("inputId não catalogado: " + inputId);
        }
        return def;
    }

    public boolean isKnown(String inputId) {
        return definitions.containsKey(inputId);
    }

    public Collection<InputTypeDefinition> all() {
        return definitions.values();
    }

    public int size() {
        return definitions.size();
    }
}