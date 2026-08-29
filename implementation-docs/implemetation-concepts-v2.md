# Especificação Técnica — Avaliação Física

## Fluxo + Motor de Aplicabilidade, Prontidão, Cálculo e Sugestão

Documento para implementação. Onde houver dado científico não confirmado com fonte primária, está marcado `(confirmar)` ou `(validar)` — não codificar como regra científica definitiva antes da validação.

---

## 1. Princípio central

`Assessment` representa um **snapshot dos dados coletados em um determinado momento**.

`Calculator` representa uma **interpretação matemática desse snapshot**.

Uma mesma `Assessment` pode gerar vários cálculos utilizando diferentes `EquationVariant`, sem criar avaliações separadas.

Nenhuma variante é dona dos seus dados. Todas consomem os mesmos `Measurement` armazenados na `Assessment`.

### Consequência

O sistema deve separar:

```text
Dados coletados
      ↓
Aplicabilidade
      ↓
Candidatos possíveis
      ↓
Prontidão
      ↓
Sugestão
      ↓
Escolha profissional
      ↓
Cálculo
      ↓
Auditoria
```

A sugestão do sistema **não substitui a decisão do profissional**.

---

# 2. Conceitos fundamentais

| Conceito                    | Responsabilidade                                             |
| --------------------------- | ------------------------------------------------------------ |
| `Client`                    | Dados relativamente estáveis do cliente                      |
| `Context`                   | Objetivo, nível de treinamento, atleta, modalidade etc.      |
| `Assessment`                | Snapshot de uma coleta                                       |
| `Measurement`               | Medida individual coletada                                   |
| `EquationFamily`            | Agrupa variantes de uma metodologia                          |
| `EquationVariant`           | Configuração matemática executável                           |
| `ApplicabilityRule`         | Define condições científicas/profissionais de aplicabilidade |
| `PopulationProfile`         | População estudada/validada                                  |
| `FormulaDefinition`         | Fórmula matemática da variante                               |
| `InputDefinition`           | Entradas matemáticas exigidas                                |
| `ConversionDefinition`      | Conversões posteriores, como densidade → %G                  |
| `SuggestionResult`          | Resultado do motor de sugestão                               |
| `ProfessionalConfiguration` | Configuração e preferências do profissional                  |
| `ProfessionalSelection`     | Método efetivamente escolhido                                |
| `CalculationRun`            | Execução de um cálculo                                       |
| `AuditSnapshot`             | Registro imutável para reprodução histórica                  |

---

# 3. Modelo de dados

| Entidade                    | Campos                                                                                                                                                              |
| --------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `Client`                    | nome, dataNascimento, sexo, altura, contextoPadrao                                                                                                                  |
| `Context`                   | objetivo, nivelTreinamento, atleta, modalidade, observações                                                                                                         |
| `Assessment`                | id, clienteId, data, status, contexto, measurements[], calculations[], suggestion, professionalSelection                                                            |
| `Measurement`               | type, value, unit, side?, location?, timestamp, source                                                                                                              |
| `EquationFamily`            | id, name, methodType                                                                                                                                                |
| `EquationVariant`           | id, familyId, name, sexRule, requiredInputs[], version, status                                                                                                      |
| `FormulaDefinition`         | variantId, expression, coefficients, inputBindings, outputType, version                                                                                             |
| `InputDefinition`           | type, unit, required, derived, source, validationRule                                                                                                               |
| `PopulationProfile`         | country, description, sex, ageRange, trainingStatus, sampleSize, sampleCharacteristics, developmentOrValidation, validationStatus, referenceId                      |
| `ApplicabilityRule`         | variantId, ruleType, condition, action, severity, explanation                                                                                                       |
| `ConversionDefinition`      | name, inputType, outputType, expression, version                                                                                                                    |
| `ScientificReference`       | authors, title, year, publicationType, journal, identifier, sourceDocument                                                                                          |
| `ValidationEvidence`        | variantId, referenceId, criterion, sampleSize, sex, ageRange, population, errorMetric, bias, conclusion, status                                                     |
| `ProfessionalConfiguration` | professionalId, mode, enabledVariants[], preferredRules, institutionPolicy                                                                                          |
| `CalculationRun`            | assessmentId, variantId, inputSnapshot, predictionResult, conversionResult, version                                                                                 |
| `SuggestionResult`          | assessmentId, suggestedVariantId, score, reasonBreakdown, stage, engineVersion                                                                                      |
| `ProfessionalSelection`     | assessmentId, selectedVariantId, override, overrideReason                                                                                                           |
| `AuditSnapshot`             | assessmentId, equationVariantId, equationVersion, conversionVersion, inputsUsed, calculationResult, suggestedVariant, professionalChoice, overrideReason, timestamp |

