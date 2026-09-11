# EquationVariantScientificProfile — G-F3

## 1. Identificação

```text
identity:

    variantId:
        "G-F3"

    familyId:
        "guedes"

    displayName:
        "Guedes 3 Dobras — Feminino"

    aliasNames:
        [
            "Guedes 3-Site — Female",
            "Guedes 3 Dobras Feminino"
        ]
```

A variante G-F3 pertence ao conjunto de equações específicas de Guedes & Guedes para estimativa da densidade corporal em mulheres adultas jovens.

Referência principal:

```text
Guedes, D. P.; Guedes, J. E. R. P. (1991).

Proposição de equações para predição de quantidade de gordura corporal em adultos jovens.

Semina: Ciências Biológicas e da Saúde, 12(2), 61–70.

DOI: 10.5433/1679-0367.1991v12n2p61
```

A publicação original teve como objetivo estabelecer equações de regressão para estimar a densidade corporal com base em espessuras de dobras cutâneas em adultos jovens. O estudo informa 110 homens e 96 mulheres, com idade entre 18 e 30 anos, e utilizou pesagem hidrostática para determinar a densidade corporal. A publicação também informa que as equações foram testadas em outra amostra de 41 sujeitos com idade e características físicas semelhantes.

---

## 2. Definição Matemática

A definição matemática da variante é:

```text
D =

    1.16650

    - 0.07063 × log10(Σ3)
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
- subescapular
- supra-ilíaca
- coxa
```

A forma computacional é:

```text
sum3 =

    SKINFOLD_SUBSCAPULAR

    + SKINFOLD_SUPRAILIAC

    + SKINFOLD_THIGH

bodyDensity =

    1.16650

    - 0.07063 × log10(sum3)
```

```text
outputType = BODY_DENSITY
```

A idade, a massa corporal e a estatura não participam matematicamente da G-F3.

A combinação das três dobras e os coeficientes da equação também é reproduzida na literatura secundária como `1.16650 - 0.07063 × log10(SB + SI + TG)`.

---

## 3. Aplicabilidade

### 3.1 Sexo

```text
sex:

    supportedSexes:
        - FEMALE
```

A G-F3 é a variante feminina correspondente à equação de três dobras do conjunto de Guedes & Guedes.

### 3.2 Idade

```text
age:

    originalDevelopmentAgeRange:
        min: 18
        max: 30

    developmentSampleMeanAge:
        null

    validatedAgeRanges:
        []

    explicitAgeRestriction:
        null
```

A publicação primária informa 18 a 30 anos para a amostra de desenvolvimento, composta por 110 homens e 96 mulheres. Ela também informa uma amostra diferente de 41 sujeitos com idade e características físicas semelhantes.

A média de idade específica das mulheres não é fornecida no resumo da publicação e permanece `null`.

A idade não participa matematicamente da G-F3. A faixa de 18–30 anos descreve a população de desenvolvimento e não é convertida automaticamente em `explicitAgeRestriction`.

---

## 4. Aplicabilidade Populacional

```text
population:

    originalPopulation:

        description:
            "Mulheres adultas jovens brasileiras"

        country:
            "Brazil"

        region:
            null

        sexCoverage:
            - FEMALE

        ageCoverage:
            min: 18
            max: 30

        sampleSize:
            96

        bodyCharacteristicsNotes:
            null

        sampleCharacteristics:
            "Subamostra feminina do estudo de Guedes & Guedes; a publicação
             informa 110 homens e 96 mulheres na amostra de desenvolvimento."

        source:
            "Guedes & Guedes (1991)"

    validationPopulations:
        - description:
            "Amostra independente utilizada para testar as equações"

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

A publicação da Universidade Estadual de Londrina registra 205 sujeitos no resumo em português, enquanto o registro PubMed informa 206. O resumo da UEL também apresenta 110 homens e 96 mulheres, com 18–30 anos, e confirma a amostra diferente de 41 sujeitos.

A inconsistência aritmética entre o total informado e a soma dos sexos é preservada como característica do registro bibliográfico e não é usada para alterar o tamanho da subamostra feminina de 96 participantes.

A composição sexual dos 41 participantes não é informada no material primário disponível; portanto, não se deve inferir que sejam 41 mulheres.

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

A fonte consultada não fornece classificação suficiente para enquadrar formalmente a população segundo o modelo operacional de atletas da plataforma.

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

Não foi identificada modalidade esportiva específica de desenvolvimento da G-F3 na fonte primária consultada.

---

## 8. Características Corporais

```text
bodyCharacteristics:

    rules:
        []
