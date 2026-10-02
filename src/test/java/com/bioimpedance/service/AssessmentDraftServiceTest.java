package com.bioimpedance.service;

import com.bioimpedance.constants.AssessmentStatus;
import com.bioimpedance.constants.Gender;
import com.bioimpedance.domain.applicability.ApplicabilityEngine;
import com.bioimpedance.domain.applicability.EvidenceSummaryBuilder;
import com.bioimpedance.domain.calculation.EquationEvaluator;
import com.bioimpedance.domain.config.SystemConversionPolicy;
import com.bioimpedance.domain.conversion.DensityToFatConverter;
import com.bioimpedance.domain.conversionsuggestion.ConversionSuggestionEngine;
import com.bioimpedance.domain.conversionsuggestion.explanation.ConversionSuggestionExplanationBuilder;
import com.bioimpedance.domain.eligibility.EligibilityResolver;
import com.bioimpedance.domain.suggestion.CompatibilityRanker;
import com.bioimpedance.domain.suggestion.SuggestionEngine;
import com.bioimpedance.domain.suggestion.explanation.SuggestionExplanationBuilder;
import com.bioimpedance.domain.validation.MeasurementValidator;
import com.bioimpedance.dto.request.CreateDraftRequestDTO;
import com.bioimpedance.dto.request.UpsertMeasurementRequestDTO;
import com.bioimpedance.dto.response.AssessmentPanelDTO;
import com.bioimpedance.dto.response.AssessmentResponseDTO;
import com.bioimpedance.dto.response.MeasurementSaveResponseDTO;
import com.bioimpedance.entity.Assessment;
import com.bioimpedance.entity.Client;
import com.bioimpedance.exception.AssessmentLockedException;
import com.bioimpedance.exception.MeasurementInvalidException;
import com.bioimpedance.exception.ResourceNotFoundException;
import com.bioimpedance.library.conversions.ConversionDefinitionRegistry;
import com.bioimpedance.library.equations.EquationVariantRegistry;
import com.bioimpedance.library.measurements.InputTypeCatalog;
import com.bioimpedance.library.scientificrules.ScientificRuleRegistry;
import com.bioimpedance.mapper.AssessmentMapper;
import com.bioimpedance.orchestration.assessmentflow.AssessmentFlowOrchestrator;
import com.bioimpedance.persistence.AuditSnapshotStore;
import com.bioimpedance.repository.AssessmentRepository;
import com.bioimpedance.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes do rascunho/autosave/painel (Fase 13 / B3 — DEC-52/55/56/57/61).
 * Repositórios mockados; motor científico real.
 */
class AssessmentDraftServiceTest {

    private static final String USER_ID = "user-1";
    private static final String CLIENT_ID = "client-1";
    private static final LocalDate ASSESSMENT_DATE = LocalDate.of(2026, 9, 30);

    private AssessmentRepository assessmentRepository;
    private ClientRepository clientRepository;
    private AssessmentDraftService service;
    private AssessmentFlowService assessmentFlowService; // Adicionado

