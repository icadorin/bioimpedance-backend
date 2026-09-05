# EquationVariantScientificProfile — G-M4

## 1. Identification

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

A variante corresponde à equação específica de quatro dobras para homens apresentada por Guedes (1985), desenvolvida para adultos jovens universitários.

Referência principal:

```text
Guedes, D. P. (1985).
Estudo da gordura corporal através da mensuração dos valores de densidade corporal e da espessura de dobras cutâneas de universitários.
Dissertação de Mestrado. Universidade Federal de Santa Maria, Santa Maria, RS.
```

---

# 2. Mathematical Definition

A definição matemática da `G-M4` é:

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

A equação é uma regressão específica baseada no logaritmo decimal da soma das quatro dobras. Fontes secundárias que reproduzem o conjunto de equações de Guedes de 1985 registram para esta variante `D = 1.18282 - 0.07030 Log10(AB+TR+SI+AX)`, com erro padrão de estimativa de 0.0057 g/ml e correlação de 0.89 na amostra de desenvolvimento. [1][2]

---

# 3. Applicability

## 3.1 Sexo

```text
sex:

    supportedSexes:
        - MALE
```

A variante pertence ao conjunto de equações masculinas de Guedes.

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

As fontes que reproduzem o conjunto original de equações de Guedes identificam a amostra de desenvolvimento como `n = 110`, com idade de `17–27 anos`. [1][2]

A média de idade não foi confirmada de forma suficientemente específica para esta ficha e, portanto, permanece `null`.

A faixa de 17–27 anos representa a faixa etária da população de desenvolvimento documentada. Ela não deve ser automaticamente transformada em uma `explicitAgeRestriction` sem uma formulação explícita de restrição de uso.

---

# 4. Population Applicability

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
            "Universitários da Universidade Federal de Santa Maria; a
             equação pertence ao conjunto de equações específicas de Guedes
             desenvolvido para adultos jovens brasileiros."

        source:
            Guedes (1985)

    validationPopulations:
        []
```

A literatura secundária identifica as equações específicas de Guedes como desenvolvidas em universitários brasileiros adultos jovens, e os resultados publicados para o conjunto masculino registram `n = 110` e idade de 17–27 anos. [1][2][3]

O país e a região são mantidos como características descritivas da população estudada. Não serão convertidos automaticamente em uma regra de compatibilidade nacional pelo motor.

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

A população é descrita como universitários, mas isso não é suficiente para classificar formalmente cada participante como atleta ou não atleta na escala operacional da plataforma.

Portanto, a informação permanece não documentada.

---

# 6. Training Level Applicability

```text
trainingLevel:

    supportedLevels:
        []

    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

Não deve ser inferido que estudantes universitários correspondam a `SEDENTARY`, `RECREATIONAL` ou qualquer outro nível.

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

Nenhuma regra específica de limitação por característica corporal foi cadastrada nesta ficha.

As estatísticas da amostra não serão convertidas artificialmente em regras de `LOW_BODY_FAT`, `HIGH_BODY_FAT`, `OBESITY` ou equivalentes.

---

# 9. Validation Evidence

## 9.1 Development Evidence

```text
development:

    studyReference:

        citation:
            "Guedes, D. P. (1985).
             Estudo da gordura corporal através da mensuração dos valores
             de densidade corporal e da espessura de dobras cutâneas de
             universitários. Dissertação de Mestrado, Universidade Federal
             de Santa Maria, Santa Maria, RS."

        doi:
            null

        url:
            null

    population:
        "Homens universitários, n = 110, 17–27 anos"

    criterionMethod:
        "Pesagem hidrostática / densidade corporal por método hidrostático"

    year:
        1985
```

O trabalho de Guedes é citado na literatura como estudo específico para universitários brasileiros e suas equações foram derivadas por regressão a partir de medidas de dobras cutâneas e densidade corporal de referência. [2][3]

---

# 10. Development Metrics

Os valores reproduzidos para esta variante no conjunto de equações de Guedes são:

```text
metrics:

    correlation:
        0.89

    standardError:
        0.0057

    meanDifference:
        null

    rmse:
        null

    otherMetrics:
        null
```

