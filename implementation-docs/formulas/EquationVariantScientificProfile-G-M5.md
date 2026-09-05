# EquationVariantScientificProfile — G-M5

## 1. Identification

```text
identity:

    variantId:
        "G-M5"

    familyId:
        "guedes"

    displayName:
        "Guedes 5 Dobras — Masculino"

    aliasNames:
        [
            "GM5",
            "Guedes 5-Site — Male"
        ]
```

A variante G-M5 pertence ao conjunto de equações específicas de Guedes & Guedes para estimativa da densidade corporal em adultos jovens.

Referência principal:

```text
Guedes, D. P.; Guedes, J. E. R. P. (1991).
Proposição de equações para predição de quantidade de gordura corporal em adultos jovens.
Semina: Ciências Biológicas e da Saúde, 12(2), 61–70.
DOI: 10.5433/1679-0367.1991v12n2p61
```

A publicação descreve equações de regressão para estimar densidade corporal a partir de espessuras de dobras cutâneas em adultos jovens. A amostra total foi composta por 206 sujeitos, sendo 110 homens e 96 mulheres, com idade entre 18 e 30 anos. A densidade corporal foi determinada por pesagem hidrostática. A publicação também informa que as equações foram validadas em uma amostra diferente de 41 sujeitos com idade e características físicas semelhantes.

---

# 2. Mathematical Definition

A definição matemática da variante é:

```text
D =
    1.20436
    - 0.07848 × log10(Σ5)
```

Onde:

```text
D
    = BODY_DENSITY

Σ5
    = soma das cinco dobras cutâneas
```

As cinco dobras são:

```text
- abdominal
- tríceps
- supra-ilíaca
- axilar média
- subescapular
```

A forma da equação é reproduzida em trabalhos posteriores que listam as equações específicas de Guedes & Guedes. Nessas tabelas, a G-M5 aparece como:

```text
Dc = 1.20436 - 0.07848 × log10(DCAB + DCTR + DCSI + DCAM + DCSB)
```

com `r = 0.894` e `EPE = 0.0057 g/ml` para a equação apresentada.

### Forma computacional

```text
sum5 =
    SKINFOLD_ABDOMEN
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_AXILLARY_MID
    + SKINFOLD_SUBSCAPULAR

bodyDensity =
    1.20436
    - 0.07848 × log10(sum5)
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

A variante é uma das equações específicas masculinas de Guedes & Guedes. A amostra masculina do estudo original continha 110 homens.

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

        - range:
            min: null
            max: null

          population:
            "Amostra independente de validação, com idade descrita como semelhante à amostra original"

          source:
            Guedes & Guedes (1991)

    explicitAgeRestriction:
        null
```

A fonte primária disponível informa idade de **18 a 30 anos** para a amostra do estudo. A média de idade masculina específica não foi localizada na informação bibliográfica disponível utilizada para esta ficha, portanto permanece `null`.

A publicação informa que as equações foram validadas em uma amostra diferente de 41 sujeitos com idade e características físicas similares, mas o resumo disponível não fornece uma faixa etária específica para essa amostra nem sua divisão por sexo. Portanto, a faixa da validação permanece explicitamente não documentada.

Algumas tabelas secundárias que reproduzem a série de Guedes identificam as equações como “Guedes 1985” e registram 17–27 anos e n = 110 para a população masculina. Como a publicação primária disponível na UEL informa 18–30 anos para o estudo, esta ficha privilegia a fonte primária e não transforma a discrepância em uma restrição de elegibilidade.

---

# 4. Population Applicability

```text
population:

    originalPopulation:

        description:
            "Homens adultos jovens"

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
            "Amostra masculina pertencente ao estudo de adultos jovens de
             Guedes & Guedes; a população total do estudo foi composta por
             206 sujeitos, 110 homens e 96 mulheres."

        source:
            Guedes & Guedes (1991)

    validationPopulations:

        - description:
            "Amostra independente utilizada para validação das equações"

          country:
            "Brazil"

          region:
            null

          sexCoverage:
            []

          ageCoverage:
            min: null
            max: null

          sampleSize:
            41

          bodyCharacteristicsNotes:
            null

          sampleCharacteristics:
            "Amostra diferente da original, com idade e características físicas
             descritas como similares; a composição por sexo não foi especificada
             no resumo disponível."

          source:
            Guedes & Guedes (1991)
```