    @BeforeEach
    void setUp() {
        // Mocks de repositórios (não precisam de lógica real)
        assessmentRepository = mock(AssessmentRepository.class);
        clientRepository = mock(ClientRepository.class);
        CurrentUserService currentUserService = mock(CurrentUserService.class);
        AuditSnapshotStore auditSnapshotStore = mock(AuditSnapshotStore.class);
        MetabolicService metabolicService = mock(MetabolicService.class);

        // Registries REAIS (motor científico)
        ScientificRuleRegistry scientificRuleRegistry = new ScientificRuleRegistry();
        ConversionDefinitionRegistry conversionDefinitionRegistry = new ConversionDefinitionRegistry();
        EquationVariantRegistry equationVariantRegistry = new EquationVariantRegistry();
        InputTypeCatalog inputTypeCatalog = new InputTypeCatalog();

        // Orchestrator REAL (coordena o pipeline científico)
        AssessmentFlowOrchestrator orchestrator = new AssessmentFlowOrchestrator(
            new MeasurementValidator(inputTypeCatalog),
            new ApplicabilityEngine(new EvidenceSummaryBuilder()),
            new EligibilityResolver(),
            new SuggestionEngine(new CompatibilityRanker(), new SuggestionExplanationBuilder()),
            new ConversionSuggestionEngine(new ConversionSuggestionExplanationBuilder()),
            new EquationEvaluator(),
            new DensityToFatConverter(),
            equationVariantRegistry,
            scientificRuleRegistry,
            conversionDefinitionRegistry,
            SystemConversionPolicy.platformDefault());

        // ConfigurationResolver REAL
        ProfessionalConfigurationResolver configurationResolver =
            new ProfessionalConfigurationResolver(scientificRuleRegistry, conversionDefinitionRegistry);

        // AssessmentFlowService REAL (NÃO é mock — usa o orchestrator real)
        assessmentFlowService = new AssessmentFlowService(
            clientRepository,
            assessmentRepository,
            currentUserService,
            configurationResolver,
            scientificRuleRegistry,
            orchestrator,
            auditSnapshotStore,
            metabolicService);

        // AssessmentDraftService com o FlowService REAL
        service = new AssessmentDraftService(
            assessmentRepository,
            clientRepository,
            currentUserService,
            configurationResolver,
            new MeasurementValidator(inputTypeCatalog),
            orchestrator,
            assessmentFlowService); // ← instância real, não mock

        // Stubs básicos
        when(currentUserService.getCurrentUserId()).thenReturn(USER_ID);
        when(clientRepository.findByIdAndUserId(CLIENT_ID, USER_ID))
            .thenReturn(Optional.of(client(Gender.MALE)));
        when(assessmentRepository.save(any(Assessment.class)))
            .thenAnswer(inv -> inv.getArgument(0));
    }

    // ==================== HELPERS ====================

    private Client client(Gender gender) {
        return Client.builder()
            .id(CLIENT_ID)
            .userId(USER_ID)
            .name("Cliente Teste")
            .gender(gender)
            .birthDate(LocalDate.of(1982, 5, 10))
            .height(178.0)
            .build();
    }

    private Assessment draft(AssessmentStatus status) {
        return Assessment.builder()
            .id("assessment-1")
            .userId(USER_ID)
            .clientId(CLIENT_ID)
            .date(ASSESSMENT_DATE)
            .status(status)
            .build();
    }

    private void stubDraft(Assessment assessment) {
        when(assessmentRepository.findByIdAndUserId("assessment-1", USER_ID))
            .thenReturn(Optional.of(assessment));
    }

    private CreateDraftRequestDTO createDraftRequest() {
        CreateDraftRequestDTO dto = new CreateDraftRequestDTO();
        dto.setClientId(CLIENT_ID);
        dto.setDate(ASSESSMENT_DATE);
        return dto;
    }

    private UpsertMeasurementRequestDTO measurement(Double value) {
        return new UpsertMeasurementRequestDTO(value, null);
    }

    // ==================== PAINEL ====================

    @Test
    void novoRascunho_painelSemMedidas_tudoMissingInputsParaMasculino() {
        AssessmentPanelDTO panel = service.createDraft(createDraftRequest());

        assertEquals("DRAFT", panel.getStatus());
        assertEquals("MALE", panel.getSex());
        assertEquals(44, panel.getAge());
        assertEquals("NO_READY_METHOD", panel.getFlow().getSuggestionStatus());
        // 43 variantes: 18 femininas INELIGIBLE para homem → 25 candidatas MISSING_INPUTS
        assertEquals(25, panel.getFlow().getCandidateVariants().size());
        assertEquals(18, panel.getFlow().getExcludedVariants().size());
        // DEC-55: união sem AGE/HEIGHT (resolvidos do perfil)
        assertFalse(panel.getFlow().getRequiredInputUnion().contains("AGE"));
        assertFalse(panel.getFlow().getRequiredInputUnion().contains("HEIGHT"));
        assertTrue(panel.getFlow().getRequiredInputUnion().contains("SKINFOLD_TRICEPS"));
        assertTrue(panel.getMeasurements().isEmpty());
    }

