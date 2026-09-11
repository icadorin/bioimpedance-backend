# EquationVariantScientificProfile — JP3-M

## 1. Identificação

```text
identity:

    variantId:
        "JP3-M"

    familyId:
        "jackson-pollock"

    displayName:
        "Jackson & Pollock 3 Dobras — Masculino"

    aliasNames:
        [
            "Jackson-Pollock 3-Site — Male",
            "JP3 Male",
            "3-Site Jackson-Pollock"
        ]
```

A variante `JP3-M` corresponde à equação masculina de três dobras cutâneas apresentada por Jackson & Pollock em 1978, como a equação nº 5 do conjunto de equações generalizadas para predição da densidade corporal de homens adultos.

Referência principal:

```text
Jackson, A. S.; Pollock, M. L. (1978).

Generalized equations for predicting body density of men.

British Journal of Nutrition, 40(3), 497–504.

DOI: 10.1079/BJN19780152

PMID: 718832
```

---

## 2. Definição Matemática

A definição matemática da `JP3-M` é:

```text
D =

    1.1093800

    - 0.0008267 × Σ3

    + 0.0000016 × Σ3²

    - 0.0002574 × AGE
```

Onde:

```text
D
    = BODY_DENSITY

Σ3
    = soma das três dobras cutâneas

AGE
    = idade em anos
```

As três dobras utilizadas na soma são:

```text
- peitoral
- abdominal
- coxa
```

A fonte original define `Σ3` como a soma das dobras do peito, abdômen e coxa. A equação corresponde à equação nº 5 da Tabela 4 do artigo de Jackson & Pollock (1978).

### Forma computacional

```text
sum3 =

    SKINFOLD_PECTORAL

    + SKINFOLD_ABDOMEN

    + SKINFOLD_THIGH

bodyDensity =

    1.1093800

    - 0.0008267 × sum3

    + 0.0000016 × sum3²

    - 0.0002574 × AGE
```

### Output

```text
outputType = BODY_DENSITY
```

A conversão posterior de `BODY_DENSITY` para percentual de gordura permanece como etapa separada e não faz parte da `EquationVariant`.

---

## 3. Aplicabilidade

### 3.1 Sexo

```text
sex:

    supportedSexes:
        - MALE
```

A variante foi desenvolvida para homens adultos.

### 3.2 Idade

```text
age:

    originalDevelopmentAgeRange:
        min: 18
        max: 61

    developmentSampleMeanAge:
        32.6

    validatedAgeRanges:
        - range:
            min: 18
            max: 59
          population:
            "Homens adultos da amostra independente de validação cruzada"
          source:
            "Jackson & Pollock (1978)"

    explicitAgeRestriction:
        null
```

A amostra total do estudo continha homens adultos entre 18 e 61 anos. A amostra de desenvolvimento utilizada para derivar as equações tinha idade média de 32,6 anos e faixa de 18–61 anos. A segunda amostra, utilizada para validação cruzada, tinha faixa etária de 18–59 anos.

A `developmentSampleMeanAge` é exclusivamente descritiva e não constitui limite de elegibilidade.

Não foi identificada na publicação original uma restrição etária adicional que deva ser transformada em `explicitAgeRestriction`.

---

## 4. Aplicabilidade Populacional

```text
population:

    originalPopulation:

        description:
            "Homens adultos"

        country:
            "Estados Unidos"

        region:
            "Carolina do Norte e Texas"

        sexCoverage:
            - MALE

        ageCoverage:
            min: 18
            max: 61

        sampleSize:
            308

        bodyCharacteristicsNotes:
            "Grande variação de estrutura corporal e composição corporal; percentual de gordura médio de 17,7%, com faixa de aproximadamente 1% a 33%."

        sampleCharacteristics:
            "Homens com ampla variação de estrutura corporal, composição corporal e hábitos de exercício; participantes avaliados na Wake Forest University, em Winston-Salem, Carolina do Norte, e no Institute for Aerobics Research, em Dallas, Texas."

        source:
            "Jackson & Pollock (1978)"

    validationPopulations:

        - description:
            "Homens adultos da amostra independente de validação cruzada"

          country:
            "Estados Unidos"

          region:
            "Carolina do Norte e Texas"

          sexCoverage:
            - MALE

          ageCoverage:
            min: 18
            max: 59

          sampleSize:
            95

          bodyCharacteristicsNotes:
            "Percentual de gordura médio de 18,7%, com faixa de aproximadamente 1% a 33%."

          sampleCharacteristics:
            "Amostra independente utilizada para validação cruzada das equações derivadas na amostra de 308 homens."

          source:
            "Jackson & Pollock (1978)"
```