O artigo é uma pesquisa brasileira publicada pela Universidade Estadual de Londrina, e o estudo foi construído para adultos jovens. A fonte primária identifica 110 homens e 96 mulheres entre 18 e 30 anos e utiliza pesagem hidrostática.

A ficha não adiciona características corporais, nível de treinamento ou modalidade sem documentação suficiente.

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

Não foi encontrada documentação suficiente para classificar a amostra diretamente como atleta ou não atleta segundo o modelo operacional da plataforma.

---

# 6. Training Level Applicability

```text
trainingLevel:

    supportedLevels:
        []

    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

Não deve ser inferido mapeamento para `SEDENTARY`, `RECREATIONAL`, `TRAINED`, `COMPETITIVE` ou `ELITE`.

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

Não foi identificada, na documentação consultada, uma regra explícita que justifique cadastrar `LOW_BODY_FAT`, `HIGH_BODY_FAT`, `OBESITY`, `HIGH_MUSCLE_MASS`, `EXTREME_SKINFOLD_VALUES` ou `EXTREME_SKINFOLD_SUM` como condição de suporte, alerta ou inelegibilidade.

---

# 9. Validation Evidence

## 9.1 Development Evidence

```text
development:

    studyReference:

        citation:
            "Guedes, D. P.; Guedes, J. E. R. P. (1991).
             Proposição de equações para predição de quantidade de gordura
             corporal em adultos jovens.
             Semina: Ciências Biológicas e da Saúde, 12(2), 61–70."

        doi:
            "10.5433/1679-0367.1991v12n2p61"

    population:
        "Homens adultos jovens, n = 110, 18–30 anos"

    criterionMethod:
        "Densidade corporal determinada pelo método de pesagem hidrostática"

    year:
        1991
```

A publicação primária informa que o método da pesagem hidrostática foi utilizado para determinar a densidade corporal.

---

# 10. Validation

```text
validationStudies:

    - studyReference:

        citation:
            "Guedes, D. P.; Guedes, J. E. R. P. (1991).
             Proposição de equações para predição de quantidade de gordura
             corporal em adultos jovens.
             Semina: Ciências Biológicas e da Saúde, 12(2), 61–70."

        doi:
            "10.5433/1679-0367.1991v12n2p61"

      population:

        description:
            "Amostra independente utilizada para validação das equações"

        country:
            "Brazil"

        region:
            null

        sexCoverage:
            []

        ageCoverage:
            min: null
            max: null

        sampleSize:
            41

        bodyCharacteristicsNotes:
            null

        sampleCharacteristics:
            "Idade e características físicas semelhantes às da amostra original;
             composição por sexo não especificada no resumo disponível."

        source:
            Guedes & Guedes (1991)

      criterionMethod:
          "Pesagem hidrostática"

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
              "A publicação informa validação em amostra diferente de 41 sujeitos,
               mas o resumo disponível não atribui métricas individuais à G-M5."

      limitations:
          "A composição por sexo e as métricas individuais da G-M5 para a amostra
           independente não foram identificadas no material primário disponível."
```

A fonte primária informa a existência da amostra independente de 41 sujeitos, mas não fornece no resumo as métricas individuais da G-M5. Por isso os campos estruturados permanecem `null`, sem distribuição artificial de valores entre variantes.

Para a estatística de desenvolvimento da G-M5, uma publicação posterior que reproduz a tabela das equações específicas de Guedes & Guedes informa:

```text
r   = 0.894
EPE = 0.0057 g/ml
```

Esses valores são tratados como estatísticas de desenvolvimento, não como resultado da validação independente de 41 sujeitos.

---

# 11. Cross-validation

```text
crossValidationStudies:
    []
```

Nesta ficha, a amostra independente de 41 sujeitos é preservada em `validationStudies`, porque a fonte primária disponível descreve a amostra como uma amostra diferente utilizada para validação, sem fornecer informação suficiente para classificá-la como cross-validation no sentido operacional mais específico do schema.

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

        - SKINFOLD_ABDOMEN
        - SKINFOLD_TRICEPS
        - SKINFOLD_SUPRAILIAC
        - SKINFOLD_AXILLARY_MID
        - SKINFOLD_SUBSCAPULAR

    optionalInputs:
        []
```

A equação utiliza somente a soma de cinco dobras cutâneas.

