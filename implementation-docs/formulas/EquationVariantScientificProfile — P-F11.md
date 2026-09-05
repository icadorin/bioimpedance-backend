# EquationVariantScientificProfile — P-F11

**## 1. Identification**

```text id="w2j7qs"
identity:

    variantId:

        "P-F11"

    familyId:

        "petroski"

    displayName:

        "Petroski F11 — Feminino"

    aliasNames:

        [

            "Petroski F11",

            "Equação F11 de Petroski"

        ]
```

Primary reference:

```text id="k5m2x8"
Petroski, E. L. (1995).

Desenvolvimento e validação de equações generalizadas para a estimativa

da densidade corporal em adultos.

Tese de Doutorado.

Universidade Federal de Santa Maria (UFSM),

Santa Maria, RS, Brasil.
```

**## 2. Mathematical Definition**

The P-F11 equation is:

```text id="n8r4ta"
D =

    1.19547130

    - 0.07513507 × LOG10(Y4)

    - 0.00041072 × AGE
```

Where:

```text id="v7q3cm"
Y4 =

    SKINFOLD_MIDAXILLARY

    + SKINFOLD_SUPRAILIAC

    + SKINFOLD_THIGH

    + SKINFOLD_MEDIAL_CALF
```

Output:

```text id="p4s7ye"
outputType:

    BODY_DENSITY
```

The equation uses the base-10 logarithm of the sum of four skinfolds together with age. The four-skinfold combination is the `Y4` configuration of Petroski's female equations.

**## 3. Applicability**

**### 3.1 Sex**

```text id="c6b4mz"
sex:

    supportedSexes:

        - FEMALE
```

**### 3.2 Age**

```text id="f8x2qd"
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

**## 4. Population Applicability**

```text id="m7u9pe"
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

The total female study sample contained 281 women, divided into 213 women for regression and 68 for independent validation. The regression sample covered 18–51 years and presented heterogeneity in age and body fat.

**## 5. Athlete Applicability**

```text id="a5x1qn"
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

No sufficiently explicit athlete/non-athlete classification compatible with the platform's boolean model is assigned to the P-F11 development or validation samples.

**## 6. Training Level Applicability**

```text id="g4y8ps"
trainingLevel:

    supportedLevels:

        []

    notes:

        "Não documentado segundo a escala operacional da plataforma."
```

**## 7. Modality Applicability**

```text id="r6t2kw"
modality:

    supportedModalities:

        []

    notes:

        "Não documentado em termos de modalidades esportivas específicas."
```

**## 8. Body Characteristics**

```text id="z9c5av"
bodyCharacteristics:

    rules:

        []
```

No explicit P-F11 body-characteristic eligibility rule is documented.

The observed study ranges describe the development population and are not converted automatically into hard runtime restrictions.

**## 9. Validation Evidence**

**### 9.1 Development Evidence**

```text id="u3m8hd"
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

            0.829

        R2:

            0.688

        standardError:

            0.0071
```

P-F11 presented `R = 0.829`, `R² = 0.688` and `EPE = 0.0071 g/ml` in the regression sample.

The model is the four-skinfold logarithmic female equation using age as the additional predictor.

**## 10. Validation**

The independent validation sample was:

```text id="b8w3ju"
n:

    68 women

age:

    18–43 years
```

Criterion method:

```text id="y1s6cn"
hydrostatic weighing
```

For P-F11:

```text id="c4z8pk"
correlation:

    0.722

constantError:

    -0.00007 g/ml

totalError:

    0.0072 g/ml

standardErrorOfEstimate:

    0.0070 g/ml
```

The validation table reports a mean estimated density of `1.046320 ± 0.0092 g/ml`, `r = 0.722`, `t = -0.074`, `EC = -0.00007 g/ml`, `ET = 0.0072 g/ml` and `EPE = 0.0070 g/ml`.

The reported constant error is very close to zero, indicating a very small mean difference between measured and estimated body density in the independent validation sample.

**## 11. Cross-validation**

```text id="x5r2ms"
crossValidationStudies:

    []
```

The independent 68-woman sample is represented under `Validation` rather than duplicated under `Cross-validation`.

Petroski's separate cross-validation analysis evaluates equations from other investigators and does not constitute an additional P-F11-specific cross-validation study.

**## 12. External Validation**

```text id="n3h6qa"
externalValidationStudies:

    []
