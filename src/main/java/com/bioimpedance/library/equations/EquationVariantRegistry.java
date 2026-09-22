package com.bioimpedance.library.equations;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
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

@Component
public class EquationVariantRegistry {

    private static final String LOCATION = "classpath:library/equations/*.yaml";
    private final Map<String, FormulaDefinition> formulas;

    public EquationVariantRegistry() {
        this(new PathMatchingResourcePatternResolver());
    }

    public EquationVariantRegistry(ResourcePatternResolver resolver) {
        YAMLMapper mapper = new YAMLMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        Map<String, FormulaDefinition> loaded = new LinkedHashMap<>();
        try {
            Resource[] resources = resolver.getResources(LOCATION);
            for (Resource resource : resources) {
                String fileName = resource.getFilename();
                try (InputStream in = resource.getInputStream()) {
                    FormulaDefinition def = mapper.readValue(in, FormulaDefinition.class);
                    String expectedId = fileName == null ? null : fileName.replaceFirst("\\.yaml$", "");
                    if (!def.variantId().equals(expectedId)) {
                        throw new IllegalStateException("variantId '" + def.variantId() + "' != arquivo '" + fileName + "'");
                    }
                    loaded.put(def.variantId(), def);
                } catch (IOException e) {
                    throw new IllegalStateException("Falha ao ler fórmula: " + fileName, e);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Falha ao varrer " + LOCATION, e);
        }
        this.formulas = Collections.unmodifiableMap(loaded);
    }

    public FormulaDefinition resolve(String variantId) {
        FormulaDefinition def = formulas.get(variantId);
        if (def == null) throw new IllegalArgumentException("Fórmula não encontrada: " + variantId);
        return def;
    }

    public Collection<FormulaDefinition> all() {
        return formulas.values();
    }

    public int size() { return formulas.size(); }
}