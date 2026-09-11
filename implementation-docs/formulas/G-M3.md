# EquationVariantScientificProfile — G-M3

## 1. Identificação

```text
identity:
    variantId:
        "G-M3"

    familyId:
        "guedes"

    displayName:
        "Guedes 3 Dobras — Masculino"

    aliasNames:
        []
```

A variante G-M3 pertence ao conjunto de equações específicas de Guedes & Guedes para estimativa da densidade corporal em homens adultos jovens.

Referência principal:

```text
Guedes, D. P.; Guedes, J. E. (1991).

Proposição de equações para predição da quantidade de gordura corporal
em adultos jovens.

Semina: Ciências Biológicas e da Saúde, 12(2), 61–70.

DOI: 10.5433/1679-0367.1991v12n2p61
```

A publicação de 1991 descreve equações de regressão para estimar a densidade corporal a partir de espessuras de dobras cutâneas em adultos jovens. O artigo informa uma amostra de 110 homens e 96 mulheres com idade entre 18 e 30 anos e descreve uma amostra diferente de 41 sujeitos, de idade e características físicas semelhantes, utilizada para testar as equações.

A literatura posterior também reproduz a G-M3 com os mesmos coeficientes e a identifica como uma das equações específicas de Guedes & Guedes.

---

## 2. Definição Matemática

A definição matemática da variante é:

```text
D =

    1.17136

    - 0.06706 × log10(Σ3)
```

Onde:

```text
D

    = BODY_DENSITY

Σ3

    = soma das três dobras cutâneas
```

As três dobras utilizadas são:

```text
- tríceps
- supra-ilíaca
- abdômen
```

A equação é reproduzida na literatura científica como:

```text
Dc = 1.17136 - 0.06706 × log10(DCAB + DCTR + DCSI)
```

A forma computacional é:

```text
sum3 =

    SKINFOLD_TRICEPS

    + SKINFOLD_SUPRAILIAC

    + SKINFOLD_ABDOMEN

bodyDensity =

    1.17136

    - 0.06706 × log10(sum3)
```

```text
outputType = BODY_DENSITY
```

As estatísticas associadas ao desenvolvimento reproduzidas para a G-M3 são:

```text
r  = 0.894
EPE = 0.0057
```

Essas métricas são estatísticas do desenvolvimento da equação, não resultados da validação cruzada posterior de 2015.

---

## 3. Aplicabilidade

### 3.1 Sexo

```text
sex:
    supportedSexes:
        - MALE
```

A G-M3 é a variante masculina correspondente à equação de três dobras do conjunto específico de Guedes & Guedes.

### 3.2 Idade

```text
age:
    originalDevelopmentAgeRange:
        min: 18
        max: 30

    developmentSampleMeanAge:
        null

    validatedAgeRanges:
        - range:
            min: null
            max: null
          population:
            "Amostra independente de 41 sujeitos, descrita como tendo idade semelhante à amostra de desenvolvimento"
          source:
            "Guedes & Guedes (1991)"

    explicitAgeRestriction:
        null
```

A publicação original disponível no registro bibliográfico do artigo informa idade de 18 a 30 anos para a amostra de desenvolvimento. A média de idade específica dos homens não foi localizada de forma suficientemente específica para esta variante e, portanto, permanece `null`.

A idade não participa matematicamente da G-M3. A faixa de 18–30 anos descreve a população de desenvolvimento e não deve ser convertida automaticamente em `explicitAgeRestriction`.

A publicação original informa ainda uma amostra diferente de 41 sujeitos com idade e características físicas semelhantes, mas não fornece, no resumo disponível, uma faixa etária específica nem uma divisão por sexo dessa amostra.

---

## 4. Aplicabilidade Populacional

```text
population:
    originalPopulation:
        description:
            "Homens adultos jovens brasileiros"

        country:
            "Brazil"

        region:
            null

        sexCoverage:
            - MALE

        ageCoverage:
            min: 18
            max: 30

        sampleSize:
            110

        bodyCharacteristicsNotes:
            null

        sampleCharacteristics:
            "Subamostra masculina do estudo de Guedes & Guedes; a publicação
             informa 110 homens e 96 mulheres na amostra de desenvolvimento."

        source:
            "Guedes & Guedes (1991)"

    validationPopulations:
        - description:
            "Amostra independente de 41 sujeitos utilizada para testar as equações"

          country:
            "Brazil"

          region:
            null

          sexCoverage:
            []

          ageCoverage:
            min: null
            max: null

          sampleSize:
            41

          bodyCharacteristicsNotes:
            null

          sampleCharacteristics:
            "Amostra diferente da original, descrita como tendo idade e
             características físicas semelhantes; a composição por sexo não foi
             especificada no resumo disponível."

          source:
            "Guedes & Guedes (1991)"
```

