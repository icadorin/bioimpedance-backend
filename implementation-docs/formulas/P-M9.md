# EquationVariantScientificProfile — P-M9

## 1. Identificação

```text
identity:
    variantId:
        "P-M9"

    familyId:
        "petroski"

    displayName:
        "Petroski M9 — Masculino"

    aliasNames:
        [
            "Petroski M9",
            "Equação M9 de Petroski"
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

A variante P-M9 corresponde à equação masculina M9 do conjunto de 16 equações generalizadas propostas por Petroski (1995) para estimativa da densidade corporal. A equação M9 integra o grupo de modelos com quatro dobras cutâneas e idade.

## 2. Definição Matemática

A equação P-M9 é:

```text
D =
    1.10539106
    - 0.00089839 × Z4
    + 0.00000278 × Z4²
    - 0.00035250 × AGE
```

Onde:

```text
Z4 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_BICEPS
    + SKINFOLD_SUPRAILIAC
```

Saída:

```text
outputType:
    BODY_DENSITY
```

A equação utiliza a soma de quatro dobras cutâneas, o quadrado dessa soma e a idade. As quatro dobras são subescapular, tricipital, bicipital e supra-ilíaca.

Forma computacional:

```text
Z4 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_BICEPS
    + SKINFOLD_SUPRAILIAC

BODY_DENSITY =
    1.10539106
    - 0.00089839 × Z4
    + 0.00000278 × Z4²
    - 0.00035250 × AGE
```

A definição dos coeficientes e das variáveis coincide com a Tabela 9 da tese de Petroski (1995).

## 3. Aplicabilidade

### 3.1 Sexo

```text
sex:
    supportedSexes:
        - MALE
```

A P-M9 pertence ao conjunto masculino de equações generalizadas desenvolvido por Petroski.

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

A população masculina de desenvolvimento abrangia indivíduos de 18 a 66 anos, com média de 30,17 anos. A amostra independente de validação compreendeu 87 homens de 18 a 56 anos. A média de idade é descritiva e não constitui limite adicional de elegibilidade.

## 4. Aplicabilidade Populacional

```text
population:
    originalPopulation:
        description:
            "Homens adultos"

        country:
            "Brasil"

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
            "Amostra heterogênea em idade e gordura corporal; a gordura corporal variou aproximadamente de 2,20% a 33,16% na amostra de desenvolvimento."

        sampleCharacteristics:
            "Homens adultos utilizados no desenvolvimento das equações generalizadas de Petroski. A amostra de regressão foi composta por 304 participantes, com densidade corporal de critério determinada por pesagem hidrostática."

        source:
            Petroski (1995)

    validationPopulations:
        - description:
            "Homens adultos da amostra independente de validação"

          country:
            "Brasil"

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
            "Amostra independente utilizada para validar as equações generalizadas masculinas."

          source:
            Petroski (1995)
```

O estudo masculino utilizou 304 homens na amostra de regressão para o desenvolvimento das equações e 87 homens em uma amostra independente de validação. A amostra de desenvolvimento apresentava heterogeneidade de idade, de 18 a 66 anos, e de gordura corporal, aproximadamente de 2,20% a 33,16%. A amostra total masculina do estudo compreendeu 391 homens.

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

A documentação original consultada não fornece classificação suficientemente explícita e compatível com o modelo booleano da plataforma para atribuir o status de atleta ou não atleta à P-M9. Nenhuma classificação é inferida.

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

A heterogeneidade de gordura corporal observada na amostra de desenvolvimento é uma característica descritiva da população e não constitui, por si só, uma regra de elegibilidade específica para a P-M9.

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
        "Homens adultos da amostra de regressão, n = 304, 18–66 anos"

    criterionMethod:
        "Densidade corporal determinada por pesagem hidrostática"

    year:
        1995

    metrics:
        R:
            0.874

        R2:
            0.764

        standardError:
            0.0075
```

A P-M9 é uma das 16 equações generalizadas masculinas desenvolvidas por Petroski. Na Tabela 9, apresenta coeficiente de correlação múltipla R = 0,874, coeficiente de determinação R² = 0,764 e erro padrão de estimativa de 0,0075 g/ml. A amostra de desenvolvimento da regressão masculina foi de 304 participantes.

## 10. Validação

A amostra independente de validação foi:

```text
n:
    87 homens

age:
    18–56 anos
```

Método de critério:

```text
criterionMethod:
    "Pesagem hidrostática"
```

Para P-M9:

```text
correlation:
    0.847

constantError:
    -0.0003 g/ml

totalError:
    0.0081 g/ml

standardErrorOfEstimate:
    0.0082 g/ml
```

A Tabela 10 também registra densidade média estimada de 1,06248 ± 0,013 g/ml para M9, em comparação com 1,06282 ± 0,0153 g/ml de densidade medida. Os resultados de validação são apresentados na tese para a amostra independente de 87 homens.

## 11. Validação Cruzada

```text
crossValidationStudies:
    []
```

Não foi incluída evidência de validação cruzada da própria P-M9 nesta ficha. A Tabela 11 da tese apresenta a validação cruzada de equações de outros pesquisadores aplicada à amostra masculina de desenvolvimento; esses resultados não devem ser reinterpretados como validação cruzada da P-M9.

## 12. Validação Externa

