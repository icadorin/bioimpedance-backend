package com.bioimpedance.controller;

import com.bioimpedance.constants.AssessmentStatus;
import com.bioimpedance.constants.Gender;
import com.bioimpedance.entity.Assessment;
import com.bioimpedance.entity.Client;
import com.bioimpedance.persistence.repository.AuditSnapshotRepository;
import com.bioimpedance.repository.AssessmentRepository;
import com.bioimpedance.repository.ClientRepository;
import com.bioimpedance.service.CurrentUserService;
import com.bioimpedance.service.TwoFactorEncryptionService;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Teste de integração do POST /api/assessments/{id}/finalize
 * (Fase 13 / B5 — DEC-54/58).
 * <p>
 * Valida:
 * <ul>
 *   <li>finalize persiste resultado, grava AuditSnapshot e trava a avaliação</li>
 *   <li>override sem motivo → 422 REASON_REQUIRED</li>
 *   <li>avaliação já FINALIZADA → 409 ASSESSMENT_LOCKED</li>
 * </ul>
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
class AssessmentFlowFinalizeIntegrationTest {

    private static final String USER_ID = "user-finalize";

    private static final Map<String, Double> JP7_M_DOBRAS = Map.of(
        "SKINFOLD_PECTORAL", 10.0,
        "SKINFOLD_AXILLARY_MID", 12.0,
        "SKINFOLD_TRICEPS", 11.0,
        "SKINFOLD_SUBSCAPULAR", 14.0,
        "SKINFOLD_ABDOMEN", 20.0,
        "SKINFOLD_SUPRAILIAC", 16.0,
        "SKINFOLD_THIGH", 15.0);

    @Autowired private MockMvc mockMvc;
    @Autowired private ClientRepository clientRepository;
    @Autowired private AssessmentRepository assessmentRepository;
    @Autowired private AuditSnapshotRepository auditSnapshotRepository;

    @MockitoBean private CurrentUserService currentUserService;
    @MockitoBean private TwoFactorEncryptionService twoFactorEncryptionService;

    @BeforeEach
    void setUp() {
        when(currentUserService.getCurrentUserId()).thenReturn(USER_ID);
    }

    // ============ Setup helper ============

