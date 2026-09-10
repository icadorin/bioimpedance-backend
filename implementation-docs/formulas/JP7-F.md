# EquationVariantScientificProfile — JP7-F

## 1. Identificação

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

A variante `JP7-F` corresponde à equação generalizada de sete dobras para mulheres desenvolvida por Jackson, Pollock e Ward.

Referência principal:

```text
Jackson, A. S.; Pollock, M. L.; Ward, A. (1980).

Generalized equations for predicting body density of women.

Medicine and Science in Sports and Exercise, 12(3), 175–181.

DOI: 10.1249/00005768-198023000-00009

PMID: 7402053
```

A publicação original buscou desenvolver equações generalizadas para mulheres com diferentes idades e níveis de composição corporal. O artigo está indexado no PubMed como publicação de 1980, com os autores A. S. Jackson, M. L. Pollock e A. Ward.

---

## 2. Definição Matemática

A definição matemática da `JP7-F` é:

```text
D =
    1.09700000
    - 0.00046971 × Σ7
    + 0.00000056 × Σ7²
    - 0.00012828 × AGE
```

Onde:

```text
D
    = BODY_DENSITY

Σ7
    = soma das sete dobras cutâneas

AGE
    = idade em anos
```

As sete dobras utilizadas na soma são:

```text
SKINFOLD_PECTORAL
SKINFOLD_AXILLARY_MID
SKINFOLD_TRICEPS
SKINFOLD_SUBSCAPULAR
SKINFOLD_ABDOMEN
SKINFOLD_SUPRAILIAC
SKINFOLD_THIGH
```

### Forma computacional

```text
sum7 =
    SKINFOLD_PECTORAL
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

```text
outputType = BODY_DENSITY
```

A publicação original informa que a modelagem utilizou variáveis antropométricas relacionadas a dobras cutâneas e idade, com o método hidrostático como referência para a densidade corporal. No resumo indexado, as equações femininas foram desenvolvidas a partir de amostras com ampla variação de idade e percentual de gordura.

---

## 3. Aplicabilidade

### 3.1 Sexo

```text
sex:
    supportedSexes:
        - FEMALE
```

A variante foi desenvolvida para mulheres adultas.

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
            "Mulheres adultas da amostra independente de validação"
          source:
            Jackson, Pollock & Ward (1980)
    explicitAgeRestriction:
        null
```

A publicação informa uma faixa etária de 18 a 55 anos para as 249 mulheres da amostra de desenvolvimento, com média de 31,4 ± 10,8 anos. Uma segunda amostra, com 82 mulheres e características semelhantes de idade e percentual de gordura, foi utilizada no estudo para testar as equações.

O valor `developmentSampleMeanAge = 31.4` é puramente descritivo e não deve ser convertido em limite de elegibilidade.

O resumo do artigo recomenda cautela na utilização das equações em mulheres com idade superior a 40 anos. Essa recomendação é tratada nesta ficha como limitação da evidência, e não como uma restrição explícita de elegibilidade.

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
            "Amostra com ampla variação de composição corporal; percentual
             de gordura de aproximadamente 4% a 44%, com média de 24,1% ± 7,2%."
        sampleCharacteristics:
            "Mulheres adultas com variabilidade considerável em estrutura
             corporal, composição corporal e prática de exercícios."
        source:
            Jackson, Pollock & Ward (1980)
    validationPopulations:
        - description:
            "Mulheres adultas da amostra independente de validação"
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
            "Amostra independente do mesmo estudo, utilizada para testar
             as equações desenvolvidas."
          source:
            Jackson, Pollock & Ward (1980)
