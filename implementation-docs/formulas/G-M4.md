# EquationVariantScientificProfile — G-M4

## 1. Identificação

```text
identity:

    variantId:

        "G-M4"

    familyId:

        "guedes"

    displayName:

        "Guedes 4 Dobras — Masculino"

    aliasNames:

        []
```

A variante G-M4 corresponde à equação específica de quatro dobras para homens do conjunto desenvolvido por Guedes (1985), a partir de universitários da Universidade Federal de Santa Maria.

Referência principal:

```text
Guedes, D. P. (1985).

Estudo da gordura corporal através da mensuração dos valores de densidade
corporal e da espessura de dobras cutâneas em universitários.

Dissertação de Mestrado. Universidade Federal de Santa Maria,
Santa Maria, RS.
```

O trabalho original estudou universitários de ambos os sexos da Universidade Federal de Santa Maria e incluiu 206 participantes, sendo 110 homens e 96 mulheres, com idade entre 17 e 27 anos. O estudo investigou a relação entre gordura corporal e espessura de dobras cutâneas e apresentou equações para predição da densidade corporal. A publicação em Kinesis de 1985 registra a mesma amostra geral e a mesma faixa etária.

## 2. Definição Matemática

A definição matemática da G-M4 é:

```text
D =

    1.18282

    - 0.07030 × log10(Σ4)
```

Onde:

```text
D

    = BODY_DENSITY

Σ4

    = soma das quatro dobras cutâneas
```

As quatro dobras da `Σ4` são:

```text
- abdômen
- tríceps
- supra-ilíaca
- axilar média
```

A equação é reproduzida em literatura científica posterior como:

```text
Dc = 1.18282 - 0.07030 × log10(DCAB + DCTR + DCSI + DCAM)
```

Para essa variante, a tabela de equações específicas de Guedes registra `r = 0.894` e `EPE = 0.0057`.

### Forma computacional

```text
sum4 =

    SKINFOLD_ABDOMEN
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_AXILLARY_MID

bodyDensity =

    1.18282
    - 0.07030 × log10(sum4)
```

### Output

```text
outputType = BODY_DENSITY
```

A conversão posterior de `BODY_DENSITY` para percentual de gordura permanece fora da definição desta `EquationVariant`.

## 3. Aplicabilidade

### 3.1 Sexo

```text
sex:

    supportedSexes:

        - MALE
```

A G-M4 é uma equação específica do conjunto masculino de Guedes. A amostra masculina original foi composta por 110 participantes.

### 3.2 Idade

```text
age:

    originalDevelopmentAgeRange:

        min: 17

        max: 27

    developmentSampleMeanAge:

        null

    validatedAgeRanges:

        []

    explicitAgeRestriction:

        null
```

A fonte de desenvolvimento identifica a população masculina como parte de uma amostra de 206 universitários, sendo 110 homens, com idade entre 17 e 27 anos. A média de idade específica para os homens não foi confirmada na fonte consultada e permanece `null`.

A faixa etária da amostra de desenvolvimento é descritiva e não é convertida automaticamente em `explicitAgeRestriction`.

## 4. Aplicabilidade Populacional

```text
population:

    originalPopulation:

        description:

            "Universitários adultos jovens do sexo masculino"

        country:

            "Brazil"

        region:

            "Santa Maria, Rio Grande do Sul"

        sexCoverage:

            - MALE

        ageCoverage:

            min: 17

            max: 27

        sampleSize:

            110

        bodyCharacteristicsNotes:

            null

        sampleCharacteristics:

            "Amostra masculina pertencente ao estudo realizado com
             universitários da Universidade Federal de Santa Maria."

        source:

            "Guedes (1985)"

    validationPopulations:

        []
```

A fonte de 1985 descreve 206 universitários da Universidade Federal de Santa Maria, sendo 110 homens e 96 mulheres, com idade entre 17 e 27 anos. A descrição original caracteriza a amostra como universitários e relata a determinação da densidade corporal e a mensuração das dobras cutâneas.

Não são adicionadas características corporais além das efetivamente documentadas nas fontes consultadas.

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

A população original é descrita como universitários. Essa descrição, isoladamente, não é suficiente para classificar formalmente os participantes como atletas ou não atletas segundo a escala operacional da plataforma.

## 6. Aplicabilidade por Nível de Treinamento

```text
trainingLevel:

    supportedLevels:

        []

    notes:

        "Não documentado segundo a escala operacional da plataforma."
```

