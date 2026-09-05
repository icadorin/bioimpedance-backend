# EquationVariantScientificProfile — G-M3

## 1. Identification

```text
identity:

    variantId:
        "G-M3"

    familyId:
        "guedes"

    displayName:
        "Guedes 3 Dobras — Masculino"

    aliasNames:
        []
```

A variante corresponde à equação de Guedes para estimativa da densidade corporal de homens adultos jovens utilizando três dobras cutâneas.

Referência principal:

```text
Guedes, D. P.; Guedes, J. E. (1991).
Proposição de equações para predição da quantidade de gordura corporal
em adultos jovens.
Semina, 12(2), 61–70.
PMID: 1845307
```

A literatura brasileira também referencia essa equação como "Guedes (1985)" ou "Guedes e Guedes", enquanto o registro bibliográfico localizado para o artigo original está publicado em 1991. Essa diferença de ano de citação deve ser preservada como questão bibliográfica, sem alterar a definição matemática da variante.

---

# 2. Mathematical Definition

A definição matemática da `G-M3` é:

```text
D =
    1.17136
    - 0.06706 × log10(Σ3)
```

Onde:

```text
D
    = BODY_DENSITY

Σ3
    = soma das três dobras cutâneas
```

Para a variante masculina:

```text
Σ3 =
    SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_ABDOMEN
```

### Output

```text
outputType = BODY_DENSITY
```

### Forma computacional

```text
sum3 =
    SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_ABDOMEN

bodyDensity =
    1.17136
    - 0.06706 × log10(sum3)
```

A equação não possui idade como variável matemática.

---

# 3. Applicability

## 3.1 Sexo

```text
sex:

    supportedSexes:
        - MALE
```

A variante masculina é utilizada para homens.

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

O estudo original foi desenvolvido em adultos jovens de **18 a 30 anos**. O registro bibliográfico informa uma amostra total de 206 participantes, sendo 110 homens e 96 mulheres.

A idade média específica da subamostra masculina não foi localizada na fonte consultada e, portanto, permanece `null`.

A faixa de 18–30 anos representa a população estudada no desenvolvimento; não foi convertida automaticamente em uma `explicitAgeRestriction` porque não foi localizada uma afirmação explícita de proibição de uso fora dessa faixa.

---

# 4. Population Applicability

```text
population:

    originalPopulation:

        description:
            "Adultos jovens brasileiros"

        country:
            "Brazil"

        region:
            null

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
            "Participantes adultos jovens do estudo de Guedes & Guedes;
             a amostra total continha 206 indivíduos, sendo 110 homens e
             96 mulheres."

        source:
            Guedes & Guedes (1991)

    validationPopulations:
        []
```

