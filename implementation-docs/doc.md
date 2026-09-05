# Especificação Técnica — Avaliação Física

## Fluxo + Motor de Aplicabilidade, Prontidão, Cálculo, Sugestão e Conversão

Documento para implementação.

Onde houver dado científico não confirmado com fonte primária, está marcado `(confirmar)` ou `(validar)` — não codificar como regra científica definitiva antes da validação.

---

# 1. Princípio central

`Assessment` representa um **snapshot dos dados coletados em um determinado momento**.

`Calculator` representa uma **interpretação matemática desse snapshot**.

Uma mesma `Assessment` pode gerar vários cálculos utilizando diferentes `EquationVariant`, sem criar avaliações separadas.

Nenhuma variante é dona dos seus dados. Todas consomem os mesmos `Measurement` armazenados na `Assessment`.

A cadeia de processamento é:

```text
Dados coletados
      ↓
Aplicabilidade
      ↓
Candidatos possíveis
      ↓
Prontidão
      ↓
Sugestão de método
      ↓
Escolha profissional
      ↓
Cálculo da variante
      ↓
PredictionResult
      ↓
Aplicabilidade de conversão
      ↓
Sugestão de conversão
      ↓
Escolha profissional da conversão
      ↓
Resultado final
      ↓
Auditoria
```

A sugestão do sistema **não substitui a decisão do profissional**.

A mesma filosofia se aplica tanto à escolha da `EquationVariant` quanto à escolha da `ConversionDefinition`.

---

# 2. Conceitos fundamentais

| Conceito                      | Responsabilidade                                             |
| ----------------------------- | ------------------------------------------------------------ |
| `Client`                      | Dados relativamente estáveis do cliente                      |
| `Context`                     | Objetivo, nível de treinamento, atleta, modalidade etc.      |
| `Assessment`                  | Snapshot de uma coleta                                       |
| `Measurement`                 | Medida individual coletada                                   |
| `EquationFamily`              | Agrupa variantes de uma metodologia                          |
| `EquationVariant`             | Configuração matemática executável                           |
| `ApplicabilityRule`           | Define condições científicas/profissionais de aplicabilidade |
| `PopulationProfile`           | População estudada/validada                                  |
| `FormulaDefinition`           | Fórmula matemática da variante                               |
| `InputDefinition`             | Entradas matemáticas exigidas                                |
| `ConversionDefinition`        | Conversão matemática posterior a um `PredictionResult`       |
| `ConversionApplicabilityRule` | Define condições em que uma conversão pode ser considerada   |
| `SuggestionResult`            | Resultado do motor de sugestão da variante                   |
| `ConversionSuggestionResult`  | Resultado do motor de sugestão da conversão                  |
| `ProfessionalConfiguration`   | Configuração e preferências do profissional                  |
| `ProfessionalSelection`       | Método efetivamente escolhido                                |
| `ConversionSelection`         | Conversão efetivamente escolhida                             |
| `CalculationRun`              | Execução de um cálculo                                       |
| `AuditSnapshot`               | Registro imutável para reprodução histórica                  |
| `ScientificReference`         | Fonte científica associada                                   |
| `ValidationEvidence`          | Evidência usada para avaliar uma variante ou conversão       |

A separação fundamental é:

```text
EquationVariant
    ↓
calcula um PredictionResult

ConversionDefinition
    ↓
transforma um PredictionResult em outro tipo de resultado
```

Portanto:

```text
Jackson & Pollock
Guedes
Petroski
Faulkner
```

são `EquationVariant`.

Enquanto:

```text
Siri
Brozek
```

são `ConversionDefinition`.

---

# 3. Modelo de dados

| Entidade                      | Campos                                                                                                                                                                                                                                                                        |
| ----------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `Client`                      | nome, dataNascimento, sexo, altura, contextoPadrao                                                                                                                                                                                                                            |
| `Context`                     | objetivo, nivelTreinamento, atleta, modalidade, observações                                                                                                                                                                                                                   |
| `Assessment`                  | id, clienteId, data, status, contexto, measurements[], calculations[], suggestion, professionalSelection, conversionSuggestions, conversionSelection                                                                                                                          |
| `Measurement`                 | type, value, unit, side?, location?, timestamp, source                                                                                                                                                                                                                        |
| `EquationFamily`              | id, name, methodType                                                                                                                                                                                                                                                          |
| `EquationVariant`             | id, familyId, name, sexRule, requiredInputs[], version, status                                                                                                                                                                                                                |
| `FormulaDefinition`           | variantId, expression, coefficients, inputBindings, outputType, version                                                                                                                                                                                                       |
| `InputDefinition`             | type, unit, required, derived, source, validationRule                                                                                                                                                                                                                         |
| `PopulationProfile`           | country, description, sex, ageRange, trainingStatus, sampleSize, sampleCharacteristics, developmentOrValidation, validationStatus, referenceId                                                                                                                                |
| `ApplicabilityRule`           | variantId, ruleType, condition, action, severity, explanation                                                                                                                                                                                                                 |
| `ConversionDefinition`        | id, name, inputType, outputType, expression, applicabilityRules[], version, status                                                                                                                                                                                            |
| `ConversionApplicabilityRule` | conversionId, ruleType, condition, action, severity, explanation                                                                                                                                                                                                              |
| `ScientificReference`         | authors, title, year, publicationType, journal, identifier, sourceDocument                                                                                                                                                                                                    |
| `ValidationEvidence`          | variantId/conversionId, referenceId, criterion, sampleSize, sex, ageRange, population, errorMetric, bias, conclusion, status                                                                                                                                                  |
| `ProfessionalConfiguration`   | professionalId, mode, enabledVariants[], enabledConversions[], preferredRules, institutionPolicy                                                                                                                                                                              |
| `CalculationRun`              | assessmentId, variantId, equationVersion, inputSnapshot, predictionResult, conversionId?, conversionVersion?, conversionResult?, calculationResult, suggestionSnapshot, professionalSelectionSnapshot, timestamp                                                              |
| `SuggestionResult`            | assessmentId, suggestedVariantId, score, reasonBreakdown, stage, engineVersion                                                                                                                                                                                                |
| `ConversionSuggestionResult`  | assessmentId, conversionCandidates, suggestedConversionId, score, reasonBreakdown, stage, engineVersion                                                                                                                                                                       |
| `ProfessionalSelection`       | assessmentId, selectedVariantId, override, overrideReason                                                                                                                                                                                                                     |
| `ConversionSelection`         | assessmentId, selectedConversionId, override, overrideReason                                                                                                                                                                                                                  |
| `AuditSnapshot`               | assessmentId, equationVariantId, equationVersion, inputsUsed, predictionResult, conversionId?, conversionVersion?, conversionResult?, calculationResult, suggestedVariant, professionalChoice, suggestedConversion?, professionalConversionChoice?, overrideReason, timestamp |

