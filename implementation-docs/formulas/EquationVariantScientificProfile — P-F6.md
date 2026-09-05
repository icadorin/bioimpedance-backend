# EquationVariantScientificProfile — P-F6

## 1. Identification

```text

identity:

    variantId:

        "P-F6"

    familyId:

        "petroski"

    displayName:

        "Petroski F6 — Feminino"

    aliasNames:

        [

            "Petroski F6",

            "Equação F6 de Petroski"

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

The P-F6 equation is:

```text

D =

    1.21527404

    - 0.06432107 × LOG10(Y7)

    - 0.00033650 × AGE

    - 0.00049553 × CIRCUMFERENCE_THIGH

```

Where:

```text

Y7 =

    SKINFOLD_SUBSCAPULAR

    + SKINFOLD_TRICEPS

    + SKINFOLD_MIDAXILLARY

    + SKINFOLD_PECTORAL

    + SKINFOLD_SUPRAILIAC

    + SKINFOLD_ABDOMINAL

    + SKINFOLD_THIGH

```

Output:

```text

outputType:

    BODY_DENSITY

```

The equation uses the base-10 logarithm of the sum of seven skinfolds, together with age and thigh circumference. The seven-skinfold combination is the `Y7` configuration of Petroski's female equations.

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

The mean age is descriptive and is not an eligibility threshold.

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

The total female study sample contained 281 women, divided into 213 women for regression and 68 for independent validation. The regression sample covered 18–51 years and included women with heterogeneous levels of body fat.

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

No sufficiently explicit athlete/non-athlete classification compatible with the platform's boolean model is assigned to the P-F6 development or validation samples.

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

No explicit P-F6 body-characteristic eligibility rule is documented.

The observed study ranges describe the development population and are not converted automatically into hard runtime restrictions.

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

            "[[https://pt.scribd.com/document/8955761/Tese-Edio-Petroski](https://pt.scribd.com/document/8955761/Tese-Edio-Petroski)](https://pt.scribd.com/document/8955761/Tese-Edio-Petroski)"

    population:

        "Mulheres adultas, n = 213, 18–51 anos"

    criterionMethod:

        "Densidade corporal determinada por pesagem hidrostática"

    year:

        1995

    metrics:

        R:

            0.836

        R2:

            0.699

        standardError:

            0.0070

```

P-F6 presented `R = 0.836`, `R² = 0.699` and `EPE = 0.0070 g/ml` in the regression sample.

The model uses the logarithm of the seven-skinfold `Y7` aggregate and additionally includes age and thigh circumference.

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

For P-F6:

```text

correlation:

    0.723

constantError:

    -0.00004 g/ml

totalError:

    0.0072 g/ml

standardErrorOfEstimate:

    0.0070 g/ml

```

The validation table reports a mean estimated density of `1.046349 ± 0.0094 g/ml`, `r = 0.723`, `t = -0.041`, `EC = -0.00004 g/ml`, `ET = 0.0072 g/ml` and `EPE = 0.0070 g/ml`.

The reported constant error is very close to zero, indicating a very small mean difference between measured and estimated body density in the independent validation sample.

## 11. Cross-validation

```text

crossValidationStudies:

    []

```

The independent 68-woman sample is represented under `Validation` rather than duplicated under `Cross-validation`.

Petroski's separate cross-validation analysis evaluates equations from other investigators and does not constitute an additional P-F6-specific cross-validation study.

## 12. External Validation

```text

externalValidationStudies:

    []

```

No external validation study of P-F6 outside the original Petroski investigation is included in this profile.

## 13. Measurement Requirements

```text

inputs:

    requiredInputs:

        - AGE

        - SKINFOLD_SUBSCAPULAR

        - SKINFOLD_TRICEPS

        - SKINFOLD_MIDAXILLARY

        - SKINFOLD_PECTORAL

        - SKINFOLD_SUPRAILIAC

        - SKINFOLD_ABDOMINAL

        - SKINFOLD_THIGH

        - CIRCUMFERENCE_THIGH

    optionalInputs:

        []

```

