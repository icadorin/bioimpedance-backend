# EquationVariantScientificProfile — P-F7

## 1. Identificação

```text
identity:
    variantId:
        "P-F7"
    familyId:
        "petroski"
    displayName:
        "Petroski F7 — Feminino"
    aliasNames:
        [
            "Petroski F7",
            "Equação F7 de Petroski"
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

A variante P-F7 é definida por:

```text
D =
    1.03091919
    - 0.00048584 × X5
    + 0.00000131 × X5²
    - 0.00026016 × AGE
    - 0.00056484 × BODY_MASS
    + 0.00053716 × HEIGHT
```

Onde:

```text
X5 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_ABDOMINAL
    + SKINFOLD_MEDIAL_CALF
```

Saída:

```text
outputType:
    BODY_DENSITY
```

A equação é um modelo de regressão quadrática que utiliza o somatório de cinco dobras cutâneas em conjunto com `AGE`, `BODY_MASS` e `HEIGHT`. A composição de `X5` corresponde a subescapular, tríceps, supra-ilíaca, abdominal e panturrilha medial. Essa composição e os coeficientes da equação são reproduzidos em publicação científica brasileira que apresenta as equações generalizadas de Petroski para mulheres de 18 a 51 anos.

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

A amostra feminina de desenvolvimento continha 213 mulheres com idade de 18 a 51 anos, com idade média de 27,46 anos. A amostra independente de validação continha 68 mulheres de 18 a 43 anos.

A idade média da amostra de desenvolvimento é exclusivamente descritiva e não deve ser convertida em limite de elegibilidade.

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

O estudo feminino totalizou 281 participantes, sendo 213 destinadas ao desenvolvimento das equações e 68 destinadas à validação independente. A fonte secundária que reproduz a tabela original identifica a população de desenvolvimento feminina como 281 mulheres de 18 a 51 anos e apresenta a P-F7 como a equação de cinco dobras com `AGE`, `BODY_MASS` e `HEIGHT`.

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

Não foi identificada documentação suficientemente explícita que permita classificar a amostra de desenvolvimento ou de validação da P-F7, com segurança, como composta por atletas ou não atletas segundo o modelo booleano adotado pela plataforma.

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

Não foi identificada regra explícita de elegibilidade baseada em característica corporal específica para a P-F7.

As faixas observadas na população de desenvolvimento descrevem a amostra original e não devem ser transformadas automaticamente em restrições rígidas de execução.

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
            0.854
        R2:
            0.729
        standardError:
            0.0066
```

Para a P-F7, foram reportados `R = 0,854`, `R² = 0,729` e erro padrão de estimativa (`EPE`) de `0,0066 g/ml` na amostra de desenvolvimento. A tabela científica que reproduz as equações de Petroski identifica a P-F7 como a equação feminina de cinco dobras com os coeficientes registrados nesta ficha.

## 10. Validação

A amostra independente de validação foi composta por:

```text
n:
    68 mulheres
age:
    18–43 anos
```

Método de referência:

```text
Pesagem hidrostática
```

Para a P-F7:

```text
correlation:
    0.771
constantError:
    0.00041 g/ml
totalError:
    0.0066 g/ml
standardErrorOfEstimate:
    0.0065 g/ml
```

A tabela de validação reporta densidade estimada média de `1,045973 ± 0,0091 g/ml`, `r = 0,771`, `t = 0,519`, `EC = 0,00041 g/ml`, `ET = 0,0066 g/ml` e `EPE = 0,0065 g/ml`.

O erro constante reportado é pequeno e positivo, indicando pequena diferença média entre a densidade corporal medida pelo método de referência e a densidade estimada pela equação na amostra independente de validação.

## 11. Validação Cruzada

```text
crossValidationStudies:
    []
```

A amostra independente de 68 mulheres pertence à seção `Validação` e não deve ser duplicada em `crossValidationStudies`.

As análises de validação cruzada de Petroski que comparam equações de outros pesquisadores não constituem, por si só, estudo adicional específico de validação cruzada da P-F7.

## 12. Validação Externa

```text
externalValidationStudies:
    []
```

Não foi registrada nesta ficha uma validação externa da P-F7 fora da investigação original de Petroski.

## 13. Requisitos de Medição