## Regra de auditoria

Atualizar uma variante, regra ou conversão **nunca reescreve avaliações históricas**.

Nova versão = nova execução.

O `AuditSnapshot` deve preservar exatamente o que foi utilizado no cálculo original.

Isso vale para:

```text
EquationVariant
FormulaDefinition
ApplicabilityRule
ConversionDefinition
ConversionApplicabilityRule
SuggestionEngine
ConversionSuggestionEngine
```

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

A plataforma possui uma biblioteca global de métodos, variantes e conversões.

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

Para conversões:

```text
Conversion Library Global
        ↓
Professional Configuration
        ↓
Enabled Conversions
        ↓
Motor de Aplicabilidade de Conversão
        ↓
ConversionSuggestionEngine
```

A biblioteca científica atual contém exclusivamente:

```text
Jackson & Pollock
Guedes
Petroski
Faulkner
```

e:

```text
Siri
Brozek
```

como conversões.

O profissional não precisa utilizar todas.

### Exemplo

Profissional A:

```text
JP3
JP7
Guedes
Petroski
Faulkner

Conversões:
Siri
Brozek
```

Profissional B:

```text
JP3
JP7
Guedes

Conversões:
Brozek
```

A biblioteca científica continua global.

A configuração determina quais variantes e conversões participam do fluxo daquele profissional.

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
- **Siri como conversão padrão da plataforma, quando o `PredictionResult` for compatível e Siri estiver elegível**;
- Brozek quando habilitado e elegível;
- regras científicas cadastradas;
- regras de produto;
- critérios de sugestão padrão;
- critérios padrão do `ConversionSuggestionEngine`.

### Regra do default de conversão

Para resultados do tipo `BODY_DENSITY`, a plataforma utilizará **Siri como default operacional** quando:

1. Siri estiver habilitada;
2. Siri for elegível para aquele `PredictionResult`;
3. o profissional não tiver definido outra preferência de conversão.

Isso significa:

```text
defaultConversion = Siri
```

Essa definição representa uma **política operacional do produto** e não uma afirmação de que Siri seja universalmente superior a Brozek.

Brozek continua fazendo parte da biblioteca e pode ser escolhido pelo profissional quando elegível.

---

## 6.2 Modo personalizado

```text
mode = CUSTOM
```

Permite ao profissional:

- habilitar/desabilitar variantes;
- habilitar/desabilitar conversões;
- definir preferências;
- configurar regras institucionais;
- definir prioridades entre métodos, quando permitido;
- definir prioridades entre conversões, quando permitido;
- estabelecer critérios próprios documentados;
- **definir outra conversão como preferência, substituindo o default operacional da plataforma**.

Exemplo:

```text
preferredConversion = Brozek
```

Nesse caso:

```text
DEFAULT → Siri
CUSTOM  → Brozek
```

A preferência do profissional não transforma Brozek em um método universalmente superior. Ela apenas define o comportamento preferencial daquele fluxo profissional.

---

## 6.3 Restaurar padrão

O profissional deve poder executar:

```text
"Restaurar configurações padrão"
```

Isso remove as personalizações da configuração e retorna às regras padrão do sistema.

Importante:

> Restaurar a configuração não altera avaliações antigas nem cálculos já realizados.

Ele apenas modifica o comportamento de futuras sugestões de variantes e conversões.

Ao restaurar o padrão de conversão:

```text
preferredConversion = none
```

A plataforma volta a utilizar:

```text
Siri
```

quando Siri estiver elegível e habilitada.

---

# 7. Tipos de regra

Para evitar misturar ciência com preferência de produto, existem camadas distintas.

## 7.1 Regra científica

Baseada em fonte científica ou evidência documentada.

Exemplos:

```text
JP7 feminina
sexo = feminino
```

ou:

```text
JP7 masculina
sexo = masculino
```

Essas regras pertencem à `ApplicabilityRule` e/ou `PopulationProfile`.

Para conversões:

```text
ConversionApplicabilityRule
```

define em quais condições uma conversão pode ser considerada.

---

## 7.2 Regra do sistema

Regra de funcionamento do produto.

Exemplo:

```text
Entre variantes elegíveis, priorizar aquela
com maior qualidade de evidência cadastrada.
```

Ou:

```text
Quando Siri estiver elegível e não existir
preferência profissional, utilizar Siri como
default operacional.
```

Essas regras não devem ser apresentadas como verdade científica universal.

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

Para conversões:

```text
Prioridade:

Brozek
Siri
```

Ou:

```text
Não sugerir Siri
```

Isso não significa que o método ou a conversão excluída seja cientificamente inválida.

Significa apenas que o profissional não deseja utilizá-la como candidata no seu fluxo.

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

Para conversões existe uma pergunta equivalente:

> **"Esta conversão pode ser considerada para este tipo de resultado?"**

A conversão deve verificar, conforme evidência disponível:

- tipo de entrada;
- tipo de saída;
- contexto de utilização;
- premissas da equação;
- eventuais restrições documentadas.

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

A mesma lógica vale para conversões.

O sistema não deve assumir:

```text
Densidade → Siri
```

simplesmente porque esse é um fluxo historicamente comum.

A conversão deve ser explicitamente cadastrada e elegível.

---

# 9. Dois momentos de sugestão

O motor de método possui dois momentos conceitualmente distintos.

O mecanismo de conversão possui um fluxo análogo, porém ocorre **depois que existe um `PredictionResult` compatível**.

---

## 9.1 Etapa 0 — pré-sugestão de método

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

## 9.2 Etapa 1 — elegibilidade/prontidão de método

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

## 9.3 Etapa 2 — sugestão refinada de método

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

## 9.4 Etapa 3 — sugestão de conversão

Depois que uma `EquationVariant` foi executada e produziu um `PredictionResult`, o sistema verifica se existe uma conversão compatível.

Exemplo:

```text
JP7

↓

PredictionResult

↓

BODY_DENSITY
```

Nesse momento:

```text
ConversionSuggestionEngine
```

recebe:

```text
PredictionResult

+

Conversion Library

+

Configuração profissional

+

Regras de aplicabilidade da conversão
```

e produz:

```text
Conversões candidatas

+

Conversões inelegíveis

+

Motivos

+

SuggestedConversion
```