```

Não foi identificada regra científica explícita que justifique cadastrar condição específica relacionada a baixo percentual de gordura, obesidade, alta massa muscular ou valores extremos de dobras.

A descrição da amostra não deve ser convertida automaticamente em `BodyCharacteristicRule`.

---

## 9. Evidências de Validação

### 9.1 Evidências de Desenvolvimento

```text
development:

    studyReference:

        citation:
            "Guedes, D. P.; Guedes, J. E. R. P. (1991).
             Proposição de equações para predição de quantidade de gordura
             corporal em adultos jovens.
             Semina: Ciências Biológicas e da Saúde, 12(2), 61–70."

        doi:
            "10.5433/1679-0367.1991v12n2p61"

        url:
            "https://doi.org/10.5433/1679-0367.1991v12n2p61"

    population:
        "Mulheres adultas jovens, n = 96, 18–30 anos"

    criterionMethod:
        "Pesagem hidrostática"

    year:
        1991

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
```

A publicação primária informa que a densidade corporal foi determinada por pesagem hidrostática e que as espessuras das dobras foram medidas em oito regiões, incluindo subescapular, supra-ilíaca e coxa.

A equação feminina de três dobras é reproduzida na literatura secundária como `1.16650 - 0.07063 × log10(CX + SI + SB)`.

Não foram localizadas, na fonte primária disponível, métricas de desenvolvimento específicas da G-F3 que permitam preencher com segurança `correlation`, `standardError`, `meanDifference` ou `rmse`. Por isso, esses campos permanecem `null`.

---

## 10. Validação

```text
validationStudies:

    - studyReference:

        citation:
            "Guedes, D. P.; Guedes, J. E. R. P. (1991).
             Proposição de equações para predição de quantidade de gordura
             corporal em adultos jovens.
             Semina: Ciências Biológicas e da Saúde, 12(2), 61–70."

        doi:
            "10.5433/1679-0367.1991v12n2p61"

        url:
            "https://doi.org/10.5433/1679-0367.1991v12n2p61"

      population:

        description:
            "Amostra independente utilizada no próprio estudo para testar as equações"

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
            "Amostra diferente da amostra de desenvolvimento, descrita como tendo
             idade e características físicas semelhantes. A composição por sexo
             não foi especificada no resumo disponível."

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
          "O resumo confirma a utilização de uma amostra diferente de 41 sujeitos,
           mas não fornece métricas específicas atribuíveis individualmente à G-F3
           nem a composição sexual desse grupo."
```

A fonte primária diz explicitamente que as equações foram validadas em uma amostra diferente formada por 41 sujeitos com idade e características físicas similares.

Conforme a regra operacional desta biblioteca, uma amostra independente pertencente ao mesmo estudo de desenvolvimento é registrada em `validationStudies`, mesmo quando a publicação usa a expressão “cross validated”.

---

## 11. Validação Cruzada

```text
crossValidationStudies:
    []
```

Não foi identificado, nas fontes consultadas, estudo posterior de outro pesquisador com amostra feminina suficientemente documentada que possa ser atribuído especificamente à G-F3 como validação cruzada.

A validação publicada em 2015 para as equações específicas de Guedes & Guedes foi realizada em 104 estudantes de Educação Física do sexo masculino e, portanto, não constitui validação cruzada da G-F3 feminina.

---

## 12. Validação Externa

```text
externalValidationStudies:
    []
```

Nenhum estudo que atenda ao conceito operacional de validação externa totalmente independente foi incorporado nesta ficha.

---

## 13. Requisitos de Medição

```text
inputs:

    requiredInputs:
        - SKINFOLD_SUBSCAPULAR
        - SKINFOLD_SUPRAILIAC
        - SKINFOLD_THIGH

    optionalInputs:
        []
```

As três variáveis matemáticas da G-F3 são:

```text
SKINFOLD_SUBSCAPULAR
SKINFOLD_SUPRAILIAC
SKINFOLD_THIGH
```

`SEX` não é variável matemática e permanece exclusivamente em `applicability.sex`.

`AGE` também não é variável matemática da G-F3.

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

A equação não utiliza massa corporal nem estatura.

---

## 14. Restrições Científicas

```text
restrictions:
    []
