# EquationVariantScientificProfile — P-M6

## 1. Identification

```text
identity:

    variantId:
        "P-M6"

    familyId:
        "petroski"

    displayName:
        "Petroski M6 — Masculino"

    aliasNames:
        []
```

Reference principal:

```text
Petroski, E. L. (1995).
Desenvolvimento e validação de equações generalizadas para a estimativa
da densidade corporal em adultos.
Tese de Doutorado em Educação Física.
Universidade Federal de Santa Maria.
```

---

## 2. Mathematical Definition

A variante M6 utiliza seis dobras cutâneas, idade e as circunferências do antebraço e abdômen.

```text
D =
    1.08555470
    - 0.00050212 × X6
    + 0.00000104 × X6²
    - 0.00015217 × AGE
    + 0.00169842 × CIRCUMFERENCE_FOREARM
    - 0.00044620 × CIRCUMFERENCE_ABDOMEN
```

Onde:

```text
X6 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_BICEPS
    + SKINFOLD_PECTORAL
    + SKINFOLD_AXILLARY_MID
    + SKINFOLD_SUPRAILIAC
```

```text
outputType = BODY_DENSITY
```

A definição acima corresponde à equação M6 da Tabela 9 da tese de Petroski.

---

## 3. Applicability

### 3.1 Sex

```text
sex:

    supportedSexes:
        - MALE
```

A equação M6 pertence ao conjunto de equações generalizadas desenvolvido para homens adultos.

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

A idade média da amostra de desenvolvimento foi 30,17 anos.

A média de idade é descritiva e não deve ser utilizada como limite de elegibilidade.

### 3.3 Population

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
            "A amostra apresentou heterogeneidade de idade e gordura corporal,
             adequada ao desenvolvimento de equações generalizadas."

        sampleCharacteristics:
            "Homens adultos das regiões estudadas por Petroski, com diferentes
             características corporais e níveis de atividade física."

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
            null

          sampleCharacteristics:
            "Amostra de calibração independente oriunda da mesma população."

          source:
            Petroski (1995)
```

### 3.4 Athlete Applicability

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

A descrição da população não foi convertida artificialmente para os booleanos operacionais de atleta/não atleta.

### 3.5 Training Level

```text
trainingLevel:

    supportedLevels:
        []

    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

### 3.6 Modality

```text
modality:

    supportedModalities:
        []

    notes:
        "Não documentado em termos de modalidades esportivas específicas."
```

### 3.7 Body Characteristics

```text
bodyCharacteristics:

    rules:
        []
```

As características gerais da amostra não foram transformadas em restrições corporais específicas sem documentação explícita.

---

## 4. Population Applicability

A população de desenvolvimento da M6 é a mesma população masculina utilizada para as equações generalizadas de Petroski:

```text
n = 304
sex = MALE
age = 18–66 years
region = central Rio Grande do Sul + coastal Santa Catarina
country = Brazil
```

A amostra de validação utilizada para testar as equações generalizadas continha:

```text
n = 87
sex = MALE
age = 18–56 years
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
        "Não documentado."
```

---

## 8. Body Characteristics

```text
bodyCharacteristics:

    rules:
        []
```

Nenhuma característica corporal específica foi cadastrada como regra de aplicabilidade.

---

## 9. Validation Evidence

### 9.1 Development Evidence

```text
development:

    studyReference:

        citation:
            "Petroski, E. L. (1995).
             Desenvolvimento e validação de equações generalizadas para
             a estimativa da densidade corporal em adultos.
             Tese de Doutorado em Educação Física, Universidade Federal
             de Santa Maria."

        doi:
            null

        url:
            "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"

    population:
        "304 homens adultos, 18–66 anos"

    criterionMethod:
        "Pesagem hidrostática para determinação da densidade corporal"

    year:
        1995

    metrics:

        correlation:
            0.889

        determination:
            0.790

        standardError:
            0.0071

        meanDifference:
            null

        rmse:
            null

        otherMetrics:
            null
```

Na Tabela 9 da tese, a M6 apresenta:

```text
R  = 0.889
R² = 0.790
EPE = 0.0071 g/ml
```

---

## 10. Validation

A validação independente das equações generalizadas foi realizada em uma amostra de 87 homens.

Para a M6:

```text
sampleSize:
    87

population:
    "Homens adultos, 18–56 anos"

criterionMethod:
    "Pesagem hidrostática"

metrics:

    correlation:
        0.858

    meanDifference:
        null

    constantError:
        -0.0004 g/ml

    technicalError:
        0.0078 g/ml

    standardErrorEstimate:
        0.0074 g/ml
```

A tabela de validação apresenta também a densidade estimada média:

```text
1.06240 ± 0.013 g/ml
```

A diferença entre a densidade mensurada e estimada para a M6 não foi estatisticamente significativa (`p > 0,05`).

---

## 11. Cross-validation

```text
crossValidationStudies:
    []
```

A tese não apresenta, para a M6, um estudo externo de cross-validation separado adicional que deva ser duplicado nesta seção. A validação da amostra independente é registrada na seção `Validation`.

---

## 12. External Validation

```text
externalValidationStudies:
    []
```

Não foi cadastrada validação externa independente.

---

## 13. Measurement Requirements