Para a biblioteca atual:

```text
Siri     candidata
Brozek   candidata
```

quando ambas estiverem habilitadas e elegíveis.

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

## Valor de avaliação anterior

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

Em ambos os casos o valor passa a fazer parte da nova `Assessment`.

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

Esses estados pertencem à **prontidão da `EquationVariant`**.

As conversões possuem estados próprios, definidos posteriormente no fluxo de conversão, porque não dependem necessariamente de novas medidas.

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
15. PredictionResult é produzido
        ↓
16. Sistema verifica conversões disponíveis
        ↓
17. ConversionSuggestionEngine avalia candidatos
        ↓
18. Sistema sugere conversão, quando aplicável
        ↓
19. Profissional escolhe conversão
        ↓
20. Conversão é executada, quando necessária
        ↓
21. Resultado final é armazenado
        ↓
22. AuditSnapshot é criado
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

Depois do cálculo, caso o método produza um tipo intermediário, a conversão será resolvida separadamente:

```text
PredictionResult
      ↓
ConversionSuggestionEngine
      ↓
SuggestedConversion
      ↓
ProfessionalConversionSelection
```

---

# 16. Motor de sugestão

## 16.1 Pré-sugestão de método

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
JP3       candidato
JP7       candidato
Guedes    candidato
Petroski  candidato
Faulkner  candidato
```

---

## 16.2 Elegibilidade de método

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

## 16.3 Ranking refinado de método

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

## 16.4 ConversionSuggestionEngine

O `ConversionSuggestionEngine` é responsável exclusivamente por decidir quais `ConversionDefinition` são candidatas e qual delas o sistema sugere.

Ele não escolhe a `EquationVariant`.

Entrada:

```text
PredictionResult

+

ConversionDefinitions habilitadas

+

Regras de aplicabilidade

+

Configuração profissional
```

Saída:

```text
ConversionSuggestionResult
```

Exemplo:

```text
PredictionResult

type = BODY_DENSITY

value = 1.06518908
```

O motor consulta:

```text
Siri
Brozek
```

e considera, nesta ordem lógica:

```text
1. elegibilidade

2. preferência profissional, quando existir

3. default operacional da plataforma

4. demais critérios de ranking
```

Quando não existir preferência profissional e Siri estiver elegível:

```text
SuggestedConversion = Siri
```

Quando o profissional tiver configurado Brozek como preferência e Brozek estiver elegível:

```text
SuggestedConversion = Brozek
```

A sugestão deve continuar apresentando os critérios utilizados.

---

## 16.5 Exemplo de ranking de conversão

Pesos provisórios:

| Critério                           | Peso |
| ---------------------------------- | ---: |
| Compatibilidade entre input/output |   30 |
| Evidência cadastrada               |   25 |
| Aderência ao contexto documentado  |   20 |
| População/contexto de validação    |   15 |
| Preferência profissional           |   10 |

Esses pesos são heurísticos de produto e **não representam um ranking científico universal de superioridade entre conversões**.

A preferência profissional pode alterar a sugestão dentro do conjunto de conversões elegíveis.

Quando não houver preferência profissional específica, o default operacional da plataforma é Siri, desde que Siri seja elegível.

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
│ 🟢 JP3             READY                      │
│ 🟡 JP7             FALTAM MEDIDAS             │
│ 🟡 Petroski 4D     FALTAM MEDIDAS             │
│ 🟡 Guedes 3D       FALTAM MEDIDAS             │
│ 🟡 Faulkner 4D     FALTAM MEDIDAS             │
│ 🔴 Variante X      INELEGÍVEL                 │
├──────────────────────────────────────────────┤
│ MEDIDAS                                      │
│                                              │
│ Peso               [          ]              │
│ Tríceps            [          ]              │
│ Subescapular       [          ]              │
│ ...                                          │
└──────────────────────────────────────────────┘
```

Depois que uma variante produzir um resultado intermediário, pode existir uma área adicional:

```text
┌──────────────────────────────────────────────┐
│ CONVERSÃO DO RESULTADO                       │
│                                              │
│ Resultado: Densidade corporal                │
│ 1.06518908                                    │
│                                              │
│ Conversão sugerida: Siri                     │
│ Alternativas: Brozek                         │
│                                              │
│ [Ver critérios] [Escolher conversão]         │
└──────────────────────────────────────────────┘
```

A tela deve permitir ao profissional entender rapidamente:

1. o que o sistema considera aplicável;
2. quais métodos estão prontos;
3. quais ainda precisam de medidas;
4. qual método está sendo sugerido;
5. por que ele está sendo sugerido;
6. quando existe um resultado intermediário, quais conversões podem ser utilizadas;
7. qual conversão o sistema sugere;
8. por que essa conversão foi sugerida.

---

# 18. Biblioteca de variantes

A biblioteca atual contém somente as variantes abaixo.

