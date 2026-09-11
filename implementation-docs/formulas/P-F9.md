# EquationVariantScientificProfile — P-F9

## 1. Identificação

```text
identity:
    variantId:
        "P-F9"

    familyId:
        "petroski"

    displayName:
        "Petroski F9 — Feminino"

    aliasNames:
        [
            "Petroski F9",
            "Equação F9 de Petroski"
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

A equação P-F9 é:

```text
D =
    1.02902361
    - 0.00067159 × X4
    + 0.00000242 × X4²
    - 0.00026073 × AGE
    - 0.00056009 × BODY_MASS
    + 0.00054649 × HEIGHT
```

Onde:

```text
X4 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_MEDIAL_CALF
```

Saída:

```text
outputType:
    BODY_DENSITY
```

A equação utiliza a soma de quatro dobras cutâneas e seu termo quadrático, juntamente com idade, massa corporal e estatura.

A combinação de quatro dobras corresponde à configuração `X4` das equações femininas generalizadas de Petroski.

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

A amostra feminina de regressão continha 213 mulheres com idade entre 18 e 51 anos, com média de 27,46 anos. A amostra independente de validação continha 68 mulheres com idade entre 18 e 43 anos, com média de 27,18 anos.

A média de idade é descritiva e não constitui um limite de elegibilidade.

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

O total da amostra feminina do estudo foi de 281 mulheres, distribuídas entre 213 participantes na amostra de regressão e 68 participantes na amostra independente de validação.

A amostra de desenvolvimento foi composta por mulheres adultas da região central do Rio Grande do Sul e da região litorânea de Santa Catarina. A população de desenvolvimento foi heterogênea em idade e composição corporal.

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

Não há classificação explícita suficientemente documentada de participantes atletas ou não atletas que permita atribuir com segurança esses estados booleanos à amostra de desenvolvimento ou à amostra de validação da P-F9.

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

Não foi identificada regra explícita de elegibilidade por característica corporal específica para a P-F9.

Os intervalos observados na população de desenvolvimento descrevem a amostra estudada e não são convertidos automaticamente em restrições rígidas de execução.

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
            0.848

        R2:
            0.719

        standardError:
            0.0068
```

A P-F9 apresentou `R = 0.848`, `R² = 0.719` e `EPE = 0.0068 g/ml` na amostra de regressão.

Entre os modelos femininos quadráticos documentados na ficha, a P-F9 utiliza quatro dobras cutâneas juntamente com idade, massa corporal e estatura.

## 10. Validação

A amostra independente de validação foi:

```text
n:
    68 mulheres

age:
    18–43 anos
```

Método critério:

```text
pesagem hidrostática
```

Para a P-F9:

```text
correlation:
    0.768

constantError:
    0.00011 g/ml

totalError:
    0.0066 g/ml

standardErrorOfEstimate:
    0.0065 g/ml
```

A tabela de validação apresenta densidade média estimada de `1.046280 ± 0.0093 g/ml`, `r = 0.768`, `t = 0.132`, `EC = 0.00011 g/ml`, `ET = 0.0066 g/ml` e `EPE = 0.0065 g/ml`.

O erro constante relatado é pequeno e positivo, indicando pequena diferença média entre a densidade medida e a densidade estimada na amostra independente de validação.

## 11. Validação Cruzada

```text
crossValidationStudies:
    []
```

A amostra independente de 68 mulheres é representada em `validationStudies` e não é duplicada em `crossValidationStudies`.

As análises de validação cruzada realizadas por Petroski para equações de outros investigadores não constituem estudo adicional específico de validação cruzada da P-F9.

## 12. Validação Externa

```text
externalValidationStudies:
    []
```

Não foi incluído estudo de validação externa da P-F9 fora da investigação original de Petroski.

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
        - SKINFOLD_MEDIAL_CALF

    optionalInputs:
        []
```

Entradas matemáticas:

```text
AGE

BODY_MASS

HEIGHT

