# EquationVariantScientificProfile — FALK4

**## 1. Identification**

```text
identity:

    variantId:

        "FALK4"

    familyId:

        "faulkner"

    displayName:

        "Faulkner 4 Skinfolds"

    aliasNames:

        [

            "Faulkner 4",

            "Faulkner 4D",

            "Equação de Faulkner de 4 dobras"

        ]

```

Primary reference:

```text
Faulkner, J. A. (1968).

Physiology of Swimming and Diving.

In: Falls, H. (Ed.).

Exercise Physiology.

```

**## 2. Mathematical Definition**

The FALK4 equation is:

```text
%G =

    5.783

    + 0.153 × SUM4
```

Where:

```text
SUM4 =

    SKINFOLD_TRICEPS

    + SKINFOLD_SUBSCAPULAR

    + SKINFOLD_SUPRAILIAC

    + SKINFOLD_ABDOMINAL
```

Output:

```text
outputType:

    BODY_FAT_PERCENTAGE
```

The equation produces body-fat percentage directly from the sum of four skinfold measurements.

Unlike the Petroski, Jackson & Pollock and Guedes density equations, FALK4 does not produce `BODY_DENSITY` and therefore does not require a subsequent Siri or Brozek conversion.

The equation is commonly described as a modification of the Yuhasz approach and is widely reproduced as:

```text
%G = 5.783 + 0.153 ×
(
    triceps
    + subscapular
    + suprailiac
    + abdominal
)
```

The source literature documents the same expression.

**## 3. Applicability**

**### 3.1 Sex**

```text
sex:

    supportedSexes:

        - MALE

    applicabilityStatus:

        PROVISIONAL

    notes:

        "A aplicabilidade sexual da forma 5,783 + 0,153 × Σ4

         apresenta inconsistência documental na literatura secundária.

         A fonte histórica e a tabela de comparação de Faulkner

         documentam explicitamente a aplicação dessa forma em homens,

         enquanto fontes posteriores reproduzem a mesma fórmula

         sem delimitação sexual consistente."

```

The historical documentation does not support treating the `5.783 + 0.153 × SUM4` expression as a universally validated female equation.

Some later sources reproduce this exact equation in female studies, while other sources explicitly distinguish a female Faulkner equation as `7.9 + 0.213 × SUM4`.

For this reason, the FALK4 profile preserves the `5.783 + 0.153 × SUM4` equation as a distinct variant and does not silently reinterpret it as the separate female coefficient set.

**### 3.2 Age**

```text
age:

    originalDevelopmentAgeRange:

        null

    developmentSampleMeanAge:

        null

    validatedAgeRanges:

        []

    explicitAgeRestriction:

        null

```

No explicit age eligibility range for the mathematical equation itself was established from the primary historical documentation.

The Faulkner source includes comparative examples involving adult university men and university swimmers, but these observations do not establish a formal runtime age boundary for the equation.

**## 4. Population Applicability**

```text
population:

    originalPopulation:

        description:

            "Amostras utilizadas como referência na discussão

             de composição corporal no contexto de Faulkner."

        country:

            "United States"

        region:

            "University of Michigan / context of Faulkner's

             swimming and exercise physiology work"

        sexCoverage:

            - MALE

        ageCoverage:

            null

        sampleSize:

            null

        bodyCharacteristicsNotes:

            "A fonte apresenta exemplos de homens universitários

             e nadadores universitários; não estabelece uma faixa

             normativa universal para uso do modelo."

        sampleCharacteristics:

            "A equação aparece no contexto histórico de estimativas

             de gordura corporal por quatro dobras cutâneas."

        source:

            Faulkner (1968)

    validationPopulations:

        []

```

The historical source presents comparative data including university men and university swimmers. In the material reproduced from Faulkner, the four-skinfold sum is associated with the equation `%G = 5.783 + 0.153 × S4DC`. The source does not provide a modern external-validation framework equivalent to the regression/validation structure used for Petroski.

The original source also includes female and female-swimmer comparative samples, but those entries do not by themselves establish that the `5.783 + 0.153 × SUM4` coefficients were developed or validated specifically for women.

**## 5. Athlete Applicability**

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

Faulkner's 1968 chapter contains data involving university swimmers, and the equation has subsequently been heavily used in athletic contexts. However, the historical documentation does not justify encoding FALK4 as an athlete-only equation.

A later correspondence attributed to John Faulkner states that the equation was a general equation and was not specifically designed for swimmers.

Therefore the platform should not convert the historical swimming context into an athlete eligibility rule.

**## 6. Training Level Applicability**

```text
trainingLevel:

    supportedLevels:

        []

    notes:

        "Não documentado segundo a escala operacional da plataforma."

```

