# EquationVariantScientificProfile — G-M7

# 1. Identificação

```text
identity:

    variantId:

        "G-M7"

    familyId:

        "guedes"

    displayName:

        "Guedes 7 Dobras — Masculino"

    aliasNames:

        [

            "Guedes GM7",

            "GM7"

        ]
```

A variante `G-M7` corresponde à sétima equação masculina do conjunto de equações de regressão desenvolvido por Dartagnan Pinto Guedes para estimativa da densidade corporal em universitários.

A fonte de desenvolvimento identificada para os coeficientes desta variante é o estudo de Dartagnan Pinto Guedes publicado em 1985 na revista Kinesis. O estudo avaliou 206 universitários da Universidade Federal de Santa Maria, sendo 110 homens e 96 mulheres, com idade entre 17 e 27 anos.

Referência principal da definição:

```text
Guedes, D. P. (1985).

Estudo da gordura corporal através da mensuração dos valores de densidade
corporal e da espessura de dobras cutâneas em universitários.

Kinesis, 1(2).

DOI: 10.5902/231654648617
```

---

# 2. Definição Matemática

A definição matemática da `G-M7` é:

```text
D =

    1.22098

    - 0.08214 × log10(Σ7)
```

Onde:

```text
D

    = BODY_DENSITY

Σ7

    = soma das sete dobras cutâneas utilizadas pela G-M7
```

A soma é composta por:

```text
Σ7 =

    SKINFOLD_ABDOMEN

    + SKINFOLD_TRICEPS

    + SKINFOLD_SUPRAILIAC

    + SKINFOLD_AXILLARY_MID

    + SKINFOLD_SUBSCAPULAR

    + SKINFOLD_THIGH

    + SKINFOLD_MEDIAL_CALF
```

A G-M7 corresponde à sétima equação masculina da série de Guedes. O número 7 em `G-M7` identifica a posição da equação no conjunto e, neste caso, também coincide com a utilização de sete dobras cutâneas.

### Output

```text
outputType = BODY_DENSITY
```

### Forma computacional

```text
sum7 =

    SKINFOLD_ABDOMEN

    + SKINFOLD_TRICEPS

    + SKINFOLD_SUPRAILIAC

    + SKINFOLD_AXILLARY_MID

    + SKINFOLD_SUBSCAPULAR

    + SKINFOLD_THIGH

    + SKINFOLD_MEDIAL_CALF

bodyDensity =

    1.22098

    - 0.08214 × log10(sum7)
```

A conversão posterior de `BODY_DENSITY` para percentual de gordura não faz parte da definição matemática desta variante.

---

# 3. Aplicabilidade

## 3.1 Sexo

```text
sex:

    supportedSexes:

        - MALE
```

A G-M7 pertence ao conjunto masculino das equações específicas desenvolvidas por Guedes. A população masculina de desenvolvimento foi composta por 110 universitários.

---

## 3.2 Idade

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

A fonte primária institucional consultada informa que o estudo avaliou universitários com idade entre 17 e 27 anos. Para o conjunto masculino, foram avaliados 110 homens.

A faixa de desenvolvimento é uma característica da população estudada e não é convertida automaticamente em uma `ScientificRestriction` ou em uma regra de inelegibilidade.

Há publicações posteriores de Guedes e Guedes com população de adultos jovens de 18 a 30 anos. Essas publicações não devem substituir automaticamente a fonte de 1985 nem ser tratadas como conflito dentro da G-M7, pois a fonte dos coeficientes deve ser determinada pela correspondência entre os coeficientes e o estudo de origem.

---

# 4. Aplicabilidade Populacional

```text
population:

    originalPopulation:

        description:

            "Homens universitários / adultos jovens"

        country:

            "Brazil"

        region:

            "Rio Grande do Sul"

        sexCoverage:

            - MALE

        ageCoverage:

            min: 17

            max: 27

        sampleSize:

            110

        bodyCharacteristicsNotes:

            "Participantes avaliados por densidade corporal e espessura

             de dobras cutâneas."

        sampleCharacteristics:

            "Universitários da Universidade Federal de Santa Maria (RS),

             integrantes da amostra total de 206 participantes."

        source:

            "Guedes (1985)"

    validationPopulations:

        []
```

O estudo de desenvolvimento publicado em 1985 avaliou 206 universitários da Universidade Federal de Santa Maria, sendo 110 homens e 96 mulheres, com idade entre 17 e 27 anos.

A fonte institucional identifica o trabalho como um estudo original de Dartagnan Pinto Guedes, publicado na Kinesis em 1985.

---

# 5. Aplicabilidade em Atletas

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

A descrição como universitários não é suficiente para classificá-los, pela taxonomia da plataforma, como atletas ou não atletas.

---

# 6. Aplicabilidade por Nível de Treinamento

