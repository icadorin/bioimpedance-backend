# EquationVariantScientificProfile — Petroski M1

## 1. Identificação

```text
identity:
    variantId:
        "P-M1"

    familyId:
        "petroski"

    displayName:
        "Petroski 9 Dobras — Masculino"

    aliasNames:
        []
```

A variante P-M1 corresponde à primeira equação generalizada de Petroski para homens adultos, utilizando nove dobras cutâneas e idade.

Referência principal:

```text
Petroski, E. L. (1995).

Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos.

Tese de Doutorado, Universidade Federal de Santa Maria, Santa Maria, RS, Brasil.
```

O trabalho desenvolveu equações generalizadas para estimativa da densidade corporal em homens e mulheres adultos. A ficha utiliza a variante M1 do conjunto masculino, correspondente ao modelo com nove dobras cutâneas e idade.

---

## 2. Definição Matemática

A definição matemática da variante é:

```text
D =
    1.10194032
    - 0.00031836 × X9
    + 0.00000029 × X9²
    - 0.00029542 × AGE
```

Onde:

```text
D
    = BODY_DENSITY

X9
    = soma de nove dobras cutâneas

AGE
    = idade em anos
```

A composição de X9 é:

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

### Forma computacional

```text
INPUTS:
    AGE
    SKINFOLD_SUBSCAPULAR
    SKINFOLD_TRICEPS
    SKINFOLD_BICEPS
    SKINFOLD_PECTORAL
    SKINFOLD_AXILLARY_MID
    SKINFOLD_SUPRAILIAC
    SKINFOLD_ABDOMEN
    SKINFOLD_THIGH
    SKINFOLD_MEDIAL_CALF

INTERMEDIATE:
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

OUTPUT:
    BODY_DENSITY =
        1.10194032
        - 0.00031836 × X9
        + 0.00000029 × X9²
        - 0.00029542 × AGE
```

```text
outputType = BODY_DENSITY
```

A equação pertence ao grupo de modelos quadráticos com soma de nove dobras cutâneas e idade.

---

## 3. Aplicabilidade

### 3.1 Sexo

```text
sex:
    supportedSexes:
        - MALE
```

A equação M1 foi desenvolvida na amostra masculina de Petroski.

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
            "Petroski (1995)"

    explicitAgeRestriction:
        null
```

A amostra masculina total tinha 391 participantes, com idade de 18–66 anos e média de 30,17 ± 9,78 anos. Para o desenvolvimento das equações foram utilizados 304 homens; a amostra independente de validação continha 87 homens, com idade de 18–56 anos e média de 30,68 ± 9,11 anos.

A média de idade é descritiva e não deve ser interpretada como limite de elegibilidade.

---

## 4. Aplicabilidade Populacional

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
            "A amostra de desenvolvimento apresentou percentual de gordura de
             aproximadamente 2,20% a 33,16%, com média de 16,14% ± 6,86%."

        sampleCharacteristics:
            "Adultos voluntários, sem aparentes problemas de saúde, predominantemente
             sedentários ou praticantes de atividades físicas regulares sem caráter
             competitivo, além de atletas amadores de nível universitário ao municipal."

        source:
            "Petroski (1995)"

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
            "Percentual de gordura médio de 15,83% ± 6,72%; faixa de 3,11% a 33,12%."

          sampleCharacteristics:
            "Subamostra masculina independente utilizada para validação das equações."

          source:
            "Petroski (1995)"
```

A delimitação geográfica do estudo corresponde à região central do Rio Grande do Sul e à região litorânea de Santa Catarina.

A amostra foi composta por voluntários e apresentou participantes sedentários, praticantes de atividade física regular sem caráter competitivo e atletas amadores de diferentes níveis de participação. Essas descrições permanecem como características da população e não são convertidas, por inferência, em categorias operacionais de treinamento ou modalidade.

---

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

Apesar de a descrição geral da amostra mencionar participantes sedentários, praticantes de atividades físicas e atletas amadores, a fonte não fornece uma classificação operacional suficientemente específica para converter essas categorias diretamente nos campos booleanos da plataforma.

Portanto, não deve ser inferido `true` ou `false` para a aplicabilidade em atletas.

---

## 6. Aplicabilidade por Nível de Treinamento