O estudo teve como objetivo derivar equações para estimar densidade corporal a partir de espessuras de dobras cutâneas em adultos jovens. A amostra total foi composta por 206 indivíduos, 110 homens e 96 mulheres, com idade entre 18 e 30 anos. O método hidrostático foi utilizado para determinar a densidade corporal e o percentual de gordura. ([PubMed](https://pubmed.ncbi.nlm.nih.gov/1845307/))

A população é descrita como brasileira pela própria documentação do estudo e por trabalhos posteriores que apontam as equações como aplicáveis à população brasileira.

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

A fonte consultada não fornece classificação suficiente para converter os participantes diretamente em `athlete = true` ou `false` segundo o modelo da plataforma.

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
        "Não documentado em termos de modalidades esportivas específicas."
```

---

# 8. Body Characteristics

```text
bodyCharacteristics:

    rules:
        []
```

Não foi cadastrada nenhuma regra específica de característica corporal, pois não foi encontrada na fonte uma restrição operacional explícita compatível com o vocabulário definido pelo schema.

---

# 9. Validation Evidence

## 9.1 Development Evidence

```text
development:

    studyReference:

        citation:
            "Guedes, D. P.; Guedes, J. E. (1991).
             Proposição de equações para predição da quantidade de gordura
             corporal em adultos jovens. Semina, 12(2), 61–70."

        doi:
            null

    population:
        "Adultos jovens brasileiros, 18–30 anos; 110 homens."

    criterionMethod:
        "Método hidrostático"

    year:
        1991
```

O resumo indexado informa que a densidade corporal foi determinada pelo método hidrostático. As dobras cutâneas foram medidas com adipômetro Harpenden em oito locais: bíceps, tríceps, subescapular, axilar média, supra-ilíaca, abdômen, coxa e panturrilha.

---

# 10. Cross-validation

```text
crossValidationStudies:

    - studyReference:

        citation:
            "Guedes, D. P.; Guedes, J. E. (1991).
             Proposição de equações para predição da quantidade de gordura
             corporal em adultos jovens. Semina, 12(2), 61–70."

        doi:
            null

      population:

        description:
            "Amostra independente com características semelhantes à amostra
             de desenvolvimento"

        country:
            "Brazil"

        region:
            null

        sexCoverage:
            - MALE

        ageCoverage:
            min: 18
            max: 30

        sampleSize:
            null

        bodyCharacteristicsNotes:
            null

        sampleCharacteristics:
            "O estudo informa uma amostra independente de 41 participantes,
             com idade e características físicas semelhantes. A fonte
             consultada não permite atribuir com segurança quantos desses
             41 eram homens."

        source:
            Guedes & Guedes (1991)

      criterionMethod:
          "Método hidrostático"

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
          "A fonte bibliográfica consultada informa cross-validation em
           41 participantes, mas não fornece, no resumo disponível,
           métricas específicas atribuídas individualmente à G-M3 nem a
           composição sexual dessa amostra."
```

O estudo informa que as equações foram cross-validated em um grupo diferente de **41 participantes** com idade e características físicas semelhantes. Como o resumo não permite separar esse grupo em homens e mulheres, não é correto registrar `sampleSize: 41` como se fossem 41 homens.

---

# 11. Validation Studies

```text
validationStudies:
    []
```

A validação independente descrita no artigo foi registrada em `crossValidationStudies`.

---

# 12. External Validation

```text
externalValidationStudies:
    []
```

Nenhum estudo de validação externa independente foi incorporado nesta ficha.

---

# 13. Measurement Requirements

```text
inputs:

    requiredInputs:

        - SKINFOLD_TRICEPS
        - SKINFOLD_SUPRAILIAC
        - SKINFOLD_ABDOMEN

    optionalInputs:
        []
```

A variante não utiliza idade matematicamente.

Assim:

```text
SEX
    → Applicability

SKINFOLD_TRICEPS
SKINFOLD_SUPRAILIAC
SKINFOLD_ABDOMEN
    → Formula inputs
```

Não devem ser adicionados `AGE`, `BODY_WEIGHT` ou `HEIGHT` aos inputs obrigatórios apenas porque são medidas disponíveis no assessment.

---

# 14. Scientific Restrictions

```text
restrictions:
    []
```

Nenhuma restrição científica explícita adicional foi identificada na fonte consultada.

A faixa 18–30 anos continua registrada como característica da população de desenvolvimento, e não como uma proibição automática de uso fora dela.

---

# 15. Source Conflict

```text
evidence:

    sourceConflict:
        null
```

Não foi identificado conflito material na definição matemática da equação nas fontes consultadas.

Existe, porém, uma **questão bibliográfica de datação**: trabalhos brasileiros posteriores frequentemente citam a equação como `Guedes (1985)`, enquanto o registro bibliográfico localizado no PubMed corresponde ao artigo de Guedes & Guedes publicado em 1991. Isso não altera a equação registrada.

Caso a biblioteca exija precisão histórica sobre o ano original da equação, essa questão deve ser resolvida posteriormente por consulta ao trabalho primário em sua forma integral.

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

A variante possui definição matemática, sexo aplicável, população de desenvolvimento, inputs e referência bibliográfica suficientes para o cadastro básico. As informações contextuais não documentadas não impedem `ACTIVE` segundo as regras estabelecidas.

---

# 17. Ficha consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:
            "G-M3"

        familyId:
            "guedes"

        displayName:
            "Guedes 3 Dobras — Masculino"

        aliasNames:
            []

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

                description:
                    "Adultos jovens brasileiros"

                country:
                    "Brazil"

                region:
                    null

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
                    "Amostra total do estudo: 206 participantes,
                     sendo 110 homens e 96 mulheres."

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
                Guedes & Guedes (1991)

            population:
                "Adultos jovens brasileiros, 18–30 anos; 110 homens."

            criterionMethod:
                "Método hidrostático"

            year:
                1991

        validationStudies:
            []

        crossValidationStudies:

            - studyReference:
                Guedes & Guedes (1991)

              population:
                "Amostra independente de 41 participantes; composição sexual
                 específica não determinada na fonte consultada."

              criterionMethod:
                "Método hidrostático"

              metrics:
                correlation: null
                standardError: null
                meanDifference: null
                rmse: null
                otherMetrics: null

              limitations:
                "Dados específicos da G-M3 não identificados no resumo disponível."

        externalValidationStudies:
            []

        sourceConflict:
            null

    inputs:

        requiredInputs:
            - SKINFOLD_TRICEPS
            - SKINFOLD_SUPRAILIAC
            - SKINFOLD_ABDOMEN

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
    MALE → compatível
    FEMALE → incompatível

idade:
    população de desenvolvimento = 18–30
    média = NOT_DOCUMENTED
    não é variável matemática

atleta:
    NOT_DOCUMENTED

trainingLevel:
    NOT_DOCUMENTED

modality:
    NOT_DOCUMENTED

inputs:
    3 skinfolds

output:
    BODY_DENSITY
```

A principal característica desta variante em relação às Jackson & Pollock é que a idade é **característica da população estudada**, mas não variável da equação. Portanto, `AGE` não deve aparecer em `requiredInputs`.

---

# 19. Reference

```text
Reference

    citation:
        "Guedes, D. P.; Guedes, J. E. (1991).
         Proposição de equações para predição da quantidade de gordura
         corporal em adultos jovens. Semina, 12(2), 61–70."

    doi:
        null

    url:
        "https://pubmed.ncbi.nlm.nih.gov/1845307/"
```

## Notes

- A fonte original encontrada no PubMed registra a publicação em 1991.
- Fontes brasileiras posteriores frequentemente citam essa equação como Guedes (1985).
- A equação masculina de três dobras é reproduzida de forma consistente como `1.17136 - 0.06706 × log10(Σ3)`, com `Σ3 = tríceps + supra-ilíaca + abdominal`.
