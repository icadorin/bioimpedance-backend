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
- Projeto já existente (`bioimpedance-backend-temp`) é um protótipo tipo "calculadora direta" (`controller → service → repository`, com `entity`/`dto`/`mapper`), diferente da arquitetura `domain/library/orchestration` do `architecture.md`. A infraestrutura (`security`, `config`, `exception`, `pagination`, auth, billing, branding) é reaproveitada como está. A lógica científica (`util/BodyFatCalculator`, `service/CalculationService`, `service/RecommendationService`, `entity/Assessment`) é o que está sendo substituído/redesenhado por este guia.
- Dependências no `pom.xml`: Jackson databind + jsr310, Lombok, MapStruct, H2 (test), PostgreSQL, `jackson-dataformat-yaml` (adicionado na Fase 2b).

---

## 0.2 Estrutura de pastas do projeto (contexto permanente)

### Protótipo existente — reaproveitar como está

```
bioimpedance-backend-temp/src/main/java/com/bioimpedance/
├── BioimpedanceApplication.java
├── config/          ← reaproveita (Security, Jackson, Web, Stripe)
├── constants/       ← reaproveita (Gender, Plan, etc.)
│                      ⚠️ ActivityLevel ≠ TrainingLevel do schema (ver §1.1)
├── controller/      ← reaproveita (Auth, Billing, Client, Dashboard)
│                      ⚠️ AssessmentController será refeito na Fase 12
├── dto/             ← reaproveita (auth, request, response)
│                      ⚠️ CalculateRequestDTO refeito na Fase 12
├── entity/          ← ✅ Checkpoint pós-Fase 3: Assessment com @OneToMany measurements (DEC-9)
│                      + AssessmentMeasurement (DEC-9/12); colunas fixas depreciadas mas vivas
├── exception/       ← reaproveita
├── mapper/          ← reaproveita
├── pagination/      ← reaproveita
├── repository/      ← reaproveita (User, Client, Billing, etc.)
├── security/        ← reaproveita
├── service/         ← ⚠️ CalculationService e RecommendationService serão substituídos
│                      ⚠️ RecommendationService ≠ SuggestionEngine (ver §1.1)
└── util/            ← ⚠️ BodyFatCalculator será substituído por domain/calculation
```

### Arquitetura nova — preenchida até agora

```
com.bioimpedance/
├── domain/
│   ├── contracts/             ← Fase 1 ✅ (17 tipos: ClientProfile, ReadinessResult, etc.)
│   ├── validation/            ← Fase 4 ✅ (MeasurementValidator)
│   ├── applicability/         ← Fase 5 (vazio)
│   ├── eligibility/           ← Fase 6 (vazio)
│   ├── suggestion/            ← Fase 7 (vazio)
│   ├── conversion-suggestion/ ← Fase 8 (vazio)
│   ├── calculation/           ← Fase 3 ✅ (EquationEvaluator, PredictionResult)
│   ├── conversion/            ← Fase 3 ✅ (DensityToFatConverter)
│   ├── config/                ← Fase 9 (vazio)
│   └── audit/                 ← Fase 10 (vazio)
├── library/
│   ├── equations/             ← Fase 3 ✅ (FormulaDefinition, FormulaTemplate, EquationVariantRegistry)
│   ├── conversions/           ← Fase 2c ✅ (ConversionDefinition, ConversionDefinitionRegistry)
│   ├── scientificrules/       ← Fase 2a ✅ (30 tipos + ScientificRuleRegistry)
│   └── measurements/          ← Fase 2c ✅ (InputTypeDefinition, InputTypeCatalog)
├── orchestration/
│   └── assessment-flow/       ← Fase 11 (vazio)
└── persistence/
    └── repositories/          ← Fase 12 (vazio)
```

### Resources — dados

```
bioimpedance-backend-temp/src/main/resources/
└── library/
    ├── scientific-rules/   ← 43 YAMLs ✅ (EquationVariantScientificProfile)
    ├── equations/          ← 43 YAMLs ✅ (FormulaDefinition — inventário completo)
    ├── conversions/        ← siri.yaml + brozek.yaml ✅
    └── measurements/       ← input-types.yaml ✅ (15 inputIds)
```

### Testes — 30 golden tests verdes (+1 legado desabilitado)

