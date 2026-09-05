# EquationVariantScientificProfile — P-M2

## 1. Identification

```text
identity:

    variantId:
        "P-M2"

    familyId:
        "petroski"

    displayName:
        "Petroski M2 — Masculino"

    aliasNames:
        [
            "Petroski M2",
            "Petroski 9 Dobras + Idade + Circunferências — Masculino"
        ]
```

A variante M2 é a segunda equação generalizada masculina de Petroski. Ela utiliza a soma das nove dobras cutâneas, idade e duas circunferências: antebraço e abdômen.

Referência principal:

```text
Petroski, E. L. (1995).
Desenvolvimento e validação de equações generalizadas para a estimativa
 da densidade corporal em adultos.
Tese de Doutorado. Universidade Federal de Santa Maria (UFSM), Santa Maria, RS.
```

## 2. Mathematical Definition

```text
D =
    1.08516305
    - 0.00028465 × X9
    + 0.00000026 × X9²
    - 0.00021018 × AGE
    + 0.00173856 × CIRCUMFERENCE_FOREARM
    - 0.00043254 × CIRCUMFERENCE_ABDOMEN
```

Onde:

```text
D
    = BODY_DENSITY

X9
    = soma das nove dobras cutâneas

AGE
    = idade em anos
```

A soma X9 utilizada por Petroski é:

```text
X9 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_BICEPS
    + SKINFOLD_MID_AXILLARY
    + SKINFOLD_PECTORAL
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_ABDOMEN
    + SKINFOLD_THIGH
    + SKINFOLD_MEDIAL_CALF
```

As variáveis de circunferência são:

```text
CIRCUMFERENCE_FOREARM
CIRCUMFERENCE_ABDOMEN
```

### Output

```text
outputType = BODY_DENSITY
```

## 3. Applicability

### 3.1 Sexo

```text
sex:

    supportedSexes:
        - MALE
```

A equação M2 pertence ao conjunto de equações generalizadas desenvolvido especificamente para homens.

### 3.2 Idade

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

A amostra de regressão masculina continha 304 homens, com idade de 18–66 anos e média de 30,17 anos. A amostra independente de validação continha 87 homens, com idade de 18–56 anos e média de 30,68 anos.

A média de idade da amostra de desenvolvimento é descritiva e não constitui limite de elegibilidade.

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
            "Amostra heterogênea em idade e composição corporal; percentual
             de gordura de 2,20% a 33,16%, com média de 16,14%."

        sampleCharacteristics:
            "Homens voluntários, selecionados para desenvolvimento das
             equações generalizadas; adaptados ao meio líquido devido ao
             protocolo de pesagem hidrostática."

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
            "Percentual de gordura médio de 15,83%, com faixa de 3,11% a 33,12%."

          sampleCharacteristics:
            "Subamostra independente utilizada para validar as equações
             generalizadas masculinas."

          source:
            Petroski (1995)
```

A tese descreve a população geral do estudo como 672 sujeitos: 281 mulheres e 391 homens, provenientes da região central do Rio Grande do Sul e da região litorânea de Santa Catarina. Para o desenvolvimento masculino foram usados 304 homens e, para validação, 87 homens.

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

A descrição da amostra não fornece classificação operacional suficiente para preencher os campos booleanos de atleta/não atleta.

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

A heterogeneidade observada na amostra não é convertida em regras artificiais de suporte, warning ou inelegibilidade.

## 9. Validation Evidence

### 9.1 Development Evidence

```text
development:

    studyReference:

        citation:
            "Petroski, E. L. (1995). Desenvolvimento e validação de
             equações generalizadas para a estimativa da densidade corporal
             em adultos. Tese de Doutorado, Universidade Federal de Santa Maria."

        doi:
            null

    population:
        "Homens adultos, n = 304, 18–66 anos"

    criterionMethod:
        "Densidade corporal determinada por pesagem hidrostática"

    year:
        1995
```

A tese utilizou regressão múltipla Stepwise com seleção Forward para o desenvolvimento das equações. A densidade corporal determinada por pesagem hidrostática foi utilizada como método critério.

## 10. Validation

```text
validationStudies:

    - studyReference:

        citation:
            "Petroski, E. L. (1995). Desenvolvimento e validação de
             equações generalizadas para a estimativa da densidade corporal
             em adultos. Tese de Doutorado, Universidade Federal de Santa Maria."

        doi:
            null

      population:
        "Homens adultos, n = 87, 18–56 anos"

      criterionMethod:
        "Densidade corporal determinada por pesagem hidrostática"

      metrics:

        correlation:
            0.880

        standardError:
            0.0069

        meanDifference:
            -0.0002

        rmse:
            null

        otherMetrics:
            "ET = 0.0072 g/ml; teste t = -0.244; diferença entre as médias
             não significativa (p > 0,05)."

      limitations:
        "A validação utiliza uma subamostra independente da mesma população
         de origem; não deve ser tratada como validação externa populacional."
```

A tabela de validação de Petroski apresenta para M2: `r = 0,880`, erro constante de `-0,0002 g/ml`, erro total de `0,0072 g/ml` e EPE de `0,0069 g/ml`. A diferença entre densidade mensurada e estimada não foi estatisticamente significativa.

## 11. Cross-validation

```text
crossValidationStudies:
    []
