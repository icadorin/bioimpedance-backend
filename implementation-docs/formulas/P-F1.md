# EquationVariantScientificProfile — P-F1

## 1. Identificação

```text
identity:
    variantId:
        "P-F1"
    familyId:
        "petroski"
    displayName:
        "Petroski F1 — Feminino"
    aliasNames:
        [
            "Petroski F1",
            "Equação F1 de Petroski"
        ]
```

Referência principal: Petroski, E. L. (1995). *Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos*. Tese de Doutorado, Universidade Federal de Santa Maria (UFSM), Santa Maria, RS, Brasil.

## 2. Definição Matemática

A equação P-F1 é:

```text
D =
    1.03987298
    - 0.00031853 × X9
    + 0.00000047 × X9²
    - 0.00025486 × AGE
    - 0.00047358 × BODY_MASS
    + 0.00046897 × HEIGHT
```

Onde:

```text
X9 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_BICEPS
    + SKINFOLD_PECTORAL
    + SKINFOLD_AXILLARY_MID
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_ABDOMEN
    + SKINFOLD_THIGH
    + SKINFOLD_MEDIAL_CALF
```

```text
outputType:
    BODY_DENSITY
```

O modelo é uma regressão quadrática que utiliza a soma de nove dobras cutâneas, idade, massa corporal e estatura.

## 3. Aplicabilidade

### 3.1 Sexo

```text
sex:
    supportedSexes:
        - FEMALE
```

### 3.2 Idade

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

A amostra feminina de regressão continha 213 mulheres com idade entre 18 e 51 anos, com média de 27,46 anos. A amostra independente de validação continha 68 mulheres com idade entre 18 e 43 anos, com média de 27,18 anos. As médias são descritivas e não constituem limites de elegibilidade.

## 4. Aplicabilidade Populacional

```text
population:
    originalPopulation:
        description:
            "Mulheres adultas"
        country:
            "Brasil"
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
            "Amostra feminina de regressão utilizada no desenvolvimento das equações generalizadas de Petroski."
        source:
            Petroski (1995)
    validationPopulations:
        - description:
            "Mulheres adultas da amostra independente de validação"
          country:
            "Brasil"
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

A tese descreve uma amostra feminina total de 281 mulheres, dividida em 213 participantes para regressão e 68 para validação independente. A faixa etária da amostra de desenvolvimento foi de 18–51 anos, e a faixa observada de gordura corporal foi de 11,11% a 36,18%.

## 5. Aplicabilidade em Atletas

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

Não há classificação explícita suficiente das amostras de desenvolvimento e validação como atletas ou não atletas compatível com o modelo booleano operacional da plataforma. A discussão de populações atléticas em outros trabalhos não é atribuída às amostras específicas da P-F1.

## 6. Aplicabilidade por Nível de Treinamento

```text
trainingLevel:
    supportedLevels:
        []
    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

## 7. Aplicabilidade por Modalidade

```text
modality:
    supportedModalities:
        []
    notes:
        "Não documentado em termos de modalidades esportivas específicas."
```

## 8. Características Corporais

```text
bodyCharacteristics:
    rules:
        []
```

Não foi identificada regra explícita de elegibilidade baseada em características corporais para a P-F1. As faixas observadas na amostra descrevem a população estudada e não são convertidas automaticamente em restrições de execução.

## 9. Evidências de Validação

### 9.1 Evidências de Desenvolvimento

```text
development:
    studyReference:
        citation:
            "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado. Universidade Federal de Santa Maria."
        doi:
            null
        url:
            "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"
    population:
        "Mulheres adultas, n = 213, 18–51 anos"
    criterionMethod:
        "Densidade corporal determinada por pesagem hidrostática"
    year:
        1995
    metrics:
        R:
            0.861
        R2:
            0.742
        standardError:
            0.0065
```

Para P-F1, foram relatados `R = 0,861`, `R² = 0,742` e erro-padrão da estimativa de `0,0065 g/ml` na amostra de regressão.

## 10. Validação

A amostra independente de validação foi composta por:

```text
n:
    68 mulheres
age:
    18–43 anos
```

Método critério:

```text
Pesagem hidrostática
```

Para P-F1:

```text
correlation:
    0.773
constantError:
    -0.00025 g/ml
totalError:
    0.0065 g/ml
standardErrorOfEstimate:
    0.0065 g/ml
```

A tabela de validação relata `r = 0,773`, `t = -0,308`, `EC = -0,00025 g/ml`, `ET = 0,0065 g/ml` e `EPE = 0,0065 g/ml`.

## 11. Validação Cruzada

```text
crossValidationStudies:
    []
```

A amostra independente de 68 mulheres é representada em `validationStudies` e não é duplicada em `crossValidationStudies`. A análise separada de validação cruzada da tese envolve equações provenientes de outros investigadores e não constitui estudo adicional específico da P-F1.

## 12. Validação Externa

```text
externalValidationStudies:
    []
```

Não foi incluído estudo de validação externa da P-F1 fora da investigação original de Petroski.

## 13. Requisitos de Medição

```text
inputs:
    requiredInputs:
        - AGE
        - BODY_MASS
        - HEIGHT
        - SKINFOLD_SUBSCAPULAR
        - SKINFOLD_TRICEPS
        - SKINFOLD_BICEPS
        - SKINFOLD_PECTORAL
        - SKINFOLD_AXILLARY_MID
        - SKINFOLD_SUPRAILIAC
        - SKINFOLD_ABDOMEN
        - SKINFOLD_THIGH
        - SKINFOLD_MEDIAL_CALF
    optionalInputs:
        []
```

