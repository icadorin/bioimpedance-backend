package com.bioimpedance;

import com.bioimpedance.service.TwoFactorEncryptionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

/**
 * Teste de contexto completo (Fase 12 / Chunk 5 — DEC-46).
 * Sobe com beans REAIS do motor (AssessmentEngineConfiguration);
 * o único mock restante é o de criptografia 2FA (secret dummy curto).
 */
@SpringBootTest
@ActiveProfiles("test")
class BioimpedanceApplicationTests {

    @MockitoBean
    private TwoFactorEncryptionService twoFactorEncryptionService;

    @Test
    void contextLoads() {
    }
}