### Regra de auditoria

Atualizar uma variante, regra ou conversão **nunca reescreve avaliações históricas**.

Nova versão = nova execução.

O `AuditSnapshot` deve preservar exatamente o que foi utilizado no cálculo original.

---

# 4. Perfil do cliente x contexto da avaliação

O sistema diferencia dados relativamente estáveis do cliente de informações que podem mudar entre avaliações.

## 4.1 Perfil

Exemplos:

```text
sexo
dataNascimento
altura
```

Esses dados pertencem ao `Client`.

A idade da avaliação é derivada:

```text
idade = data da avaliação - data de nascimento
```

Não deve ser armazenada como dado independente da avaliação.

---

## 4.2 Contexto

Exemplos:

```text
objetivo
nível de treinamento
atleta
modalidade
observações
```

O cliente pode possuir um contexto padrão:

```text
Client.contextoPadrao
```

Ao iniciar uma avaliação:

```text
Client.contextoPadrao
        ↓
Assessment.contexto
```

O contexto da avaliação pode ser editado pelo profissional.

Isso permite que duas avaliações do mesmo cliente tenham contextos diferentes.

---

# 5. Biblioteca global x configuração do profissional

A plataforma possui uma biblioteca global de métodos e variantes.

```text
Equation Library Global
        ↓
Professional Configuration
        ↓
Enabled Variants
        ↓
Motor de Aplicabilidade
        ↓
Motor de Sugestão
```

A biblioteca global pode conter:

```text
JP3
JP7
JP4
Guedes
Petroski
Faulkner
Durnin & Womersley
US Navy
IMC
BIA
...
```

O profissional não precisa utilizar todas.

### Exemplo

Profissional A:

```text
JP3
JP7
Guedes
Petroski
Faulkner
```

Profissional B:

```text
JP3
JP7
Durnin & Womersley
US Navy
```

A biblioteca científica continua global.

A configuração determina quais variantes participam do fluxo daquele profissional.

---

# 6. Personalização do motor

A personalização do profissional **não deve alterar a biblioteca científica global**.

Ela deve funcionar como uma camada de configuração.

## 6.1 Modo padrão

```text
mode = DEFAULT
```

Utiliza:

- variantes habilitadas por padrão;
- regras científicas cadastradas;
- regras de produto;
- critérios de sugestão padrão.

---

## 6.2 Modo personalizado

```text
mode = CUSTOM
```

Permite ao profissional:

- habilitar/desabilitar variantes;
- definir preferências;
- configurar regras institucionais;
- definir prioridades entre métodos, quando permitido;
- estabelecer critérios próprios documentados.

---

## 6.3 Restaurar padrão

O profissional deve poder executar:

```text
"Restaurar configurações padrão"
```

Isso remove as personalizações da configuração e retorna às regras padrão do sistema.

Importante:

> Restaurar a configuração não altera avaliações antigas nem cálculos já realizados.

Ele apenas modifica o comportamento de futuras sugestões.

---

# 7. Tipos de regra

Para evitar misturar ciência com preferência de produto, existem três camadas.

## 7.1 Regra científica

Baseada em fonte científica ou evidência documentada.

Exemplo conceitual:

```text
JP7 feminina
sexo = feminino
```

Ou:

```text
Durnin & Womersley
sexo + faixa etária
```

Essas regras pertencem à `ApplicabilityRule` e/ou `PopulationProfile`.

---

## 7.2 Regra do sistema

Regra de funcionamento do produto.

Exemplo:

```text
Entre variantes elegíveis, priorizar aquela
com maior qualidade de evidência cadastrada.
```

Essa regra não deve ser apresentada como verdade científica universal.

---

## 7.3 Preferência do profissional

Regra configurada pelo profissional.

Exemplo:

```text
Prioridade:
Petroski
JP7
Guedes
```

Ou:

```text
Não sugerir Faulkner
```

Isso não significa que Faulkner seja cientificamente inválido.

Significa apenas que o profissional não deseja utilizá-lo como candidato no seu fluxo.

---

# 8. Aplicabilidade científica

A aplicabilidade responde:

> **"Esta variante pode ser considerada para este perfil/contexto?"**

Ela não responde sozinha:

> **"Esta é a melhor fórmula."**

A avaliação da aplicabilidade deve considerar, conforme documentação científica disponível:

- sexo;
- idade;
- faixa etária;
- população estudada;
- treinamento;
- condição de atleta;
- modalidade;
- características da amostra;
- outras restrições documentadas.

---

## 8.1 Atleta, sedentário e nível de treinamento

O sistema deve armazenar:

```text
trainingLevel
athlete
modality
```

Essas informações **não devem automaticamente escolher uma fórmula**.

Exemplo incorreto:

```text
atleta → Petroski
sedentário → Guedes
```

Isso só pode virar uma regra se houver:

1. evidência científica que sustente a relação; ou
2. regra profissional explicitamente cadastrada.

Portanto:

```text
Atleta
   ↓
pode influenciar aplicabilidade/evidência/sugestão
   ↓
somente quando documentado
```

---

# 9. Dois momentos de sugestão

O motor possui dois momentos conceitualmente distintos.

## 9.1 Etapa 0 — pré-sugestão

Acontece **antes de qualquer medida da avaliação**.

Entrada:

```text
Perfil do cliente
+
Contexto da avaliação
+
Configuração do profissional
+
Regras de aplicabilidade
```

Saída:

```text
Métodos candidatos
+
Métodos inelegíveis
+
Motivos
```

Nesse momento o sistema ainda não sabe quais métodos podem ser executados, porque as medidas ainda não foram coletadas.

Portanto, a pré-sugestão representa:

> **"Quais métodos fazem sentido considerar e quais medidas/protocolos podem ser necessários?"**

---

## 9.2 Etapa 1 — elegibilidade/prontidão

Depois que existem medidas:

```text
Perfil
+
Contexto
+
Measurements
```

O sistema resolve os `requiredInputs`.

Cada variante recebe um estado:

```text
READY
MISSING_INPUTS
INELIGIBLE
DISABLED
```

---

## 9.3 Etapa 2 — sugestão refinada

Somente entre as variantes elegíveis/executáveis o sistema calcula o ranking.

Agora entram:

- medidas disponíveis;
- aderência à população;
- faixa etária;
- evidência;
- configuração profissional;
- regras documentadas;
- outros critérios permitidos.

Assim:

```text
Pré-sugestão
     ↓
Coleta de medidas
     ↓
Prontidão
     ↓
Sugestão refinada
```

---

# 10. Regra de exibição de campos

Na tela de coleta, os campos exibidos devem ser determinados pela união dos `requiredInputs` das variantes habilitadas pelo profissional.

```text
enabledVariants
       ↓
requiredInputs
       ↓
união dos campos
       ↓
filtro por sexo/regra aplicável
       ↓
campos disponíveis na avaliação
```

Isso acontece **antes da escolha da variante**.

### Exemplo

Profissional habilitou:

```text
Guedes 3D feminina
JP7 feminina
Petroski 4D feminina
Faulkner 4D
IMC
```

A tela pode apresentar:

```text
Peso
Altura

Tríceps
Subescapular
Supra-ilíaca
Abdominal
Peitoral
Axilar média
Coxa
Panturrilha medial
```

Não significa que todos esses campos sejam obrigatórios.

Significa apenas que algum método habilitado pode utilizá-los.

---

# 11. Comportamento de preenchimento

| Tipo                              | Comportamento                                  |
| --------------------------------- | ---------------------------------------------- |
| Perfil                            | preenchido automaticamente e travado           |
| Contexto                          | preenchido pelo padrão do perfil, mas editável |
| Medida anterior                   | começa vazia                                   |
| Medida anterior disponível        | possui opção "usar valor anterior"             |
| Medida já preenchida na avaliação | reutilizada automaticamente                    |
| Medida editada                    | continua pertencendo à mesma `Assessment`      |

### Valor de avaliação anterior

O botão aparece somente quando existe uma medida anterior correspondente.

Exemplo:

```text
Tríceps
[          ] [Usar 12 mm de 26/07]
```

Ao clicar:

```text
12 mm
```

é inserido no campo como valor normal e continua editável.

Se salvar sem alterar:

```text
source = AVALIACAO_ANTERIOR
```

Se alterar:

```text
source = MANUAL
```

---

# 12. Estados de prontidão

| Estado           | Significado                                    |
| ---------------- | ---------------------------------------------- |
| `READY`          | Todos os inputs necessários estão disponíveis  |
| `MISSING_INPUTS` | Variante aplicável, mas faltam medidas         |
| `INELIGIBLE`     | Variante não se aplica ao perfil/contexto      |
| `DISABLED`       | Variante não está habilitada pelo profissional |

### Importante

`MISSING_INPUTS` não significa que a fórmula seja inadequada.

Significa somente:

> A variante pode ser considerada, mas ainda não possui todos os dados necessários para execução.

---

# 13. Fluxo operacional completo

## 13.1 Cliente sem medidas anteriores

```text
1. Profissional abre cliente
        ↓
2. Sistema carrega perfil
        ↓
3. Sistema carrega contexto padrão
        ↓
4. Profissional confirma/edita contexto da avaliação
        ↓
5. Sistema executa pré-sugestão
        ↓
6. Sistema apresenta métodos candidatos
        ↓
7. Sistema apresenta medidas necessárias
        ↓
8. Profissional começa coleta
        ↓
9. Cada Measurement é salvo individualmente
        ↓
10. Sistema recalcula prontidão
        ↓
11. Sistema recalcula sugestão
        ↓
12. Profissional escolhe método
        ↓
13. Se faltar medida, solicita somente a medida faltante
        ↓
14. Profissional calcula
        ↓
15. Resultado é armazenado
        ↓
16. AuditSnapshot é criado
```

---

# 14. Fluxo quando o cliente já possui medidas anteriores

A existência de medidas anteriores **não significa que elas devem ser automaticamente copiadas para a nova avaliação**.

Ao iniciar:

```text
Nova Assessment
      ↓
Campos começam vazios
      ↓
Sistema verifica existência de valores anteriores
      ↓
Exibe "usar valor anterior" quando disponível
```

Se o profissional utilizar o valor anterior, ele passa a fazer parte da nova `Assessment`.

Depois disso, os dados ficam disponíveis para todas as variantes da avaliação.

---

# 15. Fluxo alternativo: profissional já sabe qual método quer

O sistema também deve permitir:

```text
Abrir JP7
   ↓
Ver medidas necessárias
   ↓
Preencher medidas
   ↓
Calcular
```

Isso não quebra o modelo.

A variante escolhida é apenas a **porta de entrada da interface**.

Os dados continuam sendo salvos na mesma `Assessment`.

A sugestão não deve ser alterada retroativamente para dizer que aquela variante foi "recomendada".

Deve permanecer:

```text
SuggestedVariant = método sugerido pelo sistema
ProfessionalSelection = método escolhido pelo profissional
```

---

# 16. Motor de sugestão

## 16.1 Pré-sugestão

Antes das medidas:

```text
Perfil
+
Contexto
+
Aplicabilidade
+
Configuração profissional
```

produz:

```text
Candidatos iniciais
```

Exemplo:

```text
JP7       candidato
JP3       candidato
Petroski  candidato
Guedes    candidato
Faulkner  candidato
```

---

## 16.2 Elegibilidade

Para cada candidato:

```text
sexo compatível?
idade compatível?
população?
regras específicas?
```

Se houver bloqueio:

```text
INELIGIBLE
```

Se for aplicável mas faltar medida:

```text
MISSING_INPUTS
```

Se tudo estiver disponível:

```text
READY
```

---

## 16.3 Ranking refinado

Somente variantes elegíveis/executáveis entram no ranking.

Pesos provisórios:

| Critério                                | Peso |
| --------------------------------------- | ---: |
| Aderência à população conhecida         |   30 |
| Faixa etária                            |   20 |
| Cobertura das medidas                   |   20 |
| Variáveis adicionais disponíveis        |   10 |
| Contexto profissional documentado       |   10 |
| Evidência local / qualidade do cadastro |   10 |

**Esses pesos são heurísticos de produto. Não representam um índice científico de precisão.**

---

# 17. Interface da primeira tela

A primeira tela da avaliação deve funcionar como um **painel de estado da avaliação**, não como uma calculadora isolada.

Exemplo conceitual:

```text
┌──────────────────────────────────────────────┐
│ NOVA AVALIAÇÃO                               │
│ João · Masculino · 44 anos                   │
│                                              │
│ Contexto                                     │
│ Hipertrofia · Avançado · Musculação          │
├──────────────────────────────────────────────┤
│ MÉTODO SUGERIDO                              │
│                                              │
│ JP7 masculina                                │
│ Faltam 7 medidas                             │
│ [Ver critérios]                              │
├──────────────────────────────────────────────┤
│ MÉTODOS                                      │
│                                              │
│ 🟢 IMC             READY                     │
│ 🟡 JP3             FALTAM MEDIDAS            │
│ 🟡 JP7             FALTAM MEDIDAS            │
│ 🟡 Petroski 4D     FALTAM MEDIDAS            │
│ 🟡 Guedes 3D       FALTAM MEDIDAS            │
│ 🔴 Variante X      INELEGÍVEL                │
├──────────────────────────────────────────────┤
│ MEDIDAS                                      │
│                                              │
│ Peso                [          ]              │
│ Tríceps             [          ]              │
│ Subescapular       [          ]              │
│ ...                                          │
└──────────────────────────────────────────────┘
```

A tela deve permitir ao profissional entender rapidamente:

1. o que o sistema considera aplicável;
2. quais métodos estão prontos;
3. quais ainda precisam de medidas;
4. qual método está sendo sugerido;
5. por que ele está sendo sugerido.

---

# 18. Biblioteca de variantes

Matriz preliminar.

