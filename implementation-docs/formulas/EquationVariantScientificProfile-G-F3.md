# EquationVariantScientificProfile — G-F3

## 1. Identification

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

### Reference principal

```text
Guedes, D. P.; Guedes, J. E. R. (1991).
Proposição de equações para predição da quantidade de gordura corporal em adultos jovens.
Semina, 12(2), 61–70.
PMID: 1845307
```

A publicação original deriva equações de regressão para estimar densidade corporal a partir de espessuras de dobras cutâneas em adultos jovens brasileiros.

---

## 2. Mathematical Definition

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

Para a variante feminina:

```text
Σ3 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_THIGH
```

### Forma computacional

```text
sum3 =
    SKINFOLD_SUBSCAPULAR
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_THIGH

bodyDensity =
    1.16650
    - 0.07063 × log10(sum3)
```

### Output

```text
outputType = BODY_DENSITY
```

A variante não utiliza idade, peso ou estatura como variáveis matemáticas.

---

## 3. Applicability

### 3.1 Sexo

```text
sex:

    supportedSexes:
        - FEMALE
```

A equação é específica para a população feminina do estudo original.

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

A fonte primária disponível no PubMed descreve a amostra como adultos jovens de **18 a 30 anos**. O resumo não informa, de forma separada, a média de idade específica das 96 mulheres.

Uma divergência aparece em fontes secundárias que reproduzem o protocolo como 17–27 anos ou o denominam “Guedes 1985/1994”. Essa divergência de apresentação não foi suficiente, nesta ficha, para declarar `SOURCE_CONFLICT` da equação, porque a fonte bibliográfica primária indexada informa 18–30 anos e a definição matemática permanece a mesma.

---

## 4. Population Applicability

```text
population:

    originalPopulation:

        description:
            "Mulheres adultas jovens"

        country:
            "Brazil"

        region:
            "Rio Grande do Sul"

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
            "Parte feminina de uma amostra total de 206 adultos jovens; o
             estudo foi realizado no contexto de uma população universitária
             brasileira e utilizou densidade corporal determinada pelo método
             hidrostático."

        source:
            Guedes & Guedes (1991)

    validationPopulations:
        []
```

A publicação original informa uma amostra total de **206 participantes**, composta por **110 homens e 96 mulheres**, com idade entre **18 e 30 anos**. O resumo identifica o estudo como realizado no Brasil e descreve as equações como válidas para aplicação à população brasileira. A associação da amostra a Rio Grande do Sul é preservada como característica documental do contexto da pesquisa, sem transformar região geográfica em regra de elegibilidade universal.

---

## 5. Athlete Applicability

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

A fonte informa características físicas semelhantes entre os participantes, mas não fornece classificação operacional compatível com `athlete = true/false` para toda a amostra.

---

## 6. Training Level Applicability

```text
trainingLevel:

    supportedLevels:
        []

    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

Não deve ser inferido mapeamento para:

```text
SEDENTARY
RECREATIONAL
TRAINED
COMPETITIVE
ELITE
```

---

## 7. Modality Applicability

```text
modality:

    supportedModalities:
        []

    notes:
        "Não documentado em termos de modalidades esportivas específicas."
```

---

## 8. Body Characteristics

```text
bodyCharacteristics:

    rules:
        []
```

Não foi cadastrada uma regra explícita para baixo percentual de gordura, obesidade, elevada massa muscular ou valores extremos de dobras. A amostra observada não deve ser transformada automaticamente em restrição científica.

---

## 9. Validation Evidence

### 9.1 Development Evidence

```text
development:

    studyReference:

        citation:
            "Guedes, D. P.; Guedes, J. E. R. (1991).
             Proposição de equações para predição da quantidade de gordura
             corporal em adultos jovens. Semina, 12(2), 61–70."

        doi:
            null

        url:
            "https://pubmed.ncbi.nlm.nih.gov/1845307/"

    population:
        "206 adultos jovens; 110 homens e 96 mulheres; 18–30 anos"

    criterionMethod:
        "Densidade corporal determinada pelo método hidrostático"

    year:
        1991
```

O resumo do estudo informa que o método hidrostático foi utilizado para determinar densidade corporal e percentual de gordura, e que as equações foram derivadas a partir dos valores de espessura das dobras cutâneas.

---

## 10. Validation

```text
validationStudies:
    []
