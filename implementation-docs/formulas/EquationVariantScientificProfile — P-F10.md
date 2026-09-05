# EquationVariantScientificProfile — P-F10

**## 1. Identification**

```text id="h9j5pk"
identity:

    variantId:

        "P-F10"

    familyId:

        "petroski"

    displayName:

        "Petroski F10 — Feminino"

    aliasNames:

        [

            "Petroski F10",

            "Equação F10 de Petroski"

        ]
```

Primary reference:

```text id="2n4z8a"
Petroski, E. L. (1995).

Desenvolvimento e validação de equações generalizadas para a estimativa

da densidade corporal em adultos.

Tese de Doutorado.

Universidade Federal de Santa Maria (UFSM),

Santa Maria, RS, Brasil.
```

**## 2. Mathematical Definition**

The P-F10 equation is:

```text id="x2aw8j"
D =

    1.03465850

    - 0.00063129 × Y4

    + 0.00000187 × Y4²

    - 0.00031165 × AGE

    - 0.00048890 × BODY_MASS

    + 0.00051345 × HEIGHT
```

Where:

```text id="x9g0pw"
Y4 =

    SKINFOLD_MIDAXILLARY

    + SKINFOLD_SUPRAILIAC

    + SKINFOLD_THIGH

    + SKINFOLD_MEDIAL_CALF
```

Output:

```text id="t22j7v"
outputType:

    BODY_DENSITY
```

The equation uses the sum of four skinfolds and its squared value together with age, body mass and height.

The four-skinfold combination is the `Y4` configuration of Petroski's female equations.

**## 3. Applicability**

**### 3.1 Sex**

```text id="77eu9w"
sex:

    supportedSexes:

        - FEMALE
```

**### 3.2 Age**

```text id="j8z9wl"
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

```text id="f3w2g0"
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

The total female study sample contained 281 women, divided into 213 women for regression and 68 for independent validation. The study describes the female population as adults from the central region of Rio Grande do Sul and the coastal region of Santa Catarina.

**## 5. Athlete Applicability**

```text id="6z6wk1"
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

No sufficiently explicit athlete/non-athlete classification compatible with the platform's boolean model is assigned to the P-F10 development or validation samples.

**## 6. Training Level Applicability**

```text id="m7qp22"
trainingLevel:

    supportedLevels:

        []

    notes:

        "Não documentado segundo a escala operacional da plataforma."
```

**## 7. Modality Applicability**

```text id="g6oz8k"
modality:

    supportedModalities:

        []

    notes:

        "Não documentado em termos de modalidades esportivas específicas."
```

**## 8. Body Characteristics**

```text id="pt4m6z"
bodyCharacteristics:

    rules:

        []
```

No explicit P-F10 body-characteristic eligibility rule is documented.

The observed study ranges describe the development population and are not converted automatically into hard runtime restrictions.

**## 9. Validation Evidence**

**### 9.1 Development Evidence**

```text id="6a1mre"
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

            0.864

        R2:

            0.746

        standardError:

            0.0064
```

P-F10 presented `R = 0.864`, `R² = 0.746` and `EPE = 0.0064 g/ml` in the regression sample.

Petroski identifies P-F10 as the female equation with the highest multiple correlation among the 16 generalized female equations. The thesis also notes that the quadratic models presented higher `R` values than the logarithmic models.

**## 10. Validation**

The independent validation sample was:

```text id="4akf3e"
n:

    68 women

age:

    18–43 years
```

Criterion method:

```text id="3w4j0x"
hydrostatic weighing
```

For P-F10:

```text id="m6j9wo"
correlation:

    0.782

constantError:

    0.00024 g/ml

totalError:

    0.0064 g/ml

standardErrorOfEstimate:

    0.0063 g/ml
```

The validation table reports a mean estimated density of `1.046628 ± 0.0093 g/ml`, `r = 0.782`, `t = 0.310`, `EC = 0.00024 g/ml`, `ET = 0.0064 g/ml` and `EPE = 0.0063 g/ml`.

The reported constant error is small and positive, indicating a small mean difference between measured and estimated body density in the independent validation sample.

The reported `ET` and `EPE` are among the lowest values in the female validation table, with P-F10 showing the lowest EPE reported there at `0.0063 g/ml`.

**## 11. Cross-validation**

```text id="x4b9s2"
crossValidationStudies:

    []
```

The independent 68-woman sample is represented under `Validation` rather than duplicated under `Cross-validation`.

Petroski's separate cross-validation analysis evaluates equations from other investigators and does not constitute an additional P-F10-specific cross-validation study.

**## 12. External Validation**

```text id="f0f4do"
externalValidationStudies:

    []
