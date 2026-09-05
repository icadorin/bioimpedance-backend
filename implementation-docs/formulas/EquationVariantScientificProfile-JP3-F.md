# EquationVariantScientificProfile — JP3-F

## 1. Identification

```text
identity:

    variantId:
        "JP3-F"

    familyId:
        "jackson-pollock"

    displayName:
        "Jackson, Pollock & Ward 3 Dobras — Feminino"

    aliasNames:
        [
            "Jackson-Pollock-Ward 3-Site — Female",
            "JPW3",
            "JP3 Female"
        ]
```

A variante corresponde à equação generalizada de três dobras para mulheres desenvolvida por Jackson, Pollock & Ward (1980).

Referência principal:

```text
Jackson, A. S.; Pollock, M. L.; Ward, A. (1980).
Generalized equations for predicting body density of women.
Medicine and Science in Sports and Exercise, 12(3), 175–181.
DOI: 10.1249/00005768-198023000-00009
PMID: 7402053
```

---

# 2. Mathematical Definition

A definição matemática da `JP3-F` é:

```text
D =
    1.0994921
    - 0.0009929 × Σ3
    + 0.0000023 × Σ3²
    - 0.0001392 × idade
```

Onde:

```text
D
    = BODY_DENSITY

Σ3
    = soma das três dobras cutâneas

idade
    = idade em anos
```

As três dobras da `Σ3` são:

```text
- tríceps
- supra-ilíaca
- coxa
```

A equação é uma forma quadrática que combina a soma das três dobras com idade para estimar densidade corporal.

### Forma computacional

```text
sum3 =
    SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_THIGH

bodyDensity =
    1.0994921
    - 0.0009929 × sum3
    + 0.0000023 × sum3²
    - 0.0001392 × AGE
```

### Output

```text
outputType = BODY_DENSITY
```

A conversão posterior de densidade corporal para percentual de gordura permanece separada da equação de densidade.

---

# 3. Applicability

## 3.1 Sexo

```text
sex:

    supportedSexes:
        - FEMALE
```

A publicação original é dedicada à derivação de equações generalizadas para mulheres.

---

## 3.2 Idade

```text
age:

    originalDevelopmentAgeRange:
        min: 18
        max: 55

    developmentSampleMeanAge:
        31.4

    validatedAgeRanges:

        - range:
            min: 18
            max: 55

          population:
            "Mulheres adultas da amostra independente de cross-validation"

          source:
            Jackson, Pollock & Ward (1980)

    explicitAgeRestriction:
        null
```

A amostra de desenvolvimento continha 249 mulheres entre 18 e 55 anos, com idade média de 31,4 ± 10,8 anos.

A amostra independente de cross-validation continha 82 mulheres com características semelhantes de idade e composição corporal.

O valor:

```text
31.4
```

é descritivo e não deve ser utilizado como limite de elegibilidade.

### Observação sobre mulheres acima de 40 anos

A publicação original afirma que deve haver cautela na aplicação das equações em mulheres acima de 40 anos.

Essa observação é preservada como limitação da evidência e não é convertida automaticamente em `explicitAgeRestriction`, porque não foi apresentada como uma proibição explícita de uso.

---

# 4. Population Applicability

```text
population:

    originalPopulation:

        description:
            "Mulheres adultas"

        country:
            null

        region:
            null

        sexCoverage:
            - FEMALE

        ageCoverage:
            min: 18
            max: 55

        sampleSize:
            249

        bodyCharacteristicsNotes:
            "Amostra com percentual de gordura de aproximadamente 4% a 44%,
             com média de 24,1% e desvio-padrão de 7,2%."

        sampleCharacteristics:
            "Mulheres adultas com considerável variabilidade na estrutura
             corporal, composição corporal e prática de exercícios."

        source:
            Jackson, Pollock & Ward (1980)

    validationPopulations:

        - description:
            "Mulheres adultas da amostra independente de cross-validation"

          country:
            null

          region:
            null

          sexCoverage:
            - FEMALE

          ageCoverage:
            min: 18
            max: 55

          sampleSize:
            82

          bodyCharacteristicsNotes:
            "Características de idade e percentual de gordura semelhantes
             às da amostra de desenvolvimento."

          sampleCharacteristics:
            "Amostra independente utilizada para cross-validation."

          source:
            Jackson, Pollock & Ward (1980)
```

O estudo avaliou 331 mulheres no total, divididas aleatoriamente em 249 participantes para derivação das equações e 82 participantes para validação cruzada.

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

