**# EquationVariantScientificProfile — P-M6**

## 1. Identificação

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

Referência principal:

```text
Petroski, E. L. (1995).

Desenvolvimento e validação de equações generalizadas para a estimativa
da densidade corporal em adultos.

Tese de Doutorado em Educação Física.

Universidade Federal de Santa Maria (UFSM),
Santa Maria, RS, Brasil.
```

A P-M6 corresponde à sexta variante masculina das equações generalizadas de Petroski (1995).

---

## 2. Definição Matemática

A variante P-M6 utiliza seis dobras cutâneas, idade e as circunferências do antebraço e abdômen.

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
outputType:

    BODY_DENSITY
```

A definição acima corresponde à equação M6 da Tabela 9 da tese de Petroski.

A forma computacional utiliza `X6` como a soma das seis dobras cutâneas, além de `AGE`, `CIRCUMFERENCE_FOREARM` e `CIRCUMFERENCE_ABDOMEN`.

---

## 3. Aplicabilidade

### 3.1 Sexo

```text
supportedSexes:

    - MALE
```

A equação M6 pertence ao conjunto de equações generalizadas desenvolvido para homens adultos.

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

A idade média da amostra de desenvolvimento foi de 30,17 anos.

A média de idade é descritiva e não deve ser utilizada como limite de elegibilidade.

---

## 4. Aplicabilidade Populacional

```text
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

        "Amostra de validação independente oriunda da mesma população estudada."

      source:

        Petroski (1995)
```

A amostra masculina total do estudo compreendeu 391 participantes. Para a documentação da variante, a população de desenvolvimento é registrada como os 304 homens usados no desenvolvimento das equações, enquanto os 87 homens da amostra independente permanecem separados como população de validação.

---

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

A descrição da população não foi convertida artificialmente para os valores booleanos operacionais de atleta/não atleta.

---

## 6. Aplicabilidade por Nível de Treinamento

```text
supportedLevels:

    []

notes:

    "Não documentado segundo a escala operacional da plataforma."
```

Não foi estabelecido mapeamento confiável para `SEDENTARY`, `RECREATIONAL`, `TRAINED`, `COMPETITIVE` ou `ELITE`.

---

## 7. Aplicabilidade por Modalidade

```text
supportedModalities:

    []

notes:

    "Não documentado em termos de modalidades esportivas específicas."
```

---

## 8. Características Corporais

```text
rules:

    []
```

As características gerais da amostra não foram transformadas em restrições corporais específicas sem documentação explícita.

A utilização de seis dobras cutâneas e duas circunferências é uma característica matemática da equação e não constitui, por si só, uma restrição adicional de composição corporal.

---

## 9. Evidências de Validação

### 9.1 Evidências de Desenvolvimento

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

        R:

            0.889

        R2:

            0.790

        standardError:

            0.0071
```

Na Tabela 9 da tese, a M6 apresenta:

```text
R:

    0.889

R²:

    0.790

EPE:

    0.0071 g/ml
```

Essas métricas permanecem dentro do objeto `metrics`, conforme o padrão da ficha científica.

---

## 10. Validação

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

    rmse:

        null

    otherMetrics:

        "EC = -0.0004 g/ml; ET = 0.0078 g/ml; EPE = 0.0074 g/ml."

    limitations:

        null
```

A tabela de validação também apresenta densidade estimada média de:

```text
1.06240 ± 0.013 g/ml
```

A diferença entre a densidade mensurada e estimada para a M6 não foi estatisticamente significativa (`p > 0,05`).

A amostra de 87 homens pertence ao mesmo estudo/tese que originou as equações e, portanto, é registrada como `validationStudies`, não como validação cruzada.

---

## 11. Validação Cruzada

```text
crossValidationStudies:

    []