```

O artigo relata 249 mulheres na amostra de desenvolvimento, com idades de 18 a 55 anos e percentual de gordura de aproximadamente 4% a 44%, com média de 24,1% ± 7,2%. A equação foi posteriormente testada em uma amostra diferente de 82 mulheres com características semelhantes.

A fonte consultada pelo PubMed não informa, no resumo, país ou região específicos da amostra; por isso esses campos permanecem `null` nesta ficha.

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

A publicação informa variabilidade nos hábitos de exercício dos participantes, mas o conteúdo consultado não apresenta uma classificação compatível com os campos booleanos operacionais da plataforma.

Portanto, não deve ser inferido que a variante foi desenvolvida ou validada especificamente em atletas ou não atletas.

---

## 6. Aplicabilidade por Nível de Treinamento

```text
trainingLevel:
    supportedLevels:
        []
    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

Não foi encontrada base suficiente para mapear a população original diretamente para `SEDENTARY`, `RECREATIONAL`, `TRAINED`, `COMPETITIVE` ou `ELITE`.

---

## 7. Aplicabilidade por Modalidade

```text
modality:
    supportedModalities:
        []
    notes:
        "Não documentado em termos de modalidades esportivas específicas."
```

A fonte não fornece uma classificação da amostra por modalidade esportiva específica que possa ser convertida diretamente para o vocabulário da plataforma.

---

## 8. Características Corporais

```text
bodyCharacteristics:
    rules:
        []
```

O estudo contempla ampla variação de percentual de gordura, mas os intervalos observados descrevem a população estudada e não constituem, por si só, regras explícitas de elegibilidade.

Portanto, não foram criadas regras artificiais para `LOW_BODY_FAT`, `HIGH_BODY_FAT`, `OBESITY` ou `EXTREME_SKINFOLD_VALUES`.

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
            "https://doi.org/10.1249/00005768-198023000-00009"
    population:
        "Mulheres adultas, n = 249, 18–55 anos"
    criterionMethod:
        "Densidade corporal determinada pelo método hidrostático"
    year:
        1980
    metrics:
        R:
            null
        R2:
            null
        standardError:
            null
```

O estudo utilizou o método hidrostático para determinar a densidade corporal de referência. O resumo indexado informa que a forma quadrática da soma de três, quatro e sete dobras, combinada com idade e, em algumas equações, circunferência glútea, produziu correlações múltiplas entre 0,842 e 0,867 e erros padrão entre 3,6% e 3,8% de gordura para o conjunto das equações femininas. Como os valores disponíveis no resumo são agregados ao conjunto de modelos e não são atribuídos individualmente à `JP7-F`, `R`, `R2` e `standardError` permanecem `null` nesta ficha.

---

## 10. Validação

```text
validationStudies:
    - studyReference:
        citation:
            "Jackson, A. S.; Pollock, M. L.; Ward, A. (1980).
             Generalized equations for predicting body density of women.
             Medicine and Science in Sports and Exercise, 12(3), 175–181."
        doi:
            "10.1249/00005768-198023000-00009"
        url:
            "https://doi.org/10.1249/00005768-198023000-00009"
      population:
        description:
            "Mulheres adultas da amostra independente de validação"
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
            "Amostra independente do mesmo estudo, utilizada para testar
             as equações desenvolvidas."
        source:
            Jackson, Pollock & Ward (1980)
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
            "Para o conjunto das equações femininas, a publicação informa
             correlações entre 0,815 e 0,820 e erros padrão entre 3,7% e
             4,0% de gordura. O resumo consultado não atribui esses valores
             individualmente à JP7-F."
      limitations:
        "A amostra de 82 mulheres pertence ao mesmo estudo original; a
         publicação a denomina cross-validation. Segundo a regra operacional
         da biblioteca, subamostras do mesmo estudo são classificadas em
         validationStudies. O artigo recomenda cautela na utilização em
         mulheres acima de 40 anos."
```

A publicação descreve explicitamente a amostra de 82 mulheres como usada para `cross-validation`. Entretanto, a regra operacional deste perfil define `validationStudies` para uma subamostra independente do mesmo estudo ou tese. Como a amostra de 82 mulheres pertence à própria investigação de Jackson, Pollock e Ward (1980), ela é classificada aqui em `validationStudies`, preservando em `limitations` a terminologia utilizada na publicação.

As métricas disponíveis no resumo são agregadas ao conjunto das equações femininas. Não foi feita atribuição individual à `JP7-F` sem evidência específica.

---

## 11. Validação Cruzada

```text
crossValidationStudies:
    []