    private String setupDraftWithJP7M() throws Exception {
        Client client = clientRepository.save(Client.builder()
            .userId(USER_ID)
            .name("Cliente Finalize")
            .email("finalize@teste.com")
            .phone("47999991111")
            .gender(Gender.MALE)
            .birthDate(LocalDate.of(1982, 5, 10))
            .height(178.0)
            .build());

        String draftJson = mockMvc.perform(post("/api/assessments/draft")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"clientId": "%s", "date": "2026-10-01", "athlete": false}
                    """.formatted(client.getId())))
            .andExpect(status().is2xxSuccessful())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String assessmentId = JsonPath.read(draftJson, "$.assessmentId");

        for (Map.Entry<String, Double> dobra : JP7_M_DOBRAS.entrySet()) {
            mockMvc.perform(put("/api/assessments/{id}/measurements/{inputId}",
                    assessmentId, dobra.getKey())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"value\": " + dobra.getValue() + "}"))
                .andExpect(status().isOk());
        }

        return assessmentId;
    }

    // ============ Testes ============

    @Test
    void finalize_persisteResultadoEAuditoriaETravaAvaliacao() throws Exception {
        String assessmentId = setupDraftWithJP7M();
        long auditoriasAntes = auditSnapshotRepository.count();

        // POST /{id}/finalize — variante sugerida (JP7-M é a melhor para homem com 7 dobras)
        mockMvc.perform(post("/api/assessments/{id}/finalize", assessmentId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "selectedVariantId": "JP7-M",
                        "selectionReason": null
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.auditId").exists())        // DEC-58: auditoria gravada
            .andExpect(jsonPath("$.selectedVariantId").value("JP7-M"))
            .andExpect(jsonPath("$.finalResult.value").isNumber());

        // Auditoria foi gravada
        assertEquals(auditoriasAntes + 1, auditSnapshotRepository.count(),
            "finalize deve gravar exatamente 1 AuditSnapshot");

        // Avaliação travada (FINALIZED)
        Assessment recarregada = assessmentRepository.findById(assessmentId).orElseThrow();
        assertEquals(AssessmentStatus.FINALIZED, recarregada.getStatus(),
            "finalize deve transicionar DRAFT → FINALIZED");
        assertNotNull(recarregada.getResult(),
            "finalize deve persistir AssessmentResult");

        // Tentar calcular de novo → 409 ASSESSMENT_LOCKED (DEC-58)
        mockMvc.perform(post("/api/assessments/{id}/calculate", assessmentId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"selectedVariantId\": \"JP7-M\"}"))
            .andExpect(status().isConflict());
    }

    @Test
    void finalize_overrideSemMotivo_422_REASON_REQUIRED() throws Exception {
        String assessmentId = setupDraftWithJP7M();

        mockMvc.perform(post("/api/assessments/{id}/finalize", assessmentId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "selectedVariantId": "P-M1",
                    "selectionReason": null
                }
                """))
            .andExpect(status().isUnprocessableContent())
            .andExpect(jsonPath("$.errors.code").value("REASON_REQUIRED"))  // ← ajustar caminho
            .andExpect(jsonPath("$.errors.variantId").value("P-M1"));       // ← ajustar caminho

        Assessment recarregada = assessmentRepository.findById(assessmentId).orElseThrow();
        assertEquals(AssessmentStatus.DRAFT, recarregada.getStatus());
    }

    @Test
    void finalize_overrideComMotivo_funciona() throws Exception {
        String assessmentId = setupDraftWithJP7M();

        // G-M3 está READY com as 7 dobras do JP7 (precisa só de TR/SI/AB),
        // mas NÃO é sugerida (idade 44 fora da faixa validada 18–30 → WARNING).
        // Ou seja: override válido. Com motivo → deve passar.
        mockMvc.perform(post("/api/assessments/{id}/finalize", assessmentId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "selectedVariantId": "G-M3",
                    "selectionReason": "Cliente prefere Guedes 3 por histórico clínico"
                }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.auditId").exists())
            .andExpect(jsonPath("$.selectedVariantId").value("G-M3"))
            .andExpect(jsonPath("$.variantOverride").value(true))
            .andExpect(jsonPath("$.variantOverrideReason")
                .value("Cliente prefere Guedes 3 por histórico clínico"));
    }

    @Test
    void finalize_varianteInelegivel_422_SELECTION_NOT_ALLOWED() throws Exception {
        String assessmentId = setupDraftWithJP7M();

        mockMvc.perform(post("/api/assessments/{id}/finalize", assessmentId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "selectedVariantId": "JP7-F",
                    "selectionReason": "qualquer motivo"
                }
                """))
            .andExpect(status().isUnprocessableContent())
            .andExpect(jsonPath("$.errors.code").value("SELECTION_NOT_ALLOWED"))  // ← ajustar
            .andExpect(jsonPath("$.errors.variantId").value("JP7-F"))
            .andExpect(jsonPath("$.errors.status").value("INELIGIBLE"));
    }

    @Test
    void finalize_avaliacaoJaFinalizada_409_ASSESSMENT_LOCKED() throws Exception {
        String assessmentId = setupDraftWithJP7M();

        // Finaliza uma vez
        mockMvc.perform(post("/api/assessments/{id}/finalize", assessmentId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"selectedVariantId\": \"JP7-M\"}"))
            .andExpect(status().isOk());

        // Tenta finalizar de novo → 409
        mockMvc.perform(post("/api/assessments/{id}/finalize", assessmentId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"selectedVariantId\": \"JP7-M\"}"))
            .andExpect(status().isConflict());
    }
}