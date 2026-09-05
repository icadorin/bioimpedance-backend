# EquationVariantScientificProfile — P-M9

## 1. Identification

```text
identity:

    variantId:
        "P-M9"

    familyId:
        "petroski"

    displayName:
        "Petroski M9 — Masculino"

    aliasNames:
        [
            "Petroski M9",
            "Equação M9 de Petroski"
        ]
```

Primary reference:

```text
Petroski, E. L. (1995).
Desenvolvimento e validação de equações generalizadas para a estimativa
da densidade corporal em adultos.
Tese de Doutorado.
Universidade Federal de Santa Maria (UFSM),
Santa Maria, RS, Brasil.
```

## 2. Mathematical Definition

The P-M9 equation is:

```text
D =
    1.10539106
    - 0.00089839 × Z4
    + 0.00000278 × Z4²
    - 0.00035250 × AGE
```

Where:

```text
Z4 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_BICEPS
    + SKINFOLD_SUPRAILIAC
```

Mathematical output:

```text
outputType:
    BODY_DENSITY
```

The equation uses the sum and square of the sum of four skinfolds, together with age.

The four skinfolds are:

```text
- subscapular
- triceps
- biceps
- suprailiac
```

## 3. Applicability

### 3.1 Sex

```text
sex:

    supportedSexes:
        - MALE
```

P-M9 belongs to the male generalized equations developed by Petroski.

### 3.2 Age

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

The development population used for the male generalized equations comprised 304 men, aged 18–66 years, with mean age 30.17 years. The independent validation sample comprised 87 men aged 18–56 years.

The mean age is descriptive and is not an eligibility threshold.

## 4. Population Applicability

```text
population:

    originalPopulation:

        description:
            "Homens adultos"

        country:
            "Brazil"

        region:
            "Rio Grande do Sul / Santa Catarina"

        sexCoverage:
            - MALE

        ageCoverage:
            min: 18
            max: 66

        sampleSize:
            304

        bodyCharacteristicsNotes:
            "Amostra heterogênea em idade e gordura corporal; gordura corporal
             de aproximadamente 2.20% a 33.16% na amostra de desenvolvimento."

        sampleCharacteristics:
            "Homens adultos utilizados no desenvolvimento das equações
             generalizadas de Petroski."

        source:
            Petroski (1995)

    validationPopulations:

        - description:
            "Homens adultos da amostra independente de validação"

          country:
            "Brazil"

          region:
            "Rio Grande do Sul / Santa Catarina"

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
            "Amostra independente utilizada para validação das equações
             generalizadas."

          source:
            Petroski (1995)
```

The thesis states that the male equations were developed in a sample heterogeneous in age and body fat. The development sample contained 304 men with 18–66 years and body fat ranging approximately from 2.20% to 33.16%.

## 5. Athlete Applicability

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

The available primary source does not provide a sufficiently explicit athlete/non-athlete classification that can be safely mapped to the platform's boolean model.

No athlete status is inferred.

## 6. Training Level Applicability

```text
trainingLevel:

    supportedLevels:
        []

    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

No reliable mapping to:

```text
SEDENTARY
RECREATIONAL
TRAINED
COMPETITIVE
ELITE
```

was established from the primary source.

## 7. Modality Applicability

```text
modality:

    supportedModalities:
        []

    notes:
        "Não documentado em termos de modalidades esportivas específicas."
```

## 8. Body Characteristics

```text
bodyCharacteristics:

    rules:
        []
```

The reported heterogeneity in body fat describes the development sample and does not by itself create a P-M9 body-characteristic eligibility rule.

## 9. Validation Evidence

### 9.1 Development Evidence

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
            0.874

        R2:
            0.764

        standardError:
            0.0075
```

The P-M9 model is the first of the two four-skinfold alternatives in the male table that uses the set defined as `Z4`, composed of subscapular, triceps, biceps and suprailiac skinfolds.

## 10. Validation

The independent validation sample was:

```text
n:
    87 men

age:
    18–56 years
```

The criterion method was hydrostatic weighing.

For P-M9, the validation results were:

```text
correlation:
    0.847

constantError:
    -0.0003 g/ml

totalError:
    0.0081 g/ml

standardErrorOfEstimate:
    0.0082 g/ml
```

The validation table also reports a mean estimated density of 1.06248 ± 0.013 g/ml for M9 versus measured density of 1.06282 ± 0.0153 g/ml.

## 11. Cross-validation

```text
crossValidationStudies:
    []
```

The primary thesis presents the 87-man independent sample under validation. It is therefore not duplicated here as a separate cross-validation study.

