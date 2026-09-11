# EquationVariantScientificProfile — JP3-F

## 1. Identificação

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

## 2. Definição Matemática

A definição matemática da `JP3-F` é:

```text
D =

    1.0994921

    - 0.0009929 × Σ3

    + 0.0000023 × Σ3²

    - 0.0001392 × AGE
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

As três dobras da `Σ3` são:

```text
- tríceps
- supra-ilíaca
- coxa
```

A equação utiliza a soma das três dobras em forma quadrática, em conjunto com a idade, para estimar `BODY_DENSITY`.

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

A conversão posterior de `BODY_DENSITY` para percentual de gordura permanece separada da equação de densidade.

---

## 3. Aplicabilidade

### 3.1 Sexo

```text
sex:

    supportedSexes:

        - FEMALE
```

A publicação original foi desenvolvida para mulheres adultas.

---

### 3.2 Idade

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

            "Jackson, Pollock & Ward (1980)"

    explicitAgeRestriction:

        null
```

A amostra de desenvolvimento continha 249 mulheres entre 18 e 55 anos, com idade média de 31,4 ± 10,8 anos.

A amostra independente de validação cruzada continha 82 mulheres com características semelhantes de idade e percentual de gordura.

O valor:

```text
31.4
```

é descritivo e não deve ser utilizado como limite de elegibilidade.

### Observação sobre mulheres acima de 40 anos

A publicação original recomenda cautela na aplicação das equações em mulheres acima de 40 anos.

Essa observação é preservada como limitação da evidência e não é convertida automaticamente em `explicitAgeRestriction`, porque não é apresentada como proibição explícita de uso.

---

## 4. Aplicabilidade Populacional

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

            "Percentual de gordura aproximadamente de 4% a 44%,

             com média de 24,1% e desvio-padrão de 7,2%."

        sampleCharacteristics:

            "Mulheres adultas com variabilidade na estrutura

             corporal, composição corporal e prática de exercícios."

        source:

            "Jackson, Pollock & Ward (1980)"

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

             às da amostra de desenvolvimento."

          sampleCharacteristics:

            "Amostra independente utilizada para validação cruzada."

          source:

            "Jackson, Pollock & Ward (1980)"
```


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

Embora a publicação descreva variabilidade na prática de exercícios, não é apropriado converter essa descrição diretamente para uma classificação de atleta ou não atleta.

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

## 9. Evidências de Validação

### 9.1 Evidências de Desenvolvimento

```text
development:

    studyReference:

        citation:

            "Jackson, A. S.; Pollock, M. L.; Ward, A. (1980).

             Generalized equations for predicting body density of women.

             Medicine and Science in Sports and Exercise, 12(3), 175–181."

        doi:

            "10.1249/00005768-198023000-00009"

        url:

            "https://pubmed.ncbi.nlm.nih.gov/7402053/"

    population:

        "Mulheres adultas, n = 249, 18–55 anos"

    criterionMethod:

        "Densidade corporal determinada pelo método hidrostático"

    year:

        1980

    metrics:

        correlation:

            null

        r2:

            null

        standardError:

            null

        otherMetrics:

            "A publicação original reporta, para o conjunto das equações

             femininas, correlações múltiplas de 0,842 a 0,867 e erros

             padrão de 3,6% a 3,8% de gordura. Esses valores não são

             atribuídos individualmente à JP3-F."
```



---

## 10. Validação

```text
validationStudies:

    []
```

Não há, nesta ficha, uma subamostra classificada separadamente como validação segundo a regra operacional adotada.

---

## 11. Validação Cruzada

```text
crossValidationStudies:

    - studyReference:

        citation:

            "Jackson, A. S.; Pollock, M. L.; Ward, A. (1980).

             Generalized equations for predicting body density of women.

             Medicine and Science in Sports and Exercise, 12(3), 175–181."

        doi:

            "10.1249/00005768-198023000-00009"

        url:

            "https://pubmed.ncbi.nlm.nih.gov/7402053/"

      population:

        description:

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

             às da amostra de desenvolvimento."

        sampleCharacteristics:

            "Amostra independente utilizada para validação cruzada."

        source:

            "Jackson, Pollock & Ward (1980)"

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

            "A publicação original reporta, para o conjunto das equações

             femininas, correlações de 0,815 a 0,820 e erros padrão de

             3,7% a 4,0% de gordura. O valor específico da JP3-F não é

             individualizado na fonte primária consultada."

      limitations:

        "A publicação recomenda cautela na utilização das equações

         em mulheres acima de 40 anos."