| Família / variante    | Sexo                  | Required Inputs                                                              | Output               | Status                                        |
| --------------------- | --------------------- | ---------------------------------------------------------------------------- | -------------------- | --------------------------------------------- |
| Faulkner 4D           | M/F `(confirmar)`     | tríceps, subescapular, supra-ilíaca, abdominal                               | `%G`                 | Fórmula reconstruída; fonte a confirmar       |
| JP 3D masc.           | M                     | peitoral, abdominal, coxa                                                    | densidade            | `(validar fonte/versão)`                      |
| JP 3D fem.            | F                     | tríceps, supra-ilíaca, coxa                                                  | densidade            | `(validar fonte/versão)`                      |
| JP 7D masc.           | M                     | peitoral, axilar média, tríceps, subescapular, abdominal, supra-ilíaca, coxa | densidade            | `(validar fonte/versão)`                      |
| JP 7D fem.            | F                     | peitoral, axilar média, tríceps, subescapular, abdominal, supra-ilíaca, coxa | densidade            | Caso real reproduzido; referência a confirmar |
| JP 4D                 | M/F `(confirmar)`     | abdominal, tríceps, coxa, supra-ilíaca                                       | `(confirmar)`        | Confirmar uso                                 |
| Guedes 3D masc.       | M                     | tríceps, supra-ilíaca, abdome                                                | densidade            | `(confirmar versão/ano)`                      |
| Guedes 3D fem.        | F                     | subescapular, supra-ilíaca, coxa                                             | densidade            | `(confirmar versão/ano)`                      |
| Petroski 4D masc.     | M                     | tríceps, subescapular, supra-ilíaca, panturrilha medial                      | densidade            | `(confirmar variante)`                        |
| Petroski 4D fem.      | F                     | subescapular, tríceps, supra-ilíaca, panturrilha medial                      | densidade            | `(confirmar variante)`                        |
| Durnin & Womersley 4D | M/F por faixa         | 4 dobras                                                                     | densidade            | Mapear variantes por faixa                    |
| US Navy               | conforme método       | circunferências + antropometria                                              | `%G`                 | Método separado de dobras                     |
| IMC                   | M/F                   | peso, altura                                                                 | IMC                  | Métrica derivada                              |
| BIA                   | modelo do equipamento | impedância + dados do dispositivo                                            | conforme dispositivo | Método próprio                                |

---

# 19. Regra de cadastro da variante

Nunca armazenar somente:

```text
Jackson & Pollock
```

ou:

```text
Guedes
```

como identidade matemática.

O cálculo deve apontar para uma variante específica:

```text
EquationFamily
      ↓
EquationVariant
      ↓
FormulaDefinition
      ↓
version
```

Exemplo:

```text
Jackson & Pollock
    └── JP7 Female
          └── Formula v1
```

Isso permite coexistirem diferentes versões e variantes sem ambiguidade.

---

# 20. Inputs e variáveis derivadas

A variante deve declarar exatamente suas entradas.

Exemplos:

```text
AGE
SEX
HEIGHT
WEIGHT
SKINFOLD
CIRCUMFERENCE
BMI
SUM_SKINFOLDS
```

Uma variável pode ser derivada.

Exemplo:

```text
BMI
derived = true
source = [WEIGHT, HEIGHT]
```

A plataforma calcula:

```text
WEIGHT + HEIGHT
      ↓
BMI
      ↓
Formula
```

Não é necessário armazenar o BMI como uma medida manual independente.

---

# 21. Cadeia de cálculo

```text
Raw Measurements
       ↓
Input Resolution
       ↓
Equation Variant
       ↓
Prediction Output
       ↓
Optional Conversion
       ↓
Final Result
       ↓
Audit Snapshot
```

## Exemplos

### Equação que produz densidade

```text
JP7
 ↓
Densidade corporal
 ↓
Siri
 ↓
%G
```

### Equação que produz %G diretamente

```text
Faulkner 4D
 ↓
%G
```

A plataforma nunca deve presumir que toda densidade deve passar por Siri.

A conversão deve ser explícita:

```text
ConversionDefinition
```

---

# 22. Elegibilidade

A elegibilidade deve ser separada do ranking.

| Critério                                | Tipo                           |
| --------------------------------------- | ------------------------------ |
| Sexo incompatível                       | Bloqueio                       |
| Idade obrigatória ausente               | Bloqueio                       |
| Medida obrigatória ausente              | `MISSING_INPUTS`               |
| Faixa etária fora da população estudada | Alerta ou regra específica     |
| População diferente                     | Alerta/evidência               |
| Atleta/treinamento                      | Alerta ou regra documentada    |
| Modalidade                              | Alerta ou regra documentada    |
| Extremos de composição                  | Alerta, quando documentado     |
| Qualidade/protocolo                     | Alerta/bloqueio conforme regra |
| Preferência profissional                | Sugestão                       |
| Escolha manual                          | Override                       |

### Regra importante

Score nunca pode transformar:

```text
MISSING_INPUTS
```

em:

```text
READY
```

e nunca pode transformar:

```text
INELIGIBLE
```

em:

```text
elegível
```

---

