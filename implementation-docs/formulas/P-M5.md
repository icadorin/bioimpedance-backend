# EquationVariantScientificProfile — P-M5

## 1. Identificação

```text
identity:

    variantId:

        "P-M5"

    familyId:

        "petroski"

    displayName:

        "Petroski M5 — Masculino"

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

A P-M5 corresponde à quinta variante masculina do conjunto de equações generalizadas de Petroski (1995).

## 2. Definição Matemática

A equação P-M5 é:

```text
D =

    1.09995680

    - 0.00055475 × X6

    + 0.00000107 × X6²

    - 0.00023367 × AGE
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

Saída matemática:

```text
outputType:

    BODY_DENSITY
```

A equação utiliza a soma de seis dobras cutâneas, o quadrado dessa soma e a idade.

As seis dobras são subescapular, tricipital, bicipital, peitoral, axilar média e supra-ilíaca.

## 3. Aplicabilidade

### 3.1 Sexo

```text
supportedSexes:

    - MALE
```

A P-M5 integra o conjunto masculino das equações generalizadas desenvolvidas por Petroski.

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

A média de idade da amostra de desenvolvimento é descritiva e não constitui limite de elegibilidade.

## 4. Aplicabilidade Populacional

```text
originalPopulation:

    description:

        "Homens adultos"

    country:

        "Brazil"

    region:

        "Região central do Rio Grande do Sul e litoral de Santa Catarina"

    sexCoverage:

        - MALE

    ageCoverage:

        min: 18

        max: 66

    sampleSize:

        304

    bodyCharacteristicsNotes:

        "A amostra apresentou heterogeneidade de idade e percentual de gordura."

    sampleCharacteristics:

        "Homens adultos utilizados no desenvolvimento das equações generalizadas de Petroski, com avaliação antropométrica e determinação da densidade corporal por pesagem hidrostática."

    source:

        Petroski (1995)

validationPopulations:

    - description:

        "Homens adultos da amostra independente de validação"

      country:

        "Brazil"

      region:

        "Região central do Rio Grande do Sul e litoral de Santa Catarina"

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

        "Amostra independente utilizada para validar as equações generalizadas."

      source:

        Petroski (1995)
```

A amostra masculina total do estudo foi de 391 participantes. Para o desenvolvimento das equações, foram utilizados 304 homens, enquanto 87 homens compuseram a amostra independente de validação.

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

A descrição disponível da população não permite classificá-la com segurança segundo o modelo operacional atleta/não atleta.

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

A heterogeneidade corporal descrita para a amostra de desenvolvimento é uma característica populacional e não foi convertida em regra específica de elegibilidade da P-M5.

## 9. Evidências de Validação

### 9.1 Evidências de Desenvolvimento

```text
development:

    studyReference:

        citation:

            "Petroski, E. L. (1995).

             Desenvolvimento e validação de equações generalizadas para

             a estimativa da densidade corporal em adultos.

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

            0.881

        R2:

            0.776

        standardError:

            0.0074
```

Na Tabela 9 da tese, a P-M5 apresenta `R = 0,881`, `R² = 0,776` e erro padrão de estimativa de `0,0074 g/ml`.

## 10. Validação

A validação independente utilizou:

```text
sampleSize:

    87

population:

    "Homens adultos, 18–56 anos"

criterionMethod:

    "Pesagem hidrostática"

studyReference:

    "Petroski (1995)"
```

Resultados da P-M5:

```text
correlation:

    0.853

meanDifference:

    -0.0005 g/ml

constantError:

    -0.0005 g/ml

totalError:

    0.0079 g/ml

standardErrorOfEstimate:

    0.0076 g/ml
```

O teste t associado à comparação entre a densidade mensurada e a densidade estimada para a P-M5 foi `-0,546`. A diferença média não foi estatisticamente significativa.

Esses resultados pertencem à amostra independente de 87 homens da própria tese de Petroski e, portanto, são registrados como `validationStudies`.

## 11. Validação Cruzada

```text
crossValidationStudies:

    []
```

