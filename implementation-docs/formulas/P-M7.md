# EquationVariantScientificProfile — P-M7

## 1. Identificação

```text
identity:

    variantId:

        "P-M7"

    familyId:

        "petroski"

    displayName:

        "Petroski M7 — Masculino"

    aliasNames:

        []
```

Referência principal:

```text
Petroski, E. L. (1995).

Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos.

Tese de Doutorado.

Universidade Federal de Santa Maria (UFSM),
Santa Maria, RS, Brasil.
```

A P-M7 corresponde à sétima variante masculina do conjunto de equações generalizadas de Petroski (1995).

## 2. Definição Matemática

A equação P-M7 é:

```text
D =

    1.10726863

    - 0.00081201 × X4

    + 0.00000212 × X4²

    - 0.00041761 × AGE
```

Onde:

```text
X4 =

    SKINFOLD_SUBSCAPULAR

    + SKINFOLD_TRICEPS

    + SKINFOLD_SUPRAILIAC

    + SKINFOLD_MEDIAL_CALF
```

Saída matemática:

```text
outputType:

    BODY_DENSITY
```

A equação utiliza a soma de quatro dobras cutâneas e sua forma quadrática, além da idade.

As quatro dobras são subescapular, tricipital, supra-ilíaca e panturrilha medial.

## 3. Aplicabilidade

### 3.1 Sexo

```text
supportedSexes:

    - MALE
```

A P-M7 integra o conjunto masculino das equações generalizadas desenvolvidas por Petroski.

### 3.2 Idade

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

      population:

        "Homens adultos da amostra independente de validação"

      source:

        Petroski (1995)

explicitAgeRestriction:

    null
```

A população de desenvolvimento compreendeu homens adultos de 18 a 66 anos, com média de idade de 30,17 anos. A amostra independente de validação compreendeu 87 homens de 18 a 56 anos.

A média de idade é descritiva e não constitui limite de elegibilidade.

## 4. Aplicabilidade Populacional

```text
originalPopulation:

    description:

        "Homens adultos"

    country:

        "Brazil"

    region:

        "Rio Grande do Sul / Santa Maria"

    sexCoverage:

        - MALE

    ageCoverage:

        min: 18

        max: 66

    sampleSize:

        304

    bodyCharacteristicsNotes:

        "População masculina adulta heterogênea em idade e composição corporal."

    sampleCharacteristics:

        "Amostra masculina de desenvolvimento das equações generalizadas de Petroski, com avaliação antropométrica e determinação da densidade corporal por pesagem hidrostática."

    source:

        Petroski (1995)

validationPopulations:

    - description:

        "Homens adultos da amostra independente de validação"

      country:

        "Brazil"

      region:

        "Rio Grande do Sul / Santa Maria"

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

        "Amostra independente utilizada para a validação das equações generalizadas."

      source:

        Petroski (1995)
```

A amostra masculina total do estudo foi de 391 participantes, distribuídos entre 304 homens utilizados no desenvolvimento/regressão das equações e 87 homens utilizados na validação independente. Essa separação é preservada na ficha para distinguir a amostra de desenvolvimento da amostra de validação.

## 5. Aplicabilidade em Atletas

```text
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

A fonte disponível não fornece classificação suficientemente explícita de atleta/não atleta que possa ser convertida com segurança para o modelo booleano da plataforma.

Nenhuma condição de atleta é inferida.

## 6. Aplicabilidade por Nível de Treinamento

```text
supportedLevels:

    []

notes:

    "Não documentado segundo a escala operacional da plataforma."
```

Não foi estabelecido mapeamento confiável para `SEDENTARY`, `RECREATIONAL`, `TRAINED`, `COMPETITIVE` ou `ELITE`.

## 7. Aplicabilidade por Modalidade

```text
supportedModalities:

    []

notes:

    "Não documentado em termos de modalidades esportivas específicas."
```

## 8. Características Corporais

```text
rules:

    []
```

Não foi identificada regra explícita de elegibilidade corporal específica da P-M7.

A composição da amostra de desenvolvimento é informação descritiva sobre a população estudada e não deve ser convertida em restrição adicional sem evidência explícita.

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

        "Homens adultos, n = 304, 18–66 anos"

    criterionMethod:

        "Densidade corporal determinada por pesagem hidrostática"

    year:

        1995

    metrics:

        R:

            0.875

        R2:

            0.765

        standardError:

            0.0075
```

Os indicadores de desenvolvimento registrados para a P-M7 são `R = 0,875`, `R² = 0,765` e erro padrão de estimativa de `0,0075 g/ml`.

A P-M7 utiliza quatro dobras cutâneas e idade, compondo uma das variantes generalizadas masculinas de Petroski.

## 10. Validação

A validação independente utilizou:

```text
n:

    87 men

