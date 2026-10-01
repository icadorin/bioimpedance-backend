# Guia de Uso dos Documentos — Sequência de Implementação

Este documento define **em que ordem e como usar** os 5 documentos-base ao implementar o motor, e serve como controle de progresso. Não substitui nenhum deles — é o mapa de navegação entre eles, pra não precisar colar os 5 inteiros toda vez que for pedir ajuda de implementação pra uma IA.

Os 5 documentos:

| Documento                                 | O que define                                                                              |
| ----------------------------------------- | ----------------------------------------------------------------------------------------- |
| `schema_cientifico.md`                    | Estrutura da `EquationVariantScientificProfile` — o que cada ficha científica deve conter |
| `equation_family_especificacao_formal.md` | O que é uma `EquationFamily`, como variantes se agrupam, inventário oficial               |
| `especificacao_cientifica.md`             | Como o `SuggestionEngine` deve interpretar aplicabilidade, prontidão e sugestão           |
| `architecture.md`                         | Onde cada responsabilidade vive no código — módulos, fronteiras, dependências             |
| `doc.md`                                  | Especificação técnica completa — fluxo operacional, modelo de dados, regras de produto/UI |

---

## 0. Regra #1 — hierarquia de fontes de verdade

**As fichas científicas (`documento-mestre-revisao.md` + os `.md` individuais por `variantId`) são a fonte de verdade para DADOS.** Isso inclui: quais variantes existem, quantas são, coeficientes, populações, evidências.

**Os 5 documentos são fonte de verdade para REGRAS DE COMPORTAMENTO** — como o motor deve interpretar aplicabilidade, prontidão, sugestão; onde cada responsabilidade vive no código; qual a estrutura de dados esperada.

Consequência prática: o `doc.md` contém uma tabela-mestra de fórmulas (§18) com coeficientes de todas as famílias — **incluindo variantes que as fichas deliberadamente excluíram** (ex.: `G-F4`–`G-F8`). Essa tabela e qualquer outro dado numérico nos 5 documentos podem estar desatualizados. **Nunca usar coeficiente, contagem de variante ou inventário desses documentos — sempre puxar das fichas.** Os documentos valem pela regra/estrutura, não pelo dado de exemplo.

Se uma IA de implementação encontrar um número/fórmula/variante divergente entre um dos 5 documentos e as fichas, **as fichas ganham, sempre.**

---

## 0.1 Stack confirmada

- **Java 21**, Spring Boot **4.0.6** (spring-boot-starter-parent), Maven.
- Pacote-base real: **`com.bioimpedance`** (não `com.bioimpedance.backend` — corrigido na migração MIG-1, concluída).
- Projeto já existente (`bioimpedance-backend-temp`) era um protótipo tipo "calculadora direta" (`controller → service → repository`, com `entity`/`dto`/`mapper`). A lógica científica legacy (`util/BodyFatCalculator`, `service/CalculationService`, `util/BodyFatInterpreter`) foi **removida na Fase 12** e substituída pela arquitetura `domain/library/orchestration/persistence`. A infraestrutura (`security`, `config`, `exception`, `pagination`, auth, billing, branding) foi reaproveitada como está.
- Dependências no `pom.xml`: Jackson databind + jsr310, Lombok, MapStruct, H2 (test), PostgreSQL, `jackson-dataformat-yaml` (adicionado na Fase 2b).

---

## 0.2 Estrutura de pastas do projeto (contexto permanente)

### Protótipo existente — estado pós-Fase 12

```
bioimpedance-backend-temp/src/main/java/com/bioimpedance/
├── BioimpedanceApplication.java
├── config/          ← reaproveita (Security, Jackson, Web, Stripe)
│                      + AssessmentEngineConfiguration ✅ Fase 12/Chunk 5 (DEC-43: wiring Spring do motor)
├── constants/       ← reaproveita (Gender, Plan, ActivityLevel, etc.)
│                      ⚠️ ActivityLevel ≠ TrainingLevel do schema (ver §1.1)
├── controller/      ← reaproveita (Auth, Billing, Client, Dashboard)
│                      ✅ AssessmentController REFEITO (Chunk 4): /calculate aceita AssessmentFlowRequestDTO
│                      ❌ AssessmentFlowController REMOVIDO (Chunk 4: duplicava /calculate, DEC-42c)
├── dto/             ← reaproveita (auth, request, response)
│                      ✅ Fase 12: AssessmentFlowRequestDTO + VariantStatusDTO/AssessmentFlowResponseDTO/
│                        PredictionDTO/ConversionSuggestionDTO/CalculationFlowResponseDTO (Chunk 2, DEC-39)
│                      │                      ✅ NavyDataDTO/BioimpedanceDataDTO/SkinfoldDataDTO REMOVIDOS (DEC-45)
│                      ✅ MethodDetailsDTO/MethodDetailItem REMOVIDOS (DEC-45)
│                      ✅ AssessmentResponseDTO agora expõe `measurements: Map<inputId, valor>`
│                      ⚠️ CalculateRequestDTO/CalculationResultDTO legacy mortos (Chunk 4)
├── entity/          ← ✅ Assessment com @OneToMany measurements (DEC-9) + AssessmentMeasurement (DEC-9/12)
│                      ✅ AssessmentResult REFEITO (Chunk 4): campos científicos + derivados (DEC-35)
│                      ⚠️ Colunas fixas de medida não são mais escritas (DEC-36); drop físico pendente
│                      (AuditSnapshotEntity movido para persistence/entity no Chunk 1)
├── exception/       ← reaproveita
├── mapper/          ← reaproveita
├── pagination/      ← reaproveita
├── repository/      ← reaproveita (User, Client, Billing, etc.)
│                      (AuditSnapshotRepository movido para persistence/repository no Chunk 1)
├── security/        ← reaproveita
├── service/         ✅ Fase 12 (a ponte nova):
│                      ├─ AssessmentService REFEITO (Chunk 4): MEASUREMENT_MAP + delega ao fluxo (DEC-42)
│                      ├─ AssessmentFlowService NOVO (Chunk 3): client→context→config→orchestrator→audit→DTOs
│                      ├─ MetabolicService NOVO (Chunk 4): métricas derivadas pós-resultado (DEC-35/44)
│                      ├─ ProfessionalConfigurationResolver NOVO (Chunk 2): DEFAULT = biblioteca inteira
│                      ├─ RecommendationService MANTIDO (DEC-35: dieta/treino, concern separado)
│                      └─ ❌ CalculationService REMOVIDO (Chunk 4, DEC-33)
└── util/            ← ✅ MetabolicCalculator MANTIDO (DEC-35: IMC/BMR/TDEE/FFMI)
                       ❌ BodyFatCalculator REMOVIDO (Chunk 4, DEC-33)
                       ❌ BodyFatInterpreter REMOVIDO (Chunk 4: lógica virou tabela no MetabolicService, DEC-44)
```

### Arquitetura nova — preenchida

```
com.bioimpedance/
├── domain/
│   ├── contracts/             ← Fase 1 ✅ (17 tipos originais + contratos das Fases 5–8)
│   ├── validation/            ← Fase 4 ✅ (MeasurementValidator)
│   ├── applicability/         ← Fase 5 ✅ (ApplicabilityEngine, EvidenceSummaryBuilder)
│   ├── eligibility/           ← Fase 6 ✅ (EligibilityResolver)
│   ├── suggestion/            ← Fase 7 ✅ (SuggestionEngine, CompatibilityRanker, SuggestionExplanationBuilder)
│   ├── conversionsuggestion/  ← Fase 8 ✅ (ConversionSuggestionEngine, ConversionSuggestionExplanationBuilder)
│   ├── calculation/           ← Fase 3 ✅ (EquationEvaluator, PredictionResult)
│   ├── conversion/            ← Fase 3 ✅ (DensityToFatConverter)
│   ├── config/                ← Fase 9 ✅ (ConfigurationMode, ProfessionalConfiguration, SystemConversionPolicy)
│   └── audit/                 ← Fase 10 ✅ (AuditSnapshot)
├── library/
│   ├── equations/             ← Fase 3 ✅ (FormulaDefinition, FormulaTemplate, EquationVariantRegistry)
│   ├── conversions/           ← Fase 2c ✅ (ConversionDefinition, ConversionDefinitionRegistry)
│   ├── scientificrules/       ← Fase 2a ✅ (30 tipos + ScientificRuleRegistry)
│   └── measurements/          ← Fase 2c ✅ (InputTypeDefinition, InputTypeCatalog)
├── orchestration/
│   └── assessment-flow/       ← Fase 11 ✅ (AssessmentFlowOrchestrator, AssessmentFlowInput, AssessmentFlowResult)
└── persistence/               ← Fase 12 ✅ (Chunk 1)
    ├── AuditSnapshotStore.java          ← Chunk 1 ✅ (append + read, JSON via ObjectMapper; DEC-34)
    ├── entity/
    │   └── AuditSnapshotEntity.java     ← Chunk 1 ✅ (imutável, payload CLOB + colunas indexadas)
    └── repository/
        └── AuditSnapshotRepository.java ← Chunk 1 ✅ (extends Repository, só save/find; append-only)
```

