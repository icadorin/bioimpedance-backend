package com.bioimpedance.service;

import com.bioimpedance.constants.Gender;
import com.bioimpedance.domain.calculation.PredictionResult;
import com.bioimpedance.domain.contracts.AssessmentContext;
import com.bioimpedance.domain.contracts.CandidateVariantSummary;
import com.bioimpedance.domain.contracts.ClientProfile;
import com.bioimpedance.domain.contracts.ConversionCandidateSummary;
import com.bioimpedance.domain.contracts.ConversionSuggestionResult;
import com.bioimpedance.domain.contracts.Sex;
import com.bioimpedance.domain.contracts.SuggestionResult;
import com.bioimpedance.dto.request.AssessmentFlowRequestDTO;
import com.bioimpedance.dto.response.AssessmentFlowResponseDTO;
import com.bioimpedance.dto.response.CalculationFlowResponseDTO;
import com.bioimpedance.dto.response.ConversionSuggestionDTO;
import com.bioimpedance.dto.response.PredictionDTO;
import com.bioimpedance.dto.response.VariantStatusDTO;
import com.bioimpedance.entity.Assessment;
import com.bioimpedance.entity.Client;
import com.bioimpedance.exception.ResourceNotFoundException;
import com.bioimpedance.library.scientificrules.ScientificRuleRegistry;
import com.bioimpedance.orchestration.assessmentflow.AssessmentFlowInput;
import com.bioimpedance.orchestration.assessmentflow.AssessmentFlowOrchestrator;
import com.bioimpedance.orchestration.assessmentflow.AssessmentFlowResult;
import com.bioimpedance.persistence.AuditSnapshotStore;
import com.bioimpedance.repository.AssessmentRepository;
import com.bioimpedance.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ponte entre a camada web e o AssessmentFlowOrchestrator (Fase 12 / Chunk 3).
 * <p>
 * Responsabilidades (architecture.md §23, doc.md §13):
 * carregar Client (com ownership) → resolver input resolution (AGE/HEIGHT,
 * DEC-31) → montar AssessmentContext → resolver configuração → persistir o
 * snapshot de coleta (Assessment + measurements) → executar o orchestrator →
 * anexar AuditSnapshot (append-only) → mapear para DTOs.
 * <p>
 * Não implementa regra científica nenhuma: só coordena (regra de ouro §0).
 * Auth/ownership: o controller resolve o userId e passa aqui — o serviço
 * não lê SecurityContext (testável sem contexto de segurança).
 */
@Service
public class AssessmentFlowService {

    /** inputIds canônicos resolvidos pelo backend (DEC-31) — não são medidas coletadas. */
    private static final String INPUT_AGE = "AGE";
    private static final String INPUT_HEIGHT = "HEIGHT";

    private final ClientRepository clientRepository;
    private final AssessmentRepository assessmentRepository;
    private final ProfessionalConfigurationResolver configurationResolver;
    private final ScientificRuleRegistry scientificRuleRegistry;
    private final AssessmentFlowOrchestrator orchestrator;
    private final AuditSnapshotStore auditSnapshotStore;

    public AssessmentFlowService(ClientRepository clientRepository,
                                 AssessmentRepository assessmentRepository,
                                 ProfessionalConfigurationResolver configurationResolver,
                                 ScientificRuleRegistry scientificRuleRegistry,
                                 AssessmentFlowOrchestrator orchestrator,
                                 AuditSnapshotStore auditSnapshotStore) {
        this.clientRepository = clientRepository;
        this.assessmentRepository = assessmentRepository;
        this.configurationResolver = configurationResolver;
        this.scientificRuleRegistry = scientificRuleRegistry;
        this.orchestrator = orchestrator;
        this.auditSnapshotStore = auditSnapshotStore;
    }

    /**
     * Fluxo de cálculo explícito (doc.md §25): o profissional já escolheu a
     * variante; aqui executamos o pipeline completo e auditamos.
     */
    @Transactional
    public CalculationFlowResponseDTO calculate(String userId, AssessmentFlowRequestDTO dto) {
        if (dto.getSelectedVariantId() == null || dto.getSelectedVariantId().isBlank()) {
            throw new IllegalArgumentException(
                "selectedVariantId é obrigatório para o cálculo (doc.md §25)");
        }

        Client client = clientRepository.findByIdAndUserId(dto.getClientId(), userId)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        int age = Period.between(client.getBirthDate(), dto.getDate()).getYears();

        // Input resolution (DEC-31): AGE derivada, HEIGHT do perfil travado (doc.md §4.1/§11).
        Map<String, Double> resolvedInputs = new HashMap<>(dto.getMeasurements());
        resolvedInputs.put(INPUT_AGE, (double) age);
        resolvedInputs.put(INPUT_HEIGHT, client.getHeight());

        // Assessment = snapshot da coleta (doc.md §1); resultado de cálculo NÃO vive aqui.
        Assessment assessment = persistAssessment(userId, client, dto);

        AssessmentFlowInput input = new AssessmentFlowInput(
            assessment.getId(),
            buildContext(client, age, dto),
            resolvedInputs,
            configurationResolver.resolve(userId),
            dto.getSelectedVariantId(),
            false, // override é derivado pelo orchestrator (DEC-40)
            dto.getSelectionReason(),
            dto.getSelectedConversionId(),
            false,
            dto.getConversionSelectionReason()
        );

        AssessmentFlowResult result = orchestrator.execute(input);

        // Auditoria append-only (architecture.md §6) — única escrita permitida.
        auditSnapshotStore.append(result.auditSnapshot());

        return toResponse(result, dto, requiredInputUnion(input.professionalConfiguration()));
    }

