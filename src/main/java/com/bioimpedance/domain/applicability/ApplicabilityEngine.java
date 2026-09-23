package com.bioimpedance.domain.applicability;

import com.bioimpedance.domain.contracts.AssessmentContext;
import com.bioimpedance.domain.contracts.ClientProfile;
import com.bioimpedance.domain.contracts.MatchClassification;
import com.bioimpedance.domain.contracts.MatchDimension;
import com.bioimpedance.domain.contracts.MatchResult;
import com.bioimpedance.domain.contracts.Sex;
import com.bioimpedance.library.scientificrules.AthleteApplicability;
import com.bioimpedance.library.scientificrules.AgeApplicability;
import com.bioimpedance.library.scientificrules.AgeValidationEntry;
import com.bioimpedance.library.scientificrules.EquationVariantScientificProfile;
import com.bioimpedance.library.scientificrules.PopulationApplicability;
import com.bioimpedance.library.scientificrules.PopulationProfile;
import com.bioimpedance.library.scientificrules.Range;
import com.bioimpedance.library.scientificrules.SexApplicability;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Fonte: architecture.md §9 + especificacao_cientifica.md §4.
 *
 * Regra de ouro (§9.1): ausência de documentação NUNCA vira INELIGIBLE.
 * A decisão READY/INELIGIBLE pertence a domain.eligibility (Fase 6).
 */
@Service
public class ApplicabilityEngine {

    private final EvidenceSummaryBuilder evidenceSummaryBuilder;

    public ApplicabilityEngine(EvidenceSummaryBuilder evidenceSummaryBuilder) {
        this.evidenceSummaryBuilder = evidenceSummaryBuilder;
    }

    public ApplicabilityResult evaluate(EquationVariantScientificProfile profile,
                                        AssessmentContext context) {
        ClientProfile client = context.client();
        List<String> warnings = new ArrayList<>();

        MatchResult sex = evaluateSex(profile.applicability().sex(), client.sex());
        MatchResult age = evaluateAge(profile.applicability().age(), client.age(), warnings);
        MatchResult population = evaluatePopulation(profile.applicability().population(), client);
        MatchResult ctx = evaluateContext(profile);

        boolean hasBlocking = hasBlockingRestriction(profile);

        return new ApplicabilityResult(
            profile.identity().variantId(),
            sex, age, population, ctx,
            evidenceSummaryBuilder.build(profile),
            warnings,
            hasBlocking
        );
    }

    // ============ SEX (§4.1) ============
    // Classificações válidas: EXACT · LOW · UNKNOWN

    private MatchResult evaluateSex(SexApplicability rule, Sex clientSex) {
        if (clientSex == null || rule == null || !hasElements(rule.supportedSexes())) {
            return new MatchResult(MatchDimension.SEX, MatchClassification.UNKNOWN,
                "Sexo do cliente ou da variante não documentado.");
        }
        // MIG-2: dois enums Sex (library vs contracts). Comparamos por nome.
        if (sexMatches(rule.supportedSexes(), clientSex)) {
            return new MatchResult(MatchDimension.SEX, MatchClassification.EXACT,
                "Sexo compatível com a população documentada.");
        }
        return new MatchResult(MatchDimension.SEX, MatchClassification.LOW,
            "Sexo incompatível com a população documentada.");
    }

    // ============ AGE (§4.2, DEC-16) ============
    // Classificações válidas: EXACT · PARTIAL · OUTSIDE_VALIDATED_RANGE · UNKNOWN

    private MatchResult evaluateAge(AgeApplicability rule, int age, List<String> warnings) {
        if (age <= 0) {
            return new MatchResult(MatchDimension.AGE, MatchClassification.UNKNOWN,
                "Idade do cliente ausente ou inválida.");
        }
        if (rule == null) {
            return new MatchResult(MatchDimension.AGE, MatchClassification.UNKNOWN,
                "Variante sem informação de faixa etária.");
        }

        if (rule.validatedAgeRanges() != null) {
            for (AgeValidationEntry entry : rule.validatedAgeRanges()) {
                Range<Integer> r = entry.range();
                if (contains(r, age)) {
                    return new MatchResult(MatchDimension.AGE, MatchClassification.EXACT,
                        "Idade dentro de faixa validada (" + r.min() + "–" + r.max() + ").");
                }
            }
        }

        Range<Integer> dev = rule.originalDevelopmentAgeRange();
        if (contains(dev, age)) {
            warnings.add("Idade dentro da faixa de desenvolvimento, "
                + "mas fora de faixa validada com bounds explícitos.");
            return new MatchResult(MatchDimension.AGE, MatchClassification.PARTIAL,
                "Idade dentro da faixa de desenvolvimento (" + dev.min() + "–" + dev.max()
                    + "), sem faixa validada com bounds que a cubra.");
        }

        String devDesc = hasBounds(dev) ? (" (" + dev.min() + "–" + dev.max() + ")") : "";
        return new MatchResult(MatchDimension.AGE, MatchClassification.OUTSIDE_VALIDATED_RANGE,
            "Idade fora das faixas documentadas" + devDesc + ".");
    }