```
src/test/java/com/bioimpedance/
├── BioimpedanceApplicationTests.java          ← @Disabled (protótipo legado, Fase 12)
├── library/
│   ├── LibraryLoadingGoldenTest.java          ← 7 testes (visão integrada)
│   ├── scientificrules/
│   │   └── ScientificRuleRegistryGoldenTest.java  ← 5 testes
│   ├── conversions/
│   │   └── ConversionDefinitionRegistryGoldenTest.java ← 2 testes
│   └── measurements/
│       └── InputTypeCatalogGoldenTest.java    ← 3 testes
└── domain/
    ├── calculation/
    │   └── CalculationGoldenTest.java              ← 2 testes (FALK4 + P-M16 + Siri)
    └── validation/
        └── MeasurementValidationGoldenTest.java    ← 7 testes (validação contra InputTypeCatalog)
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

**DEC-7 — BioimpedanceApplicationTests com `@Disabled` em nível de classe.** O teste de contexto do protótipo falha por BLOB/H2 + placeholder `APP_ENCRYPTION_SECRET`. Será refeito na Fase 12. Os 19 golden tests da arquitetura nova não dependem de contexto Spring e passam isolados.

**DEC-8 — `FormulaTemplate` com 7 variantes.** LINEAR_SUM, LOG10_SUM, QUADRATIC_SUM_WITH_AGE, QUADRATIC_SUM_WITH_AGE_AND_CIRC, QUADRATIC_SUM_WITH_AGE_MASS_HEIGHT, LOG10_SUM_WITH_AGE, LOG10_SUM_WITH_AGE_AND_CIRC. Circunferências mapeadas via `namedInputs` (circ1/circ2); BODY_MASS e HEIGHT acessados diretamente pelo evaluator.

**DEC-13 — UNIT_MISMATCH, DUPLICATE, CONFLICT e INCONSISTENCY existem no enum por contrato,
mas nenhum validador da V1 os emite; a emissão entra quando a fonte de dados
correspondente existir (unidade declarada no DTO de entrada, entrada em lista,
regras entre campos).

**DEC-14 — MeasurementValidator.validate(map, requiredInputIds) (overload) fica em
domain/validation. Se a Fase 6 decidir que MISSING_REQUIRED_INPUT é responsabilidade
exclusiva de domain/eligibility, o overload é removido sem dor (a chamada é opcional).

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
| 3    | `domain/calculation` + `domain/conversion` + 43 FormulaDefinitions em YAML | `architecture.md` §3, §13–15 · `doc.md` §21 · golden tests §19.1 | 🟡 Em andamento — motor pronto; 35/43 YAMLs salvos; falta Lote 6 (8 Petroski F log) + EquationLibraryGoldenTest |
| —    | **Checkpoint: redesenho de `entity.Assessment`** (ver §1.1) — só depois da Fase 3 provar que o mapa `inputId → valor` funciona de ponta a ponta | — | 🔲 Pendente |
| 4    | `domain/validation` | `architecture.md` §2, §2.1–2.3 | 🔲 Pendente |
| 5    | `domain/applicability` | `architecture.md` §9, §9.1 · `especificacao_cientifica.md` §4–5 · `doc.md` §8, §8.1 | 🔲 Pendente |
| 6    | `domain/eligibility` | `architecture.md` §10, §10.1–10.2 · `especificacao_cientifica.md` §9 · `doc.md` §9.2, §12 | 🔲 Pendente |
| 7    | `domain/suggestion` (+ criteria + explanation) | `architecture.md` §11, §11.1–11.3, §16–17 · `especificacao_cientifica.md` §11–14 · `doc.md` §9.1, §9.3, §10–11, §16 · golden tests §19.2–19.3 | 🔲 Pendente |
| 8    | `domain/conversion-suggestion` | `architecture.md` §12, §12.1–12.2 · `doc.md` §6, §7.3, §9.4, §19 (estrutura, não os coeficientes) | 🔲 Pendente |
| 9    | `domain/config` | `architecture.md` §7, §7.1–7.4 · `doc.md` §5–6 | 🔲 Pendente |
| 10   | `domain/audit` | `architecture.md` §6, §22 · `doc.md` §3 (regra de auditoria) | 🔲 Pendente |
| 11   | `orchestration/assessment-flow` | `architecture.md` §23 · `doc.md` §13–15 | 🔲 Pendente |
| 12   | `persistence` (resto: `CalculateRequestDTO`/`AssessmentController` refeitos + reabilitar BioimpedanceApplicationTests) | Sem seção fixa | 🔲 Pendente |

**Transversal (relevante em toda fase, reler quando bater dúvida):**
`architecture.md` §0 (regra de ouro), §4 (nenhuma seleção por nome — nunca `if variantId == "JP7"`), §5 (versionamento), §8 (separação de responsabilidades), §24 (regra de evolução), **§25 (checklist de PR — rodar ao final de toda fase)**. `doc.md` §33 (modelo mental definitivo — bom resumo pra realinhar entre fases).

### 1.1 Por que o checkpoint entra no meio da Fase 3

`entity.Assessment` hoje tem colunas fixas (`biceps`, `chest`, ... `thigh`) que não comportam as variantes (usam combinações diferentes de dobra/circunferência/massa). Precisa virar algo tipo mapa `inputId → valor`. Mas redesenhar isso **antes** de ver `domain.calculation` funcionando de ponta a ponta seria decidir o formato no escuro — melhor provar primeiro (com dado solto, sem banco) que o mapa aguenta o cálculo, só depois migrar a entidade de verdade. Evita redesenhar duas vezes.

Duas coisas que **não são** a mesma coisa e não devem ser fundidas quando chegar nesse redesenho:

- `constants.ActivityLevel` (SEDENTARY..VERY_ACTIVE, usado no TDEE) **≠** `TrainingLevel` do schema científico (SEDENTARY..ELITE, usado em aplicabilidade de fórmula).
- `service.RecommendationService` (dieta/treino) **≠** `SuggestionEngine` (qual fórmula usar). Concerns diferentes, o primeiro pode continuar quase como está.

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
- [ ] **Fase 3 — RETOMAR AQUI**:
  - [x] Motor de cálculo (EquationEvaluator + FormulaTemplate + FormulaDefinition + EquationVariantRegistry)
  - [x] Conversor de densidade (DensityToFatConverter)
  - [x] CalculationGoldenTest (2 testes: FALK4 direto + P-M16 → Siri)
  - [x] Lotes 1–5 de FormulaDefinition YAMLs (35/43 salvos)
  - [ ] **Lote 6**: 8 Petroski F logarítmicas (P-F2, P-F3, P-F5, P-F6, P-F8, P-F11, P-F12, P-F16)
  - [ ] **EquationLibraryGoldenTest**: cross-check dos 43 YAMLs matemáticos contra os 43 científicos (sumInputs ⊆ requiredInputs, outputType bate, template cobre os coeficientes)
- [ ] Checkpoint — redesenho de `entity.Assessment` (ver §1.1).
- [ ] Fases 4 (validação) — pode ser feita em paralelo com a 3, não depende dela.
- [ ] Fases 5–6 (applicability, eligibility) em sequência — eligibility consome o resultado de applicability.
- [ ] Fase 7 (suggestion) só depois que 5–6 estiverem testadas.
- [ ] Fase 8 (conversion-suggestion) espelha a 7, entra depois dela.
- [ ] Fases 9–10 (config, audit) são pequenas, podem entrar em qualquer momento a partir da fase 2.
- [ ] Fase 11 (orchestration) por último — só amarra o que já existe.
- [ ] Fase 12 (persistence completa + `CalculateRequestDTO`/`AssessmentController` refeitos + reabilitar BioimpedanceApplicationTests).

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

### equations/ (35/43 🟡)

| Lote | Variantes | Template | Status |
| ---- | --------- | -------- | ------ |
| 1 | G-M3..G-M8 (6) | LOG10_SUM | ✅ |
| 2 | G-F3, JP3-M, JP7-M, JP3-F, JP7-F (5) | LOG10_SUM / QUADRATIC_SUM_WITH_AGE | ✅ |
| 3 | P-M1, P-M3, P-M5, P-M7, P-M9, P-M11, P-M13, P-M15 (8) | QUADRATIC_SUM_WITH_AGE | ✅ |
| 4 | P-M2, P-M4, P-M6, P-M8, P-M10, P-M12, P-M14 (7) | QUADRATIC_SUM_WITH_AGE_AND_CIRC | ✅ |
| 5 | P-F1, P-F4, P-F7, P-F9, P-F10, P-F13, P-F14 (7) | QUADRATIC_SUM_WITH_AGE_MASS_HEIGHT | ✅ |
| 6 | P-F2, P-F3, P-F5, P-F6, P-F8, P-F11, P-F12, P-F16 (8) | LOG10_SUM_WITH_AGE / LOG10_SUM_WITH_AGE_AND_CIRC | 🔲 **PRÓXIMO** |
| — | FALK4 (1) | LINEAR_SUM | ✅ |
| **Total** | **43** | | **35 feitos, 8 faltando** |

### conversions/ (2/2 ✅)

| id | inputType | outputType | expression |
| -- | --------- | ---------- | ---------- |
| siri | BODY_DENSITY | BODY_FAT_PERCENTAGE | (4.95 / D − 4.50) × 100 |
| brozek | BODY_DENSITY | BODY_FAT_PERCENTAGE | (4.57 / D − 4.142) × 100 |

### measurements/ (15 inputIds ✅)

AGE, BODY_MASS, HEIGHT, SKINFOLD_SUBSCAPULAR, SKINFOLD_TRICEPS, SKINFOLD_BICEPS, SKINFOLD_PECTORAL, SKINFOLD_AXILLARY_MID, SKINFOLD_SUPRAILIAC, SKINFOLD_ABDOMEN, SKINFOLD_THIGH, SKINFOLD_MEDIAL_CALF, CIRCUMFERENCE_FOREARM, CIRCUMFERENCE_ABDOMEN, CIRCUMFERENCE_THIGH