# Padrão de Estrutura — EquationVariantScientificProfile

Este documento é a referência única para revisar cada ficha científica, uma por uma. Ele não substitui os 3 documentos-base (`schema_cientifico.md`, `especificacao_cientifica.md`, `equation_family_especificacao_formal.md`) — ele **operacionaliza** as regras deles em forma de checklist, junto com as decisões de padronização e os erros já confirmados na auditoria.

Cole este documento junto com cada ficha na hora de revisar.

---

## 1. Estrutura obrigatória — 19 seções, nesta ordem exata

Nenhuma ficha deve ter mais ou menos que 19 seções de nível superior referentes ao perfil científico. Submétricas, notas extras etc. **sempre viram subseção** (ex. `9.1`, `9.2`) — nunca uma seção nova de nível superior dentro do perfil científico.

| #   | Seção                                   | O que entra aqui                                                                                                                                                            |
| --- | --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 1   | Identificação                           | `variantId`, `familyId`, `displayName`, `aliasNames` + referência principal em prosa (informal)                                                                             |
| 2   | Definição Matemática                    | Fórmula, coeficientes, `outputType`, forma computacional                                                                                                                    |
| 3   | Aplicabilidade                          | `3.1 Sexo`, `3.2 Idade` (sempre essas duas subseções, nessa ordem)                                                                                                          |
| 4   | Aplicabilidade Populacional             | `originalPopulation` + `validationPopulations`                                                                                                                              |
| 5   | Aplicabilidade em Atletas               | `developedInAthletes/validatedInAthletes/...`                                                                                                                               |
| 6   | Aplicabilidade por Nível de Treinamento | `supportedLevels` + `notes`                                                                                                                                                 |
| 7   | Aplicabilidade por Modalidade           | `supportedModalities` + `notes`                                                                                                                                             |
| 8   | Características Corporais               | `rules: Array<BodyCharacteristicRule>`                                                                                                                                      |
| 9   | Evidências de Validação                 | `9.1 Evidências de Desenvolvimento` — **inclusive as métricas de desenvolvimento (`R`, `R²`, `standardError`) vão dentro do objeto `metrics` aqui, nunca em seção própria** |
| 10  | Validação                               | Ver regra exata na seção 4 deste documento                                                                                                                                  |
| 11  | Validação Cruzada                       | Ver regra exata na seção 4 deste documento                                                                                                                                  |
| 12  | Validação Externa                       | `externalValidationStudies`                                                                                                                                                 |
| 13  | Requisitos de Medição                   | `requiredInputs` / `optionalInputs`                                                                                                                                         |
| 14  | Restrições Científicas                  | `restrictions: Array<ScientificRestriction>`                                                                                                                                |
| 15  | Conflito de Fonte                       | `sourceConflict` — ver regra exata na seção 5                                                                                                                               |
| 16  | Ciclo de Vida                           | `status/version/...` + checklist de campos essenciais                                                                                                                       |
| 17  | Ficha Consolidada                       | Objeto completo, montado a partir das seções 1–16                                                                                                                           |
| 18  | Interpretação para o SuggestionEngine   | Notas de interpretação em runtime                                                                                                                                           |
| 19  | Referência                              | Objeto `Reference` formal + fontes bibliográficas adicionais                                                                                                                |

### Erros já confirmados nesta seção — corrigir

- `G-M4`, `G-M7` — criaram uma seção extra "Métricas de Desenvolvimento" (nº 10), empurrando tudo e terminando em 20 seções. Mover o conteúdo para dentro de `9.1`.
- `G-M3`, `G-M6` — inverteram 10/11 (colocaram Validação Cruzada antes de Validação).
- `P-M3` — colocou "Validação" como subseção `9.2` em vez de seção própria; tudo desliza uma posição e termina em 18.

---

## 2. Regras gerais herdadas do schema — resumo operacional

- `Nullable<X>` = ausência documentada. `null` ≠ `false` ≠ "documentado como não aplicável" — são três estados diferentes, nunca colapsar.