| Família                 | ID      |  Sexo | Nº dobras | Dobras / variáveis                                                                 | Fórmula                                                                                                      | Referência                     |
| ----------------------- | ------- | ----: | --------: | ---------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------ | ------------------------------ |
| Jackson & Pollock       | `JP7-M` |     M |         7 | Peitoral + axilar média + tríceps + subescapular + abdominal + supra-ilíaca + coxa | `D = 1,112 − 0,00043499·Σ7 + 0,00000055·Σ7² − 0,00028826·idade`                                              | Jackson & Pollock (1978)       |
| Jackson, Pollock & Ward | `JP7-F` |     F |         7 | Peitoral + axilar média + tríceps + subescapular + abdominal + supra-ilíaca + coxa | `D = 1,097 − 0,00046971·Σ7 + 0,00000056·Σ7² − 0,00012828·idade`                                              | Jackson, Pollock & Ward (1980) |
| Jackson & Pollock       | `JP3-M` |     M |         3 | Peitoral + abdominal + coxa                                                        | `D = 1,10938 − 0,0008267·Σ3 + 0,0000016·Σ3² − 0,0002574·idade`                                               | Jackson & Pollock (1978)       |
| Jackson, Pollock & Ward | `JP3-F` |     F |         3 | Tríceps + supra-ilíaca + coxa                                                      | `D = 1,0994921 − 0,0009929·Σ3 + 0,0000023·Σ3² − 0,0001392·idade`                                             | Jackson, Pollock & Ward (1980) |
| Guedes                  | `G-M3`  |     M |         3 | Abdômen + tríceps + supra-ilíaca                                                   | `D = 1,17136 − 0,06706·log₁₀(AB + TR + SI)`                                                                  | Guedes (1985)                  |
| Guedes                  | `G-M4`  |     M |         4 | Abdômen + tríceps + supra-ilíaca + axilar média                                    | `D = 1,18282 − 0,07030·log₁₀(AB + TR + SI + AM)`                                                             | Guedes (1985)                  |
| Guedes                  | `G-M5`  |     M |         5 | AB + TR + SI + AM + SE                                                             | `D = 1,20436 − 0,07848·log₁₀(AB + TR + SI + AM + SE)`                                                        | Guedes (1985)                  |
| Guedes                  | `G-M6`  |     M |         6 | AB + TR + SI + AM + SE + CX                                                        | `D = 1,21546 − 0,08119·log₁₀(AB + TR + SI + AM + SE + CX)`                                                   | Guedes (1985)                  |
| Guedes                  | `G-M7`  |     M |         7 | AB + TR + SI + AM + SE + CX + PM                                                   | `D = 1,22098 − 0,08214·log₁₀(AB + TR + SI + AM + SE + CX + PM)`                                              | Guedes (1985)                  |
| Guedes                  | `G-M8`  |     M |         8 | AB + TR + SI + AM + SE + CX + PM + BI                                              | `D = 1,22627 − 0,08384·log₁₀(AB + TR + SI + AM + SE + CX + PM + BI)`                                         | Guedes (1985)                  |
| Guedes                  | `G-F3`  |     F |         3 | Supra-ilíaca + coxa + subescapular                                                 | `D = 1,16650 − 0,07063·log₁₀(SI + CX + SE)`                                                                  | Guedes (1985)                  |
| Guedes                  | `G-F4`  |     F |         4 | SI + CX + SE + TR                                                                  | `D = 1,18452 − 0,07508·log₁₀(SI + CX + SE + TR)`                                                             | Guedes (1985)                  |
| Guedes                  | `G-F5`  |     F |         5 | SI + CX + SE + TR + BI                                                             | `D = 1,18588 − 0,07417·log₁₀(SI + CX + SE + TR + BI)`                                                        | Guedes (1985)                  |
| Guedes                  | `G-F6`  |     F |         6 | SI + CX + SE + TR + BI + PM                                                        | `D = 1,19665 − 0,07634·log₁₀(SI + CX + SE + TR + BI + PM)`                                                   | Guedes (1985)                  |
| Guedes                  | `G-F7`  |     F |         7 | SI + CX + SE + TR + BI + PM + AB                                                   | `D = 1,19748 − 0,07419·log₁₀(SI + CX + SE + TR + BI + PM + AB)`                                              | Guedes (1985)                  |
| Guedes                  | `G-F8`  |     F |         8 | AB + TR + SI + AM + SE + CX + PM + BI                                              | `D = 1,19863 − 0,07343·log₁₀(AB + TR + SI + AM + SE + CX + PM + BI)`                                         | Guedes (1985)                  |
| Petroski                | `P-M1`  |     M |         9 | X9 = SE + TR + BI + PT + AM + SI + AB + CX + PM                                    | `D = 1,10194032 − 0,00031836·X9 + 0,00000029·X9² − 0,00029542·idade`                                         | Petroski (1995)                |
| Petroski                | `P-M2`  |     M |         9 | X9 + circ. antebraço + circ. abdômen                                               | `D = 1,08516305 − 0,00028465·X9 + 0,00000026·X9² − 0,00021018·idade + 0,00173856·CAT − 0,00043254·CAB`       | Petroski (1995)                |
| Petroski                | `P-M3`  |     M |         7 | X7 = SE + TR + PT + AM + SI + AB + CX                                              | `D = 1,10038145 − 0,00035804·X7 + 0,00000036·X7² − 0,00025154·idade`                                         | Petroski (1995)                |
| Petroski                | `P-M4`  |     M |         7 | X7 + circ. antebraço + circ. abdômen                                               | `D = 1,08566598 − 0,00032750·X7 + 0,00000036·X7² − 0,00017521·idade + 0,00161816·CAT − 0,00041043·CAB`       | Petroski (1995)                |
| Petroski                | `P-M5`  |     M |         6 | X6 = SE + TR + BI + PT + AM + SI                                                   | `D = 1,09995680 − 0,00055475·X6 + 0,00000107·X6² − 0,00023367·idade`                                         | Petroski (1995)                |
| Petroski                | `P-M6`  |     M |         6 | X6 + circ. antebraço + circ. abdômen                                               | `D = 1,08555470 − 0,00050212·X6 + 0,00000104·X6² − 0,00015217·idade + 0,00169842·CAT − 0,00044620·CAB`       | Petroski (1995)                |
| Petroski                | `P-M7`  |     M |         4 | X4 = SE + TR + SI + PM                                                             | `D = 1,10726863 − 0,00081201·X4 + 0,00000212·X4² − 0,00041761·idade`                                         | Petroski (1995)                |
| Petroski                | `P-M8`  |     M |         4 | X4 + circ. antebraço + circ. abdômen                                               | `D = 1,09255357 − 0,00067980·X4 + 0,00000182·X4² − 0,00027287·idade + 0,00204435·CAT − 0,00060405·CAB`       | Petroski (1995)                |
| Petroski                | `P-M9`  |     M |         4 | Z4 = SE + TR + BI + SI                                                             | `D = 1,10539106 − 0,00089839·Z4 + 0,00000278·Z4² − 0,00035250·idade`                                         | Petroski (1995)                |
| Petroski                | `P-M10` |     M |         4 | Z4 + circ. antebraço + circ. abdômen                                               | `D = 1,09158117 − 0,00077719·Z4 + 0,00000257·Z4² − 0,00022634·idade + 0,00195027·CAT − 0,00057011·CAB`       | Petroski (1995)                |
| Petroski                | `P-M11` |     M |         3 | X3 = SE + TR + SI                                                                  | `D = 1,10491700 − 0,00099061·X3 + 0,00000327·X3² − 0,00034527·idade`                                         | Petroski (1995)                |
| Petroski                | `P-M12` |     M |         3 | X3 + circ. antebraço + circ. abdômen                                               | `D = 1,09360757 − 0,00086876·X3 + 0,00000327·X3² − 0,00021422·idade + 0,00191721·CAT − 0,00059091·CAB`       | Petroski (1995)                |
| Petroski                | `P-M13` |     M |         3 | Z3 = SE + TR + PT                                                                  | `D = 1,10404686 − 0,00111938·Z3 + 0,00000391·Z3² − 0,00027884·idade`                                         | Petroski (1995)                |
| Petroski                | `P-M14` |     M |         3 | Z3 + circ. antebraço + circ. abdômen                                               | `D = 1,08974189 − 0,00098446·Z3 + 0,00000376·Z3² − 0,00017218·idade + 0,00191020·CAT − 0,00054056·CAB`       | Petroski (1995)                |
| Petroski                | `P-F1`  |     F |         9 | X9 = SE + TR + BI + PT + AM + SI + AB + CX + PM                                    | `D = 1,03987298 − 0,00031853·X9 + 0,00000047·X9² − 0,00025486·idade − 0,00047358·peso + 0,00046897·estatura` | Petroski (1995)                |
| Petroski                | `P-F2`  |     F |         9 | X9                                                                                 | `D = 1,21630958 − 0,07522765·log₁₀(X9) − 0,00032901·idade`                                                   | Petroski (1995)                |
| Petroski                | `P-F3`  |     F |         9 | X9 + circ. coxa                                                                    | `D = 1,22219652 − 0,06681170·log₁₀(X9) − 0,00035407·idade − 0,00041834·CCX`                                  | Petroski (1995)                |
| Petroski                | `P-F4`  |     F |         7 | X7 = SE + TR + AM + SI + AB + CX + PM                                              | `D = 1,03992377 − 0,00036083·X7 + 0,00000058·X7² − 0,00027099·idade − 0,00046621·peso + 0,00047136·estatura` | Petroski (1995)                |
| Petroski                | `P-F5`  |     F |         7 | Y7 = SE + TR + AM + PT + SI + AB + CX                                              | `D = 1,20670046 − 0,07395778·log₁₀(Y7) − 0,00030860·idade`                                                   | Petroski (1995)                |
| Petroski                | `P-F6`  |     F |         7 | Y7 + circ. coxa                                                                    | `D = 1,21527404 − 0,06432107·log₁₀(Y7) − 0,00033650·idade − 0,00049553·CCX`                                  | Petroski (1995)                |
| Petroski                | `P-F7`  |     F |         5 | X5 = SE + TR + SI + AB + PM                                                        | `D = 1,03091919 − 0,00048584·X5 + 0,00000131·X5² − 0,00026016·idade − 0,00056484·peso + 0,00053716·estatura` | Petroski (1995)                |
| Petroski                | `P-F8`  |     F |         5 | X5 + circ. coxa                                                                    | `D = 1,20263859 − 0,05941591·log₁₀(X5) − 0,00037947·idade − 0,00058310·CCX`                                  | Petroski (1995)                |
| Petroski                | `P-F9`  |     F |         4 | X4 = SE + TR + SI + PM                                                             | `D = 1,02902361 − 0,00067159·X4 + 0,00000242·X4² − 0,00026073·idade − 0,00056009·peso + 0,00054649·estatura` | Petroski (1995)                |
| Petroski                | `P-F10` |     F |         4 | Y4 = AM + SI + CX + PM                                                             | `D = 1,03465850 − 0,00063129·Y4 + 0,00000187·Y4² − 0,00031165·idade − 0,00048890·peso + 0,00051345·estatura` | Petroski (1995)                |
| Petroski                | `P-F11` |     F |         4 | Y4                                                                                 | `D = 1,19547130 − 0,07513507·log₁₀(Y4) − 0,00041072·idade`                                                   | Petroski (1995)                |
| Petroski                | `P-F12` |     F |         4 | Y4 + circ. abdômen                                                                 | `D = 1,19762048 − 0,06503676·log₁₀(Y4) − 0,00032730·idade − 0,00033622·CAB`                                  | Petroski (1995)                |
| Petroski                | `P-F13` |     F |         3 | X3 = SE + SI + CX                                                                  | `D = 1,04127059 − 0,00087756·X3 + 0,00000380·X3² − 0,00025821·idade − 0,00059076·peso + 0,00051050·estatura` | Petroski (1995)                |
| Petroski                | `P-F14` |     F |         3 | Y3 = AM + SI + CX                                                                  | `D = 1,04279001 − 0,00086587·Y3 + 0,00000378·Y3² − 0,00028831·idade − 0,00053501·peso + 0,00047533·estatura` | Petroski (1995)                |
| Petroski                | `P-F15` |     F |         3 | Y3                                                                                 | `D = 1,18187115 − 0,07320426·log₁₀(Y3) − 0,00037317·idade`                                                   | Petroski (1995)                |
| Petroski                | `P-F16` |     F |         3 | Y3 + circ. abdômen                                                                 | `D = 1,18483723 − 0,06461929·log₁₀(Y3) − 0,00030703·idade − 0,00028509·CAB`                                  | Petroski (1995)                |
| Faulkner                | `FALK4` | M/F\* |         4 | Tríceps + subescapular + supra-ilíaca + abdominal                                  | `%G = 5,783 + 0,153·Σ4`                                                                                      | Faulkner (1968)                |

