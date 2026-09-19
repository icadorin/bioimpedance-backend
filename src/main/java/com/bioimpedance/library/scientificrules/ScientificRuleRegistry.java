package com.bioimpedance.library.scientificrules;

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
import java.util.Optional;

/**
 * Fonte: architecture.md §4.2 / equation_family_especificacao_formal.md §7.
 * <p>
 * Carrega todas as fichas científicas no boot (fail-fast) e resolve
 * variantId → EquationVariantScientificProfile. Não interpreta o cliente:
 * apenas fornece a definição científica.
 */
@Component
public class ScientificRuleRegistry {

    private static final String LOCATION = "classpath:library/scientific-rules/*.yaml";

    private final Map<String, EquationVariantScientificProfile> profiles;

    public ScientificRuleRegistry() {
        this(new PathMatchingResourcePatternResolver());
    }

    public ScientificRuleRegistry(ResourcePatternResolver resolver) {
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
            throw new IllegalStateException("Nenhuma ficha científica encontrada em " + LOCATION);
        }

        Map<String, EquationVariantScientificProfile> loaded = new LinkedHashMap<>();
        for (Resource resource : resources) {
            String fileName = resource.getFilename();
            try (InputStream in = resource.getInputStream()) {
                EquationVariantScientificProfile profile =
                    mapper.readValue(in, EquationVariantScientificProfile.class);

                String variantId = profile.identity().variantId();
                String expectedId = fileName == null ? null : fileName.replaceFirst("\\.yaml$", "");
                if (!variantId.equals(expectedId)) {
                    throw new IllegalStateException(
                        "variantId '" + variantId + "' não corresponde ao nome do arquivo '" + fileName + "'");
                }
                if (loaded.putIfAbsent(variantId, profile) != null) {
                    throw new IllegalStateException("variantId duplicado na biblioteca: " + variantId);
                }
            } catch (IOException e) {
                throw new IllegalStateException("Falha ao ler ficha científica: " + fileName, e);
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException("Ficha científica inválida: " + fileName, e);
            }
        }
        this.profiles = Collections.unmodifiableMap(loaded);
    }

    public EquationVariantScientificProfile resolve(String variantId) {
        EquationVariantScientificProfile profile = profiles.get(variantId);
        if (profile == null) {
            throw new IllegalArgumentException("Variante científica não encontrada: " + variantId);
        }
        return profile;
    }

    public Optional<EquationVariantScientificProfile> find(String variantId) {
        return Optional.ofNullable(profiles.get(variantId));
    }

    public Collection<EquationVariantScientificProfile> all() {
        return profiles.values();
    }

    public int size() {
        return profiles.size();
    }
}