- Estes campos **nunca** podem aparecer dentro da ficha (são derivados em runtime pelo motor):
  - `ageMatch`
  - `populationMatch`
  - `sexMatch`
  - `contextMatch`
  - `evidenceCoverage`
  - `READY`
  - `WARNING`
  - `INELIGIBLE`

- `SEX` nunca é `requiredInput` — ele mora somente em `applicability.sex`.

- `AGE` só é `requiredInput` se for variável matemática da própria fórmula. Se a população tem faixa etária documentada mas a fórmula não usa idade no cálculo, `AGE` fica de fora dos inputs (ex.: toda a família Guedes que não possui idade como variável matemática).

- `developmentSampleMeanAge` é sempre descritivo — nunca virar limite de elegibilidade.

- Campos essenciais para `status: ACTIVE` (não podem ser `TBD`):
  - `identity`
  - `mathematicalDefinition`
  - `supportedSexes`
  - `originalDevelopmentAgeRange`
  - `originalPopulation`
  - `requiredInputs`
  - referência da definição

- Além disso, quando `status: ACTIVE`, `sourceConflict` não pode permanecer com `resolutionStatus: UNRESOLVED`.

- Uma publicação posterior **não deve ser assumida como substituta** de uma publicação anterior apenas por ser mais recente. Quando houver mais de uma versão historicamente documentada, cada conjunto de coeficientes e população deve ser investigado individualmente antes de decidir se representa:
  - a mesma `EquationVariant`;
  - uma nova `EquationVariant`;
  - uma atualização/substituição formal;
  - ou uma nomenclatura secundária para a mesma equação.

---

## 3. Nomenclatura canônica de `inputId`

| Nome canônico (usar sempre) | Variações encontradas que precisam virar isso                                                                                    | Onde apareceu errado / observação                                                                                                                 |
| --------------------------- | -------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------- |
| `SKINFOLD_AXILLARY_MID`     | `SKINFOLD_MID_AXILLARY`, `SKINFOLD_MIDAXILLARY`                                                                                  | `P-M2` (`MID_AXILLARY`); **todos** os `P-F1–P-F16` (`MIDAXILLARY`)                                                                                |
| `SKINFOLD_ABDOMEN`          | `SKINFOLD_ABDOMINAL`                                                                                                             | `FALK4`; **todos** os `P-F1–P-F16`                                                                                                                |
| `SKINFOLD_PECTORAL`         | `SKINFOLD_PECTORal` (erro de capitalização); `SKINFOLD_CHEST` quando utilizado para o protocolo equivalente de Jackson & Pollock | `P-F1` e variantes com `SKINFOLD_CHEST`                                                                                                           |
| `BODY_MASS`                 | `BODY_WEIGHT` (usado no exemplo do `schema_cientifico.md`, mas nunca na prática)                                                 | Nenhum arquivo usa `BODY_WEIGHT` de fato; recomenda-se atualizar o exemplo do schema para `BODY_MASS`, que é o identificador utilizado no projeto |

### Decisão científica — `SKINFOLD_CHEST` vs. `SKINFOLD_PECTORAL` ✅ RESOLVIDO

`SKINFOLD_CHEST` e `SKINFOLD_PECTORAL` **não serão tratados como `Measurement` distintos** nos protocolos de Jackson & Pollock e Petroski analisados.

A investigação das descrições anatômicas e técnicas disponíveis indicou convergência para o mesmo sítio de medição na região entre a **linha axilar anterior e o mamilo**, com a posição relativa variando conforme o sexo e a orientação da dobra descrita como diagonal/oblíqua.

Portanto:

```text
SKINFOLD_CHEST
→ nomenclatura alternativa utilizada em determinados protocolos/fontes

SKINFOLD_PECTORAL
→ identificador canônico do sistema
```

Regra de implementação:

```text
Jackson & Pollock → SKINFOLD_PECTORAL
Petroski          → SKINFOLD_PECTORAL
```

