# EquationVariantScientificProfile — P-M3

## 1. Identification

```text
identity:

    variantId:
        "P-M3"

    familyId:
        "petroski"

    displayName:
        "Petroski M3 — 7 Dobras, Masculino"

    aliasNames:
        [
            "Petroski M3",
            "Petroski 7 Dobras — Masculino"
        ]
```

Reference principal:

```text
Petroski, Edio Luiz (1995).
Desenvolvimento e validação de equações generalizadas para a estimativa
da densidade corporal em adultos.
Tese de Doutorado. Universidade Federal de Santa Maria.
Santa Maria, RS, Brasil.
```

## 2. Mathematical Definition

```text
D =
    1.10038145
    - 0.00035804 × X7
    + 0.00000036 × X7²
    - 0.00025154 × AGE
```

Onde:

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

```text
outputType = BODY_DENSITY
```

A variante corresponde à equação **M3** da Tabela 9 da tese de Petroski. A fonte original classifica M3 como modelo quadrático com soma de sete dobras cutâneas e idade.

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

A amostra de regressão masculina continha 304 homens, de 18–66 anos, com média de 30,17 anos. A amostra independente de validação continha 87 homens, de 18–56 anos, com média de 30,68 anos.

A média de idade é descritiva e não constitui limite de elegibilidade.

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
            304

        bodyCharacteristicsNotes:
            "Amostra heterogênea em idade e gordura corporal; percentual de gordura
             de 2,20% a 33,16%, média de 16,14%."

        sampleCharacteristics:
            "Amostra de regressão do estudo de Petroski, obtida a partir de adultos
             das regiões central do Rio Grande do Sul e litorânea de Santa Catarina."

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
            "Densidade corporal média de 1,062822 g/ml; percentual de gordura médio de 15,83%."

          sampleCharacteristics:
            "Amostra de validação selecionada da mesma população e que não participou do desenvolvimento dos modelos."

          source:
            Petroski (1995)
```

O estudo informa que a população abrangia adultos das regiões central do Rio Grande do Sul e litorânea de Santa Catarina. A amostra total foi dividida por sexo em grupos de regressão e validação; para homens, foram 304 na regressão e 87 na validação.

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

A fonte descreve características gerais da população e não fornece classificação compatível com o booleano operacional da plataforma para atleta/não atleta. Não inferir essa classificação.

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
        "Não documentado."
```

## 8. Body Characteristics

```text
bodyCharacteristics:

    rules:
        []
```

A faixa de gordura corporal observada na amostra é descritiva e não será transformada em regra de aplicabilidade ou restrição.

## 9. Validation Evidence

### 9.1 Development

```text
development:

    studyReference:

        citation:
            "Petroski, Edio Luiz (1995). Desenvolvimento e validação de equações
             generalizadas para a estimativa da densidade corporal em adultos.
             Tese de Doutorado, Universidade Federal de Santa Maria."

        doi:
            null

    population:
        "Homens adultos, n = 304, 18–66 anos"

    criterionMethod:
        "Densidade corporal determinada por pesagem hidrostática"

    year:
        1995
```

O estudo utilizou a densidade determinada pela pesagem hidrostática como variável dependente/critério no desenvolvimento das equações.

### 9.2 Validation

Para a equação M3, a Tabela 10 apresenta na amostra independente de 87 homens:

```text
metrics:

    correlation:
        0.870

    standardError:
        0.0078

    meanDifference:
        -0.0003

    rmse:
        0.0075

    otherMetrics:
        "EC = -0,0003 g/ml; ET = 0,0075 g/ml; teste t não significativo
         (t = -0,397; p > 0,05)."
```

A Tabela 10 informa ainda densidade estimada média de 1,06250 g/ml para M3, contra 1,06282 g/ml de densidade mensurada.

## 10. Cross-validation

```text
crossValidationStudies:
    []
```

A tese distingue a **validação das próprias equações desenvolvidas**, realizada na amostra independente de 87 homens, da **validação cruzada de equações oriundas de outras amostras**. Para esta ficha, a validação de M3 é registrada em `validationStudies`, não em `crossValidationStudies`.

## 11. External Validation

```text
externalValidationStudies:
    []
```

Nenhum estudo independente externo foi incorporado nesta ficha.

## 12. Measurement Requirements

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

    optionalInputs:
        []