Não deve ser inferido que universitários correspondam a `SEDENTARY`, `RECREATIONAL`, `TRAINED`, `COMPETITIVE` ou `ELITE`.

## 7. Aplicabilidade por Modalidade

```text
modality:

    supportedModalities:

        []

    notes:

        "Não documentado em termos de modalidade esportiva específica."
```

A fonte de desenvolvimento não fornece documentação suficiente para associar a G-M4 a uma modalidade esportiva específica.

## 8. Características Corporais

```text
bodyCharacteristics:

    rules:

        []
```

Não foi identificada uma regra científica específica que justifique cadastrar condições como `LOW_BODY_FAT`, `HIGH_BODY_FAT`, `OBESITY`, `HIGH_MUSCLE_MASS`, `EXTREME_SKINFOLD_VALUES` ou `EXTREME_SKINFOLD_SUM`.

Os limites observados nas amostras não devem ser convertidos artificialmente em regras de elegibilidade.

## 9. Evidências de Validação

### 9.1 Evidências de Desenvolvimento

```text
development:

    studyReference:

        citation:

            "Guedes, D. P. (1985).
             Estudo da gordura corporal através da mensuração dos valores
             de densidade corporal e da espessura de dobras cutâneas em
             universitários. Dissertação de Mestrado, Universidade Federal
             de Santa Maria, Santa Maria, RS."

        doi:

            null

        url:

            null

    population:

        "Homens universitários, n = 110, 17–27 anos"

    criterionMethod:

        "Densidade corporal determinada por pesagem hidrostática"

    year:

        1985

    metrics:

        correlation:

            0.894

        rSquared:

            null

        standardError:

            0.0057

        meanDifference:

            null

        rmse:

            null

        otherMetrics:

            null
```

A equação G-M4 é reproduzida em tabela científica posterior como parte do conjunto específico de Guedes (1985), com `r = 0.894` e `EPE = 0.0057`, para `n = 110` e idade de 17–27 anos. A tabela não fornece `r²` para as equações específicas de Guedes, portanto esse campo permanece `null`.

A fonte institucional da Universidade Federal de Santa Maria confirma a publicação de 1985 e a amostra geral de 206 sujeitos, sendo 110 homens e 96 mulheres, com idade de 17–27 anos.

## 10. Validação

```text
validationStudies:

    []
```

Não foi localizada uma subamostra independente do mesmo estudo original que possa ser registrada especificamente como `validationStudies` para a G-M4 sem misturar uma validação posterior ou uma reprodução secundária.

## 11. Validação Cruzada

```text
crossValidationStudies:

    - studyReference:

        citation:

            "Both, D. R.; Matheus, S. C.; Behenck, M. S. et al. (2015).
             Validação de equações antropométricas específicas e generalizadas
             para estimativa do percentual de gordura corporal em estudantes
             de Educação Física do sexo masculino.
             Revista Brasileira de Educação Física e Esporte, 29(1)."

        doi:

            "10.1590/1807-55092015000100013"

        url:

            "https://doi.org/10.1590/1807-55092015000100013"

      population:

        description:

            "Universitários do sexo masculino de Educação Física"

        country:

            "Brazil"

        region:

            "Santa Maria, Rio Grande do Sul"

        sexCoverage:

            - MALE

        ageCoverage:

            min: 18

            max: 30

        sampleSize:

            104

        bodyCharacteristicsNotes:

            null

        sampleCharacteristics:

            "Universitários voluntários de Educação Física da Universidade
             Federal de Santa Maria."

        source:

            "Both et al. (2015)"

      criterionMethod:

          "Pesagem hidrostática"

      metrics:

          correlation:

              0.79

          rSquared:

              0.62

          standardError:

              3.8

          meanDifference:

              2.4

          rmse:

              null

          otherMetrics:

              "Teste t pareado: t = -6.517; p < 0.001.
               Erro constante (EC) = 2.4%.
               Erro total (ET) = 24.7%.
               A análise foi realizada sobre o percentual de gordura,
               após conversão da densidade corporal estimada pela equação."

      limitations:

          "A G-M4 apresentou diferença estatisticamente significativa
           em relação à pesagem hidrostática e EPE de 3,8% nesta
           amostra de validação cruzada."
```

O estudo de Both et al. (2015) foi explicitamente concebido como validação cruzada das equações específicas de Guedes e das equações generalizadas de Petroski. A amostra teve 104 universitários masculinos de Educação Física, com 18–30 anos, e utilizou pesagem hidrostática como método de referência.