```

Não há registro adicional de validação cruzada de outro estudo ou pesquisador para esta variante.

A amostra de 82 mulheres da publicação de 1980 não permanece nesta seção porque, segundo a regra operacional da biblioteca, uma subamostra independente pertencente ao mesmo estudo deve ser registrada em `validationStudies`.

---

## 12. Validação Externa

```text
externalValidationStudies:
    []
```

Não foi incorporado nesta ficha nenhum estudo de validação externa completamente independente da investigação original de Jackson, Pollock e Ward.

---

## 13. Requisitos de Medição

```text
inputs:
    requiredInputs:
        - AGE
        - SKINFOLD_PECTORAL
        - SKINFOLD_AXILLARY_MID
        - SKINFOLD_TRICEPS
        - SKINFOLD_SUBSCAPULAR
        - SKINFOLD_ABDOMEN
        - SKINFOLD_SUPRAILIAC
        - SKINFOLD_THIGH
    optionalInputs:
        []
```

`SEX` não é variável matemática da equação e permanece exclusivamente em `applicability.sex`.

Os inputs matemáticos são:

```text
AGE
SKINFOLD_PECTORAL
SKINFOLD_AXILLARY_MID
SKINFOLD_TRICEPS
SKINFOLD_SUBSCAPULAR
SKINFOLD_ABDOMEN
SKINFOLD_SUPRAILIAC
SKINFOLD_THIGH
```

A decisão de normalização da biblioteca determina `SKINFOLD_PECTORAL` como identificador canônico para os protocolos de Jackson & Pollock abrangidos pela decisão. `SKINFOLD_CHEST` não deve aparecer como input independente nesta ficha.

---

## 14. Restrições Científicas

```text
restrictions:
    []
```

Não foi cadastrada uma restrição científica explícita adicional.

A recomendação do artigo para que se tenha cautela com mulheres acima de 40 anos é preservada como limitação da evidência em `validationStudies.limitations`, não como regra automática de inelegibilidade.

A faixa de 18–55 anos representa a população documentada do estudo e não é convertida em uma restrição científica adicional separada sem evidência de que a fonte a formule dessa maneira.

---

## 15. Conflito de Fonte

```text
evidence:
    sourceConflict:
        null