```

`SEX` permanece exclusivamente em `applicability`.

## 13. Scientific Restrictions

```text
restrictions:
    []
```

A faixa de 18–66 anos é a faixa da população de desenvolvimento e não foi convertida em uma restrição explícita além da evidência documentada.

## 14. Source Conflict

```text
evidence:

    sourceConflict:
        null
```

Não foi identificado conflito material na definição matemática da M3 entre as fontes consultadas.

## 15. Lifecycle

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

## 16. Ficha consolidada

```text
EquationVariantScientificProfile

    identity:
        variantId: "P-M3"
        familyId: "petroski"
        displayName: "Petroski M3 — 7 Dobras, Masculino"
        aliasNames:
            - "Petroski M3"
            - "Petroski 7 Dobras — Masculino"

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
            explicitAgeRestriction: null

        population:
            originalPopulation:
                description: "Homens adultos"
                country: "Brazil"
                region: "Região central do RS e litoral de SC"
                sexCoverage:
                    - MALE
                ageCoverage:
                    min: 18
                    max: 66
                sampleSize: 304
                bodyCharacteristicsNotes:
                    "%G de 2,20% a 33,16%; média de 16,14%."
                sampleCharacteristics:
                    "Amostra de regressão do estudo de Petroski."
                source: Petroski (1995)

            validationPopulations:
                - description: "Homens adultos da amostra independente de validação"
                  country: "Brazil"
                  region: "Região central do RS e litoral de SC"
                  sexCoverage:
                      - MALE
                  ageCoverage:
                      min: 18
                      max: 56
                  sampleSize: 87
                  bodyCharacteristicsNotes:
                      "%G médio de 15,83%."
                  sampleCharacteristics:
                      "Amostra independente de validação."
                  source: Petroski (1995)

        athlete:
            developedInAthletes: null
            validatedInAthletes: null
            developedInNonAthletes: null
            validatedInNonAthletes: null
            explicitAthleteRestriction: null

        trainingLevel:
            supportedLevels: []
            notes: "Não documentado."

        modality:
            supportedModalities: []
            notes: "Não documentado."

        bodyCharacteristics:
            rules: []

    evidence:

        development:
            studyReference: "Petroski (1995)"
            population: "Homens adultos, n = 304, 18–66 anos"
            criterionMethod: "Pesagem hidrostática"
            year: 1995

        validationStudies:
            - studyReference: "Petroski (1995)"
              population: "Homens adultos, n = 87, 18–56 anos"
              criterionMethod: "Pesagem hidrostática"
              metrics:
                  correlation: 0.870
                  standardError: 0.0078
                  meanDifference: -0.0003
                  rmse: 0.0075
                  otherMetrics:
                      "EC = -0,0003 g/ml; ET = 0,0075 g/ml; t = -0,397; p > 0,05."
              limitations:
                  "Validação independente oriunda da mesma população do estudo."

        crossValidationStudies: []
        externalValidationStudies: []
        sourceConflict: null

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
        optionalInputs: []

    restrictions: []

    lifecycle:
        status: ACTIVE
        version: "1"
        supersedes: null
        supersededBy: null
        effectiveFrom: null
        changeLog: []
```

## 17. Interpretação para o SuggestionEngine

```text
sex:
    MALE → compatível
    FEMALE → incompatível

age:
    development evidence = 18–66
    validation evidence = 18–56
    mean age = 30.17
    mean age is not an eligibility limit

athlete:
    NOT_DOCUMENTED

trainingLevel:
    NOT_DOCUMENTED

modality:
    NOT_DOCUMENTED

requiredInputs:
    AGE
    + 7 skinfolds

output:
    BODY_DENSITY
```

A variante é matematicamente distinta das M1 e M2: M3 usa **sete dobras + idade**, sem circunferências, e corresponde à equação M3 original da Tabela 9.

## 18. Reference

```text
Reference

    citation:
        "Petroski, Edio Luiz (1995).
         Desenvolvimento e validação de equações generalizadas para a
         estimativa da densidade corporal em adultos.
         Tese de Doutorado.
         Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

    doi:
        null

    url:
        "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"
```

Fonte digital consultada para a reprodução da tese de doutorado de Petroski, cuja ficha catalográfica identifica a obra como tese da Universidade Federal de Santa Maria, 1995.
