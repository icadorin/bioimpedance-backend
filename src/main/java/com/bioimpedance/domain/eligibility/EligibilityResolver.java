package com.bioimpedance.domain.eligibility;

import com.bioimpedance.domain.contracts.ApplicabilityResult;
import com.bioimpedance.domain.contracts.CandidateStatus;
import com.bioimpedance.domain.contracts.MatchClassification;
import com.bioimpedance.domain.contracts.MatchResult;
import com.bioimpedance.domain.contracts.ReadinessResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Fonte: architecture.md §10 + especificacao_cientifica.md §9 + doc.md §12.
 *
 * Resolve o estado operacional (READY / MISSING_INPUTS / INELIGIBLE /
 * DISABLED) de uma variante a partir do resultado de aplicabilidade,
 * da disponibilidade de inputs e do estado de habilitação.
 *
 * DEC-16: não acessa library nem persistence — recebe tudo por parâmetro,
 * já resolvido pela orchestration.
 *
 * DEC-17: precedência de reporte DISABLED → INELIGIBLE → MISSING_INPUTS → READY.
 * Não é máquina de estados linear (architecture.md §10.2).
 *
 * Regra de ouro (architecture.md §10, §25): warnings NÃO viram estado
 * principal; INELIGIBLE nunca vira elegível; MISSING_INPUTS nunca vira READY.
 */
@Service
public class EligibilityResolver {

    public ReadinessResult resolve(ApplicabilityResult applicability,
                                   Collection<String> requiredInputIds,
                                   Collection<String> availableInputIds,
                                   boolean enabled) {

        String variantId = applicability.variantId();

        // 1. DISABLED tem precedência — configuração do profissional.
        if (!enabled) {
            return new ReadinessResult(variantId, CandidateStatus.DISABLED,
                List.of(), List.of(),
                List.of("VARIANT_DISABLED"),
                List.of("Variante desabilitada pelo profissional."));
        }

        // 2. INELIGIBLE — incompatibilidades científicas explícitas.
        List<String> exclusionReasons = new ArrayList<>();
        if (applicability.hasBlockingScientificRestriction()) {
            exclusionReasons.add("SCIENTIFIC_RESTRICTION");
        }
        if (isSexIncompatible(applicability.sexMatch())) {
            exclusionReasons.add("SEX_NOT_SUPPORTED");
        }
        if (!exclusionReasons.isEmpty()) {
            return new ReadinessResult(variantId, CandidateStatus.INELIGIBLE,
                List.of(), List.of(),
                List.copyOf(exclusionReasons),
                buildIneligibleReasons(exclusionReasons));
        }

        // 3. MISSING_INPUTS — dados obrigatórios ausentes (DEC-15).
        // Ordenado explicitamente: requiredInputIds costuma chegar como Set.of(...),
        // cuja ordem de iteração não é garantida entre execuções da JVM.
        List<String> missing = requiredInputIds.stream()
            .filter(id -> !availableInputIds.contains(id))
            .sorted()
            .toList();
        if (!missing.isEmpty()) {
            return new ReadinessResult(variantId, CandidateStatus.MISSING_INPUTS,
                missing, List.of(), List.of(),
                List.of("Faltam " + missing.size() + " medida(s) obrigatória(s)."));
        }

        // 4. READY — warnings coexistem, nunca viram estado principal (§10.1).
        List<String> warnings = new ArrayList<>();
        collectWarnings(applicability, warnings);
        return new ReadinessResult(variantId, CandidateStatus.READY,
            List.of(), List.copyOf(warnings), List.of(),
            List.of("Todos os inputs obrigatórios estão disponíveis."));
    }

    /**
     * Em sexMatch, LOW significa sexo incompatível (Fase 5). Sexo
     * incompatível é restrição científica explícita → INELIGIBLE (§4.1).
     */
    private boolean isSexIncompatible(MatchResult sexMatch) {
        return isClassification(sexMatch, MatchClassification.LOW);
    }

    private void collectWarnings(ApplicabilityResult applicability, List<String> warnings) {
        // Idade fora da faixa sem restrição explícita é WARNING, não INELIGIBLE (doc.md §23).
        if (isClassification(applicability.ageMatch(), MatchClassification.OUTSIDE_VALIDATED_RANGE)) {
            warnings.add("AGE_OUTSIDE_VALIDATED_RANGE");
        }
        // NOVO: idade dentro da faixa de desenvolvimento mas sem faixa validada
        // com bounds explícitos (PARTIAL, calculado em ApplicabilityEngine)
        // estava sendo descartada silenciosamente antes desta correção.
        if (isClassification(applicability.ageMatch(), MatchClassification.PARTIAL)) {
            warnings.add("AGE_PARTIAL_MATCH");
        }
        if (isClassification(applicability.populationMatch(), MatchClassification.LOW)) {
            warnings.add("POPULATION_LOW_MATCH");
        }
        // Contexto (atleta/treino/modalidade) não documentado → evidência limitada (§6.5, §12.6).
        if (isClassification(applicability.contextMatch(), MatchClassification.NOT_DOCUMENTED)) {
            warnings.add("EVIDENCE_LIMITED");
        }
    }

    private boolean isClassification(MatchResult match, MatchClassification classification) {
        return match != null && match.classification() == classification;
    }

    private List<String> buildIneligibleReasons(List<String> exclusionReasons) {
        List<String> reasons = new ArrayList<>();
        for (String reason : exclusionReasons) {
            switch (reason) {
                case "SEX_NOT_SUPPORTED" -> reasons.add("Sexo incompatível com a variante.");
                case "SCIENTIFIC_RESTRICTION" -> reasons.add("Restrição científica explícita da variante.");
                default -> reasons.add("Variante inelegível: " + reason + ".");
            }
        }
        return reasons;
    }
}