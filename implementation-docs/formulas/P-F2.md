# EquationVariantScientificProfile — P-F2

## 1. Identificação

```text
identity:
    variantId:
        "P-F2"
    familyId:
        "petroski"
    displayName:
        "Petroski F2 — Feminino"
    aliasNames:
        [
            "Petroski F2",
            "Equação F2 de Petroski"
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

A equação P-F2 é:

```text
D =
    1.21630958
    - 0.07522765 × LOG10(X9)
    - 0.00032901 × AGE
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

Saída:

```text
outputType:
    BODY_DENSITY
```

A equação é um modelo logarítmico que utiliza o logaritmo de base 10 da soma de nove dobras cutâneas, juntamente com a idade. A configuração `X9` corresponde à soma das nove dobras utilizadas na variante feminina P-F2.

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

A amostra feminina de regressão continha 213 mulheres, com idades de 18 a 51 anos e média de 27,46 anos. A amostra independente de validação continha 68 mulheres, com idades de 18 a 43 anos e média de 27,18 anos.

A média etária é descritiva e não constitui limite de elegibilidade.

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

O estudo reuniu 281 mulheres, sendo 213 destinadas à regressão e 68 à validação independente. A descrição do estudo caracteriza a amostra feminina como heterogênea em idade e composição corporal.

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

Não há classificação explícita suficiente da amostra de desenvolvimento ou validação de P-F2 como atleta ou não atleta compatível com o modelo booleano da plataforma.

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

Não foi identificada regra explícita de elegibilidade baseada em característica corporal específica para P-F2. As características observadas na amostra descrevem a população de desenvolvimento e não são convertidas automaticamente em restrições rígidas de execução.

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
            "https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/"
    population:
        "Mulheres adultas, n = 213, 18–51 anos"
    criterionMethod:
        "Densidade corporal determinada por pesagem hidrostática"
    year:
        1995
    metrics:
        R:
            0.834
        R2:
            0.695
        standardError:
            0.0070
```

Para P-F2, foram fornecidos os valores `R = 0,834`, `R² = 0,695` e erro padrão de estimativa de `0,0070 g/ml` na amostra de regressão.

A ficha mantém a equação P-F2 e suas métricas conforme a documentação fornecida para a variante. A fonte institucional confirma que a tese de Petroski desenvolveu 16 equações generalizadas para cada sexo, com 281 mulheres no conjunto feminino, e informa que a validação feminina utilizou 68 mulheres.

## 10. Validação

A amostra independente de validação foi:

```text
n:
    68 women
age:
    18–43 years
```

Método de critério:

```text
Pesagem hidrostática
```

Para P-F2:

```text
correlation:
    0.718
constantError:
    -0.00060 g/ml
totalError:
    0.0072 g/ml
standardErrorOfEstimate:
    0.0071 g/ml
```

A tabela de validação documentada para P-F2 apresenta densidade estimada média de `1.045786 ± 0.0091 g/ml`, `r = 0.718`, `t = -0.682`, `EC = -0.00060 g/ml`, `ET = 0.0072 g/ml` e `EPE = 0.0071 g/ml`.

A amostra de 68 mulheres pertence à validação das equações femininas dentro do estudo original e, portanto, permanece classificada em `validationStudies`. A fonte institucional também confirma a utilização de 68 mulheres na etapa de validação das equações desenvolvidas.

## 11. Validação Cruzada

```text
crossValidationStudies:
    []
```

A amostra independente de 68 mulheres está representada em `Validation` e não é duplicada em `Cross-validation`.

A tese possui análise separada de validação cruzada envolvendo equações de outros investigadores; isso não constitui uma nova validação cruzada específica da variante P-F2.

## 12. Validação Externa

```text
externalValidationStudies:
    []