Entradas matemáticas:

```text
AGE
BODY_MASS
HEIGHT
X9
```

Onde `X9` é a soma de:

```text
SKINFOLD_SUBSCAPULAR
SKINFOLD_TRICEPS
SKINFOLD_BICEPS
SKINFOLD_PECTORAL
SKINFOLD_AXILLARY_MID
SKINFOLD_SUPRAILIAC
SKINFOLD_ABDOMEN
SKINFOLD_THIGH
SKINFOLD_MEDIAL_CALF
```

A equação utiliza `X9` e `X9²`. `SEX` não faz parte de `requiredInputs`; determina a aplicabilidade à variante feminina.

Segundo a notação da tese, a idade é expressa em anos, a massa corporal em quilogramas, a estatura em centímetros e as dobras cutâneas em milímetros.

Definições gerais de unidades, precisão e faixas plausíveis permanecem em:

```text
/library/measurements
```

## 14. Restrições Científicas

```text
restrictions:
    []
```

Não foi identificada restrição científica adicional explícita que produza `INELIGIBLE`. As faixas observadas de idade e composição corporal são evidências descritivas da população estudada e não são transformadas automaticamente em limites rígidos de elegibilidade.

## 15. Conflito de Fonte

```text
sourceConflict:
    null
```

Não foi identificado conflito material entre fontes primárias para a definição matemática, população de desenvolvimento ou resultados de validação documentados para P-F1.

## 16. Ciclo de Vida

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

Informações essenciais:

```text
identity                    CONFIRMED
mathematicalDefinition      CONFIRMED
supportedSexes              CONFIRMED
originalDevelopmentAgeRange CONFIRMED
originalPopulation          CONFIRMED
requiredInputs              CONFIRMED
definitionReference         CONFIRMED
sourceConflict              null
```

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile
    identity:
        variantId:
            "P-F1"
        familyId:
            "petroski"
        displayName:
            "Petroski F1 — Feminino"
        aliasNames:
            [
                "Petroski F1",
                "Equação F1 de Petroski"
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
                    "Brasil"
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
                    "Amostra feminina de regressão utilizada no desenvolvimento das equações generalizadas de Petroski."
                source:
                    Petroski (1995)
            validationPopulations:
                - description:
                    "Mulheres adultas da amostra independente de validação"
                  country:
                    "Brasil"
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
                "Não documentado segundo a escala operacional da plataforma."
        modality:
            supportedModalities:
                []
            notes:
                "Não documentado em termos de modalidades esportivas específicas."
        bodyCharacteristics:
            rules:
                []
    evidence:
        development:
            studyReference:
                citation:
                    "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado. Universidade Federal de Santa Maria."
                doi:
                    null
                url:
                    "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"
            population:
                "Mulheres adultas, n = 213, 18–51 anos"
            criterionMethod:
                "Pesagem hidrostática"
            year:
                1995
            metrics:
                R:
                    0.861
                R2:
                    0.742
                standardError:
                    0.0065
        validationStudies:
            - studyReference:
                citation:
                    "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado. Universidade Federal de Santa Maria."
                doi:
                    null
                url:
                    "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"
              population:
                  "Mulheres adultas, n = 68, 18–43 anos"
              criterionMethod:
                  "Pesagem hidrostática"
              metrics:
                  correlation:
                      0.773
                  standardError:
                      0.0065
                  meanDifference:
                      -0.00025
                  rmse:
                      null
                  otherMetrics:
                      "EC = -0.00025 g/ml; ET = 0.0065 g/ml; EPE = 0.0065 g/ml."
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
            - SKINFOLD_BICEPS
            - SKINFOLD_PECTORAL
            - SKINFOLD_AXILLARY_MID
            - SKINFOLD_SUPRAILIAC
            - SKINFOLD_ABDOMEN
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

O perfil científico permite ao motor considerar:

```text
sex:
    FEMALE → compatível com o sexo documentado
age:
    comparar com a evidência populacional documentada,
    sem utilizar 27.46 como limite de elegibilidade
athlete:
    NOT_DOCUMENTED
trainingLevel:
    NOT_DOCUMENTED
modality:
    NOT_DOCUMENTED
bodyCharacteristics:
    sem regra explícita
inputs:
    AGE
    + BODY_MASS
    + HEIGHT
    + 9 skinfolds
output:
    BODY_DENSITY
```

O motor não deve transformar a média de idade de desenvolvimento em limite de elegibilidade. A ausência de evidência sobre atleta, nível de treinamento ou modalidade também não deve resultar automaticamente em `INELIGIBLE`.

P-F1 é a variante feminina de Petroski que utiliza o conjunto de nove dobras `X9`, além de `AGE`, `BODY_MASS` e `HEIGHT`, com modelo quadrático em `X9`. A variante deve permanecer distinta das demais equações femininas da família, mesmo quando compartilha parte dos mesmos locais de medição.

## 19. Referência

```text
Reference:
    citation:
        "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado. Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."
    doi:
        null
    url:
        "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"
```

Fonte bibliográfica adicional:

```text
citation:
    "Centro Esportivo Virtual — Desenvolvimento e Validação de Equações Generalizadas Para a Estimativa da Densidade Corporal em Adultos."
doi:
    null
url:
    "https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/"
```
