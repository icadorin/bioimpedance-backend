# EquationVariantScientificProfile — P-M5

## 1. Identification

```text
VariantIdentity

variantId: "P-M5"
familyId: "petroski"
displayName: "Petroski M5 — Masculino"
aliasNames: []
```

## 2. Mathematical Definition

```text
D =
    1.09995680
    - 0.00055475 × X6
    + 0.00000107 × X6²
    - 0.00023367 × AGE
```

Where:

```text
X6 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_BICEPS
    + SKINFOLD_PECTORAL
    + SKINFOLD_AXILLARY_MID
    + SKINFOLD_SUPRAILIAC
```

Output:

```text
BODY_DENSITY
```

## 3. Applicability

### 3.1 Sex

```text
supportedSexes:
    - MALE
```

### 3.2 Age

```text
originalDevelopmentAgeRange:
    min: 18
    max: 66

developmentSampleMeanAge:
    30.17

validatedAgeRanges:
    - range:
        min: 18
        max: 56
      population: "Homens adultos da amostra independente de validação"
      source: Petroski (1995)

explicitAgeRestriction:
    null
```

A média de idade é descritiva e não é usada como limite de elegibilidade.

## 4. Population Applicability

```text
originalPopulation:
    description: "Homens adultos"
    country: "Brazil"
    region: "Região central do Rio Grande do Sul e litoral de Santa Catarina"
    sexCoverage:
        - MALE
    ageCoverage:
        min: 18
        max: 66
    sampleSize: 304
    bodyCharacteristicsNotes: "A amostra apresentou heterogeneidade de idade e percentual de gordura, aproximadamente 2,20–33,16%."
    sampleCharacteristics: "Homens avaliados no estudo de desenvolvimento de equações generalizadas de Petroski."
    source: Petroski (1995)

validationPopulations:
    - description: "Homens adultos da amostra independente de validação"
      country: "Brazil"
      region: "População independente oriunda da mesma população de estudo"
      sexCoverage:
          - MALE
      ageCoverage:
          min: 18
          max: 56
      sampleSize: 87
      bodyCharacteristicsNotes: "Amostra independente utilizada para validação das equações generalizadas."
      sampleCharacteristics: "Homens adultos avaliados pelo mesmo estudo de validação."
      source: Petroski (1995)
```

## 5. Athlete Applicability

```text
developedInAthletes: null
validatedInAthletes: null
developedInNonAthletes: null
validatedInNonAthletes: null
explicitAthleteRestriction: null
```

A população foi descrita como heterogênea quanto a hábitos de exercício, mas essa descrição não permite o mapeamento seguro para os booleanos operacionais de atleta/não atleta.

## 6. Training Level Applicability

```text
supportedLevels: []
notes: "Não documentado segundo a escala operacional da plataforma."
```

## 7. Modality Applicability

```text
supportedModalities: []
notes: "Não documentado em termos de modalidades esportivas específicas."
```

## 8. Body Characteristics

```text
rules: []
```

A faixa de gordura observada na amostra é descritiva e não foi convertida em regra de aplicabilidade.

## 9. Validation Evidence

### 9.1 Development Evidence

```text
studyReference:
    citation: "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado, Universidade Federal de Santa Maria."
    doi: null
    url: "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"

population: "Homens adultos, n = 304, 18–66 anos"
criterionMethod: "Pesagem hidrostática / determinação da densidade corporal"
year: 1995
```

## 10. Validation

```text
validationStudies: []
```

Para a P-M5, a validação independente da equação é registrada separadamente em `crossValidationStudies`, conforme a classificação adotada para a tese de Petroski.

## 11. Cross-validation

```text
crossValidationStudies:
    - studyReference:
        citation: "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado, Universidade Federal de Santa Maria."
        doi: null
        url: "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"

      population:
        description: "Homens adultos da amostra independente de validação"
        country: "Brazil"
        region: "Amostra independente do estudo de Petroski"
        sexCoverage:
            - MALE
        ageCoverage:
            min: 18
            max: 56
        sampleSize: 87
        bodyCharacteristicsNotes: null
        sampleCharacteristics: "Amostra independente utilizada na validação das equações generalizadas."
        source: Petroski (1995)

      criterionMethod: "Pesagem hidrostática / determinação da densidade corporal"

      metrics:
          correlation: 0.853
          standardError: 0.0079
          meanDifference: -0.0005
          rmse: null
          otherMetrics: "EC = -0.0005 g/ml; ET = 0.0079 g/ml; EPE = 0.0076 g/ml."

      limitations: null
```

A tabela de validação do estudo reporta, para a M5, `r = 0.853`, `EC = -0.0005 g/ml`, `ET = 0.0079 g/ml` e `EPE = 0.0076 g/ml`.