## 12. External Validation

```text
externalValidationStudies:
    []
```

No independent external-validation study is included in this profile.

## 13. Measurement Requirements

```text
inputs:

    requiredInputs:

        - AGE
        - SKINFOLD_SUBSCAPULAR
        - SKINFOLD_TRICEPS
        - SKINFOLD_BICEPS
        - SKINFOLD_SUPRAILIAC

    optionalInputs:
        []
```

`SEX` is not a mathematical input.

```text
SEX
    → Applicability

AGE
    → Formula input

4 skinfolds
    → Formula inputs
```

The general definition, unit, precision and plausible range of each measurement belong to `/library/measurements`.

## 14. Scientific Restrictions

```text
restrictions:
    []
```

No explicit additional scientific restriction producing `INELIGIBLE` was identified for P-M9 beyond its documented male population/applicability context.

## 15. Source Conflict

```text
evidence:

    sourceConflict:
        null
```

No material unresolved conflict was identified for the P-M9 mathematical definition or the development/validation statistics in the primary source used for this profile.

## 16. Lifecycle

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

Essential scientific information is confirmed:

```text
identity                     CONFIRMED
mathematicalDefinition       CONFIRMED
supportedSexes               CONFIRMED
originalDevelopmentAgeRange  CONFIRMED
originalPopulation           CONFIRMED
requiredInputs               CONFIRMED
definitionReference          CONFIRMED
sourceConflict               null
```

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:
            "P-M9"

        familyId:
            "petroski"

        displayName:
            "Petroski M9 — Masculino"

        aliasNames:
            [
                "Petroski M9",
                "Equação M9 de Petroski"
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
                region: "Rio Grande do Sul / Santa Catarina"
                sexCoverage:
                    - MALE
                ageCoverage:
                    min: 18
                    max: 66
                sampleSize: 304
                bodyCharacteristicsNotes:
                    "Amostra heterogênea em idade e gordura corporal."
                sampleCharacteristics:
                    "Homens adultos utilizados no desenvolvimento das
                     equações generalizadas de Petroski."
                source:
                    Petroski (1995)

            validationPopulations:

                - description:
                    "Homens adultos da amostra independente de validação"
                  country: "Brazil"
                  region: "Rio Grande do Sul / Santa Catarina"
                  sexCoverage:
                      - MALE
                  ageCoverage:
                      min: 18
                      max: 56
                  sampleSize: 87
                  bodyCharacteristicsNotes:
                      null
                  sampleCharacteristics:
                      "Amostra independente utilizada para validação."
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
                "Não documentado segundo a escala operacional."

        modality:

            supportedModalities:
                []

            notes:
                "Não documentado."

        bodyCharacteristics:

            rules:
                []

    evidence:

        development:

            studyReference:
                Petroski (1995)

            population:
                "Homens adultos, n = 304, 18–66 anos"

            criterionMethod:
                "Pesagem hidrostática"

            year:
                1995

            metrics:
                R: 0.874
                R2: 0.764
                standardError: 0.0075

        validationStudies:

            - studyReference:
                Petroski (1995)

              population:
                "Homens adultos, n = 87, 18–56 anos"

              criterionMethod:
                "Pesagem hidrostática"

              metrics:
                correlation: 0.847
                standardError: 0.0082
                meanDifference: -0.0003
                rmse: null
                otherMetrics:
                    "EC = -0.0003 g/ml; ET = 0.0081 g/ml."

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
            - SKINFOLD_SUBSCAPULAR
            - SKINFOLD_TRICEPS
            - SKINFOLD_BICEPS
            - SKINFOLD_SUPRAILIAC

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

The scientific profile allows the engine to derive at runtime:

```text
sex:
    MALE → compatible with the documented sex

age:
    compare against documented population evidence
    without using 30.17 as an eligibility threshold

athlete:
    NOT_DOCUMENTED

trainingLevel:
    NOT_DOCUMENTED

modality:
    NOT_DOCUMENTED

bodyCharacteristics:
    no explicit rule

inputs:
    AGE
    + 4 skinfolds

output:
    BODY_DENSITY
```

The engine must not convert:

```text
developmentSampleMeanAge = 30.17
```

into an eligibility limit.

The engine must also not convert the absence of athlete, training-level or modality evidence into `INELIGIBLE`.

## 19. Reference

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

Additional bibliographic source:

```text
"Centro Esportivo Virtual — Desenvolvimento e Validação de Equações
Generalizadas Para a Estimativa da Densidade Corporal em Adultos."

url:
    "https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/"
```
