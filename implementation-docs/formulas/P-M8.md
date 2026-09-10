# EquationVariantScientificProfile — P-M8

## 1. Identificação

```text
identity:
    variantId:
        "P-M8"
    familyId:
        "petroski"
    displayName:
        "Petroski M8 — Masculino"
    aliasNames:
        [
            "Petroski M8",
            "Equação M8 de Petroski"
        ]
```

Referência principal:

```text
Petroski, E. L. (1995).
Desenvolvimento e validação de equações generalizadas para a estimativa
da densidade corporal em adultos.
Tese de Doutorado.
Universidade Federal de Santa Maria (UFSM),
Santa Maria, RS, Brasil.
```

## 2. Definição Matemática

A equação P-M8 é:

```text
D =
    1.09255357
    - 0.00067980 × X4
    + 0.00000182 × X4²
    - 0.00027287 × AGE
    + 0.00204435 × CIRCUMFERENCE_FOREARM
    - 0.00060405 × CIRCUMFERENCE_ABDOMEN
```

Onde:

```text
X4 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_MEDIAL_CALF
```

Saída matemática:

```text
outputType:
    BODY_DENSITY
```

A equação utiliza a soma e o quadrado da soma de quatro dobras cutâneas, além da idade, da circunferência do antebraço e da circunferência do abdômen.

As quatro dobras cutâneas são subescapular, tríceps, suprailíaca e panturrilha medial.

## 3. Aplicabilidade

### 3.1 Sexo

```text
sex:
    supportedSexes:
        - MALE
```

A P-M8 integra o conjunto de equações generalizadas masculinas desenvolvido por Petroski.

### 3.2 Idade

```text
age:
    originalDevelopmentAgeRange:
        min: 18
        max: 66
    developmentSampleMeanAge:
        30.17
    validatedAgeRanges:
        - range:
            min: 18
            max: 56
          population:
            "Homens adultos da amostra independente de validação"
          source:
            Petroski (1995)
    explicitAgeRestriction:
        null
```

A amostra masculina utilizada no desenvolvimento das equações compreendeu homens de 18 a 66 anos, com idade média de 30,17 anos. A amostra independente utilizada para validação compreendeu 87 homens de 18 a 56 anos.

A idade média da amostra de desenvolvimento é descritiva e não constitui limite de elegibilidade.

## 4. Aplicabilidade Populacional

```text
population:
    originalPopulation:
        description:
            "Homens adultos"
        country:
            "Brazil"
        region:
            "Região central do Rio Grande do Sul e região litorânea de Santa Catarina"
        sexCoverage:
            - MALE
        ageCoverage:
            min: 18
            max: 66
        sampleSize:
            304
        bodyCharacteristicsNotes:
            null
        sampleCharacteristics:
            "Amostra masculina de regressão utilizada no desenvolvimento das equações generalizadas de Petroski, com densidade corporal de critério determinada por pesagem hidrostática."
        source:
            Petroski (1995)
    validationPopulations:
        - description:
            "Homens adultos da amostra independente de validação"
          country:
            "Brazil"
          region:
            "Região central do Rio Grande do Sul e região litorânea de Santa Catarina"
          sexCoverage:
            - MALE
          ageCoverage:
            min: 18
            max: 56
          sampleSize:
            87
          bodyCharacteristicsNotes:
            null
          sampleCharacteristics:
            "Amostra independente utilizada para validar as equações generalizadas masculinas."
          source:
            Petroski (1995)
```

O estudo contou com 391 homens no total: 304 participantes compuseram a amostra de regressão utilizada para o desenvolvimento das equações masculinas e 87 participantes compuseram a amostra independente de validação. A distinção entre esses grupos é preservada no perfil para não confundir a amostra total do estudo com a amostra efetivamente usada no desenvolvimento da variante.

## 5. Aplicabilidade em Atletas

```text
athlete:
    developedInAthletes:
        null
    validatedInAthletes:
        null
    developedInNonAthletes:
        null
    validatedInNonAthletes:
        null
    explicitAthleteRestriction:
        null
```

A fonte primária não fornece uma classificação suficientemente explícita de atletas e não atletas que possa ser mapeada com segurança para o modelo booleano da plataforma.

Nenhum status de atleta é inferido.

## 6. Aplicabilidade por Nível de Treinamento

```text
trainingLevel:
    supportedLevels:
        []
    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

Não foi estabelecido, a partir da fonte primária, um mapeamento confiável para `SEDENTARY`, `RECREATIONAL`, `TRAINED`, `COMPETITIVE` ou `ELITE`.

## 7. Aplicabilidade por Modalidade

```text
modality:
    supportedModalities:
        []
    notes:
        "Não documentado em termos de modalidades esportivas específicas."
```

## 8. Características Corporais

```text
bodyCharacteristics:
    rules:
        []
