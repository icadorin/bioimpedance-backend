# EquationVariantScientificProfile — G-M6

## 1. Identification

```text
identity:

    variantId:
        "G-M6"

    familyId:
        "guedes"

    displayName:
        "Guedes 6 Dobras — Masculino"

    aliasNames:
        [
            "GM6",
            "Guedes 6-Site — Male"
        ]
```

A variante `G-M6` corresponde à sexta equação específica masculina da série de equações de Guedes & Guedes para adultos jovens.

### Reference principal

```text
Guedes, D. P.; Guedes, J. E. R. P. (1991).
Proposição de equações para predição da quantidade de gordura corporal em adultos jovens.
Semina, 12(2), 61–70.
PMID: 1845307
```

A série foi derivada a partir de medidas de dobras cutâneas e densidade corporal determinada por método hidrostático.

---

# 2. Mathematical Definition

A definição matemática é:

```text
D =
    1.21546
    - 0.08119 × log10(Σ6)
```

Onde:

```text
D
    = BODY_DENSITY

Σ6
    = soma das seis dobras cutâneas
```

As seis dobras utilizadas são:

```text
- abdômen
- tríceps
- supra-ilíaca
- axilar média
- subescapular
- coxa
```

### Forma computacional

```text
sum6 =
    SKINFOLD_ABDOMEN
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_AXILLARY_MID
    + SKINFOLD_SUBSCAPULAR
    + SKINFOLD_THIGH

bodyDensity =
    1.21546
    - 0.08119 × log10(sum6)
```

### Output

```text
outputType = BODY_DENSITY
```

A conversão da densidade corporal para percentual de gordura permanece uma etapa posterior e separada.

---

# 3. Applicability

## 3.1 Sexo

```text
sex:

    supportedSexes:
        - MALE
```

A variante pertence ao conjunto de equações específicas masculinas de Guedes & Guedes.

---

## 3.2 Idade

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

A publicação de Guedes & Guedes informa uma amostra composta por 206 adultos jovens, sendo 110 homens e 96 mulheres, com idade entre 18 e 30 anos.

A idade não participa matematicamente da equação G-M6.

`developmentSampleMeanAge` permanece `null` porque não foi confirmada na fonte primária utilizada para esta ficha.

A faixa 18–30 anos descreve a população de desenvolvimento e não é transformada automaticamente em uma restrição científica explícita.

---

# 4. Population Applicability

```text
population:

    originalPopulation:

        description:
            "Adultos jovens do sexo masculino"

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
            110

        bodyCharacteristicsNotes:
            null

        sampleCharacteristics:
            "Universitários da Universidade Federal de Santa Maria."

        source:
            Guedes & Guedes (1991)

    validationPopulations:
        []
```

A fonte primária informa 110 homens e 96 mulheres, com idades de 18 a 30 anos, e descreve a utilização da pesagem hidrostática para determinação da densidade corporal. O conjunto de medidas incluiu oito pontos de dobra cutânea: bíceps, tríceps, subescapular, axilar média, supra-ilíaca, abdômen, coxa e panturrilha.

---

# 5. Athlete Applicability

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

A amostra foi descrita como universitários, mas isso não é suficiente para classificar cientificamente os participantes como atletas ou não atletas segundo o modelo operacional da plataforma.

---

# 6. Training Level Applicability

```text
trainingLevel:

    supportedLevels:
        []

    notes:
        "Não documentado segundo a escala SEDENTARY/RECREATIONAL/TRAINED/COMPETITIVE/ELITE."
```

---

# 7. Modality Applicability

```text
modality:

    supportedModalities:
        []

    notes:
        "Não documentado."
```

---

# 8. Body Characteristics

```text
bodyCharacteristics:

    rules:
        []
```

A ficha não cria regras artificiais a partir da distribuição observada na amostra.

---

# 9. Validation Evidence

## 9.1 Development Evidence

```text
development:

    studyReference:

        citation:
            "Guedes, D. P.; Guedes, J. E. R. P. (1991).
             Proposição de equações para predição da quantidade de gordura
             corporal em adultos jovens. Semina, 12(2), 61–70."

        doi:
            null

    population:
        "Adultos jovens; n = 206 no estudo, sendo 110 homens e 96 mulheres; 18–30 anos"

    criterionMethod:
        "Densidade corporal determinada pelo método hidrostático"

    year:
        1991
```

A publicação informa que as equações foram derivadas a partir da relação entre espessuras de dobras cutâneas e densidade corporal obtida pelo método hidrostático.

---

# 10. Cross-validation