```

A validação posterior não será classificada como `validationStudies` aqui sem identificação inequívoca da população e da natureza da validação para esta variante específica.

---

## 11. Cross-validation

```text
crossValidationStudies:

    - studyReference:

        citation:
            "Guedes, D. P.; Guedes, J. E. R. (1991).
             Proposição de equações para predição da quantidade de gordura
             corporal em adultos jovens. Semina, 12(2), 61–70."

        doi:
            null

        url:
            "https://pubmed.ncbi.nlm.nih.gov/1845307/"

      population:
          "Amostra independente de 41 participantes com idade e características
           físicas semelhantes às da amostra de desenvolvimento."

      criterionMethod:
          "Densidade corporal determinada pelo método hidrostático"

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
          "O resumo da publicação confirma a cross-validation em 41 participantes,
           mas não fornece a composição por sexo nem métricas específicas da G-F3."
```

O estudo original informa que as equações foram **cross-validated em uma amostra diferente de 41 participantes**, com idade e características físicas semelhantes. O resumo disponível não permite atribuir com segurança métricas específicas à G-F3 feminina; portanto, os campos estruturados permanecem `null`.

---

## 12. External Validation

```text
externalValidationStudies:
    []
```

Nenhum estudo de validação externa independente foi incorporado nesta ficha nesta etapa.

---

## 13. Measurement Requirements

```text
inputs:

    requiredInputs:

        - SKINFOLD_SUBSCAPULAR
        - SKINFOLD_SUPRAILIAC
        - SKINFOLD_THIGH

    optionalInputs:
        []
```

`SEX` não é input matemático da equação. Ele participa da aplicabilidade da variante:

```text
SEX
    → Applicability

3 skinfolds
    → Formula inputs
```

`AGE` também não é necessário matematicamente, apesar de existir uma faixa etária documentada para a população de desenvolvimento.

---

## 14. Scientific Restrictions

```text
restrictions:
    []
```

A faixa de 18–30 anos é registrada como característica da população de desenvolvimento e não como uma `ScientificRestriction`, pois a fonte primária consultada não formula uma proibição explícita de uso fora dessa faixa.

---

## 15. Source Conflict

```text
evidence:

    sourceConflict:
        null
```

Há divergências de apresentação em fontes secundárias quanto ao ano/denominação do protocolo e à faixa etária reproduzida em alguns materiais, mas não foi identificado conflito material suficiente para declarar conflito científico da definição matemática da G-F3.

---

## 16. Lifecycle

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

A variante possui os elementos essenciais:

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

As informações não documentadas sobre atleta, nível de treinamento, modalidade e validações específicas não impedem `ACTIVE`.

---

## 17. Ficha consolidada

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
                    "Mulheres adultas jovens"

                country:
                    "Brazil"

                region:
                    "Rio Grande do Sul"

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
                    "Parte feminina de uma amostra total de 206 adultos jovens;
                     contexto universitário brasileiro."

                source:
                    Guedes & Guedes (1991)

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
                "Guedes & Guedes (1991)"

            population:
                "206 adultos jovens; 110 homens e 96 mulheres; 18–30 anos"

            criterionMethod:
                "Densidade corporal determinada pelo método hidrostático"

            year:
                1991

        validationStudies:
            []

        crossValidationStudies:

            - studyReference:
                "Guedes & Guedes (1991)"

              population:
                "Amostra independente de 41 participantes"

              criterionMethod:
                "Densidade corporal determinada pelo método hidrostático"

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
                  "O resumo não permite atribuir métricas específicas à G-F3."

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
    FEMALE → compatível
    MALE   → incompatível

idade:
    população de desenvolvimento = 18–30
    média não documentada
    não existe explicitAgeRestriction

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

O motor não deve transformar a ausência de documentação em inelegibilidade.

---

## 19. Reference

```text
Reference

    citation:
        "Guedes, D. P.; Guedes, J. E. R. (1991).
         Proposição de equações para predição da quantidade de gordura corporal
         em adultos jovens. Semina, 12(2), 61–70."

    doi:
        null

    url:
        "https://pubmed.ncbi.nlm.nih.gov/1845307/"
```