```

A heterogeneidade da população estudada não estabelece, por si só, uma regra explícita de elegibilidade por característica corporal para a P-M8.

O uso de quatro dobras cutâneas e de duas circunferências é uma propriedade matemática da equação e não uma restrição de composição corporal.

## 9. Evidências de Validação

### 9.1 Evidências de Desenvolvimento

```text
development:
    studyReference:
        citation:
            "Petroski, E. L. (1995).
             Desenvolvimento e validação de equações generalizadas
             para a estimativa da densidade corporal em adultos.
             Tese de Doutorado.
             Universidade Federal de Santa Maria."
        doi:
            null
        url:
            "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"
    population:
        "Homens adultos, n = 304, 18–66 anos"
    criterionMethod:
        "Densidade corporal determinada por pesagem hidrostática"
    year:
        1995
    metrics:
        R:
            0.889
        R2:
            0.791
        standardError:
            0.0071
```

A Tabela 9 da tese registra para a M8: R = 0,889, R² = 0,791 e erro padrão da estimativa de 0,0071 g/ml. A tabela apresenta a identificação da M8 como modelo com quatro dobras cutâneas, idade e circunferências do antebraço e do abdômen.

## 10. Validação

A amostra independente de validação foi:

```text
n:
    87 homens
age:
    18–56 anos
```

O método de critério foi a pesagem hidrostática.

Para a P-M8, a Tabela 10 da tese apresenta:

```text
correlation:
    0.871
constantError:
    0.00001 g/ml
totalError:
    0.0075 g/ml
standardErrorOfEstimate:
    0.0075 g/ml
```

A tabela também informa densidade estimada média de 1,06282 ± 0,014 g/ml para M8, em comparação com 1,06282 ± 0,0153 g/ml para a densidade mensurada. O teste t foi 0,006, sem diferença estatisticamente significativa entre as médias.

Esses resultados pertencem à validação da própria P-M8 na amostra independente de 87 homens do mesmo estudo de Petroski (1995).

## 11. Validação Cruzada

```text
crossValidationStudies:
    []
```

A amostra independente de 87 homens é apresentada pela tese como amostra de validação das equações desenvolvidas. Portanto, ela permanece em `validationStudies` e não é duplicada como `crossValidationStudies`.

A tese também apresenta análises de equações desenvolvidas por outros pesquisadores em sua amostra de estudo. Essas análises não são registradas como validação cruzada da P-M8, pois não constituem teste de uma variante P-M8 contra uma população ou estudo externo específico.

## 12. Validação Externa

```text
externalValidationStudies:
    []
```

Não foi identificada, neste perfil, uma validação externa independente da P-M8 que deva ser registrada como evidência específica da variante.

## 13. Requisitos de Medição

```text
inputs:
    requiredInputs:
        - AGE
        - SKINFOLD_TRICEPS
        - SKINFOLD_SUBSCAPULAR
        - SKINFOLD_SUPRAILIAC
        - SKINFOLD_MEDIAL_CALF
        - CIRCUMFERENCE_FOREARM
        - CIRCUMFERENCE_ABDOMEN
    optionalInputs:
        []
```

Entradas matemáticas:

```text
AGE
X4
CIRCUMFERENCE_FOREARM
CIRCUMFERENCE_ABDOMEN
```

onde:

```text
X4 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_MEDIAL_CALF
```

`SEX` não é uma entrada matemática e não aparece em `requiredInputs`; ele determina a aplicabilidade à variante masculina.

As definições gerais, unidades, precisão e faixas plausíveis das medições permanecem em:

```text
/library/measurements
```

## 14. Restrições Científicas

```text
restrictions:
    []
```

Nenhuma restrição científica adicional explícita que produza `INELIGIBLE` foi identificada para a P-M8 além da aplicabilidade documentada à população masculina adulta da variante.

## 15. Conflito de Fonte

```text
sourceConflict:
    null
```

Não foi identificado conflito material entre fontes primárias para a definição matemática da P-M8 ou para as métricas de desenvolvimento e validação registradas neste perfil.

## 16. Ciclo de Vida

```text
lifecycle:
    status:
        ACTIVE
    version:
        "1"
    supersedes:
        null
    supersededBy:
        null
    effectiveFrom:
        null
    changeLog:
        []