```


Como a fonte primária consultada não individualiza no resumo a métrica específica da JP3-F, os campos `correlation` e `standardError` permanecem `null`, e os valores agregados são mantidos em `otherMetrics`.

---

## 12. Validação Externa

```text
externalValidationStudies:

    []
```

Nenhum estudo de validação externa foi incorporado nesta ficha.

---

## 13. Requisitos de Medição

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

## 14. Restrições Científicas

```text
restrictions:

    []
```

A cautela documentada para mulheres acima de 40 anos é registrada nas limitações da evidência e não convertida em uma restrição científica explícita de inelegibilidade.

---

## 15. Conflito de Fonte

```text
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

    - 0.0001392 × AGE
```

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

A variante possui as informações científicas essenciais para avaliação e execução:

```text
identity                       ✓

mathematicalDefinition         ✓

supportedSexes                ✓

originalDevelopmentAgeRange   ✓

originalPopulation            ✓

requiredInputs                ✓

reference                     ✓

sourceConflict                null
```

Informações não documentadas sobre atleta, treinamento, modalidade e validação externa não impedem `ACTIVE`.

---

## 17. Ficha Consolidada

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

    mathematicalDefinition:

        formula:

            "D = 1.0994921 - 0.0009929 × Σ3

             + 0.0000023 × Σ3² - 0.0001392 × AGE"

        variables:

            Σ3:

                "Soma de SKINFOLD_TRICEPS + SKINFOLD_SUPRAILIAC

                 + SKINFOLD_THIGH"

            AGE:

                "Idade em anos"

        outputType:

            BODY_DENSITY

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

                    "Mulheres adultas da amostra independente de validação cruzada"

                  source:

                    "Jackson, Pollock & Ward (1980)"

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

                    "Mulheres adultas com variabilidade na estrutura

                     corporal, composição corporal e prática de exercícios."

                source:

                    "Jackson, Pollock & Ward (1980)"

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

                    "Características de idade e percentual de gordura

                     semelhantes às da amostra de desenvolvimento."

                  sampleCharacteristics:

                    "Amostra independente utilizada para validação cruzada."

                  source:

                    "Jackson, Pollock & Ward (1980)"

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

                    "Jackson, A. S.; Pollock, M. L.; Ward, A. (1980).

                     Generalized equations for predicting body density of women.

                     Medicine and Science in Sports and Exercise,

                     12(3), 175–181."

                doi:

                    "10.1249/00005768-198023000-00009"

                url:

                    "https://pubmed.ncbi.nlm.nih.gov/7402053/"

            population:

                "Mulheres adultas, n = 249, 18–55 anos"

            criterionMethod:

                "Densidade corporal determinada pelo método hidrostático"

            year:

                1980

            metrics:

                correlation:

                    null

                r2:

                    null

                standardError:

                    null

                otherMetrics:

                    "Para o conjunto das equações femininas:

                     correlações múltiplas de 0,842 a 0,867 e erros

                     padrão de 3,6% a 3,8% de gordura."

        validationStudies:

            []

        crossValidationStudies:

            - studyReference:

                citation:

                    "Jackson, A. S.; Pollock, M. L.; Ward, A. (1980).

                     Generalized equations for predicting body density of women.

                     Medicine and Science in Sports and Exercise,

                     12(3), 175–181."

                doi:

                    "10.1249/00005768-198023000-00009"

                url:

                    "https://pubmed.ncbi.nlm.nih.gov/7402053/"

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

                    "Para o conjunto das equações femininas:

                     r = 0,815–0,820 e erro padrão = 3,7–4,0%

                     de gordura. Os valores não foram atribuídos

                     individualmente à JP3-F."

              limitations:

                "Cautela recomendada pelo estudo em mulheres

                 acima de 40 anos."

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

## 18. Interpretação para o SuggestionEngine

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

A cautela para mulheres acima de 40 anos deve ser tratada como limitação científica da evidência, e não como `INELIGIBLE`, salvo se outra regra do sistema ou nova evidência científica específica determinar o contrário.

---

## 19. Referência

```text
Reference

    citation:

        "Jackson, A. S.; Pollock, M. L.; Ward, A. (1980).

         Generalized equations for predicting body density of women.

         Medicine and Science in Sports and Exercise, 12(3), 175–181."

    doi:

        "10.1249/00005768-198023000-00009"

    url:

        "https://pubmed.ncbi.nlm.nih.gov/7402053/"
```