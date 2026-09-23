package com.bioimpedance.domain.applicability;

import com.bioimpedance.domain.contracts.DocumentationStatus;
import com.bioimpedance.domain.contracts.EvidenceSummary;
import com.bioimpedance.library.scientificrules.EquationVariantScientificProfile;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static com.bioimpedance.domain.contracts.DocumentationStatus.DOCUMENTED;
import static com.bioimpedance.domain.contracts.DocumentationStatus.NOT_DOCUMENTED;

/**
 * Fonte: especificacao_cientifica.md §6.5 (Evidence Coverage).
 * Deriva a cobertura de evidência direto do perfil científico.
 * Ausência de cobertura NUNCA implica inelegibilidade (§6.5) —
 * a decisão READY/INELIGIBLE pertence a domain.eligibility.
 */
@Component
public class EvidenceSummaryBuilder {

    public EvidenceSummary build(EquationVariantScientificProfile profile) {
        var a = profile.applicability();
        var athlete = a.athlete();

        boolean athleteDocumented = athlete.developedInAthletes() != null
            || athlete.validatedInAthletes() != null
            || athlete.developedInNonAthletes() != null
            || athlete.validatedInNonAthletes() != null
            || athlete.explicitAthleteRestriction() != null;

        // Nomeado 1:1 com a ordem dos componentes de EvidenceSummary —
        // evita troca silenciosa de posição entre campos do mesmo tipo.
        DocumentationStatus sex = status(a.sex().supportedSexes());
        DocumentationStatus age = status(a.age().originalDevelopmentAgeRange() != null);
        DocumentationStatus population = status(a.population().originalPopulation() != null);
        DocumentationStatus athleteStatus = status(athleteDocumented);
        DocumentationStatus trainingLevel = status(a.trainingLevel().supportedLevels());
        DocumentationStatus modality = status(a.modality().supportedModalities());
        DocumentationStatus bodyCharacteristics = status(a.bodyCharacteristics().rules());

        return new EvidenceSummary(sex, age, population, athleteStatus, trainingLevel, modality, bodyCharacteristics);
    }

    private static DocumentationStatus status(boolean documented) {
        return documented ? DOCUMENTED : NOT_DOCUMENTED;
    }

    private static DocumentationStatus status(Collection<?> collection) {
        return status(collection != null && !collection.isEmpty());
    }
}