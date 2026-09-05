# EquationVariantScientificProfile — P-M14

## 1. Identification

```text
identity:

    variantId:

        "P-M14"

    familyId:

        "petroski"

    displayName:

        "Petroski M14 — Masculino"

    aliasNames:

        [

            "Petroski M14",

            "Equação M14 de Petroski"

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

The P-M14 equation is:

```text
D =

    1.08974189

    - 0.00098446 × Z3

    + 0.00000376 × Z3²

    - 0.00017218 × AGE

    + 0.00191020 × CIRCUMFERENCE_FOREARM

    - 0.00054056 × CIRCUMFERENCE_ABDOMEN
```

Where:

```text
Z3 =

    SKINFOLD_SUBSCAPULAR

    + SKINFOLD_TRICEPS

    + SKINFOLD_PECTORAL
```

Output:

```text
outputType:

    BODY_DENSITY
```

The equation uses three skinfolds, age, forearm circumference and abdominal circumference. The three skinfolds are subscapular, triceps and pectoral.

## 3. Applicability

### 3.1 Sex

```text
sex:

    supportedSexes:

        - MALE
```

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

The development population consisted of 391 men aged 18–66 years, with a mean age of 30.17 years. The independent validation sample consisted of 87 men aged 18–56 years. The mean development age is descriptive and is not an eligibility threshold.

## 4. Population Applicability

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

            391

        bodyCharacteristicsNotes:

            "População masculina adulta heterogênea em idade e composição corporal."

        sampleCharacteristics:

            "Amostra masculina utilizada no desenvolvimento das equações

             generalizadas de Petroski, com densidade corporal de critério

             determinada por pesagem hidrostática."

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

            "Amostra independente utilizada para validar as equações generalizadas."

          source:

            Petroski (1995)
```

The thesis describes the male development sample as 391 men aged 18–66 years from the central region of Rio Grande do Sul and coastal region of Santa Catarina. Validation was performed in an independent sample of 87 men.

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

No sufficiently explicit athlete/non-athlete classification compatible with the platform's boolean model is assigned.

## 6. Training Level Applicability

```text
trainingLevel:

    supportedLevels:

        []

    notes:

        "Não documentado segundo a escala operacional da plataforma."
```

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

No explicit P-M14 body-characteristic eligibility rule is documented.

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

            "[https://pt.scribd.com/document/8955761/Tese-Edio-Petroski](https://pt.scribd.com/document/8955761/Tese-Edio-Petroski)"

    population:

        "Homens adultos, n = 391, 18–66 anos"

    criterionMethod:

        "Densidade corporal determinada por pesagem hidrostática"

    year:

        1995

    metrics:

        R:

            0.885

        R2:

            0.783

        standardError:

            0.0072
```

The P-M14 development model reported R = 0.885, R² = 0.783 and standard error of estimate = 0.0072 g/ml.

## 10. Validation

The independent validation sample was:

```text
n:

    87 men

age:

    18–56 years
```

Criterion method:

```text
hydrostatic weighing
```

For P-M14:

```text
correlation:

    0.844

constantError:

    -0.0004 g/ml

totalError:

    0.0082 g/ml

standardErrorOfEstimate:

    0.0082 g/ml
```

The values above are reported in Table 10 for the independent 87-man validation sample. The measured density in that sample was 1.06282 ± 0.0153 g/ml.

## 11. Cross-validation

```text
crossValidationStudies:

    []
```

The thesis contains a separate cross-validation analysis of generalized equations, but that table evaluates previously published equations from other investigators rather than presenting a cross-validation study of P-M14 itself. Therefore, no P-M14-specific study is duplicated here.

## 12. External Validation

```text
externalValidationStudies:

    []
```

No external validation study of P-M14 outside the original study population is included in this profile.

## 13. Measurement Requirements

```text
inputs:

    requiredInputs:

        - AGE

        - SKINFOLD_SUBSCAPULAR

        - SKINFOLD_TRICEPS

        - SKINFOLD_PECTORAL

        - CIRCUMFERENCE_FOREARM

        - CIRCUMFERENCE_ABDOMEN

    optionalInputs:

        []
```

Mathematical inputs:

```text
AGE

