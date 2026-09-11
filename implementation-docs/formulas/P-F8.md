# EquationVariantScientificProfile — P-F8

## 1. Identificação

```text
identity:
    variantId:
        "P-F8"

    familyId:
        "petroski"

    displayName:
        "Petroski F8 — Feminino"

    aliasNames:
        [
            "Petroski F8",
            "Equação F8 de Petroski"
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

## 2. Definição Matemática

A equação P-F8 é:

```text
D =
    1.20263859
    - 0.05941591 × LOG10(X5)
    - 0.00037947 × AGE
    - 0.00058310 × CIRCUMFERENCE_THIGH
```

Onde:

```text
X5 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_ABDOMEN
    + SKINFOLD_MEDIAL_CALF
```

Saída:

```text
outputType:
    BODY_DENSITY
```

A forma computacional utiliza o logaritmo de base 10 da soma de cinco dobras cutâneas, além de `AGE` e `CIRCUMFERENCE_THIGH`. A configuração `X5` corresponde à soma de `SKINFOLD_SUBSCAPULAR`, `SKINFOLD_TRICEPS`, `SKINFOLD_SUPRAILIAC`, `SKINFOLD_ABDOMEN` e `SKINFOLD_MEDIAL_CALF` documentada para as equações femininas de Petroski (1995). A equação específica P-F8 é reproduzida com esses coeficientes em fontes secundárias que compilam as equações de Petroski.

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
            "Petroski (1995)"

    explicitAgeRestriction:
        null
```

A amostra feminina total do estudo compreendeu 281 mulheres, com idade entre 18 e 51 anos e média de 27,46 ± 7,58 anos. Destas, 213 foram utilizadas na regressão de desenvolvimento e 68 na validação independente. A média de idade é descritiva e não constitui limite de elegibilidade.

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
            "Amostra feminina heterogênea em idade e composição corporal."

        sampleCharacteristics:
            "Amostra feminina de regressão utilizada no desenvolvimento das equações generalizadas de Petroski."

        source:
            "Petroski (1995)"

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
            "Petroski (1995)"
```

A investigação original reuniu 281 mulheres das regiões central do Rio Grande do Sul e litorânea de Santa Catarina. O estudo utilizou pesagem hidrostática como método de referência para a densidade corporal e desenvolveu equações generalizadas para mulheres adultas. A descrição publicada do estudo confirma 281 mulheres, entre 18 e 51 anos, com média de 27,46 ± 7,58 anos, e uma amostra de 68 mulheres destinada à validação das equações desenvolvidas.

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

Não foi identificada, na documentação considerada para esta ficha, classificação explícita da amostra de desenvolvimento ou validação segundo a categoria operacional de atleta/não atleta da plataforma.

## 6. Aplicabilidade por Nível de Treinamento

```text
trainingLevel:
    supportedLevels:
        []

    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

Não há classificação suficiente no estudo para atribuir `SEDENTARY`, `RECREATIONAL`, `TRAINED`, `COMPETITIVE` ou `ELITE` como aplicabilidade formal da variante.

## 7. Aplicabilidade por Modalidade

```text
modality:
    supportedModalities:
        []

    notes:
        "Não documentado em termos de modalidades esportivas específicas."
```

Não foi identificada modalidade esportiva específica como condição formal de desenvolvimento ou validação da variante.

## 8. Características Corporais

```text
bodyCharacteristics:
    rules:
        []
```

Não foi identificada regra explícita de elegibilidade corporal específica para P-F8 além das características descritivas da população de desenvolvimento.

## 9. Evidências de Validação

### 9.1 Evidências de Desenvolvimento

```text
development:
    studyReference:
        citation:
            "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado. Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

        doi:
            null

        url:
            "https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/"

    population:
        "Mulheres adultas, n = 213, 18–51 anos"

    criterionMethod:
        "Densidade corporal determinada por pesagem hidrostática"

    year:
        1995

    metrics:
        R:
            0.830

        R2:
            0.688

        standardError:
            0.0071
```