```

No external validation study of P-F11 outside the original Petroski investigation is included in this profile.

A later publication may have used P-F11 for body-density or body-fat estimation, but use of the equation in another study is not itself treated as external validation of the equation.

**## 13. Measurement Requirements**

```text id="q8v2nd"
inputs:

    requiredInputs:

        - AGE

        - SKINFOLD_MIDAXILLARY

        - SKINFOLD_SUPRAILIAC

        - SKINFOLD_THIGH

        - SKINFOLD_MEDIAL_CALF

    optionalInputs:

        []
```

Mathematical inputs:

```text id="r4m7yc"
AGE

Y4
```

where:

```text id="s6k1pf"
Y4 =

    SKINFOLD_MIDAXILLARY

    + SKINFOLD_SUPRAILIAC

    + SKINFOLD_THIGH

    + SKINFOLD_MEDIAL_CALF
```

and:

```text id="e7n3wx"
LOG10(Y4)
```

is used in the mathematical equation.

`SEX` is not included in `requiredInputs`; it determines applicability to the female variant.

According to the thesis notation, age is expressed in years and skinfolds in millimeters. The source defines `Y4` as the sum of the midaxillary, suprailiac, thigh and medial calf skinfolds.

General definitions of units, precision and plausible ranges remain in:

```text id="k2x8vf"
/library/measurements
```

**## 14. Scientific Restrictions**

```text id="p3w6za"
restrictions:

    []
```

No additional explicit scientific restriction producing `INELIGIBLE` was identified.

The development age range of 18–51 years is treated as population evidence rather than an automatically generated runtime restriction.

The requirement for the four component skinfolds is a mathematical input requirement rather than an independent eligibility restriction.

The `Y4` aggregate must be valid before applying `LOG10(Y4)`.

**## 15. Source Conflict**

```text id="m5c7yr"
evidence:

    sourceConflict:

        null
```

No material unresolved conflict was identified for the P-F11 mathematical definition or the reported development and independent validation statistics.

The mathematical definition is directly reported in the female generalized-equation table of the Petroski thesis.

**## 16. Lifecycle**

```text id="a8n4qd"
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

```text id="w7p3km"
identity                      CONFIRMED

mathematicalDefinition        CONFIRMED

supportedSexes                CONFIRMED

originalDevelopmentAgeRange   CONFIRMED

originalPopulation            CONFIRMED

requiredInputs                CONFIRMED

definitionReference           CONFIRMED

sourceConflict                null
```

**## 17. Ficha Consolidada**

```text id="z6d2px"
EquationVariantScientificProfile

    identity:

        variantId:

            "P-F11"

        familyId:

            "petroski"

        displayName:

            "Petroski F11 — Feminino"

        aliasNames:

            [

                "Petroski F11",

                "Equação F11 de Petroski"

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

                R: 0.829

                R2: 0.688

                standardError: 0.0071

        validationStudies:

            - studyReference:

                Petroski (1995)

              population:

                "Mulheres adultas, n = 68, 18–43 anos"

              criterionMethod:

                "Pesagem hidrostática"

              metrics:

                correlation:

                    0.722

                standardError:

                    0.0070

                meanDifference:

                    -0.00007

                rmse:

                    null

                otherMetrics:

                    "EC = -0.00007 g/ml; ET = 0.0072 g/ml;

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

            - SKINFOLD_MIDAXILLARY

            - SKINFOLD_SUPRAILIAC

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

**## 18. Interpretação para o SuggestionEngine**

The scientific profile allows runtime evaluation of:

```text id="p2x6mw"
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

    + 4 skinfolds

output:

    BODY_DENSITY
```

The engine must not transform the mean development age into an eligibility limit, and absence of athlete, training-level or modality evidence must not become `INELIGIBLE`.

P-F11 is the **four-skinfold logarithmic Petroski female variant**, using:

```text id="c8r5yj"
Y4 =

    midaxillary

    + suprailiac

    + thigh

    + medial calf
```

In addition to `Y4`, the equation requires only `AGE`.

Therefore:

```text id="m9f3qa"
P-F10:

    AGE + BODY_MASS + HEIGHT + Y4

P-F11:

    AGE + Y4
```

P-F10 and P-F11 share the same four-skinfold `Y4` configuration but are mathematically distinct.

P-F10 is a quadratic model that additionally uses body mass and height, whereas P-F11 is a logarithmic model using only age in addition to the skinfold aggregate.

The `SuggestionEngine` must keep these variants mathematically and operationally distinct.

**## 19. Reference**

```text id="r2k7hs"
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

```text id="n6w4zc"
"Centro Esportivo Virtual — Desenvolvimento e Validação de Equações

Generalizadas Para a Estimativa da Densidade Corporal em Adultos."

url:

    "[[https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/](https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/)](https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/)"
```