O estudo reuniu 403 homens adultos e dividiu aleatoriamente o conjunto em uma amostra de desenvolvimento de 308 homens e uma amostra de validação cruzada de 95 homens. A coleta foi realizada em dois laboratórios, na Wake Forest University, em Winston-Salem, Carolina do Norte, e no Institute for Aerobics Research, em Dallas, Texas.

A Tabela 1 da publicação apresenta, para a amostra de desenvolvimento, idade média de 32,6 anos, percentual de gordura médio de 17,7% e soma média das três dobras de 59,4 mm. Para a amostra de validação cruzada, apresenta idade média de 33,3 anos, percentual de gordura médio de 18,7% e soma média das três dobras de 59,2 mm.

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

A fonte informa que os participantes apresentavam ampla variação nos hábitos de exercício, mas não fornece uma categorização compatível com a semântica operacional de atleta e não atleta adotada pela plataforma.

Portanto, não há base documental suficiente para substituir `null` por `true` ou `false`.

---

## 6. Aplicabilidade por Nível de Treinamento

```text
trainingLevel:

    supportedLevels:
        []

    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

A publicação não fornece categorização compatível com:

```text
SEDENTARY
RECREATIONAL
TRAINED
COMPETITIVE
ELITE
```

---

## 7. Aplicabilidade por Modalidade

```text
modality:

    supportedModalities:
        []

    notes:
        "Não documentado em termos de modalidades esportivas específicas."
```

---

## 8. Características Corporais

```text
bodyCharacteristics:

    rules:
        []
```

A publicação descreve ampla variação de estrutura corporal e composição corporal na amostra. Essas características permanecem descritivas e não devem ser convertidas automaticamente em `ScientificRestriction`.

---

## 9. Evidências de Validação

### 9.1 Evidências de Desenvolvimento

```text
development:

    studyReference:

        citation:
            "Jackson, A. S.; Pollock, M. L. (1978).
             Generalized equations for predicting body density of men.
             British Journal of Nutrition, 40(3), 497–504."

        doi:
            "10.1079/BJN19780152"

        url:
            "https://pubmed.ncbi.nlm.nih.gov/718832/"

    population:
        "Homens adultos, n = 308, 18–61 anos"

    criterionMethod:
        "Densidade corporal determinada pelo método hidrostático."

    year:
        1978

    equationNumber:
        5

    metrics:

        R:
            0.905

        R2:
            null

        standardError:
            0.0077

    notes:
        "R² não foi reportado explicitamente na Tabela 4; por isso permanece null. A Tabela 4 reporta R = 0.905 e erro padrão (SE) = 0.0077 para a equação nº 5."
```

A amostra de 308 homens foi utilizada para derivar as equações por regressão. Para a variante de três dobras, a fonte avaliou a relação entre densidade corporal e a soma das três dobras, incluindo o termo linear, o termo quadrático e a idade.

A publicação informa que foram selecionados os sítios do peito, abdômen e coxa porque apresentavam alta correlação com a soma de sete dobras e permitiam um teste de campo mais viável.

---

## 10. Validação

```text
validationStudies:
    []
```

A publicação original não apresenta uma subamostra separada utilizada para testar a equação após seu desenvolvimento dentro da mesma etapa de desenvolvimento. A amostra adicional de 95 homens foi classificada pelos autores como `cross-validation` e, segundo a regra operacional desta documentação, permanece em `crossValidationStudies`.

---

## 11. Validação Cruzada

```text
crossValidationStudies:

    - studyReference:

        citation:
            "Jackson, A. S.; Pollock, M. L. (1978).
             Generalized equations for predicting body density of men.
             British Journal of Nutrition, 40(3), 497–504."

        doi:
            "10.1079/BJN19780152"

        url:
            "https://pubmed.ncbi.nlm.nih.gov/718832/"

      population:

        description:
            "Homens adultos da amostra independente de validação cruzada"

        country:
            "Estados Unidos"

        region:
            "Carolina do Norte e Texas"

        sexCoverage:
            - MALE

        ageCoverage:
            min: 18
            max: 59

        sampleSize:
            95

        bodyCharacteristicsNotes:
            "Percentual de gordura médio de 18,7%, com ampla variação."

        sampleCharacteristics:
            "Amostra independente do estudo original utilizada para validação cruzada das equações derivadas na amostra de 308 homens."

      criterionMethod:
          "Densidade corporal determinada pelo método hidrostático."

      equationNumber:
          5

      metrics:

          correlation:
              0.917

          standardError:
              0.0077

          meanDifference:
              null

          rmse:
              null

          otherMetrics:
              null

      limitations:
          "Validação cruzada interna do estudo original; não constitui validação externa independente."
