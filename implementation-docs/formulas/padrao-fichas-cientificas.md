# Padrão de Estrutura — EquationVariantScientificProfile

Este documento é a referência única para revisar cada ficha científica, uma por uma. Ele não substitui os 3 documentos-base (schema_cientifico.md, especificacao_cientifica.md, equation_family_especificacao_formal.md) — ele **operacionaliza** as regras deles em forma de checklist, junto com as decisões de padronização e os erros já confirmados na auditoria.

Cole este documento junto com cada ficha na hora de revisar.

---

## 1. Estrutura obrigatória — 19 seções, nesta ordem exata

Nenhuma ficha deve ter mais ou menos que 19 seções de nível superior. Sub-métricas, notas extras etc. **sempre viram subseção** (ex. `9.1`, `9.2`) — nunca uma seção nova de nível superior.

| # | Seção | O que entra aqui |
|---|---|---|
| 1 | Identification | `variantId`, `familyId`, `displayName`, `aliasNames` + referência principal em prosa (informal) |
| 2 | Mathematical Definition | Fórmula, coeficientes, `outputType`, forma computacional |
| 3 | Applicability | `3.1 Sex`, `3.2 Age` (sempre essas duas subseções, nessa ordem) |
| 4 | Population Applicability | `originalPopulation` + `validationPopulations` |
| 5 | Athlete Applicability | `developedInAthletes/validatedInAthletes/...` |
| 6 | Training Level Applicability | `supportedLevels` + `notes` |
| 7 | Modality Applicability | `supportedModalities` + `notes` |
| 8 | Body Characteristics | `rules: Array<BodyCharacteristicRule>` |
| 9 | Validation Evidence | `9.1 Development Evidence` — **inclusive as métricas de desenvolvimento (`R`, `R²`, `standardError`) vão dentro do objeto `metrics` aqui, nunca em seção própria** |
| 10 | Validation | Ver regra exata na seção 4 deste documento |
| 11 | Cross-validation | Ver regra exata na seção 4 deste documento |
| 12 | External Validation | `externalValidationStudies` |
| 13 | Measurement Requirements | `requiredInputs` / `optionalInputs` |
| 14 | Scientific Restrictions | `restrictions: Array<ScientificRestriction>` |
| 15 | Source Conflict | `sourceConflict` — ver regra exata na seção 5 |
| 16 | Lifecycle | `status/version/...` + checklist de campos essenciais |
| 17 | Ficha Consolidada | Objeto completo, montado a partir das seções 1–16 |
| 18 | Interpretação para o SuggestionEngine | Notas de interpretação em runtime |
| 19 | Reference | Objeto `Reference` formal + fontes bibliográficas adicionais |

**Erros já confirmados nesta seção (corrigir):**
- `G-M4`, `G-M7` — criaram uma seção extra "Development Metrics" (nº 10), empurrando tudo e terminando em 20 seções. Mover o conteúdo pra dentro de `9.1`.
- `G-M3`, `G-M6` — inverteram 10/11 (puseram Cross-validation antes de Validation).
- `P-M3` — dobrou "Validation" como subseção `9.2` em vez de seção própria; tudo desliza uma posição e termina em 18.

---

## 2. Regras gerais herdadas do schema (resumo operacional)

- `Nullable<X>` = ausência documentada. `null` ≠ `false` ≠ "documentado como não aplicável" — são três estados diferentes, nunca colapsar.
- Estes campos **nunca** podem aparecer dentro da ficha (são derivados em runtime pelo motor): `ageMatch`, `populationMatch`, `sexMatch`, `contextMatch`, `evidenceCoverage`, `READY`, `WARNING`, `INELIGIBLE`.
- `SEX` nunca é `requiredInput` — ele mora só em `applicability.sex`.
- `AGE` só é `requiredInput` se for variável matemática da própria fórmula. Se a população tem faixa etária documentada mas a fórmula não usa idade no cálculo, `AGE` fica de fora dos inputs (ex.: toda a família Guedes).
- `developmentSampleMeanAge` é sempre descritivo — nunca virar limite de elegibilidade.
- Campos essenciais pra `status: ACTIVE` (não podem ser `TBD`): identity, mathematicalDefinition, supportedSexes, originalDevelopmentAgeRange, originalPopulation, requiredInputs, reference da definição. Além disso, `sourceConflict` não pode estar `UNRESOLVED`.