```

Informações científicas essenciais:

```text
identity                       CONFIRMED
mathematicalDefinition         CONFIRMED
supportedSexes                CONFIRMED
originalDevelopmentAgeRange   CONFIRMED
originalPopulation            CONFIRMED
requiredInputs                CONFIRMED
definitionReference            CONFIRMED
sourceConflict                 null
```

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile
    identity:
        variantId:
            "P-M8"
        familyId:
            "petroski"
        displayName:
            "Petroski M8 — Masculino"
        aliasNames:
            [
                "Petroski M8",
                "Equação M8 de Petroski"
            ]
    applicability:
        sex:
            supportedSexes:
                - MALE
        age:
            originalDevelopmentAgeRange:
                min: 18
                max: 66
            developmentSampleMeanAge:
                30.17
            validatedAgeRanges:
                - range:
                    min: 18
                    max: 56
                  population:
                    "Homens adultos da amostra independente de validação"
                  source:
                    Petroski (1995)
            explicitAgeRestriction:
                null
        population:
            originalPopulation:
                description: "Homens adultos"
                country: "Brazil"
                region: "Região central do Rio Grande do Sul e região litorânea de Santa Catarina"
                sexCoverage:
                    - MALE
                ageCoverage:
                    min: 18
                    max: 66
                sampleSize: 304
                bodyCharacteristicsNotes: null
                sampleCharacteristics:
                    "Amostra masculina de regressão utilizada no desenvolvimento das equações generalizadas de Petroski, com densidade corporal de critério determinada por pesagem hidrostática."
                source:
                    Petroski (1995)
            validationPopulations:
                - description:
                    "Homens adultos da amostra independente de validação"
                  country: "Brazil"
                  region: "Região central do Rio Grande do Sul e região litorânea de Santa Catarina"
                  sexCoverage:
                      - MALE
                  ageCoverage:
                      min: 18
                      max: 56
                  sampleSize: 87
                  bodyCharacteristicsNotes: null
                  sampleCharacteristics:
                      "Amostra independente utilizada para validar as equações generalizadas masculinas."
                  source:
                      Petroski (1995)
        athlete:
            developedInAthletes:
                null
            validatedInAthletes:
                null
            developedInNonAthletes:
                null
            validatedInNonAthletes:
                null
            explicitAthleteRestriction:
                null
        trainingLevel:
            supportedLevels:
                []
            notes:
                "Não documentado segundo a escala operacional da plataforma."
        modality:
            supportedModalities:
                []
            notes:
                "Não documentado em termos de modalidades esportivas específicas."
        bodyCharacteristics:
            rules:
                []
    evidence:
        development:
            studyReference:
                citation:
                    "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado. Universidade Federal de Santa Maria."
                doi: null
                url:
                    "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"
            population:
                "Homens adultos, n = 304, 18–66 anos"
            criterionMethod:
                "Densidade corporal determinada por pesagem hidrostática"
            year:
                1995
            metrics:
                R: 0.889
                R2: 0.791
                standardError: 0.0071
        validationStudies:
            - studyReference:
                citation:
                    "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado. Universidade Federal de Santa Maria."
                doi: null
                url:
                    "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"
              population:
                  "Homens adultos, n = 87, 18–56 anos"
              criterionMethod:
                  "Pesagem hidrostática"
              metrics:
                  correlation:
                      0.871
                  standardError:
                      0.0075
                  meanDifference:
                      0.00001
                  rmse:
                      null
                  otherMetrics:
                      "EC = 0,00001 g/ml; ET = 0,0075 g/ml; EPE = 0,0075 g/ml; t = 0,006."
                  limitations:
                      null
        crossValidationStudies:
            []
        externalValidationStudies:
            []
        sourceConflict:
            null
    inputs:
        requiredInputs:
            - AGE
            - SKINFOLD_TRICEPS
            - SKINFOLD_SUBSCAPULAR
            - SKINFOLD_SUPRAILIAC
            - SKINFOLD_MEDIAL_CALF
            - CIRCUMFERENCE_FOREARM
            - CIRCUMFERENCE_ABDOMEN
        optionalInputs:
            []
    restrictions:
        []
    lifecycle:
        status:
            ACTIVE
        version:
            "1"
        supersedes:
            null
        supersededBy:
            null
        effectiveFrom:
            null
        changeLog:
            []
```

## 18. Interpretação para o SuggestionEngine

O perfil científico permite ao motor avaliar em runtime:

```text
sex:
    MALE → compatível com o sexo documentado

age:
    comparar com a evidência populacional documentada
    sem utilizar 30.17 como limite de elegibilidade

athlete:
    NOT_DOCUMENTED

trainingLevel:
    NOT_DOCUMENTED

modality:
    NOT_DOCUMENTED

bodyCharacteristics:
    sem regra explícita

inputs:
    AGE
    + 4 skinfolds
    + circunferência do antebraço
    + circunferência do abdômen

output:
    BODY_DENSITY
```

O motor não deve transformar `developmentSampleMeanAge = 30.17` em limite de elegibilidade.

A ausência de evidência sobre atleta, nível de treinamento ou modalidade também não deve ser transformada em `INELIGIBLE`.

A P-M8 é particularmente aplicável quando a avaliação possui as quatro dobras cutâneas exigidas — subescapular, tríceps, suprailíaca e panturrilha medial — além da idade e das circunferências do antebraço e do abdômen.

## 19. Referência

```text
Reference
    citation:
        "Petroski, E. L. (1995).
         Desenvolvimento e validação de equações generalizadas
         para a estimativa da densidade corporal em adultos.
         Tese de Doutorado.
         Universidade Federal de Santa Maria,
         Santa Maria, RS, Brasil."
    doi:
        null
    url:
        "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"
```

Fonte bibliográfica adicional:

```text
citation:
    "Centro Esportivo Virtual — Desenvolvimento e Validação de Equações Generalizadas Para a Estimativa da Densidade Corporal em Adultos."
doi:
    null
url:
    "https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/"
```