```text
inputs:
    requiredInputs:
        - AGE
        - BODY_MASS
        - HEIGHT
        - SKINFOLD_SUBSCAPULAR
        - SKINFOLD_TRICEPS
        - SKINFOLD_SUPRAILIAC
        - SKINFOLD_ABDOMINAL
        - SKINFOLD_MEDIAL_CALF
    optionalInputs:
        []
```

Entradas matemáticas:

```text
AGE
BODY_MASS
HEIGHT
X5
```

Onde:

```text
X5 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_ABDOMINAL
    + SKINFOLD_MEDIAL_CALF
```

A equação utiliza tanto `X5` quanto `X5²`.

`SEX` não integra `requiredInputs`; ele determina a aplicabilidade da variante feminina.

Segundo a notação utilizada nas fontes consultadas, `AGE` é expresso em anos, `BODY_MASS` em quilogramas, `HEIGHT` em centímetros e as dobras cutâneas em milímetros.

Definições gerais de unidade, precisão e faixas plausíveis permanecem em:

```text
/library/measurements
```

## 14. Restrições Científicas

```text
restrictions:
    []
```

Não foi identificada restrição científica adicional que, por si só, deva produzir `INELIGIBLE`.

A faixa etária de desenvolvimento de 18 a 51 anos é registrada como evidência da população original e não como limite automático de elegibilidade.

Os requisitos de `AGE`, `BODY_MASS`, `HEIGHT` e das cinco dobras componentes de `X5` são requisitos matemáticos de entrada, e não restrições independentes adicionais.

O agregado `X5` deve ser válido antes da aplicação dos termos `X5` e `X5²`.

## 15. Conflito de Fonte

```text
sourceConflict:
    null
```

Não foi identificado conflito material entre fontes primárias para a definição matemática da P-F7 no material analisado.

A equação de cinco dobras com `X5`, `AGE`, `BODY_MASS` e `HEIGHT`, incluindo seus coeficientes, é reproduzida em publicação científica que apresenta as equações generalizadas de Petroski para mulheres de 18 a 51 anos. A referência principal da variante permanece sendo a tese de Petroski de 1995.

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
            "P-F7"
        familyId:
            "petroski"
        displayName:
            "Petroski F7 — Feminino"
        aliasNames:
            [
                "Petroski F7",
                "Equação F7 de Petroski"
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
                    0.854
                R2:
                    0.729
                standardError:
                    0.0066
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
                    0.771
                standardError:
                    0.0065
                meanDifference:
                    0.00041
                rmse:
                    null
                otherMetrics:
                    "EC = 0,00041 g/ml; ET = 0,0066 g/ml; EPE = 0,0065 g/ml."
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
            - SKINFOLD_SUPRAILIAC
            - SKINFOLD_ABDOMINAL
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

O perfil científico permite ao motor avaliar, em tempo de execução:

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
    nenhuma regra explícita

inputs:
    AGE
    + BODY_MASS
    + HEIGHT
    + 5 skinfolds

output:
    BODY_DENSITY
```

O motor não deve transformar a idade média de desenvolvimento em limite de elegibilidade. A ausência de evidência específica sobre atletas, nível de treinamento ou modalidade também não deve ser convertida em `INELIGIBLE`.

A P-F7 é a variante feminina de Petroski baseada em cinco dobras e regressão quadrática, utilizando:

```text
X5 =
    subscapular
    + triceps
    + suprailiac
    + abdominal
    + medial calf
```

Além de `X5`, a equação exige `AGE`, `BODY_MASS` e `HEIGHT`.

A distinção operacional em relação às demais variantes da mesma série deve ser mantida segundo suas definições matemáticas específicas. Na documentação recebida, P-F5 utiliza `AGE + Y7`, P-F6 utiliza `AGE + Y7 + CIRCUMFERENCE_THIGH` e P-F7 utiliza `AGE + BODY_MASS + HEIGHT + X5`. Essas diferenças matemáticas devem permanecer preservadas pelo `SuggestionEngine`.

## 19. Referência

```text
Reference
    citation:
        "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado. Universidade Federal de Santa Maria (UFSM), Santa Maria, RS, Brasil."
    doi:
        null
    url:
        "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"
```

Fonte bibliográfica adicional:

```text
citation:
    "Validação de equações antropométricas generalizadas para a estimativa da densidade corporal em mulheres. Revista Brasileira de Cineantropometria & Desempenho Humano. 2006;8(1):22-28."
doi:
    null
url:
    "https://periodicos.ufsc.br/index.php/rbcdh/article/download/3759/3205/11264"
```