A tabela de desenvolvimento reproduzida em literatura posterior apresenta para `G-M4` erro padrão de estimativa de `0.0057`, correlação de `0.89`, `n = 110` e idade de `17–27` anos. [1][2]

---

# 11. Validation Studies

```text
validationStudies:
    []
```

Não foi identificada nesta etapa uma validação independente suficientemente específica para registrar como `ValidationStudy` sem misturar estudos posteriores de comparação ou validação populacional com a validação original da variante.

---

# 12. Cross-validation

```text
crossValidationStudies:
    []
```

Não foi identificada, nas fontes consultadas para esta ficha, uma amostra de cross-validation claramente atribuível especificamente à `G-M4`.

Portanto, não serão reutilizadas métricas de estudos posteriores como se fossem cross-validation original.

---

# 13. External Validation

```text
externalValidationStudies:
    []
```

---

# 14. Measurement Requirements

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

A equação não utiliza idade, massa corporal ou estatura como variáveis matemáticas.

Como nas variantes anteriores, `SEX` permanece em `applicability` e não em `requiredInputs`.

A separação é:

```text
SEX
    → Applicability

4 skinfolds
    → Formula inputs
```

---

# 15. Scientific Restrictions

```text
restrictions:
    []
```

A faixa de desenvolvimento de 17–27 anos não foi transformada em restrição explícita porque a fonte consultada não foi interpretada como estabelecendo uma proibição formal fora dessa faixa.

---

# 16. Source Conflict

```text
evidence:

    sourceConflict:
        null
```

Não foi identificado conflito material nas fontes consultadas quanto à forma matemática da `G-M4`:

```text
D = 1.18282 - 0.07030 × log10(AB + TR + SI + AX)
```

Há variações de arredondamento/apresentação em materiais posteriores, mas não foram consideradas conflito científico material nesta ficha.

---

# 17. Lifecycle

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

A variante possui definição matemática identificada, sexo documentado, população de desenvolvimento, faixa etária, inputs e referência de origem suficientes para a estrutura operacional definida.

Informações contextuais não documentadas permanecem explicitamente ausentes e não impedem `ACTIVE` por si mesmas.

---

# 18. Ficha consolidada

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
                    "Universitários da Universidade Federal de Santa Maria."

                source:
                    Guedes (1985)

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
                Guedes (1985)

            population:
                "Homens universitários, n = 110, 17–27 anos"

            criterionMethod:
                "Pesagem hidrostática / método hidrostático"

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

# 19. Pontos para o SuggestionEngine

A ficha permite ao motor distinguir claramente:

```text
sexo:
    MALE → compatível
    FEMALE → incompatível

idade:
    desenvolvimento documentado em 17–27 anos
    média de idade → NOT_DOCUMENTED

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

sem documentação específica para essas inferências.

---

# 20. Referência principal

```text
Reference

    citation:
        "Guedes, D. P. (1985).
         Estudo da gordura corporal através da mensuração dos valores de
         densidade corporal e da espessura de dobras cutâneas de universitários.
         Dissertação de Mestrado. Universidade Federal de Santa Maria,
         Santa Maria, RS."

    doi:
        null

    url:
        null
```

### Fontes consultadas para confirmação

[1] Moura, J. A. et al. (2003). *Validação de equações para estimativa da densidade corporal em atletas de futebol categoria sub-20*. Revista Brasileira de Cineantropometria & Desempenho Humano. Tabela de equações de Guedes (1985), com `n = 110`, idade `17–27`, `r = 0.89` e `SEE = 0.0057` para a equação de quatro dobras.

[2] Revista Brasileira de Cineantropometria & Desempenho Humano, tabela de equações específicas de Guedes (1985), reproduzindo as oito equações masculinas e suas estatísticas de desenvolvimento.

[3] Guedes, D. P.; Sampedro, R. M. F. (1986). *Tentativa de validação de equações para predição dos valores de densidade corporal com base nas espessuras de dobras cutâneas em universitários*. Revista Brasileira de Ciência do Esporte.
