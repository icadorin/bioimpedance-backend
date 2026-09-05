# EquationVariantScientificProfile — JP7-M

## 1. Identification

```text
identity:

    variantId:
        "JP7-M"

    familyId:
        "jackson-pollock"

    displayName:
        "Jackson & Pollock 7 Dobras — Masculino"

    aliasNames:
        []
```

A variante `JP7-M` corresponde à equação generalizada de Jackson & Pollock para previsão de densidade corporal em homens adultos utilizando a soma de sete dobras cutâneas e idade. A publicação original é:

```text
Jackson, A. S.; Pollock, M. L. (1978).
Generalized equations for predicting body density of men.
British Journal of Nutrition, 40(3), 497–504.
DOI: 10.1079/BJN19780152
```

A publicação identifica os autores como A. S. Jackson e M. L. Pollock e descreve o desenvolvimento de equações generalizadas para homens variando em idade e composição corporal.

---

# 2. Mathematical Definition

A definição matemática da variante é:

```text
D =
    1.11200000
    - 0.00043499 × Σ7
    + 0.00000055 × Σ7²
    - 0.00028826 × idade
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

As sete dobras da `Σ7` são:

```text
- peito
- axilar
- tríceps
- subescapular
- abdômen
- supra-ilíaca
- coxa
```

A tabela original apresenta essa equação como a equação nº 1 para homens adultos de 18–61 anos.

### Output

```text
outputType = BODY_DENSITY
```

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
    1.11200000
    - 0.00043499 × sum7
    + 0.00000055 × sum7²
    - 0.00028826 × AGE
```

A relação entre soma das dobras e densidade corporal foi modelada quadraticamente no estudo.

---

# 3. Applicability

## 3.1 Sex

```text
sex:

    supportedSexes:
        - MALE
```

A variante foi desenvolvida para homens adultos.

A publicação original é especificamente intitulada:

```text
Generalized equations for predicting body density of men
```

e a amostra do estudo foi composta por homens adultos.

---

## 3.2 Age

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
            max: 59

          population:
            "Homens adultos da amostra de cross-validation"

          source:
            Jackson & Pollock (1978)

    explicitAgeRestriction:
        null
```

A amostra utilizada para desenvolvimento continha homens de 18 a 61 anos, com idade média de 32,6 anos.

A amostra independente de cross-validation continha homens de 18 a 59 anos, com idade média de 33,3 anos.

O valor:

```text
developmentSampleMeanAge = 32.6
```

é descritivo e **não constitui limite de elegibilidade**.

Não foi identificada, na fonte original consultada, uma restrição explícita adicional que deva ser armazenada em:

```text
explicitAgeRestriction
```

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
            "A amostra apresentou ampla variação de composição corporal; a
             amostra de desenvolvimento apresentou percentual de gordura
             médio de 17,7%, com faixa de 1% a 33%."

        sampleCharacteristics:
            "Homens com grande variação na estrutura corporal, composição
             corporal e hábitos de exercício; participantes avaliados nos
             laboratórios da Wake Forest University, Winston-Salem, North
             Carolina, e Institute for Aerobics Research, Dallas, Texas."

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
            "Percentual de gordura médio de 18,7%, com faixa de 1% a 33%."

          sampleCharacteristics:
            "Amostra independente utilizada para cross-validation da
             equação desenvolvida no grupo de 308 homens."

          source:
            Jackson & Pollock (1978)
```

A publicação informa que 403 homens adultos participaram do estudo, sendo 308 utilizados para derivar as equações e 95 utilizados para cross-validation. Os indivíduos apresentavam variação considerável de estrutura corporal, composição corporal e hábitos de exercício.

A tabela original registra, para a amostra de desenvolvimento, idade de 18–61 anos e percentual de gordura de 1–33%, e para a amostra de cross-validation, idade de 18–59 anos e percentual de gordura de 1–33%.

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

A publicação informa que os participantes variavam em seus hábitos de exercício, mas a fonte consultada não fornece uma classificação compatível com a estrutura operacional:

```text
athlete = true / false
```

Portanto, não deve ser inferido que a equação foi:

```text
desenvolvida em atletas
```

nem:

```text
desenvolvida em não atletas
```

O estado correto para esse campo é:

```text
null
```

---

# 6. Training Level Applicability

```text
trainingLevel:

    supportedLevels:
        []

    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

A publicação menciona variação nos hábitos de exercício dos participantes, mas não apresenta a amostra nas categorias:

```text
SEDENTARY
RECREATIONAL
TRAINED
COMPETITIVE
ELITE
```

Portanto, não deve ser criada uma correspondência artificial entre os participantes do estudo e essas categorias.

---

# 7. Modality Applicability

```text
modality:

    supportedModalities:
        []

    notes:
        "Não documentado em termos de modalidades esportivas específicas."
```

A fonte descreve hábitos de exercício, mas não fornece uma classificação por modalidade esportiva que possa ser convertida diretamente para o vocabulário da plataforma.

---

# 8. Body Characteristics

```text
bodyCharacteristics:

    rules:
        []
