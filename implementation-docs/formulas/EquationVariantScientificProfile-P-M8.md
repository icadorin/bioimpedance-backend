# EquationVariantScientificProfile — P-M8

## 1. Identification

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

The P-M8 equation is:

```text
D =
    1.09255357
    - 0.00067980 × X4
    + 0.00000182 × X4²
    - 0.00027287 × AGE
    + 0.00204435 × CIRCUMFERENCE_FOREARM
    - 0.00060405 × CIRCUMFERENCE_ABDOMEN
```

Where:

```text
X4 =
    SKINFOLD_TRICEPS
    + SKINFOLD_SUBSCAPULAR
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_MEDIAL_CALF
```

Additional mathematical inputs:

```text
AGE
CIRCUMFERENCE_FOREARM
CIRCUMFERENCE_ABDOMEN
```

Output:

```text
outputType:
    BODY_DENSITY
```

## 3. Applicability

### 3.1 Sex

```text
sex:

    supportedSexes:
        - MALE
```

P-M8 belongs to the male generalized equations developed by Petroski.

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

The development population comprised adult men aged 18–66 years. The independent validation sample comprised 87 men aged 18–56 years.

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
            "Rio Grande do Sul / Santa Maria region"

        sexCoverage:
            - MALE

        ageCoverage:
            min: 18
            max: 66

        sampleSize:
            391

        bodyCharacteristicsNotes:
            "Adult male population heterogeneous in age and body composition."

        sampleCharacteristics:
            "Male participants of the Petroski study; generalized equations
             developed using anthropometric measurements and hydrostatic weighing."

        source:
            Petroski (1995)

    validationPopulations:

        - description:
            "Homens adultos da amostra independente de validação"

          country:
            "Brazil"

          region:
            "Rio Grande do Sul / Santa Maria region"

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
            "Independent validation sample used to evaluate the generalized equations."

          source:
            Petroski (1995)
```

The study population was heterogeneous in age and body composition.

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

The available source does not provide a sufficiently explicit athlete/non-athlete classification that can be safely mapped to the platform's boolean model.

No athlete status is inferred.

## 6. Training Level Applicability

```text
trainingLevel:

    supportedLevels:
        []

    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

No reliable mapping to `SEDENTARY`, `RECREATIONAL`, `TRAINED`, `COMPETITIVE` or `ELITE` was established from the primary source.

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

The study describes heterogeneity in body composition, but does not establish an explicit P-M8 body-characteristic eligibility rule.

The use of four skinfolds plus two circumferences is a mathematical property of the equation and not a body-composition restriction.

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
        "Homens adultos, n = 391, 18–66 anos"

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

The P-M8 equation is reproduced in later academic validation literature with the same mathematical definition and an original-development age range of 18–66 years, `r = 0.88` and `ESE = 0.0071 g/cm³`.

## 10. Validation

The independent validation sample was:

```text
n:
    87 men

age:
    18–56 years
```

The criterion method was hydrostatic weighing.

For P-M8, the available validation table should be represented with the values reported for the M8 equation when directly confirmed from the Petroski source. Where an individual validation metric cannot be unambiguously assigned from the accessible extract, the corresponding field remains `null` rather than being inferred.

## 11. Cross-validation

```text
crossValidationStudies:
    []
```

The primary source presents the independent 87-man sample as the validation sample for the developed equations. It is therefore not duplicated here as a separate cross-validation study.

## 12. External Validation

```text
externalValidationStudies:
    []
```

No independent external-validation study is included in this profile.

Later use of P-M8 by another study does not automatically constitute external validation.

## 13. Measurement Requirements

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

`SEX` is not a mathematical input.

```text
SEX
    → Applicability

AGE
    → Formula input

4 skinfolds
    → Formula inputs

FOREARM circumference
    → Formula input

ABDOMEN circumference
    → Formula input
```

The global definitions, units, precision and plausible ranges belong to `/library/measurements`.

## 14. Scientific Restrictions

```text
restrictions:
    []
```

No additional explicit scientific restriction producing `INELIGIBLE` was identified for P-M8 beyond the documented male population/applicability context.

## 15. Source Conflict

```text
evidence:

    sourceConflict:
        null
```

No material unresolved conflict was identified for the P-M8 mathematical definition in the sources used for this profile.

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

Essential scientific information is documented sufficiently for the current library state.

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
                region: "Rio Grande do Sul / Santa Maria region"
                sexCoverage:
                    - MALE
                ageCoverage:
                    min: 18
                    max: 66
                sampleSize: 391
                bodyCharacteristicsNotes:
                    "Adult male population heterogeneous in age and body composition."
                sampleCharacteristics:
                    "Male participants of the Petroski study; generalized
                     equations developed using anthropometric measurements
                     and hydrostatic weighing."
                source:
                    Petroski (1995)

            validationPopulations:

                - description:
                    "Homens adultos da amostra independente de validação"
                  country: "Brazil"
                  region: "Rio Grande do Sul / Santa Maria region"
                  sexCoverage:
                      - MALE
                  ageCoverage:
                      min: 18
                      max: 56
                  sampleSize: 87
                  bodyCharacteristicsNotes:
                      null
                  sampleCharacteristics:
                      "Independent validation sample."
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
                R: 0.889
                R2: 0.791
                standardError: 0.0071

        validationStudies:

            - studyReference:
                Petroski (1995)

              population:
                "Homens adultos, n = 87, 18–56 anos"

              criterionMethod:
                "Pesagem hidrostática"

              metrics:
                correlation: null
                standardError: null
                meanDifference: null
                rmse: null
                otherMetrics: null

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
    + forearm circumference
    + abdominal circumference

output:
    BODY_DENSITY
```

The engine must not convert:

```text
developmentSampleMeanAge = 30.17
```

into an eligibility limit, and must not convert absence of athlete, training-level or modality evidence into `INELIGIBLE`.

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