A amostra de 87 homens pertence ao mesmo estudo/tese que originou a P-M5 e está classificada como validação na seção 10. Ela não deve ser duplicada em `crossValidationStudies`.

A tese possui uma análise de validação cruzada destinada a testar equações desenvolvidas por outros pesquisadores na amostra de desenvolvimento. Essa análise não constitui validação cruzada específica da P-M5 e, portanto, não é incorporada como estudo da variante.

## 12. Validação Externa

```text
externalValidationStudies:

    []
```

Não foi identificado nesta ficha um estudo totalmente independente utilizado para validação externa da P-M5.

## 13. Requisitos de Medição

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

    optionalInputs:

        []
```

A forma matemática da equação é:

```text
AGE

X6

X6 =

    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_BICEPS
    + SKINFOLD_PECTORAL
    + SKINFOLD_AXILLARY_MID
    + SKINFOLD_SUPRAILIAC
```

`SEX` não é um `requiredInput`; ele permanece exclusivamente em `applicability.sex`.

As definições gerais de unidade, precisão e faixas plausíveis permanecem em:

```text
/library/measurements
```

## 14. Restrições Científicas

```text
restrictions:

    []
```

Não foi identificada restrição científica adicional explicitamente documentada que deva produzir `INELIGIBLE` para a P-M5.

## 15. Conflito de Fonte

```text
sourceConflict:

    null
```

Não foi identificado conflito material entre fontes primárias para a definição matemática da P-M5 e os dados de desenvolvimento e validação utilizados nesta ficha.

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

A variante permanece `ACTIVE`, pois os campos científicos essenciais estão documentados.

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:

            "P-M5"

        familyId:

            "petroski"

        displayName:

            "Petroski M5 — Masculino"

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

                    "Região central do Rio Grande do Sul e litoral de Santa Catarina"

                sexCoverage:

                    - MALE

                ageCoverage:

                    min: 18

                    max: 66

                sampleSize:

                    304

                bodyCharacteristicsNotes:

                    "A amostra apresentou heterogeneidade de idade e percentual de gordura."

                sampleCharacteristics:

                    "Homens adultos utilizados no desenvolvimento das equações generalizadas de Petroski, com avaliação antropométrica e determinação da densidade corporal por pesagem hidrostática."

                source:

                    Petroski (1995)

            validationPopulations:

                - description:

                    "Homens adultos da amostra independente de validação"

                  country:

                    "Brazil"

                  region:

                    "Região central do Rio Grande do Sul e litoral de Santa Catarina"

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

                      "Amostra independente utilizada para validar as equações generalizadas."

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

                    0.881

                R2:

                    0.776

                standardError:

                    0.0074

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

                        0.853

                    standardError:

                        0.0076

                    meanDifference:

                        -0.0005

                    rmse:

                        null

                    otherMetrics:

                        "EC = -0.0005 g/ml; ET = 0.0079 g/ml; t = -0.546."

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

            - SKINFOLD_PECTORAL

            - SKINFOLD_AXILLARY_MID

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

O perfil científico permite ao motor interpretar a P-M5 com base nos campos documentados:

```text
sex:

    MALE → compatível com a aplicabilidade documentada

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

    + 6 skinfolds

output:

    BODY_DENSITY
```

O motor não deve transformar `developmentSampleMeanAge = 30.17` em limite de elegibilidade.

A ausência de documentação sobre atleta, nível de treinamento ou modalidade também não deve ser convertida em `INELIGIBLE`.

A classificação da amostra de 87 homens como validação, e não validação cruzada, deve ser preservada no runtime porque se trata de uma subamostra do mesmo estudo original.

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

        "Petroski, E. L.; Pires Neto, C. S. Validação de equações antropométricas para a estimativa da densidade corporal em homens. Revista Brasileira de Atividade Física & Saúde, v. 1, n. 3, p. 5–14, 1996."

      doi:

        "10.12820/rbafs.v.1n3p5-14"

      url:

        "https://rbafs.org.br/RBAFS/article/view/496"
```