```text
trainingLevel:
    supportedLevels:
        []

    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

A descrição da atividade física da amostra não fornece correspondência suficientemente precisa com:

```text
SEDENTARY
RECREATIONAL
TRAINED
COMPETITIVE
ELITE
```

Não deve ser feito esse mapeamento por inferência.

---

## 7. Aplicabilidade por Modalidade

```text
modality:
    supportedModalities:
        []

    notes:
        "Não documentado em termos de modalidades esportivas específicas."
```

O estudo não associa a equação M1 a uma modalidade esportiva específica.

---

## 8. Características Corporais

```text
bodyCharacteristics:
    rules:
        []
```

A ampla variação de percentual de gordura observada na amostra é uma característica descritiva da população, não uma regra explícita de aplicabilidade da variante.

Portanto, não serão criadas regras artificiais para `LOW_BODY_FAT`, `HIGH_BODY_FAT`, `OBESITY` ou outras categorias.

---

## 9. Evidências de Validação

### 9.1 Evidências de Desenvolvimento

```text
development:
    studyReference:
        citation:
            "Petroski, E. L. (1995). Desenvolvimento e validação de equações
             generalizadas para a estimativa da densidade corporal em adultos.
             Tese de Doutorado, Universidade Federal de Santa Maria, Santa Maria."

        doi:
            null

        url:
            "http://www.nucidh.ufsc.br/teses/tese_edio.pdf"

    population:
        "Homens adultos, n = 304, 18–66 anos"

    criterionMethod:
        "Densidade corporal determinada por pesagem hidrostática"

    year:
        1995

    metrics:
        R:
            null

        R²:
            null

        standardError:
            null
```

O desenvolvimento foi realizado por regressão múltipla Stepwise com seleção Forward, tendo a densidade corporal determinada por pesagem hidrostática como critério de referência. A tese apresenta resultados de desenvolvimento para o conjunto das equações masculinas, mas não foi encontrada, nas fontes consultadas, evidência suficiente para atribuir métricas agregadas especificamente à variante M1. Por isso, `R`, `R²` e `standardError` permanecem `null`.

---

## 10. Validação

```text
validationStudies:
    - studyReference:
        citation:
            "Petroski, E. L. (1995). Desenvolvimento e validação de equações
             generalizadas para a estimativa da densidade corporal em adultos."

        doi:
            null

        url:
            "http://www.nucidh.ufsc.br/teses/tese_edio.pdf"

      population:
        description:
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
            "Percentual de gordura médio de 15,83% ± 6,72%; faixa de 3,11% a 33,12%."

        sampleCharacteristics:
            "Subamostra masculina independente utilizada para validação das equações."

        source:
            "Petroski (1995)"

      criterionMethod:
          "Densidade corporal determinada por pesagem hidrostática"

      metrics:
          correlation:
              null

          standardError:
              null

          meanDifference:
              null

          rmse:
              null

          otherMetrics:
              "A tese reporta resultados de correlação, teste t pareado, erro constante,
               erro total e erro padrão de estimativa para o conjunto das equações
               desenvolvidas, mas a fonte consultada não permite atribuir esses valores
               de forma específica à M1."

      limitations:
          "A validação foi realizada em subamostra da mesma população de origem;
           não corresponde a validação externa independente."
```

A amostra independente de 87 homens pertence ao mesmo estudo de desenvolvimento de Petroski (1995) e, portanto, deve permanecer em `validationStudies`, e não em `crossValidationStudies`.

Como não há suporte específico para atribuir métricas agregadas à M1, os campos métricos específicos permanecem `null`.

---

## 11. Validação Cruzada

```text
crossValidationStudies:
    []
```

A tese também realizou análises de validação cruzada, porém esta ficha não cadastra um estudo de validação cruzada específico para M1 sem população, comparação e métricas claramente atribuíveis à variante.

---

## 12. Validação Externa

```text
externalValidationStudies:
    []
```

Não foi cadastrada validação externa independente para esta variante.

---

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
        - SKINFOLD_ABDOMEN
        - SKINFOLD_THIGH
        - SKINFOLD_MEDIAL_CALF

    optionalInputs:
        []
```

A idade é variável matemática da equação e, por isso, `AGE` permanece em `requiredInputs`.

`SEX` permanece exclusivamente como informação de aplicabilidade da variante e não é repetido em `requiredInputs`.

A variante utiliza nove dobras cutâneas:

```text
SE
TR
BI
PT
AM
SI
AB
CX
PM
```

Os identificadores canônicos utilizados pela ficha são `SKINFOLD_PECTORAL` e `SKINFOLD_AXILLARY_MID`.