`*` Aplicabilidade sexual da variante deve permanecer marcada como `(confirmar)` até a validação documental correspondente.

Todas as variantes que produzem `D` possuem:

```text
outputType = BODY_DENSITY
```

A variante `FALK4` possui:

```text
outputType = BODY_FAT_PERCENTAGE
```

---

# 19. Biblioteca de conversões

A biblioteca atual possui somente duas conversões.

| Família | ID       | Entrada        | Saída                 | Fórmula                          | Referência           |
| ------- | -------- | -------------- | --------------------- | -------------------------------- | -------------------- |
| Siri    | `SIRI`   | `BODY_DENSITY` | `BODY_FAT_PERCENTAGE` | `%G = (4,95 / D − 4,50) × 100`   | Siri (1961)          |
| Brozek  | `BROZEK` | `BODY_DENSITY` | `BODY_FAT_PERCENTAGE` | `%G = (4,570 / D − 4,142) × 100` | Brozek et al. (1963) |

Essas definições **não são `EquationVariant`**.

Elas recebem um `PredictionResult` do tipo:

```text
BODY_DENSITY
```

e produzem:

```text
BODY_FAT_PERCENTAGE
```

Assim:

```text
JP7
   ↓
BODY_DENSITY
   ↓
Siri ou Brozek
   ↓
BODY_FAT_PERCENTAGE
```

---

# 20. Regra de cadastro da variante

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

O mesmo princípio vale para Petroski, Guedes e Faulkner.

---

# 21. Inputs e variáveis derivadas

A variante deve declarar exatamente suas entradas.

Exemplos:

```text
AGE
SEX
HEIGHT
WEIGHT
SKINFOLD
CIRCUMFERENCE
SUM_SKINFOLDS
```