Para a G-M4, a Tabela 3 registra média de `18,7 ± 6,2%`, `r = 0,79`, `r² = 0,62`, `t = -6,517`, `p = 0,000`, `EPE = 3,8%`, `EC = 2,4%` e `ET = 24,7%`. O próprio artigo informa que todas as equações de Guedes apresentaram diferença estatisticamente significativa em relação à pesagem hidrostática e que as equações de Guedes, de modo geral, não atenderam aos critérios de validação cruzada utilizados no estudo.

## 12. Validação Externa

```text
externalValidationStudies:

    []
```

O estudo de Both et al. (2015) é classificado como `crossValidationStudies`, não como `externalValidationStudies`, porque a publicação o apresenta explicitamente como validação cruzada das equações específicas de Guedes e das generalizadas de Petroski.

## 13. Requisitos de Medição

```text
inputs:

    requiredInputs:

        - SKINFOLD_ABDOMEN

        - SKINFOLD_TRICEPS

        - SKINFOLD_SUPRAILIAC

        - SKINFOLD_AXILLARY_MID

    optionalInputs:

        []
```

A G-M4 utiliza exclusivamente quatro espessuras de dobras cutâneas na forma matemática da equação.

`AGE` não participa matematicamente da fórmula.

`SEX` não é um `requiredInput`; permanece exclusivamente em `applicability.sex`.

A separação operacional é:

```text
SEX

    → Applicability

AGE

    → Population / Evidence context

    → NÃO é Formula input

4 skinfolds

    → Formula inputs
```

A tabela científica que reproduz a equação confirma as quatro variáveis: abdominal, tríceps, supra-ilíaca e axilar média.

## 14. Restrições Científicas

```text
restrictions:

    []
```

A faixa de 17–27 anos permanece como característica da população de desenvolvimento e não foi convertida em `ScientificRestriction` explícita.

A validação cruzada posterior em adultos de 18–30 anos também não deve ser transformada automaticamente em nova faixa de elegibilidade da variante. Ela constitui evidência de desempenho em outra população.

## 15. Conflito de Fonte

```text
sourceConflict:

    null
```

Não foi identificado conflito material entre as fontes consultadas quanto aos coeficientes da G-M4.

A fonte de desenvolvimento de 1985 e a literatura científica posterior convergem para:

```text
D = 1.18282 - 0.07030 × log10(AB + TR + SI + AX)
```

A diferença entre materiais que reproduzem a equação não foi suficiente para caracterizar duas fontes primárias conflitantes para a mesma variante. A faixa de 17–27 anos está diretamente documentada para o estudo de 1985, inclusive na publicação da Kinesis.

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

Os elementos essenciais exigidos para `ACTIVE` estão documentados:

```text
identity                     ✓

mathematicalDefinition       ✓

supportedSexes               ✓

originalDevelopmentAgeRange  ✓

originalPopulation           ✓

requiredInputs               ✓

reference                    ✓

sourceConflict               null
```

