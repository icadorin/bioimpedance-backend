# EquationVariantScientificProfile — P-M7

## 1. Identification

```text
identity:

    variantId:
        "P-M7"

    familyId:
        "petroski"

    displayName:
        "Petroski M7 — Masculino"

    aliasNames:
        []
```

Reference identity:

```text
Petroski, E. L. (1995).
Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos.
Tese de Doutorado, Universidade Federal de Santa Maria (UFSM), Santa Maria, RS, Brasil.
```

## 2. Mathematical Definition

```text
D =
    1.10726863
    - 0.00081201 × X4
    + 0.00000212 × X4²
    - 0.00041761 × AGE
```

Where:

```text
X4 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_MEDIAL_CALF
```

```text
outputType:
    BODY_DENSITY
```

## 3. Applicability

### 3.1 Sex

```text
supportedSexes:
    - MALE
```

### 3.2 Age

```text
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

A média de idade é descritiva e não é limite de elegibilidade.

## 4. Population Applicability

```text
originalPopulation:
    description: "Homens adultos"
    country: "Brazil"
    region: "Rio Grande do Sul / Santa Maria"
    sexCoverage:
        - MALE
    ageCoverage:
        min: 18
        max: 66
    sampleSize: 391
    bodyCharacteristicsNotes: "População masculina adulta heterogênea em idade e composição corporal."
    sampleCharacteristics: "Amostra masculina do estudo de Petroski, com avaliação antropométrica e pesagem hidrostática."
    source: Petroski (1995)

validationPopulations:
    - description: "Homens adultos da amostra independente de validação"
      country: "Brazil"
      region: "Rio Grande do Sul / Santa Maria"
      sexCoverage:
          - MALE
      ageCoverage:
          min: 18
          max: 56
      sampleSize: 87
      bodyCharacteristicsNotes: null
      sampleCharacteristics: "Amostra independente utilizada na validação das equações."
      source: Petroski (1995)
```

## 5. Athlete Applicability

```text
developedInAthletes: null
validatedInAthletes: null
developedInNonAthletes: null
validatedInNonAthletes: null
explicitAthleteRestriction: null
```

A fonte não permite classificar com segurança a amostra segundo o binário operacional atleta/não atleta.

## 6. Training Level Applicability

```text
supportedLevels: []
notes: "Não documentado segundo a escala operacional da plataforma."
```

## 7. Modality Applicability

```text
supportedModalities: []
notes: "Não documentado em termos de modalidades esportivas específicas."
```

## 8. Body Characteristics

```text
rules: []
```

Não foi identificada uma restrição corporal explícita específica da P-M7.

## 9. Validation Evidence

### 9.1 Development Evidence

```text
studyReference:
    citation:
        "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado, Universidade Federal de Santa Maria."
    doi: null
    url:
        "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"

population:
    "Homens adultos, n = 391, 18–66 anos"

criterionMethod:
    "Pesagem hidrostática"

year:
    1995
```

Para a P-M7, os indicadores de desenvolvimento reportados são:

```text
R:
    0.875

R²:
    0.765

standardError:
    0.0075 g/ml
```

A tese descreve a M7 como uma equação de quatro dobras, com vantagem de praticidade e simplicidade para avaliações de grupos maiores.

## 10. Validation

A validação independente utilizou:

```text
n:
    87 men

age:
    18–56 years

criterionMethod:
    hydrostatic weighing
```

Resultados reportados para P-M7:

```text
correlation:
    0.861

meanDifference:
    -0.0001 g/ml

standardError:
    0.0078 g/ml

otherMetrics:
    "EC = -0.0001 g/ml; ET = 0.0078 g/ml; EPE = 0.0078 g/ml."
```

## 11. Cross-validation

```text
crossValidationStudies:
    []
```

A amostra independente de 87 homens é registrada em `validation`, conforme a estrutura utilizada para este conjunto de equações na tese.

## 12. External Validation

```text
externalValidationStudies:
    []
```

Nenhum estudo externo independente foi incorporado nesta ficha.

## 13. Measurement Requirements

```text
requiredInputs:
    - AGE
    - SKINFOLD_SUBSCAPULAR
    - SKINFOLD_TRICEPS
    - SKINFOLD_SUPRAILIAC
    - SKINFOLD_MEDIAL_CALF