```text
crossValidationStudies:

    - studyReference:

        citation:
            "Guedes, D. P.; Guedes, J. E. R. P. (1991).
             Proposição de equações para predição da quantidade de gordura
             corporal em adultos jovens. Semina, 12(2), 61–70."

        doi:
            null

      population:

        description:
            "Amostra independente de sujeitos com idade e características físicas semelhantes"

        country:
            "Brazil"

        region:
            "Santa Maria, Rio Grande do Sul"

        sexCoverage:
            []

        ageCoverage:
            min: 18
            max: 30

        sampleSize:
            41

        bodyCharacteristicsNotes:
            null

        sampleCharacteristics:
            "Amostra independente usada para cross-validation das equações."

        source:
            Guedes & Guedes (1991)

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
          "A fonte primária disponível não permite atribuir de forma inequívoca
           o subconjunto masculino da amostra de 41 participantes nem métricas
           específicas à G-M6."
```

A publicação informa que as equações foram cross-validadas em uma amostra diferente de 41 sujeitos com idade e características físicas semelhantes. Como a composição por sexo e as métricas específicas da G-M6 não foram confirmadas na fonte primária disponível, esses campos permanecem não documentados.

---

# 11. Validation Studies

```text
validationStudies:
    []
```

A validação disponível na publicação original é representada em `crossValidationStudies`.

---

# 12. External Validation

```text
externalValidationStudies:
    []
```

Nenhum estudo de validação externa foi incorporado nesta ficha.

---

# 13. Measurement Requirements

```text
inputs:

    requiredInputs:

        - SKINFOLD_ABDOMEN
        - SKINFOLD_TRICEPS
        - SKINFOLD_SUPRAILIAC
        - SKINFOLD_AXILLARY_MID
        - SKINFOLD_SUBSCAPULAR
        - SKINFOLD_THIGH

    optionalInputs:
        []
```

`AGE` não é input matemático da G-M6.

`SEX` não é input matemático; participa da aplicabilidade da variante.

A soma das seis dobras é calculada antes da aplicação do logaritmo decimal.

---

# 14. Scientific Restrictions

```text
restrictions:
    []
```

A ficha não adiciona restrições explícitas não documentadas.

A faixa de 18–30 anos é preservada como descrição da população de desenvolvimento.

---

# 15. Source Conflict

```text
evidence:

    sourceConflict:
        null
```

Não foi identificado conflito material quanto à forma matemática da G-M6 entre as fontes consultadas.

---

# 16. Lifecycle

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

A variante possui definição matemática, população de desenvolvimento, sexo aplicável, inputs necessários e referência científica suficiente para permanecer `ACTIVE` dentro das regras atuais da biblioteca.

---

# 17. Ficha consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:
            "G-M6"

        familyId:
            "guedes"

        displayName:
            "Guedes 6 Dobras — Masculino"

        aliasNames:
            [
                "GM6",
                "Guedes 6-Site — Male"
            ]

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
                []

            explicitAgeRestriction:
                null

        population:

            originalPopulation:
                description: "Adultos jovens do sexo masculino"
                country: "Brazil"
                region: "Santa Maria, Rio Grande do Sul"
                sexCoverage:
                    - MALE
                ageCoverage:
                    min: 18
                    max: 30
                sampleSize: 110
                bodyCharacteristicsNotes: null
                sampleCharacteristics:
                    "Universitários da Universidade Federal de Santa Maria."
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
                "Guedes & Guedes (1991)"

            population:
                "Adultos jovens; estudo com 110 homens e 96 mulheres; 18–30 anos"

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
                "Amostra independente de 41 sujeitos com idade e características físicas semelhantes"

              criterionMethod:
                "Densidade corporal determinada pelo método hidrostático"

              metrics:
                correlation: null
                standardError: null
                meanDifference: null
                rmse: null
                otherMetrics: null

              limitations:
                "Composição por sexo e métricas específicas da G-M6 não confirmadas."

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

# 18. Observações para o SuggestionEngine

```text
sexo:
    MALE → compatível
    FEMALE → incompatível

idade:
    população de desenvolvimento = 18–30
    idade não entra matematicamente na equação

atleta:
    NOT_DOCUMENTED

trainingLevel:
    NOT_DOCUMENTED

modality:
    NOT_DOCUMENTED

inputs:
    6 skinfolds

output:
    BODY_DENSITY
```

O motor deve distinguir:

```text
faixa observada na população
```

de:

```text
restrição explícita de uso
```

No estado atual da evidência, nenhuma restrição explícita adicional foi cadastrada.

---

# 19. Reference

```text
Reference

    citation:
        "Guedes, D. P.; Guedes, J. E. R. P. (1991).
         Proposição de equações para predição da quantidade de gordura corporal
         em adultos jovens. Semina, 12(2), 61–70."

    doi:
        null

    url:
        "https://pubmed.ncbi.nlm.nih.gov/1845307/"
```

A tabela que reproduz a série das equações registra a G-M6 como `1.21546 - 0.08119 × log10(Σ6)`, com `r = 0.889` e `EPE = 0.0056 g/ml` na amostra de desenvolvimento.