`AGE` não é input matemático da G-M5.

`SEX` não é input matemático da equação; participa da seleção de aplicabilidade porque a variante é masculina.

A separação permanece:

```text
SEX
    → Applicability

AGE
    → Evidence / Population context
    → NÃO é Formula input

5 skinfolds
    → Formula inputs
```

As cinco dobras correspondem às variáveis listadas nas reproduções da equação G-M5: abdominal, tríceps, supra-ilíaca, axilar média e subescapular.

---

# 14. Scientific Restrictions

```text
restrictions:
    []
```

A faixa etária da população de desenvolvimento não é transformada automaticamente em uma `ScientificRestriction` explícita.

Não foi encontrada restrição científica documentada adicional que justifique `INELIGIBLE` ou `WARNING` para outra característica.

---

# 15. Source Conflict

```text
evidence:

    sourceConflict:
        null
```

Não foi identificado conflito material quanto à definição matemática da G-M5.

Existe, porém, uma divergência bibliográfica secundária sobre a associação temporal da série às datas “1985” e “1991” e sobre a faixa etária de 17–27 versus 18–30 anos. Para esta ficha, a publicação primária disponível na UEL (1991) é tratada como a fonte principal para descrição da população e da documentação do estudo. A diferença é preservada em observação, sem alterar a definição matemática da variante.

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

Os elementos essenciais para a operação da variante estão disponíveis:

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

As informações não documentadas permanecem explicitamente como `null` ou listas vazias, conforme o schema.

---

# 17. Ficha consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:
            "G-M5"

        familyId:
            "guedes"

        displayName:
            "Guedes 5 Dobras — Masculino"

        aliasNames:
            [
                "GM5",
                "Guedes 5-Site — Male"
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

                - range:
                    min: null
                    max: null

                  population:
                    "Amostra independente de validação, com idade descrita
                     como semelhante à amostra original"

                  source:
                    Guedes & Guedes (1991)

            explicitAgeRestriction:
                null

        population:

            originalPopulation:

                description:
                    "Homens adultos jovens"

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
                    "Amostra masculina do estudo de Guedes & Guedes."

                source:
                    Guedes & Guedes (1991)

            validationPopulations:

                - description:
                    "Amostra independente utilizada para validação das equações"

                  country:
                    "Brazil"

                  region:
                    null

                  sexCoverage:
                    []

                  ageCoverage:
                    min: null
                    max: null

                  sampleSize:
                    41

                  bodyCharacteristicsNotes:
                    null

                  sampleCharacteristics:
                    "Idade e características físicas similares às da amostra
                     original; composição por sexo não especificada."

                  source:
                    Guedes & Guedes (1991)

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
                "Homens adultos jovens, n = 110, 18–30 anos"

            criterionMethod:
                "Pesagem hidrostática"

            year:
                1991

        validationStudies:

            - studyReference:
                Guedes & Guedes (1991)

              population:
                "Amostra independente de 41 sujeitos"

              criterionMethod:
                "Pesagem hidrostática"

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
                    "Validação reportada para o conjunto das equações; métricas
                     individuais da G-M5 não identificadas no resumo disponível."

              limitations:
                "Composição por sexo e métricas individuais não especificadas
                 no material primário disponível."

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

A ficha permite a seguinte interpretação:

```text
sexo:
    MALE   → compatível
    FEMALE → incompatível

idade:
    população original documentada = 18–30
    média específica = NOT_DOCUMENTED
    não usar a ausência da média como restrição

athlete:
    NOT_DOCUMENTED

trainingLevel:
    NOT_DOCUMENTED

modality:
    NOT_DOCUMENTED

inputs:
    5 skinfolds

output:
    BODY_DENSITY
```

A regra permanece:

```text
NOT_DOCUMENTED
    ≠
INELIGIBLE
```

---

# 19. Reference

```text
Reference

    citation:
        "Guedes, D. P.; Guedes, J. E. R. P. (1991).
         Proposição de equações para predição de quantidade de gordura corporal
         em adultos jovens.
         Semina: Ciências Biológicas e da Saúde, 12(2), 61–70."

    doi:
        "10.5433/1679-0367.1991v12n2p61"

    url:
        "https://doi.org/10.5433/1679-0367.1991v12n2p61"
```

Fonte primária consultada: publicação oficial da Universidade Estadual de Londrina.