Não criar dois inputs independentes apenas por diferença de nomenclatura.

Essa normalização permite reutilizar corretamente o mesmo `Measurement` ao comparar equações de diferentes famílias.

### Regra de cautela

A unificação acima é específica aos protocolos e fontes analisados. Não deve ser generalizada automaticamente para qualquer metodologia antropométrica que utilize termos como `chest`, `pectoralis` ou `peitoral` sem verificar a definição anatômica e técnica correspondente.

---

## 4. Regra de classificação: Validação vs. Validação Cruzada vs. Validação Externa

Esta é a regra operacional para classificar corretamente as evidências.

> **Validação** = subamostra independente do **mesmo** estudo/tese original, usada para testar a equação desenvolvida na amostra principal.

> **Validação Cruzada** = teste da variante contra dado, equação ou população de **outro** estudo/pesquisador.

> **Validação Externa** = validação totalmente independente, fora do programa de pesquisa original.

### Correções identificadas

- A maioria dos arquivos Petroski já está certa: subamostras do mesmo estudo devem permanecer em `validationStudies`.

- **`P-M5`** — estava errado; a amostra de 87 homens deve estar em `validationStudies`, não em `crossValidationStudies`.

- **`G-F3`** — estava errado; a amostra de 41 participantes deve estar em `validationStudies`.

- **`G-M3`** — estava errado; a amostra de 41 participantes deve estar em `validationStudies`.

- **`G-M5`** — já está correto e deve permanecer como referência de classificação.

### Regra adicional para estudos com múltiplas publicações

Quando uma publicação posterior da mesma família utilizar uma nova amostra, novo conjunto de coeficientes ou nova população de desenvolvimento, isso **não deve ser classificado automaticamente como validação** da versão anterior.

Primeiro deve-se determinar se a publicação representa:

1. nova `EquationVariant`;
2. atualização formal da variante anterior;
3. validação independente da variante anterior.

A classificação depende da relação científica entre os estudos, não apenas da cronologia das publicações.

---

## 5. Regra de `sourceConflict`

`sourceConflict` é `Nullable<SourceConflictRecord>` — nunca uma string solta.

Quando preenchido, precisa ter:

```text
sourceConflict:

    field: <qual campo diverge>

    conflictingValues:

        - value: <valor 1>
          source: <referência 1>

        - value: <valor 2>
          source: <referência 2>

    resolutionStatus:
        UNRESOLVED
        | RESOLVED_USING_PRIMARY_SOURCE
        | EXCLUDED_PENDING_SOURCE

    notes: <opcional>
```

### Heurística para decidir quando usar

- Duas fontes primárias legítimas relatam **valores diferentes** para o mesmo parâmetro da mesma variante (ex.: dois coeficientes, duas idades de amostra) → **é** `SOURCE_CONFLICT`, estruturado.

- Simplesmente não há informação sobre algo → fica `null` / campo individual vira `NOT_DOCUMENTED`. Isso **não é** conflito.

- Quando a diferença encontrada ocorre porque as fontes representam **variantes ou estudos distintos**, não registrar automaticamente como `sourceConflict`. Primeiro separar as variantes.

### Correções e decisões já resolvidas

#### `FALK4`

A questão histórica da autoria/nomenclatura deve ser documentada de forma estruturada.

Quando houver divergência entre a atribuição tradicional da fórmula como "Faulkner" e a evidência histórica que relaciona sua origem a Yuhasz/modificação posterior, essa divergência deve ser descrita com clareza no perfil e na referência científica.

Caso `sourceConflict` seja utilizado, deve ser objeto completo e, com `status: ACTIVE`, não pode permanecer:

```text
resolutionStatus: UNRESOLVED
```

Deve ser resolvido por fonte primária ou a variante deve deixar de ser `ACTIVE`.

#### Guedes 1985 vs. Guedes & Guedes 1991

A diferença:

```text
1985 → 17–27 anos
1991 → 18–30 anos
```

