package com.bioimpedance.service;

import com.bioimpedance.constants.AssessmentMeasurementSource;
import com.bioimpedance.constants.AssessmentStatus;
import com.bioimpedance.constants.Gender;
import com.bioimpedance.domain.contracts.*;
import com.bioimpedance.domain.validation.MeasurementValidator;
import com.bioimpedance.dto.request.CreateDraftRequestDTO;
import com.bioimpedance.dto.request.UpdateContextRequestDTO;
import com.bioimpedance.dto.request.UpsertMeasurementRequestDTO;
import com.bioimpedance.dto.response.*;
import com.bioimpedance.entity.Assessment;
import com.bioimpedance.entity.AssessmentMeasurement;
import com.bioimpedance.entity.Client;
import com.bioimpedance.exception.AssessmentLockedException;
import com.bioimpedance.exception.MeasurementInvalidException;
import com.bioimpedance.exception.ResourceNotFoundException;
import com.bioimpedance.library.scientificrules.ScientificRuleRegistry;
import com.bioimpedance.orchestration.assessmentflow.AssessmentFlowAssessment;
import com.bioimpedance.orchestration.assessmentflow.AssessmentFlowInput;
import com.bioimpedance.orchestration.assessmentflow.AssessmentFlowOrchestrator;
import com.bioimpedance.repository.AssessmentRepository;
import com.bioimpedance.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Rascunho, autosave e painel da avaliação (Fase 13 / B3).
 * <p>
 * DEC-52: a tela é um painel de estado sobre uma Assessment DRAFT; cada
 * gravação (medida ou contexto) devolve o painel recalculado pelo
 * orchestrator (doc.md §13 passos 9–11, §17). Nenhuma regra científica
 * vive aqui — só coordenação. DEC-58: calcular/finalizar são outros
 * endpoints (B4/B5); aqui nada grava resultado nem auditoria.
 */
@Service
@RequiredArgsConstructor
public class AssessmentDraftService {

    /** inputIds resolvidos do perfil (DEC-31) — nunca medidas coletadas. */
    private static final String INPUT_AGE = "AGE";
    private static final String INPUT_HEIGHT = "HEIGHT";

    private final AssessmentRepository assessmentRepository;
    private final ClientRepository clientRepository;
    private final CurrentUserService currentUserService;
    private final ProfessionalConfigurationResolver configurationResolver;
    private final ScientificRuleRegistry scientificRuleRegistry;
    private final MeasurementValidator measurementValidator;
    private final AssessmentFlowOrchestrator orchestrator;
    private final AssessmentFlowService assessmentFlowService;

    // ==================== ENDPOINTS DO RASCUNHO ====================

    /** POST /api/assessments/draft — cria o rascunho e devolve o painel. */
    @Transactional
    public AssessmentPanelDTO createDraft(CreateDraftRequestDTO dto) {
        String userId = currentUserService.getCurrentUserId();
        Client client = clientRepository.findByIdAndUserId(dto.getClientId(), userId)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Assessment assessment = Assessment.builder()
            .userId(userId)
            .clientId(client.getId())
            .date(dto.getDate())
            .status(AssessmentStatus.DRAFT)
            .objective(dto.getObjective())
            .trainingLevel(dto.getTrainingLevel())
            .athlete(dto.getAthlete())
            .modality(dto.getModality())
            .build();
        assessment = assessmentRepository.save(assessment);
        return buildPanel(assessment, client);
    }

    /** PATCH /api/assessments/{id}/context — atualiza contexto, recalcula painel. */
    @Transactional
    public AssessmentPanelDTO updateContext(String assessmentId, UpdateContextRequestDTO dto) {
        String userId = currentUserService.getCurrentUserId();
        Assessment assessment = loadDraft(assessmentId, userId);

        if (dto.getDate() != null) {
            assessment.setDate(dto.getDate());
        }
        assessment.setObjective(dto.getObjective());
        assessment.setTrainingLevel(dto.getTrainingLevel());
        assessment.setAthlete(dto.getAthlete());
        assessment.setModality(dto.getModality());

        assessment = assessmentRepository.save(assessment);
        return buildPanel(assessment, loadClient(assessment));
    }

    /**
     * PUT /api/assessments/{id}/measurements/{inputId} — autosave (DEC-56).
     * Upsert por (assessmentId, inputId); value = null remove. INVALID_VALUE
     * e IMPOSSIBLE_VALUE recusam (422 MEASUREMENT_INVALID); demais tipos
     * (ex.: PRECISION_MISMATCH) gravam e voltam como aviso.
     */
    @Transactional
    public MeasurementSaveResponseDTO upsertMeasurement(String assessmentId, String inputId,
                                                        UpsertMeasurementRequestDTO dto) {
        String userId = currentUserService.getCurrentUserId();
        Assessment assessment = loadDraft(assessmentId, userId);
        Client client = loadClient(assessment);

        List<ValidationIssueDTO> issueDTOs;

        if (dto.getValue() == null) {
            assessment.getMeasurements().removeIf(m -> m.getInputId().equals(inputId));
            assessmentRepository.save(assessment);
            issueDTOs = List.of();
        } else {
            ValidationResult validation =
                measurementValidator.validate(Map.of(inputId, dto.getValue()));
            boolean blocking = validation.issues().stream().anyMatch(issue ->
                issue.type() == ValidationIssueType.INVALID_VALUE
                    || issue.type() == ValidationIssueType.IMPOSSIBLE_VALUE);
            if (blocking) {
                throw new MeasurementInvalidException(inputId, validation.issues());
            }
            AssessmentMeasurementSource source = dto.getSource() != null
                ? dto.getSource()
                : AssessmentMeasurementSource.MANUAL;
            assessment.addMeasurement(inputId, dto.getValue(), source);
            assessmentRepository.save(assessment);
            issueDTOs = validation.issues().stream()
                .map(issue -> ValidationIssueDTO.builder()
                    .type(issue.type().name())
                    .inputId(issue.inputId())
                    .message(issue.message())
                    .build())
                .toList();
        }

        return MeasurementSaveResponseDTO.builder()
            .issues(issueDTOs)
            .panel(buildPanel(assessment, client))
            .build();
    }