Uma variável pode ser derivada.

Exemplos:

```text
SUM_SKINFOLDS
derived = true
source = conjunto de SKINFOLD
```

As somas intermediárias das fórmulas não precisam ser armazenadas como novas medidas manuais.

Exemplo:

```text
TR + SE + SI
      ↓
X3
      ↓
Formula
```

O mesmo vale para:

```text
X4
X5
X6
X7
X9
Y3
Y4
Y7
Z3
Z4
```

Essas variáveis são derivadas da `Assessment`.

Não devem ser tratadas como `Measurement` independente.

O mesmo princípio vale para a densidade produzida por uma `EquationVariant`.

Exemplo:

```text
PredictionResult

type = BODY_DENSITY

value = 1.06518908
```

não precisa virar uma `Measurement`.

Ele é um resultado produzido por uma `CalculationRun` e pode ser consumido por uma `ConversionDefinition`.

---

# 22. Cadeia de cálculo

A cadeia completa passa a ser:

```text
Raw Measurements
       ↓
Input Resolution
       ↓
Equation Variant
       ↓
Prediction Output
       ↓
Conversion Applicability
       ↓
Conversion Suggestion
       ↓
Professional Conversion Selection
       ↓
Optional Conversion
       ↓
Final Result
       ↓
Audit Snapshot
```

A execução de uma `EquationVariant` produz um:

```text
PredictionResult
```

Exemplo:

```text
PredictionResult

type = BODY_DENSITY
value = 1.06518908
```

Esse resultado pode ser:

1. o resultado final da avaliação; ou
2. uma saída intermediária que pode ser convertida.

---

## 22.1 Equação que produz densidade

```text
JP7
  ↓
BODY_DENSITY
  ↓
ConversionSuggestionEngine
  ↓
Siri / Brozek
  ↓
BODY_FAT_PERCENTAGE
```

O mesmo fluxo pode ocorrer com Guedes e Petroski.

---

## 22.2 Equação que produz %G diretamente

```text
Faulkner 4D
  ↓
BODY_FAT_PERCENTAGE
  ↓
Final Result
```

Nesse caso:

```text
ConversionDefinition
```

não é necessária.

---

## 22.3 Regra fundamental

A plataforma nunca deve presumir:

```text
BODY_DENSITY → Siri
```

como regra matemática universal.

A conversão deve ser explícita:

```text
ConversionDefinition
```

e sua seleção deve passar pelo:

```text
ConversionSuggestionEngine
```

quando houver múltiplas opções aplicáveis.

---

## 22.4 ConversionDefinition

A entidade deve possuir, no mínimo:

```text
ConversionDefinition

id
name
inputType
outputType
expression
applicabilityRules[]
version
status
```

Exemplo:

```text
ConversionDefinition

name = Siri
inputType = BODY_DENSITY
outputType = BODY_FAT_PERCENTAGE
expression = (4.95 / D - 4.50) * 100
version = 1
status = ACTIVE
```

Outro:

```text
ConversionDefinition

name = Brozek
inputType = BODY_DENSITY
outputType = BODY_FAT_PERCENTAGE
expression = (4.570 / D - 4.142) * 100
version = 1
status = ACTIVE
```

Assim:

```text
JP7
  ↓
BODY_DENSITY
  ↓
ConversionDefinition
  ├── Siri
  └── Brozek
```

em vez de criar variantes artificiais:

```text
JP7 + Siri
JP7 + Brozek
Petroski + Siri
Petroski + Brozek
```

---

# 23. Elegibilidade

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

Para conversões:

| Critério                              | Tipo                                   |
| ------------------------------------- | -------------------------------------- |
| Input incompatível                    | Bloqueio                               |
| Output incompatível com o fluxo       | Bloqueio                               |
| Conversão desabilitada                | `DISABLED`                             |
| Restrição científica documentada      | `INELIGIBLE`                           |
| Falta de dado necessário da conversão | `MISSING_INPUTS` ou estado equivalente |
| Evidência insuficiente                | Alerta, quando aplicável               |
| Preferência profissional              | Sugestão                               |
| Escolha manual                        | Override                               |

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

O mesmo princípio vale para conversões.

Ranking ocorre **depois** da resolução da elegibilidade.

---

# 24. Sugestão x decisão profissional

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

## 24.1 Conversão

A mesma separação deve existir para conversões:

```text
SuggestedConversion
```

versus:

```text
ConversionSelection
```

Exemplo:

```text
Sistema sugeriu:

Siri

Profissional escolheu:

Brozek
```

Então:

```text
suggestedConversion = Siri

selectedConversion = Brozek

override = true
```

Opcionalmente:

```text
overrideReason = "Preferência profissional"
```

Isso não significa:

```text
Brozek = errado
```

Significa:

```text
Brozek = escolha profissional diferente da sugestão do sistema
```

---

# 25. Cálculo

O cálculo deve ser uma ação explícita.

Fluxo principal:

```text
READY
  ↓
Profissional escolhe variante
  ↓
[Calcular]
  ↓
CalculationRun
  ↓
PredictionResult
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

## 25.1 Quando a saída é final

Exemplo:

```text
Faulkner 4D

      ↓

BODY_FAT_PERCENTAGE

      ↓

Final Result
```

Nesse caso:

```text
CalculationRun
```

pode encerrar diretamente no resultado final.

---

## 25.2 Quando existe conversão

Exemplo:

```text
JP7

↓

PredictionResult

type = BODY_DENSITY

↓

ConversionSuggestionEngine

↓

SuggestedConversion = Siri

↓

ProfessionalConversionSelection

↓

ConversionDefinition

↓

ConversionResult

↓

Final Result
```

Quando o profissional tiver configurado Brozek como preferência:

```text
SuggestedConversion = Brozek
```

A conversão continua sendo parte do mesmo fluxo de cálculo lógico, mas permanece conceitualmente independente da `EquationVariant`.

---

## 25.3 AUTO

O sistema poderá possuir o modo:

```text
conversionMode = AUTO
```

Nesse modo:

```text
PredictionResult
      ↓
ConversionSuggestionEngine
      ↓
verificação de elegibilidade
      ↓
preferência profissional
      ↓
default da plataforma
      ↓
seleção automática
```

A política será:

```text
1. Se existir preferência profissional válida e elegível:
   utilizar essa preferência.

2. Caso contrário, se Siri estiver habilitada e elegível:
   utilizar Siri como default operacional.

3. Caso Siri não esteja disponível/elegível:
   aplicar a política de ranking configurada para Brozek
   ou outra conversão presente na biblioteca.