Embora a publicação descreva variabilidade na prática de exercícios, não é apropriado converter essa descrição diretamente para `athlete = true` ou `athlete = false`.

---

# 6. Training Level Applicability

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

# 7. Modality Applicability

```text
modality:

    supportedModalities:
        []

    notes:
        "Não documentado em termos de modalidades esportivas específicas."
```

---

# 8. Body Characteristics

```text
bodyCharacteristics:

    rules:
        []
```

O intervalo de composição corporal observado na amostra é preservado como característica descritiva da população e não como uma restrição da fórmula.

Não foi criada regra específica para:

```text
LOW_BODY_FAT
HIGH_BODY_FAT
OBESITY
HIGH_MUSCLE_MASS
EXTREME_SKINFOLD_VALUES
EXTREME_SKINFOLD_SUM
```

por ausência de fundamento suficiente para transformar esses achados descritivos em regras de aplicabilidade.

---

# 9. Validation Evidence

## 9.1 Development Evidence

```text
development:

    studyReference:

        citation:
            "Jackson, A. S.; Pollock, M. L.; Ward, A. (1980).
             Generalized equations for predicting body density of women.
             Medicine and Science in Sports and Exercise, 12(3), 175–181."

        doi:
            "10.1249/00005768-198023000-00009"

    population:
        "Mulheres adultas, n = 249, 18–55 anos"

    criterionMethod:
        "Densidade corporal determinada pelo método hidrostático"

    year:
        1980
```

O método hidrostático foi utilizado para determinar a densidade corporal de referência.

---

# 10. Cross-validation

```text
crossValidationStudies:

    - studyReference:

        citation:
            "Jackson, A. S.; Pollock, M. L.; Ward, A. (1980).
             Generalized equations for predicting body density of women.
             Medicine and Science in Sports and Exercise, 12(3), 175–181."

        doi:
            "10.1249/00005768-198023000-00009"

      population:

        description:
            "Mulheres adultas da amostra independente de cross-validation"

        country:
            null

        region:
            null

        sexCoverage:
            - FEMALE

        ageCoverage:
            min: 18
            max: 55

        sampleSize:
            82

        bodyCharacteristicsNotes:
            "Características de idade e percentual de gordura semelhantes
             às da amostra de desenvolvimento."

        sampleCharacteristics:
            "Amostra independente utilizada para cross-validation."

        source:
            Jackson, Pollock & Ward (1980)

      criterionMethod:
          "Densidade corporal determinada por método hidrostático"

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
              "Para o conjunto das equações femininas, a publicação original
               reporta correlações entre 0.815 e 0.820 e erros padrão entre
               3.7 e 4.0 % de gordura. Para a variante de 3 dobras, fontes
               secundárias que identificam individualmente a equação reportam
               r = 0.820 e SEE = 3.7 % de gordura; esses valores não foram
               convertidos para standardError sem uma definição de unidade
               compatível com o schema."

      limitations:
          "A publicação recomenda cautela na utilização em mulheres acima
           de 40 anos."
```

A publicação original informa que a validação cruzada foi realizada em uma amostra diferente de 82 mulheres com características semelhantes de idade e percentual de gordura.

O resumo original apresenta, para o conjunto das equações femininas, correlações entre 0,815 e 0,820 e erros padrão entre 3,7% e 4,0% de gordura. Para a equação específica de três dobras, uma fonte secundária que a identifica individualmente reporta `r = 0,820` e `SEE = 3,7%`. Como o campo `standardError` do schema não define unidade própria para % de gordura, o valor permanece em `otherMetrics`.

---

# 11. Validation Studies

```text
validationStudies:
    []
```

A amostra independente de 82 mulheres é representada em `crossValidationStudies`, preservando a distinção metodológica da publicação original.

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

        - AGE
        - SKINFOLD_TRICEPS
        - SKINFOLD_SUPRAILIAC
        - SKINFOLD_THIGH

    optionalInputs:
        []
```

`SEX` não é input matemático da fórmula.

A separação permanece:

```text
SEX
    → Applicability

AGE
    → Formula input

3 skinfolds
    → Formula inputs
```

A soma utilizada pela equação é composta por:

```text
SKINFOLD_TRICEPS
SKINFOLD_SUPRAILIAC
SKINFOLD_THIGH
```

---

# 14. Scientific Restrictions

```text
restrictions:
    []
```

A cautela documentada para mulheres acima de 40 anos é registrada em `limitations` e não convertida em uma restrição científica explícita de inelegibilidade.

---

# 15. Source Conflict

```text
evidence:

    sourceConflict:
        null