    // ==================== PRIVADOS ====================

    private Assessment persistAssessment(String userId, Client client, AssessmentFlowRequestDTO dto) {
        Assessment assessment = Assessment.builder()
            .userId(userId)
            .clientId(client.getId())
            .date(dto.getDate())
            .build();
        // Somente medidas brutas coletadas; AGE/HEIGHT não viram measurement (doc.md §4.1/§11).
        dto.getMeasurements().forEach((inputId, value) -> {
            if (value != null) {
                assessment.addMeasurement(inputId, value);
            }
        });
        return assessmentRepository.save(assessment);
    }

    private AssessmentContext buildContext(Client client, int age, AssessmentFlowRequestDTO dto) {
        ClientProfile profile = new ClientProfile(
            toSex(client.getGender()),
            age,
            dto.getAthlete(),
            dto.getTrainingLevel(),
            dto.getModality()
        );
        return new AssessmentContext(profile, dto.getObjective());
    }

    private Sex toSex(Gender gender) {
        return gender == Gender.FEMALE ? Sex.FEMALE : Sex.MALE;
    }

    /** doc.md §10: união dos requiredInputs das variantes habilitadas (tela de coleta). */
    private List<String> requiredInputUnion(com.bioimpedance.domain.config.ProfessionalConfiguration config) {
        return scientificRuleRegistry.all().stream()
            .filter(p -> config.enabledVariantIds().contains(p.identity().variantId()))
            .flatMap(p -> p.inputs().requiredInputs().stream())
            .distinct()
            .sorted()
            .toList();
    }

    // ==================== MAPPING (domain → DTO) ====================

    private CalculationFlowResponseDTO toResponse(AssessmentFlowResult result,
                                                  AssessmentFlowRequestDTO dto,
                                                  List<String> union) {
        String suggestedConversionId = firstSuggestedConversion(result.conversionSuggestionResult());
        boolean conversionOverride = result.selectedConversionId() != null
            && !result.selectedConversionId().equals(suggestedConversionId);

        return CalculationFlowResponseDTO.builder()
            .auditId(result.auditSnapshot().auditId())
            .flow(toFlowDTO(result.suggestionResult(), union))
            .selectedVariantId(result.selectedVariantId())
            .variantOverride(result.auditSnapshot().override())
            .variantOverrideReason(result.auditSnapshot().overrideReason())
            .prediction(toPrediction(result.variantPrediction()))
            .conversionSuggestion(toConversionSuggestion(result.conversionSuggestionResult()))
            .selectedConversionId(result.selectedConversionId())
            .conversionOverride(conversionOverride)
            .conversionOverrideReason(conversionOverride ? dto.getConversionSelectionReason() : null)
            .finalResult(toPrediction(result.finalResult()))
            .build();
    }

    private AssessmentFlowResponseDTO toFlowDTO(SuggestionResult suggestion, List<String> union) {
        return AssessmentFlowResponseDTO.builder()
            .suggestionStatus(suggestion.status().name())
            .suggestedVariantIds(suggestion.suggestedVariants().stream()
                .map(CandidateVariantSummary::variantId)
                .toList())
            .candidateVariants(suggestion.candidateVariants().stream()
                .map(this::toVariantStatus)
                .toList())
            .excludedVariants(suggestion.excludedVariants().stream()
                .map(this::toVariantStatus)
                .toList())
            .requiredInputUnion(union)
            .build();
    }

    private VariantStatusDTO toVariantStatus(CandidateVariantSummary c) {
        return VariantStatusDTO.builder()
            .variantId(c.variantId())
            .status(c.status().name())
            .warnings(c.warnings())
            .missingInputIds(c.missingInputs())     // ← corrigido
            .reasons(c.reasons())
            .build();
    }

    private PredictionDTO toPrediction(PredictionResult p) {
        if (p == null) {
            return null;
        }
        return PredictionDTO.builder()
            .sourceId(p.sourceId())
            .outputType(p.outputType())
            .value(p.value())
            .build();
    }

    private ConversionSuggestionDTO toConversionSuggestion(ConversionSuggestionResult r) {
        if (r == null) {
            return null;
        }
        return ConversionSuggestionDTO.builder()
            .status(r.status().name())
            .suggestedConversionIds(conversionIds(r.suggestedConversions()))
            .candidateConversionIds(conversionIds(r.candidateConversions()))
            .excludedConversionIds(conversionIds(r.excludedConversions()))
            .build();
    }

    private String firstSuggestedConversion(ConversionSuggestionResult r) {
        if (r == null || r.suggestedConversions().isEmpty()) {
            return null;
        }
        return r.suggestedConversions().get(0).conversionId();
    }

    private List<String> conversionIds(List<ConversionCandidateSummary> list) {
        return list.stream().map(ConversionCandidateSummary::conversionId).toList();
    }
}