```

A Tabela 5 do artigo apresenta, especificamente para a equação nº 5 da soma de três dobras, correlação de `0.917` e erro padrão de `0.0077` na amostra independente de 95 homens.

A classificação como validação cruzada é consistente com o desenho metodológico descrito pelos autores: a equação foi derivada na amostra de 308 homens e posteriormente testada na segunda amostra independente de 95 homens.

---

## 12. Validação Externa

```text
externalValidationStudies:
    []
```

Nenhum estudo externo foi incorporado nesta ficha.

---

## 13. Requisitos de Medição

```text
inputs:

    requiredInputs:
        - AGE
        - SKINFOLD_PECTORAL
        - SKINFOLD_ABDOMEN
        - SKINFOLD_THIGH

    optionalInputs:
        []
```

A soma utilizada pela `JP3-M` é composta por:

```text
SKINFOLD_PECTORAL
SKINFOLD_ABDOMEN
SKINFOLD_THIGH
```

A publicação original descreve esses três sítios como peito, abdômen e coxa.

A separação entre aplicabilidade e entradas matemáticas permanece:

```text
SEX
    → applicability.sex

AGE
    → variável matemática da fórmula

3 skinfolds
    → variáveis matemáticas da fórmula
```

`SEX` não é incluído em `requiredInputs` porque não aparece como variável matemática explícita da equação.

`SKINFOLD_PECTORAL` é utilizado como identificador canônico no lugar de `SKINFOLD_CHEST`, conforme a decisão de normalização da documentação, sem tratar o sítio como uma medida adicional independente.

---

## 14. Restrições Científicas

```text
restrictions:
    []
```

A faixa etária observada no desenvolvimento descreve a população estudada, mas não é transformada automaticamente em `ScientificRestriction`.

A publicação caracteriza a equação como válida para homens adultos que variavam em idade e gordura corporal, sem estabelecer uma proibição operacional adicional que precise ser registrada como restrição científica.

---

## 15. Conflito de Fonte

```text
sourceConflict:
    null
```

A definição matemática da `JP3-M` é consistente entre a publicação original e as fontes secundárias consultadas:

```text
D =

    1.10938

    - 0.0008267 × Σ3

    + 0.0000016 × Σ3²

    - 0.0002574 × AGE
```

Não foi identificado conflito científico material entre fontes para os coeficientes, as três variáveis da soma ou a população de desenvolvimento da variante.

A diferença observada entre a faixa geral de 18–61 anos e a faixa de 18–59 anos da amostra de validação cruzada não constitui `sourceConflict`; trata-se de uma característica distinta das duas amostras do mesmo estudo.

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

A variante possui as informações essenciais necessárias para publicação operacional:

```text
identity                       ✓

mathematicalDefinition         ✓

supportedSexes                ✓

developmentAgeRange            ✓

originalPopulation             ✓

requiredInputs                 ✓

reference                      ✓