```text
inputs:

    requiredInputs:

        - AGE

        - SKINFOLD_SUBSCAPULAR
        - SKINFOLD_TRICEPS
        - SKINFOLD_BICEPS
        - SKINFOLD_PECTORAL
        - SKINFOLD_AXILLARY_MID
        - SKINFOLD_SUPRAILIAC

        - CIRCUMFERENCE_FOREARM
        - CIRCUMFERENCE_ABDOMEN

    optionalInputs:
        []
```

Importante:

```text
SEX
    → Applicability

AGE
    → Formula input

6 skinfolds
    → Formula inputs

FOREARM CIRCUMFERENCE
    → Formula input

ABDOMEN CIRCUMFERENCE
    → Formula input
```

`SEX` não é duplicado como variável matemática da fórmula.

As propriedades gerais de cada input permanecem no `InputTypeCatalog`.

---

## 14. Scientific Restrictions

```text
restrictions:
    []
```

A faixa de 18–66 anos corresponde à população de desenvolvimento e não foi convertida em `ScientificRestriction`.

Nenhuma restrição científica adicional explicitamente documentada foi cadastrada para a M6.

---

## 15. Source Conflict

```text
evidence:

    sourceConflict:
        null
```

A definição matemática da M6 é apresentada diretamente na Tabela 9 da tese de Petroski:

```text
D =
    1.08555470
    - 0.00050212(X6)
    + 0.00000104(X6²)
    - 0.00015217(ID)
    + 0.00169842(CAT)
    - 0.00044620(CAB)
```

Os valores das métricas de desenvolvimento e validação também são apresentados nas Tabelas 9 e 10.

Não foi identificado conflito material entre essas informações na fonte principal consultada.

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

Informações essenciais preenchidas:

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

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:
            "P-M6"

        familyId:
            "petroski"

        displayName:
            "Petroski M6 — Masculino"

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
                    "Homens adultos"

                country:
                    "Brazil"

                region:
                    "Região central do Rio Grande do Sul e região litorânea
                     de Santa Catarina"

                sexCoverage:
                    - MALE

                ageCoverage:
                    min: 18
                    max: 66

                sampleSize:
                    304

                bodyCharacteristicsNotes:
                    "Amostra heterogênea em idade e gordura corporal."

                sampleCharacteristics:
                    "Homens adultos da população estudada por Petroski."

                source:
                    Petroski (1995)

            validationPopulations:

                - description:
                    "Homens adultos da amostra independente de validação"

                  country:
                    "Brazil"

                  region:
                    "Região central do Rio Grande do Sul e região litorânea
                     de Santa Catarina"

                  sexCoverage:
                    - MALE

                  ageCoverage:
                    min: 18
                    max: 56

                  sampleSize:
                    87

                  bodyCharacteristicsNotes:
                    null

                  sampleCharacteristics:
                    "Amostra independente oriunda da mesma população."

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
                "Não documentado."

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
                "304 homens adultos, 18–66 anos"

            criterionMethod:
                "Pesagem hidrostática"

            year:
                1995

            metrics:
                R: 0.889
                R2: 0.790
                EPE: 0.0071

        validationStudies:

            - studyReference:
                "Petroski (1995)"

              population:
                "87 homens adultos, 18–56 anos"

              criterionMethod:
                "Pesagem hidrostática"

              metrics:
                correlation: 0.858
                meanDifference: null
                constantError: -0.0004
                technicalError: 0.0078
                standardErrorEstimate: 0.0074

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
            - SKINFOLD_BICEPS
            - SKINFOLD_PECTORAL
            - SKINFOLD_AXILLARY_MID
            - SKINFOLD_SUPRAILIAC
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

A ficha permite ao motor interpretar a P-M6 sem conhecimento científico hardcoded da variante:

```text
sex:
    MALE
        → compatível

age:
    18–66
        → faixa da população de desenvolvimento

athlete:
    NOT_DOCUMENTED

trainingLevel:
    NOT_DOCUMENTED

modality:
    NOT_DOCUMENTED

bodyCharacteristics:
    NOT_DOCUMENTED

requiredInputs:
    AGE
    + 6 skinfolds
    + forearm circumference
    + abdomen circumference

output:
    BODY_DENSITY
```

Os valores:

```text
R = 0.889
R² = 0.790
EPE = 0.0071 g/ml
```

são evidência científica da variante, e não `evidenceCoverage` em tempo de execução.

A validação independente apresentou:

```text
r  = 0.858
EC = -0.0004 g/ml
ET = 0.0078 g/ml
EPE = 0.0074 g/ml
```

O motor deve continuar distinguindo essas métricas documentadas dos estados derivados de aplicabilidade.

---

## 19. Reference

```text
Reference

    citation:
        "Petroski, E. L. (1995).
         Desenvolvimento e validação de equações generalizadas para a
         estimativa da densidade corporal em adultos.
         Tese de Doutorado em Educação Física.
         Universidade Federal de Santa Maria, Santa Maria, RS."

    doi:
        null

    url:
        "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"
```

Fonte adicional de validação:

```text
Petroski, E. L.; Pires Neto, C. S.
Validação de equações antropométricas para a estimativa da densidade
corporal em homens.
Revista Brasileira de Atividade Física & Saúde.

url:
https://rbafs.org.br/RBAFS/article/view/496
```