```

No external validation study of P-F10 outside the original Petroski investigation is included in this profile.

**## 13. Measurement Requirements**

```text id="29l6ty"
inputs:

    requiredInputs:

        - AGE

        - BODY_MASS

        - HEIGHT

        - SKINFOLD_MIDAXILLARY

        - SKINFOLD_SUPRAILIAC

        - SKINFOLD_THIGH

        - SKINFOLD_MEDIAL_CALF

    optionalInputs:

        []
```

Mathematical inputs:

```text id="7t2q6y"
AGE

BODY_MASS

HEIGHT

Y4
```

where:

```text id="u7kzrq"
Y4 =

    SKINFOLD_MIDAXILLARY

    + SKINFOLD_SUPRAILIAC

    + SKINFOLD_THIGH

    + SKINFOLD_MEDIAL_CALF
```

and:

```text id="a1d2ne"
Y4²
```

is used in the mathematical equation.

`SEX` is not included in `requiredInputs`; it determines applicability to the female variant.

According to the thesis notation, age is expressed in years, body mass in kilograms, height in centimeters and skinfolds in millimeters. The source defines `Y4` as the sum of the midaxillary, suprailiac, thigh and medial calf skinfolds.

General definitions of units, precision and plausible ranges remain in:

```text id="w0w7k1"
/library/measurements
```

**## 14. Scientific Restrictions**

```text id="j3v9xq"
restrictions:

    []
```

No additional explicit scientific restriction producing `INELIGIBLE` was identified.

The development age range of 18–51 years is treated as population evidence rather than an automatically generated runtime restriction.

The requirements for body mass, height and the four component skinfolds are mathematical input requirements rather than independent eligibility restrictions.

**## 15. Source Conflict**

```text id="3q7w6u"
evidence:

    sourceConflict:

        null
```

No material unresolved conflict was identified for the P-F10 mathematical definition or the reported development and independent validation statistics.

The mathematical definition and reported regression coefficients are directly presented in the female generalized-equation table of the Petroski thesis.

**## 16. Lifecycle**

```text id="3s9lwi"
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

```text id="u6u6ck"
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

```text id="t1p0ea"
EquationVariantScientificProfile

    identity:

        variantId:

            "P-F10"

        familyId:

            "petroski"

        displayName:

            "Petroski F10 — Feminino"

        aliasNames:

            [

                "Petroski F10",

                "Equação F10 de Petroski"

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

                R: 0.864

                R2: 0.746

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

                    0.782

                standardError:

                    0.0063

                meanDifference:

                    0.00024

                rmse:

                    null

                otherMetrics:

                    "EC = 0.00024 g/ml; ET = 0.0064 g/ml;

                     EPE = 0.0063 g/ml."

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

```text id="c0n8t2"
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

    + 4 skinfolds

output:

    BODY_DENSITY
```

The engine must not transform the mean development age into an eligibility limit, and absence of athlete, training-level or modality evidence must not become `INELIGIBLE`.

P-F10 is the **four-skinfold `Y4` quadratic Petroski female variant**, using:

```text id="jr2k68"
Y4 =

    midaxillary

    + suprailiac

    + thigh

    + medial calf
```

In addition to `Y4`, the equation requires `AGE`, `BODY_MASS` and `HEIGHT`.

Therefore:

```text id="c53hzk"
P-F9:

    AGE + BODY_MASS + HEIGHT + X4

P-F10:

    AGE + BODY_MASS + HEIGHT + Y4
```

P-F9 and P-F10 share the same quadratic mathematical structure and the same `AGE + BODY_MASS + HEIGHT` predictors, but use different four-skinfold aggregates.

P-F9 uses:

```text id="y2w4mf"
subscapular
triceps
suprailiac
medial calf
```

while P-F10 uses:

```text id="h73i4q"
midaxillary
suprailiac
thigh
medial calf
```

The `SuggestionEngine` must keep these variants mathematically and operationally distinct.

P-F10 has the highest reported development `R` among the 16 Petroski female equations (`R = 0.864`) and the highest reported validation correlation among them (`r = 0.782`). These evidence metrics may contribute to ranking, but they must not by themselves override applicability or input-readiness rules.

**## 19. Reference**

```text id="q7a1sv"
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

```text id="4z0j3p"
"Centro Esportivo Virtual — Desenvolvimento e Validação de Equações

Generalizadas Para a Estimativa da Densidade Corporal em Adultos."

url:

    "[[https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/](https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/)](https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/)"
```