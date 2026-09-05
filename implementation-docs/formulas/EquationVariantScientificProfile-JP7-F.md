# EquationVariantScientificProfile — JP7-F

## 1. Identification

```text
identity:

    variantId:
        "JP7-F"

    familyId:
        "jackson-pollock"

    displayName:
        "Jackson, Pollock & Ward 7 Dobras — Feminino"

    aliasNames:
        [
            "Jackson-Pollock-Ward 7-Site — Female",
            "JPW7",
            "JP7 Female"
        ]
```

A variante corresponde à equação generalizada de sete dobras para mulheres desenvolvida por Jackson, Pollock e Ward.

Referência principal:

```text
Jackson, A. S.; Pollock, M. L.; Ward, A. (1980).
Generalized equations for predicting body density of women.
Medicine and Science in Sports and Exercise, 12(3), 175–181.
DOI: 10.1249/00005768-198023000-00009
PMID: 7402053
```

A publicação é indexada no PubMed e apresenta o objetivo de desenvolver equações generalizadas para mulheres com diferentes idades e níveis de composição corporal.

---

# 2. Mathematical Definition

A definição matemática da `JP7-F` é:

```text
D =
    1.09700000
    - 0.00046971 × Σ7
    + 0.00000056 × Σ7²
    - 0.00012828 × idade
```

Onde:

```text
D
    = BODY_DENSITY

Σ7
    = soma das sete dobras cutâneas

idade
    = idade em anos
```

As sete dobras são:

```text
- peito / chest
- axilar média / midaxillary
- tríceps
- subescapular
- abdominal
- supra-ilíaca
- coxa
```

Essa forma da equação é reproduzida em trabalhos posteriores que utilizam a equação original de Jackson, Pollock & Ward.

### Forma computacional

```text
sum7 =
    SKINFOLD_CHEST
    + SKINFOLD_AXILLARY_MID
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUBSCAPULAR
    + SKINFOLD_ABDOMEN
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_THIGH

bodyDensity =
    1.09700000
    - 0.00046971 × sum7
    + 0.00000056 × sum7²
    - 0.00012828 × AGE
```

### Output

```text
outputType = BODY_DENSITY
```

A conversão posterior de densidade corporal para percentual de gordura permanece uma etapa separada da equação de densidade, conforme a arquitetura definida para a plataforma.

---

# 3. Applicability

## 3.1 Sexo

```text
sex:

    supportedSexes:
        - FEMALE
```

A fonte original é especificamente dedicada a mulheres e a equação está definida para a população feminina do estudo.

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
            "Mulheres adultas da amostra independente de validação cruzada"

          source:
            Jackson, Pollock & Ward (1980)

    explicitAgeRestriction:
        null
```

A publicação informa uma faixa de 18 a 55 anos para as 249 mulheres da amostra de desenvolvimento e apresenta média de idade de 31,4 ± 10,8 anos. A amostra de 82 mulheres utilizada na validação cruzada tinha características de idade e percentual de gordura semelhantes.

O valor:

```text
31.4
```

é puramente descritivo e não será utilizado pelo motor como limite de elegibilidade.

### Observação sobre >40 anos

O resumo original encerra com a recomendação de que seja tomada cautela com mulheres acima de 40 anos.

Como essa redação não estabelece:

```text
"não utilizar acima de 40"
```

não vamos transformá-la artificialmente em:

```text
explicitAgeRestriction = [0,40]
```

Ela será preservada como **limitação da evidência**, não como restrição científica explícita.

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
            "Amostra com ampla variação de composição corporal; percentual
             de gordura de aproximadamente 4% a 44%, com média de 24,1%."

        sampleCharacteristics:
            "Mulheres adultas apresentando variabilidade considerável em
             estrutura corporal, composição corporal e prática de exercícios."

        source:
            Jackson, Pollock & Ward (1980)

    validationPopulations:

        - description:
            "Mulheres adultas da amostra independente de validação cruzada"

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
             à amostra de desenvolvimento."

          sampleCharacteristics:
            "Amostra independente utilizada para cross-validation."

          source:
            Jackson, Pollock & Ward (1980)
```

O estudo original informa que foram avaliadas 249 mulheres de 18 a 55 anos, com percentual de gordura de aproximadamente 4% a 44%, média de 24,1 ± 7,2%, além de uma amostra independente de 82 mulheres usada para cross-validation.

Uma fonte secundária brasileira descreve o conjunto total como 331 mulheres, dividido entre as 249 da regressão e as 82 da validação, e confirma a coleta das sete dobras cutâneas.

### Sobre raça/etnia

Não incorporar automaticamente classificações raciais/étnicas presentes em trabalhos posteriores à população original da variante, salvo quando uma fonte adequada demonstrar que isso faz parte da definição científica relevante da variante.

Isso evita transformar uma adaptação ou aplicação posterior em característica da população original.

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

A publicação descreve variabilidade na prática de exercícios, mas isso não é suficiente para converter a amostra para a classificação operacional da plataforma.

Portanto:

```text
null
```

permanece o valor correto.

---

# 6. Training Level Applicability

