package com.bioimpedance.service;

import com.bioimpedance.constants.AssessmentStatus;
import com.bioimpedance.constants.Gender;
import com.bioimpedance.domain.calculation.PredictionResult;
import com.bioimpedance.domain.config.ProfessionalConfiguration;
import com.bioimpedance.domain.contracts.*;
import com.bioimpedance.dto.request.AssessmentFlowRequestDTO;
import com.bioimpedance.dto.request.CalculateRequestDTO;
import com.bioimpedance.dto.response.AssessmentFlowResponseDTO;
import com.bioimpedance.dto.response.CalculationFlowResponseDTO;
import com.bioimpedance.dto.response.ConversionSuggestionDTO;
import com.bioimpedance.dto.response.PredictionDTO;
import com.bioimpedance.dto.response.VariantStatusDTO;
import com.bioimpedance.entity.Assessment;
import com.bioimpedance.entity.Client;
import com.bioimpedance.exception.AssessmentLockedException;
import com.bioimpedance.exception.ResourceNotFoundException;
import com.bioimpedance.library.scientificrules.ScientificRuleRegistry;
import com.bioimpedance.orchestration.assessmentflow.AssessmentFlowInput;
import com.bioimpedance.orchestration.assessmentflow.AssessmentFlowOrchestrator;
import com.bioimpedance.orchestration.assessmentflow.AssessmentFlowResult;
import com.bioimpedance.persistence.AuditSnapshotStore;
import com.bioimpedance.repository.AssessmentRepository;
import com.bioimpedance.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fonte única da orquestração científica (Fase 12/13).
 * <p>
 * DEC-58 / Chunk B4: {@code calculate} é exploratório — carrega a Assessment
 * do banco, roda o pipeline em memória e devolve o resultado. NÃO persiste
 * resultado derivado nem auditoria (auditId = null). A gravação fica no B5.
 */
@Service
@RequiredArgsConstructor
public class AssessmentFlowService {

    public static final String INPUT_AGE = "AGE";
    public static final String INPUT_HEIGHT = "HEIGHT";

    private final ClientRepository clientRepository;
    private final AssessmentRepository assessmentRepository;
    private final CurrentUserService currentUserService;
    private final ProfessionalConfigurationResolver configurationResolver;
    private final ScientificRuleRegistry scientificRuleRegistry;
    private final AssessmentFlowOrchestrator orchestrator;
    private final AuditSnapshotStore auditSnapshotStore;
    private final MetabolicService metabolicService;

    // ==================== B4: CÁLCULO EXPLORATÓRIO ====================

