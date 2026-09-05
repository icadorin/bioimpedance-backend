# EquationVariantScientificProfile — P-M4

## 1. Identification

```text
identity:

    variantId:
        "P-M4"

    familyId:
        "petroski"

    displayName:
        "Petroski M4 — Masculino"

    aliasNames:
        []
```

Reference basis:

```text
Petroski, Edio Luiz (1995).
Desenvolvimento e validação de equações generalizadas para a estimativa da
 densidade corporal em adultos.
Tese de Doutorado, Universidade Federal de Santa Maria, Santa Maria, RS, Brasil.
```

---

## 2. Mathematical Definition

```text
D =
    1.08566598
    - 0.00032750 × X7
    + 0.00000036 × X7²
    - 0.00017521 × AGE
    + 0.00161816 × CIRCUMFERENCE_FOREARM
    - 0.00041043 × CIRCUMFERENCE_ABDOMEN
```

Where:

```text
X7 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_PECTORAL
    + SKINFOLD_AXILLARY_MID
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_ABDOMEN
    + SKINFOLD_THIGH
```

Output:

```text
outputType = BODY_DENSITY
```

The equation is the M4 generalized model from Petroski's Table 9. It uses the
sum and square of seven skinfolds, age, forearm circumference and abdominal
circumference.

---

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

The development sample comprised 304 men aged 18–66 years, with mean age
30.17 ± 9.78 years. The validation sample comprised 87 men aged 18–56 years,
with mean age 30.68 ± 9.11 years.

The development mean age is descriptive and is not an eligibility limit.

---

## 4. Population Applicability

```text
population:

    originalPopulation:

        description:
            "Homens adultos das regiões central do Rio Grande do Sul e
             litorânea de Santa Catarina"

        country:
            "Brazil"

        region:
            "Região central do RS e região litorânea de SC"

        sexCoverage:
            - MALE

        ageCoverage:
            min: 18
            max: 66

        sampleSize:
            304

        bodyCharacteristicsNotes:
            "A amostra de desenvolvimento apresentou heterogeneidade em idade
             e gordura corporal. Percentual de gordura: 2.20%–33.16%; média
             16.14 ± 6.86%."

        sampleCharacteristics:
            "Amostra de homens adultos utilizada para desenvolvimento das
             equações generalizadas de Petroski."

        source:
            Petroski (1995)

    validationPopulations:

        - description:
            "Homens adultos da amostra independente de validação"

          country:
            "Brazil"

          region:
            "Região central do RS e região litorânea de SC"

          sexCoverage:
            - MALE

          ageCoverage:
            min: 18
            max: 56

          sampleSize:
            87

          bodyCharacteristicsNotes:
            "Percentual de gordura: 3.11%–33.12%; média 15.83 ± 6.72%."

          sampleCharacteristics:
            "Amostra de calibração independente oriunda da mesma população."

          source:
            Petroski (1995)
```

---

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

The study describes heterogeneity in physical activity habits, but the available
source does not provide a sufficiently precise mapping to the platform's
binary athlete fields. No athlete-specific restriction is assigned.

---

## 6. Training Level Applicability

```text
trainingLevel:

    supportedLevels:
        []

    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

---

## 7. Modality Applicability

```text
modality:

    supportedModalities:
        []

    notes:
        "Não documentado em termos de modalidades esportivas específicas."
```

---

## 8. Body Characteristics

```text
bodyCharacteristics:

    rules:
        []
```

The observed body-composition range is retained as a population description,
not converted into explicit scientific restrictions.

---

## 9. Validation Evidence

### 9.1 Development Evidence

```text
development:

    studyReference:

        citation:
            "Petroski, E. L. (1995). Desenvolvimento e validação de equações
             generalizadas para a estimativa da densidade corporal em adultos.
             Tese de Doutorado, Universidade Federal de Santa Maria."

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
```

### 9.2 Development metrics

```text
metrics:

    multipleCorrelation_R:
        0.892

    coefficientOfDetermination_R2:
        0.795

    standardError:
        0.0071
```

Petroski reports these values for M4 in Table 9.

---

## 10. Validation

```text
validationStudies:

    - studyReference:

        citation:
            "Petroski, E. L. (1995). Desenvolvimento e validação de equações
             generalizadas para a estimativa da densidade corporal em adultos.
             Tese de Doutorado, Universidade Federal de Santa Maria."

        doi:
            null

        url:
            "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"

      population:

        description:
            "Homens adultos da amostra independente de validação"

        country:
            "Brazil"

        region:
            "Região central do RS e região litorânea de SC"

        sexCoverage:
            - MALE

        ageCoverage:
            min: 18
            max: 56

        sampleSize:
            87

        bodyCharacteristicsNotes:
            "Percentual de gordura: 3.11%–33.12%; média 15.83 ± 6.72%."

        sampleCharacteristics:
            "Amostra de validação independente oriunda da mesma população."

        source:
            Petroski (1995)

      criterionMethod:
          "Pesagem hidrostática"

      metrics:

          correlation:
              0.873

          standardError:
              0.0074

          meanDifference:
              -0.0003

          rmse:
              null

          otherMetrics:
              "Densidade média estimada = 1.06251 g/ml; densidade média medida
               = 1.06282 g/ml; t = -0.386; ET = 0.0074 g/ml."

      limitations:
          "Validação feita em amostra independente oriunda da mesma população,
           e não em população externa."
```

Petroski reports no statistically significant difference between measured and
estimated mean density for the developed male equations (p > 0.05).

---

## 11. Cross-validation

```text
crossValidationStudies:
    []
