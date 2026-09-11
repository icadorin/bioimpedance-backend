# EquationVariantScientificProfile — G-M8

## 1. Identificação

```text
identity:

    variantId:

        "G-M8"

    familyId:

        "guedes"

    displayName:

        "Guedes 8 Dobras — Masculino"

    aliasNames:

        [

            "Guedes GM8",

            "GM8"

        ]
```

A variante G-M8 corresponde à oitava equação específica do conjunto masculino desenvolvido por Dartagnan Pinto Guedes para estimativa da densidade corporal em universitários. A fonte original de desenvolvimento é o estudo de Guedes realizado na Universidade Federal de Santa Maria em 1985. A série masculina contém oito equações de regressão, e a G-M8 utiliza oito sítios de dobras cutâneas, correspondentes à oitava equação da série.

Referência principal de desenvolvimento:

```text
Guedes, D. P. (1985).

Estudo da gordura corporal através da mensuração dos valores de

densidade corporal e da espessura de dobras cutâneas de universitários.

Dissertação de Mestrado, Mestrado em Educação Física,

Universidade Federal de Santa Maria, Santa Maria, RS.
```

A publicação de 1985 registra uma amostra de 206 universitários, sendo 110 homens e 96 mulheres, com idade entre 17 e 27 anos. A base masculina utilizada nas equações específicas foi composta por 110 homens.

---

## 2. Definição Matemática

A definição matemática da `G-M8` é:

```text
D =

    1.22627

    - 0.08384 × log10(Σ8)
```

Onde:

```text
D

    = BODY_DENSITY

Σ8

    = soma dos oito sítios de dobras cutâneas utilizados pela G-M8
```

A soma é composta por:

```text
Σ8 =

    SKINFOLD_ABDOMEN

    + SKINFOLD_TRICEPS

    + SKINFOLD_SUBSCAPULAR

    + SKINFOLD_SUPRAILIAC

    + SKINFOLD_AXILLARY_MID

    + SKINFOLD_THIGH

    + SKINFOLD_MEDIAL_CALF
```

A literatura que reproduz o quadro original apresenta a G-M8 como a oitava equação da série Guedes de 1985, com coeficientes `1,22627` e `0,08384`, correlação `0,90`, erro padrão de estimativa `0,0055`, `n = 110` e faixa etária de `17–27` anos.

A G-M8 utiliza oito sítios de dobras cutâneas. A G-M7 utiliza os mesmos sete primeiros sítios, sem `SKINFOLD_BICEPS`; portanto, as duas equações não possuem o mesmo conjunto de inputs e, além disso, possuem coeficientes próprios. Portanto, não deve ser fundida com a G-M7 apenas por compartilhar as mesmas variáveis matemáticas.

### Output

```text
outputType = BODY_DENSITY
```

### Forma computacional

```text
sum8 =

    SKINFOLD_ABDOMEN

    + SKINFOLD_TRICEPS

    + SKINFOLD_SUBSCAPULAR

    + SKINFOLD_SUPRAILIAC

    + SKINFOLD_AXILLARY_MID

    + SKINFOLD_THIGH

    + SKINFOLD_MEDIAL_CALF

    + SKINFOLD_BICEPS

bodyDensity =

    1.22627

    - 0.08384 × log10(sum8)
```

A conversão posterior de densidade corporal para percentual de gordura não faz parte da definição matemática desta variante.

---

## 3. Aplicabilidade

### 3.1 Sexo

```text
sex:

    supportedSexes:

        - MALE
```

A G-M8 pertence ao conjunto masculino das equações específicas desenvolvidas por Guedes. A população masculina do estudo original foi composta por 110 universitários.

---

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

A população masculina utilizada no desenvolvimento das equações específicas foi composta por universitários com idade entre 17 e 27 anos.

A faixa etária é uma característica da população de desenvolvimento e não deve ser convertida automaticamente em uma restrição explícita de inelegibilidade.

---

## 4. Aplicabilidade Populacional

```text
population:

    originalPopulation:

        description:

            "Homens universitários jovens"

        country:

            "Brasil"

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

            "Amostra masculina de universitários submetidos à determinação

             da densidade corporal e à mensuração de dobras cutâneas."

        sampleCharacteristics:

            "Universitários da Universidade Federal de Santa Maria (UFSM),

             no Rio Grande do Sul."

        source:

            "Guedes (1985)"

    validationPopulations:

        []
```

O estudo original avaliou 206 universitários da Universidade Federal de Santa Maria, sendo 110 homens e 96 mulheres, com idade entre 17 e 27 anos. A G-M8 pertence ao conjunto masculino desenvolvido a partir dos 110 homens.

A publicação original descreve a determinação da densidade corporal e a mensuração de diferentes dobras cutâneas como base para a construção das equações.

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

A população é descrita como universitários, mas as fontes consultadas não fornecem evidência suficiente para classificá-la, segundo a taxonomia da plataforma, como uma população explicitamente de atletas ou explicitamente de não atletas.

---

## 6. Aplicabilidade por Nível de Treinamento

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