As métricas de desenvolvimento fornecidas para P-F8 são `R = 0.830`, `R² = 0.688` e `EPE = 0.0071 g/ml`. A documentação bibliográfica da tese informa que foram propostas 16 equações generalizadas para cada sexo; para as mulheres, as correlações múltiplas variaram de 0,827 a 0,864 e os EPEs de 0,0064 a 0,0070 g/ml. A equação P-F8 pertence a esse conjunto feminino de equações generalizadas.

## 10. Validação

A amostra independente de validação foi:

```text
n:
    68 mulheres

age:
    18–43 anos
```

Método de referência:

```text
criterionMethod:
    "Pesagem hidrostática"
```

Para P-F8, foram reportados:

```text
correlation:
    0.731

constantError:
    0.00015 g/ml

totalError:
    0.0072 g/ml

standardErrorOfEstimate:
    0.0070 g/ml
```

A tabela de validação também relata densidade estimada média de `1.046233 ± 0.0094 g/ml`, `r = 0.731`, `t = 0.174`, `EC = 0.00015 g/ml`, `ET = 0.0072 g/ml` e `EPE = 0.0070 g/ml`.

A amostra de 68 mulheres pertence ao mesmo programa de pesquisa de Petroski (1995) e, portanto, é classificada como validação da variante, e não como validação cruzada ou externa.

## 11. Validação Cruzada

```text
crossValidationStudies:
    []
```

A amostra independente de 68 mulheres é registrada exclusivamente em `validationStudies`, pois faz parte do mesmo estudo original. Não foi incluída uma amostra de outro pesquisador ou de outro estudo como validação cruzada específica da P-F8 nesta ficha.

## 12. Validação Externa

```text
externalValidationStudies:
    []
```

Não foi identificada, para esta ficha, validação externa totalmente independente do programa original de Petroski que permita preencher este campo sem inferência adicional.

## 13. Requisitos de Medição

```text
inputs:
    requiredInputs:
        - AGE
        - SKINFOLD_SUBSCAPULAR
        - SKINFOLD_TRICEPS
        - SKINFOLD_SUPRAILIAC
        - SKINFOLD_ABDOMEN
        - SKINFOLD_MEDIAL_CALF
        - CIRCUMFERENCE_THIGH

    optionalInputs:
        []
```

Entradas matemáticas:

```text
AGE
X5
CIRCUMFERENCE_THIGH
```

Onde:

```text
X5 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_ABDOMEN
    + SKINFOLD_MEDIAL_CALF
```

A equação utiliza `LOG10(X5)`. `SEX` não integra `requiredInputs`, pois determina a aplicabilidade da variante feminina e permanece em `applicability.sex`.

Segundo a notação empregada na documentação de Petroski, a idade é expressa em anos, as dobras cutâneas em milímetros e as circunferências em centímetros. As definições gerais de unidades, precisão e faixas plausíveis permanecem em:

```text
/library/measurements
```

## 14. Restrições Científicas

```text
restrictions:
    []
```

Não foi identificada restrição científica adicional explícita que deva gerar exclusão da variante para P-F8.

A faixa etária de 18–51 anos descreve a população de desenvolvimento e não deve ser transformada automaticamente em uma regra adicional de elegibilidade além do que estiver formalmente definido pelo schema e pela política de aplicabilidade da plataforma.

A presença de `AGE` e `CIRCUMFERENCE_THIGH` é uma exigência matemática da equação. A aplicação de `LOG10(X5)` requer que `X5` seja um valor matematicamente válido para o logaritmo.

## 15. Conflito de Fonte

```text
sourceConflict:
    null
```