optionalInputs:
    []
```

`SEX` permanece somente em applicability.

## 14. Scientific Restrictions

```text
restrictions:
    []
```

Não foi cadastrada restrição científica explícita com efeito `INELIGIBLE`.

## 15. Source Conflict

```text
sourceConflict:
    null
```

Não foi identificado conflito material não resolvido para a definição da P-M7 nas fontes usadas.

## 16. Lifecycle

```text
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

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile

    identity:
        variantId: "P-M7"
        familyId: "petroski"
        displayName: "Petroski M7 — Masculino"
        aliasNames: []

    applicability:
        sex:
            supportedSexes:
                - MALE

        age:
            originalDevelopmentAgeRange:
                min: 18
                max: 66
            developmentSampleMeanAge: 30.17
            validatedAgeRanges:
                - range:
                    min: 18
                    max: 56
                  population: "Homens adultos da amostra independente de validação"
                  source: Petroski (1995)
            explicitAgeRestriction: null

        population:
            originalPopulation:
                description: "Homens adultos"
                country: "Brazil"
                region: "Rio Grande do Sul / Santa Maria"
                sexCoverage:
                    - MALE
                ageCoverage:
                    min: 18
                    max: 66
                sampleSize: 391
                bodyCharacteristicsNotes: "População masculina adulta heterogênea em idade e composição corporal."
                sampleCharacteristics: "Amostra masculina do estudo de Petroski."
                source: Petroski (1995)

            validationPopulations:
                - description: "Homens adultos da amostra independente de validação"
                  country: "Brazil"
                  region: "Rio Grande do Sul / Santa Maria"
                  sexCoverage:
                      - MALE
                  ageCoverage:
                      min: 18
                      max: 56
                  sampleSize: 87
                  bodyCharacteristicsNotes: null
                  sampleCharacteristics: "Amostra independente de validação."
                  source: Petroski (1995)

        athlete:
            developedInAthletes: null
            validatedInAthletes: null
            developedInNonAthletes: null
            validatedInNonAthletes: null
            explicitAthleteRestriction: null

        trainingLevel:
            supportedLevels: []
            notes: "Não documentado segundo a escala operacional."

        modality:
            supportedModalities: []
            notes: "Não documentado."

        bodyCharacteristics:
            rules: []

    evidence:
        development:
            studyReference: Petroski (1995)
            population: "Homens adultos, n = 391, 18–66 anos"
            criterionMethod: "Pesagem hidrostática"
            year: 1995
            metrics:
                R: 0.875
                R²: 0.765
                standardError: 0.0075

        validationStudies:
            - studyReference: Petroski (1995)
              population: "Homens adultos, n = 87, 18–56 anos"
              criterionMethod: "Pesagem hidrostática"
              metrics:
                  correlation: 0.861
                  standardError: 0.0078
                  meanDifference: -0.0001
                  rmse: null
                  otherMetrics: "EC = -0.0001 g/ml; ET = 0.0078 g/ml; EPE = 0.0078 g/ml."
              limitations: null

        crossValidationStudies: []
        externalValidationStudies: []
        sourceConflict: null

    inputs:
        requiredInputs:
            - AGE
            - SKINFOLD_SUBSCAPULAR
            - SKINFOLD_TRICEPS
            - SKINFOLD_SUPRAILIAC
            - SKINFOLD_MEDIAL_CALF
        optionalInputs: []

    restrictions: []

    lifecycle:
        status: ACTIVE
        version: "1"
        supersedes: null
        supersededBy: null
        effectiveFrom: null
        changeLog: []
```

## 18. Interpretação para o SuggestionEngine

```text
sex:
    MALE → compatível com a aplicabilidade documentada

age:
    comparar com a evidência populacional documentada
    sem usar 30.17 como limite

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

output:
    BODY_DENSITY
```

A descrição de praticidade da M7 não deve virar uma regra hardcoded de recomendação no motor.

## 19. Reference

```text
Reference

    citation:
        "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado. Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

    doi:
        null

    url:
        "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"

Additional bibliographic source:
    citation:
        "Centro Esportivo Virtual — Desenvolvimento e Validação de Equações Generalizadas Para a Estimativa da Densidade Corporal em Adultos."
    url:
        "https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/"
```