Não foi identificada uma característica corporal explicitamente documentada que deva ser convertida em regra `SUPPORTED`, `WARNING` ou `INELIGIBLE` para a G-M8.

A G-M8 utiliza matematicamente os oito sítios de dobras cutâneas que compõem `Σ8`, incluindo `SKINFOLD_BICEPS`.

---

## 9. Evidências de Validação

### 9.1 Evidências de Desenvolvimento

```text
development:

    studyReference:

        citation:

            "Guedes, D. P. (1985).

             Estudo da gordura corporal através da mensuração dos valores de

             densidade corporal e da espessura de dobras cutâneas de universitários.

             Dissertação de Mestrado, Mestrado em Educação Física,

             Universidade Federal de Santa Maria, Santa Maria, RS."

        doi:

            null

        url:

            null

    population:

        "Homens universitários, n = 110, 17–27 anos"

    criterionMethod:

        "Determinação da densidade corporal por pesagem hidrostática"

    year:

        1985

    metrics:

        correlation:

            0.90

        r2:

            null

        standardError:

            0.0055

        otherMetrics:

            "A tabela de equações reproduzida na literatura informa n = 110

             e faixa etária de 17–27 anos para a G-M8."
```

A fonte de desenvolvimento da família é o estudo de Guedes realizado na Universidade Federal de Santa Maria. O registro institucional do estudo informa 206 participantes, sendo 110 homens, e idade entre 17 e 27 anos.

A tabela que reproduz as equações originais de Guedes apresenta para a G-M8 `r = 0,90`, erro padrão de estimativa `0,0055`, `n = 110` e idade `17–27`.

O campo `r2` permanece `null` porque a fonte consultada reporta a correlação `r`, mas não apresenta explicitamente o coeficiente `R²` da G-M8. Não é realizada conversão por inferência.

O método de referência utilizado no estudo foi a determinação da densidade corporal por pesagem hidrostática, conforme a descrição da metodologia do estudo de Guedes e as fontes que reproduzem sua base experimental.

---

## 10. Validação

```text
validationStudies:

    []
```

Não foi identificada, nas fontes consultadas para esta ficha, uma subamostra independente do mesmo estudo original que possa ser atribuída especificamente à G-M8 com informações suficientes para preencher uma entrada estruturada de `validationStudies`.

A publicação de Guedes & Sampedro de 1985 é um trabalho relacionado à tentativa de validação das equações, mas sua existência, isoladamente, não é suficiente para reconstruir uma subamostra específica da G-M8 segundo a regra operacional desta documentação.

---

## 11. Validação Cruzada

```text
crossValidationStudies:

    []
```

Não foi identificada, nas fontes consultadas, uma população de outro estudo ou pesquisador cuja aplicação da G-M8 possa ser documentada com segurança nesta ficha como `crossValidationStudies`.

---

## 12. Validação Externa

```text
externalValidationStudies:

    []
```

Não foi incorporado nesta ficha um estudo de validação externa com resultados especificamente atribuíveis à G-M8.

Validações posteriores da família Guedes somente devem ser adicionadas nesta seção quando o resultado específico da G-M8 estiver inequivocamente identificado.

---

## 13. Requisitos de Medição

```text
inputs:

    requiredInputs:

        - SKINFOLD_ABDOMEN

        - SKINFOLD_TRICEPS

        - SKINFOLD_SUBSCAPULAR

        - SKINFOLD_SUPRAILIAC

        - SKINFOLD_AXILLARY_MID

        - SKINFOLD_THIGH

        - SKINFOLD_MEDIAL_CALF

    optionalInputs:

        []
```

A equação não contém idade como variável matemática.

Portanto:

```text
SEX

    → Applicability

AGE

    → Applicability / comparação populacional

8 skinfolds

    → Formula inputs
```

A G-M8 utiliza os seguintes identificadores canônicos:

```text
SKINFOLD_ABDOMEN
SKINFOLD_TRICEPS
SKINFOLD_SUBSCAPULAR
SKINFOLD_SUPRAILIAC
SKINFOLD_AXILLARY_MID
SKINFOLD_THIGH
SKINFOLD_MEDIAL_CALF
```

A literatura que reproduz a série de Guedes mostra que a G-M8 utiliza a combinação `AB + TR + SE + SI + AX + CXM + PM`.

O local correspondente a `SKINFOLD_BICEPS` integra a fórmula específica da G-M8 e deve ser incluído em `requiredInputs`.

---

## 14. Restrições Científicas

```text
restrictions:

    []
```

A faixa etária de 17–27 anos caracteriza a população de desenvolvimento e, na ausência de uma regra científica explícita de proibição, não é convertida diretamente em uma restrição `INELIGIBLE`.

Não foram identificadas outras restrições científicas específicas suficientemente documentadas para a G-M8.

---

## 15. Conflito de Fonte

```text
sourceConflict:

    null
```

A evidência consultada permite identificar a G-M8 como uma equação específica de Guedes de 1985, com coeficientes `1,22627` e `0,08384`, população masculina de `n = 110` e faixa etária de `17–27` anos.

