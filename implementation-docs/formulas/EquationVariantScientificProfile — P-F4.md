# EquationVariantScientificProfile — P-F4

## 1. Identification

```text
identity:

    variantId:

        "P-F4"

    familyId:

        "petroski"

    displayName:

        "Petroski F4 — Feminino"

    aliasNames:

        [

            "Petroski F4",

            "Equação F4 de Petroski"

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

The P-F4 equation is:

```text
D =

    1.03992377

    - 0.00036083 × X7

    + 0.00000058 × X7²

    - 0.00027099 × AGE

    - 0.00046621 × BODY_MASS

    + 0.00047136 × HEIGHT
```

Where:

```text
X7 =

    SKINFOLD_SUBSCAPULAR

    + SKINFOLD_TRICEPS

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

The equation uses seven skinfolds, age, body mass and height.

The seven-skinfold sum is the `X7` combination defined in Petroski's Table 4.

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

The female regression sample contained 213 women aged 18–51 years, with mean age 27.46 years. The independent validation sample contained 68 women aged 18–43 years, with mean age 27.18 years.

The mean ages are descriptive and are not eligibility thresholds.

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

The study included 281 women in total, with 213 assigned to regression and 68 to independent validation. In the regression sample, age ranged from 18–51 years, body mass from 43.80–87.40 kg, height from 143.00–177.10 cm and body fat from 11.11–36.18%.

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

No sufficiently explicit athlete/non-athlete classification compatible with the platform's boolean model is assigned to the P-F4 development or validation samples.

The thesis discusses other athlete populations in its literature review, but those populations are not the P-F4 development or validation samples.

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

No explicit P-F4 body-characteristic eligibility rule is documented.

The observed body-mass, height and body-fat ranges describe the study sample and are not converted automatically into hard runtime eligibility restrictions.

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

            0.863

        R2:

            0.744

        standardError:

            0.0064
```

For P-F4, Petroski reported `R = 0.863`, `R² = 0.744` and `EPE = 0.0064 g/ml`. This is one of the lower-error models in the female set.

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

For P-F4:

```text
correlation:

    0.778

constantError:

    -0.00019 g/ml

totalError:

    0.0064 g/ml

standardErrorOfEstimate:

    0.0064 g/ml
```

The validation table reports an estimated mean density of `1.046192 ± 0.0092 g/ml`, `r = 0.778`, `t = -0.246`, `EC = -0.00019 g/ml`, `ET = 0.0064 g/ml` and `EPE = 0.0064 g/ml`.

## 11. Cross-validation

```text
crossValidationStudies:

    []
```

The independent 68-woman sample is represented under `Validation` rather than duplicated under `Cross-validation`.

Petroski's separate cross-validation analysis concerns equations originating from other populations and investigators; it does not represent an additional P-F4-specific cross-validation study.

## 12. External Validation

```text
externalValidationStudies:

    []
```

No external validation study of P-F4 outside the original Petroski investigation is included in this profile.

Later use of Petroski equations in other studies is not automatically classified as external validation.

## 13. Measurement Requirements

```text
inputs:

    requiredInputs:

        - AGE

        - BODY_MASS

        - HEIGHT

        - SKINFOLD_SUBSCAPULAR

        - SKINFOLD_TRICEPS

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

X7
```

where:

```text
X7 =

    SKINFOLD_SUBSCAPULAR

    + SKINFOLD_TRICEPS

    + SKINFOLD_MIDAXILLARY

    + SKINFOLD_SUPRAILIAC

    + SKINFOLD_ABDOMINAL

    + SKINFOLD_THIGH

    + SKINFOLD_MEDIAL_CALF
```

`SEX` is not included in `requiredInputs`; it determines applicability to the female variant.

According to the thesis notation, age is expressed in years, body mass in kilograms, height in centimeters and skinfolds in millimeters.

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

The development age range of 18–51 years is evidence about the population studied, not an automatically generated runtime restriction.

## 15. Source Conflict

```text
evidence:

    sourceConflict:

        null
```

No material unresolved conflict was identified for the P-F4 mathematical definition or the reported development and independent validation statistics.

The mathematical definition is directly presented in Petroski's Table 4, and the corresponding validation results are directly presented in Table 5.

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

            "P-F4"

        familyId:

            "petroski"

        displayName:

            "Petroski F4 — Feminino"

        aliasNames:

            [

                "Petroski F4",

                "Equação F4 de Petroski"

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

                  country: "Brazil"

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

                R: 0.863

                R2: 0.744

                standardError: 0.0064

        validationStudies:

            - studyReference:

                Petroski (1995)

              population:

                "Mulheres adultas, n = 68, 18–43 anos"

              criterionMethod:

                "Pesagem hidrostática"

              metrics:

                correlation:

                    0.778

                standardError:

                    0.0064

                meanDifference:

                    -0.00019

                rmse:

                    null

                otherMetrics:

                    "EC = -0.00019 g/ml; ET = 0.0064 g/ml;

                     EPE = 0.0064 g/ml."

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

    + 7 skinfolds

output:

    BODY_DENSITY
```

The engine must not transform the mean development age into an eligibility limit, and absence of athlete, training-level or modality evidence must not become `INELIGIBLE`.

P-F4 is the **seven-skinfold quadratic model with age, body mass and height**. Its `X7` combination is distinct from the nine-skinfold models: it excludes biceps and pectoral and uses subscapular, triceps, midaxillary, suprailiac, abdominal, thigh and medial calf.

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