As informações que não foram confirmadas permanecem explicitamente como `null` ou listas vazias.

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:

            "G-M4"

        familyId:

            "guedes"

        displayName:

            "Guedes 4 Dobras — Masculino"

        aliasNames:

            []

    applicability:

        sex:

            supportedSexes:

                - MALE

        age:

            originalDevelopmentAgeRange:

                min: 17

                max: 27

            developmentSampleMeanAge:

                null

            validatedAgeRanges:

                []

            explicitAgeRestriction:

                null

        population:

            originalPopulation:

                description:

                    "Universitários adultos jovens do sexo masculino"

                country:

                    "Brazil"

                region:

                    "Santa Maria, Rio Grande do Sul"

                sexCoverage:

                    - MALE

                ageCoverage:

                    min: 17

                    max: 27

                sampleSize:

                    110

                bodyCharacteristicsNotes:

                    null

                sampleCharacteristics:

                    "Amostra masculina pertencente ao estudo realizado
                     com universitários da Universidade Federal de Santa Maria."

                source:

                    "Guedes (1985)"

            validationPopulations:

                []

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

                "Não documentado em termos de modalidade esportiva específica."

        bodyCharacteristics:

            rules:

                []

    evidence:

        development:

            studyReference:

                citation:

                    "Guedes, D. P. (1985).
                     Estudo da gordura corporal através da mensuração dos valores
                     de densidade corporal e da espessura de dobras cutâneas em
                     universitários. Dissertação de Mestrado, Universidade Federal
                     de Santa Maria, Santa Maria, RS."

                doi:

                    null

                url:

                    null

            population:

                "Homens universitários, n = 110, 17–27 anos"

            criterionMethod:

                "Densidade corporal determinada por pesagem hidrostática"

            year:

                1985

            metrics:

                correlation:

                    0.89

                rSquared:

                    null

                standardError:

                    0.0057

                meanDifference:

                    null

                rmse:

                    null

                otherMetrics:

                    null

        validationStudies:

            []

        crossValidationStudies:

            - studyReference:

                citation:

                    "Both, D. R.; Matheus, S. C.; Behenck, M. S. et al. (2015).
                     Validação de equações antropométricas específicas e
                     generalizadas para estimativa do percentual de gordura
                     corporal em estudantes de Educação Física do sexo masculino.
                     Revista Brasileira de Educação Física e Esporte, 29(1)."

                doi:

                    "10.1590/1807-55092015000100013"

                url:

                    "https://doi.org/10.1590/1807-55092015000100013"

              population:

                "Universitários masculinos de Educação Física, n = 104,
                 18–30 anos, Santa Maria, Rio Grande do Sul, Brasil"

              criterionMethod:

                  "Pesagem hidrostática"

              metrics:

                  correlation:

                      0.79

                  rSquared:

                      0.62

                  standardError:

                      3.8

                  meanDifference:

                      2.4

                  rmse:

                      null

                  otherMetrics:

                      "t = -6.517; p < 0.001; EC = 2.4%; ET = 24.7%.
                       Métricas calculadas no percentual de gordura após
                       conversão da densidade corporal estimada."

              limitations:

                  "A G-M4 apresentou diferença estatisticamente significativa
                   em relação à pesagem hidrostática e EPE de 3,8% nesta
                   amostra de validação cruzada."

        externalValidationStudies:

            []

        sourceConflict:

            null

    inputs:

        requiredInputs:

            - SKINFOLD_ABDOMEN

            - SKINFOLD_TRICEPS

            - SKINFOLD_SUPRAILIAC

            - SKINFOLD_AXILLARY_MID

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

A G-M4 permite ao motor distinguir:

```text
sexo:

    MALE → compatível

    FEMALE → incompatível

idade:

    população de desenvolvimento documentada = 17–27 anos

    média específica = NOT_DOCUMENTED

    não usar a ausência da média como restrição

atleta:

    NOT_DOCUMENTED

trainingLevel:

    NOT_DOCUMENTED

modalidade:

    NOT_DOCUMENTED

inputs:

    4 skinfolds

output:

    BODY_DENSITY
```

O motor não deve interpretar:

```text
população universitária

    ↓

SEDENTARY
```

nem:

```text
17–27 anos

    ↓

explicitAgeRestriction = [17,27]
```

sem documentação científica específica para essas inferências.

A existência da validação cruzada de 2015 também não deve alterar automaticamente a população original ou a faixa de desenvolvimento da variante; ela deve permanecer como evidência de desempenho em uma amostra posterior.

## 19. Referência

```text
Reference

    citation:

        "Guedes, D. P. (1985).
         Estudo da gordura corporal através da mensuração dos valores de
         densidade corporal e da espessura de dobras cutâneas em universitários.
         Dissertação de Mestrado. Universidade Federal de Santa Maria,
         Santa Maria, RS."

    doi:

        null

    url:

        null
```

### Fontes bibliográficas adicionais

```text
Reference

    citation:

        "Guedes, D. P. (1985).
         Estudo da gordura corporal através da mensuração dos valores de
         densidade corporal e da espessura de dobras cutâneas em universitários.
         Kinesis, 1(2), 183–212."

    doi:

        "10.5902/231654648617"

    url:

        "https://doi.org/10.5902/231654648617"
```

```text
Reference

    citation:

        "Both, D. R.; Matheus, S. C.; Behenck, M. S. et al. (2015).
         Validação de equações antropométricas específicas e generalizadas
         para estimativa do percentual de gordura corporal em estudantes
         de Educação Física do sexo masculino.
         Revista Brasileira de Educação Física e Esporte, 29(1)."

    doi:

        "10.1590/1807-55092015000100013"

    url:

        "https://doi.org/10.1590/1807-55092015000100013"
```

A fonte de 1985 está disponível institucionalmente pela Universidade Federal de Santa Maria, com DOI `10.5902/231654648617`. A publicação posterior de Both et al. está disponível na SciELO e documenta a validação cruzada das equações de Guedes, incluindo os resultados específicos da G-M4.