---

## 14. Restrições Científicas

```text
restrictions:
    []
```

Não foi cadastrada restrição científica explícita adicional para a M1.

A faixa de 18–66 anos representa a faixa observada na população de desenvolvimento e não é transformada em uma restrição adicional independente da aplicabilidade documentada.

---

## 15. Conflito de Fonte

```text
sourceConflict:
    null
```

Não foi identificado conflito material na definição matemática da M1 entre as fontes consultadas.

Diferenças de apresentação entre a tese original e fontes secundárias não são tratadas como `sourceConflict` quando não demonstram dois valores primários conflitantes para a mesma variante.

A ficha utiliza a variante M1 como identificada no conjunto masculino de Petroski (1995).

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

A variante possui os elementos essenciais:

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

Não há campo essencial com valor `TBD` que impeça o cadastro como `ACTIVE` dentro da biblioteca científica.

---

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile
    identity:
        variantId:
            "P-M1"

        familyId:
            "petroski"

        displayName:
            "Petroski 9 Dobras — Masculino"

        aliasNames:
            []

    mathematicalDefinition:
        formula:
            "D = 1.10194032 - 0.00031836 × X9 + 0.00000029 × X9² - 0.00029542 × AGE"

        outputType:
            BODY_DENSITY

        variables:
            X9:
                "SKINFOLD_SUBSCAPULAR + SKINFOLD_TRICEPS + SKINFOLD_BICEPS + SKINFOLD_PECTORAL + SKINFOLD_AXILLARY_MID + SKINFOLD_SUPRAILIAC + SKINFOLD_ABDOMEN + SKINFOLD_THIGH + SKINFOLD_MEDIAL_CALF"

            AGE:
                "Idade em anos"

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
                    "Petroski (1995)"

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
                "Percentual de gordura de aproximadamente 2,20% a 33,16%; média de 16,14% ± 6,86%."

            sampleCharacteristics:
                "Voluntários sem aparentes problemas de saúde, predominantemente sedentários
                 ou praticantes de atividade física regular sem caráter competitivo, além de
                 atletas amadores de nível universitário ao municipal."

            source:
                "Petroski (1995)"

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
                "Percentual de gordura médio de 15,83% ± 6,72%; faixa de 3,11% a 33,12%."

              sampleCharacteristics:
                "Subamostra masculina independente utilizada para validação."

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
                    "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado, Universidade Federal de Santa Maria, Santa Maria."

                doi:
                    null

                url:
                    "http://www.nucidh.ufsc.br/teses/tese_edio.pdf"

            population:
                "Homens adultos, n = 304, 18–66 anos"

            criterionMethod:
                "Densidade corporal determinada por pesagem hidrostática"

            year:
                1995

            metrics:
                R:
                    null

                R²:
                    null

                standardError:
                    null

        validationStudies:
            - studyReference:
                citation:
                    "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos."

                doi:
                    null

                url:
                    "http://www.nucidh.ufsc.br/teses/tese_edio.pdf"

              population:
                "Homens adultos, n = 87, 18–56 anos"

              criterionMethod:
                "Densidade corporal determinada por pesagem hidrostática"

              metrics:
                correlation:
                    null

                standardError:
                    null

                meanDifference:
                    null

                rmse:
                    null

                otherMetrics:
                    "Resultados agregados do conjunto das equações masculinas; não atribuídos individualmente à M1."

              limitations:
                  "Validação realizada em subamostra da mesma população de origem."

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

---

## 18. Interpretação para o SuggestionEngine

```text
sex:
    MALE → compatível
    FEMALE → incompatível

age:
    development population = 18–66
    mean age = 30.17
    mean age is NOT a limit

athlete:
    NOT_DOCUMENTED

trainingLevel:
    NOT_DOCUMENTED

modality:
    NOT_DOCUMENTED

inputs:
    AGE + 9 skinfolds

output:
    BODY_DENSITY
```

A compatibilidade por sexo é determinada pela aplicabilidade da variante. A média de idade é exclusivamente descritiva e não deve ser utilizada como limite de elegibilidade.

A presença de participantes sedentários, fisicamente ativos e atletas amadores na população não deve gerar automaticamente uma correspondência com níveis de treinamento ou modalidades esportivas.

---

## 19. Referência

```text
Reference
    citation:
        "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado. Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

    doi:
        null

    url:
        "http://www.nucidh.ufsc.br/teses/tese_edio.pdf"
```