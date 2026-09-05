# EquationVariantScientificProfile — P-M11

## 1. Identification

```text
identity:

    variantId:

        "P-M11"

    familyId:

        "petroski"

    displayName:

        "Petroski M11 — Masculino"

    aliasNames:

        [

            "Petroski M11",

            "Equação M11 de Petroski"

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

The P-M11 equation is:

```text
D =

    1.10491700

    - 0.00099061 × X3

    + 0.00000327 × X3²

    - 0.00034527 × AGE
```

Where:

```text
X3 =

    SKINFOLD_SUBSCAPULAR

    + SKINFOLD_TRICEPS

    + SKINFOLD_SUPRAILIAC
```

Output:

```text
outputType:

    BODY_DENSITY
```

The equation uses three skinfolds and age.

The three skinfolds are subscapular, triceps and suprailiac.

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

The development population included adult men aged 18–66 years, with mean age 30.17 years. The independent validation sample included 87 men aged 18–56 years.

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

The original male sample comprised 391 men aged 18–66 years from the central region of Rio Grande do Sul and the coastal region of Santa Catarina. The independent validation sample comprised 87 men aged 18–56 years.

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

No explicit P-M11 body-characteristic eligibility rule is documented.

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

            0.871

        R2:

            0.759

        standardError:

            0.0076
```

The P-M11 development model was one of the 16 generalized male equations developed by Petroski. It uses three skinfolds and age. The reported multiple correlation was R = 0.871, with R² = 0.759 and standard error of estimate of 0.0076 g/ml.

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

For P-M11:

```text
correlation:

    0.839

constantError:

    0.0004 g/ml

totalError:

    0.0083 g/ml

standardErrorOfEstimate:

    0.0084 g/ml
```

These values correspond to the independent validation analysis reported by Petroski (1995).

## 11. Cross-validation

```text
crossValidationStudies:

    []
```

The independent 87-man sample is represented under `Validation` rather than duplicated under `Cross-validation`.

## 12. External Validation

```text
externalValidationStudies:

    []
```

No external validation study is included in this profile.

## 13. Measurement Requirements

```text
inputs:

    requiredInputs:

        - AGE

        - SKINFOLD_SUBSCAPULAR

        - SKINFOLD_TRICEPS

        - SKINFOLD_SUPRAILIAC

    optionalInputs:

        []
```

Mathematical inputs:

```text
AGE

X3
```

where:

```text
X3 =

    SKINFOLD_SUBSCAPULAR

    + SKINFOLD_TRICEPS

    + SKINFOLD_SUPRAILIAC
```

`SEX` is not included in `requiredInputs`; it determines applicability to the male variant.

General definitions of units, precision and plausible ranges remain in:

```text
/library/measurements
```

The source defines X3 as the sum of the subscapular, triceps and suprailiac skinfolds.

## 14. Scientific Restrictions

```text
restrictions:

    []
```

No additional explicit scientific restriction producing `INELIGIBLE` was identified.

## 15. Source Conflict

```text
evidence:

    sourceConflict:

        null
```

No material unresolved conflict was identified for the P-M11 mathematical definition or the reported development and validation statistics in the source used.

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

            "P-M11"

        familyId:

            "petroski"

        displayName:

            "Petroski M11 — Masculino"

        aliasNames:

            [

                "Petroski M11",

                "Equação M11 de Petroski"

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

                R: 0.871

                R2: 0.759

                standardError: 0.0076

        validationStudies:

            - studyReference:

                Petroski (1995)

              population:

                "Homens adultos, n = 87, 18–56 anos"

              criterionMethod:

                "Pesagem hidrostática"

              metrics:

                correlation:

                    0.839

                standardError:

                    0.0084

                meanDifference:

                    0.0004

                rmse:

                    null

                otherMetrics:

                    "EC = 0.0004 g/ml; ET = 0.0083 g/ml;

                     EPE = 0.0084 g/ml."

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

output:

    BODY_DENSITY
```

The engine must not transform the mean development age into an eligibility limit, and absence of athlete, training-level or modality evidence must not become `INELIGIBLE`.

The P-M11 is particularly relevant when the assessment contains the three required skinfolds—subscapular, triceps and suprailiac—because these are the exact measurements used by the equation.

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