## 12. External Validation

```text
externalValidationStudies: []
```

## 13. Measurement Requirements

```text
requiredInputs:
    - AGE
    - SKINFOLD_SUBSCAPULAR
    - SKINFOLD_TRICEPS
    - SKINFOLD_BICEPS
    - SKINFOLD_PECTORAL
    - SKINFOLD_AXILLARY_MID
    - SKINFOLD_SUPRAILIAC

optionalInputs: []
```

`SEX` permanece somente em `applicability` e não é variável matemática da equação.

## 14. Scientific Restrictions

```text
restrictions: []
```

## 15. Source Conflict

```text
sourceConflict: null
```

Não foi identificado conflito material na definição matemática da M5 nas fontes consultadas.

## 16. Lifecycle

```text
status: ACTIVE
version: "1"
supersedes: null
supersededBy: null
effectiveFrom: null
changeLog: []
```

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile

identity:
    variantId: "P-M5"
    familyId: "petroski"
    displayName: "Petroski M5 — Masculino"
    aliasNames: []

applicability:
    sex:
        supportedSexes:
            - MALE
    age:
        originalDevelopmentAgeRange:
            min: 18
            max: 66
        developmentSampleMeanAge: 30.17
        validatedAgeRanges:
            - range:
                min: 18
                max: 56
              population: "Homens adultos da amostra independente de validação"
              source: Petroski (1995)
        explicitAgeRestriction: null
    population:
        originalPopulation:
            description: "Homens adultos"
            country: "Brazil"
            region: "Região central do Rio Grande do Sul e litoral de Santa Catarina"
            sexCoverage:
                - MALE
            ageCoverage:
                min: 18
                max: 66
            sampleSize: 304
            bodyCharacteristicsNotes: "Heterogeneidade em idade e percentual de gordura."
            sampleCharacteristics: "Amostra de desenvolvimento de equações generalizadas."
            source: Petroski (1995)
        validationPopulations:
            - description: "Homens adultos da amostra independente de validação"
              country: "Brazil"
              region: "Amostra independente do estudo"
              sexCoverage:
                  - MALE
              ageCoverage:
                  min: 18
                  max: 56
              sampleSize: 87
              bodyCharacteristicsNotes: null
              sampleCharacteristics: "Amostra independente utilizada para validação."
              source: Petroski (1995)
    athlete:
        developedInAthletes: null
        validatedInAthletes: null
        developedInNonAthletes: null
        validatedInNonAthletes: null
        explicitAthleteRestriction: null
    trainingLevel:
        supportedLevels: []
        notes: "Não documentado segundo a escala operacional."
    modality:
        supportedModalities: []
        notes: "Não documentado."
    bodyCharacteristics:
        rules: []

evidence:
    development:
        studyReference: Petroski (1995)
        population: "Homens adultos, n = 304, 18–66 anos"
        criterionMethod: "Pesagem hidrostática / determinação da densidade corporal"
        year: 1995
    validationStudies: []
    crossValidationStudies:
        - studyReference: Petroski (1995)
          population: "Homens adultos, n = 87, 18–56 anos"
          criterionMethod: "Pesagem hidrostática / determinação da densidade corporal"
          metrics:
              correlation: 0.853
              standardError: 0.0079
              meanDifference: -0.0005
              rmse: null
              otherMetrics: "EC = -0.0005 g/ml; ET = 0.0079 g/ml; EPE = 0.0076 g/ml."
          limitations: null
    externalValidationStudies: []
    sourceConflict: null

inputs:
    requiredInputs:
        - AGE
        - SKINFOLD_SUBSCAPULAR
        - SKINFOLD_TRICEPS
        - SKINFOLD_BICEPS
        - SKINFOLD_PECTORAL
        - SKINFOLD_AXILLARY_MID
        - SKINFOLD_SUPRAILIAC
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

## 18. Interpretação para o SuggestionEngine

```text
sex:
    MALE → compatível
    FEMALE → incompatível

age:
    desenvolvimento documentado = 18–66
    média = 30.17
    média não é limite de elegibilidade

athlete:
    NOT_DOCUMENTED

trainingLevel:
    NOT_DOCUMENTED

modality:
    NOT_DOCUMENTED

inputs:
    AGE + 6 skinfolds

output:
    BODY_DENSITY
```

A ausência de documentação específica para atleta, nível de treinamento ou modalidade não deve ser transformada automaticamente em `INELIGIBLE`.

## 19. Reference

```text
Reference

citation:
    "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado. Universidade Federal de Santa Maria."

doi:
    null

url:
    "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"
```