```

A faixa de 18–30 anos é uma característica da população de desenvolvimento e não foi transformada automaticamente em `ScientificRestriction`.

Também não foi identificada restrição explícita referente a atletas, nível de treinamento, modalidade ou características corporais.

---

## 15. Conflito de Fonte

```text
evidence:

    sourceConflict:
        null
```

Não foi identificado conflito material entre fontes quanto aos coeficientes e às três variáveis matemáticas da G-F3.

Existe, porém, uma divergência histórica na forma como a série é citada em fontes secundárias: alguns materiais associam o protocolo a “Guedes (1985)” ou “Guedes (1989)”, enquanto a publicação primária encontrada para o conjunto de equações de adultos jovens é o artigo de Guedes & Guedes de 1991.

Essa divergência de datação bibliográfica não é tratada como `sourceConflict` da equação porque não foram encontrados dois conjuntos primários conflitantes de coeficientes para a mesma G-F3.

A referência de definição e desenvolvimento desta ficha permanece a publicação de 1991.

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

Os campos essenciais estão documentados:

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

As informações não documentadas permanecem explicitamente como `null` ou listas vazias.

---

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:
            "G-F3"

        familyId:
            "guedes"

        displayName:
            "Guedes 3 Dobras — Feminino"

        aliasNames:
            [
                "Guedes 3-Site — Female",
                "Guedes 3 Dobras Feminino"
            ]

    applicability:

        sex:

            supportedSexes:
                - FEMALE

        age:

            originalDevelopmentAgeRange:
                min: 18
                max: 30

            developmentSampleMeanAge:
                null

            validatedAgeRanges:
                []

            explicitAgeRestriction:
                null

        population:

            originalPopulation:

                description:
                    "Mulheres adultas jovens brasileiras"

                country:
                    "Brazil"

                region:
                    null

                sexCoverage:
                    - FEMALE

                ageCoverage:
                    min: 18
                    max: 30

                sampleSize:
                    96

                bodyCharacteristicsNotes:
                    null

                sampleCharacteristics:
                    "Subamostra feminina do estudo de Guedes & Guedes."

                source:
                    "Guedes & Guedes (1991)"

            validationPopulations:

                - description:
                    "Amostra independente utilizada para testar as equações"

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
                    "Amostra independente do mesmo estudo; composição por sexo não especificada."

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
                    "Guedes, D. P.; Guedes, J. E. R. P. (1991).
                     Proposição de equações para predição de quantidade de gordura
                     corporal em adultos jovens.
                     Semina: Ciências Biológicas e da Saúde, 12(2), 61–70."

                doi:
                    "10.5433/1679-0367.1991v12n2p61"

                url:
                    "https://doi.org/10.5433/1679-0367.1991v12n2p61"

            population:
                "Mulheres adultas jovens, n = 96, 18–30 anos"

            criterionMethod:
                "Pesagem hidrostática"

            year:
                1991

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

        validationStudies:

            - studyReference:
                "Guedes & Guedes (1991)"

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
                  "Métricas individuais da G-F3 não identificadas no material primário disponível."

        crossValidationStudies:
            []

        externalValidationStudies:
            []

        sourceConflict:
            null

    inputs:

        requiredInputs:
            - SKINFOLD_SUBSCAPULAR
            - SKINFOLD_SUPRAILIAC
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

```text
sexo:

    FEMALE
        → compatível

    MALE
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

O motor não deve transformar ausência de documentação em `INELIGIBLE`.

A amostra de 41 sujeitos também não deve ser interpretada como uma amostra de 41 mulheres, porque a composição sexual não está documentada no resumo disponível.

A publicação posterior de 2015 com 104 homens não deve ser usada como validação cruzada da G-F3 feminina.

---

## 19. Referência

```text
Reference

    citation:
        "Guedes, D. P.; Guedes, J. E. R. P. (1991).
         Proposição de equações para predição de quantidade de gordura corporal
         em adultos jovens.
         Semina: Ciências Biológicas e da Saúde, 12(2), 61–70."

    doi:
        "10.5433/1679-0367.1991v12n2p61"

    url:
        "https://doi.org/10.5433/1679-0367.1991v12n2p61"
```

### Fontes bibliográficas adicionais

```text
Guedes, D. P.; Guedes, J. E. R. P. (1991).
Proposição de equações para predição de quantidade de gordura corporal em adultos jovens.
Semina: Ciências Biológicas e da Saúde, 12(2), 61–70.

DOI:
10.5433/1679-0367.1991v12n2p61

URL:
https://doi.org/10.5433/1679-0367.1991v12n2p61
```