```text
externalValidationStudies:
    []
```

Não foi identificado, na documentação utilizada para este perfil, estudo de validação externa independente específico da variante P-M9.

## 13. Requisitos de Medição

```text
inputs:
    requiredInputs:
        - AGE
        - SKINFOLD_SUBSCAPULAR
        - SKINFOLD_TRICEPS
        - SKINFOLD_BICEPS
        - SKINFOLD_SUPRAILIAC

    optionalInputs:
        []
```

Entradas matemáticas:

```text
AGE
Z4
```

onde:

```text
Z4 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_BICEPS
    + SKINFOLD_SUPRAILIAC
```

`SEX` não integra `requiredInputs`; o sexo determina a aplicabilidade à variante masculina.

`AGE` integra `requiredInputs` porque participa diretamente da equação por meio do termo `- 0.00035250 × AGE`.

As definições gerais de unidade, precisão e faixas plausíveis permanecem em:

```text
/library/measurements
```

Na Tabela 9, Z4 é definido como a soma das quatro dobras cutâneas subescapular, tricipital, bicipital e supra-ilíaca.

## 14. Restrições Científicas

```text
restrictions:
    []
```

Não foi identificada restrição científica adicional explícita que produza `INELIGIBLE` para a P-M9 além da aplicabilidade documentada ao sexo masculino e da população de desenvolvimento e validação registrada nesta ficha.

## 15. Conflito de Fonte

```text
sourceConflict:
    null
```

Não foi identificado conflito material entre as informações da fonte principal consultada quanto à definição matemática, população de desenvolvimento ou resultados de validação da P-M9.

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
identity:
    CONFIRMED

mathematicalDefinition:
    CONFIRMED

supportedSexes:
    CONFIRMED

originalDevelopmentAgeRange:
    CONFIRMED

originalPopulation:
    CONFIRMED

requiredInputs:
    CONFIRMED

definitionReference:
    CONFIRMED

sourceConflict:
    null
```

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile
    identity:
        variantId:
            "P-M9"

        familyId:
            "petroski"

        displayName:
            "Petroski M9 — Masculino"

        aliasNames:
            [
                "Petroski M9",
                "Equação M9 de Petroski"
            ]

    mathematicalDefinition:
        formula:
            "D = 1.10539106 - 0.00089839 × Z4 + 0.00000278 × Z4² - 0.00035250 × AGE"

        variables:
            Z4:
                "SKINFOLD_SUBSCAPULAR + SKINFOLD_TRICEPS + SKINFOLD_BICEPS + SKINFOLD_SUPRAILIAC"

        outputType:
            "BODY_DENSITY"

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
                    "Brasil"
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
                    "Amostra heterogênea em idade e gordura corporal; a gordura corporal variou aproximadamente de 2,20% a 33,16% na amostra de desenvolvimento."
                sampleCharacteristics:
                    "Homens adultos utilizados no desenvolvimento das equações generalizadas de Petroski."
                source:
                    Petroski (1995)

            validationPopulations:
                - description:
                    "Homens adultos da amostra independente de validação"
                  country:
                    "Brasil"
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
                    "Amostra independente utilizada para validar as equações generalizadas masculinas."
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
                "Homens adultos da amostra de regressão, n = 304, 18–66 anos"

            criterionMethod:
                "Pesagem hidrostática"

            year:
                1995

            metrics:
                R:
                    0.874
                R2:
                    0.764
                standardError:
                    0.0075

        validationStudies:
            - studyReference:
                citation:
                    "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado. Universidade Federal de Santa Maria."
                doi:
                    null
                url:
                    "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"

              population:
                "Homens adultos, n = 87, 18–56 anos"

              criterionMethod:
                "Pesagem hidrostática"

              metrics:
                correlation:
                    0.847
                standardError:
                    0.0082
                meanDifference:
                    -0.0003
                rmse:
                    null
                otherMetrics:
                    "EC = -0,0003 g/ml; ET = 0,0081 g/ml."
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
            - SKINFOLD_BICEPS
            - SKINFOLD_SUPRAILIAC

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

```text
sex:
    MALE → compatível com o sexo documentado da variante

age:
    comparar com a faixa populacional documentada
    sem utilizar 30.17 como limite de elegibilidade

athlete:
    NOT_DOCUMENTED

trainingLevel:
    NOT_DOCUMENTED

modality:
    NOT_DOCUMENTED

bodyCharacteristics:
    nenhuma regra explícita

inputs:
    AGE
    + SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_BICEPS
    + SKINFOLD_SUPRAILIAC

output:
    BODY_DENSITY
```

O motor não deve transformar a média de idade de 30,17 anos em limite de elegibilidade. A ausência de classificação documental sobre atleta, nível de treinamento ou modalidade também não deve ser convertida em `INELIGIBLE`.

A P-M9 requer cinco entradas observáveis para o cálculo: idade e quatro dobras cutâneas. O conjunto Z4 utilizado pela equação é formado pelas dobras subescapular, tricipital, bicipital e supra-ilíaca.

## 19. Referência

```text
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

A fonte primária consultada apresenta a equação M9, os coeficientes de desenvolvimento e os resultados da validação da amostra independente. A documentação institucional adicional identifica a tese de Petroski (1995) e sua origem na Universidade Federal de Santa Maria.