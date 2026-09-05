# EquationVariantScientificProfile — JP3-M

## 1. Identification

```text
identity:

    variantId:
        "JP3-M"

    familyId:
        "jackson-pollock"

    displayName:
        "Jackson & Pollock 3 Dobras — Masculino"

    aliasNames:
        [
            "Jackson-Pollock 3-Site — Male",
            "JP3 Male",
            "3-Site Jackson-Pollock"
        ]
```

A variante `JP3-M` corresponde à equação generalizada de três dobras para homens desenvolvida por Jackson & Pollock em 1978.

Referência principal:

```text
Jackson, A. S.; Pollock, M. L. (1978).
Generalized equations for predicting body density of men.
British Journal of Nutrition, 40(3), 497–504.
DOI: 10.1079/BJN19780152
PMID: 718832
```

---

# 2. Mathematical Definition

A definição matemática da `JP3-M` é:

```text
D =
    1.10938000
    - 0.00082670 × Σ3
    + 0.00000160 × Σ3²
    - 0.00025740 × idade
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

As três dobras são:

```text
- peito / chest
- abdômen / abdominal
- coxa / thigh
```

A literatura que reproduz a equação confirma essa combinação de três sítios e os coeficientes acima.

### Forma computacional

```text
sum3 =
    SKINFOLD_CHEST
    + SKINFOLD_ABDOMEN
    + SKINFOLD_THIGH

bodyDensity =
    1.10938000
    - 0.00082670 × sum3
    + 0.00000160 × sum3²
    - 0.00025740 × AGE
```

### Output

```text
outputType = BODY_DENSITY
```

A conversão posterior de densidade corporal para percentual de gordura permanece uma etapa separada da equação de densidade.

---

# 3. Applicability

## 3.1 Sexo

```text
sex:

    supportedSexes:
        - MALE
```

A variante pertence ao conjunto de equações generalizadas desenvolvido para homens adultos. A publicação original descreve uma amostra de 308 homens para o desenvolvimento das equações e uma segunda amostra de 95 homens para cross-validation.

---

## 3.2 Idade

```text
age:

    originalDevelopmentAgeRange:
        min: 18
        max: 61

    developmentSampleMeanAge:
        32.6

    validatedAgeRanges:

        - range:
            min: 18
            max: 61

          population:
            "Homens adultos da amostra independente de cross-validation"

          source:
            Jackson & Pollock (1978)

    explicitAgeRestriction:
        null
```

A publicação original informa que os homens das amostras tinham entre 18 e 61 anos. A amostra de desenvolvimento tinha idade média de 32,6 ± 10,8 anos. A tabela original registra a faixa de 18–61 anos na amostra de desenvolvimento; a documentação disponível também reporta a faixa de 18–59 anos para a amostra independente em uma transcrição da tabela original.

Para a ficha, a idade de desenvolvimento é mantida como:

```text
18–61
```

e a média de 32,6 permanece exclusivamente descritiva.

Não foi identificada uma restrição etária explícita adicional na fonte original que deva ser transformada em `explicitAgeRestriction`.

---

# 4. Population Applicability

```text
population:

    originalPopulation:

        description:
            "Homens adultos"

        country:
            "USA"

        region:
            "North Carolina e Texas"

        sexCoverage:
            - MALE

        ageCoverage:
            min: 18
            max: 61

        sampleSize:
            308

        bodyCharacteristicsNotes:
            "Grande variação de composição corporal; percentual de gordura
             médio de 17,7%, com faixa de aproximadamente 1% a 33%."

        sampleCharacteristics:
            "Homens com ampla variação de estrutura corporal, composição
             corporal e hábitos de exercício; participantes avaliados na
             Wake Forest University e no Institute for Aerobics Research."

        source:
            Jackson & Pollock (1978)

    validationPopulations:

        - description:
            "Homens adultos da amostra independente de cross-validation"

          country:
            "USA"

          region:
            "North Carolina e Texas"

          sexCoverage:
            - MALE

          ageCoverage:
            min: 18
            max: 59

          sampleSize:
            95

          bodyCharacteristicsNotes:
            "Percentual de gordura médio de 18,7%, faixa de aproximadamente
             1% a 33%."

          sampleCharacteristics:
            "Amostra independente utilizada para cross-validation da
             equação desenvolvida na amostra de 308 homens."

          source:
            Jackson & Pollock (1978)