> **Nota (DEC-43):** as classes de `domain/`, `library/` e `orchestration/` permanecem framework-agnostic (sem `@Component`). O wiring Spring delas vive num único ponto: `config/AssessmentEngineConfiguration`.

### Resources — dados

```
bioimpedance-backend-temp/src/main/resources/
└── library/
    ├── scientific-rules/   ← 43 YAMLs ✅ (EquationVariantScientificProfile)
    ├── equations/          ← 43 YAMLs ✅ (FormulaDefinition — inventário completo)
    ├── conversions/        ← siri.yaml + brozek.yaml ✅
    └── measurements/       ← input-types.yaml ✅ (15 inputIds)
```

### Testes — 101 tests verdes (98 golden + 2 store + 1 contexto)

```
src/test/java/com/bioimpedance/
├── BioimpedanceApplicationTests.java          ← ✅ REABILITADO (Chunk 5): contextLoads com profile "test"
├── library/
│   ├── LibraryLoadingGoldenTest.java          ← 7 testes (visão integrada)
│   ├── scientificrules/
│   │   └── ScientificRuleRegistryGoldenTest.java  ← 5 testes
│   ├── conversions/
│   │   └── ConversionDefinitionRegistryGoldenTest.java ← 2 testes
│   └── measurements/
│   │    └── InputTypeCatalogGoldenTest.java    ← 3 testes
│   └── equations/
│       └── EquationLibraryGoldenTest.java     ← 4 testes (cross-check matemática↔ciência)
├── domain/
│   ├── calculation/
│   │   └── CalculationGoldenTest.java              ← 2 testes (FALK4 + P-M16 + Siri)
│   ├── validation/
│   │   └── MeasurementValidationGoldenTest.java    ← 7 testes (validação contra InputTypeCatalog)
│   ├── applicability/
│   │   ├── ApplicabilityGoldenTest.java        ← 17 testes (SEX/AGE/POPULATION/CONTEXT + invariantes)
│   │   └── EvidenceCoverageGoldenTest.java     ← 5 testes (cobertura de evidência das 43 variantes)
│   ├── eligibility/
│   │   └── EligibilityGoldenTest.java     ← 11 testes (READY/WARNING/MISSING/INELIGIBLE/DISABLED + invariantes)
│   ├── suggestion/
│   │   └── SuggestionGoldenTest.java     ← 12 testes (indicação + estados extremos + invariantes + fail-fast)
│   ├── conversionsuggestion/
│   │   └── ConversionSuggestionGoldenTest.java  ← 7 testes (indicação + estados + fail-fast)
│   ├── config/
│   │   └── ConfigGoldenTest.java          ← 7 testes (default Siri + habilitação + preferência + restaurar padrão)
│   └── audit/
│       └── AuditGoldenTest.java           ← 8 testes (imutabilidade + cenários com/sem conversão + override + versões + contexto)
├── orchestration/
│   └── AssessmentFlowOrchestrationGoldenTest.java  ← 1 teste (fluxo completo JP7-M → Siri → BODY_FAT_PERCENTAGE)
└── persistence/
    └── AuditSnapshotStoreTest.java        ← 2 testes (round-trip JSON + histórico ordenado; fake in-memory, DEC-41)
```

---

## 0.3 Pendências e decisões registradas

**MIG-1 — ✅ RESOLVIDA.** Java das Fases 1+2a migrado para o projeto com rename `com.bioimpedance.backend.*` → `com.bioimpedance.*`. Compila verde.

**MIG-2 — Decisão em aberto.** `Sex` e `TrainingLevel` duplicados em `library.scientificrules` e `domain.contracts` (exigência de fronteira). Manter duplicação até decidir sobre shared-kernel.

**DAD-1 — `P-F15` sem ficha fonte.** Inventário real = 43 variantes.

**DEC-1 — `G-F4`–`G-F8` fora da V1.** Não gerar YAML nem FormulaDefinition.

**DEC-2 — `FALK4` com `supportedSexes = [MALE]`.** A forma feminina `7.9 + 0.213 × Σ4` tem coeficientes próprios e não deve ser fundida.

**DEC-3 — Siri é default operacional.** Vive em `SystemConversionPolicy.defaultConversionId` (Fase 9), nunca como `if (name == "Siri")` no motor.

**DEC-4 — `DevelopmentEvidence.population` modelado como `String`.**

**DEC-5 — Regra de classificação de estudos** (validationStudies / crossValidationStudies / externalValidationStudies).

**DEC-6 — Métricas de validação** (standardError = EPE; ET nunca; rmse só explícito; % gordura → otherMetrics).

**DEC-7 — BioimpedanceApplicationTests com `@Disabled` em nível de classe.** O teste de contexto do protótipo falhava por BLOB/H2 + placeholder `APP_ENCRYPTION_SECRET`. **RESOLVIDO na Fase 12/Chunk 5** (application-test.yml + BYTEA + @MockitoBean; ver DEC-43).

**DEC-8 — `FormulaTemplate` com 7 variantes.** LINEAR_SUM, LOG10_SUM, QUADRATIC_SUM_WITH_AGE, QUADRATIC_SUM_WITH_AGE_AND_CIRC, QUADRATIC_SUM_WITH_AGE_MASS_HEIGHT, LOG10_SUM_WITH_AGE, LOG10_SUM_WITH_AGE_AND_CIRC. Circunferências mapeadas via `namedInputs` (circ1/circ2); BODY_MASS e HEIGHT acessados diretamente pelo evaluator.

**DEC-9 — Medidas em tabela filha `assessment_measurements` (Opção A), com unique constraint (assessment_id, input_id) desde a primeira migration.** `Assessment` ganha @OneToMany(mappedBy = "assessment", cascade = ALL, orphanRemoval = true) + @Builder.Default; ponte toInputMap() entrega o Map<String, Double> pro EquationEvaluator; addMeasurement() faz upsert em memória. Colunas fixas depreciadas mas vivas até a Fase 12.

**DEC-10 — Coluna `unit` adiada.** Premissa aceita: unidades canônicas do InputTypeCatalog (mm/cm/kg/years) são imutáveis.

**DEC-11 — `recordedAt` e batch_size adiados.** Aditivos puros.

**DEC-12 — Coluna física `measurement_value`; campo Java `value`.** Motivo: VALUE é palavra reservada em vários dialetos SQL (H2 incluso).

**DEC-13 — UNIT_MISMATCH, DUPLICATE, CONFLICT e INCONSISTENCY existem no enum por contrato, mas nenhum validador da V1 os emite;** a emissão entra quando a fonte de dados correspondente existir (unidade declarada no DTO de entrada, entrada em lista, regras entre campos).

**DEC-14 — MeasurementValidator.validate(map, requiredInputIds) (overload) fica em domain/validation.** Se a Fase 6 decidir que MISSING_REQUIRED_INPUT é responsabilidade exclusiva de domain/eligibility, o overload é removido sem dor (a chamada é opcional).

**DEC-15 — MISSING_INPUTS é responsabilidade de domain.eligibility.** Resolve a DEC-14: o overload MeasurementValidator.validate(map, requiredInputIds) fica como utilitário opcional, mas a resolução canônica de inputs ausentes é de eligibility (estado operacional). Validation cuida da QUALIDADE dos dados presentes, eligibility da PRESENÇA dos dados necessários.

**DEC-16 — eligibility não depende de library.** A regra de dependência (architecture.md §1.1) lista eligibility → domain/contracts, sem library. Então eligibility recebe requiredInputIds / availableInputIds / enabled como PARÂMETROS, já resolvidos pela orchestration. Applicability lê o perfil científico; eligibility só resolve estado a partir de resultados já interpretados.

**DEC-17 — Precedência de reporte: DISABLED → INELIGIBLE → MISSING_INPUTS → READY.** Não é máquina de estados linear (§10.2), mas: variante desabilitada nem se avalia; inelegível não adianta pedir medidas. Sexo incompatível (MatchClassification.LOW em sexMatch) → INELIGIBLE por restrição científica explícita (§4.1). Idade fora da faixa SEM restrição explícita → READY + WARNING (AGE_OUTSIDE_VALIDATED_RANGE), não INELIGIBLE (doc.md §23). PARTIAL em idade → READY + WARNING (AGE_PARTIAL_MATCH). POPULATION.LOW → WARNING (POPULATION_LOW_MATCH). CONTEXT.NOT_DOCUMENTED → WARNING (EVIDENCE_LIMITED).