    @Test
    void painel_mudaComSexo_femininoInverteCandidatas() {
        when(clientRepository.findByIdAndUserId(CLIENT_ID, USER_ID))
            .thenReturn(Optional.of(client(Gender.FEMALE)));

        AssessmentPanelDTO panel = service.createDraft(createDraftRequest());

        assertEquals("FEMALE", panel.getSex());
        assertTrue(panel.getFlow().getExcludedVariants().stream()
            .anyMatch(v -> v.getVariantId().equals("JP7-M")
                && v.getStatus().equals("INELIGIBLE")));
        assertTrue(panel.getFlow().getCandidateVariants().stream()
            .anyMatch(v -> v.getVariantId().equals("JP7-F")));
    }

    @Test
    void painel_medidasCompletas_missingInputsViraReadyESugerido() {
        stubDraft(draft(AssessmentStatus.DRAFT));
        // 7 dobras do JP7-M (AGE/HEIGHT vêm do perfil — DEC-31)
        service.upsertMeasurement("assessment-1", "SKINFOLD_PECTORAL", measurement(10.0));
        service.upsertMeasurement("assessment-1", "SKINFOLD_AXILLARY_MID", measurement(9.0));
        service.upsertMeasurement("assessment-1", "SKINFOLD_TRICEPS", measurement(8.0));
        service.upsertMeasurement("assessment-1", "SKINFOLD_SUBSCAPULAR", measurement(14.0));
        service.upsertMeasurement("assessment-1", "SKINFOLD_ABDOMEN", measurement(15.0));
        service.upsertMeasurement("assessment-1", "SKINFOLD_SUPRAILIAC", measurement(11.0));
        service.upsertMeasurement("assessment-1", "SKINFOLD_THIGH", measurement(12.0));

        AssessmentPanelDTO panel = service.getPanel("assessment-1");

        assertEquals("SUGGESTED", panel.getFlow().getSuggestionStatus());
        assertTrue(panel.getFlow().getCandidateVariants().stream()
            .anyMatch(v -> v.getVariantId().equals("JP7-M")
                && v.getStatus().equals("READY")));
        assertTrue(panel.getFlow().getSuggestedVariantIds().contains("JP7-M"));
    }

    // ==================== AUTOSAVE (DEC-56/57) ====================

    @Test
    void upsertMedida_adicionaAtualizaEApareceNoPainel() {
        stubDraft(draft(AssessmentStatus.DRAFT));

        MeasurementSaveResponseDTO response = service.upsertMeasurement(
            "assessment-1", "SKINFOLD_TRICEPS", measurement(12.0));

        assertEquals(12.0, response.getPanel().getMeasurements().get("SKINFOLD_TRICEPS").getValue());
        assertEquals("MANUAL", response.getPanel().getMeasurements().get("SKINFOLD_TRICEPS").getSource());

        service.upsertMeasurement("assessment-1", "SKINFOLD_TRICEPS", measurement(13.5));
        AssessmentPanelDTO panel = service.getPanel("assessment-1");
        assertEquals(13.5, panel.getMeasurements().get("SKINFOLD_TRICEPS").getValue());
    }

    @Test
    void upsertMedida_valorNulo_remove() {
        stubDraft(draft(AssessmentStatus.DRAFT));
        service.upsertMeasurement("assessment-1", "SKINFOLD_TRICEPS", measurement(12.0));
        service.upsertMeasurement("assessment-1", "SKINFOLD_TRICEPS", measurement(null));

        AssessmentPanelDTO panel = service.getPanel("assessment-1");
        assertFalse(panel.getMeasurements().containsKey("SKINFOLD_TRICEPS"));
    }