```

A fonte original descreve 308 homens no grupo de desenvolvimento e 95 na amostra de cross-validation. A tabela de características físicas apresenta, para o grupo de desenvolvimento, idade média de 32,6 anos, percentual de gordura médio de 17,7% e soma média de três dobras de 59,4 mm. Para a amostra de cross-validation, apresenta idade média de 33,3 anos, percentual de gordura médio de 18,7% e soma média de três dobras de 62,4 mm.

A identificação geográfica da instituição é suficiente para registrar EUA, com Wake Forest University em Winston-Salem, North Carolina, e Institute of Aerobics Research em Dallas, Texas.

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

A fonte menciona variação nos hábitos de exercício, mas isso não é suficiente para classificar a amostra diretamente como atleta ou não atleta segundo a semântica operacional da plataforma.

Portanto, não há base para substituir `null` por `true` ou `false`.

---

# 6. Training Level Applicability

```text
trainingLevel:

    supportedLevels:
        []

    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

A publicação não fornece uma categorização compatível com:

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

As faixas de gordura observadas na amostra são características descritivas da população estudada e não devem ser transformadas automaticamente em restrições de uso da equação.

---

# 9. Validation Evidence

## 9.1 Development Evidence

```text
development:

    studyReference:

        citation:
            "Jackson, A. S.; Pollock, M. L. (1978).
             Generalized equations for predicting body density of men.
             British Journal of Nutrition, 40(3), 497–504."

        doi:
            "10.1079/BJN19780152"

    population:
        "Homens adultos, n = 308, 18–61 anos"

    criterionMethod:
        "Densidade corporal determinada pelo método hidrostático"

    year:
        1978
```

O artigo original informa que as equações foram derivadas a partir de 308 homens e que a densidade corporal foi determinada em laboratório. A publicação descreve a abordagem de regressão múltipla utilizando idade e a soma de dobras, inclusive sua forma quadrática.

---

# 10. Cross-validation

```text
crossValidationStudies:

    - studyReference:

        citation:
            "Jackson, A. S.; Pollock, M. L. (1978).
             Generalized equations for predicting body density of men.
             British Journal of Nutrition, 40(3), 497–504."

        doi:
            "10.1079/BJN19780152"

      population:

        description:
            "Homens adultos da amostra independente de cross-validation"

        country:
            "USA"

        region:
            "North Carolina e Texas"

        sexCoverage:
            - MALE

        ageCoverage:
            min: 18
            max: 59

        sampleSize:
            95

        bodyCharacteristicsNotes:
            "Percentual de gordura médio de 18,7%, com ampla variação."

        sampleCharacteristics:
            "Amostra independente utilizada para cross-validation."

        source:
            Jackson & Pollock (1978)

      criterionMethod:
          "Densidade corporal determinada por método hidrostático"

      metrics:

          correlation:
              0.90

          standardError:
              0.0077

          meanDifference:
              null

          rmse:
              null

          otherMetrics:
              null

      limitations:
          "A validação é cross-validation interna do estudo original;
           não constitui validação externa independente."
```

A literatura que reproduz a tabela da equação `JP3` reporta aproximadamente `r = 0.90` e `SEE = 0.0077 g/ml`.

A publicação original informa, de maneira agregada, que as equações foram cross-validated na segunda amostra de 95 homens, com correlações superiores a 0,90 e erros padrão de aproximadamente 0,0077 g/ml.

Como a tabela secundária atribui especificamente `0.90` e `0.0077` à equação de três dobras, esses valores podem ser mantidos nos campos estruturados.

---

# 11. Validation Studies

```text
validationStudies:
    []
```

A validação da equação dentro do estudo original é tratada como `crossValidationStudies`, preservando a separação conceitual do schema.

---

# 12. External Validation

```text
externalValidationStudies:
    []
```

Nenhum estudo externo foi incorporado nesta ficha.

---

# 13. Measurement Requirements

```text
inputs:

    requiredInputs:

        - AGE
        - SKINFOLD_CHEST
        - SKINFOLD_ABDOMEN
        - SKINFOLD_THIGH

    optionalInputs:
        []
```

A fonte e referências posteriores confirmam que a soma de três dobras da variante masculina é composta por:

```text
CHEST
ABDOMEN
THIGH
```

Como nas variantes anteriores:

```text
SEX
    → Applicability

AGE
    → Formula input

3 skinfolds
    → Formula inputs
```

`SEX` não é incluído em `requiredInputs` porque não aparece como variável matemática da equação.

---

# 14. Scientific Restrictions

```text
restrictions:
    []
```