age:

    18–56 years

criterionMethod:

    hydrostatic weighing
```

Para a P-M7, os resultados registrados são:

```text
correlation:

    0.861

meanDifference:

    -0.0001 g/ml

standardError:

    0.0078 g/ml

otherMetrics:

    "EC = -0.0001 g/ml; ET = 0.0078 g/ml; EPE = 0.0078 g/ml."
```

A amostra de 87 homens pertence ao mesmo estudo/tese de desenvolvimento de Petroski e, portanto, permanece classificada como `validationStudies`.

## 11. Validação Cruzada

```text
crossValidationStudies:

    []
```

A amostra independente de 87 homens é registrada como validação da própria série de equações de Petroski. Ela não é duplicada como `crossValidationStudies`.

Não foi incorporado nesta ficha teste da P-M7 contra dados, equação ou população de outro pesquisador como validação cruzada específica da variante.

## 12. Validação Externa

```text
externalValidationStudies:

    []
```

Nenhum estudo de validação externa independente foi incorporado nesta ficha.

O uso posterior da equação em outros trabalhos não é tratado automaticamente como validação externa sem caracterização explícita do desenho e da amostra.

## 13. Requisitos de Medição

```text
requiredInputs:

    - AGE

    - SKINFOLD_SUBSCAPULAR

    - SKINFOLD_TRICEPS

    - SKINFOLD_SUPRAILIAC

    - SKINFOLD_MEDIAL_CALF

optionalInputs:

    []
```

A forma matemática da equação é:

```text
AGE

X4

X4 =

    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_MEDIAL_CALF
```

`SEX` não é um `requiredInput`; ele determina a aplicabilidade da variante masculina.

As definições gerais de unidade, precisão e faixas plausíveis permanecem em:

```text
/library/measurements
```

## 14. Restrições Científicas

```text
restrictions:

    []
```

Não foi identificada restrição científica adicional explicitamente documentada que produza `INELIGIBLE`.

## 15. Conflito de Fonte

```text
sourceConflict:

    null
```

Não foi identificado conflito material de fonte que exija registro em `SourceConflictRecord` para a definição matemática ou para os dados de desenvolvimento e validação utilizados nesta ficha.

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

A variante permanece `ACTIVE` porque a definição matemática, a aplicabilidade sexual e etária, a população de desenvolvimento, os requisitos de entrada e a referência de definição estão documentados.

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:

            "P-M7"

        familyId:

            "petroski"

        displayName:

            "Petroski M7 — Masculino"

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

                    "Rio Grande do Sul / Santa Maria"

                sexCoverage:

                    - MALE

                ageCoverage:

                    min: 18

                    max: 66

                sampleSize:

                    304

                bodyCharacteristicsNotes:

                    "População masculina adulta heterogênea em idade e composição corporal."

                sampleCharacteristics:

                    "Amostra masculina de desenvolvimento das equações generalizadas de Petroski, com avaliação antropométrica e determinação da densidade corporal por pesagem hidrostática."

                source:

                    Petroski (1995)

            validationPopulations:

                - description:

                    "Homens adultos da amostra independente de validação"

                  country:

                    "Brazil"

                  region:

                    "Rio Grande do Sul / Santa Maria"

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

                    "Amostra independente utilizada para a validação das equações generalizadas."

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

                "Homens adultos, n = 304, 18–66 anos"

            criterionMethod:

                "Pesagem hidrostática"

            year:

                1995

            metrics:

                R:

                    0.875

                R2:

                    0.765

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

                        0.861

                    standardError:

                        0.0078

                    meanDifference:

                        -0.0001

                    rmse:

                        null

                    otherMetrics:

                        "EC = -0.0001 g/ml; ET = 0.0078 g/ml; EPE = 0.0078 g/ml."

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

O perfil científico permite ao motor considerar, em runtime:

```text
sex:

    MALE → compatível com o sexo documentado

age:

    comparar com a evidência populacional documentada

    sem usar 30.17 como limite de elegibilidade

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

    + 4 skinfolds

output:

    BODY_DENSITY
```

O motor não deve transformar:

```text
developmentSampleMeanAge = 30.17
```

em limite de elegibilidade.

A ausência de evidência sobre atleta, nível de treinamento ou modalidade também não deve ser convertida em `INELIGIBLE`.

A indicação de que a P-M7 possui configuração relativamente simples não deve ser convertida em regra fixa de recomendação sem suporte explícito no modelo de decisão do produto.

## 19. Referência

```text
Reference:

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

additionalBibliographicSources:

    - citation:

        "Centro Esportivo Virtual — Desenvolvimento e Validação de Equações Generalizadas Para a Estimativa da Densidade Corporal em Adultos."

      doi:

        null

      url:

        "https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/"
```