O estudo original foi realizado em adultos jovens brasileiros e utilizou pesagem hidrostática como método de referência para a determinação da densidade corporal.

A documentação bibliográfica disponível apresenta uma pequena inconsistência no tamanho total da amostra do artigo, com registros que informam 205 ou 206 participantes. Essa diferença não altera o tamanho da subamostra masculina de 110 participantes utilizado para a descrição da G-M3 e não é tratada como conflito da equação.

A amostra independente de 41 sujeitos é mantida separada da população de desenvolvimento. A fonte consultada não permite identificar com segurança quantos participantes eram homens, portanto `sexCoverage` permanece vazio e a composição sexual não é inferida.

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

A documentação da publicação original não fornece informação suficiente para classificar formalmente a amostra de desenvolvimento como atleta ou não atleta segundo o modelo operacional da plataforma.

A ausência dessa informação não deve ser convertida em restrição.

---

## 6. Aplicabilidade por Nível de Treinamento

```text
trainingLevel:
    supportedLevels:
        []

    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

Não deve ser inferido mapeamento para `SEDENTARY`, `RECREATIONAL`, `TRAINED`, `COMPETITIVE` ou `ELITE`.

---

## 7. Aplicabilidade por Modalidade

```text
modality:
    supportedModalities:
        []

    notes:
        "Não documentado em termos de modalidades esportivas específicas."
```

Não foi identificada modalidade esportiva específica de desenvolvimento da G-M3 na fonte primária utilizada para esta ficha.

---

## 8. Características Corporais

```text
bodyCharacteristics:
    rules:
        []
```

Não foi identificada, na documentação utilizada, uma regra explícita que justifique cadastrar condições de suporte, alerta ou inelegibilidade relacionadas a gordura corporal, massa muscular ou valores extremos das dobras.

As características observadas nas amostras não devem ser transformadas automaticamente em `BodyCharacteristicRule`.

---

## 9. Evidências de Validação

### 9.1 Evidências de Desenvolvimento

```text
development:
    studyReference:
        citation:
            "Guedes, D. P.; Guedes, J. E. (1991).
             Proposição de equações para predição da quantidade de gordura
             corporal em adultos jovens.
             Semina: Ciências Biológicas e da Saúde, 12(2), 61–70."

        doi:
            "10.5433/1679-0367.1991v12n2p61"

        url:
            "https://doi.org/10.5433/1679-0367.1991v12n2p61"

    population:
        "Homens adultos jovens, n = 110, 18–30 anos"

    criterionMethod:
        "Pesagem hidrostática"

    year:
        1991

    metrics:
        correlation:
            0.894

        standardError:
            0.0057

        meanDifference:
            null

        rmse:
            null

        otherMetrics:
            null
```

A publicação de desenvolvimento utilizou pesagem hidrostática para determinar a densidade corporal de referência. As equações foram construídas a partir de medidas de espessuras de dobras cutâneas.

A G-M3 foi apresentada como:

```text
Dc = 1.17136 - 0.06706 × log10(DCAB + DCTR + DCSI)
```

As estatísticas de desenvolvimento reproduzidas na literatura são `r = 0.894` e `EPE = 0.0057`.

A fonte também afirma que as equações foram testadas em uma amostra diferente de 41 sujeitos com idade e características físicas semelhantes.

---

## 10. Validação

```text
validationStudies:
    - studyReference:
        citation:
            "Guedes, D. P.; Guedes, J. E. (1991).
             Proposição de equações para predição da quantidade de gordura
             corporal em adultos jovens.
             Semina: Ciências Biológicas e da Saúde, 12(2), 61–70."

        doi:
            "10.5433/1679-0367.1991v12n2p61"

        url:
            "https://doi.org/10.5433/1679-0367.1991v12n2p61"

      population:
        description:
            "Amostra independente de 41 sujeitos utilizada no próprio estudo"

        country:
            "Brazil"

        region:
            null

        sexCoverage:
            []

        ageCoverage:
            min: null
            max: null

        sampleSize:
            41

        bodyCharacteristicsNotes:
            null

        sampleCharacteristics:
            "Grupo diferente da amostra de desenvolvimento, descrito como
             tendo idade e características físicas semelhantes. A composição
             por sexo não foi especificada no resumo disponível."

        source:
            "Guedes & Guedes (1991)"

      criterionMethod:
          "Pesagem hidrostática"

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
            null

      limitations:
          "O resumo disponível confirma a utilização de uma amostra diferente
           de 41 sujeitos, mas não apresenta métricas específicas atribuíveis
           individualmente à G-M3 nem sua composição por sexo."