The historical material mentions university populations and swimmers but does not establish a standardized training-level applicability rule compatible with the platform.

**## 7. Modality Applicability**

```text
modality:

    supportedModalities:

        []

    notes:

        "O contexto histórico inclui natação, mas a equação

         não deve ser tratada automaticamente como específica

         de uma modalidade esportiva."

```

The association between Faulkner and swimming is historically strong, but the available documentation indicates that the equation itself was intended as a general equation rather than a swimmer-only equation.

**## 8. Body Characteristics**

```text
bodyCharacteristics:

    rules:

        []

```

No explicit body-characteristic eligibility rule is documented for FALK4.

The observed populations in the historical source should not automatically be converted into hard runtime thresholds.

**## 9. Validation Evidence**

**### 9.1 Development Evidence**

```text
development:

    studyReference:

        citation:

            "Faulkner, J. A. (1968).

             Physiology of Swimming and Diving.

             In: Falls, H. (Ed.).

             Exercise Physiology."

        doi:

            null

        url:

            null

    population:

        "Historical comparative samples involving university

         men and swimmers"

    criterionMethod:

        "Não documentado de forma suficiente para representar

         uma validação moderna da equação."

    year:

        1968

    metrics:

        R:

            null

        R2:

            null

        standardError:

            null

```

No regression `R`, `R²` or standard error equivalent to the Petroski generalized equations was identified for FALK4 in the historical source.

The equation is documented as a direct anthropometric body-fat estimation formula, but the available historical material does not provide a standardized development-statistics block suitable for populating these fields.

**## 10. Validation**

No independent validation sample equivalent to the Petroski 68-woman validation sample was identified for the original FALK4 documentation.

```text
validationStudies:

    []

```

The available evidence primarily consists of the historical equation and subsequent applications.

Later studies that simply apply FALK4 to an athletic or non-athletic sample must not automatically be classified as validation studies of the equation.

**## 11. Cross-validation**

```text
crossValidationStudies:

    []

```

No dedicated cross-validation study of the FALK4 equation was identified in the source material used for this profile.

**## 12. External Validation**

```text
externalValidationStudies:

    []

```

No sufficiently explicit external-validation study of the exact FALK4 coefficient set was identified for inclusion in this profile.

The existence of later applications in swimmers, football players and other athlete groups is evidence of usage, not by itself evidence of external validation.

**## 13. Measurement Requirements**

```text
inputs:

    requiredInputs:

        - SKINFOLD_TRICEPS

        - SKINFOLD_SUBSCAPULAR

        - SKINFOLD_SUPRAILIAC

        - SKINFOLD_ABDOMINAL

    optionalInputs:

        []

```

Mathematical inputs:

```text
SUM4
```

where:

```text
SUM4 =

    SKINFOLD_TRICEPS

    + SKINFOLD_SUBSCAPULAR

    + SKINFOLD_SUPRAILIAC

    + SKINFOLD_ABDOMINAL

```

`AGE` is not a mathematical input.

`SEX` is not a mathematical input; it is an applicability/context field for the variant.

According to the documented equation, the four skinfolds are expressed in millimeters.

General definitions of units, precision and plausible ranges remain in:

```text
/library/measurements
```

**## 14. Scientific Restrictions**

```text
restrictions:

    []

```

No additional explicit scientific restriction producing `INELIGIBLE` was identified.

FALK4 should not be assigned a hard age, athlete, training-level or sport-modality restriction in the absence of explicit supporting evidence.

The `SUM4` aggregate must be valid before calculating body-fat percentage.

**## 15. Source Conflict**

```text
evidence:

    sourceConflict:

        "A literatura apresenta conflito quanto à aplicabilidade

         sexual da forma 5,783 + 0,153 × Σ4.

         Algumas fontes reproduzem essa forma sem distinção sexual,

         enquanto outras apresentam uma forma feminina diferente:

         %G = 7,9 + 0,213 × Σ4.

         A documentação histórica também relaciona a fórmula

         a contextos de homens universitários e nadadores,

         sem estabelecer claramente uma validação feminina

         equivalente."

```

This conflict is material and should remain visible to the scientific library.

The platform must not merge:

```text
5.783 + 0.153 × SUM4
```

with:

```text
7.9 + 0.213 × SUM4
```

as though they were the same mathematical variant.

Secondary literature explicitly documents both coefficient sets.

**## 16. Lifecycle**

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

supportedSexes                PROVISIONAL

originalDevelopmentAgeRange   NOT_DOCUMENTED

originalPopulation            PARTIALLY_DOCUMENTED

requiredInputs                CONFIRMED

definitionReference           CONFIRMED