    @Test
    void upsertMedida_valorImpossivel_recusaCom422() {
        stubDraft(draft(AssessmentStatus.DRAFT));

        MeasurementInvalidException ex = assertThrows(MeasurementInvalidException.class, () ->
            service.upsertMeasurement("assessment-1", "SKINFOLD_TRICEPS", measurement(250.0)));

        assertEquals("SKINFOLD_TRICEPS", ex.getInputId());
        assertTrue(ex.getIssues().stream()
            .anyMatch(i -> i.type().name().equals("IMPOSSIBLE_VALUE")));
        // nada foi gravado
        assertTrue(service.getPanel("assessment-1").getMeasurements().isEmpty());
    }

    @Test
    void upsertMedida_precisionMismatch_gravaComAviso() {
        stubDraft(draft(AssessmentStatus.DRAFT));

        MeasurementSaveResponseDTO response = service.upsertMeasurement(
            "assessment-1", "SKINFOLD_TRICEPS", measurement(12.34));

        assertEquals(1, response.getIssues().size());
        assertEquals("PRECISION_MISMATCH", response.getIssues().get(0).getType());
        assertEquals(12.34, response.getPanel().getMeasurements().get("SKINFOLD_TRICEPS").getValue());
    }

    // ==================== OWNERSHIP E TRAVA (DEC-58) ====================

    @Test
    void ownership_rascunhoDeOutroProfissional_naoEncontrado() {
        when(assessmentRepository.findByIdAndUserId("assessment-1", USER_ID))
            .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getPanel("assessment-1"));
    }

    @Test
    void avaliacaoFinalizada_painelEEdicaoBloqueados() {
        stubDraft(draft(AssessmentStatus.FINALIZED));

        assertThrows(AssessmentLockedException.class, () -> service.getPanel("assessment-1"));
        assertThrows(AssessmentLockedException.class, () ->
            service.upsertMeasurement("assessment-1", "SKINFOLD_TRICEPS", measurement(12.0)));
    }

    // ==================== DEC-61: leitores legados ====================

    @Test
    void leitoresLegados_buscamSomenteFinalizadas() {
        AssessmentRepository repo = mock(AssessmentRepository.class);
        CurrentUserService currentUser = mock(CurrentUserService.class);
        ClientRepository clients = mock(ClientRepository.class);
        BillingService billing = mock(BillingService.class);
        AssessmentMapper mapper = mock(AssessmentMapper.class);
        AssessmentFlowService flowService = mock(AssessmentFlowService.class);
        // MetabolicService removido pois não faz parte do construtor de AssessmentService

        when(currentUser.getCurrentUserId()).thenReturn(USER_ID);
        when(clients.findByIdAndUserId(CLIENT_ID, USER_ID)).thenReturn(Optional.of(client(Gender.MALE)));
        when(repo.findByUserIdAndClientIdAndStatusOrderByDateDescCreatedAtDesc(
            USER_ID, CLIENT_ID, AssessmentStatus.FINALIZED))
            .thenReturn(List.of(draft(AssessmentStatus.FINALIZED)));
        when(mapper.toResponse(any(Assessment.class))).thenReturn(new AssessmentResponseDTO());

        // Removido 'metabolic' da chamada (eram 7 argumentos, agora são 6)
        AssessmentService assessmentService = new AssessmentService(
            repo, mapper, flowService, billing, clients, currentUser);

        List<AssessmentResponseDTO> result = assessmentService.findByClientId(CLIENT_ID);

        assertEquals(1, result.size());
        verify(repo).findByUserIdAndClientIdAndStatusOrderByDateDescCreatedAtDesc(
            USER_ID, CLIENT_ID, AssessmentStatus.FINALIZED);
    }
}