```

A amostra de 41 sujeitos permanece em `validationStudies` porque pertence ao mesmo estudo de desenvolvimento e foi utilizada para testar as equações propostas naquele trabalho.

A fonte disponível não fornece resultados específicos da G-M3 para esse grupo. Portanto, não são distribuídas artificialmente métricas entre as oito equações do conjunto.

---

## 11. Validação Cruzada

```text
crossValidationStudies:
    - studyReference:
        citation:
            "Both, D. R.; Matheus, S. C.; Behenck, M. S.
             Validação de equações antropométricas específicas e generalizadas
             para estimativa do percentual de gordura corporal em estudantes
             de Educação Física do sexo masculino.
             Revista Brasileira de Educação Física e Esporte, 29(1), 2015."

        doi:
            "10.1590/1807-55092015000100013"

        url:
            "https://www.scielo.br/j/rbefe/a/ZNXBmLtPMNWnt9nwSwv4qZH/"

      population:
        description:
            "Universitários do sexo masculino do curso de Educação Física"

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
            "104 universitários de Educação Física, idade média de 21,7 ± 2,7 anos,
             submetidos à pesagem hidrostática e à antropometria. A amostra foi
             composta por voluntários que atendiam aos critérios de inclusão do estudo."

        source:
            "Both, Matheus & Behenck (2015)"

      criterionMethod:
          "Pesagem hidrostática"

      metrics:
        correlation:
            0.80

        standardError:
            3.8

        meanDifference:
            null

        rmse:
            null

        otherMetrics:
            "r² = 0,64; t = -6,856; p < 0,001; EC = 2,4%; ET = 24,3%.
             As métricas de EPE, EC e ET foram reportadas após a conversão
             dos valores de densidade corporal para percentual de gordura corporal."

      limitations:
          "A G-M3 apresentou diferença estatisticamente significativa em relação
           à pesagem hidrostática e não atendeu aos critérios de validade cruzada
           adotados pelos autores. O estudo também relata tendência de
           superestimação do percentual de gordura pelas equações de Guedes e Guedes."
```

O estudo de 2015 é uma validação cruzada independente, conduzida por pesquisadores diferentes e com uma nova amostra de 104 homens. Ele testou as equações específicas de Guedes & Guedes em universitários de Educação Física.

Para a G-M3, foram reportados:

```text
r   = 0.80
r²  = 0.64
t   = -6.856
p   < 0.001
EPE = 3.8%
EC  = 2.4%
ET  = 24.3%
```

O estudo utilizou a pesagem hidrostática como referência. Os valores das equações foram convertidos para percentual de gordura corporal para a comparação estatística.

A G-M3 apresentou diferença estatisticamente significativa em relação ao método de referência e, portanto, não foi considerada válida segundo os critérios de validação cruzada adotados no estudo.

---

## 12. Validação Externa

```text
externalValidationStudies:
    []
```

Não foi incorporado, nesta ficha, estudo que atenda ao conceito de validação externa totalmente independente fora dos estudos de desenvolvimento e dos estudos de validação cruzada identificados.

---

## 13. Requisitos de Medição

```text
inputs:
    requiredInputs:
        - SKINFOLD_TRICEPS
        - SKINFOLD_SUPRAILIAC
        - SKINFOLD_ABDOMEN

    optionalInputs:
        []
```

A G-M3 utiliza exclusivamente três espessuras de dobras cutâneas como variáveis matemáticas:

```text
SKINFOLD_TRICEPS
SKINFOLD_SUPRAILIAC
SKINFOLD_ABDOMEN
```

A idade não participa matematicamente da equação e, portanto, `AGE` não deve aparecer em `requiredInputs`.

`SEX` também não é variável matemática da equação e permanece exclusivamente em `applicability.sex`.

A separação operacional é:

```text
SEX

    → Applicability