    /** GET /api/assessments/{id}/panel — reabre o rascunho (F5). */
    @Transactional(readOnly = true)
    public AssessmentPanelDTO getPanel(String assessmentId) {
        String userId = currentUserService.getCurrentUserId();
        Assessment assessment = loadDraft(assessmentId, userId);
        return buildPanel(assessment, loadClient(assessment));
    }

    // ==================== PAINEL ====================

    private AssessmentPanelDTO buildPanel(Assessment assessment, Client client) {
        int age = Period.between(client.getBirthDate(), assessment.getDate()).getYears();

        Map<String, Double> resolvedInputs = new HashMap<>(assessment.toInputMap());
        resolvedInputs.put(INPUT_AGE, (double) age);
        resolvedInputs.put(INPUT_HEIGHT, client.getHeight());

        ClientProfile profile = new ClientProfile(
            toSex(client.getGender()), age,
            assessment.getAthlete(), assessment.getTrainingLevel(), assessment.getModality());
        AssessmentContext context = new AssessmentContext(profile, assessment.getObjective());

        AssessmentFlowInput input = new AssessmentFlowInput(
            assessment.getId(), context, resolvedInputs,
            configurationResolver.resolve(assessment.getUserId()),
            null, false, null, null, false, null);

        AssessmentFlowAssessment assessed = orchestrator.assess(input);
        SuggestionResult suggestion = assessed.suggestionResult();

        Map<String, MeasurementValueDTO> measurements = assessment.getMeasurements().stream()
            .collect(Collectors.toMap(
                AssessmentMeasurement::getInputId,
                m -> MeasurementValueDTO.builder()
                    .value(m.getValue())
                    .source(m.getSource().name())
                    .build()));

        return AssessmentPanelDTO.builder()
            .assessmentId(assessment.getId())
            .clientId(client.getId())
            .clientName(client.getName())
            .date(assessment.getDate())
            .status(assessment.getStatus().name())
            .sex(toSex(client.getGender()).name())
            .age(age)
            .height(client.getHeight())
            .objective(assessment.getObjective() != null ? assessment.getObjective().name() : null)
            .trainingLevel(assessment.getTrainingLevel() != null ? assessment.getTrainingLevel().name() : null)
            .athlete(assessment.getAthlete())
            .modality(assessment.getModality())
            .measurements(measurements)
            .flow(assessmentFlowService.toFlowDTO(suggestion)) // ← instância, não estático
            .build();
    }

    /**
     * POST /api/assessments/{id}/calculate — calcula sem efeito colateral
     * (Fase 13 / B4 — DEC-58). Só DRAFT; não grava resultado nem auditoria.
     */
    @Transactional(readOnly = true)
    public CalculationFlowResponseDTO calculate(String assessmentId) {
        return assessmentFlowService.calculateFromDraft(assessmentId);
    }

    private List<String> requiredInputUnion(SuggestionResult suggestion, Map<String, Double> recordedMeasurements) {
        Set<String> union = new HashSet<>();

        // 1. Pega os IDs das variantes candidatas (READY + MISSING_INPUTS)
        Set<String> candidateIds = suggestion.candidateVariants().stream()
            .map(CandidateVariantSummary::variantId)
            .collect(Collectors.toSet());

        // 2. Adiciona os requiredInputs de cada candidata
        for (String variantId : candidateIds) {
            scientificRuleRegistry.find(variantId).ifPresent(profile -> {
                if (profile.inputs() != null && profile.inputs().requiredInputs() != null) {
                    union.addAll(profile.inputs().requiredInputs());
                }
            });
        }

        // 3. Remove AGE e HEIGHT (são resolvidos pelo back, não são campos de coleta na tela)
        union.remove(INPUT_AGE);
        union.remove(INPUT_HEIGHT);

        // 4. REGRA DE OURO (DEC-55 / doc.md §10.1): Campo com valor gravado nunca some.
        // Se o profissional já preencheu uma medida, ela deve continuar aparecendo
        // no painel mesmo que nenhuma variante candidata a exija mais.
        if (recordedMeasurements != null) {
            union.addAll(recordedMeasurements.keySet());
        }

        // 5. Garante que AGE e HEIGHT não vazem mesmo se estiverem no mapa de medidas
        union.remove(INPUT_AGE);
        union.remove(INPUT_HEIGHT);

        return union.stream().sorted().toList();
    }

    // ==================== HELPERS ====================

    private Assessment loadDraft(String assessmentId, String userId) {
        Assessment assessment = assessmentRepository.findByIdAndUserId(assessmentId, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada"));
        if (assessment.getStatus() != AssessmentStatus.DRAFT) {
            throw new AssessmentLockedException(assessmentId);
        }
        return assessment;
    }

    private Client loadClient(Assessment assessment) {
        return clientRepository
            .findByIdAndUserId(assessment.getClientId(), assessment.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    }

    private Sex toSex(Gender gender) {
        return gender == Gender.FEMALE ? Sex.FEMALE : Sex.MALE;
    }

    private VariantStatusDTO toVariantStatus(CandidateVariantSummary c) {
        return VariantStatusDTO.builder()
            .variantId(c.variantId())
            .status(c.status().name())
            .warnings(c.warnings())
            .missingInputIds(c.missingInputs())
            .reasons(c.reasons())
            .build();
    }
}