```

Como a biblioteca atual possui apenas Siri e Brozek, o fluxo padrão esperado será:

```text
AUTO
  ↓
preferência profissional válida?
  ├── sim → preferência
  └── não
       ↓
   Siri elegível?
       ├── sim → Siri
       └── não → Brozek, se elegível
```

Portanto:

```text
AUTO → Siri
```

pode ocorrer como consequência da política padrão da plataforma, mas não significa que Siri seja obrigatoriamente utilizada em todos os casos.

O comportamento de `AUTO` deve continuar sendo versionado e registrado na auditoria.

---

# 26. Reutilização entre variantes

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

Da mesma forma, o mesmo:

```text
PredictionResult
```

pode ser consumido por:

```text
Siri
Brozek
```

sem duplicar a fórmula ou criar combinações artificiais entre método e conversão.

---

# 27. Golden tests

| ID                    | Entrada                          | Esperado                                                | Status                                   |
| --------------------- | -------------------------------- | ------------------------------------------------------- | ---------------------------------------- |
| `GOLDEN-JP7-F-001`    | sexo=F, idade=44, sum7=60mm      | densidade≈1.06518908; `%G Siri≈14.7062%`; display=14.7% | Reprodução forte; referência a confirmar |
| `GOLDEN-FAULKNER-001` | sum4=17mm                        | `%G = 5,783 + 0,153×soma = 8,384%`; display=8.4%        | Reprodução forte; fonte a confirmar      |
| `REGRESSION-002`      | idade=44, peso=70kg, sum7=90mm   | reproduzir quando variante identificada                 | Investigação                             |
| `REGRESSION-003`      | idade=44, peso=64,7kg, sum7=79mm | reproduzir quando variante identificada                 | Investigação                             |

Além dos testes das variantes, devem existir testes específicos para conversões.

Exemplo:

```text
GOLDEN-CONVERSION-SIRI-001

inputType = BODY_DENSITY

input = 1.06518908

expected ≈ 14.7062%

status = referência a confirmar
```

```text
GOLDEN-CONVERSION-BROZEK-001

inputType = BODY_DENSITY

input = 1.06518908

expected ≈ 14.8318%

status = referência a confirmar
```

Também devem existir testes de seleção:

```text
CONVERSION-SUGGESTION-001

Siri elegível
Brozek elegível

esperado:

SuggestedConversion = Siri
```

e:

```text
CONVERSION-OVERRIDE-001

Sistema sugere Siri
Profissional escolhe Brozek

esperado:

suggestedConversion = Siri
selectedConversion = Brozek
override = true
```

Também deve existir o cenário:

```text
CONVERSION-FALLBACK-001

Siri inelegível ou desabilitada
Brozek elegível

esperado:

SuggestedConversion = Brozek
```

---

# 28. Auditoria

Cada cálculo deve preservar:

```text
assessmentId

equationVariantId

equationVersion

inputsUsed

predictionResult

conversionId

conversionVersion

conversionResult

calculationResult

suggestedVariant

professionalChoice

suggestedConversion

professionalConversionChoice

overrideReason

timestamp
```

Uma alteração futura na biblioteca não deve alterar o resultado histórico.

Exemplo:

```text
2026

JP7 v1

↓

Densidade = 1.06518908

↓

Siri v1

↓

Resultado = 14,7%
```

Em 2027:

```text
JP7 v2

↓

novo cálculo
```

O resultado de 2026 continua vinculado à:

```text
JP7 v1
```

e à:

```text
Siri v1
```

caso Siri tenha sido utilizada.

---

## 28.1 Auditoria do caminho completo

O histórico deve permitir responder:

> "Como esse 14,7% foi obtido?"

O sistema deve conseguir reconstruir:

```text
Assessment
      ↓
JP7 Female v1
      ↓
inputsUsed
      ↓
PredictionResult
      ↓
Siri v1
      ↓
ConversionResult
      ↓
Final Result
```

ou:

```text
Assessment
      ↓
JP7 Female v1
      ↓
PredictionResult
      ↓
Brozek v1
      ↓
Final Result
```

Caso a equação produza diretamente `%G`:

```text
Assessment
      ↓
Faulkner 4D v1
      ↓
PredictionResult
      ↓
Final Result
```

---

# 29. Regras de engenharia não negociáveis

- Não hardcodar seleção por nome de família.
- Sempre utilizar `EquationVariant` versionada.
- Não duplicar medidas brutas por fórmula.
- Não misturar aplicabilidade, elegibilidade e ranking.
- Não usar score para liberar variante matematicamente inexequível.
- **Não tratar Siri como universalmente superior a Brozek.**
- **Siri é o default operacional da plataforma quando elegível e quando não houver preferência profissional diferente.**
- Não transformar engenharia reversa em fonte científica.
- Não tratar "Brasil" como população suficiente por si só.
- Não assumir que atleta/treinamento determina automaticamente uma fórmula.
- Separar regras científicas de preferências profissionais.
- Permitir configuração personalizada sem alterar a biblioteca global.
- Permitir restaurar configuração padrão.
- **Ao restaurar o padrão, remover a preferência personalizada de conversão e retornar a Siri como default operacional, quando aplicável.**
- Versionar equação, conversão e motor de sugestão separadamente.
- Versionar também o `ConversionSuggestionEngine` separadamente do `SuggestionEngine`.
- Manter `SuggestedVariant` e `ProfessionalSelection` separados.
- Manter `SuggestedConversion` e `ConversionSelection` separados.
- Não transformar `ConversionDefinition` em `EquationVariant`.
- Não criar variantes artificiais combinando método e conversão.
- Não assumir que toda saída `BODY_DENSITY` deve ser convertida por Siri **sem passar pela elegibilidade e pela política de configuração**.
- Permitir Siri e Brozek como alternativas para o mesmo `PredictionResult`, quando cientificamente e tecnicamente aplicáveis.
- Não usar preferência profissional como evidência científica.
- Manter golden tests para casos reais.
- Preservar `AuditSnapshot` das execuções históricas.
- Alteração de fórmula ou conversão deve gerar nova versão.
- Alteração do motor de sugestão não deve reescrever resultados históricos.
- **`AUTO` deve respeitar preferência profissional válida; na ausência dela, utilizar Siri como default operacional quando elegível; caso contrário, utilizar Brozek quando elegível.**

---

# 30. Estados conceituais do sistema

Uma `EquationVariant` pode passar por:

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

## 30.1 Estados conceituais de conversão

Uma `ConversionDefinition` pode passar por:

```text
DISABLED
   │
   ▼
