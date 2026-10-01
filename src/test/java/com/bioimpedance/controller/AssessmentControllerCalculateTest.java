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
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Teste de integração do POST /api/assessments/{id}/calculate
 * (Fase 13 / B4 — DEC-58).
 * <p>
 * Percorre o fluxo real da tela: cria rascunho → grava as 7 dobras do JP7-M
 * (autosave) → calcula. Verifica que o cálculo devolve resultado SEM efeito
 * colateral: auditId nulo, nenhuma Assessment nova, nenhuma auditoria gravada
 * e a avaliação continua DRAFT.
 * <p>
 * Decisões do teste:
 * - MockMvc em vez de TestRestTemplate: os endpoints ficam atrás do JWT e o
 *   CurrentUserService resolve o usuário por e-mail no banco; aqui os filtros
 *   de segurança saem (addFilters = false) e o CurrentUserService é mockado.
 *   Segurança/ownership têm teste próprio.
 * - Corpos em JSON texto + jsonPath: não depende de construtor/builder dos DTOs
 *   nem da versão do Jackson.
 * - @Transactional: tudo que o teste grava é desfeito no fim.
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Transactional
class AssessmentControllerCalculateTest {

    private static final String USER_ID = "user-calc";

    /** requiredInputs do JP7-M (AGE vem do perfil, DEC-31). */
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

    // Mesmo mock do BioimpedanceApplicationTests (secret dummy da criptografia 2FA).
    @MockitoBean private TwoFactorEncryptionService twoFactorEncryptionService;

    @BeforeEach
    void setUp() {
        when(currentUserService.getCurrentUserId()).thenReturn(USER_ID);
    }

    @Test
    void calcularRascunho_devolveResultadoSemGravarNada() throws Exception {
        // Cliente (email e phone são NOT NULL; o id é gerado pelo JPA — não setar)
        Client client = clientRepository.save(Client.builder()
            .userId(USER_ID)
            .name("Cliente Calc")
            .email("calc@teste.com")
            .phone("47999990000")
            .gender(Gender.MALE)
            .birthDate(LocalDate.of(1982, 5, 10))
            .height(178.0)
            .build());

        // POST /draft
        String draftJson = mockMvc.perform(post("/api/assessments/draft")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"clientId": "%s", "date": "2026-10-01", "athlete": false}
                    """.formatted(client.getId())))
            .andExpect(status().is2xxSuccessful())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String assessmentId = JsonPath.read(draftJson, "$.assessmentId");

        // PUT /measurements/{inputId} — autosave das 7 dobras
        for (Map.Entry<String, Double> dobra : JP7_M_DOBRAS.entrySet()) {
            mockMvc.perform(put("/api/assessments/{id}/measurements/{inputId}",
                    assessmentId, dobra.getKey())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"value\": " + dobra.getValue() + "}"))
                .andExpect(status().isOk());
        }

        long avaliacoesAntes = assessmentRepository.count();
        long auditoriasAntes = auditSnapshotRepository.count();

        // POST /{id}/calculate — a variante escolhida vai no corpo
        mockMvc.perform(post("/api/assessments/{id}/calculate", assessmentId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"selectedVariantId\": \"JP7-M\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.selectedVariantId").value("JP7-M"))
            .andExpect(jsonPath("$.auditId").doesNotExist())           // DEC-58: sem auditoria
            .andExpect(jsonPath("$.prediction.outputType").value("BODY_DENSITY"))
            .andExpect(jsonPath("$.prediction.value").isNumber())
            .andExpect(jsonPath("$.finalResult.value").isNumber());    // conversão AUTO (Siri)

        // Sem efeito colateral
        assertEquals(avaliacoesAntes, assessmentRepository.count(),
            "calcular não pode criar Assessment");
        assertEquals(auditoriasAntes, auditSnapshotRepository.count(),
            "calcular não pode gravar auditoria");
        Assessment recarregada = assessmentRepository.findById(assessmentId).orElseThrow();
        assertEquals(AssessmentStatus.DRAFT, recarregada.getStatus(),
            "calcular não finaliza a avaliação");
    }
}