---

## 3. Nomenclatura canônica de `inputId`

| Nome canônico (usar sempre) | Variações encontradas que precisam virar isso | Onde apareceu errado |
|---|---|---|
| `SKINFOLD_AXILLARY_MID` | `SKINFOLD_MID_AXILLARY`, `SKINFOLD_MIDAXILLARY` | P-M2 (`MID_AXILLARY`); **todos** os P-F1–P-F16 (`MIDAXILLARY`) |
| `SKINFOLD_ABDOMEN` | `SKINFOLD_ABDOMINAL` | FALK4; **todos** os P-F1–P-F16 |
| `SKINFOLD_PECTORAL` | `SKINFOLD_PECTORal` (typo) | P-F1 |
| `BODY_MASS` | `BODY_WEIGHT` (usado no exemplo do schema_cientifico.md, mas nunca na prática) | — nenhum arquivo usa `BODY_WEIGHT` de fato; recomendo atualizar o exemplo do schema pra `BODY_MASS`, que é o que todo mundo já usa |

**🟡 Decisão pendente — não decidi sozinho:** `SKINFOLD_CHEST` (Jackson-Pollock) e `SKINFOLD_PECTORAL` (Petroski) foram mantidos como enums **distintos**, sob a hipótese de que são pontos de dobra anatomicamente diferentes entre os dois protocolos. Isso é uma call científica de vocês — se for confirmado que são o mesmo ponto físico com nomes de tradução diferentes, essa recomendação muda.

---

## 4. Regra de classificação: Validation vs. Cross-validation vs. External Validation

Esta é a regra que resolve a contradição encontrada entre as famílias Guedes e Petroski:

> **Validation** = subamostra independente do **mesmo** estudo/tese original, usada para testar a equação desenvolvida na amostra principal (ex.: os 68 mulheres / 87 homens do Petroski; os 41 participantes do Guedes).
>
> **Cross-validation** = teste da variante contra dado, equação ou população de **outro** estudo/pesquisador.
>
> **External Validation** = validação totalmente independente, fora do programa de pesquisa original.

Sob essa regra:
- A maioria dos arquivos Petroski já está certa (usam `validationStudies` pra subamostra do mesmo estudo).
- **P-M5** está errado — moveu a amostra de 87 homens pra `crossValidationStudies`. Corrigir: mover para `validationStudies`.
- **G-F3** e **G-M3** estão errados — moveram a amostra de 41 participantes pra `crossValidationStudies`. Corrigir: mover para `validationStudies`.
- **G-M5** já está certo (usa `validationStudies` para os 41 participantes) — **não mexer**.

> Isso inverte a recomendação de "corrigir G-M5 pra espelhar G-F3/G-M3" feita antes — na verdade G-M5 é o gold standard aqui, e G-F3/G-M3 é que precisam mudar.

---

## 5. Regra de `sourceConflict`

`sourceConflict` é `Nullable<SourceConflictRecord>` — nunca uma string solta. Quando preenchido, precisa ter:

```
sourceConflict:
    field: <qual campo diverge>
    conflictingValues:
        - value: <valor 1>
          source: <referência 1>
        - value: <valor 2>
          source: <referência 2>
    resolutionStatus: UNRESOLVED | RESOLVED_USING_PRIMARY_SOURCE | EXCLUDED_PENDING_SOURCE
    notes: <opcional>
```

**Heurística pra decidir quando usar (resolve a inconsistência FALK4 vs. Guedes):**
- Duas fontes primárias legítimas relatam **valores diferentes** pro mesmo parâmetro da mesma variante (ex.: dois coeficientes, duas idades de amostra) → **é** `SOURCE_CONFLICT`, estruturado.
- Simplesmente não há informação sobre algo → fica `null` / campo individual vira `NOT_DOCUMENTED`. Isso **não é** conflito.

**Correções necessárias:**
- **FALK4** — reescrever `sourceConflict` como objeto estruturado (está como string solta hoje). Como a variante está `ACTIVE`, o `resolutionStatus` não pode ficar `UNRESOLVED` — precisa virar `RESOLVED_USING_PRIMARY_SOURCE` (ou a variante deixa de ser `ACTIVE`).
- **G-M4, G-M7, G-M8** — pela heurística acima, a divergência 1985 (17–27 anos) vs. 1991 (18–30 anos) **é** conflito material, não apenas "pendência bibliográfica". Estruturar como `SourceConflictRecord` nos três.