```

A validação da amostra independente de 87 homens é registrada na seção 10.

Não foi identificado, para esta variante, estudo adicional de validação cruzada contra uma equação ou população de outro pesquisador que deva ser registrado nesta seção.

---

## 12. Validação Externa

```text
externalValidationStudies:

    []
```

Não foi cadastrada validação externa independente para a P-M6.

---

## 13. Requisitos de Medição

```text
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

A forma matemática utiliza:

```text
AGE

X6

CIRCUMFERENCE_FOREARM

CIRCUMFERENCE_ABDOMEN
```

com:

```text
X6 =

    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_BICEPS
    + SKINFOLD_PECTORAL
    + SKINFOLD_AXILLARY_MID
    + SKINFOLD_SUPRAILIAC
```

`SEX` não é incluído em `requiredInputs`; ele determina a aplicabilidade da variante masculina.

As propriedades gerais de unidade, precisão e faixas plausíveis permanecem definidas no `InputTypeCatalog` e em `/library/measurements`.

---

## 14. Restrições Científicas

```text
restrictions:

    []
```

A faixa de 18–66 anos corresponde à população de desenvolvimento e não foi convertida em `ScientificRestriction`.

Nenhuma restrição científica adicional explicitamente documentada foi cadastrada para a P-M6.

---

## 15. Conflito de Fonte

```text
sourceConflict:

    null
```

A definição matemática da M6 e as métricas registradas para desenvolvimento e validação são atribuídas à tese de Petroski (1995).

Não foi identificado conflito material entre essas informações na fonte principal utilizada para esta ficha.

---

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

                      "Amostra de validação independente oriunda da mesma população estudada."

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

                R:

                    0.889

                R2:

                    0.790

                standardError:

                    0.0071

        validationStudies:

            - studyReference:

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

                "87 homens adultos, 18–56 anos"

              criterionMethod:

                "Pesagem hidrostática"

              metrics:

                correlation:

                    0.858

                standardError:

                    0.0074

                meanDifference:

                    null

                rmse:

                    null

                otherMetrics:

                    "EC = -0.0004 g/ml; ET = 0.0078 g/ml; EPE = 0.0074 g/ml."

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

A ficha permite ao motor interpretar a P-M6 sem conhecimento científico específico hardcoded da variante:

```text
sex:

    MALE

        → compatível

age:

    comparar com a evidência populacional documentada

        → sem usar 30.17 como limite de elegibilidade

athlete:

    NOT_DOCUMENTED

trainingLevel:

    NOT_DOCUMENTED

modality:

    NOT_DOCUMENTED

bodyCharacteristics:

    sem regra explícita

requiredInputs:

    AGE

    + 6 skinfolds

    + forearm circumference

    + abdominal circumference

output:

    BODY_DENSITY
```

As métricas:

```text
R:

    0.889

R²:

    0.790

EPE:

    0.0071 g/ml
```

são evidências científicas da variante e não correspondem a `evidenceCoverage` em tempo de execução.

A validação independente apresentou:

```text
r:

    0.858

EC:

    -0.0004 g/ml

ET:

    0.0078 g/ml

EPE:

    0.0074 g/ml
```

O motor deve continuar distinguindo essas métricas documentadas dos estados derivados de aplicabilidade.

---

## 19. Referência

```text
Reference:

    citation:

        "Petroski, E. L. (1995).

         Desenvolvimento e validação de equações generalizadas para a

         estimativa da densidade corporal em adultos.

         Tese de Doutorado em Educação Física.

         Universidade Federal de Santa Maria,

         Santa Maria, RS, Brasil."

    doi:

        null

    url:

        "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"
```

Fonte bibliográfica adicional:

```text
Reference:

    citation:

        "Petroski, E. L.; Pires Neto, C. S.

         Validação de equações antropométricas para a estimativa da densidade

         corporal em homens.

         Revista Brasileira de Atividade Física & Saúde."

    doi:

        null

    url:

        "https://rbafs.org.br/RBAFS/article/view/496"
```