Não foi identificado conflito material entre fontes primárias ou bibliográficas confiáveis que justifique um `SourceConflictRecord` para os coeficientes, sexo, faixa etária ou população da P-F8. A diferença entre versões e conjuntos de equações da família Petroski não é tratada como conflito quando corresponde a variantes distintas dentro do conjunto original.

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
            "P-F8"

        familyId:
            "petroski"

        displayName:
            "Petroski F8 — Feminino"

        aliasNames:
            [
                "Petroski F8",
                "Equação F8 de Petroski"
            ]

    mathematicalDefinition:
        formula:
            "D = 1.20263859 - 0.05941591 × LOG10(X5) - 0.00037947 × AGE - 0.00058310 × CIRCUMFERENCE_THIGH"

        variables:
            X5:
                "SKINFOLD_SUBSCAPULAR + SKINFOLD_TRICEPS + SKINFOLD_SUPRAILIAC + SKINFOLD_ABDOMEN + SKINFOLD_MEDIAL_CALF"

        outputType:
            BODY_DENSITY

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
                    "Petroski (1995)"

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
                    "Amostra feminina heterogênea em idade e composição corporal."

                sampleCharacteristics:
                    "Amostra feminina de regressão utilizada no desenvolvimento das equações generalizadas de Petroski."

                source:
                    "Petroski (1995)"

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
                    "Petroski (1995)"

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
                    "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado. Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

                doi:
                    null

                url:
                    "https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/"

            population:
                "Mulheres adultas, n = 213, 18–51 anos"

            criterionMethod:
                "Pesagem hidrostática"

            year:
                1995

            metrics:
                R:
                    0.830

                R2:
                    0.688

                standardError:
                    0.0071

        validationStudies:
            - studyReference:
                citation:
                    "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado. Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

                doi:
                    null

                url:
                    "https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/"

              population:
                "Mulheres adultas, n = 68, 18–43 anos"

              criterionMethod:
                "Pesagem hidrostática"

              metrics:
                correlation:
                    0.731

                standardError:
                    0.0070

                meanDifference:
                    0.00015

                rmse:
                    null

                otherMetrics:
                    "EC = 0.00015 g/ml; ET = 0.0072 g/ml; EPE = 0.0070 g/ml."

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
            - SKINFOLD_SUPRAILIAC
            - SKINFOLD_ABDOMEN
            - SKINFOLD_MEDIAL_CALF
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
    nenhuma regra explícita

inputs:
    AGE
    + 5 dobras cutâneas
    + CIRCUMFERENCE_THIGH

output:
    BODY_DENSITY
```

A ausência de evidência específica para atleta, nível de treinamento ou modalidade não deve ser convertida automaticamente em uma exclusão da variante. Da mesma forma, `developmentSampleMeanAge = 27.46` é descritivo e não deve ser usado como limiar.

A P-F8 é a variante feminina logarítmica de cinco dobras de Petroski que incorpora `AGE` e `CIRCUMFERENCE_THIGH`, utilizando:

```text
X5 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_ABDOMEN
    + SKINFOLD_MEDIAL_CALF
```

A distinção em relação à P-F7 deve permanecer explícita no `SuggestionEngine`: ambas usam a mesma configuração `X5`, porém P-F7 e P-F8 possuem definições matemáticas diferentes. A P-F8 utiliza `LOG10(X5)` e `CIRCUMFERENCE_THIGH`, enquanto P-F7 utiliza outra forma matemática e outras variáveis. As variantes não devem ser fundidas por compartilharem o mesmo conjunto de cinco dobras.

## 19. Referência

```text
Reference:
    citation:
        "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado. Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

    doi:
        null

    url:
        "https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/"
```

Fonte bibliográfica adicional:

```text
citation:
    "Petroski EL, Pires-Neto CS. Validação de equações antropométricas para a estimativa da densidade corporal em mulheres. Revista Brasileira de Atividade Física & Saúde. 1995;1(2):65–73."

doi:
    "10.12820/rbafs.v.1n2p65-73"

url:
    "https://rbafs.org.br/RBAFS/article/view/470/0"
```