```

A fonte apresenta ampla variação de composição corporal, mas não estabelece uma regra explícita do tipo:

```text
LOW_BODY_FAT
HIGH_BODY_FAT
OBESITY
HIGH_MUSCLE_MASS
EXTREME_SKINFOLD_VALUES
EXTREME_SKINFOLD_SUM
```

Portanto, não deve ser criada uma `BodyCharacteristicRule` apenas a partir das faixas observadas na amostra.

Os intervalos observados são preservados como características descritivas da população.

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

O estudo utilizou o método hidrostático para determinar a densidade corporal de referência. O procedimento de pesagem submersa foi repetido até obtenção de três leituras semelhantes.

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
            "Percentual de gordura médio de 18,7%, faixa de 1% a 33%."

        sampleCharacteristics:
            "Amostra independente utilizada para verificar a capacidade
             preditiva das equações derivadas na amostra de 308 homens."

        source:
            Jackson & Pollock (1978)

      criterionMethod:
          "Densidade corporal determinada laboratorialmente pelo método
           hidrostático"

      metrics:

          correlation:
              0.915

          standardError:
              0.0078

          meanDifference:
              null

          rmse:
              null

          otherMetrics:
              null

      limitations:
          "A amostra de cross-validation teve n = 95; não constitui
           validação externa independente do estudo original."
```

Para a equação nº 1, correspondente à `JP7-M`, a tabela de cross-validation apresenta:

```text
r = 0.915
SE = 0.0078 g/ml
```

A análise também foi realizada por categorias de idade e percentual de gordura.

---

# 11. Validation Studies

```text
validationStudies:
    []
```

O estudo original utiliza uma divisão entre:

```text
development / validation sample
```

e:

```text
cross-validation sample
```

Para o schema da plataforma, a amostra independente de 95 homens é registrada em:

```text
crossValidationStudies
```

e não deve ser artificialmente convertida em:

```text
externalValidationStudies
```

---

# 12. External Validation

```text
externalValidationStudies:
    []
```

Nenhum estudo de validação externa foi incorporado nesta ficha nesta etapa.

Não inferir validação externa a partir de estudos posteriores que simplesmente utilizaram a equação.

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

### Regra importante

`SEX` **não é input matemático da fórmula**.

Ele participa da seleção/aplicabilidade da variante porque esta variante é especificamente masculina.

Portanto:

```text
SEX
    → Applicability

AGE
    → Formula input

7 skinfolds
    → Formula inputs
```

Não devem ser confundidos.

A fonte original informa que foram medidas as sete dobras:

```text
chest
axilla
triceps
subscapula
abdomen
supra-iliac
thigh
```

e que a equação nº 1 utiliza a soma dessas sete dobras juntamente com idade.

---

# 14. Scientific Restrictions

```text
restrictions:
    []
```

Não foi cadastrada nesta ficha uma restrição científica adicional além da aplicabilidade documentada por sexo e da população/idade original.

A faixa:

```text
18–61 anos
```

é registrada como faixa da população de desenvolvimento, não como uma restrição explícita adicional.

---

# 15. Source Conflict

```text
evidence:

    sourceConflict:
        null
```

Nesta etapa não foi identificado conflito material entre a fonte original consultada e a definição matemática utilizada pela especificação técnica da plataforma para:

```text
JP7-M
```

A equação encontrada na fonte original corresponde à definida na biblioteca:

```text
D =
1.11200000
- 0.00043499 × Σ7
+ 0.00000055 × Σ7²
- 0.00028826 × idade
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

A variante possui:

```text
identity
mathematicalDefinition
supportedSexes
originalDevelopmentAgeRange
originalPopulation
requiredInputs
reference
```

e a definição matemática está sustentada pela publicação original.

Portanto, não existe nesta ficha um `TBD` essencial que impeça a definição de:

```text
ACTIVE
```

---

# 17. Ficha consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:
            "JP7-M"

        familyId:
            "jackson-pollock"

        displayName:
            "Jackson & Pollock 7 Dobras — Masculino"

        aliasNames:
            []

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
                description: "Homens adultos"
                country: "USA"
                region: "North Carolina e Texas"
                sexCoverage:
                    - MALE
                ageCoverage:
                    min: 18
                    max: 61
                sampleSize: 308
                bodyCharacteristicsNotes:
                    "Percentual de gordura médio de 17,7%, faixa de 1% a 33%."
                sampleCharacteristics:
                    "Grande variação de estrutura corporal, composição corporal
                     e hábitos de exercício."
                source:
                    Jackson & Pollock (1978)

            validationPopulations:

                - description:
                    "Homens adultos da amostra independente de cross-validation"
                  country: "USA"
                  region: "North Carolina e Texas"
                  sexCoverage:
                      - MALE
                  ageCoverage:
                      min: 18
                      max: 59
                  sampleSize: 95
                  bodyCharacteristicsNotes:
                      "Percentual de gordura médio de 18,7%, faixa de 1% a 33%."
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
                "Homens adultos, n = 95, 18–59 anos"

              criterionMethod:
                "Densidade corporal determinada pelo método hidrostático"

              metrics:
                correlation: 0.915
                standardError: 0.0078
                meanDifference: null
                rmse: null
                otherMetrics: null

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

# 18. Observações científicas para o motor

A ficha permite que o motor faça as seguintes avaliações sem hardcode específico de `JP7-M`:

```text
sexo:
    MALE → compatível
    FEMALE → incompatível

idade:
    comparar com a população documentada
    sem usar 32,6 como limite

atleta:
    NOT_DOCUMENTED

trainingLevel:
    NOT_DOCUMENTED

modalidade:
    NOT_DOCUMENTED

inputs:
    requer AGE + 7 skinfolds

output:
    BODY_DENSITY
```

O motor não deve transformar:

```text
validatedInAthletes = null
```

em:

```text
INELIGIBLE
```

nem transformar:

```text
developmentSampleMeanAge = 32.6
```

em:

```text
idade permitida = 32.6
```

A informação é interpretada de acordo com o significado definido pelo schema.

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

A publicação original está indexada pelo PubMed com PMID 718832 e DOI `10.1079/bjn19780152`.