A diferença bibliográfica encontrada entre diferentes citações da família Guedes não foi tratada como conflito da G-M8. A fonte de desenvolvimento do estudo é o trabalho de Dartagnan Pinto Guedes de 1985, enquanto Guedes & Sampedro (1985) corresponde a uma publicação posterior relacionada à tentativa de validação.

G-M7 e G-M8 constituem equações distintas dentro do conjunto de Guedes. A G-M8 acrescenta `SKINFOLD_BICEPS` e possui coeficientes próprios.

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

A variante possui definição matemática, sexo, população de desenvolvimento, faixa etária, inputs e referência de desenvolvimento suficientes para permanecer `ACTIVE`.

A ausência de documentação específica sobre atleta, nível de treinamento, modalidade e validação externa permanece explicitamente registrada e não impede `ACTIVE`.

---

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:

            "G-M8"

        familyId:

            "guedes"

        displayName:

            "Guedes 8 Dobras — Masculino"

        aliasNames:

            [

                "Guedes GM8",

                "GM8"

            ]

    mathematicalDefinition:

        formula:

            "D = 1.22627 - 0.08384 × log10(Σ8)"

        variables:

            Σ8:

                "Soma de SKINFOLD_ABDOMEN + SKINFOLD_TRICEPS

                 + SKINFOLD_SUBSCAPULAR + SKINFOLD_SUPRAILIAC

                 + SKINFOLD_AXILLARY_MID + SKINFOLD_THIGH

                 + SKINFOLD_MEDIAL_CALF"

        outputType:

            BODY_DENSITY

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

                    "Homens universitários jovens"

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

                    "Amostra masculina de universitários submetidos à

                     determinação da densidade corporal e mensuração

                     de dobras cutâneas."

                sampleCharacteristics:

                    "Universitários da Universidade Federal de Santa Maria

                     (UFSM), no Rio Grande do Sul."

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

                "Não documentado."

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

                     e da espessura de dobras cutâneas de universitários.

                     Dissertação de Mestrado, Universidade Federal de Santa Maria."

                doi:

                    "10.5902/231654648617"

                url:

                    "https://doi.org/10.5902/231654648617"

            population:

                "Homens universitários, n = 110, 17–27 anos"

            criterionMethod:

                "Determinação da densidade corporal por pesagem hidrostática"

            year:

                1985

            metrics:

                correlation:

                    0.90

                r2:

                    null

                standardError:

                    0.0055

                otherMetrics:

                    "n = 110; faixa etária = 17–27 anos."

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

            - SKINFOLD_SUBSCAPULAR

            - SKINFOLD_SUPRAILIAC

            - SKINFOLD_AXILLARY_MID

            - SKINFOLD_THIGH

            - SKINFOLD_MEDIAL_CALF

            - SKINFOLD_BICEPS

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
sexo:

    MALE → compatível

    FEMALE → incompatível

idade:

    população de desenvolvimento = 17–27 anos

    faixa populacional NÃO é, por si só, restrição explícita

atleta:

    NOT_DOCUMENTED

trainingLevel:

    NOT_DOCUMENTED

modality:

    NOT_DOCUMENTED

inputs:

    8 skinfolds

output:

    BODY_DENSITY
```

O motor deve distinguir entre a faixa etária documentada da população e uma restrição formal de inelegibilidade.

O nome G-M8 identifica a oitava equação da série e, nesta variante, também corresponde a oito inputs de dobras cutâneas.

A ausência de documentação específica sobre atleta, treinamento e modalidade não deve ser convertida em inelegibilidade.

---

## 19. Referência

```text
Reference

    citation:

        "Guedes, D. P. (1985).

         Estudo da gordura corporal através da mensuração dos valores de

         densidade corporal e da espessura de dobras cutâneas de universitários.

         Dissertação de Mestrado, Mestrado em Educação Física,

         Universidade Federal de Santa Maria, Santa Maria, RS."

    doi:

        "10.5902/231654648617"

    url:

        "https://doi.org/10.5902/231654648617"
```

Fonte bibliográfica adicional:

```text
Guedes, D. P.; Sampedro, R. M. F. (1985).

Tentativa de validação de equações para predição dos valores de densidade

corporal com base nas espessuras de dobras cutâneas em universitários.

Revista Brasileira de Ciências do Esporte, 6(3), 182–191.
```

Essa publicação é mantida como fonte bibliográfica adicional relacionada à tentativa de validação das equações, sem ser utilizada para substituir a fonte de desenvolvimento da G-M8.

Fonte bibliográfica adicional que reproduz explicitamente a G-M8 e seus indicadores de desenvolvimento:

```text
Moura, J. A. R.; Rech, C. R.; Fonseca, P. H. S.; Zinn, J. L. (2003).

Validação de equações para estimativa da densidade corporal em atletas de
futebol categoria Sub-20.

Revista Brasileira de Cineantropometria & Desempenho Humano, 5(2), 22–32.

URL: https://periodicos.ufsc.br/index.php/rbcdh/article/download/3946/3348/0
```