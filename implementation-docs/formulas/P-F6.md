# EquationVariantScientificProfile — P-F6

## 1. Identificação

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

Referência principal:

```text
Petroski, E. L. (1995).
Desenvolvimento e validação de equações generalizadas para a estimativa
da densidade corporal em adultos.
Tese de Doutorado.
Universidade Federal de Santa Maria (UFSM),
Santa Maria, RS, Brasil.
```

A fonte primária identifica a P-F6 como uma equação feminina de regressão logarítmica para estimativa da densidade corporal, baseada no logaritmo decimal de `Y7`, idade e circunferência da coxa.

## 2. Definição Matemática

A equação P-F6 é:

```text
D =
    1.21527404
    - 0.06432107 × LOG10(Y7)
    - 0.00033650 × AGE
    - 0.00049553 × CIRCUMFERENCE_THIGH
```

Onde:

```text
Y7 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_AXILLARY_MID
    + SKINFOLD_PECTORAL
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_ABDOMEN
    + SKINFOLD_THIGH
```

A tabela da tese apresenta a P-F6 com a composição `Y7 = SE + TR + AM + PT + SI + AB + CX`. Os identificadores anatômicos foram normalizados para os nomes canônicos do sistema, preservando a composição matemática documentada.

Saída:

```text
outputType:
    BODY_DENSITY
```

Forma computacional:

```text
1. calcular Y7 como a soma das sete dobras cutâneas requeridas;
2. calcular LOG10(Y7);
3. aplicar AGE;
4. aplicar CIRCUMFERENCE_THIGH;
5. calcular D como BODY_DENSITY.
```

A equação é um modelo de regressão logarítmica que utiliza a soma de sete dobras cutâneas, idade e circunferência da coxa.

## 3. Aplicabilidade

### 3.1 Sexo

```text
sex:
    supportedSexes:
        - FEMALE
```

A P-F6 pertence ao conjunto de equações generalizadas desenvolvido para mulheres adultas.

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

A amostra de desenvolvimento feminina continha 213 mulheres de 18 a 51 anos, com idade média de 27,46 anos. A amostra independente de validação continha 68 mulheres de 18 a 43 anos. A média de idade do desenvolvimento é descritiva e não constitui, isoladamente, um limite de elegibilidade.

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
            "Amostra feminina de regressão utilizada no desenvolvimento
das equações generalizadas de Petroski."
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

O estudo envolveu 281 mulheres, sendo 213 utilizadas no desenvolvimento das equações e 68 em uma amostra independente de validação. A fonte descreve a amostra feminina como heterogênea em idade e gordura corporal.

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

A fonte não fornece, no conteúdo utilizado para esta ficha, uma classificação explícita de atleta/não atleta compatível com o modelo booleano operacional da plataforma. Portanto, não é atribuída aplicabilidade específica a atletas ou não atletas.

## 6. Aplicabilidade por Nível de Treinamento

```text
trainingLevel:
    supportedLevels:
        []
    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

Não há documentação suficiente para classificar a amostra segundo a escala `SEDENTARY`, `RECREATIONAL`, `TRAINED`, `COMPETITIVE` ou `ELITE`.

## 7. Aplicabilidade por Modalidade

```text
modality:
    supportedModalities:
        []
    notes:
        "Não documentado em termos de modalidades esportivas específicas."
```

Não há uma modalidade esportiva específica documentada como critério de desenvolvimento ou validação da P-F6.

## 8. Características Corporais

```text
bodyCharacteristics:
    rules:
        []
```

Não foi identificada uma regra explícita de elegibilidade por característica corporal específica para a P-F6. A heterogeneidade de idade e gordura corporal descrita na amostra é informação descritiva da população de desenvolvimento, não uma regra de elegibilidade automática.

## 9. Evidências de Validação

### 9.1 Evidências de Desenvolvimento

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
            "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"
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

A Tabela 4 da tese apresenta a P-F6 com `R = 0,836`, `R² = 0,699` e `EPE = 0,0070 g/ml`. A mesma tabela identifica o modelo como `Log10 DC, ID, C` e fornece os coeficientes da equação documentada nesta ficha.

## 10. Validação

A amostra independente de validação foi:

```text
n:
    68 women
age:
    18–43 years
```

Forma normalizada em português:

```text
n:
    68 mulheres
idade:
    18–43 anos
```

Método de referência:

```text
criterionMethod:
    "Pesagem hidrostática"
```

Resultados da P-F6:

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

A Tabela 5 apresenta, para a P-F6, densidade estimada média de `1,046349 ± 0,0094 g/ml`, `r = 0,723`, `t = -0,041`, `EC = -0,00004 g/ml`, `ET = 0,0072 g/ml` e `EPE = 0,0070 g/ml`. A tese descreve essa amostra como independente e proveniente da mesma população do estudo.

No modelo consolidado:

```text
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
```

## 11. Validação Cruzada

```text
crossValidationStudies:
    []
```

A amostra independente de 68 mulheres pertence à validação da própria equação dentro do estudo original e, portanto, permanece em `validationStudies`. A tese também apresenta uma análise de validação cruzada de equações de outros investigadores; essa análise não deve ser atribuída como estudo adicional específico da P-F6.

## 12. Validação Externa

```text
externalValidationStudies:
    []