```text
trainingLevel:

    supportedLevels:

        []

    notes:

        "Não documentado segundo a escala operacional da plataforma."
```

Não foi estabelecida correspondência documentada com:

```text
SEDENTARY
RECREATIONAL
TRAINED
COMPETITIVE
ELITE
```

---

# 7. Aplicabilidade por Modalidade

```text
modality:

    supportedModalities:

        []

    notes:

        "Não documentado."
```

Não foi identificada modalidade esportiva específica como critério de desenvolvimento da G-M7.

---

# 8. Características Corporais

```text
bodyCharacteristics:

    rules:

        []
```

Não foi identificada característica corporal explicitamente documentada que justifique a criação de uma regra `SUPPORTED`, `WARNING` ou `INELIGIBLE` específica para a G-M7.

Os valores observados na população de desenvolvimento permanecem características descritivas da amostra e não são convertidos em restrições sem evidência específica.

---

# 9. Evidências de Validação

## 9.1 Evidências de Desenvolvimento

```text
development:

    studyReference:

        citation:

            "Guedes, D. P. (1985). Estudo da gordura corporal através da

             mensuração dos valores de densidade corporal e da espessura

             de dobras cutâneas em universitários. Kinesis, 1(2)."

        doi:

            "10.5902/231654648617"

        url:

            "https://periodicos.ufsm.br/kinesis/article/view/8617"

    population:

        "Homens universitários, n = 110, 17–27 anos"

    criterionMethod:

        "Densidade corporal determinada por pesagem hidrostática"

    year:

        1985

    metrics:

        correlation:

            0.90

        r2:

            null

        standardError:

            0.0055

        meanDifference:

            null

        rmse:

            null

        otherMetrics:

            null
```

A tabela da literatura que reproduz as oito equações masculinas de Guedes identifica especificamente a G-M7 como `D = 1.22098 - 0.08214 × log10(AB + SI + TR + SE + AX + CXM + PM)`, com `r = 0.90`, `EPE = 0.0055 g/ml`, `n = 110` e faixa etária de 17–27 anos.

A fonte primária institucional de 1985 confirma a população total e sua composição de 110 homens e 96 mulheres entre 17 e 27 anos.

O valor `r2` permanece `null` porque a fonte consultada informa a correlação `r`, mas não fornece `R²` como métrica independente para a G-M7. O erro padrão de estimativa de `0.0055 g/ml` é mantido no campo `standardError`, conforme a tabela que individualiza a G-M7.

---

# 10. Validação

```text
validationStudies:

    []
```

Não foi identificada, na documentação utilizada para esta ficha, uma subamostra independente do mesmo estudo de 1985 que permita cadastrar uma validação separada da G-M7 com população e métricas individualizadas.

Métricas agregadas ou informações que não podem ser atribuídas inequivocamente à G-M7 não devem ser distribuídas por inferência entre as variantes do conjunto.

---

# 11. Validação Cruzada

```text
crossValidationStudies:

    []
```

A classificação como `crossValidationStudies` exige evidência de teste da variante contra dados de outro estudo ou pesquisador. Nenhum estudo foi incorporado aqui sem identificação inequívoca dos resultados específicos da G-M7.

Não utilizar a existência de publicações posteriores da família Guedes como prova automática de validação cruzada da G-M7.

---

# 12. Validação Externa

```text
externalValidationStudies:

    []
```

Não foi incorporado estudo de validação externa com resultados especificamente atribuíveis à G-M7.

Estudos posteriores que avaliaram várias equações simultaneamente somente devem ser registrados nesta seção quando os resultados da G-M7 puderem ser individualizados de forma inequívoca.

---

# 13. Requisitos de Medição

```text
inputs:

    requiredInputs:

        - SKINFOLD_ABDOMEN

        - SKINFOLD_TRICEPS

        - SKINFOLD_SUPRAILIAC

        - SKINFOLD_AXILLARY_MID

        - SKINFOLD_SUBSCAPULAR

        - SKINFOLD_THIGH

        - SKINFOLD_MEDIAL_CALF

    optionalInputs:

        []
```

A equação não utiliza `AGE` como variável matemática. Portanto, a idade pertence à descrição da aplicabilidade populacional e não aos `requiredInputs`.

A separação permanece:

```text
SEX

    → Applicability

AGE

    → Applicability / comparação populacional

7 skinfolds

    → Formula inputs
```

Os identificadores canônicos dos locais são utilizados integralmente.

---

# 14. Restrições Científicas

```text
restrictions:

    []
```

A faixa de 17–27 anos descreve a população de desenvolvimento e não é convertida automaticamente em uma restrição científica explícita.

Não foi identificada restrição adicional com evidência suficiente para produzir `INELIGIBLE` nesta variante.

---

# 15. Conflito de Fonte