sourceConflict                 null
```

Informações contextuais não documentadas permanecem como `null` ou listas vazias conforme o schema e não impedem `ACTIVE` por si só.

---

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:
            "JP3-M"

        familyId:
            "jackson-pollock"

        displayName:
            "Jackson & Pollock 3 Dobras — Masculino"

        aliasNames:
            [
                "Jackson-Pollock 3-Site — Male",
                "JP3 Male",
                "3-Site Jackson-Pollock"
            ]

    mathematicalDefinition:

        formula:
            "D = 1.1093800 - 0.0008267 × Σ3 + 0.0000016 × Σ3² - 0.0002574 × AGE"

        sum3Definition:
            "Σ3 = SKINFOLD_PECTORAL + SKINFOLD_ABDOMEN + SKINFOLD_THIGH"

        outputType:
            BODY_DENSITY

        equationNumber:
            5

    applicability:

        sex:

            supportedSexes:
                - MALE

        age:

            originalDevelopmentAgeRange:
                min: 18
                max: 61

            developmentSampleMeanAge:
                32.6

            validatedAgeRanges:
                - range:
                    min: 18
                    max: 59
                  population:
                    "Homens adultos da amostra independente de validação cruzada"
                  source:
                    "Jackson & Pollock (1978)"

            explicitAgeRestriction:
                null

    population:

        originalPopulation:

            description:
                "Homens adultos"

            country:
                "Estados Unidos"

            region:
                "Carolina do Norte e Texas"

            sexCoverage:
                - MALE

            ageCoverage:
                min: 18
                max: 61

            sampleSize:
                308

            bodyCharacteristicsNotes:
                "Percentual de gordura médio de 17,7%, com faixa aproximada de 1% a 33%."

            sampleCharacteristics:
                "Grande variação de estrutura corporal, composição corporal e hábitos de exercício."

            source:
                "Jackson & Pollock (1978)"

        validationPopulations:

            - description:
                "Homens adultos da amostra independente de validação cruzada"

              country:
                "Estados Unidos"

              region:
                "Carolina do Norte e Texas"

              sexCoverage:
                - MALE

              ageCoverage:
                min: 18
                max: 59

              sampleSize:
                95

              bodyCharacteristicsNotes:
                "Percentual de gordura médio de 18,7%."

              sampleCharacteristics:
                "Amostra independente utilizada para validação cruzada."

              source:
                "Jackson & Pollock (1978)"

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
                    "Jackson, A. S.; Pollock, M. L. (1978).
                     Generalized equations for predicting body density of men.
                     British Journal of Nutrition, 40(3), 497–504."

                doi:
                    "10.1079/BJN19780152"

                url:
                    "https://pubmed.ncbi.nlm.nih.gov/718832/"

            population:
                "Homens adultos, n = 308, 18–61 anos"

            criterionMethod:
                "Densidade corporal determinada pelo método hidrostático."

            year:
                1978

            equationNumber:
                5

            metrics:

                R:
                    0.905

                R2:
                    null

                standardError:
                    0.0077

            notes:
                "R² não foi reportado explicitamente na publicação."

        validationStudies:
            []

        crossValidationStudies:

            - studyReference:

                citation:
                    "Jackson, A. S.; Pollock, M. L. (1978).
                     Generalized equations for predicting body density of men.
                     British Journal of Nutrition, 40(3), 497–504."

                doi:
                    "10.1079/BJN19780152"

                url:
                    "https://pubmed.ncbi.nlm.nih.gov/718832/"

              population:
                "Homens adultos, n = 95, 18–59 anos"

              criterionMethod:
                "Densidade corporal determinada pelo método hidrostático."

              equationNumber:
                5

              metrics:

                correlation:
                    0.917

                standardError:
                    0.0077

                meanDifference:
                    null

                rmse:
                    null

                otherMetrics:
                    null

              limitations:
                "Validação cruzada interna do estudo original."

        externalValidationStudies:
            []

    sourceConflict:
        null

    inputs:

        requiredInputs:
            - AGE
            - SKINFOLD_PECTORAL
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

---

## 18. Interpretação para o SuggestionEngine

A ficha produz uma interpretação operacional:

```text
sexo:

    MALE
        → compatível

    FEMALE
        → incompatível

idade:

    população de desenvolvimento = 18–61

    amostra de validação cruzada = 18–59

    média da amostra de desenvolvimento = 32.6

    média NÃO é limite

atleta:

    NOT_DOCUMENTED

trainingLevel:

    NOT_DOCUMENTED

modality:

    NOT_DOCUMENTED

inputs:

    AGE
    + SKINFOLD_PECTORAL
    + SKINFOLD_ABDOMEN
    + SKINFOLD_THIGH

output:

    BODY_DENSITY
```

A variante deve ser interpretada como uma equação para homens adultos, derivada em uma amostra de 308 homens e submetida a validação cruzada em uma segunda amostra independente de 95 homens.

A presença de hábitos de exercício variados na amostra não é suficiente para classificar a variante como específica para atletas ou não atletas.

---

## 19. Referência

```text
Reference

    citation:

        "Jackson, A. S.; Pollock, M. L. (1978).
         Generalized equations for predicting body density of men.
         British Journal of Nutrition, 40(3), 497–504."

    doi:

        "10.1079/BJN19780152"

    url:

        "https://pubmed.ncbi.nlm.nih.gov/718832/"
```

A publicação original de Jackson & Pollock (1978) apresenta a equação nº 5 para três dobras cutâneas, com `R = 0.905` e `SE = 0.0077` na amostra de desenvolvimento. Na validação cruzada da mesma equação em 95 homens, a Tabela 5 reporta correlação de `0.917` e `SE = 0.0077`.