sourceConflict                DOCUMENTED
```

**## 17. Ficha Consolidada**

```text
EquationVariantScientificProfile

    identity:

        variantId:

            "FALK4"

        familyId:

            "faulkner"

        displayName:

            "Faulkner 4 Skinfolds"

        aliasNames:

            [

                "Faulkner 4",

                "Faulkner 4D",

                "Equação de Faulkner de 4 dobras"

            ]

    applicability:

        sex:

            supportedSexes:

                - MALE

            applicabilityStatus:

                PROVISIONAL

            notes:

                "Aplicabilidade sexual da forma 5,783 + 0,153 × Σ4

                 permanece provisória devido ao conflito documental

                 encontrado na literatura."

        age:

            originalDevelopmentAgeRange:

                null

            developmentSampleMeanAge:

                null

            validatedAgeRanges:

                []

            explicitAgeRestriction:

                null

        population:

            originalPopulation:

                description:

                    "Amostras históricas envolvendo homens universitários

                     e nadadores universitários."

                country:

                    "United States"

                region:

                    "University of Michigan / contexto de Faulkner"

                sexCoverage:

                    - MALE

                ageCoverage:

                    null

                sampleSize:

                    null

                bodyCharacteristicsNotes:

                    "O material histórico apresenta grupos comparativos,

                     mas não estabelece uma faixa universal de elegibilidade."

                sampleCharacteristics:

                    "Equação antropométrica de quatro dobras para

                     estimativa de percentual de gordura."

                source:

                    Faulkner (1968)

            validationPopulations:

                []

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

                "Não documentado como restrição de modalidade."

        bodyCharacteristics:

            rules:

                []

    evidence:

        development:

            studyReference:

                Faulkner (1968)

            population:

                "Amostras históricas de homens universitários

                 e nadadores universitários"

            criterionMethod:

                "Não documentado suficientemente para uma

                 ficha moderna de validação estatística."

            year:

                1968

            metrics:

                R: null

                R2: null

                standardError: null

        validationStudies:

            []

        crossValidationStudies:

            []

        externalValidationStudies:

            []

        sourceConflict:

            "Conflito documental sobre aplicabilidade sexual

             e interpretação histórica da equação."

    inputs:

        requiredInputs:

            - SKINFOLD_TRICEPS

            - SKINFOLD_SUBSCAPULAR

            - SKINFOLD_SUPRAILIAC

            - SKINFOLD_ABDOMINAL

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

```text
sex:

    MALE → provisional compatibility

    FEMALE → requires caution because of documented source conflict

age:

    no explicit age restriction

athlete:

    NOT_DOCUMENTED

trainingLevel:

    NOT_DOCUMENTED

modality:

    NOT_DOCUMENTED

bodyCharacteristics:

    no explicit rule

inputs:

    4 skinfolds

output:

    BODY_FAT_PERCENTAGE
```

The engine must recognize that FALK4 is structurally different from the body-density equations:

```text
4 skinfolds
    ↓
FALK4
    ↓
BODY_FAT_PERCENTAGE
```

There is no:

```text
BODY_DENSITY
    ↓
Siri
```

conversion step in the FALK4 calculation itself.

This is consistent with the platform architecture, which treats `BODY_DENSITY` and `BODY_FAT_PERCENTAGE` as separate prediction outputs.

The engine must also preserve the distinction between the exact FALK4 expression:

```text
%G = 5.783 + 0.153 × SUM4
```

and the separately documented female coefficient set:

```text
%G = 7.9 + 0.213 × SUM4
```

They must not be merged under one mathematical identity.

Because the original documentation does not establish a robust modern applicability matrix, FALK4 should not receive strong automatic preference solely from the historical association with swimming.

Its strongest runtime characteristics are:

```text
directOutput:

    BODY_FAT_PERCENTAGE

inputCount:

    4 skinfolds

additionalInputs:

    none

validationEvidence:

    LIMITED / NOT_STANDARDIZED

sourceConflict:

    DOCUMENTED
```

The suggestion engine may therefore use FALK4 when explicitly enabled by the professional or when the product's scientific policy permits provisional formulas, but the unresolved applicability conflict should remain visible in the explanation.

**## 19. Reference**

```text
Reference

    citation:

        "Faulkner, J. A. (1968).

         Physiology of Swimming and Diving.

         In: Falls, H. (Ed.).

         Exercise Physiology."

    doi:

        null

    url:

        null
```

Additional bibliographic evidence:

```text
"Artigo de revisão sobre a origem matemática da equação

de Faulkner e a documentação histórica de sua fórmula."

url:

    "https://periodicos.ufsc.br/index.php/rbcdh/article/download/4065/16716/55386"
```

Additional application sources document the exact FALK4 expression:

```text
%G = 5.783 + 0.153 ×
(
    triceps
    + subscapular
    + suprailiac
    + abdominal
)
```

including applications in athletic and clinical/population contexts.