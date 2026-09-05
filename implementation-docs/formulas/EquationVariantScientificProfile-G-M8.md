# EquationVariantScientificProfile — G-M8

## 1. Identification

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

A variante G-M8 pertence ao conjunto de equações específicas de Guedes & Guedes para estimativa da densidade corporal em homens jovens.

Referência bibliográfica da família:

```text
Guedes, D. P.; Guedes, J. E. R. P.
Equações específicas para estimativa da densidade corporal a partir de
espessuras de dobras cutâneas em adultos jovens.
```

Na literatura brasileira, o conjunto é frequentemente citado como Guedes (1985), embora também existam fontes posteriores que utilizam 1991/1994 para a mesma família de equações. Essa diferença bibliográfica não altera, nesta ficha, a definição matemática da G-M8.

---

# 2. Mathematical Definition

A definição matemática é:

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
    = soma das oito dobras cutâneas utilizadas pela G-M8
```

A soma é composta por:

```text
Σ8 =
    SKINFOLD_ABDOMEN
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_AXILLARY_MID
    + SKINFOLD_SUBSCAPULAR
    + SKINFOLD_THIGH
    + SKINFOLD_MEDIAL_CALF
    + SKINFOLD_BICEPS
```

### Output

```text
outputType = BODY_DENSITY
```

### Forma computacional

```text
sum8 =
    SKINFOLD_ABDOMEN
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_AXILLARY_MID
    + SKINFOLD_SUBSCAPULAR
    + SKINFOLD_THIGH
    + SKINFOLD_MEDIAL_CALF
    + SKINFOLD_BICEPS

bodyDensity =
    1.22627
    - 0.08384 × log10(sum8)
```

A conversão posterior de densidade corporal para percentual de gordura não faz parte da definição matemática desta variante.

---

# 3. Applicability

## 3.1 Sexo

```text
sex:

    supportedSexes:
        - MALE
```

A G-M8 pertence ao conjunto masculino das equações específicas de Guedes.

---

## 3.2 Idade

As fontes secundárias que reproduzem o conjunto GM1–GM8 atribuem às equações masculinas uma população de jovens universitários com faixa etária de 17–27 anos.

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

A faixa de desenvolvimento é uma característica da população estudada e não deve ser convertida automaticamente em uma restrição formal de inelegibilidade.

---

# 4. Population Applicability

```text
population:

    originalPopulation:

        description:
            "Homens jovens / adultos jovens"

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
            "Amostra de jovens universitários submetidos à avaliação de
             composição corporal por método de referência hidrostático."

        sampleCharacteristics:
            "Estudantes da Universidade Federal de Santa Maria (RS),
             conforme fontes que reproduzem a descrição da amostra original."

        source:
            Guedes & Guedes

    validationPopulations:
        []
```

A amostra masculina é reportada como constituída por 110 participantes. A família de equações foi derivada em adultos jovens universitários e utiliza densidade corporal como variável dependente.

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

A descrição de estudantes universitários não é suficiente para classificá-los, segundo a taxonomia da plataforma, como atletas ou não atletas.

---

# 6. Training Level Applicability

```text
trainingLevel:

    supportedLevels:
        []

    notes:
        "Não documentado segundo a escala operacional da plataforma."
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

Não foi identificada uma característica corporal explicitamente documentada que deva ser transformada em regra `SUPPORTED`, `WARNING` ou `INELIGIBLE` para a G-M8.

---

# 9. Validation Evidence

## 9.1 Development Evidence

```text
development:

    studyReference:

        citation:
            "Guedes & Guedes — equações específicas de densidade corporal
             para adultos jovens."

        doi:
            null

    population:
        "Homens jovens, n = 110, 17–27 anos"

    criterionMethod:
        "Pesagem hidrostática"

    year:
        1985
```

A literatura brasileira reproduz a G-M8 dentro da série de oito equações masculinas específicas de Guedes.

---

# 10. Validation

As fontes disponíveis indicam validação das equações específicas de Guedes em amostra independente, porém os dados recuperados não permitem atribuir com segurança métricas exclusivas de validação à G-M8.

Portanto:

```text
validationStudies:
    []
```

Não devem ser distribuídas entre as oito variantes métricas que foram apresentadas apenas de forma agregada para o conjunto.

---

# 11. Cross-validation

```text
crossValidationStudies:
    []
```

A existência de uma amostra independente para validação cruzada do conjunto de equações não é suficiente, no material recuperado, para atribuir uma população e métricas específicas exclusivamente à G-M8.

---

# 12. External Validation

```text
externalValidationStudies:
    []
```

Validações posteriores das equações de Guedes devem ser incorporadas aqui somente quando o resultado da G-M8 puder ser identificado de forma inequívoca.

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
        - SKINFOLD_MEDIAL_CALF
        - SKINFOLD_BICEPS

    optionalInputs:
        []
```

A equação não contém idade como variável matemática.

Portanto:

```text
SEX
    → Applicability

AGE
    → Applicability / population comparison

8 skinfolds
    → Formula inputs
```

Não incluir `AGE` em `requiredInputs` apenas porque a população original possui uma faixa etária documentada.

---

# 14. Scientific Restrictions

```text
restrictions:
    []
```

Não foi identificada uma restrição científica explícita adicional que justifique `INELIGIBLE`.

A faixa etária da população de desenvolvimento representa evidência de população, não uma proibição de uso formal sem fonte que a estabeleça como tal.

---

# 15. Source Conflict

```text
evidence:

    sourceConflict:
        null
```

Existe divergência em fontes secundárias quanto ao ano bibliográfico e à forma de apresentação da população da família Guedes. Essa divergência documental não é suficiente, nesta etapa, para caracterizar um conflito científico material sobre a definição da G-M8.

A questão deverá ser reaberta caso a fonte primária recuperada apresente valores incompatíveis para a mesma informação.

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

A variante possui definição matemática, sexo, população, inputs e referência suficientes para permanecer cadastrada na biblioteca. Informações não documentadas permanecem explicitamente como não documentadas.

---

# 17. Ficha consolidada

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
                    "Homens jovens / adultos jovens"

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
                    "Amostra de jovens universitários."

                sampleCharacteristics:
                    "Estudantes da Universidade Federal de Santa Maria (RS)."

                source:
                    Guedes & Guedes

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
                Guedes & Guedes

            population:
                "Homens jovens, n = 110, 17–27 anos"

            criterionMethod:
                "Pesagem hidrostática"

            year:
                1985

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

# 18. Interpretação para o SuggestionEngine

A ficha permite que o motor derive:

```text
sexo:
    MALE → compatível
    FEMALE → incompatível

idade:
    população documentada = 17–27 anos
    sem usar a faixa como restrição explícita

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

O motor não deve transformar ausência de documentação em inelegibilidade.

---

# 19. Reference

```text
Reference

    citation:
        "Guedes & Guedes — equações específicas para estimativa da
         densidade corporal em adultos jovens."

    doi:
        null

    url:
        null
```

Fontes consultadas para esta ficha indicam a G-M8 como:

```text
D = 1.22627 - 0.08384 × log10(Σ8)

r = 0.901
EPE = 0.0055 g/ml
```

Os valores de `r` e `EPE` são apresentados para a G-M8 em tabela secundária que reproduz as oito equações masculinas de Guedes. A identificação bibliográfica primária exata da publicação original permanece registrada como informação bibliográfica da família e não deve ser complementada por inferência.