# 23. Sugestão x decisão profissional

O sistema deve distinguir claramente:

```text
SuggestedVariant
```

de:

```text
ProfessionalSelection
```

Exemplo:

```text
Sistema sugeriu:
JP7

Profissional escolheu:
Petroski 4D
```

Nesse caso:

```text
suggestedVariant = JP7
selectedVariant = Petroski 4D
override = true
```

Se necessário:

```text
overrideReason = "Preferência profissional"
```

A sugestão nunca bloqueia a escolha profissional.

---

# 24. Cálculo

O cálculo deve ser uma ação explícita.

Fluxo:

```text
READY
  ↓
Profissional escolhe variante
  ↓
[Calcular]
  ↓
CalculationRun
  ↓
Resultado
```

O sistema não deve calcular automaticamente apenas porque uma variante ficou `READY`.

O recálculo automático é aplicado ao:

```text
estado de prontidão
+
SuggestionResult
```

e não ao resultado matemático final.

---

# 25. Reutilização entre variantes

Exemplo:

```text
Assessment
│
├── Tríceps = 12 mm
├── Subescapular = 15 mm
├── Supra-ilíaca = 18 mm
├── Abdominal = 20 mm
├── Coxa = 16 mm
└── ...
```

Essas medidas podem ser utilizadas por:

```text
JP3
JP7
Guedes
Petroski
Faulkner
```

sem duplicação.

A variante apenas declara:

```text
requiredInputs
```

e o sistema resolve essas entradas a partir da `Assessment`.

---

# 26. Golden tests

| ID                    | Entrada                          | Esperado                                           | Status                                   |
| --------------------- | -------------------------------- | -------------------------------------------------- | ---------------------------------------- |
| `GOLDEN-JP7-F-001`    | sexo=F, idade=44, sum7=60mm      | densidade=1.06518908; `%G≈14.7062%`; display=14.7% | Reprodução forte; referência a confirmar |
| `GOLDEN-FAULKNER-001` | sum4=17mm                        | `%G = 5,783 + 0,153×soma = 8.384%`; display=8.4%   | Reprodução forte; fonte a confirmar      |
| `REGRESSION-002`      | idade=44, peso=70kg, sum7=90mm   | reproduzir quando variante identificada            | Investigação                             |
| `REGRESSION-003`      | idade=44, peso=64,7kg, sum7=79mm | reproduzir quando variante identificada            | Investigação                             |

---

# 27. Auditoria

Cada cálculo deve preservar:

```text
assessmentId
equationVariantId
equationVersion
conversionVersion
inputsUsed
predictionResult
conversionResult
calculationResult
suggestedVariant
professionalChoice
overrideReason
timestamp
```

Uma alteração futura na biblioteca não deve alterar o resultado histórico.

Exemplo:

```text
2026
JP7 v1
↓
resultado 14,7%

2027
JP7 v2
↓
novo cálculo
```

O resultado de 2026 continua vinculado à `JP7 v1`.

---

# 28. Regras de engenharia não negociáveis

- Não hardcodar seleção por nome de família.
- Sempre utilizar `EquationVariant` versionada.
- Não duplicar medidas brutas por fórmula.
- Não misturar aplicabilidade, elegibilidade e ranking.
- Não usar score para liberar variante matematicamente inexequível.
- Não presumir Siri.
- Não transformar engenharia reversa em fonte científica.
- Não tratar "Brasil" como população suficiente por si só.
- Não assumir que atleta/treinamento determina automaticamente uma fórmula.
- Separar regras científicas de preferências profissionais.
- Permitir configuração personalizada sem alterar a biblioteca global.
- Permitir restaurar configuração padrão.
- Versionar equação, conversão e motor de sugestão separadamente.
- Manter `SuggestedVariant` e `ProfessionalSelection` separados.
- Manter golden tests para casos reais.
- Preservar `AuditSnapshot` das execuções históricas.

---

# 29. Estados conceituais do sistema

Uma variante pode passar por:

```text
DISABLED
   │
   ▼
INELIGIBLE
   │
   ▼
MISSING_INPUTS
   │
   ▼
READY
   │
   ▼
CALCULATED
```

Esses estados não significam necessariamente que a variante "melhorou".

Eles representam o quanto o sistema consegue avançar com os dados e regras atuais.

---

# 30. Exemplo completo

Cliente:

```text
Sexo: masculino
Idade: 44
Altura: 1,80m
Objetivo: hipertrofia
Treinamento: avançado
Atleta: não
Modalidade: musculação
```

### Antes das medidas

O sistema avalia:

```text
Perfil
+
Contexto
+
Regras científicas
+
Configuração profissional
```

Resultado:

```text
JP3       candidato
JP7       candidato
Guedes    candidato
Petroski  candidato
Faulkner  candidato
```

Nenhum método de dobras está `READY`, porque ainda não existem as medidas.

O sistema pode informar:

> "Para executar JP7 são necessárias 7 dobras."

---

### Após algumas medidas

O profissional coleta:

```text
Tríceps
Subescapular
Supra-ilíaca
Abdominal
Coxa
```

O sistema recalcula:

```text
JP3       READY
JP7       MISSING_INPUTS
Guedes    READY
Petroski  MISSING_INPUTS
Faulkner  READY
```

A sugestão é recalculada apenas entre os métodos elegíveis/executáveis.

---

### Profissional escolhe Petroski

O sistema identifica:

```text
Petroski
↓
faltando panturrilha medial
```

Solicita somente:

```text
Panturrilha medial
```

Depois:

```text
Petroski
↓
READY
```

O profissional calcula.

---

### Depois

O profissional pode calcular:

```text
Petroski
Guedes
JP3
Faulkner
```

utilizando as mesmas medidas.

Não são criadas quatro avaliações.

Existe:

```text
1 Assessment
    ├── CalculationRun Petroski
    ├── CalculationRun Guedes
    ├── CalculationRun JP3
    └── CalculationRun Faulkner
```

---

# 31. Fluxo final do produto

```text
                    CLIENT
                       │
                       ▼
              PERFIL + CONTEXTO
                       │
                       ▼
          APLICABILIDADE CIENTÍFICA
                       │
                       ▼
             PRÉ-SUGESTÃO
          "O que faz sentido considerar?"
                       │
                       ▼
               NOVA ASSESSMENT
                       │
                       ▼
                COLETA DE DADOS
                       │
             ┌─────────┴─────────┐
             │                   │
             ▼                   ▼
        Measurement         Reutilização
             │              de anterior
             └─────────┬─────────┘
                       ▼
              PRONTIDÃO DOS MÉTODOS
                       │
          ┌────────────┼────────────┐
          ▼            ▼            ▼
        READY       MISSING      INELIGIBLE
          │         INPUTS
          │            │
          │            └── solicitar
          │                somente o faltante
          │
          ▼
          SUGESTÃO REFINADA
                       │
                       ▼
            ESCOLHA PROFISSIONAL
                       │
                       ▼
                   CALCULAR
                       │
                       ▼
                  RESULTADO
                       │
                       ▼
                AUDIT SNAPSHOT
```

---

# 32. Modelo mental definitivo

A lógica principal da plataforma pode ser resumida em cinco perguntas:

### 1. O que eu sei sobre essa pessoa?

```text
Perfil + Contexto
```

### 2. Quais métodos fazem sentido considerar?

```text
Aplicabilidade + configuração profissional
```

### 3. O que já consigo executar?

```text
Measurements + RequiredInputs
```

### 4. Entre os executáveis, o que o sistema sugere?

```text
SuggestionEngine
```

### 5. O que o profissional realmente decidiu?

```text
ProfessionalSelection
```

Portanto:

> **Aplicabilidade não é recomendação.**
>
> **Recomendação não é escolha profissional.**
>
> **Escolha profissional não é cálculo.**
>
> **Cálculo não é a avaliação inteira.**

A `Assessment` é o snapshot que conecta todas essas etapas.