    // ============ POPULATION (§4.3, DEC-17) ============
    // Classificações válidas: HIGH · MODERATE · LOW · UNKNOWN

    private MatchResult evaluatePopulation(PopulationApplicability rule, ClientProfile client) {
        if (rule == null) {
            return new MatchResult(MatchDimension.POPULATION, MatchClassification.UNKNOWN,
                "Variante sem informação de população.");
        }
        PopulationProfile best = pickBestPopulation(rule);
        if (best == null) {
            return new MatchResult(MatchDimension.POPULATION, MatchClassification.UNKNOWN,
                "Nenhuma população com coverage documentado.");
        }

        boolean sexOk = sexMatches(best.sexCoverage(), client.sex());
        boolean ageOk = client.age() > 0 && contains(best.ageCoverage(), client.age());

        boolean sexCoverageExists = hasElements(best.sexCoverage());
        boolean ageCoverageExists = hasBounds(best.ageCoverage());

        if (sexOk && ageOk) {
            return new MatchResult(MatchDimension.POPULATION, MatchClassification.HIGH,
                "Forte correspondência com a população documentada.");
        }
        if (sexOk || ageOk) {
            return new MatchResult(MatchDimension.POPULATION, MatchClassification.MODERATE,
                "Correspondência parcial com a população documentada.");
        }
        if (!sexCoverageExists && !ageCoverageExists) {
            return new MatchResult(MatchDimension.POPULATION, MatchClassification.UNKNOWN,
                "População sem coverage estruturado de sexo/idade.");
        }
        return new MatchResult(MatchDimension.POPULATION, MatchClassification.LOW,
            "Diferenças significativas entre o cliente e a população documentada.");
    }

    private PopulationProfile pickBestPopulation(PopulationApplicability rule) {
        if (rule.originalPopulation() != null) return rule.originalPopulation();
        if (rule.validationPopulations() != null && !rule.validationPopulations().isEmpty()) {
            return rule.validationPopulations().getFirst();
        }
        return null;
    }

    // ============ CONTEXT (§4.4–4.8, V1) ============
    // Classificações válidas na V1: NOT_DOCUMENTED · UNKNOWN

    private MatchResult evaluateContext(EquationVariantScientificProfile profile) {
        var a = profile.applicability();
        boolean anyDocumented = hasAnyAthleteFlag(a.athlete())
            || isDocumented(a.trainingLevel() != null ? a.trainingLevel().supportedLevels() : null)
            || isDocumented(a.modality() != null ? a.modality().supportedModalities() : null)
            || isDocumented(a.bodyCharacteristics() != null ? a.bodyCharacteristics().rules() : null);

        if (anyDocumented) {
            return new MatchResult(MatchDimension.CONTEXT, MatchClassification.UNKNOWN,
                "Contexto documentado parcialmente; classificação refinada "
                    + "requer regras específicas da variante.");
        }
        return new MatchResult(MatchDimension.CONTEXT, MatchClassification.NOT_DOCUMENTED,
            "Variante sem documentação de atleta/treinamento/modalidade/"
                + "características corporais.");
    }

    private boolean hasAnyAthleteFlag(AthleteApplicability a) {
        if (a == null) return false;
        return a.developedInAthletes() != null
            || a.validatedInAthletes() != null
            || a.developedInNonAthletes() != null
            || a.validatedInNonAthletes() != null
            || a.explicitAthleteRestriction() != null;
    }

    // ============ Restrições científicas ============

    private boolean hasBlockingRestriction(EquationVariantScientificProfile profile) {
        if (profile.restrictions() == null) return false;
        return profile.restrictions().stream()
            .anyMatch(r -> r.severity() != null
                && "INELIGIBLE".equals(r.severity().name()));
    }

    // ============ Helpers compartilhados ============
    // Centralizam bound-check e comparação por nome — evita 3 cópias
    // ligeiramente diferentes da mesma regra (SEX e AGE).

    private static boolean hasBounds(Range<Integer> r) {
        return r != null && r.min() != null && r.max() != null;
    }

    private static boolean contains(Range<Integer> r, int value) {
        return hasBounds(r) && value >= r.min() && value <= r.max();
    }

    private static boolean hasElements(Collection<?> c) {
        return c != null && !c.isEmpty();
    }

    private static <E extends Enum<E>> boolean sexMatches(Collection<E> candidates, Sex clientSex) {
        if (!hasElements(candidates) || clientSex == null) return false;
        String name = clientSex.name();
        return candidates.stream().anyMatch(c -> c.name().equals(name));
    }

    private static boolean isDocumented(Collection<?> c) {
        return hasElements(c);
    }
}