```

The primary validation table for M4 is classified here as validation rather than
cross-validation because the study explicitly presents it as validation on an
independent calibration sample. No separate M4 cross-validation result is
assigned without evidence that distinguishes it from this validation sample.

---

## 12. External Validation

```text
externalValidationStudies:
    []
```

No independent external-validation study was incorporated into this profile.

---

## 13. Measurement Requirements

```text
inputs:

    requiredInputs:

        - AGE
        - SKINFOLD_SUBSCAPULAR
        - SKINFOLD_TRICEPS
        - SKINFOLD_PECTORAL
        - SKINFOLD_AXILLARY_MID
        - SKINFOLD_SUPRAILIAC
        - SKINFOLD_ABDOMEN
        - SKINFOLD_THIGH
        - CIRCUMFERENCE_FOREARM
        - CIRCUMFERENCE_ABDOMEN

    optionalInputs:
        []
```

`SEX` is not a mathematical input. It participates in applicability because this
variant is specifically defined for men.

The distinction is:

```text
SEX
    → Applicability

AGE
    → Formula input

7 skinfolds
    → Formula inputs

FOREARM CIRCUMFERENCE
    → Formula input

ABDOMEN CIRCUMFERENCE
    → Formula input
```

The Petroski thesis defines:

```text
X7 = Σ7DC
```

with the seven skinfolds:

```text
subscapular
triceps
pectoral
midaxillary
suprailiac
abdomen
thigh
```

and uses forearm and abdominal circumferences as additional predictors in M4.

---

## 14. Scientific Restrictions

```text
restrictions:
    []
```

No explicit scientific restriction beyond the documented population and sex
applicability is assigned to M4.

The population age range is not converted automatically into an explicit
prohibition outside the observed sample.

---

## 15. Source Conflict

```text
evidence:

    sourceConflict:
        null
```

No material conflict was identified for the M4 mathematical definition,
development metrics, or validation metrics in the source used for this profile.

---

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

The profile contains the essential information required by the schema:

```text
identity                    ✓
mathematicalDefinition      ✓
supportedSexes              ✓
originalDevelopmentAgeRange ✓
originalPopulation          ✓
requiredInputs              ✓
reference                   ✓
sourceConflict              null
```

---

## 17. Ficha consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:
            "P-M4"

        familyId:
            "petroski"

        displayName:
            "Petroski M4 — Masculino"

        aliasNames:
            []

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
                description:
                    "Homens adultos das regiões central do RS e litorânea de SC"
                country:
                    "Brazil"
                region:
                    "Região central do RS e região litorânea de SC"
                sexCoverage:
                    - MALE
                ageCoverage:
                    min: 18
                    max: 66
                sampleSize:
                    304
                bodyCharacteristicsNotes:
                    "Percentual de gordura de 2.20%–33.16%, média 16.14%."
                sampleCharacteristics:
                    "Amostra heterogênea em idade e composição corporal."
                source:
                    Petroski (1995)
            validationPopulations:
                - description:
                    "Homens adultos da amostra independente de validação"
                  country:
                    "Brazil"
                  region:
                    "Região central do RS e região litorânea de SC"
                  sexCoverage:
                    - MALE
                  ageCoverage:
                    min: 18
                    max: 56
                  sampleSize:
                    87
                  bodyCharacteristicsNotes:
                    "Percentual de gordura de 3.11%–33.12%, média 15.83%."
                  sampleCharacteristics:
                    "Amostra de validação independente oriunda da mesma população."
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
                "Petroski (1995)"
            population:
                "Homens adultos, n = 304, 18–66 anos"
            criterionMethod:
                "Pesagem hidrostática"
            year:
                1995

        validationStudies:
            - studyReference:
                "Petroski (1995)"
              population:
                "Homens adultos, n = 87, 18–56 anos"
              criterionMethod:
                "Pesagem hidrostática"
              metrics:
                correlation:
                    0.873
                standardError:
                    0.0074
                meanDifference:
                    -0.0003
                rmse:
                    null
                otherMetrics:
                    "ET = 0.0074 g/ml; t = -0.386."
              limitations:
                "Validação interna em amostra independente da mesma população."

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
            - SKINFOLD_AXILLARY_MID
            - SKINFOLD_SUPRAILIAC
            - SKINFOLD_ABDOMEN
            - SKINFOLD_THIGH
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

---

## 18. Interpretação para o SuggestionEngine

The scientific profile supports the following runtime interpretation:

```text
sex:
    MALE → documented applicability
    FEMALE → incompatible with this male-specific variant

age:
    development population = 18–66
    validation population = 18–56
    development mean = 30.17
    mean is NOT an eligibility limit

athlete:
    NOT_DOCUMENTED

trainingLevel:
    NOT_DOCUMENTED

modality:
    NOT_DOCUMENTED

required mathematical data:
    AGE
    7 skinfolds
    forearm circumference
    abdominal circumference

output:
    BODY_DENSITY
```

The engine must not infer athlete incompatibility, training-level incompatibility,
or modality incompatibility from missing documentation.

Likewise, the observed development age range must not automatically become an
explicit scientific prohibition unless such a restriction is separately
documented.

---

## 19. Reference

```text
Reference

    citation:
        "Petroski, Edio Luiz (1995). Desenvolvimento e validação de equações
         generalizadas para a estimativa da densidade corporal em adultos.
         Tese de Doutorado. Programa de Pós-Graduação em Ciência do Movimento
         Humano, Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

    doi:
        null

    url:
        "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"
```
