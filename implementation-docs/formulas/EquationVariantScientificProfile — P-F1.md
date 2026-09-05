# EquationVariantScientificProfile — P-F1

## 1. Identification

```text
identity:

    variantId:

        "P-F1"

    familyId:

        "petroski"

    displayName:

        "Petroski F1 — Feminino"

    aliasNames:

        [

            "Petroski F1",

            "Equação F1 de Petroski"

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

The P-F1 equation is:

```text
D =

    1.03987298

    - 0.00031853 × X9

    + 0.00000047 × X9²

    - 0.00025486 × AGE

    - 0.00047358 × BODY_MASS

    + 0.00046897 × HEIGHT
```

Where:

```text
X9 =

    SKINFOLD_SUBSCAPULAR

    + SKINFOLD_TRICEPS

    + SKINFOLD_BICEPS

    + SKINFOLD_PECTOral

    + SKINFOLD_MIDAXILLARY

    + SKINFOLD_SUPRAILIAC

    + SKINFOLD_ABDOMINAL

    + SKINFOLD_THIGH

    + SKINFOLD_MEDIAL_CALF
```

Output:

```text
outputType:

    BODY_DENSITY
```

The equation uses nine skinfolds, age, body mass and height. Petroski's notation defines X9 as the sum of the nine skinfolds: subscapular, triceps, biceps, pectoral, midaxillary, suprailiac, abdominal, thigh and medial calf.

## 3. Applicability

### 3.1 Sex

```text
sex:

    supportedSexes:

        - FEMALE
```

### 3.2 Age

```text
age:

    originalDevelopmentAgeRange:

        min: 18

        max: 51

    developmentSampleMeanAge:

        27.46

    validatedAgeRanges:

        - range:

            min: 18

            max: 43

          population:

            "Mulheres adultas da amostra independente de validação"

          source:

            Petroski (1995)

    explicitAgeRestriction:

        null
```

The female regression sample included 213 women aged 18–51 years, with mean age 27.46 years. The independent validation sample included 68 women aged 18–43 years, with mean age 27.18 years. The mean ages are descriptive and are not eligibility thresholds.

## 4. Population Applicability

```text
population:

    originalPopulation:

        description:

            "Mulheres adultas"

        country:

            "Brazil"

        region:

            "Região central do Rio Grande do Sul e região litorânea de Santa Catarina"

        sexCoverage:

            - FEMALE

        ageCoverage:

            min: 18

            max: 51

        sampleSize:

            213

        bodyCharacteristicsNotes:

            "Amostra feminina heterogênea em idade e gordura corporal."

        sampleCharacteristics:

            "Amostra feminina de regressão utilizada no desenvolvimento

             das equações generalizadas de Petroski."

        source:

            Petroski (1995)

    validationPopulations:

        - description:

            "Mulheres adultas da amostra independente de validação"

          country:

            "Brazil"

          region:

            "Região central do Rio Grande do Sul e região litorânea de Santa Catarina"

          sexCoverage:

            - FEMALE

          ageCoverage:

            min: 18

            max: 43

          sampleSize:

            68

          bodyCharacteristicsNotes:

            null

          sampleCharacteristics:

            "Amostra independente utilizada para validar as equações generalizadas femininas."

          source:

            Petroski (1995)
```

The thesis describes a total female sample of 281 women, randomly divided into a regression sample of 213 and an independent validation sample of 68. The regression sample had age range 18–51 years and body-fat range of 11.11–36.18%.

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

No sufficiently explicit athlete/non-athlete classification compatible with the platform's boolean model is assigned to the P-F1 development or validation samples.

The thesis discusses athlete populations in other studies, but those populations are not the P-F1 development or validation samples.

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

No explicit P-F1 body-characteristic eligibility rule is documented.

The development sample was intentionally heterogeneous in age and body-fat percentage, covering ages 18–51 years and body-fat values from 11.11% to 36.18%. This describes the study sample rather than establishing runtime eligibility limits.

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

        "Mulheres adultas, n = 213, 18–51 anos"

    criterionMethod:

        "Densidade corporal determinada por pesagem hidrostática"

    year:

        1995

    metrics:

        R:

            0.861

        R2:

            0.742

        standardError:

            0.0065
```

For P-F1, Petroski reported R = 0.861, R² = 0.742 and standard error of estimate = 0.0065 g/ml in the regression sample.

## 10. Validation

The independent validation sample was:

```text
n:

    68 women

age:

    18–43 years
```

Criterion method:

```text
hydrostatic weighing
```

For P-F1:

```text
correlation:

    0.773

constantError:

    -0.00025 g/ml

totalError:

    0.0065 g/ml

standardErrorOfEstimate:

    0.0065 g/ml
```

The validation table reports, for F1, r = 0.773, t = -0.308, EC = -0.00025 g/ml, ET = 0.0065 g/ml and EPE = 0.0065 g/ml.

## 11. Cross-validation

```text
crossValidationStudies:

    []
```

The thesis uses the independent 68-woman sample under `Validation`. Its separate cross-validation analysis evaluates generalized and specific equations originating from other populations, rather than providing an additional P-F1-specific cross-validation study.

## 12. External Validation

```text
externalValidationStudies:

    []
```

No external validation study of P-F1 outside the original Petroski investigation is included in this profile.