```text
sourceConflict:

    null
```

Não foi identificado conflito científico material dentro da definição da G-M7.

Existe uma diferença bibliográfica entre o estudo de 1985, com universitários de 17–27 anos, e publicações posteriores de Guedes e Guedes sobre adultos jovens de 18–30 anos. Essa diferença não é tratada automaticamente como `sourceConflict`, pois publicações distintas podem representar conjuntos de equações ou populações distintos.

Para a G-M7, os coeficientes `1.22098` e `0.08214` correspondem à equação identificada como Guedes (1985) nas tabelas que reproduzem o conjunto original de oito equações masculinas.

---

# 16. Ciclo de Vida

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

A variante possui os elementos essenciais para permanecer como `ACTIVE`:

```text
identity                        ✓

mathematicalDefinition          ✓

supportedSexes                 ✓

originalDevelopmentAgeRange    ✓

originalPopulation             ✓

requiredInputs                 ✓

reference                      ✓

sourceConflict                 null
```

As informações não documentadas sobre atleta, nível de treinamento, modalidade e validação externa permanecem explicitamente não documentadas e não impedem o status `ACTIVE`.

---

# 17. Ficha Consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:

            "G-M7"

        familyId:

            "guedes"

        displayName:

            "Guedes 7 Dobras — Masculino"

        aliasNames:

            [

                "Guedes GM7",

                "GM7"

            ]

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

                    "Homens universitários / adultos jovens"

                country:

                    "Brazil"

                region:

                    "Rio Grande do Sul"

                sexCoverage:

                    - MALE

                ageCoverage:

                    min: 17

                    max: 27

                sampleSize:

                    110

                bodyCharacteristicsNotes:

                    "Participantes avaliados por densidade corporal e

                     espessura de dobras cutâneas."

                sampleCharacteristics:

                    "Universitários da Universidade Federal de Santa Maria (RS),

                     integrantes da amostra total de 206 participantes."

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

                    "Guedes, D. P. (1985). Estudo da gordura corporal

                     através da mensuração dos valores de densidade corporal

                     e da espessura de dobras cutâneas em universitários.

                     Kinesis, 1(2)."

                doi:

                    "10.5902/231654648617"

                url:

                    "https://periodicos.ufsm.br/kinesis/article/view/8617"

            population:

                "Homens universitários, n = 110, 17–27 anos"

            criterionMethod:

                "Densidade corporal determinada por pesagem hidrostática"

            year:

                1985

            metrics:

                correlation:

                    0.90

                r2:

                    null

                standardError:

                    0.0055

                meanDifference:

                    null

                rmse:

                    null

                otherMetrics:

                    null

        validationStudies:

            []

        crossValidationStudies:

            []

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

            - SKINFOLD_SUBSCAPULAR

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

# 18. Interpretação para o SuggestionEngine

A interpretação operacional deve permanecer baseada somente nos dados documentados nesta ficha:

```text
sexo:

    MALE → compatível

    FEMALE → incompatível

idade:

    população de desenvolvimento = 17–27 anos

    faixa descritiva da população

    NÃO converter automaticamente em restrição explícita

atleta:

    NOT_DOCUMENTED

trainingLevel:

    NOT_DOCUMENTED

modality:

    NOT_DOCUMENTED

inputs:

    7 skinfolds

output:

    BODY_DENSITY
```

O motor não deve transformar a ausência de documentação específica sobre atleta, treinamento ou modalidade em inelegibilidade.

A existência de uma faixa populacional de 17–27 anos deve ser tratada como evidência de aplicabilidade populacional. Qualquer decisão adicional de elegibilidade etária depende de regra explícita do modelo científico ou de restrição documentada.

---

# 19. Referência

```text
Reference

    citation:

        "Guedes, D. P. (1985). Estudo da gordura corporal através da

         mensuração dos valores de densidade corporal e da espessura de

         dobras cutâneas em universitários. Kinesis, 1(2)."

    doi:

        "10.5902/231654648617"

    url:

        "https://periodicos.ufsm.br/kinesis/article/view/8617"
```

A fonte de desenvolvimento identifica o trabalho de Dartagnan Pinto Guedes publicado em 1985 na Kinesis. A URL registrada corresponde à fonte efetivamente utilizada para a definição da variante. O estudo avaliou 206 universitários da Universidade Federal de Santa Maria, sendo 110 homens e 96 mulheres, com idade de 17 a 27 anos.

A tabela secundária utilizada para confirmação dos coeficientes individualiza a G-M7 como a equação de 7 dobras com `r = 0.90`, `EPE = 0.0055 g/ml`, `n = 110` e idade de 17–27 anos. Essa fonte é usada apenas para confirmação dos parâmetros da equação; a referência principal da ficha permanece o estudo de Guedes de 1985.
