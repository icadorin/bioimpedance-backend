# EquationVariantScientificProfile — P-M13

## 1. Identification

```text
identity:

    variantId:

        "P-M13"

    familyId:

        "petroski"

    displayName:

        "Petroski M13 — Masculino"

    aliasNames:

        [

            "Petroski M13",

            "Equação M13 de Petroski"

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

The P-M13 equation is:

```text
D =

    1.10404686

    - 0.00111938 × Z3

    + 0.00000391 × Z3²

    - 0.00027884 × AGE
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

The equation uses three skinfolds and age. The three skinfolds are subscapular, triceps and pectoral.

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

The development population included adult men aged 18–66 years, with mean age 30.17 years. The independent validation sample included 87 men aged 18–56 years. The development mean is descriptive and is not an eligibility threshold.

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

The original male development sample comprised 391 men aged 18–66 years. The independent validation sample comprised 87 men aged 18–56 years.

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

No explicit P-M13 body-characteristic eligibility rule is documented.

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

            0.873

        R2:

            0.763

        standardError:

            0.0075
```

For P-M13, Petroski reported R = 0.873, R² = 0.763 and standard error of estimate = 0.0075 g/ml in the development sample.

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

For P-M13:

```text
correlation:

    0.832

constantError:

    -0.0004 g/ml

totalError:

    0.0085 g/ml

standardErrorOfEstimate:

    0.0085 g/ml
```

These values are reported in Petroski's independent validation analysis for the 87-man sample.

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

        - SKINFOLD_PECTORAL

    optionalInputs:

        []
```

Mathematical inputs:

```text
AGE

Z3
```

where:

```text
Z3 =

    SKINFOLD_SUBSCAPULAR

    + SKINFOLD_TRICEPS

    + SKINFOLD_PECTORAL
```

`SEX` is not included in `requiredInputs`; it determines applicability to the male variant.

General definitions of units, precision and plausible ranges remain in:

```text
/library/measurements
```

Petroski defines Z3 as the sum of subscapular, triceps and pectoral skinfolds.

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

No material unresolved conflict was identified for the P-M13 mathematical definition or the reported development and validation statistics in the source used.

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

            "P-M13"

        familyId:

            "petroski"

        displayName:

            "Petroski M13 — Masculino"

        aliasNames:

            [

                "Petroski M13",

                "Equação M13 de Petroski"

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

                R: 0.873

                R2: 0.763

                standardError: 0.0075

        validationStudies:

            - studyReference:

                Petroski (1995)

              population:

                "Homens adultos, n = 87, 18–56 anos"

              criterionMethod:

                "Pesagem hidrostática"

              metrics:

                correlation:

                    0.832

                standardError:

                    0.0085

                meanDifference:

                    -0.0004

                rmse:

                    null

                otherMetrics:

                    "EC = -0.0004 g/ml; ET = 0.0085 g/ml;

                     EPE = 0.0085 g/ml."

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

P-M13 is the three-skinfold + age Petroski variant using the **Z3** combination (subscapular + triceps + pectoral), rather than the **X3** combination used by P-M11 and P-M12.

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