AGE

    → Population / Evidence context

    → NÃO é Formula input

3 skinfolds

    → Formula inputs
```

A G-M3 não utiliza massa corporal nem estatura como variáveis matemáticas.

---

## 14. Restrições Científicas

```text
restrictions:
    []
```

Não foi identificada, nas fontes utilizadas, uma restrição científica adicional que justifique o cadastro de uma `ScientificRestriction` específica.

A faixa de 18–30 anos permanece documentada como característica da população de desenvolvimento, sem ser transformada automaticamente em `explicitAgeRestriction`.

A ausência de documentação sobre atletas, nível de treinamento ou modalidade não deve ser convertida em inelegibilidade.

---

## 15. Conflito de Fonte

```text
evidence:
    sourceConflict:
        null
```

Não foi identificado conflito material entre fontes quanto aos coeficientes ou às variáveis matemáticas da G-M3:

```text
D = 1.17136 - 0.06706 × log10(
    SKINFOLD_ABDOMEN
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
)
```

Existe uma questão bibliográfica sobre a datação histórica da série de Guedes: a equação é frequentemente citada em materiais posteriores como "Guedes (1985)", enquanto a publicação científica de Guedes & Guedes que documenta o conjunto específico de equações para adultos jovens foi publicada em 1991.

Essa diferença de citação histórica não é tratada como `sourceConflict` da G-M3 porque não foram identificados, para os mesmos coeficientes, dois valores primários conflitantes que exijam resolução dentro da variante.

A ficha utiliza a publicação de 1991 como referência de definição e desenvolvimento da variante documentada aqui.

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

Os campos essenciais para `ACTIVE` estão disponíveis:

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

As informações que não foram documentadas nas fontes permanecem explicitamente como `null` ou listas vazias.

---

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile

    identity:
        variantId:
            "G-M3"

        familyId:
            "guedes"

        displayName:
            "Guedes 3 Dobras — Masculino"

        aliasNames:
            []

    applicability:
        sex:
            supportedSexes:
                - MALE

        age:
            originalDevelopmentAgeRange:
                min: 18
                max: 30

            developmentSampleMeanAge:
                null

            validatedAgeRanges:
                - range:
                    min: null
                    max: null

                  population:
                    "Amostra independente de 41 sujeitos, com idade descrita como semelhante à amostra original"

                  source:
                    "Guedes & Guedes (1991)"

            explicitAgeRestriction:
                null

        population:
            originalPopulation:
                description:
                    "Homens adultos jovens brasileiros"

                country:
                    "Brazil"

                region:
                    null

                sexCoverage:
                    - MALE

                ageCoverage:
                    min: 18
                    max: 30

                sampleSize:
                    110

                bodyCharacteristicsNotes:
                    null

                sampleCharacteristics:
                    "Subamostra masculina do estudo de Guedes & Guedes."

                source:
                    "Guedes & Guedes (1991)"

            validationPopulations:
                - description:
                    "Amostra independente de 41 sujeitos utilizada para testar as equações"

                  country:
                    "Brazil"

                  region:
                    null

                  sexCoverage:
                    []

                  ageCoverage:
                    min: null
                    max: null

                  sampleSize:
                    41

                  bodyCharacteristicsNotes:
                    null

                  sampleCharacteristics:
                    "Idade e características físicas semelhantes às da amostra original; composição por sexo não especificada."

                  source:
                    "Guedes & Guedes (1991)"

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
                    "Guedes, D. P.; Guedes, J. E. (1991).
                     Proposição de equações para predição da quantidade de gordura corporal em adultos jovens.
                     Semina: Ciências Biológicas e da Saúde, 12(2), 61–70."

                doi:
                    "10.5433/1679-0367.1991v12n2p61"

                url:
                    "https://doi.org/10.5433/1679-0367.1991v12n2p61"

            population:
                "Homens adultos jovens, n = 110, 18–30 anos"

            criterionMethod:
                "Pesagem hidrostática"

            year:
                1991

            metrics:
                correlation:
                    0.894

                standardError:
                    0.0057

                meanDifference:
                    null

                rmse:
                    null

                otherMetrics:
                    null

        validationStudies:
            - studyReference:
                citation:
                    "Guedes, D. P.; Guedes, J. E. (1991).
                     Proposição de equações para predição da quantidade de gordura corporal em adultos jovens.
                     Semina: Ciências Biológicas e da Saúde, 12(2), 61–70."

                doi:
                    "10.5433/1679-0367.1991v12n2p61"

                url:
                    "https://doi.org/10.5433/1679-0367.1991v12n2p61"

              population:
                "Amostra independente de 41 sujeitos; composição sexual específica não determinada."

              criterionMethod:
                "Pesagem hidrostática"

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
                    null

              limitations:
                "Métricas específicas da G-M3 não identificadas no material primário disponível."

        crossValidationStudies:
            - studyReference:
                citation:
                    "Both, D. R.; Matheus, S. C.; Behenck, M. S.
                     Validação de equações antropométricas específicas e generalizadas
                     para estimativa do percentual de gordura corporal em estudantes
                     de Educação Física do sexo masculino.
                     Revista Brasileira de Educação Física e Esporte, 29(1), 2015."

                doi:
                    "10.1590/1807-55092015000100013"

                url:
                    "https://www.scielo.br/j/rbefe/a/ZNXBmLtPMNWnt9nwSwv4qZH/"

              population:
                "104 universitários do sexo masculino, 18–30 anos, estudantes de Educação Física."

              criterionMethod:
                "Pesagem hidrostática"

              metrics:
                correlation:
                    0.80

                standardError:
                    3.8

                meanDifference:
                    null

                rmse:
                    null

                otherMetrics:
                    "r² = 0,64; t = -6,856; p < 0,001; EC = 2,4%; ET = 24,3%."

              limitations:
                "A G-M3 apresentou diferença estatisticamente significativa em relação à pesagem hidrostática e não atendeu aos critérios de validação cruzada adotados no estudo."

        externalValidationStudies:
            []

        sourceConflict:
            null

    inputs:
        requiredInputs:
            - SKINFOLD_TRICEPS
            - SKINFOLD_SUPRAILIAC
            - SKINFOLD_ABDOMEN

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

A ficha permite ao motor interpretar a variante da seguinte forma:

```text
sexo:

    MALE
        → compatível

    FEMALE
        → incompatível