Z3

CIRCUMFERENCE_FOREARM

CIRCUMFERENCE_ABDOMEN
```

where:

```text
Z3 =

    SKINFOLD_SUBSCAPULAR

    + SKINFOLD_TRICEPS

    + SKINFOLD_PECTORAL
```

`SEX` is not included in `requiredInputs`; it determines applicability to the male variant.

According to the thesis notation, the skinfolds are measured in millimeters, age in years, forearm circumference (`CAT`) in centimeters and abdominal circumference (`CAB`) in centimeters.

General definitions of units, precision and plausible ranges remain in:

```text
/library/measurements
```

## 14. Scientific Restrictions

```text
restrictions:

    []
```

No additional explicit scientific restriction producing `INELIGIBLE` was identified for P-M14.

## 15. Source Conflict

```text
evidence:

    sourceConflict:

        null
```

No material unresolved conflict was identified for the P-M14 mathematical definition or the development and independent validation statistics in the source used.

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

Essential information:

```text
identity                      CONFIRMED

mathematicalDefinition        CONFIRMED

supportedSexes                CONFIRMED

originalDevelopmentAgeRange   CONFIRMED

originalPopulation            CONFIRMED

requiredInputs                CONFIRMED

definitionReference           CONFIRMED

sourceConflict                null
```

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:

            "P-M14"

        familyId:

            "petroski"

        displayName:

            "Petroski M14 — Masculino"

        aliasNames:

            [

                "Petroski M14",

                "Equação M14 de Petroski"

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

                region:

                    "Região central do Rio Grande do Sul e região litorânea de Santa Catarina"

                sexCoverage:

                    - MALE

                ageCoverage:

                    min: 18

                    max: 66

                sampleSize: 391

                bodyCharacteristicsNotes:

                    "População masculina adulta heterogênea em idade e composição corporal."

                sampleCharacteristics:

                    "Amostra masculina utilizada no desenvolvimento das

                     equações generalizadas."

                source:

                    Petroski (1995)

            validationPopulations:

                - description:

                    "Homens adultos da amostra independente de validação"

                  country: "Brazil"

                  region:

                    "Região central do Rio Grande do Sul e região litorânea de Santa Catarina"

                  sexCoverage:

                      - MALE

                  ageCoverage:

                      min: 18

                      max: 56

                  sampleSize: 87

                  bodyCharacteristicsNotes: null

                  sampleCharacteristics:

                      "Amostra independente utilizada para validar as equações."

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

                "Homens adultos, n = 391, 18–66 anos"

            criterionMethod:

                "Pesagem hidrostática"

            year:

                1995

            metrics:

                R: 0.885

                R2: 0.783

                standardError: 0.0072

        validationStudies:

            - studyReference:

                Petroski (1995)

              population:

                "Homens adultos, n = 87, 18–56 anos"

              criterionMethod:

                "Pesagem hidrostática"

              metrics:

                correlation:

                    0.844

                standardError:

                    0.0082

                meanDifference:

                    -0.0004

                rmse:

                    null

                otherMetrics:

                    "EC = -0.0004 g/ml; ET = 0.0082 g/ml;

                     EPE = 0.0082 g/ml."

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

            - SKINFOLD_PECTORAL

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

The scientific profile allows runtime evaluation of:

```text
sex:

    MALE → compatible with documented sex

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

    + 3 skinfolds

    + forearm circumference

    + abdominal circumference

output:

    BODY_DENSITY
```

The engine must not transform the mean development age into an eligibility limit, and absence of athlete, training-level or modality evidence must not become `INELIGIBLE`.

P-M14 uses the same three-skinfold combination as P-M13 (`Z3 = subscapular + triceps + pectoral`) but adds forearm and abdominal circumferences to the regression model.

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

        "[https://pt.scribd.com/document/8955761/Tese-Edio-Petroski](https://pt.scribd.com/document/8955761/Tese-Edio-Petroski)"
```

Additional bibliographic source:

```text
"Centro Esportivo Virtual — Desenvolvimento e Validação de Equações

Generalizadas Para a Estimativa da Densidade Corporal em Adultos."

url:

    "[https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/](https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/)"
```