    /**
     * POST /{id}/calculate — carrega a Assessment DRAFT do banco, usa as
     * medidas persistidas, roda o pipeline em memória. Sem efeito colateral.
     */
    @Transactional(readOnly = true)
    public CalculationFlowResponseDTO calculate(String assessmentId, CalculateRequestDTO dto) {
        String userId = currentUserService.getCurrentUserId();

        Assessment assessment = assessmentRepository.findByIdAndUserId(assessmentId, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada."));
        if (assessment.getStatus() != AssessmentStatus.DRAFT) {
            throw new AssessmentLockedException(assessmentId);
        }

        Client client = clientRepository.findByIdAndUserId(assessment.getClientId(), userId)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        AssessmentFlowInput input = buildInputFromAssessment(assessment, client, userId, dto);
        return executeInMemory(input, dto);
    }

    /**
     * Chamado pelo AssessmentDraftService.calculate(assessmentId) — monta o
     * input a partir da Assessment e executa sem DTO de seleção (preview puro).
     */
    @Transactional(readOnly = true)
    public CalculationFlowResponseDTO calculateFromDraft(String assessmentId) {
        String userId = currentUserService.getCurrentUserId();

        Assessment assessment = assessmentRepository.findByIdAndUserId(assessmentId, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada."));
        if (assessment.getStatus() != AssessmentStatus.DRAFT) {
            throw new AssessmentLockedException(assessmentId);
        }

        Client client = clientRepository.findByIdAndUserId(assessment.getClientId(), userId)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        AssessmentFlowInput input = buildInputFromAssessment(assessment, client, userId, null);
        return executeInMemory(input, null);
    }

    // ==================== LEGADO (Fase 12) ====================

    /**
     * Fluxo legado: recebe DTO com medidas inline, executa e persiste auditoria.
     * Usado pelo AssessmentService.create. Será removido no F5.
     */
    public CalculationFlowResponseDTO executeLegacy(AssessmentFlowRequestDTO dto) {
        String userId = currentUserService.getCurrentUserId();
        Client client = clientRepository.findByIdAndUserId(dto.getClientId(), userId)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        int age = Period.between(client.getBirthDate(), dto.getDate()).getYears();
        Map<String, Double> resolvedInputs = new HashMap<>(dto.getMeasurements());
        resolvedInputs.put(INPUT_AGE, (double) age);
        resolvedInputs.put(INPUT_HEIGHT, client.getHeight());

        AssessmentFlowInput input = new AssessmentFlowInput(
            null,
            buildContext(client, age, dto),
            resolvedInputs,
            configurationResolver.resolve(userId),
            dto.getSelectedVariantId(),
            false,
            dto.getSelectionReason(),
            dto.getSelectedConversionId(),
            false,
            dto.getConversionSelectionReason()
        );

        AssessmentFlowResult result = orchestrator.execute(input);
        if (result.auditSnapshot() != null) {
            auditSnapshotStore.append(result.auditSnapshot());
        }

        List<String> union = requiredInputUnion(input.professionalConfiguration());
        CalculationFlowResponseDTO response = toCalculationDTO(
            result, dto.getConversionSelectionReason(), union, null);
        if (result.auditSnapshot() != null) {
            response.setAuditId(result.auditSnapshot().auditId());
        }
        return response;
    }

    // ==================== MAPEAMENTO PÚBLICO (reuso) ====================

    /**
     * DEC-55: painel de estado. Público para reuso no AssessmentDraftService.
     */
    public AssessmentFlowResponseDTO toFlowDTO(SuggestionResult suggestion) {
        List<String> union = suggestion.candidateVariants().stream()
            .map(CandidateVariantSummary::variantId)
            .map(scientificRuleRegistry::resolve)
            .flatMap(p -> p.inputs().requiredInputs().stream())
            .filter(id -> !INPUT_AGE.equals(id) && !INPUT_HEIGHT.equals(id))
            .distinct()
            .sorted()
            .toList();

        return AssessmentFlowResponseDTO.builder()
            .suggestionStatus(suggestion.status().name())
            .suggestedVariantIds(suggestion.suggestedVariants().stream()
                .map(CandidateVariantSummary::variantId).toList())
            .candidateVariants(suggestion.candidateVariants().stream()
                .map(AssessmentFlowService::toVariantStatus).toList())
            .excludedVariants(suggestion.excludedVariants().stream()
                .map(AssessmentFlowService::toVariantStatus).toList())
            .requiredInputUnion(union)
            .build();
    }

    // ==================== HELPERS PRIVADOS ====================

    private AssessmentFlowInput buildInputFromAssessment(
        Assessment assessment, Client client,
        String userId, CalculateRequestDTO dto
    ) {
        Map<String, Double> resolvedInputs = new HashMap<>(assessment.toInputMap());
        int age = Period.between(client.getBirthDate(), assessment.getDate()).getYears();
        resolvedInputs.put(INPUT_AGE, (double) age);
        resolvedInputs.put(INPUT_HEIGHT, client.getHeight());

        String selectedVariantId = dto != null ? dto.getSelectedVariantId() : null;
        String selectionReason = dto != null ? dto.getSelectionReason() : null;
        String selectedConversionId = dto != null ? dto.getSelectedConversionId() : null;
        String conversionReason = dto != null ? dto.getConversionSelectionReason() : null;

        return new AssessmentFlowInput(
            assessment.getId(),
            buildContextFromAssessment(client, age, assessment),
            resolvedInputs,
            configurationResolver.resolve(userId),
            selectedVariantId,
            false,
            selectionReason,
            selectedConversionId,
            false,
            conversionReason
        );
    }

    private CalculationFlowResponseDTO executeInMemory(AssessmentFlowInput input,
                                                       CalculateRequestDTO dto) {
        AssessmentFlowResult result = orchestrator.execute(input);
        List<String> union = requiredInputUnion(input.professionalConfiguration());
        return toCalculationDTO(result,
            dto != null ? dto.getConversionSelectionReason() : null, union, null);
    }

    private AssessmentContext buildContext(Client client, int age, AssessmentFlowRequestDTO dto) {
        ClientProfile profile = new ClientProfile(
            toSex(client.getGender()), age,
            dto.getAthlete(), dto.getTrainingLevel(), dto.getModality());
        return new AssessmentContext(profile, dto.getObjective());
    }

    private AssessmentContext buildContextFromAssessment(Client client, int age, Assessment a) {
        ClientProfile profile = new ClientProfile(
            toSex(client.getGender()), age,
            a.getAthlete(), a.getTrainingLevel(), a.getModality());
        return new AssessmentContext(profile, a.getObjective());
    }

    private Sex toSex(Gender gender) {
        return gender == Gender.FEMALE ? Sex.FEMALE : Sex.MALE;
    }

    private List<String> requiredInputUnion(ProfessionalConfiguration config) {
        return scientificRuleRegistry.all().stream()
            .filter(p -> config.enabledVariantIds().contains(p.identity().variantId()))
            .flatMap(p -> p.inputs().requiredInputs().stream())
            .filter(id -> !INPUT_AGE.equals(id) && !INPUT_HEIGHT.equals(id))
            .distinct()
            .sorted()
            .toList();
    }

    private CalculationFlowResponseDTO toCalculationDTO(
        AssessmentFlowResult result,
        String conversionSelectionReason,
        List<String> union,
        String auditId
    ) {
        String suggestedConversionId = firstSuggestedConversion(result.conversionSuggestionResult());
        boolean conversionOverride = result.selectedConversionId() != null
            && !result.selectedConversionId().equals(suggestedConversionId);

        return CalculationFlowResponseDTO.builder()
            .assessmentId(result.assessmentId())
            .auditId(auditId)
            .flow(toFlowDTO(result.suggestionResult()))
            .selectedVariantId(result.selectedVariantId())
            .variantOverride(result.auditSnapshot() != null && result.auditSnapshot().override())
            .variantOverrideReason(result.auditSnapshot() != null ? result.auditSnapshot().overrideReason() : null)
            .prediction(toPrediction(result.variantPrediction()))
            .conversionSuggestion(toConversionSuggestion(result.conversionSuggestionResult()))
            .selectedConversionId(result.selectedConversionId())
            .conversionOverride(conversionOverride)
            .conversionOverrideReason(conversionOverride ? conversionSelectionReason : null)
            .finalResult(toPrediction(result.finalResult()))
            .build();
    }

    private static VariantStatusDTO toVariantStatus(CandidateVariantSummary c) {
        return VariantStatusDTO.builder()
            .variantId(c.variantId())
            .status(c.status().name())
            .warnings(c.warnings())
            .missingInputIds(c.missingInputs())
            .reasons(c.reasons())
            .build();
    }

    private static PredictionDTO toPrediction(PredictionResult p) {
        if (p == null) return null;
        return PredictionDTO.builder()
            .sourceId(p.sourceId())
            .outputType(p.outputType())
            .value(p.value())
            .build();
    }

    private static ConversionSuggestionDTO toConversionSuggestion(ConversionSuggestionResult r) {
        if (r == null) return null;
        return ConversionSuggestionDTO.builder()
            .status(r.status().name())
            .suggestedConversionIds(r.suggestedConversions().stream()
                .map(ConversionCandidateSummary::conversionId).toList())
            .candidateConversionIds(r.candidateConversions().stream()
                .map(ConversionCandidateSummary::conversionId).toList())
            .excludedConversionIds(r.excludedConversions().stream()
                .map(ConversionCandidateSummary::conversionId).toList())
            .build();
    }

    private static String firstSuggestedConversion(ConversionSuggestionResult r) {
        if (r == null || r.suggestedConversions().isEmpty()) return null;
        return r.suggestedConversions().getFirst().conversionId();
    }
}