idade:

    população de desenvolvimento = 18–30

    média específica = NOT_DOCUMENTED

    AGE não é variável matemática

atleta:

    NOT_DOCUMENTED

trainingLevel:

    NOT_DOCUMENTED

modalidade:

    NOT_DOCUMENTED

inputs:

    3 skinfolds

output:

    BODY_DENSITY
```

A lógica deve preservar:

```text
NOT_DOCUMENTED

    ≠

INELIGIBLE
```

A existência de validação cruzada posterior não deve ser interpretada pelo motor como se ela alterasse os coeficientes da variante.

A validação cruzada de 2015 deve ser tratada como evidência externa sobre o desempenho da G-M3 em outra amostra. O resultado daquele estudo foi desfavorável à validade cruzada da variante naquela população específica.

A faixa etária de 18–30 anos também não deve ser convertida automaticamente em `explicitAgeRestriction`.

---

## 19. Referência

```text
Reference

    citation:
        "Guedes, D. P.; Guedes, J. E. (1991).
         Proposição de equações para predição da quantidade de gordura corporal
         em adultos jovens.
         Semina: Ciências Biológicas e da Saúde, 12(2), 61–70."

    doi:
        "10.5433/1679-0367.1991v12n2p61"

    url:
        "https://doi.org/10.5433/1679-0367.1991v12n2p61"
```

### Fontes bibliográficas adicionais

```text
Both, D. R.; Matheus, S. C.; Behenck, M. S. (2015).
Validação de equações antropométricas específicas e generalizadas para estimativa
do percentual de gordura corporal em estudantes de Educação Física do sexo masculino.
Revista Brasileira de Educação Física e Esporte, 29(1).

DOI:
10.1590/1807-55092015000100013

URL:
https://www.scielo.br/j/rbefe/a/ZNXBmLtPMNWnt9nwSwv4qZH/
```

A publicação de Guedes & Guedes de 1991 registra o desenvolvimento das equações para adultos jovens, a utilização de pesagem hidrostática e a aplicação posterior em uma amostra diferente de 41 sujeitos. O estudo de 2015 constitui evidência de validação cruzada independente e apresentou resultados desfavoráveis para as equações específicas de Guedes & Guedes, incluindo a G-M3.