não deve mais ser tratada automaticamente como conflito dentro de uma mesma variante.

A decisão científica adotada é:

```text
Guedes (1985)
→ conjunto próprio de equações
→ aplicabilidade própria
→ 17–27 anos

Guedes & Guedes (1991)
→ conjunto próprio de equações
→ aplicabilidade própria
→ 18–30 anos
```

Portanto, quando cada equação puder ser vinculada claramente à sua publicação de origem, a diferença de faixa etária representa **diferença entre variantes/fontes**, não conflito a ser artificialmente resolvido.

Os casos anteriormente marcados para conflito (`G-M4`, `G-M7`, `G-M8`) devem ser **reavaliados individualmente** para determinar qual publicação efetivamente contém os coeficientes utilizados antes de manter ou remover `sourceConflict`.

---

## 6. Regra de `Reference` / `studyReference`

- `Reference` é **sempre** objeto `{ citation, doi, url }` — nunca string solta, mesmo dentro de `development.studyReference`.

- `url` é **sempre string pura**. Nunca sintaxe Markdown dentro do valor.

### Correto

```text
url: "https://pubmed.ncbi.nlm.nih.gov/1845307/"
```

### Errado

```text
url: "[https://pubmed.ncbi.nlm.nih.gov/1845307/](https://pubmed.ncbi.nlm.nih.gov/1845307/)"
```

### Correções necessárias

- Limpeza em lote de URL malformada:
  - `P-F1`
  - `P-F2`
  - `P-F4`
  - `P-F5`
  - `P-F6`
  - `P-F10`
  - `P-F11`
  - `P-F12`
  - `P-F13`
  - `P-F14`
  - `P-F16`
  - `P-M11`
  - `P-M12`
  - `P-M13`
  - `P-M14`

- `G-M3` — `studyReference` em `9.1` está sem o campo `url`; completar ou usar `null` quando a fonte não disponibilizar URL.

- `G-M7` — `studyReference` em `9.1` está como string solta; reestruturar como `Reference`.

- `G-M4`, `G-M7`, `G-M8` — verificar a referência principal da seção 19. Quando a publicação for a mesma fonte de `G-M3/G-M5/G-M6/G-F3`, utilizar a referência correspondente da publicação, incluindo:

```text
https://pubmed.ncbi.nlm.nih.gov/1845307/
```

somente quando essa for efetivamente a fonte da variante.

### Regra adicional

Nunca copiar uma URL apenas porque duas variantes parecem pertencer à mesma família. A URL deve corresponder à **fonte efetivamente utilizada para aquela variante**.

---

## 7. Decisões científicas já fechadas — preservar

### 7.1 `SKINFOLD_CHEST` vs. `SKINFOLD_PECTORAL`

✅ Resolvido.

Utilizar:

```text
SKINFOLD_PECTORAL
```

como identificador canônico para os protocolos Jackson & Pollock e Petroski abrangidos pela decisão.

### 7.2 Guedes 1985 vs. Guedes & Guedes 1991

✅ Resolvido.

São tratados como **fontes/conjuntos de equações distintos**.

```text
Guedes (1985)
→ 17–27 anos

Guedes & Guedes (1991)
→ 18–30 anos
```

Cada `EquationVariant` deve utilizar a fonte que efetivamente originou seus coeficientes.

Uma publicação posterior não substitui automaticamente a anterior.

### 7.3 `P-F15`

✅ Resolvido.

- `P-F15` existe.
- Faz parte do conjunto original feminino de Petroski (1995).
- A ausência da ficha representa uma lacuna de documentação, não evidência de inexistência.
- Contagem adotada:
  - 16 variantes masculinas;
  - 16 variantes femininas;
  - 32 variantes no total.

Portanto, `P-F15` deve ser mantida/adicionada à biblioteca.

### 7.4 `FALK4`

✅ Resolvido.

Manter:

```text
supportedSexes:
    [MALE]
```