```

Não foi incluído neste perfil estudo de validação externa de P-F2 realizado fora da investigação original de Petroski.

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

Entradas matemáticas:

```text
AGE
X9
```

onde:

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

e:

```text
LOG10(X9)
```

é utilizado na equação matemática.

`SEX` não aparece em `requiredInputs`; ele determina a aplicabilidade da variante feminina.

Segundo a notação utilizada na ficha, a idade é expressa em anos e as dobras cutâneas em milímetros.

Definições gerais de unidades, precisão e faixas plausíveis permanecem em:

```text
/library/measurements
```

## 14. Restrições Científicas

```text
restrictions:
    []
```

Não foi identificada restrição científica adicional explícita que gere estado de inelegibilidade para P-F2.

A faixa de 18 a 51 anos caracteriza a amostra de desenvolvimento e não é convertida automaticamente em limite rígido de execução.

A disponibilidade de `AGE` e das nove dobras que compõem `X9` é requisito matemático para calcular a equação.

## 15. Conflito de Fonte

```text
sourceConflict:
    null
```

Não foi identificado conflito material entre fontes primárias legítimas para a definição matemática ou para os dados de desenvolvimento e validação apresentados na ficha.

A fonte institucional consultada confirma a existência da tese, a população feminina de 281 participantes e a utilização de 68 mulheres na validação, mas não reproduz na página resumida os coeficientes individuais de P-F2. Por isso, os coeficientes da ficha são preservados conforme a documentação fornecida, sem atribuir à página institucional uma transcrição que ela não apresenta.

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
identity                      CONFIRMED
mathematicalDefinition        CONFIRMED
supportedSexes                CONFIRMED
originalDevelopmentAgeRange   CONFIRMED
originalPopulation            CONFIRMED
requiredInputs                CONFIRMED
definitionReference           CONFIRMED
sourceConflict                null
```

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile
    identity:
        variantId:
            "P-F2"
        familyId:
            "petroski"
        displayName:
            "Petroski F2 — Feminino"
        aliasNames:
            [
                "Petroski F2",
                "Equação F2 de Petroski"
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
                "Não documentado segundo a escala operacional."
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
                citation:
                    "Petroski, E. L. (1995).
                     Desenvolvimento e validação de equações generalizadas
                     para a estimativa da densidade corporal em adultos.
                     Tese de Doutorado.
                     Universidade Federal de Santa Maria."
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
                R: 0.834
                R2: 0.695
                standardError: 0.0070
        validationStudies:
            - studyReference:
                citation:
                    "Petroski, E. L. (1995).
                     Desenvolvimento e validação de equações generalizadas
                     para a estimativa da densidade corporal em adultos.
                     Tese de Doutorado.
                     Universidade Federal de Santa Maria."
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
                    0.718
                standardError:
                    0.0071
                meanDifference:
                    -0.00060
                rmse:
                    null
                otherMetrics:
                    "EC = -0.00060 g/ml; ET = 0.0072 g/ml;
                     EPE = 0.0071 g/ml."
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

O perfil científico permite avaliação em runtime de:

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
    + 9 skinfolds

output:
    BODY_DENSITY
```

O motor não deve transformar a média etária de desenvolvimento em limite de elegibilidade, e a ausência de evidência sobre atleta, nível de treinamento ou modalidade não deve resultar em estado de inelegibilidade.

P-F2 é a variante feminina logarítmica de nove dobras cutâneas + idade da série Petroski, utilizando:

```text
X9 =
    subscapular
    + triceps
    + biceps
    + pectoral
    + axillary mid
    + suprailiac
    + abdomen
    + thigh
    + medial calf
```

A equação requer `AGE` e as nove dobras que compõem `X9`. A padronização operacional utiliza `SKINFOLD_AXILLARY_MID` e `SKINFOLD_ABDOMEN` como identificadores canônicos.

A `SuggestionEngine` deve manter P-F2 distinta de P-F1, pois as duas variantes possuem definições matemáticas diferentes, mesmo quando compartilham a mesma base de nove dobras.

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
        "https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/"
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