**DEC-18 — Resolução dos estados extremos (especificacao_cientifica.md §12):** lista vazia ou nenhum elegível → NO_ELIGIBLE_METHOD; todas desabilitadas → NO_ENABLED_METHOD; candidatas existem mas nenhuma READY → NO_READY_METHOD (com os inputs faltantes, §12.3); senão → SUGGESTED.

**DEC-19 — Ordenação sem score (Abordagem A na V1).** Chave: classificação de idade (EXACT > PARTIAL > OUTSIDE_VALIDATED_RANGE > UNKNOWN), depois população (EXACT > HIGH > MODERATE > LOW > UNKNOWN). Empate completo → todas as empatadas são sugeridas (§11.3). variantId só ordena a lista (determinismo, §20) — nunca é preferência. Warnings não rebaixam (§9).

**DEC-20 — Estrutura do SuggestionResult:** candidateVariants = READY + MISSING_INPUTS; excludedVariants = INELIGIBLE + DISABLED (com motivos); suggestedVariants = subconjunto READY mais compatível. CandidateVariantSummary reutilizado nas três listas.

**DEC-21 — ConversionStatus é enum próprio.** DISABLED → INELIGIBLE → READY → SELECTED → CALCULATED (doc.md §30.1). Sem MISSING_INPUTS (conversão opera sobre PredictionResult já produzido). Não reusa CandidateStatus.

**DEC-22 — Ranking de conversão V1: Abordagem A (sem score, heurística determinística).** Ordem lógica (doc.md §16.4): (1) elegibilidade por inputType; (2) preferência profissional válida e elegível; (3) defaultConversionId (Siri via SystemConversionPolicy); (4) empate → indica todas. Nunca if (name == "Siri").

**DEC-23 — ConversionSuggestionEngine recebe um record neutro ConversionCandidateInput (em domain/contracts), não ConversionDefinition.** Mantém o engine dependente só de domain/contracts, sem importar library/conversions (espelha DEC-16). A conversão ConversionDefinition → ConversionCandidateInput fica a cargo da orchestration (Fase 11). O engine recebe o outputType do prediction como String, não o PredictionResult inteiro — evita depender de domain/calculation.

**DEC-24 — domain/config modela ProfessionalConfiguration + SystemConversionPolicy como records imutáveis.** Não depende de library: a resolução defaultConversionId → ConversionDefinition é da orchestration. Versionamento de config/policy fica adiado (entra com auditoria/orchestration, como DEC-10/11).

**DEC-25 — SystemConversionPolicy.platformDefault() retorna defaultConversionId = "siri" (DEC-3).** É dado de política operacional, não condição de seleção (§4) — por isso vive aqui e é coberto por golden test.

**DEC-26 — ConfigurationMode DEFAULT/CUSTOM é marcador.** enabledVariantIds/enabledConversionIds representam o estado efetivo; a resolução dos defaults da plataforma (quando mode = DEFAULT) é responsabilidade da camada que constrói o ProfessionalConfiguration (orchestration/persistence), não de domain/config.

