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
- Pacote-base real: **`com.bioimpedance`** (não `com.bioimpedance.backend` — isso foi corrigido; ver pendência MIG-1 abaixo).
- Projeto já existente (`bioimpedance-backend-temp`) é um protótipo tipo "calculadora direta" (`controller → service → repository`, com `entity`/`dto`/`mapper`), diferente da arquitetura `domain/library/orchestration` do `architecture.md`. A infraestrutura (`security`, `config`, `exception`, `pagination`, auth, billing, branding) é reaproveitada como está. A lógica científica (`util/BodyFatCalculator`, `service/CalculationService`, `service/RecommendationService`, `entity/Assessment`) é o que está sendo substituído/redesenhado por este guia.
- Dependências no `pom.xml`: Jackson databind + jsr310, Lombok, MapStruct, H2 (test), PostgreSQL. **`jackson-dataformat-yaml` já foi adicionado** (necessário nas Fases 2b–2d).

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
├── entity/          ← ⚠️ Assessment/AssessmentResult redesenhados no checkpoint pós-Fase 3
├── exception/       ← reaproveita
├── mapper/          ← reaproveita
├── pagination/      ← reaproveita
├── repository/      ← reaproveita (User, Client, Billing, etc.)
├── security/        ← reaproveita
├── service/         ← ⚠️ CalculationService e RecommendationService serão substituídos
│                      ⚠️ RecommendationService ≠ SuggestionEngine (ver §1.1)
└── util/            ← ⚠️ BodyFatCalculator será substituído por domain/calculation
```

### Arquitetura nova — pastas JÁ CRIADAS no projeto (vazias)

```
com.bioimpedance/
├── domain/
│   ├── contracts/             ← Fase 1 (código pronto, migrar — ver MIG-1)
│   ├── validation/            ← Fase 4
│   ├── applicability/         ← Fase 5
│   ├── eligibility/           ← Fase 6
│   ├── suggestion/            ← Fase 7
│   │   ├── criteria/
│   │   └── explanation/
│   ├── conversion-suggestion/ ← Fase 8
│   │   ├── criteria/
│   │   └── explanation/
│   ├── calculation/           ← Fase 3
│   ├── conversion/            ← Fase 3
│   ├── config/                ← Fase 9 (SystemConversionPolicy)
│   └── audit/                 ← Fase 10
├── library/
│   ├── equations/             ← Fase 2c (EquationVariantRegistry)
│   ├── conversions/           ← Fase 2c (ConversionDefinitionRegistry)
│   ├── scientificrules/       ← Fase 2a (código pronto, migrar — ver MIG-1)
│   └── measurements/          ← Fase 2c (InputTypeCatalog)
├── orchestration/
│   └── assessment-flow/       ← Fase 11
└── persistence/
    └── repositories/          ← Fase 12
```

### Resources — dados científicos (Fase 2b concluída)

```
bioimpedance-backend-temp/src/main/resources/
└── library/
    ├── scientific-rules/   ← 43 YAMLs de EquationVariantScientificProfile
    │   ├── FALK4.yaml
    │   ├── G-M3..G-M8, G-F3.yaml        (Guedes: 7)
    │   ├── JP3-M, JP3-F, JP7-M, JP7-F   (Jackson & Pollock: 4)
    │   ├── P-M1..P-M16                  (Petroski M: 16)
    │   └── P-F1..P-F14, P-F16           (Petroski F: 15)
    └── conversions/
        ├── siri.yaml
        └── brozek.yaml