A faixa etária de desenvolvimento não é convertida automaticamente em uma `ScientificRestriction`. A fonte original caracteriza a equação para homens adultos e reporta a faixa estudada, mas não estabelece uma proibição adicional que precise ser representada aqui.

---

# 15. Source Conflict

```text
evidence:

    sourceConflict:
        null
```

A definição matemática da `JP3-M` é consistentemente reproduzida nas fontes consultadas:

```text
D =
    1.10938
    - 0.0008267 × Σ3
    + 0.0000016 × Σ3²
    - 0.0002574 × age
```

Não foi identificado conflito científico material nesta etapa.

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

Informações contextuais não documentadas permanecem como `null` ou listas vazias conforme o schema e não impedem `ACTIVE` por si só.

---

# 17. Ficha consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:
            "JP3-M"

        familyId:
            "jackson-pollock"

        displayName:
            "Jackson & Pollock 3 Dobras — Masculino"

        aliasNames:
            [
                "Jackson-Pollock 3-Site — Male",
                "JP3 Male",
                "3-Site Jackson-Pollock"
            ]

    applicability:

        sex:

            supportedSexes:
                - MALE

        age:

            originalDevelopmentAgeRange:
                min: 18
                max: 61

            developmentSampleMeanAge:
                32.6

            validatedAgeRanges:

                - range:
                    min: 18
                    max: 59

                  population:
                    "Homens adultos da amostra independente de cross-validation"

                  source:
                    Jackson & Pollock (1978)

            explicitAgeRestriction:
                null

        population:

            originalPopulation:

                description:
                    "Homens adultos"

                country:
                    "USA"

                region:
                    "North Carolina e Texas"

                sexCoverage:
                    - MALE

                ageCoverage:
                    min: 18
                    max: 61

                sampleSize:
                    308

                bodyCharacteristicsNotes:
                    "Percentual de gordura médio de 17,7%, faixa aproximada
                     de 1% a 33%."

                sampleCharacteristics:
                    "Grande variação de estrutura corporal, composição corporal
                     e hábitos de exercício."

                source:
                    Jackson & Pollock (1978)

            validationPopulations:

                - description:
                    "Homens adultos da amostra independente de cross-validation"

                  country:
                    "USA"

                  region:
                    "North Carolina e Texas"

                  sexCoverage:
                    - MALE

                  ageCoverage:
                    min: 18
                    max: 59

                  sampleSize:
                    95

                  bodyCharacteristicsNotes:
                    "Percentual de gordura médio de 18,7%."

                  sampleCharacteristics:
                    "Amostra independente utilizada para cross-validation."

                  source:
                    Jackson & Pollock (1978)

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
                Jackson & Pollock (1978)

            population:
                "Homens adultos, n = 308, 18–61 anos"

            criterionMethod:
                "Densidade corporal determinada pelo método hidrostático"

            year:
                1978

        validationStudies:
            []

        crossValidationStudies:

            - studyReference:
                Jackson & Pollock (1978)

              population:
                "Homens adultos, n = 95, aproximadamente 18–59 anos"

              criterionMethod:
                "Densidade corporal determinada pelo método hidrostático"

              metrics:

                correlation:
                    0.90

                standardError:
                    0.0077

                meanDifference:
                    null

                rmse:
                    null

                otherMetrics:
                    null

              limitations:
                "Cross-validation interna do estudo original."

        externalValidationStudies:
            []

        sourceConflict:
            null

    inputs:

        requiredInputs:
            - AGE
            - SKINFOLD_CHEST
            - SKINFOLD_ABDOMEN
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
    MALE   → compatível
    FEMALE → incompatível

idade:
    população de desenvolvimento = 18–61
    média = 32.6
    média NÃO é limite

atleta:
    NOT_DOCUMENTED

trainingLevel:
    NOT_DOCUMENTED

modality:
    NOT_DOCUMENTED

inputs:
    AGE + CHEST + ABDOMEN + THIGH

output:
    BODY_DENSITY
```

A amostra original apresenta a equação como parte do conjunto de equações generalizadas para homens adultos e reporta validade para homens variando em idade e gordura corporal.

---

# 19. Referência principal

```text
Reference

    citation:
        "Jackson, A. S.; Pollock, M. L. (1978).
         Generalized equations for predicting body density of men.
         British Journal of Nutrition, 40(3), 497–504."

    doi:
        "10.1079/BJN19780152"

    url:
        "https://doi.org/10.1079/BJN19780152"
```

A referência bibliográfica e o DOI são confirmados pelas bases bibliográficas consultadas.
