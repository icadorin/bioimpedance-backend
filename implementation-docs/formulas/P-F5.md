# EquationVariantScientificProfile — P-F5

## 1. Identificação

```text
identity:
    variantId:
        "P-F5"
    familyId:
        "petroski"
    displayName:
        "Petroski F5 — Feminino"
    aliasNames:
        [
            "Petroski F5",
            "Equação F5 de Petroski"
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

A equação P-F5 é:

```text
D =
    1.20670046
    - 0.07395778 × LOG10(Y7)
    - 0.00030860 × AGE
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

Saída:

```text
outputType:
    BODY_DENSITY
```

A equação utiliza o logaritmo de base 10 da soma de sete dobras cutâneas, juntamente com a idade. A combinação das sete dobras corresponde à configuração `Y7` das equações femininas de Petroski.

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

A amostra feminina de regressão continha 213 mulheres com idade entre 18 e 51 anos, com média de 27,46 anos. A amostra independente de validação continha 68 mulheres com idade entre 18 e 43 anos.

A média de idade da amostra de desenvolvimento é descritiva e não constitui limite de elegibilidade.

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

A amostra feminina total do estudo compreendeu 281 mulheres, distribuídas entre 213 participantes utilizadas na regressão e 68 na validação independente. A amostra de desenvolvimento abrangeu 18–51 anos e foi descrita como heterogênea em idade e gordura corporal.

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

Não há classificação suficientemente explícita de atletas ou não atletas, compatível com o modelo booleano da plataforma, atribuída às amostras de desenvolvimento ou validação da P-F5.

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

Não foi identificada regra explícita de elegibilidade por característica corporal específica para a P-F5.

Os intervalos observados na amostra descrevem a população de desenvolvimento e não são convertidos automaticamente em restrições rígidas de execução.

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
            0.827
        R2:
            0.684
        standardError:
            0.0072
```

A P-F5 apresentou `R = 0,827`, `R² = 0,684` e `EPE = 0,0072 g/ml` na amostra de regressão. Na documentação fornecida para esta variante, a P-F5 é descrita como o modelo logarítmico feminino com menor valor de `R` entre as variantes femininas consideradas.

## 10. Validação

A amostra independente de validação foi composta por:

```text
n:
    68 mulheres
age:
    18–43 anos
```

Método de critério:

```text
Pesagem hidrostática
```

Para P-F5:

```text
correlation:
    0.710
constantError:
    -0.00074 g/ml
totalError:
    0.0073 g/ml
standardErrorOfEstimate:
    0.0072 g/ml
```

A tabela de validação relata densidade estimada média de `1,045645 ± 0,0089 g/ml`, `r = 0,710`, `t = -0,835`, `EC = -0,00074 g/ml`, `ET = 0,0073 g/ml` e `EPE = 0,0072 g/ml`.

O erro constante relatado é negativo e de pequena magnitude. A diferença média entre os valores medidos e estimados permaneceu pequena, e o teste t pareado informado não indica diferença estatisticamente significativa entre as médias.

Na documentação fornecida, a P-F5 é ainda descrita como a variante feminina com maior erro constante absoluto entre os 16 modelos femininos.

## 11. Validação Cruzada

```text
crossValidationStudies:
    []
```

A amostra independente de 68 mulheres está representada em `validationStudies` e não deve ser duplicada em `crossValidationStudies`.

As análises separadas de validação cruzada realizadas por Petroski sobre equações de outros investigadores não constituem estudos adicionais específicos de validação cruzada da P-F5.

## 12. Validação Externa

```text
externalValidationStudies:
    []
```

Não foi incluído neste perfil estudo de validação externa da P-F5 realizado fora da investigação original de Petroski.

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
    optionalInputs:
        []
```

Entradas matemáticas:

```text
AGE
Y7
```

onde:

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

e:

```text
LOG10(Y7)
```

é utilizado na equação matemática.

`SEX` não faz parte de `requiredInputs`; ele determina a aplicabilidade da variante feminina.

Segundo a notação da tese, a idade é expressa em anos e as dobras cutâneas em milímetros. A fonte define `Y7` como a soma das dobras subescapular, tricipital, axilar média, peitoral, supra-ilíaca, abdominal e da coxa.

As definições gerais de unidades, precisão e faixas plausíveis permanecem em:

```text
/library/measurements
```

## 14. Restrições Científicas

```text
restrictions:
    []
```

Não foi identificada restrição científica adicional explícita que produza `INELIGIBLE` para a P-F5.

A faixa etária de desenvolvimento de 18–51 anos é tratada como evidência da população estudada, e não como restrição de elegibilidade gerada automaticamente.

A exigência das sete dobras cutâneas e de `AGE` decorre diretamente da definição matemática da equação.

O agregado `Y7` deve ser válido antes da aplicação de `LOG10(Y7)`.

## 15. Conflito de Fonte

```text
sourceConflict:
    null
```

Não foi identificado conflito material entre fontes primárias legítimas para a definição matemática da P-F5 ou para as métricas de desenvolvimento e validação apresentadas nesta ficha.

A definição matemática é atribuída à tabela de equações generalizadas femininas da tese de Petroski (1995).

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
            "P-F5"
        familyId:
            "petroski"
        displayName:
            "Petroski F5 — Feminino"
        aliasNames:
            [
                "Petroski F5",
                "Equação F5 de Petroski"
            ]
    mathematicalDefinition:
        equation:
            "D = 1.20670046 - 0.07395778 × LOG10(Y7) - 0.00030860 × AGE"
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
                "Pesagem hidrostática"
            year:
                1995
            metrics:
                R:
                    0.827
                R2:
                    0.684
                standardError:
                    0.0072
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
                    0.710
                standardError:
                    0.0072
                meanDifference:
                    -0.00074
                rmse:
                    null
                otherMetrics:
                    "EC = -0.00074 g/ml; ET = 0.0073 g/ml; EPE = 0.0072 g/ml."
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

O perfil científico permite ao runtime avaliar:

```text
sex:
    FEMALE → compatível com o sexo documentado

age:
    comparar com a evidência da população documentada
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

output:
    BODY_DENSITY
```

O motor não deve transformar a média de idade do desenvolvimento em limite de elegibilidade, e a ausência de evidência sobre atleta, nível de treinamento ou modalidade não deve resultar em `INELIGIBLE`.

A P-F5 é a variante feminina logarítmica de sete dobras de Petroski, utilizando:

```text
Y7 =
    subescapular
    + tricipital
    + axilar média
    + peitoral
    + supra-ilíaca
    + abdominal
    + coxa
```

A única variável adicional do modelo, além de `Y7`, é `AGE`.

Portanto:

```text
P-F5:
    AGE + Y7
```

A P-F5 deve permanecer matematicamente distinta de outras variantes femininas de Petroski, mesmo quando compartilha a mesma configuração de dobras.

## 19. Referência

```text
Reference
    citation:
        "Petroski, E. L. (1995).
         Desenvolvimento e validação de equações generalizadas
         para a estimativa da densidade corporal em adultos.
         Tese de Doutorado.
         Universidade Federal de Santa Maria (UFSM),
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
    "https://cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/"
```

A ficha mantém a referência principal da tese de Petroski (1995) e uma fonte institucional adicional do Centro Esportivo Virtual, que identifica a tese, sua instituição e o estudo de desenvolvimento e validação das equações. 