```

Não foi incluído nesta ficha um estudo externo independente da investigação original de Petroski que tenha validado especificamente a P-F6.

## 13. Requisitos de Medição

```text
inputs:
    requiredInputs:
        - AGE
        - SKINFOLD_SUBSCAPULAR
        - SKINFOLD_TRICEPS
        - SKINFOLD_AXILLARY_MID
        - SKINFOLD_PECTORAL
        - SKINFOLD_SUPRAILIAC
        - SKINFOLD_ABDOMEN
        - SKINFOLD_THIGH
        - CIRCUMFERENCE_THIGH
    optionalInputs:
        []
```

Entradas matemáticas:

```text
AGE
Y7
CIRCUMFERENCE_THIGH
```

Onde:

```text
Y7 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_AXILLARY_MID
    + SKINFOLD_PECTORAL
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_ABDOMEN
    + SKINFOLD_THIGH
```

E:

```text
LOG10(Y7)
```

é utilizado na equação.

A fonte informa `ID` em anos, `DC` em milímetros e `CCX` em centímetros. Os demais identificadores foram convertidos para a nomenclatura canônica do sistema.

`SEX` não é incluído em `requiredInputs`; ele determina a aplicabilidade à variante feminina.

As definições gerais de unidades, precisão e intervalos plausíveis permanecem em:

```text
/library/measurements
```

## 14. Restrições Científicas

```text
restrictions:
    []
```

Não foi identificada uma restrição científica adicional explícita que deva produzir uma classificação de inaplicabilidade além da necessidade de possuir as entradas matemáticas requeridas pela equação.

A faixa de 18–51 anos corresponde à população de desenvolvimento documentada e não é convertida automaticamente em uma restrição adicional de runtime. A idade, porém, é variável matemática da própria equação e permanece em `requiredInputs`.

A entrada `CIRCUMFERENCE_THIGH` é matematicamente necessária porque aparece explicitamente na equação.

O agregado `Y7` deve estar validamente calculado antes da aplicação de `LOG10(Y7)`.

## 15. Conflito de Fonte

```text
sourceConflict:
    null
```

Não foi identificado conflito material entre fontes legítimas para a definição matemática da P-F6 nem para os valores de desenvolvimento e validação registrados nesta ficha. A fonte primária consultada apresenta a equação F6, a composição de `Y7` e as métricas correspondentes de forma consistente.

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

Checklist de campos essenciais:

```text
identity                       CONFIRMED
mathematicalDefinition         CONFIRMED
supportedSexes                 CONFIRMED
originalDevelopmentAgeRange   CONFIRMED
originalPopulation             CONFIRMED
requiredInputs                 CONFIRMED
definitionReference            CONFIRMED
sourceConflict                  null
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
    mathematicalDefinition:
        formula:
            "D = 1.21527404 - 0.06432107 × LOG10(Y7) - 0.00033650 × AGE - 0.00049553 × CIRCUMFERENCE_THIGH"
        outputType:
            BODY_DENSITY
        variables:
            Y7:
                "SKINFOLD_SUBSCAPULAR + SKINFOLD_TRICEPS + SKINFOLD_AXILLARY_MID + SKINFOLD_PECTORAL + SKINFOLD_SUPRAILIAC + SKINFOLD_ABDOMEN + SKINFOLD_THIGH"
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
                    0.723
                standardError:
                    0.0070
                meanDifference:
                    -0.00004
                rmse:
                    null
                otherMetrics:
                    "EC = -0.00004 g/ml; ET = 0.0072 g/ml; EPE = 0.0070 g/ml."
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
            - SKINFOLD_AXILLARY_MID
            - SKINFOLD_PECTORAL
            - SKINFOLD_SUPRAILIAC
            - SKINFOLD_ABDOMEN
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

O perfil científico permite a avaliação em runtime de:

```text
sex:
    FEMALE → compatível com o sexo documentado

age:
    comparar com a evidência populacional documentada
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
    + 7 skinfolds
    + CIRCUMFERENCE_THIGH

output:
    BODY_DENSITY
```

A ausência de documentação específica sobre atleta, nível de treinamento ou modalidade não deve produzir uma classificação de inaplicabilidade.

A P-F6 é a variante feminina logarítmica de sete dobras cutâneas com circunferência da coxa. Seu conjunto `Y7` é compartilhado com a P-F5, mas a P-F6 acrescenta `CIRCUMFERENCE_THIGH` à equação. Portanto:

```text
P-F5:
    AGE + Y7

P-F6:
    AGE + Y7 + CIRCUMFERENCE_THIGH
```

As duas variantes são matematicamente distintas e devem permanecer distintas no `SuggestionEngine`.

## 19. Referência

```text
Reference:
    citation:
        "Petroski, E. L. (1995).
        Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos.
        Tese de Doutorado.
        Universidade Federal de Santa Maria (UFSM), Santa Maria, RS, Brasil."
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

A referência institucional do Centro Esportivo Virtual registra a tese de Petroski e confirma que o estudo desenvolveu 16 equações generalizadas para cada sexo, com 281 mulheres no conjunto feminino e 68 mulheres na amostra utilizada para validação.