```text
trainingLevel:

    supportedLevels:
        []

    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

Não foi encontrada base suficiente para mapear a amostra original diretamente para:

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

Embora o estudo abranja mulheres com aproximadamente 4% a 44% de gordura corporal, esse intervalo descreve a amostra e não constitui, por si só, uma regra explícita como:

```text
LOW_BODY_FAT
HIGH_BODY_FAT
OBESITY
EXTREME_SKINFOLD_VALUES
```

Portanto, não foi cadastrada uma `BodyCharacteristicRule` artificial.

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

O método hidrostático foi utilizado como referência para determinar densidade corporal e percentual de gordura.

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
            "Idade e percentual de gordura com características semelhantes
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
              "A publicação original reporta, no conjunto das equações,
               correlações entre 0.815 e 0.820 e erros padrão entre
               3.7% e 4.0% de gordura, mas o resumo disponível não atribui
               individualmente essas métricas à JP7-F."

      limitations:
          "O estudo recomenda cautela na utilização em mulheres acima
           de 40 anos."
```

Aqui foi adotada uma regra importante de integridade científica.

O resumo fornece:

```text
cross-validation:
    r = 0.815–0.820
    SE = 3.7–4.0 %F
```

para o conjunto das equações femininas, não identificando, no resumo disponível, qual valor pertence especificamente à `JP7-F`.

Portanto, não deve ser inventado um valor específico para a variante.

Os campos específicos permanecem:

```text
correlation = null
standardError = null
```

enquanto a informação agregada é preservada em `otherMetrics`.

---

# 11. Validation Studies

```text
validationStudies:
    []
```

A validação disponível na publicação original é representada como:

```text
crossValidationStudies
```

porque a publicação utiliza uma amostra independente para cross-validation.

---

# 12. External Validation

```text
externalValidationStudies:
    []
```

Nenhum estudo de validação externa independente foi adicionado nesta ficha.

---

# 13. Measurement Requirements

```text
inputs:

    requiredInputs:

        - AGE
        - SKINFOLD_CHEST
        - SKINFOLD_AXILLARY_MID
        - SKINFOLD_TRICEPS
        - SKINFOLD_SUBSCAPULAR
        - SKINFOLD_ABDOMEN
        - SKINFOLD_SUPRAILIAC
        - SKINFOLD_THIGH

    optionalInputs:
        []
```

E, mantendo a regra definida na JP7-M:

```text
SEX
```

**não entra em `requiredInputs`**, porque não é variável matemática da equação.

A separação continua:

```text
SEX
    → Applicability

AGE
    → Formula input

7 skinfolds
    → Formula inputs
```

---

# 14. Scientific Restrictions

```text
restrictions:
    []
```

Não transformar a recomendação de cautela em mulheres acima de 40 anos em `INELIGIBLE`, pois a fonte não estabelece uma proibição explícita.

A informação é preservada em `ValidationStudy.limitations`.

---

# 15. Source Conflict

```text
evidence:

    sourceConflict:
        null
```

Não foi identificado conflito material quanto à definição matemática da JP7-F nas fontes consultadas.

A forma reproduzida é:

```text
D =
1.097
- 0.00046971 × Σ7
+ 0.00000056 × Σ7²
- 0.00012828 × age
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

A variante possui as informações essenciais necessárias para publicação operacional:

```text
identity                    ✓
mathematicalDefinition      ✓
supportedSexes              ✓
developmentAgeRange         ✓
originalPopulation          ✓
requiredInputs              ✓
reference                   ✓
sourceConflict              null
```

Informações não documentadas como atleta, nível de treinamento, modalidade e validação externa não impedem `ACTIVE` conforme a regra estabelecida.

---

# 17. Ficha consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:
            "JP7-F"

        familyId:
            "jackson-pollock"

        displayName:
            "Jackson, Pollock & Ward 7 Dobras — Feminino"

        aliasNames:
            [
                "Jackson-Pollock-Ward 7-Site — Female",
                "JPW7",
                "JP7 Female"
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
                    "Mulheres adultas da amostra independente
                     de cross-validation"

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
                     média de 24,1%."

                sampleCharacteristics:
                    "Mulheres adultas com variabilidade considerável em
                     estrutura corporal, composição corporal e prática
                     de exercícios."

                source:
                    Jackson, Pollock & Ward (1980)

            validationPopulations:

                - description:
                    "Mulheres adultas da amostra independente
                     de cross-validation"

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
                     semelhantes à amostra de desenvolvimento."

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
                    "Para o conjunto das equações: r = 0.815–0.820;
                     SE = 3.7–4.0% BF."

              limitations:
                "O estudo recomenda cautela em mulheres acima de 40 anos."

        externalValidationStudies:
            []

        sourceConflict:
            null

    inputs:

        requiredInputs:
            - AGE
            - SKINFOLD_CHEST
            - SKINFOLD_AXILLARY_MID
            - SKINFOLD_TRICEPS
            - SKINFOLD_SUBSCAPULAR
            - SKINFOLD_ABDOMEN
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

A ficha produz uma interpretação limpa:

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
    AGE + 7 skinfolds

output:
    BODY_DENSITY
```

Isso permite que o motor derive seus próprios estados de aplicabilidade, evidência, elegibilidade e readiness, sem armazená-los na ficha científica.

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

---

# Controle mestre — V1

| # | Família | Variante | Status |
|---:|---|---|---|
| 1 | Jackson & Pollock | **JP7-M** | ✅ Feita |
| 2 | Jackson, Pollock & Ward | **JP7-F** | ✅ Feita |
| 3 | Jackson & Pollock | JP3-M | ⬜ Pendente |
| 4 | Jackson, Pollock & Ward | JP3-F | ⬜ Pendente |
| 5–16 | Guedes | G-M3 … G-F8 | ⬜ Pendente |
| 17–46 | Petroski | P-M1 … P-F16 | ⬜ Pendente |
| 47 | Faulkner | FALK4 | ⬜ Pendente |

**Progresso: 2/47 — 4,3% concluído.**
