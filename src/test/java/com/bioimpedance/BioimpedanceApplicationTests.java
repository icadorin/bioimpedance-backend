package com.bioimpedance;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Disabled("Protótipo legado: falha de schema H2/BLOB e beans de 2FA. Será substituído na Fase 12 (persistence).")
class BioimpedanceApplicationTests {

    @Test
    void contextLoads() {
    }
}