Não incluir `FEMALE`, nem mesmo com `warning`.

A presença posterior da chamada versão feminina na literatura não é suficiente para caracterizar suporte científico primário equivalente ao desenvolvimento masculino.

A questão histórica da autoria/nomenclatura da chamada "equação de Faulkner" deve ser explicitamente documentada.

---

## 8. Verificação transversal — versões históricas, variantes e autoria

Esta auditoria passou a ser **obrigatória** para toda a biblioteca antes de considerar o conjunto científico encerrado.

A descoberta de múltiplas versões em Guedes e a questão de atribuição em Faulkner demonstraram que uma mesma família pode conter:

- mais de uma publicação original;
- mais de um conjunto de coeficientes;
- diferentes populações de desenvolvimento;
- diferentes faixas etárias;
- diferentes sexos;
- diferentes condições de treinamento;
- versões posteriores que não substituem a original;
- nomes tradicionais cuja autoria real precisa ser investigada.

### Escopo da auditoria

Verificar todas as famílias e variantes já documentadas, incluindo pelo menos:

- Guedes;
- Petroski;
- Faulkner;
- Jackson & Pollock;
- demais famílias já incorporadas à biblioteca.

### Para cada família/variante verificar

- se existem versões históricas diferentes;
- se existem coeficientes diferentes publicados em anos diferentes;
- se houve reformulação da equação;
- se houve mudança de população de desenvolvimento;
- se houve mudança de faixa etária;
- se houve mudança de sexo;
- se houve mudança no número ou identidade das dobras;
- se existe nova publicação que representa uma nova variante e não mera validação;
- se a autoria tradicional corresponde à origem científica efetiva;
- se a nomenclatura histórica pode estar escondendo variantes distintas.

### Fontes obrigatórias

A investigação deve utilizar **fontes científicas confiáveis nacionais e internacionais**, priorizando:

1. artigos originais;
2. teses e dissertações originais;
3. periódicos científicos;
4. PubMed;
5. SciELO;
6. repositórios universitários e institucionais;
7. outras bases acadêmicas reconhecidas.

Fontes secundárias podem ser utilizadas para:

- localizar uma equação;
- identificar terminologia;
- descobrir uma referência;
- corroborar uma informação já encontrada.

Entretanto, **fonte secundária isolada não deve fechar uma decisão científica importante**.

### Regra de rastreabilidade

Para cada possível versão adicional, registrar pelo menos:

```text
familyId
variantId
source
publicationYear
mathematicalDefinition
originalDevelopmentAgeRange
originalPopulation
supportedSexes
requiredInputs
developmentReference
validationEvidence
notes
```

### Regra de decisão

Ao encontrar duas versões, não assumir imediatamente que são variantes distintas nem que são a mesma variante.

Primeiro comparar:

```text
coeficientes
+
variáveis matemáticas
+
população
+
sexo
+
faixa etária
+
método de referência
+
publicação de origem
```

Somente após essa comparação decidir entre:

```text
mesma EquationVariant
nova EquationVariant
atualização formal
validação
reformulação
nomenclatura secundária
```

---

## 9. O que já está certo — preservar, não regredir

- `developmentSampleMeanAge` tratado como puramente descritivo em praticamente todo arquivo — manter essa disciplina.

- Distinção `NOT_DOCUMENTED` vs. `INELIGIBLE` respeitada (campos de atleta/treino/modalidade ficam `null`, nunca viram restrição por omissão).

- `SEX` nunca aparece em `requiredInputs` em nenhum arquivo — está certo, manter.

- Populações de desenvolvimento e de validação sempre registradas como entradas separadas, nunca colapsadas em uma só.

- Nenhuma ficha herdou dado científico da `EquationFamily` — aplicabilidade sempre resolvida no nível da variante.

- Uso de `null` explícito em vez de inventar valor quando a fonte não informa.

- Conversões (Siri/Brozek) mantidas fora do conceito de `EquationVariant`.