## 13. Measurement Requirements

```text
inputs:

    requiredInputs:

        - AGE

        - BODY_MASS

        - HEIGHT

        - SKINFOLD_SUBSCAPULAR

        - SKINFOLD_TRICEPS

        - SKINFOLD_BICEPS

        - SKINFOLD_PECTORAL

        - SKINFOLD_MIDAXILLARY

        - SKINFOLD_SUPRAILIAC

        - SKINFOLD_ABDOMINAL

        - SKINFOLD_THIGH

        - SKINFOLD_MEDIAL_CALF

    optionalInputs:

        []
```

Mathematical inputs:

```text
AGE

BODY_MASS

HEIGHT

X9
```

where:

```text
X9 =

    SKINFOLD_SUBSCAPULAR

    + SKINFOLD_TRICEPS

    + SKINFOLD_BICEPS

    + SKINFOLD_PECTORAL

    + SKINFOLD_MIDAXILLARY

    + SKINFOLD_SUPRAILIAC

    + SKINFOLD_ABDOMINAL

    + SKINFOLD_THIGH

    + SKINFOLD_MEDIAL_CALF
```

`SEX` is not included in `requiredInputs`; it determines applicability to the female variant.

According to the thesis notation, age is expressed in years, body mass (`MC`) in kilograms, height (`ES`) in centimeters and skinfolds in millimeters.

General definitions of units, precision and plausible ranges remain in:

```text
/library/measurements
```

## 14. Scientific Restrictions

```text
restrictions:

    []
```

No additional explicit scientific restriction producing `INELIGIBLE` was identified.

The study's observed age and body-fat ranges describe the source population and should not automatically be converted into hard platform restrictions.

## 15. Source Conflict

```text
evidence:

    sourceConflict:

        null
```

No material unresolved conflict was identified for the P-F1 mathematical definition or the reported development and independent validation statistics in the source used.

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

            "P-F1"

        familyId:

            "petroski"

        displayName:

            "Petroski F1 — Feminino"

        aliasNames:

            [

                "Petroski F1",

                "Equação F1 de Petroski"

            ]

    applicability:

        sex:

            supportedSexes:

                - FEMALE

        age:

            originalDevelopmentAgeRange:

                min: 18

                max: 51

            developmentSampleMeanAge:

                27.46

            validatedAgeRanges:

                - range:

                    min: 18

                    max: 43

                  population:

                    "Mulheres adultas da amostra independente de validação"

                  source:

                    Petroski (1995)

            explicitAgeRestriction:

                null

        population:

            originalPopulation:

                description:

                    "Mulheres adultas"

                country:

                    "Brazil"

                region:

                    "Região central do Rio Grande do Sul e região litorânea de Santa Catarina"

                sexCoverage:

                    - FEMALE

                ageCoverage:

                    min: 18

                    max: 51

                sampleSize:

                    213

                bodyCharacteristicsNotes:

                    "Amostra feminina heterogênea em idade e gordura corporal."

                sampleCharacteristics:

                    "Amostra feminina de regressão utilizada no desenvolvimento

                     das equações generalizadas."

                source:

                    Petroski (1995)

            validationPopulations:

                - description:

                    "Mulheres adultas da amostra independente de validação"

                  country:

                    "Brazil"

                  region:

                    "Região central do Rio Grande do Sul e região litorânea de Santa Catarina"

                  sexCoverage:

                    - FEMALE

                  ageCoverage:

                    min: 18

                    max: 43

                  sampleSize:

                    68

                  bodyCharacteristicsNotes:

                    null

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

                "Mulheres adultas, n = 213, 18–51 anos"

            criterionMethod:

                "Pesagem hidrostática"

            year:

                1995

            metrics:

                R: 0.861

                R2: 0.742

                standardError: 0.0065

        validationStudies:

            - studyReference:

                Petroski (1995)

              population:

                "Mulheres adultas, n = 68, 18–43 anos"

              criterionMethod:

                "Pesagem hidrostática"

              metrics:

                correlation:

                    0.773

                standardError:

                    0.0065

                meanDifference:

                    -0.00025

                rmse:

                    null

                otherMetrics:

                    "EC = -0.00025 g/ml; ET = 0.0065 g/ml;

                     EPE = 0.0065 g/ml."

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

            - BODY_MASS

            - HEIGHT

            - SKINFOLD_SUBSCAPULAR

            - SKINFOLD_TRICEPS

            - SKINFOLD_BICEPS

            - SKINFOLD_PECTORAL

            - SKINFOLD_MIDAXILLARY

            - SKINFOLD_SUPRAILIAC

            - SKINFOLD_ABDOMINAL

            - SKINFOLD_THIGH

            - SKINFOLD_MEDIAL_CALF

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

    FEMALE → compatible with documented sex

age:

    compare against documented population evidence

    without using 27.46 as an eligibility threshold

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

    + BODY_MASS

    + HEIGHT

    + 9 skinfolds

output:

    BODY_DENSITY
```

The engine must not transform the mean development age into an eligibility limit, and absence of athlete, training-level or modality evidence must not become `INELIGIBLE`.

P-F1 is the first female Petroski variant and uses the largest skinfold set in the female series: **nine skinfolds + age + body mass + height**. The regression model achieved R = 0.861 and EPE = 0.0065 g/ml.

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