```

Não foi identificado conflito material entre a publicação original consultada e a definição matemática utilizada para `JP7-F`.

A terminologia de `cross-validation` empregada pelo artigo para a amostra de 82 mulheres é tratada como uma diferença de classificação metodológica entre a fonte e a regra operacional da biblioteca, e não como conflito de fonte.

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

Checklist dos campos essenciais:

```text
identity                     ✓
mathematicalDefinition       ✓
supportedSexes               ✓
originalDevelopmentAgeRange  ✓
originalPopulation           ✓
requiredInputs               ✓
definitionReference           ✓
sourceConflict               null
```

A variante possui os elementos essenciais necessários para `status: ACTIVE`. Campos sem documentação suficiente, como atleta, nível de treinamento, modalidade e validação externa, permanecem em `null` ou `[]` sem bloquear o estado ativo.

---

## 17. Ficha Consolidada

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
    mathematicalDefinition:
        formula:
            "D = 1.09700000 - 0.00046971 × Σ7 + 0.00000056 × Σ7² - 0.00012828 × AGE"
        outputType:
            BODY_DENSITY
        sum7Inputs:
            - SKINFOLD_PECTORAL
            - SKINFOLD_AXILLARY_MID
            - SKINFOLD_TRICEPS
            - SKINFOLD_SUBSCAPULAR
            - SKINFOLD_ABDOMEN
            - SKINFOLD_SUPRAILIAC
            - SKINFOLD_THIGH
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
                    "Mulheres adultas da amostra independente de validação"
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
                     média de 24,1% ± 7,2%."
                sampleCharacteristics:
                    "Mulheres adultas com variabilidade considerável em
                     estrutura corporal, composição corporal e prática de exercícios."
                source:
                    Jackson, Pollock & Ward (1980)
            validationPopulations:
                - description:
                    "Mulheres adultas da amostra independente de validação"
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
                    "Amostra independente do mesmo estudo, utilizada para testar
                     as equações desenvolvidas."
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
                "Não documentado segundo a escala operacional da plataforma."
        modality:
            supportedModalities:
                []
            notes:
                "Não documentado em termos de modalidades esportivas específicas."
        bodyCharacteristics:
            rules:
                []
    evidence:
        development:
            studyReference:
                citation:
                    "Jackson, A. S.; Pollock, M. L.; Ward, A. (1980).
                     Generalized equations for predicting body density of women.
                     Medicine and Science in Sports and Exercise, 12(3), 175–181."
                doi:
                    "10.1249/00005768-198023000-00009"
                url:
                    "https://doi.org/10.1249/00005768-198023000-00009"
            population:
                "Mulheres adultas, n = 249, 18–55 anos"
            criterionMethod:
                "Densidade corporal determinada pelo método hidrostático"
            year:
                1980
            metrics:
                R:
                    null
                R2:
                    null
                standardError:
                    null
        validationStudies:
            - studyReference:
                citation:
                    "Jackson, A. S.; Pollock, M. L.; Ward, A. (1980).
                     Generalized equations for predicting body density of women.
                     Medicine and Science in Sports and Exercise, 12(3), 175–181."
                doi:
                    "10.1249/00005768-198023000-00009"
                url:
                    "https://doi.org/10.1249/00005768-198023000-00009"
              population:
                "Mulheres adultas, n = 82, 18–55 anos"
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
                    "Para o conjunto das equações femininas: r = 0,815–0,820;
                     SE = 3,7–4,0% de gordura. Não atribuídos individualmente à JP7-F."
              limitations:
                "Amostra independente do mesmo estudo, chamada de cross-validation
                 na publicação original; classificada aqui como validationStudies
                 segundo a regra operacional da biblioteca. Cautela em mulheres >40 anos."
        crossValidationStudies:
            []
        externalValidationStudies:
            []
        sourceConflict:
            null
    inputs:
        requiredInputs:
            - AGE
            - SKINFOLD_PECTORAL
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

A ficha consolidada reúne a definição matemática, aplicabilidade, população, evidências, requisitos de medição, restrições e ciclo de vida registrados nas seções anteriores. Os estados derivados de runtime não fazem parte do objeto consolidado.

---

## 18. Interpretação para o SuggestionEngine

```text
sex:
    FEMALE → compatível
    MALE → incompatível

age:
    população de desenvolvimento = 18–55
    média = 31.4
    média NÃO é limite

>40:
    existe cautela documentada
    NÃO existe restrição explícita de inelegibilidade

athlete:
    NOT_DOCUMENTED

trainingLevel:
    NOT_DOCUMENTED

modality:
    NOT_DOCUMENTED

bodyCharacteristics:
    nenhuma regra explícita

inputs:
    AGE + 7 skinfolds

output:
    BODY_DENSITY
```

O `SuggestionEngine` deve utilizar os dados científicos documentados para derivar os estados de aplicabilidade e prontidão em runtime, sem armazenar na ficha campos como `ageMatch`, `populationMatch`, `sexMatch`, `contextMatch`, `evidenceCoverage`, `READY`, `WARNING` ou `INELIGIBLE`.

A média de idade de 31,4 anos não deve ser transformada em limite de elegibilidade. A cautela recomendada para mulheres acima de 40 anos deve ser interpretada como limitação da evidência, não como bloqueio automático.

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
        "https://doi.org/10.1249/00005768-198023000-00009"
```

A publicação original é identificada no PubMed pelo PMID `7402053`, com DOI `10.1249/00005768-198023000-00009`.