Mathematical inputs:

```text

AGE

Y7

CIRCUMFERENCE_THIGH

```

where:

```text

Y7 =

    SKINFOLD_SUBSCAPULAR

    + SKINFOLD_TRICEPS

    + SKINFOLD_MIDAXILLARY

    + SKINFOLD_PECTORAL

    + SKINFOLD_SUPRAILIAC

    + SKINFOLD_ABDOMINAL

    + SKINFOLD_THIGH

```

and:

```text

LOG10(Y7)

```

is used in the mathematical equation.

`SEX` is not included in `requiredInputs`; it determines applicability to the female variant.

According to the thesis notation, age is expressed in years, skinfolds in millimeters and thigh circumference in centimeters. The source defines `Y7` as the sum of the subscapular, triceps, midaxillary, pectoral, suprailiac, abdominal and thigh skinfolds.

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

The development age range of 18–51 years is treated as population evidence rather than an automatically generated runtime restriction.

The requirement for `CIRCUMFERENCE_THIGH` is mathematical rather than an independent eligibility restriction.

## 15. Source Conflict

```text

evidence:

    sourceConflict:

        null

```

No material unresolved conflict was identified for the P-F6 mathematical definition or the reported development and independent validation statistics.

The mathematical definition is directly reported in the female generalized-equation table of the Petroski thesis.

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

            "P-F6"

        familyId:

            "petroski"

        displayName:

            "Petroski F6 — Feminino"

        aliasNames:

            [

                "Petroski F6",

                "Equação F6 de Petroski"

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

                R: 0.836

                R2: 0.699

                standardError: 0.0070

        validationStudies:

            - studyReference:

                Petroski (1995)

              population:

                "Mulheres adultas, n = 68, 18–43 anos"

              criterionMethod:

                "Pesagem hidrostática"

              metrics:

                correlation:

                    0.723

                standardError:

                    0.0070

                meanDifference:

                    -0.00004

                rmse:

                    null

                otherMetrics:

                    "EC = -0.00004 g/ml; ET = 0.0072 g/ml;

                     EPE = 0.0070 g/ml."

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

            - SKINFOLD_MIDAXILLARY

            - SKINFOLD_PECTORAL

            - SKINFOLD_SUPRAILIAC

            - SKINFOLD_ABDOMINAL

            - SKINFOLD_THIGH

            - CIRCUMFERENCE_THIGH

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

    + 7 skinfolds

    + CIRCUMFERENCE_THIGH

output:

    BODY_DENSITY

```

The engine must not transform the mean development age into an eligibility limit, and absence of athlete, training-level or modality evidence must not become `INELIGIBLE`.

P-F6 is the **seven-skinfold logarithmic Petroski female variant with thigh circumference**, using:

```text

Y7 =

    subscapular

    + triceps

    + midaxillary

    + pectoral

    + suprailiac

    + abdominal

    + thigh

```

Unlike P-F5, P-F6 additionally requires `CIRCUMFERENCE_THIGH` as a mathematical predictor.

Therefore:

```text

P-F5:

    AGE + Y7

P-F6:

    AGE + Y7 + CIRCUMFERENCE_THIGH

```

The two variants must remain distinct in the `SuggestionEngine`, even though they share the same seven-skinfold aggregate.

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

        "[[https://pt.scribd.com/document/8955761/Tese-Edio-Petroski](https://pt.scribd.com/document/8955761/Tese-Edio-Petroski)](https://pt.scribd.com/document/8955761/Tese-Edio-Petroski)"

```

Additional bibliographic source:

```text

"Centro Esportivo Virtual — Desenvolvimento e Validação de Equações

Generalizadas Para a Estimativa da Densidade Corporal em Adultos."

url:

    "[[https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/](https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/)](https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/)"

```