```

---

## 0.3 Pendências e decisões registradas

**MIG-1 — Java das Fases 1 e 2a ainda está FORA do projeto.**
Os records vivem em pastas separadas no desktop (`fase1-domain-contracts-java`, `fase1-e-2a-java`) sob pacote `com.bioimpedance.backend.*`. Antes da Fase 2c:
1. Copiar `domain/contracts/*` → `src/main/java/com/bioimpedance/domain/contracts/`
2. Copiar `library/scientificrules/*` → `src/main/java/com/bioimpedance/library/scientificrules/`
3. Renomear pacote `com.bioimpedance.backend.*` → `com.bioimpedance.*` em todos os arquivos (declarações, imports e notas de `package-info`).
4. Compilar (`mvn -q compile`) antes de seguir.

**MIG-2 — Decisão em aberto (nota do `package-info` da Fase 2a):** `Sex` e `TrainingLevel` existem duplicados em `library.scientificrules` e `domain.contracts` (exigência de fronteira: library não depende de domain). Alternativa documentada: pacote shared-kernel abaixo de library. Decisão não tomada; manter duplicação até decidir.

**DAD-1 — `P-F15` sem ficha fonte.** Nenhum `.md` de P-F15 foi fornecido; portanto não há YAML. Inventário real de YAMLs = **43**. Se a ficha aparecer depois, gerar YAML + fórmula e atualizar este guia.

**DEC-1 — `G-F4`–`G-F8` fora da V1.** Excluídas deliberadamente pelas fichas (Regra #1). Não gerar YAML nem FormulaDefinition; não usar a tabela do `doc.md` §18 para ressuscitá-las.

**DEC-2 — `FALK4` com `supportedSexes = [MALE]`.** Decisão científica da ficha. A forma feminina `7.9 + 0.213 × Σ4` tem coeficientes próprios e **não** deve ser fundida com FALK4.

**DEC-3 — Siri é default operacional, não verdade científica.** O default vive em `SystemConversionPolicy.defaultConversionId` (Fase 9), nunca como `if (name == "Siri")` no motor. Brozek permanece na biblioteca e é oferecida sempre que elegível; preferência profissional válida substitui o default.

**DEC-4 — `DevelopmentEvidence.population` modelado como `String`.** Fidelidade ao dado real das fichas (resumo textual), não ao `PopulationProfile` estrito do schema. Se as fichas mudarem, o record muda junto.

**DEC-5 — Regra de classificação de estudos:** subamostra independente do MESMO estudo → `validationStudies`; dado/equação de OUTRO estudo/pesquisador → `crossValidationStudies`; validação totalmente independente do programa original → `externalValidationStudies`.

**DEC-6 — Métricas de validação:** `standardError` recebe o EPE quando a fonte reporta EPE; ET nunca vai para `standardError`; `rmse` só quando a fonte nomeia RMSE explicitamente; métricas em % de gordura (estudos que converteram densidade) ficam em `otherMetrics`, nunca como métricas de `BODY_DENSITY`.

---

## 1. Ordem de implementação

A ordem segue a árvore de dependência definida em `architecture.md` §1.1 (`orchestration → domain → library → persistence`), do módulo com zero dependência até o que depende de tudo.

Cada fase fecha com os golden tests correspondentes (`architecture.md` §19) como critério de "pronto" — não "parece certo".

| Fase | Módulo                                                                                                                                                 | Documentos a ler (só as seções listadas)                                                                                                      | Status          |
| ---- | ------------------------------------------------------------------------------------------------------------------------------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------- | --------------- |
| 1    | `domain/contracts`                                                                                                                                     | `architecture.md` §1.3                                                                                                                        | ✅ Feita        |
| 2a   | `library.scientificrules` — modelo Java (espelha `schema_cientifico.md`)                                                                               | `schema_cientifico.md` (inteiro)                                                                                                              | ✅ Feita        |
| 2b   | Converter as fichas de Markdown → YAML real                                                                                                            | `equation_family_especificacao_formal.md` (inteiro) · fichas como dado real                                                                   | ✅ Feita (43 YAMLs + 2 conversões; ver DAD-1/DEC-1) |
| MIG  | Migrar Java das Fases 1+2a para o projeto + rename de pacote (ver MIG-1)                                                                                | —                                                                                                                                              | 🔲 Pendente     |
| 2c   | `ScientificRuleRegistry`, `EquationVariantRegistry`, `InputTypeCatalog` carregando o YAML                                                              | `architecture.md` §1.4, §4.1–4.4                                                                                                              | 🔲 Pendente     |
| 2d   | Golden test de carregamento (todos os YAMLs sobem sem erro, nenhum campo essencial falta)                                                              | `architecture.md` §19                                                                                                                         | 🔲 Pendente     |
| 3    | `domain/calculation` + `domain/conversion`                                                                                                             | `architecture.md` §3, §13–15 · `doc.md` §21 · golden tests §19.1                                                                              | 🔲 Pendente     |
| —    | **Checkpoint: redesenho de `entity.Assessment`** (ver §1.1 abaixo) — só depois da Fase 3 provar que o mapa `inputId → valor` funciona de ponta a ponta | —                                                                                                                                             | 🔲 Pendente     |
| 4    | `domain/validation`                                                                                                                                    | `architecture.md` §2, §2.1–2.3                                                                                                                | 🔲 Pendente     |
| 5    | `domain/applicability`                                                                                                                                 | `architecture.md` §9, §9.1 · `especificacao_cientifica.md` §4–5 · `doc.md` §8, §8.1                                                           | 🔲 Pendente     |
| 6    | `domain/eligibility`                                                                                                                                   | `architecture.md` §10, §10.1–10.2 · `especificacao_cientifica.md` §9 · `doc.md` §9.2, §12                                                     | 🔲 Pendente     |
| 7    | `domain/suggestion` (+ criteria + explanation)                                                                                                         | `architecture.md` §11, §11.1–11.3, §16–17 · `especificacao_cientifica.md` §11–14 · `doc.md` §9.1, §9.3, §10–11, §16 · golden tests §19.2–19.3 | 🔲 Pendente     |
| 8    | `domain/conversion-suggestion`                                                                                                                         | `architecture.md` §12, §12.1–12.2 · `doc.md` §6, §7.3, §9.4, §19 (estrutura, não os coeficientes)                                             | 🔲 Pendente     |
| 9    | `domain/config`                                                                                                                                        | `architecture.md` §7, §7.1–7.4 · `doc.md` §5–6                                                                                                | 🔲 Pendente     |
| 10   | `domain/audit`                                                                                                                                         | `architecture.md` §6, §22 · `doc.md` §3 (regra de auditoria)                                                                                  | 🔲 Pendente     |
| 11   | `orchestration/assessment-flow`                                                                                                                        | `architecture.md` §23 · `doc.md` §13–15                                                                                                       | 🔲 Pendente     |
| 12   | `persistence` (resto: `CalculateRequestDTO`/`AssessmentController` refeitos pro fluxo de 2 momentos)                                                   | Sem seção fixa                                                                                                                                | 🔲 Pendente     |

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
- [x] 2b — 43 YAMLs científicos + 2 conversões salvos em `resources/library/` (ver §0.2/§0.3).
- [ ] MIG — migrar Java das Fases 1+2a para o projeto + rename de pacote (MIG-1).
- [ ] 2c–2d — registries carregando o YAML + golden test de carregamento.
- [ ] Fase 3 — cálculo/conversão (prova a arquitetura funcionando de ponta a ponta, com dado solto).
- [ ] Checkpoint — redesenho de `entity.Assessment` (ver §1.1).
- [ ] Fases 4 (validação) — pode ser feita em paralelo com a 3, não depende dela.
- [ ] Fases 5–6 (applicability, eligibility) em sequência — eligibility consome o resultado de applicability.
- [ ] Fase 7 (suggestion) só depois que 5–6 estiverem testadas.
- [ ] Fase 8 (conversion-suggestion) espelha a 7, entra depois dela.
- [ ] Fases 9–10 (config, audit) são pequenas, podem entrar em qualquer momento a partir da fase 2.
- [ ] Fase 11 (orchestration) por último — só amarra o que já existe.
- [ ] Fase 12 (persistence completa + `CalculateRequestDTO`/`AssessmentController` refeitos).

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

| Família           | YAMLs presentes                                   | Ausentes / observações                          |
| ----------------- | ------------------------------------------------- | ----------------------------------------------- |
| Jackson & Pollock | JP3-M, JP3-F, JP7-M, JP7-F (4)                    | —                                               |
| Guedes            | G-M3..G-M8, G-F3 (7)                              | G-F4..G-F8 fora da V1 (DEC-1)                   |
| Petroski          | P-M1..P-M16 (16), P-F1..P-F14, P-F16 (15)        | P-F15 sem ficha fonte (DAD-1)                   |
| Faulkner          | FALK4 (1)                                         | sexo MALE (DEC-2)                               |
| **Total**         | **43**                                            | + 2 conversões (`siri.yaml`, `brozek.yaml`)     |