**DEC-27 — Inventário oficial da V1 = 43 variantes.** O inventário antigo do `equation_family` listava 47. A V1 real contém 43: foram removidas `G-F4..G-F8` (DEC-1, falta de evidência) e `P-F15` (DAD-1, sem ficha fonte), e validadas `P-M15` e `P-M16`. A fonte de verdade para dados e coeficientes são os YAMLs em `library/` (Regra #1). O `equation_family` e o `doc.md §18/§33` foram atualizados para refletir as 43 variantes.

**DEC-28 — `SuggestionResult`/`ConversionSuggestionResult` sem `score`.** O modelo antigo no `doc.md` previa `score`, `reasonBreakdown` e `stage`. Isso foi superado pela DEC-19/22 e pelo Princípio Central da `especificacao_cientifica.md §2` (o motor não transforma validade científica em pontuação arbitrária). O sistema usa ordenação/rank por classificações. O `doc.md` (§3, §16.3, §16.5, §23, §29) foi reescrito para remover o score e consolidar a regra de que a ordenação nunca libera variante inelegível/inexequível.

**DEC-29 — `AuditSnapshot` unificado (sugestão + cálculo + conversão).** Alinha `doc.md §3/§28` com `architecture.md §6/§22`. O snapshot preserva o contexto, a decisão de sugestão (candidatos, status, escolha), o cálculo (equação, predição), a conversão (definição, resultado) e todas as versões de motores/regras/configuração. É estritamente append-only (create/read).

**DEC-30 — `AuditSnapshot` é record plano em `domain/audit`, usa `AssessmentContext` do `domain/contracts`.** Sem sub-records aninhados. Imutável via compact constructor (`inputsUsed` e `candidateVariantIds` copiados). Persistência append-only real é Fase 12; em domain, o record imutável garante a semântica create/read.

**DEC-31 — `AssessmentFlowOrchestrator` coordena o pipeline completo sem implementar regras científicas.** Recebe todos os dados já resolvidos via `AssessmentFlowInput` (não consulta banco). Converte `ConversionDefinition` → `ConversionCandidateInput` (DEC-23) e usa o outputType do prediction como String para o `ConversionSuggestionEngine` (DEC-23). A resolução de inputs do contexto (AGE, SEX, etc.) para o mapa de cálculo é responsabilidade da orchestration (input resolution, `architecture.md §3`).

**DEC-32 — Reescrita (não adaptação) da camada de integração do protótipo.** O paradigma mudou de métodos genéricos (NAVY/BIOIMPEDANCE/SKINFOLD) para 43 variantes científicas. `CalculationService` e `BodyFatCalculator` foram removidos; `AssessmentService`, `AssessmentController`, DTOs e mapper foram reescritos para delegar ao `AssessmentFlowOrchestrator`. Mantiveram-se intactos domain/, library/, orchestration/ (Fases 1–11) e a infraestrutura (auth/billing/branding/security).

**DEC-33 — Fórmulas legacy removidas; 43 variantes são a fonte.** As fórmulas antigas (Navy/Bioimpedance/Skinfold jp3/jp7/dw4 em `util/BodyFatCalculator`) foram descartadas. Os 43 YAMLs em `library/` já estão alinhados e são a fonte de verdade (Regra #1). Nenhuma fórmula nova será implementada sem as docs fornecidas pelo usuário.

**DEC-34 — `AuditSnapshot` persistido como payload JSON (CLOB) + colunas indexadas** (`assessment_id`, `equation_variant_id`, `conversion_id`, `created_at`). O JSON protege o histórico de mudanças de schema (doc.md §3). O repositório expõe só `save`/`find` (append-only de verdade, architecture.md §6).

**DEC-35 — Métricas derivadas e recomendações sobrevivem fora das 43 fórmulas.** `MetabolicCalculator` (IMC/BMR/TDEE/FFMI), classificação de %G e `RecommendationService` (dieta/treino) são aplicados DEPOIS do resultado final do orchestrator (via `MetabolicService`). São camada de produto, não fórmula científica de variante.

**DEC-36 — Colunas fixas de medida do `Assessment` param de ser escritas no Chunk 4; drop físico depois.** Com `ddl-auto: update` (que não remove colunas), os campos órfãos permanecem no banco até migration manual. O código Java para de ler/escrever eles no Chunk 4. Ecos de perfil (weight/height/age/gender) ainda são escritos para leitores legacy até estes migrarem para measurements (DEC-42b).

**DEC-37 — Persistência da auditoria em pacote próprio dentro de `persistence/`.** `AuditSnapshotEntity`, `AuditSnapshotRepository` e `AuditSnapshotStore` vivem em `persistence/entity`, `persistence/repository` e `persistence/` (pacotes novos da Fase 12 / Chunk 1), separados dos pacotes legacy `entity/` + `repository/` do protótipo.

**DEC-38 — `AssessmentFlowOrchestrator` deriva `variantOverride` internamente.** O campo `variantOverride` do `AssessmentFlowInput` torna-se depreciado: o orchestrator calcula `override = (selectedVariantId ∉ suggestedVariants)` durante a construção do `AuditSnapshot` (doc.md §24). O `AssessmentFlowService` passa `false` no input; o reason continua sendo input.

**DEC-39 — DTOs do fluxo novo nascem com nome próprio; legacy morre no Chunk 4.** `AssessmentFlowRequestDTO`/`AssessmentFlowResponseDTO`/`CalculationFlowResponseDTO` conviveram com `CalculateRequestDTO`/`AssessmentRequestDTO`/`CalculationResultDTO` legacy até o Chunk 3 virar o controller e o Chunk 4 deletar os legacy — assim cada chunk fechou compile-green sem refactor cascata.

**DEC-40 — Chunk 3: bridge calculate-only; preview adiado.** (a) `AssessmentFlowService.calculate` persiste Assessment (só measurements; colunas fixas já nascem null), executa o orchestrator, anexa audit e mapeia DTOs. (b) `variantOverride` derivado pelo orchestrator (DEC-38). (c) Preview (doc.md §9.1/§17) adiado: exigirá `assess()` aditivo no orchestrator, com DEC própria. (d) AGE/HEIGHT injetados nos resolved inputs pelo service (DEC-31) e NÃO persistidos como measurement. (e) `CandidateVariantSummary.missingInputs` → DTO `missingInputIds` (ajuste de nome entre record e DTO).

**DEC-41 — Teste do store é unitário com fake in-memory do repositório.** No Spring Boot 4 os test-slices foram modularizados e o `@DataJpaTest` mudou de pacote; em vez de depender disso, o `AuditSnapshotStoreTest` usa um fake in-memory (a interface declara só 3 métodos) e valida serialização JSON + contrato append/read. O round-trip real de banco (CLOB H2/Postgres) é coberto pelo teste de contexto (Chunk 5).

**DEC-42 — Mapeamento legacy→fluxo declarativo; persistência única da Assessment.** (a) O mapeamento campo do DTO legacy → inputId canônico vive num único `MEASUREMENT_MAP` (AssessmentService); nova medida = 1 linha. (b) Quem persiste Assessment + measurements é o `AssessmentFlowService.calculate` (Chunk 3); o `create()` apenas enriquece a linha persistida com o resultado derivado (DEC-35) e com ecos de perfil para leitores legacy. (c) `/calculate` exige cliente (perfil dirige aplicabilidade, doc.md §4/§8) e aceita `AssessmentFlowRequestDTO`; `AssessmentFlowController` removido por duplicar `/api/assessments/calculate`.

**DEC-43 — Wiring Spring do motor vive num único `@Configuration`.** As classes de domain/, library/ e orchestration/ permanecem framework-agnostic (sem @Component): `config/AssessmentEngineConfiguration` instancia registries, engines e o AssessmentFlowOrchestrator num único ponto, espelhando a composição do golden test. Surgiu quando o teste de contexto passou a subir (Chunk 5): injeção por construtor exige beans, e os golden tests instanciavam tudo manualmente. O teste de contexto usa `@MockitoBean` para `AssessmentFlowOrchestrator` e `TwoFactorEncryptionService`.

**DEC-44 — Classificação de %G em tabela declarativa (substitui BodyFatInterpreter).** Os thresholds de classificação (ACE: Gordura essencial / Atleta / Saudável / Acima da média / Obesidade) vivem como `List<BodyFatThreshold>` no `MetabolicService` em vez de if/else aninhado. Novo limite = 1 linha no array; a lógica de busca é genérica. O `BodyFatInterpreter` legado foi deletado no Chunk 4.

**DEC-45 — Limpeza completa dos DTOs legacy de método.** Os DTOs `NavyDataDTO`,
`BioimpedanceDataDTO`, `SkinfoldDataDTO`, `MethodDetailsDTO` e `MethodDetailItem`
foram removidos junto com os blocos legacy do `AssessmentMapper`. O
`AssessmentResponseDTO` agora expõe `measurements: Map<inputId, valor>` — o formato
que as 43 variantes científicas realmente usam. O campo `methodDetails` do
`AssessmentResultDTO` (que nunca era preenchido desde o Chunk 4) também foi removido.

**DEC-46 — Teste de contexto sem mocks escondendo wiring.** Os `@MockitoBean` de
`AssessmentFlowOrchestrator` e `TwoFactorEncryptionService` foram ponte temporária no
Chunk 5. Como `@MockitoBean` só vale no teste, o wiring real é garantido por
`config/AssessmentEngineConfiguration` (DEC-43); sem ele o app real não sobe
(UnsatisfiedDependencyException), mesmo com teste verde. Os mocks devem ser removidos
assim que o contexto subir com beans reais. Teste de contexto com mock de engine =
verde enganoso.

**DEC-47 — `AuditSnapshotEntity.payload` usa `TEXT` em vez de `@Lob` (DEC-7 estendido).**
O `@Lob` com `String` no PostgreSQL gera coluna tipo OID (large object por referência),
que não suporta queries SQL diretas (LENGTH, ::json, etc.) e exige `lo_get()`. Como
`payload` é JSON textual, o tipo correto é `TEXT` (sem limite prático de 1GB no PG).
Mesma causa-raiz do DEC-7 (BrandingProfile.logoData = BYTEA). Ambos foram corrigidos
com `columnDefinition` explícito.

**DEC-48 — `/calculate` enriquece Assessment com resultado + ecos (DEC-35/42b).**
O `AssessmentFlowService.calculate()` agora chama `MetabolicService.buildResult()`
após o orchestrator e persiste o `AssessmentResult` + ecos de perfil (weight/height/
age/gender) na linha de Assessment já criada. Fecha o gap do Chunk 4: as colunas
`result_*` e os ecos voltam a ser preenchidos, permitindo que os leitores legados
(ClientProgressService/DashboardService) funcionem sem adaptação imediata. As
colunas fixas de MEDIDA (dobras/circ/bio) continuam não escritas (DEC-36).

**DEC-49 — Migration SQL manual para colunas do AssessmentResult (embedded).**
O `ddl-auto: update` não adiciona de forma confiável colunas novas de `@Embedded`
em tabelas que já existem no banco. Para tabelas criadas antes da Fase 12 Chunk 4,
a migration precisa ser feita manualmente via `ALTER TABLE ... ADD COLUMN IF NOT EXISTS`.
Aplicar no Neon (ou qualquer PostgreSQL de produção) antes de executar o /calculate
pela primeira vez contra essa tabela. Em ambiente de dev com `ddl-auto: create-drop`
(testes), o Hibernate cria do zero — sem problema.

**DEC-50 — Colunas do AssessmentResult todas com prefixo `result_` via @Column explícito.**
Os 9 campos derivados (imc, bodyFat, leanMass, fatMass, ffmi, bmr, tdee,
targetCalories, bodyFatLevel) não tinham @Column e o Hibernate os nomeou sem prefixo
(imc, body_fat, ...), enquanto os 5 científicos usavam result_*. A inconsistência gerou
colunas órfãs result_* (migration manual) que nada escrevia. Fix: @Column explícito
com prefixo result_ nos 9 derivados + migration de cópia/drop no Neon. Lição: em
@Embeddable, nomear TODAS as colunas explicitamente — nunca confiar no naming implícito.

**DEC-51 — Handler específico para HttpMessageNotReadableException.**
Adicionado @ExceptionHandler no GlobalExceptionHandler para capturar erros
de parsing JSON (Jackson) ANTES de cair no RuntimeException genérico.
Retorna 400 Bad Request com mensagem clara ("Corpo da requisição inválido")
em vez de 500 genérico. Cobre: JSON malformado, encoding UTF-8 corrompido
(ex: Git Bash do Windows passando Windows-1252 para curl.exe nativo),
tipos incompatíveis. O handler específico tem precedência sobre o genérico
pela hierarquia de exceções do Spring.

**DEC-52 — Fase 13: a tela de avaliação é um painel de estado sobre uma Assessment em rascunho.**
O front passa a operar sobre uma Assessment `DRAFT` criada ao abrir a tela; medidas e contexto são gravados por chamada (autosave), e cada gravação devolve o painel recalculado. `POST /api/assessments/{id}/calculate` (sem efeito colateral, DEC-58) e `POST /api/assessments/{id}/finalize` substituem o `POST /calculate` atual, que criava e gravava a Assessment; ambos operam sobre a Assessment existente e as medidas vêm do banco, não do corpo. **Reverte DEC-40(c)** (preview adiado) e **altera DEC-42(c)**. Contrato em `doc.md` §17.8.

**DEC-53 — Guard de seleção no back: só executa o que está em condição de execução.**
Hoje o `AssessmentFlowOrchestrator.execute()` calcula o estado das variantes e depois executa a variante escolhida sem consultá-lo: `INELIGIBLE` e `DISABLED` executam, e `MISSING_INPUTS` só falha porque o `EquationEvaluator` lança `IllegalArgumentException` (efeito colateral, não regra). Regra nova: a variante escolhida deve estar `READY`; a conversão escolhida deve estar habilitada e elegível. Violação lança `SelectionNotAllowedException` (domain), mapeada para 422 `SELECTION_NOT_ALLOWED`, sem gravar nada; vale tanto no calcular quanto no finalizar. Fontes: `especificacao_cientifica.md` §14; `architecture.md` §10.1 (não transformar `INELIGIBLE` em `READY`). Exige extrair `assess()` (etapas 1–9) de `execute()` — mudança aditiva no orchestrator, prevista no DEC-40(c).

**DEC-54 — `overrideReason` obrigatório em dois casos.**
Para variantes: obrigatório quando `override = true` (fora do conjunto sugerido, DEC-38) e existe ao menos uma sugerida, ou quando a variante é `READY` com `warnings`. Sem sugestão não há do que divergir. Para conversão: sempre opcional (`doc.md` §24.1). Validada só na finalização (calcular é exploração e não exige motivo, DEC-58), como verificação separada do guard do DEC-53; erro 422 `REASON_REQUIRED`.

**DEC-55 — Campos exibidos = união dos `requiredInputs` das variantes candidatas.**
Candidatas = `READY` + `MISSING_INPUTS`, sem `AGE` e `HEIGHT`; campo com valor gravado nunca some (`doc.md` §10.1). Corrige o `requiredInputUnion` do `AssessmentFlowService`, que hoje une todas as habilitadas sem considerar sexo/aplicabilidade. Sem dependência circular: `INELIGIBLE` não depende de medidas (DEC-17).

**DEC-56 — Autosave por medida.**
Upsert por (`assessmentId`, `inputId`) respeitando a unique constraint do DEC-9; `value = null` remove. A gravação passa pelo `MeasurementValidator`: `INVALID_VALUE` e `IMPOSSIBLE_VALUE` recusam (422 `MEASUREMENT_INVALID`); demais tipos gravam com aviso — confirmar severidade no validador ao implementar. A resposta traz o painel recalculado (`doc.md` §13, passos 9–11).

**DEC-57 — Origem da medida (`source`).**
Coluna `source` (`MANUAL` | `AVALIACAO_ANTERIOR`) em `assessment_measurements`, conforme `doc.md` §11. Migration manual (lição do DEC-49: `ddl-auto: update` não é confiável), default `MANUAL`. Valor anterior via `GET /api/clients/{id}/previous-measurements`.

**DEC-58 — Rascunho editável; Calcular não grava; Salvar é manual e finaliza.**
Medidas e contexto ficam editáveis enquanto a avaliação é `DRAFT` (autosave, DEC-56). "Calcular" é exploração: roda o pipeline sobre as medidas gravadas e devolve o resultado sem gravar resultado nem auditoria e sem exigir motivo; pode ser repetido com outras fórmulas e conversões. "Salvar avaliação" é a ação explícita do profissional (equivale ao antigo "Salvar"): o back refaz o cálculo a partir das medidas gravadas (nunca aceita resultado vindo do cliente), aplica o guard (DEC-53) e a regra de motivo (DEC-54) e, numa transação, appenda o `AuditSnapshot`, grava o resultado + ecos de perfil (DEC-48) e marca `FINALIZED`. Depois disso a avaliação fica travada (409 `ASSESSMENT_LOCKED`); corrigir = nova avaliação (reabertura fica fora da V1). Status: `DRAFT` | `FINALIZED`; o `CALCULATED` cogitado não existe. Alinha com `doc.md` §13, passos 21–22 (resultado armazenado e auditoria criados ao final do fluxo). Custo aceito: cálculos exploratórios não deixam rastro; a auditoria registra a decisão que virou resultado (sugerida × escolhida, override, motivo, entradas). **Altera DEC-40(a) e DEC-48**: a gravação do resultado e da auditoria passa do calcular para o finalizar.

**DEC-59 — Catálogos somente-leitura + `group` nos inputs.**
Endpoints `GET /api/catalog/inputs|variants|conversions`. Novo campo `group` (`BASIC` | `SKINFOLD` | `CIRCUMFERENCE`) em `input-types.yaml` e em `InputTypeDefinition`, para o front não deduzir grupo pelo prefixo do id. **Exceção controlada à regra #1 das "Regras do jogo"**: mexe em `library/measurements` (não nos 43 YAMLs de fórmula); o `InputTypeCatalogGoldenTest` (15 inputs) é atualizado na mesma entrega.

**DEC-60 — Textos de `reasons`, `warnings` e issues vêm do back, em português.**
Os builders já emitem texto pt-BR (ex.: "Sexo compatível com a variante."). Na V1 o front os exibe sem traduzir; o i18next do front não cobre esses textos. "READY com alerta" = `status = READY` com `warnings` não vazio, sem campo novo.

**DEC-61 — Rascunhos ficam fora dos leitores legados.**
`ClientProgressService`, `DashboardService`, `findByClient` e `findPaged` leem `Assessment` esperando resultado (DEC-48). Com rascunhos sem `result_*` eles precisam filtrar por status. Coluna `status` (`DRAFT` | `FINALIZED`) com migration manual (`NOT NULL DEFAULT 'FINALIZED'` para as linhas existentes, que são avaliações já salvas).

**DEC-62 — Sem restrição por plano na Fase 13: salvamento liberado para todos.**
No fluxo novo, rascunho/autosave e histórico ficam liberados para todos os planos; a feature `history` (`PlanFeature.HISTORY`) deixa de ser exigida. O front antigo só permitia salvar com `history` (`canSaveHistory`), e no back só existe a definição dos planos, sem enforcement encontrado. Se o gating voltar, entra num único ponto (service/controller), sem tocar no motor. Decisão do dono do produto, revisável.

---

## 1. Ordem de implementação

A ordem segue a árvore de dependência definida em `architecture.md` §1.1 (`orchestration → domain → library → persistence`), do módulo com zero dependência até o que depende de tudo.

Cada fase fecha com os golden tests correspondentes (`architecture.md` §19) como critério de "pronto" — não "parece certo".

| Fase | Módulo | Documentos a ler (só as seções listadas) | Status |
| ---- | ------ | ---------------------------------------- | ------ |
| 1    | `domain/contracts` | `architecture.md` §1.3 | ✅ Feita |
| 2a   | `library.scientificrules` — modelo Java | `schema_cientifico.md` (inteiro) | ✅ Feita |
| 2b   | Converter as fichas de Markdown → YAML real | `equation_family_especificacao_formal.md` (inteiro) · fichas como dado real | ✅ Feita (43 YAMLs + 2 conversões; ver DAD-1/DEC-1) |
| MIG  | Migrar Java das Fases 1+2a para o projeto + rename de pacote | — | ✅ Feita |
| 2c   | `ScientificRuleRegistry`, `ConversionDefinitionRegistry`, `InputTypeCatalog`, `EquationVariantRegistry` carregando YAML | `architecture.md` §1.4, §4.1–4.4 | ✅ Feita |
| 2d   | Golden tests de carregamento (43 científicas + 2 conversões + 15 inputs) | `architecture.md` §19 | ✅ Feita (19 testes verdes) |
| 3    | `domain/calculation` + `domain/conversion` + 43 FormulaDefinitions em YAML | `architecture.md` §3, §13–15 · `doc.md` §21 · golden tests §19.1 | ✅ Feita (motor + conversão + 43 FormulaDefinitions + EquationLibraryGoldenTest) |
| —    | **Checkpoint: redesenho de `entity.Assessment`** (ver §1.1) — só depois da Fase 3 provar que o mapa `inputId → valor` funciona de ponta a ponta | — | ✅ Feito (DEC-9/10/11/12) |
| 4 | `domain/validation` | `architecture.md` §2, §2.1–2.3 | ✅ Feita (MeasurementValidator + 7 golden tests; DEC-13/14) |
| 5 | `domain/applicability` | `architecture.md` §9, §9.1 · `especificacao_cientifica.md` §4–5 · `doc.md` §8, §8.1 | ✅ Feita (ApplicabilityEngine + EvidenceSummaryBuilder + 22 golden tests) |
| 6 | `domain/eligibility` | `architecture.md` §10, §10.1–10.2 · `especificacao_cientifica.md` §9 · `doc.md` §9.2, §12 | ✅ Feita (EligibilityResolver + 11 golden tests; DEC-15/16/17) |
| 7 | `domain/suggestion` (+ criteria + explanation) | `architecture.md` §11, §11.1–11.3, §16–17 · `especificacao_cientifica.md` §11–14 · `doc.md` §9.1, §9.3, §10–11, §16 · golden tests §19.2–19.3 | ✅ Feita (SuggestionEngine + CompatibilityRanker + SuggestionExplanationBuilder + 12 golden tests; DEC-18/19/20) |
| 8 | `domain/conversion-suggestion` | `architecture.md` §12, §12.1–12.2 · `doc.md` §6, §7.3, §9.4, §19 (estrutura, não os coeficientes) | ✅ Feita (ConversionSuggestionEngine + ConversionSuggestionExplanationBuilder + 7 golden tests; DEC-21/22/23) |
| 9 | `domain/config` | `architecture.md` §7, §7.1–7.4 · `doc.md` §5–6 | ✅ Feita (ConfigurationMode + ProfessionalConfiguration + SystemConversionPolicy + 7 golden tests; DEC-24/25/26) |
| 10 | `domain/audit` | `architecture.md` §6, §22 · `doc.md` §3 (regra de auditoria) | ✅ Feita (AuditSnapshot + 8 golden tests; DEC-29/30) |
| 11 | `orchestration/assessment-flow` | `architecture.md` §23 · `doc.md` §13–15 | ✅ Feita (AssessmentFlowOrchestrator + AssessmentFlowInput + AssessmentFlowResult + 1 golden test; DEC-31) |
| 12 | `persistence` (integração: DTOs/Controller/Service refeitos + persistência append-only do AuditSnapshot + reabilitar BioimpedanceApplicationTests) | Sem seção fixa · controle de chunks no §6 | ✅ Concluída (Chunks 1–5; DEC-32–44; 101 tests verdes) |
| 13 | Front da tela de avaliação + ajustes de back (guard de seleção, rascunho/autosave, catálogos) | `doc.md` §10, §11, §13, §15, §17, §24 · `especificacao_cientifica.md` §14 · `architecture.md` §4, §10, §23 | 🔜 Planejada (DEC-52–62; controle no §7) |

**Transversal (relevante em toda fase, reler quando bater dúvida):**
`architecture.md` §0 (regra de ouro), §4 (nenhuma seleção por nome — nunca `if variantId == "JP7"`), §5 (versionamento), §8 (separação de responsabilidades), §24 (regra de evolução), **§25 (checklist de PR — rodar ao final de toda fase)**. `doc.md` §33 (modelo mental definitivo — bom resumo pra realinhar entre fases).

### 1.1 Por que o checkpoint entrou no meio da Fase 3

`entity.Assessment` tinha colunas fixas (`biceps`, `chest`, ... `thigh`) que não comportavam as variantes (usam combinações diferentes de dobra/circunferência/massa). Virou um mapa `inputId → valor`. Redesenhá-lo **antes** de ver `domain.calculation` funcionando de ponta a ponta seria decidir o formato no escuro — melhor provar primeiro (com dado solto, sem banco) que o mapa aguenta o cálculo, só depois migrar a entidade de verdade. Evitou redesenhar duas vezes.

Duas coisas que **não são** a mesma coisa e não foram fundidas:

- `constants.ActivityLevel` (SEDENTARY..VERY_ACTIVE, usado no TDEE) **≠** `TrainingLevel` do schema científico (SEDENTARY..ELITE, usado em aplicabilidade de fórmula).
- `service.RecommendationService` (dieta/treino) **≠** `SuggestionEngine` (qual fórmula usar). Concerns diferentes; o primeiro continuou quase como está (DEC-35).

---

## 2. Como montar o briefing de cada fase

Antes de abrir uma fase com a IA de implementação, montar um briefing curto (não colar os documentos inteiros):

```
1. Nome da fase e uma frase do que ela faz
2. As seções listadas na tabela acima (coladas, não o doc inteiro)
3. A regra #1 (hierarquia de fontes) — sempre incluir
4. O checklist §25 relevante àquela fase (só os itens aplicáveis)
5. O golden test correspondente, se existir
```

Isso resolve o problema de perda de contexto: a IA de implementação nunca precisa segurar os 5 documentos inteiros na cabeça, só o recorte da fase corrente.

---

## 3. Ordem sugerida de execução prática

- [x] Fases 1–2a primeiro — contrato + modelo da biblioteca.
- [x] 2b — 43 YAMLs científicos + 2 conversões salvos em `resources/library/`.
- [x] MIG — migração do Java + rename de pacote.
- [x] 2c — registries carregando YAML (ScientificRuleRegistry, ConversionDefinitionRegistry, InputTypeCatalog, EquationVariantRegistry).
- [x] 2d — golden tests de carregamento (19 testes verdes).
- [x] Fase 3 — cálculo/conversão:
  - [x] Motor de cálculo (EquationEvaluator + FormulaTemplate + FormulaDefinition + EquationVariantRegistry)
  - [x] Conversor de densidade (DensityToFatConverter)
  - [x] CalculationGoldenTest (2 testes: FALK4 direto + P-M16 → Siri)
  - [x] Lotes 1–6 de FormulaDefinition YAMLs (43/43 salvos)
  - [x] EquationLibraryGoldenTest: cross-check dos 43 YAMLs matemáticos contra os 43 científicos (4 testes)
- [x] Checkpoint — redesenho de `entity.Assessment` (DEC-9/10/11/12).
- [x] Fase 4 (validação) — MeasurementValidator + 7 golden tests (DEC-13/14).
- [x] Fase 5 (applicability) — ApplicabilityEngine + EvidenceSummaryBuilder + 22 golden tests.
- [x] Fase 6 (eligibility) — EligibilityResolver + 11 golden tests (DEC-15/16/17).
- [x] Fase 7 (suggestion) — SuggestionEngine + CompatibilityRanker + SuggestionExplanationBuilder + 12 golden tests (DEC-18/19/20).
- [x] Fase 8 (conversion-suggestion) — ConversionSuggestionEngine + ConversionSuggestionExplanationBuilder + 7 golden tests (DEC-21/22/23).
- [x] Fase 9 (config) — ConfigurationMode + ProfessionalConfiguration + SystemConversionPolicy + 7 golden tests (DEC-24/25/26).
- [x] Fase 10 (audit) — AuditSnapshot append-only + 8 golden tests (DEC-29/30).
- [x] Fase 11 (orchestration) — AssessmentFlowOrchestrator + AssessmentFlowInput + AssessmentFlowResult + 1 golden test (DEC-31).
- [x] **Fase 12 (persistence/integração) — CONCLUÍDA** (controle de chunks no §6):
  - [x] Chunk 1 — AuditSnapshot persistence (entity + repository append-only + store; DEC-34/37)
  - [x] Chunk 2 — DTOs novos + ProfessionalConfigurationResolver (DEC-39)
  - [x] Chunk 3 — ponte service/controller → orchestrator + salvar audit via store (DEC-38/40/41)
  - [x] Chunk 4 — remoção do legado (CalculationService, BodyFatCalculator, BodyFatInterpreter) + AssessmentService/AssessmentResult novos + MetabolicService (DEC-32/33/35/36/42/44)
  - [x] Chunk 5 — application-test.yml + BioimpedanceApplicationTests reabilitado + AssessmentEngineConfiguration (DEC-7/43)

---

## 4. Definição de pronto, por fase

Não é "o código roda". É:

- [ ] Passa no golden test da responsabilidade (quando existir, `architecture.md` §19)
- [ ] Passa no checklist de PR aplicável (`architecture.md` §25)
- [ ] Nenhum identificador de variante/conversão hardcoded fora de `library`/fixtures (`architecture.md` §4.4)
- [ ] Nenhuma dependência na direção errada (`architecture.md` §1.1)
- [ ] Os dados usados vieram das fichas, não dos documentos de exemplo

---

## 5. Inventário real de YAMLs (V1)

### scientific-rules/ (43/43 ✅)

| Família | YAMLs presentes | Ausentes / observações |
| ------- | --------------- | ---------------------- |
| Jackson & Pollock | JP3-M, JP3-F, JP7-M, JP7-F (4) | — |
| Guedes | G-M3..G-M8, G-F3 (7) | G-F4..G-F8 fora da V1 (DEC-1) |
| Petroski | P-M1..P-M16 (16), P-F1..P-F14, P-F16 (15) | P-F15 sem ficha fonte (DAD-1) |
| Faulkner | FALK4 (1) | sexo MALE (DEC-2) |
| **Total** | **43** | + 2 conversões (`siri.yaml`, `brozek.yaml`) |

### equations/ (43/43 ✅)

| Lote | Variantes | Template | Status |
| ---- | --------- | -------- | ------ |
| 1 | G-M3..G-M8 (6) | LOG10_SUM | ✅ |
| 2 | G-F3, JP3-M, JP7-M, JP3-F, JP7-F (5) | LOG10_SUM / QUADRATIC_SUM_WITH_AGE | ✅ |
| 3 | P-M1, P-M3, P-M5, P-M7, P-M9, P-M11, P-M13, P-M15 (8) | QUADRATIC_SUM_WITH_AGE | ✅ |
| 4 | P-M2, P-M4, P-M6, P-M8, P-M10, P-M12, P-M14 (7) | QUADRATIC_SUM_WITH_AGE_AND_CIRC | ✅ |
| 5 | P-F1, P-F4, P-F7, P-F9, P-F10, P-F13, P-F14 (7) | QUADRATIC_SUM_WITH_AGE_MASS_HEIGHT | ✅ |
| 6 | P-F2, P-F3, P-F5, P-F6, P-F8, P-F11, P-F12, P-F16 (8) | LOG10_SUM_WITH_AGE / LOG10_SUM_WITH_AGE_AND_CIRC | ✅ |
| — | FALK4 (1) | LINEAR_SUM | ✅ |
| **Total** | **43** | | **43/43 completos** |

### conversions/ (2/2 ✅)

| id | inputType | outputType | expression |
| -- | --------- | ---------- | ---------- |
| siri | BODY_DENSITY | BODY_FAT_PERCENTAGE | (4.95 / D − 4.50) × 100 |
| brozek | BODY_DENSITY | BODY_FAT_PERCENTAGE | (4.57 / D − 4.142) × 100 |

### measurements/ (15 inputIds ✅)

AGE, BODY_MASS, HEIGHT, SKINFOLD_SUBSCAPULAR, SKINFOLD_TRICEPS, SKINFOLD_BICEPS, SKINFOLD_PECTORAL, SKINFOLD_AXILLARY_MID, SKINFOLD_SUPRAILIAC, SKINFOLD_ABDOMEN, SKINFOLD_THIGH, SKINFOLD_MEDIAL_CALF, CIRCUMFERENCE_FOREARM, CIRCUMFERENCE_ABDOMEN, CIRCUMFERENCE_THIGH

---

## 6. Checklist de Controle — Fase 12 (registro histórico da operação)

Controle operacional que guiou a Fase 12, chunk a chunk (DEC-38). Cada chunk fechou com compile/teste verde. Mantido como registro histórico do que foi feito.

### Pré-voo (confirmado)

- [x] 43 fórmulas alinhadas nos YAMLs (`library/equations` + `library/scientific-rules`)
- [x] `domain/`, `library/`, `orchestration/` prontos e testados (98 golden tests)
- [x] Decisão: substituir o legado, não adaptar (DEC-32)

### Chunk 1 — Persistência do AuditSnapshot ✅

- [x] `persistence/entity/AuditSnapshotEntity.java` (imutável, sem setters, payload CLOB + colunas indexadas)
- [x] `persistence/repository/AuditSnapshotRepository.java` (extends `Repository`, só save/find — append-only)
- [x] `persistence/AuditSnapshotStore.java` (append + read, JSON via ObjectMapper)
- [x] Higiene: duplicados legacy apagados, `AuditGoldenTest.java` renomeado, javadoc corrigido (DEC-37)
- 📖 Fontes: `architecture.md §6/§22` · `doc.md §3/§28`

### Chunk 2 — DTOs novos + resolver de configuração ✅

- [x] `AssessmentFlowRequestDTO` (clientId, date, contexto, measurements Map, seleções + reasons)
- [x] DTOs de response: `VariantStatusDTO`, `AssessmentFlowResponseDTO`, `PredictionDTO`, `ConversionSuggestionDTO`, `CalculationFlowResponseDTO`
- [x] `ProfessionalConfigurationResolver` (V1: DEFAULT = biblioteca inteira, sem preferência)
- [x] DEC-39: DTOs novos com nome próprio; legacy morre no Chunk 4
- 📖 Fontes: `doc.md §3/§10/§11` · `architecture.md §7`

### Chunk 3 — Ponte service/controller → orchestrator ✅

- [x] `AssessmentFlowService` (client → context → config → orchestrator → audit → DTOs)
- [x] Override derivado no orchestrator (DEC-38)
- [x] `AuditSnapshotStoreTest` (unitário, fake in-memory; DEC-41)
- [x] DEC-40: bridge calculate-only; preview adiado; AGE/HEIGHT não persistidos como measurement
- 📖 Fontes: `architecture.md §23` · `doc.md §13–15`

### Chunk 4 — Remoção do legado ✅

- [x] Deletados `CalculationService`, `BodyFatCalculator`, `BodyFatInterpreter` (DEC-33)
- [x] `AssessmentFlowController` deletado (duplicava `/calculate`, DEC-42c)
- [x] `AssessmentService` reescrito: `MEASUREMENT_MAP` declarativo, delega ao fluxo (DEC-42)
- [x] `AssessmentResult` novo: campos científicos + derivados (DEC-35)
- [x] `MetabolicService` novo: métricas derivadas pós-resultado + classificação de %G em tabela (DEC-35/44)
- [x] `/calculate` aceita `AssessmentFlowRequestDTO` e exige cliente (DEC-42c)
- ⚠️ Gap conhecido: response do /calculate não expõe recomendação completa; ecos de perfil ainda escritos até leitores migrarem (DEC-36/42b)

### Chunk 5 — Teste de contexto + limpeza final ✅

### Chunk 5 — Teste de contexto + limpeza final ✅

- [x] `application-test.yml` (H2 modo PostgreSQL + placeholders dummy: jwt, encryption, stripe, ssl)
- [x] `BrandingProfile.logoData` → `columnDefinition = "BYTEA"` (resolve BLOB/H2, DEC-7)
- [x] `config/AssessmentEngineConfiguration` — wiring Spring do motor num único ponto (DEC-43)
- [x] `BioimpedanceApplicationTests` reabilitado (`@ActiveProfiles("test")` + `@MockitoBean` orchestrator/encryption)
- [x] `mvn clean test` verde completo: **101 tests run, 0 failures, 0 errors, 0 skipped**
- [x] Limpeza completa: DTOs legacy de método (Navy/Bio/Skinfold) + detalhes (MethodDetails/Item) removidos; `AssessmentResponseDTO.measurements` agora é `Map<inputId, valor>` (DEC-45)

### Regras do jogo (valeram em todo chunk)

1. **Não tocar** em `domain/`, `library/`, `orchestration/` nem nos 43 YAMLs
2. **Não inventar** coeficiente/fórmula — faltou dado, pede a doc (DEC-33)
3. **Nunca** `if (variantId == "JP7")` / `if (name == "Siri")` fora de `library`
4. Todo chunk fechou com **compile/teste verde** antes do próximo
5. Dúvida técnica fora da lista → **consulta a doc** na hora


---

## 7. Checklist de Controle — Fase 13 (front da tela de avaliação + ajustes de back)

Ordem: back primeiro (B1 → B5), porque o front depende do contrato de `doc.md` §17.8. O front (F1) pode começar quando B2 e B3 estiverem com contrato fechado. Cada chunk fecha com compile/teste verde.

### Pré-voo

- [x] DEC-58 fechada: rascunho editável; calcular não grava; salvar manual finaliza e trava
- [x] DEC-62 fechada: sem restrição por plano na Fase 13
- [x] `doc.md` §10.1 e §17 reescritos; §24 com a regra de elegibilidade
- [ ] Levantar consumidores do `AssessmentResponseDTO` no front que usam o modelo antigo (`method`, `methodDetails`, `result.bodyFat`): ~84 ocorrências em `front.txt` — PDF, histórico, dashboard, `ClientCharts`, `AssessmentViewModal`

### Chunk B1 — Guard de seleção (DEC-53/54) `orchestration` + `domain`

- [ ] Extrair `assess()` (etapas 1–9) de `AssessmentFlowOrchestrator.execute()`; `execute()` passa a reutilizá-lo
- [ ] `SelectionNotAllowedException` (domain) com `variantId`, `status` e `reasons`
- [ ] Recusar variante que não esteja `READY`, e conversão desabilitada ou inelegível
- [ ] Regra `REASON_REQUIRED` (fora do sugerido com sugestão não vazia; `READY` com `warnings`) como verificação separada, chamada só na finalização (DEC-54/58)
- [ ] `GlobalExceptionHandler`: 422 com `code`, `variantId`, `status`, `reasons`
- [ ] Golden tests: `INELIGIBLE` recusada · `DISABLED` recusada · `MISSING_INPUTS` recusada com erro de domínio (não `IllegalArgumentException` do evaluator) · `READY+WARNING` sem motivo recusada na finalização · override sem motivo recusado na finalização · conversão desabilitada recusada · recusa não persiste Assessment nem auditoria
- 📖 Fontes: `especificacao_cientifica.md` §14 · `architecture.md` §10.1, §23 · `doc.md` §24

### Chunk B2 — Catálogos e valores anteriores (DEC-59/57) `library` + `controller`

- [ ] `group` em `input-types.yaml` + `InputTypeDefinition`; `InputTypeCatalogGoldenTest` atualizado
- [ ] `GET /api/catalog/inputs`, `/variants`, `/conversions` (DTOs próprios, sem vazar tipos de domínio)
- [ ] `GET /api/clients/{id}/previous-measurements` (última medida por input em avaliações anteriores, excluindo a atual; valida ownership)
- [ ] Cache HTTP nos catálogos (imutáveis por versão da biblioteca)

### Chunk B3 — Rascunho, autosave e painel (DEC-52/55/56/57/61) `service` + `controller` + `entity`

- [ ] `Assessment.status` (`DRAFT` | `FINALIZED`) com migration manual (`DEFAULT 'FINALIZED'` para linhas existentes)
- [ ] `assessment_measurements.source` com migration manual (`DEFAULT 'MANUAL'`)
- [ ] `AssessmentDraftService` (sem lógica no controller): criar rascunho, atualizar contexto, upsert/remoção de medida, montar painel
- [ ] Endpoints `POST /draft`, `PATCH /{id}/context`, `PUT /{id}/measurements/{inputId}`, `GET /{id}/panel`
- [ ] Painel com `requiredInputUnion` filtrada (DEC-55), perfil travado, contexto e medidas com `source`
- [ ] Autosave passa pelo `MeasurementValidator` (DEC-56); confirmar severidade dos tipos de issue
- [ ] Leitores legados e listagens ignoram `DRAFT` (DEC-61): `ClientProgressService`, `DashboardService`, `findByClient`, `findPaged`
- [ ] Testes: upsert e remoção · ownership · painel muda com sexo/contexto e com medidas (`MISSING_INPUTS` → `READY`) · rascunhos fora do dashboard

### Chunk B4 — Calcular sem efeito colateral (DEC-52/58) `service` + `controller`

- [ ] `POST /api/assessments/{id}/calculate` carrega a Assessment (com ownership), usa as medidas do banco e roda o pipeline com o guard (B1); não grava Assessment, resultado nem auditoria; `auditId = null` no response
- [ ] Refatorar `AssessmentFlowService.calculate`: extrair o pipeline comum e tirar dele `persistAssessment`, `setResult`, ecos de perfil e `auditSnapshotStore.append`, que passam para a finalização (B5)
- [ ] Remover o `POST /api/assessments/calculate` atual
- [ ] Testes: calcular não altera Assessment nem a tabela de auditoria · recusa 422 · trocar de conversão recalcula sem gravar · rascunho de outro profissional → 404

### Chunk B5 — Salvar / finalizar (DEC-58) `service` + `controller`

- [ ] `POST /api/assessments/{id}/finalize` (variante, conversão, motivos, objetivo nutricional): refaz o pipeline a partir das medidas gravadas, nunca aceita resultado vindo do cliente
- [ ] Aplica o guard (B1) e `REASON_REQUIRED`; em sucesso, numa transação: appenda o `AuditSnapshot`, grava `AssessmentResult` + ecos de perfil (DEC-48/42b) e marca `FINALIZED`
- [ ] Medidas, contexto e nova finalização em avaliação `FINALIZED` → 409 `ASSESSMENT_LOCKED`
- [ ] Histórico, dashboard e gráficos leem só `FINALIZED` (DEC-61)
- [ ] Definir se `inputsUsed` do `AuditSnapshot` registra a origem da medida (`source`)
- [ ] Testes de contexto: finalizar feliz (1 auditoria, resultado preenchido, status, trava) · recusa pelo guard não grava nada · finalizar sem motivo obrigatório → 422 · resultado do finalizar igual ao do calcular com as mesmas entradas · editar após finalizar → 409

### Chunk F1 — Limpeza e base do front

- [ ] Remover o legado do paradigma antigo: `NewAssessment.tsx` atual, `calculators/*` (Bio, Imc, Navy, Skinfold), `protocolFields`, `methodDetails.ts`, `skinfoldFields`, tipos `AssessmentMethod`/`skinfold`/`bioimpedance`, `methodOptions`
- [ ] Reaproveitar `ClientContextBar`, `useClientDetail`, `PageLoader` e `InputField`; o billing não restringe o fluxo novo (DEC-62)
- [ ] Tipos TS espelhando os DTOs (painel, catálogos, erros estruturados)
- [ ] Módulo de API novo para o fluxo (`draft`, `context`, `measurement`, `panel`, `calculate`, `catalog`, `previous-measurements`) + hooks react-query (catálogos com `staleTime` longo; gravação de medida sequencial por `inputId`)
- [ ] Nenhum `if (variantId === ...)` no front (`architecture.md` §4): tudo vem do catálogo e do painel
- [ ] O `package.json` do front não tem framework de teste: decidir (ex.: Vitest + Testing Library) para a lógica pura de mapeamento painel → visão

### Chunk F2 — Coleta

- [ ] Cabeçalho de perfil travado + contexto editável
- [ ] Bloco de medidas agrupado por `group`, com rótulo/unidade/precisão/faixa/tooltip do catálogo
- [ ] Autosave no blur com estado por campo (`salvando`/`salvo`/`erro`), retry, e erros de validação inline
- [ ] Botão "Usar X de dd/MM" com `source` (`AVALIACAO_ANTERIOR` → `MANUAL` se editar)
- [ ] Regra "campo com valor nunca some" (`doc.md` §10.1); entrada com vírgula decimal

### Chunk F3 — Painel de fórmulas

- [ ] Cartão "Método sugerido" com os 4 estados de `suggestionStatus` e empates
- [ ] Lista por status (🟢, 🟢⚠, 🟡, grupo recolhido com 🔴/⚪); inelegível sem botão de escolher
- [ ] "Ver critérios" com `reasons`/`warnings` do back
- [ ] Seleção com destaque dos campos usados, progresso "N/M" e foco no campo faltante
- [ ] Selo "diferente da sugerida"; o painel só atualiza com as respostas do back

### Chunk F4 — Cálculo e resultado

- [ ] Barra de ações: Calcular (habilitação por estado) e Salvar avaliação (diálogo com motivo obrigatório conforme §17.5)
- [ ] Resultado exibido vira "desatualizado" quando medida ou contexto muda; ao salvar, mostrar o valor gravado pelo back
- [ ] Tratamento de 422 (`SELECTION_NOT_ALLOWED`, `REASON_REQUIRED`) e 409
- [ ] Bloco de conversão (sugerida, alternativas, critérios, escolher → recalcula) e resultado final
- [ ] Objetivo nutricional e métricas derivadas: o response do `/calculate` não expõe a recomendação completa (gap do Chunk 4 da Fase 12); ler a Assessment ou ampliar o DTO
- [ ] Exibir o `auditId` após salvar

### Chunk F5 — Bordas e qualidade

- [ ] Reabrir rascunho (`GET /panel`)
- [ ] Cliente sem medidas anteriores; erro de rede; perda de conexão durante o autosave
- [ ] Layout empilhado abaixo de 900 px, barra de cálculo fixa, foco e `aria-live` para o estado de salvamento
- [ ] Ajustar os consumidores do `AssessmentResponseDTO` levantados no pré-voo

### Regras do jogo — Fase 13

1. **Continuam intocáveis:** `domain/` (exceto a nova exceção do DEC-53) e os 43 YAMLs de fórmula e de regras científicas
2. **Exceções controladas, cada uma com DEC:** `orchestration/` (extração de `assess()` + guard, DEC-53) e `library/measurements` (campo `group`, DEC-59)
3. **Não inventar** regra científica nem texto científico no front — faltou dado, consulta a doc
4. **Nunca** `if (variantId == "JP7")` / `if (name == "Siri")`, no back ou no front
5. Toda regra de execução vale no back; o front só a antecipa na interface
6. Migrations manuais para colunas novas (DEC-49), executadas antes de subir a versão
7. Todo chunk fecha com compile/teste verde antes do próximo

### Pendências fora do escopo da V1

- Limpeza de rascunhos abandonados (política de expiração)
- Persistência da configuração customizada do profissional (V1 roda em DEFAULT, DEC-26); `DISABLED` só passa a ocorrer depois disso
- Tradução dos textos científicos para outros idiomas (DEC-60)
- Reabrir avaliação finalizada para correção (V1: nova avaliação)