- Variantes historicamente distintas não devem ser artificialmente fundidas apenas para reduzir a quantidade de registros.

- Uma única entrada pode ser reutilizada por diferentes equações quando a equivalência anatômica/protocolar tiver sido cientificamente estabelecida, como ocorreu na decisão `SKINFOLD_CHEST` → `SKINFOLD_PECTORAL`.

---

## 10. Checklist de revisão por arquivo

Rodar isto em cada ficha antes de aprovar:

```text
[ ] 19 seções do perfil científico, na ordem exata

[ ] Nenhum campo derivado (ageMatch, READY, INELIGIBLE etc.) na ficha

[ ] Todo inputId bate com a lista canônica

[ ] SEX fora de requiredInputs

[ ] AGE em requiredInputs somente se for variável matemática de fato

[ ] Toda Reference é objeto completo {citation, doi, url}

[ ] Todo url é string pura, sem Markdown

[ ] sourceConflict é null OU SourceConflictRecord completo — nunca string solta

[ ] sourceConflict não permanece UNRESOLVED em ficha ACTIVE

[ ] Amostra independente do MESMO estudo → validationStudies

[ ] Dados de OUTRO estudo/pesquisador → crossValidationStudies

[ ] Estudo totalmente independente → externalValidationStudies

[ ] developmentSampleMeanAge não é tratado como limite

[ ] Nada preenchido por inferência — ausência real fica null/[]

[ ] A fonte da equação realmente corresponde aos coeficientes documentados

[ ] A faixa etária corresponde à população da fonte da variante

[ ] Não houve confusão entre versões históricas da mesma família

[ ] Não houve substituição indevida de variante antiga por publicação posterior

[ ] A autoria/nomenclatura histórica foi verificada quando houver dúvida

[ ] A equivalência entre inputs foi cientificamente verificada antes de unificação
```

---

## 11. Requisito de Saída

Sempre que um `EquationVariantScientificProfile` for criado, revisado, corrigido ou refatorado, a versão final completa dessa ficha científica deve ser retornada como um **único documento Markdown (`.md`) completo**.

Não retornar somente seções modificadas, fragmentos parciais, diferenças (`diffs`) ou correções isoladas.

Sempre que uma fórmula for refeita, a saída deve conter **o documento inteiro atualizado**, preservando a estrutura estabelecida, a numeração das seções, a formatação e todos os campos obrigatórios.

O documento retornado deve estar completo e pronto para ser copiado diretamente para um arquivo `.md` independente.

---

## 12. Requisito de Idioma

Todo o conteúdo da documentação deve ser escrito exclusivamente em **português do Brasil**.

A documentação deve manter um único idioma de forma consistente. Não misturar português e inglês em textos descritivos, explicações, observações científicas, descrições de aplicabilidade, informações de validação, comentários ou qualquer outro conteúdo escrito em linguagem natural.

O inglês deve ser utilizado somente quando tecnicamente necessário, incluindo:

- Código.
- Identificadores.
- Nomes de variáveis.
- Constantes.
- Nomes de funções e métodos.
- Nomes de classes, interfaces, enums e tipos.
- Campos de API.
- Campos de banco de dados.
- Identificadores técnicos que fazem parte da implementação.
- Outros elementos relacionados à programação que seguem as convenções de nomenclatura em inglês do projeto.

Identificadores técnicos nunca devem ser traduzidos, localizados, renomeados ou substituídos por equivalentes em português. Sua grafia e capitalização originais devem ser preservadas exatamente conforme definidas pelo schema e pela implementação.

Quando um identificador técnico for mencionado na documentação, seu nome original em inglês deve ser preservado exatamente como utilizado na implementação.

Todo o texto explicativo ao redor desses identificadores deve permanecer em português.

Os títulos das seções, descrições, explicações, notas, regras, observações e demais conteúdos em linguagem natural devem permanecer em português.

A documentação final deve ser linguisticamente consistente e não deve conter mistura desnecessária entre português e inglês.