```

A ficha mantém a validação dos próprios modelos em `validationStudies`. Não foi adicionada uma segunda categoria de cross-validation sem evidência específica atribuindo esse procedimento à M2.

## 12. External Validation

```text
externalValidationStudies:
    []
```

Nenhum estudo independente externo à população de origem foi incorporado nesta ficha.

## 13. Measurement Requirements

```text
inputs:

    requiredInputs:

        - AGE
        - SKINFOLD_SUBSCAPULAR
        - SKINFOLD_TRICEPS
        - SKINFOLD_BICEPS
        - SKINFOLD_MID_AXILLARY
        - SKINFOLD_PECTORAL
        - SKINFOLD_SUPRAILIAC
        - SKINFOLD_ABDOMEN
        - SKINFOLD_THIGH
        - SKINFOLD_MEDIAL_CALF
        - CIRCUMFERENCE_FOREARM
        - CIRCUMFERENCE_ABDOMEN

    optionalInputs:
        []
```

`SEX` não é input matemático da equação; ele participa da aplicabilidade da variante.

A M2 exige nove dobras cutâneas, idade e duas circunferências. As propriedades gerais desses tipos de medidas permanecem no `InputTypeCatalog`.

## 14. Scientific Restrictions

```text
restrictions:
    []
```

Não foi cadastrada restrição científica explícita adicional além da aplicabilidade populacional documentada.

## 15. Source Conflict

```text
sourceConflict:
    null
```

A definição matemática da M2 está apresentada de forma consistente na tese de Petroski (1995), incluindo os coeficientes, X9, idade, circunferência do antebraço e circunferência do abdômen.

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

Os elementos essenciais da variante estão documentados: identidade, definição matemática, sexo, população de desenvolvimento, faixa etária, inputs obrigatórios e referência da definição.

## 17. Ficha consolidada

```text
EquationVariantScientificProfile

    identity:
        variantId: "P-M2"
        familyId: "petroski"
        displayName: "Petroski M2 — Masculino"
        aliasNames:
            - "Petroski M2"
            - "Petroski 9 Dobras + Idade + Circunferências — Masculino"

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
                  source: "Petroski (1995)"
            explicitAgeRestriction: null

        population:
            originalPopulation:
                description: "Homens adultos das regiões central do RS e litorânea de SC"
                country: "Brazil"
                region: "Região central do RS e região litorânea de SC"
                sexCoverage:
                    - MALE
                ageCoverage:
                    min: 18
                    max: 66
                sampleSize: 304
                bodyCharacteristicsNotes:
                    "Gordura corporal de 2,20% a 33,16%; média 16,14%."
                sampleCharacteristics:
                    "Amostra heterogênea em idade e composição corporal."
                source: "Petroski (1995)"

            validationPopulations:
                - description: "Homens adultos da amostra independente de validação"
                  country: "Brazil"
                  region: "Região central do RS e região litorânea de SC"
                  sexCoverage:
                      - MALE
                  ageCoverage:
                      min: 18
                      max: 56
                  sampleSize: 87
                  bodyCharacteristicsNotes:
                      "Gordura corporal média 15,83%; faixa 3,11%–33,12%."
                  sampleCharacteristics:
                      "Subamostra independente de validação."
                  source: "Petroski (1995)"

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
                  correlation: 0.880
                  standardError: 0.0069
                  meanDifference: -0.0002
                  rmse: null
                  otherMetrics:
                      "ET = 0.0072 g/ml; t = -0.244; p > 0,05."
              limitations:
                  "Validação na mesma população geográfica de origem."

        crossValidationStudies: []
        externalValidationStudies: []
        sourceConflict: null

    inputs:
        requiredInputs:
            - AGE
            - SKINFOLD_SUBSCAPULAR
            - SKINFOLD_TRICEPS
            - SKINFOLD_BICEPS
            - SKINFOLD_MID_AXILLARY
            - SKINFOLD_PECTORAL
            - SKINFOLD_SUPRAILIAC
            - SKINFOLD_ABDOMEN
            - SKINFOLD_THIGH
            - SKINFOLD_MEDIAL_CALF
            - CIRCUMFERENCE_FOREARM
            - CIRCUMFERENCE_ABDOMEN
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
    desenvolvimento documentado = 18–66 anos
    média = 30.17
    média não é limite

population:
    origem = região central do RS + litoral de SC

athlete:
    NOT_DOCUMENTED

trainingLevel:
    NOT_DOCUMENTED

modality:
    NOT_DOCUMENTED

bodyCharacteristics:
    NOT_DOCUMENTED

inputs:
    AGE
    + 9 skinfolds
    + forearm circumference
    + abdomen circumference

output:
    BODY_DENSITY
```

A presença de circunferências na M2 é parte da própria definição matemática e deve ser tratada como requisito de entrada da variante. Isso diferencia M2 de M1, que usa somente nove dobras e idade.

## 19. Reference

```text
Reference

    citation:
        "Petroski, E. L. (1995).
         Desenvolvimento e validação de equações generalizadas para a
         estimativa da densidade corporal em adultos.
         Tese de Doutorado.
         Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

    doi:
        null

    url:
        "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"
```

Fonte consultada para a equação e dados da amostra: tese de doutorado de Edio Luiz Petroski, UFSM, 1995.

Fonte de catálogo complementar:
    "https://www.cev.org.br/qq/edio-petroski/"