```

Não foi identificado conflito material na definição matemática da JP3-F nas fontes consultadas.

A forma reproduzida de maneira consistente é:

```text
D =
    1.0994921
    - 0.0009929 × Σ3
    + 0.0000023 × Σ3²
    - 0.0001392 × age
```

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

A variante possui as informações científicas essenciais para avaliação e execução:

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

Informações não documentadas sobre atleta, treinamento, modalidade e validação externa não impedem `ACTIVE`.

---

# 17. Ficha consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:
            "JP3-F"

        familyId:
            "jackson-pollock"

        displayName:
            "Jackson, Pollock & Ward 3 Dobras — Feminino"

        aliasNames:
            [
                "Jackson-Pollock-Ward 3-Site — Female",
                "JPW3",
                "JP3 Female"
            ]

    applicability:

        sex:

            supportedSexes:
                - FEMALE

        age:

            originalDevelopmentAgeRange:
                min: 18
                max: 55

            developmentSampleMeanAge:
                31.4

            validatedAgeRanges:

                - range:
                    min: 18
                    max: 55

                  population:
                    "Mulheres adultas da amostra independente de cross-validation"

                  source:
                    Jackson, Pollock & Ward (1980)

            explicitAgeRestriction:
                null

        population:

            originalPopulation:

                description:
                    "Mulheres adultas"

                country:
                    null

                region:
                    null

                sexCoverage:
                    - FEMALE

                ageCoverage:
                    min: 18
                    max: 55

                sampleSize:
                    249

                bodyCharacteristicsNotes:
                    "Percentual de gordura aproximadamente de 4% a 44%,
                     média de 24,1% e desvio-padrão de 7,2%."

                sampleCharacteristics:
                    "Mulheres adultas com considerável variabilidade na
                     estrutura corporal, composição corporal e prática
                     de exercícios."

                source:
                    Jackson, Pollock & Ward (1980)

            validationPopulations:

                - description:
                    "Mulheres adultas da amostra independente de cross-validation"

                  country:
                    null

                  region:
                    null

                  sexCoverage:
                    - FEMALE

                  ageCoverage:
                    min: 18
                    max: 55

                  sampleSize:
                    82

                  bodyCharacteristicsNotes:
                    "Características de idade e percentual de gordura
                     semelhantes às da amostra de desenvolvimento."

                  sampleCharacteristics:
                    "Amostra independente utilizada para cross-validation."

                  source:
                    Jackson, Pollock & Ward (1980)

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
                Jackson, Pollock & Ward (1980)

            population:
                "Mulheres adultas, n = 249, 18–55 anos"

            criterionMethod:
                "Densidade corporal determinada pelo método hidrostático"

            year:
                1980

        validationStudies:
            []

        crossValidationStudies:

            - studyReference:
                Jackson, Pollock & Ward (1980)

              population:
                "Mulheres adultas, n = 82"

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
                    "Para o conjunto das equações femininas: r = 0.815–0.820;
                     SE = 3.7–4.0% BF. Para JP3-F, fonte secundária reporta
                     r = 0.820 e SEE = 3.7% BF."

              limitations:
                "Cautela recomendada pelo estudo em mulheres acima de 40 anos."

        externalValidationStudies:
            []

        sourceConflict:
            null

    inputs:

        requiredInputs:
            - AGE
            - SKINFOLD_TRICEPS
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

# 18. Pontos importantes para o SuggestionEngine

```text
sexo:
    FEMALE → compatível
    MALE   → incompatível

idade:
    população de desenvolvimento = 18–55
    média = 31.4
    média NÃO é limite

>40:
    existe cautela documentada
    NÃO existe restrição explícita de inelegibilidade

atleta:
    NOT_DOCUMENTED

trainingLevel:
    NOT_DOCUMENTED

modality:
    NOT_DOCUMENTED

inputs:
    AGE + 3 skinfolds

output:
    BODY_DENSITY
```

O motor não deve converter a ausência de documentação específica sobre atleta, treinamento ou modalidade em inelegibilidade.

---

# 19. Referência principal

```text
Reference

    citation:
        "Jackson, A. S.; Pollock, M. L.; Ward, A. (1980).
         Generalized equations for predicting body density of women.
         Medicine and Science in Sports and Exercise, 12(3), 175–181."

    doi:
        "10.1249/00005768-198023000-00009"

    url:
        "https://doi.org/10.1249/00005768-198023000-00009"
```