X4
```

onde:

```text
X4 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_MEDIAL_CALF
```

e:

```text
X4²
```

é utilizado na equação matemática.

`SEX` não é incluído em `requiredInputs`; ele determina a aplicabilidade à variante feminina.

Segundo a notação utilizada na fonte, idade é expressa em anos, massa corporal em quilogramas, estatura em centímetros e dobras cutâneas em milímetros. `X4` corresponde à soma das dobras subescapular, tríceps, supra-ilíaca e panturrilha medial.

As definições gerais de unidades, precisão e intervalos plausíveis permanecem em:

```text
/library/measurements
```

## 14. Restrições Científicas

```text
restrictions:
    []
```

Não foi identificada restrição científica adicional explícita que produza `INELIGIBLE`.

A faixa etária de desenvolvimento de 18–51 anos é tratada como evidência da população estudada, e não como restrição de execução automaticamente gerada.

Os requisitos de massa corporal, estatura e das quatro dobras componentes são requisitos matemáticos de entrada e não restrições independentes de elegibilidade.

## 15. Conflito de Fonte

```text
sourceConflict:
    null
```

Não foi identificado conflito material não resolvido quanto à definição matemática da P-F9 ou às estatísticas de desenvolvimento e validação independente registradas nesta ficha.

A definição matemática corresponde à equação feminina P-F9 documentada no conjunto de equações generalizadas de Petroski.

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
identity                       CONFIRMED

mathematicalDefinition         CONFIRMED

supportedSexes                CONFIRMED

originalDevelopmentAgeRange   CONFIRMED

originalPopulation            CONFIRMED

requiredInputs                CONFIRMED

definitionReference            CONFIRMED

sourceConflict                 null
```

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile
    identity:
        variantId:
            "P-F9"

        familyId:
            "petroski"

        displayName:
            "Petroski F9 — Feminino"

        aliasNames:
            [
                "Petroski F9",
                "Equação F9 de Petroski"
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
                     das equações generalizadas."

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
                    "Amostra independente utilizada para validar as equações."

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
                    "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"

            population:
                "Mulheres adultas, n = 213, 18–51 anos"

            criterionMethod:
                "Pesagem hidrostática"

            year:
                1995

            metrics:
                R:
                    0.848

                R2:
                    0.719

                standardError:
                    0.0068

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
                    "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"

              population:
                "Mulheres adultas, n = 68, 18–43 anos"

              criterionMethod:
                "Pesagem hidrostática"

              metrics:
                correlation:
                    0.768

                standardError:
                    0.0065

                meanDifference:
                    0.00011

                rmse:
                    null

                otherMetrics:
                    "EC = 0.00011 g/ml; ET = 0.0066 g/ml;
                     EPE = 0.0065 g/ml."

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

O perfil científico permite avaliar em tempo de execução:

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
    + BODY_MASS
    + HEIGHT
    + 4 dobras cutâneas

output:
    BODY_DENSITY
```

O motor não deve transformar a média de idade de desenvolvimento em limite de elegibilidade, e a ausência de evidência sobre atleta, nível de treinamento ou modalidade não deve resultar em `INELIGIBLE`.

A P-F9 é a variante feminina quadrática de quatro dobras de Petroski, baseada na configuração `X4`:

```text
X4 =
    subescapular
    + tríceps
    + supra-ilíaca
    + panturrilha medial
```

Além de `X4`, a equação exige `AGE`, `BODY_MASS` e `HEIGHT`.

Portanto:

```text
P-F9:
    AGE + BODY_MASS + HEIGHT + X4
```

A P-F9 e a P-F10 possuem a mesma estrutura quadrática e utilizam `AGE + BODY_MASS + HEIGHT`, mas são matematicamente distintas porque empregam agregações diferentes de dobras cutâneas.

P-F9 utiliza:

```text
subescapular
tríceps
supra-ilíaca
panturrilha medial
```

enquanto P-F10 utiliza a configuração `Y4` documentada para aquela variante.

O `SuggestionEngine` deve manter as duas variantes matematicamente e operacionalmente distintas.

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
```

Fonte bibliográfica adicional:

```text
citation:
    "Centro Esportivo Virtual — Desenvolvimento e Validação de Equações
     Generalizadas Para a Estimativa da Densidade Corporal em Adultos."

url:
    "https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/"
```