🟡 **Decisão pendente:** qual das duas fontes (Guedes 1985 ou Guedes & Guedes 1991) é a primária pra cada equação — isso define o `resolutionStatus` e qual valor prevalece.

---

## 6. Regra de `Reference` / `studyReference`

- `Reference` é **sempre** objeto `{ citation, doi, url }` — nunca string solta, mesmo dentro de `development.studyReference`.
- `url` é **sempre string pura**. Nunca sintaxe markdown (`[texto](link)`) dentro do valor.

Correto:
```
url: "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"
```
Errado (encontrado em vários arquivos):
```
url: "[[https://...](https://...)](https://...)"
```

**Correções necessárias:**
- Limpeza em lote de URL malformada: `P-F1, P-F2, P-F4, P-F5, P-F6, P-F10, P-F11, P-F12, P-F13, P-F14, P-F16, P-M11, P-M12, P-M13, P-M14`.
- `G-M3` — `studyReference` em 9.1 está sem o campo `url` (nem `null`). Completar.
- `G-M7` — `studyReference` em 9.1 é string solta em vez de objeto. Reestruturar.
- `G-M4, G-M7, G-M8` — `Reference` principal (seção 19) está com `url: null`, mas citam a mesma fonte de `G-M3/G-M5/G-M6/G-F3`, que já tem a URL do PubMed. Copiar: `https://pubmed.ncbi.nlm.nih.gov/1845307/`.

---

## 7. O que já está certo — preservar, não regredir

- `developmentSampleMeanAge` tratado como puramente descritivo em praticamente todo arquivo — manter essa disciplina.
- Distinção `NOT_DOCUMENTED` vs. `INELIGIBLE` respeitada (campos de atleta/treino/modalidade ficam `null`, nunca viram restrição por omissão).
- `SEX` nunca aparece em `requiredInputs` em nenhum arquivo — está certo, manter.
- Populações de desenvolvimento e de validação sempre registradas como entradas separadas, nunca colapsadas numa só.
- Nenhuma ficha herdou dado científico da `EquationFamily` — aplicabilidade sempre resolvida no nível da variante.
- Uso de `null` explícito em vez de inventar valor quando a fonte não informa (ex.: métricas de cross-validation da Guedes ficam `null` em vez de estimadas).
- Conversões (Siri/Brozek) mantidas fora do conceito de `EquationVariant`.

---

## 8. Decisões pendentes — precisam da sua palavra antes de eu seguir com a correção

1. `SKINFOLD_CHEST` vs. `SKINFOLD_PECTORAL` — são pontos anatômicos diferentes de verdade, ou o mesmo ponto com nome diferente?
2. Guedes 1985 (17–27 anos) vs. 1991 (18–30 anos) — qual é a fonte primária correta pra cada equação da família?
3. Contagem final da V1: Guedes = 7 (6M + 1F)? Petroski = 32 (16M + 16F)? O que houve com P-F15 — existe e falta o arquivo, ou foi descontinuada?
4. FALK4 — mantém `supportedSexes: [MALE]` só, ou reavalia incluir FEMALE com restrição/warning?

---

## 9. Checklist de revisão por arquivo

Rodar isto em cada ficha antes de aprovar:

```
[ ] 19 seções, na ordem exata (tabela da seção 1)
[ ] Nenhum campo derivado (ageMatch, READY, INELIGIBLE etc.) na ficha
[ ] Todo inputId bate com a lista canônica (seção 3)
[ ] SEX fora de requiredInputs
[ ] AGE em requiredInputs só se for variável matemática de fato
[ ] Toda Reference é objeto completo {citation, doi, url}
[ ] Todo url é string pura, sem markdown aninhado
[ ] sourceConflict é null OU SourceConflictRecord completo — nunca string solta
[ ] Amostra independente do MESMO estudo → validationStudies (não crossValidationStudies)
[ ] developmentSampleMeanAge não é tratado como limite
[ ] Nada preenchido por inferência — ausência real fica null/[]
```