INELIGIBLE
   │
   ▼
READY
   │
   ▼
SELECTED
   │
   ▼
CALCULATED
```

Como uma conversão normalmente trabalha sobre um `PredictionResult` já produzido, ela não precisa necessariamente de um equivalente a `MISSING_INPUTS`.

Entretanto, caso determinada conversão dependa de entradas adicionais, poderá utilizar:

```text
MISSING_INPUTS
```

desde que isso seja explicitamente modelado.

A máquina de estados deve refletir a necessidade real da conversão, e não simplesmente copiar os estados das variantes.

---

# 31. Exemplo completo

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

## Antes das medidas

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

## Após algumas medidas

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

## Profissional escolhe Petroski

O sistema identifica os inputs faltantes conforme a variante específica escolhida.

Exemplo:

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

## Petroski produz densidade

Suponha:

```text
PredictionResult

type = BODY_DENSITY

value = 1.06518908
```

Nesse momento a plataforma não deve presumir:

```text
1.06518908 → Siri
```

Ela consulta o:

```text
ConversionSuggestionEngine
```

O motor encontra:

```text
Siri

Brozek
```

como candidatas.

Exemplo de saída:

```text
SuggestedConversion = Siri
```

O sistema informa os critérios utilizados.

---

## Profissional escolhe Brozek

O profissional pode escolher:

```text
Brozek
```

Nesse caso:

```text
suggestedConversion = Siri

selectedConversion = Brozek

override = true
```

A escolha profissional não invalida a sugestão anterior.

---

## Conversão

O sistema executa:

```text
PredictionResult
      ↓
Brozek v1
      ↓
ConversionResult
      ↓
BODY_FAT_PERCENTAGE
```

Depois:

```text
Final Result
```

é armazenado.

---

## Depois

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

Cada `CalculationRun` poderá possuir ou não:

```text
ConversionDefinition
```

dependendo da saída da variante.

Exemplo:

```text
CalculationRun Petroski

    ├── PredictionResult = BODY_DENSITY

    ├── Conversion = Brozek

    └── FinalResult = BODY_FAT_PERCENTAGE
```

Enquanto:

```text
CalculationRun Faulkner

    ├── PredictionResult = BODY_FAT_PERCENTAGE

    └── Conversion = none
```

---

# 32. Fluxo final do produto

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
               ┌────────────┴────────────┐
               │                         │
               ▼                         ▼
          Measurement              Reutilização
               │                    de anterior
               └────────────┬────────────┘
                            ▼
                 PRONTIDÃO DOS MÉTODOS
                            │
              ┌─────────────┼─────────────┐
              ▼             ▼             ▼
            READY       MISSING        INELIGIBLE
                         INPUTS
                           │
                           └── solicitar
                               somente o faltante
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
                   PREDICTION RESULT
                           │
              ┌────────────┴────────────┐
              │                         │
              ▼                         ▼
       RESULTADO FINAL          OUTPUT INTERMEDIÁRIO
                                        │
                                        ▼
                         APLICABILIDADE DE CONVERSÃO
                                        │
                                        ▼
                           CONVERSIONS CANDIDATAS
                                        │
                                        ▼
                           CONVERSIONS ELEGÍVEIS
                                        │
                                        ▼
                            CONVERSION SUGGESTION
                                        │
                                        ▼
                         ESCOLHA PROFISSIONAL
                                        │
                                        ▼
                                  CONVERSÃO
                                        │
                                        ▼
                               RESULTADO FINAL
                                        │
                                        ▼
                               AUDIT SNAPSHOT
```

---

# 33. Modelo mental definitivo

A lógica principal da plataforma pode ser resumida em **seis perguntas**:

## 1. O que eu sei sobre essa pessoa?

```text
Perfil + Contexto
```

---

## 2. Quais métodos fazem sentido considerar?

```text
Aplicabilidade + configuração profissional
```

---

## 3. O que já consigo executar?

```text
Measurements + RequiredInputs
```

---

## 4. Entre os executáveis, o que o sistema sugere?

```text
SuggestionEngine
```

---

## 5. O que o profissional realmente decidiu?

```text
ProfessionalSelection
```

---

## 6. Como transformar o resultado intermediário em resultado final?

Quando a variante produzir uma saída que exige conversão:

```text
PredictionResult
```

↓

```text
ConversionSuggestionEngine
```

↓

```text
SuggestedConversion
```

↓

```text
ConversionSelection
```

↓

```text
ConversionDefinition
```

↓

```text
Final Result
```

Portanto:

> **Aplicabilidade não é recomendação.**

> **Recomendação não é escolha profissional.**

> **Escolha profissional não é cálculo.**

> **Cálculo não é a avaliação inteira.**

> **PredictionResult não é necessariamente o resultado final.**

> **ConversionDefinition não é EquationVariant.**

> **Sugestão de conversão não é escolha profissional de conversão.**

A `Assessment` é o snapshot que conecta todas essas etapas.

O modelo mental completo é:

```text
Assessment

    │

    ├── Measurements

    │

    ├── Applicability

    │

    ├── Method Suggestion

    │

    ├── Professional Selection

    │

    ├── CalculationRun

    │       │

    │       └── PredictionResult

    │

    ├── Conversion Applicability

    │

    ├── Conversion Suggestion

    │

    ├── Conversion Selection

    │

    └── Final Result + AuditSnapshot
```

E a regra arquitetural central permanece:

```text
EquationVariant
    ↓
PredictionResult
    ↓
(optional)
ConversionDefinition
    ↓
Final Result
```

A biblioteca científica atualmente implementada para este fluxo é:

```text
EquationVariant

├── Jackson & Pollock
│   ├── JP3-M
│   ├── JP3-F
│   ├── JP7-M
│   └── JP7-F
│
├── Guedes
│   ├── G-M3
│   ├── G-M4
│   ├── G-M5
│   ├── G-M6
│   ├── G-M7
│   ├── G-M8
│   ├── G-F3
│   ├── G-F4
│   ├── G-F5
│   ├── G-F6
│   ├── G-F7
│   └── G-F8
│
├── Petroski
│   ├── P-M1 ... P-M14
│   └── P-F1 ... P-F16
│
└── Faulkner
    └── FALK4
```

E:

```text
ConversionDefinition

├── Siri
└── Brozek
```

Siri e Brozek pertencem ao segundo estágio, como `ConversionDefinition`.

Elas **não competem com JP7, Petroski, Guedes ou Faulkner como métodos de avaliação**.

A plataforma conecta os dois estágios de forma explícita, versionada, auditável e configurável.
