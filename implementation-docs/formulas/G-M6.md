# EquationVariantScientificProfile — G-M6

## 1. Identificação

```text
identity:

    variantId:

        "G-M6"

    familyId:

        "guedes"

    displayName:

        "Guedes 6 Dobras — Masculino"

    aliasNames:

        [

            "Guedes GM6",

            "GM6"

        ]
```

A variante `G-M6` corresponde à sexta equação específica masculina do conjunto de equações de Guedes e Guedes para estimativa da densidade corporal em adultos jovens.

Referência principal:

```text
Guedes, D. P.; Guedes, J. E. R. P. (1991).

Proposição de equações para predição da quantidade de gordura corporal
em adultos jovens.

Semina, 12(2), 61–70.
```

A publicação de 1991 descreve a derivação de equações de regressão para estimar a densidade corporal a partir de espessuras de dobras cutâneas em adultos jovens. A amostra foi composta por 206 participantes, sendo 110 homens e 96 mulheres, com idade entre 18 e 30 anos. A densidade corporal de referência foi determinada por pesagem hidrostática.

---

# 2. Definição Matemática

A definição matemática da `G-M6` é:

```text
D =

    1.21546

    - 0.08119 × log10(Σ6)
```

Onde:

```text
D

    = BODY_DENSITY

Σ6

    = soma das seis dobras cutâneas utilizadas pela G-M6
```

As seis dobras utilizadas são:

```text
- abdômen
- tríceps
- supra-ilíaca
- axilar média
- subescapular
- coxa
```

A tabela científica que reproduz as equações específicas masculinas de Guedes confirma a forma matemática, os seis sítios de medida e os coeficientes da G-M6.

### Forma computacional

```text
sum6 =

    SKINFOLD_ABDOMEN

    + SKINFOLD_TRICEPS

    + SKINFOLD_SUPRAILIAC

    + SKINFOLD_AXILLARY_MID

    + SKINFOLD_SUBSCAPULAR

    + SKINFOLD_THIGH

bodyDensity =

    1.21546

    - 0.08119 × log10(sum6)
```

### Output

```text
outputType = BODY_DENSITY
```

A conversão posterior de `BODY_DENSITY` para percentual de gordura permanece separada da definição matemática desta variante.

---

# 3. Aplicabilidade

## 3.1 Sexo

```text
sex:

    supportedSexes:

        - MALE
```

A G-M6 pertence ao conjunto masculino das equações específicas de Guedes e Guedes.

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

            min: 18

            max: 30

          population:

            "Subamostra independente do mesmo estudo original"

          source:

            "Guedes & Guedes (1991)"

    explicitAgeRestriction:

        null
```

A publicação original informa uma população de adultos jovens com idade entre 18 e 30 anos. A idade não participa matematicamente da G-M6 e, portanto, não aparece em `requiredInputs`.

`developmentSampleMeanAge` permanece `null` porque a média etária não foi confirmada na fonte primária disponível para esta ficha.

A faixa de 18–30 anos descreve a população estudada e não é convertida automaticamente em uma restrição científica explícita.

---

# 4. Aplicabilidade Populacional

```text
population:

    originalPopulation:

        description:

            "Adultos jovens do sexo masculino"

        country:

            "Brazil"

        region:

            "Santa Maria, Rio Grande do Sul"

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

            "Adultos jovens participantes do estudo original,
             sendo 110 homens e 96 mulheres no conjunto total."

        source:

            "Guedes & Guedes (1991)"

    validationPopulations:

        - description:

            "Subamostra independente utilizada para testar as equações
             dentro do mesmo estudo original"

          country:

            "Brazil"

          region:

            "Santa Maria, Rio Grande do Sul"

          sexCoverage:

            null

          ageCoverage:

            min: 18

            max: 30

          sampleSize:

            41

          bodyCharacteristicsNotes:

            null

          sampleCharacteristics:

            "Amostra diferente da utilizada na derivação, com idade e
             características físicas semelhantes às da amostra original."

          source:

            "Guedes & Guedes (1991)"
```

A publicação de 1991 informa 206 participantes no estudo de desenvolvimento, sendo 110 homens e 96 mulheres, com idade de 18 a 30 anos. As equações foram testadas posteriormente em uma amostra diferente de 41 sujeitos com idade e características físicas semelhantes.

Para esta documentação, a amostra de 41 participantes é tratada como população de validação do mesmo estudo original, em conformidade com a regra operacional definida para distinguir `validationStudies` de `crossValidationStudies`.

---

# 5. Aplicabilidade em Atletas

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

A descrição da amostra como adultos jovens não fornece base suficiente para classificar os participantes como atletas ou não atletas segundo a taxonomia operacional da plataforma.

---

# 6. Aplicabilidade por Nível de Treinamento

```text
trainingLevel:

    supportedLevels:

        []

    notes:

        "Não documentado segundo a escala operacional da plataforma."
```

Não foi estabelecida correspondência documentada com `SEDENTARY`, `RECREATIONAL`, `TRAINED`, `COMPETITIVE` ou `ELITE`.

---

# 7. Aplicabilidade por Modalidade

```text
modality:

    supportedModalities:

        []

    notes:

        "Não documentado em termos de modalidades esportivas específicas."
```

---

# 8. Características Corporais

```text
bodyCharacteristics:

    rules:

        []
```

Não foi identificada característica corporal da população original que deva ser transformada, sem evidência adicional, em regra `SUPPORTED`, `WARNING` ou `INELIGIBLE`.

---

# 9. Evidências de Validação

## 9.1 Evidências de Desenvolvimento

```text
development:

    studyReference:

        citation:

            "Guedes, D. P.; Guedes, J. E. R. P. (1991).

             Proposição de equações para predição da quantidade de gordura
             corporal em adultos jovens. Semina, 12(2), 61–70."

        doi:

            "10.5433/1679-0367.1991v12n2p61"

        url:

            "https://pubmed.ncbi.nlm.nih.gov/1845307/"

    population:

        "Adultos jovens; n = 206 no estudo, sendo 110 homens e 96 mulheres;
         18–30 anos"

    criterionMethod:

        "Densidade corporal determinada pelo método da pesagem hidrostática"

    year:

        1991

    metrics:

        correlation:

            0.889

        r2:

            null

        standardError:

            0.0056

        meanDifference:

            null

        rmse:

            null

        otherMetrics:

            null
```

A publicação estabelece equações de regressão para predição da densidade corporal em adultos jovens e utiliza pesagem hidrostática como método de referência.

A tabela secundária que reproduz a série das equações masculinas de Guedes identifica especificamente a G-M6 com `r = 0,889` e `EPE = 0,0056 g/ml`. O campo `standardError` recebe `0.0056`, preservando a unidade reportada em `EPE`; `r2` permanece `null` porque a mesma tabela informa `NI` para essa métrica.

---

# 10. Validação

```text
validationStudies:

    - studyReference:

        citation:

            "Guedes, D. P.; Guedes, J. E. R. P. (1991).

             Proposição de equações para predição da quantidade de gordura
             corporal em adultos jovens. Semina, 12(2), 61–70."

        doi:

            "10.5433/1679-0367.1991v12n2p61"

        url:

            "https://pubmed.ncbi.nlm.nih.gov/1845307/"

      population:

        description:

            "Subamostra independente de 41 sujeitos do mesmo estudo original"

        country:

            "Brazil"

        region:

            "Santa Maria, Rio Grande do Sul"

        sexCoverage:

            null

        ageCoverage:

            min: 18

            max: 30

        sampleSize:

            41

        bodyCharacteristicsNotes:

            null

        sampleCharacteristics:

            "Amostra independente, com idade e características físicas
             semelhantes às da amostra utilizada na derivação."

        source:

            "Guedes & Guedes (1991)"

      criterionMethod:

        "Densidade corporal determinada pelo método da pesagem hidrostática"

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

        "A fonte primária consultada não permite atribuir de forma inequívoca
         métricas específicas da G-M6 à subamostra de 41 participantes."
```

Embora o resumo do artigo utilize a expressão “cross validated” para a amostra de 41 participantes, esta ficha segue a regra operacional da biblioteca: uma subamostra independente pertencente ao mesmo estudo original é classificada em `validationStudies`.

---

# 11. Validação Cruzada

```text
crossValidationStudies:

    []
```

Não foi cadastrada nesta ficha uma amostra de outro estudo ou pesquisador como validação cruzada.

A amostra de 41 participantes descrita na publicação original permanece em `validationStudies`, conforme a regra operacional da documentação.

---

# 12. Validação Externa

```text
externalValidationStudies:

    []
```

Nenhum estudo totalmente independente foi incorporado a esta ficha.

---

# 13. Requisitos de Medição

```text
inputs:

    requiredInputs:

        - SKINFOLD_ABDOMEN

        - SKINFOLD_TRICEPS

        - SKINFOLD_SUPRAILIAC

        - SKINFOLD_AXILLARY_MID

        - SKINFOLD_SUBSCAPULAR

        - SKINFOLD_THIGH

    optionalInputs:

        []
```

`SEX` não é input matemático e permanece em `applicability.sex`.

`AGE` não é variável matemática da G-M6 e, portanto, não aparece em `requiredInputs`.

Os seis inputs matemáticos são exatamente os sítios empregados na soma `Σ6`.

---

# 14. Restrições Científicas

```text
restrictions:

    []
```

Não foi identificada uma restrição científica explícita adicional que justifique inelegibilidade.

A faixa etária de 18–30 anos permanece como característica documentada da população de desenvolvimento.

---

# 15. Conflito de Fonte

```text
sourceConflict:

    null
```

Não foi identificado conflito entre fontes primárias legítimas quanto aos coeficientes ou à definição matemática da G-M6.

A existência de diferenças bibliográficas entre a publicação de 1991 e referências secundárias que atribuem conjuntos de Guedes a anos anteriores não é, por si só, um conflito da mesma variante. A relação entre possíveis versões históricas deve ser tratada na auditoria transversal da família.

---

# 16. Ciclo de Vida

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

A variante possui definição matemática, sexo aplicável, faixa etária de desenvolvimento, população original, inputs necessários e referência científica formal suficientes para permanecer `ACTIVE`.

As informações não documentadas permanecem explicitamente como `null` ou `[]`.

---

# 17. Ficha Consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:

            "G-M6"

        familyId:

            "guedes"

        displayName:

            "Guedes 6 Dobras — Masculino"

        aliasNames:

            [

                "Guedes GM6",

                "GM6"

            ]

    mathematicalDefinition:

        formula:

            "D = 1.21546 - 0.08119 × log10(Σ6)"

        variables:

            Σ6:

                "Soma de SKINFOLD_ABDOMEN + SKINFOLD_TRICEPS
                 + SKINFOLD_SUPRAILIAC + SKINFOLD_AXILLARY_MID
                 + SKINFOLD_SUBSCAPULAR + SKINFOLD_THIGH"

        outputType:

            BODY_DENSITY

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

                    min: 18

                    max: 30

                  population:

                    "Subamostra independente do mesmo estudo original"

                  source:

                    "Guedes & Guedes (1991)"

            explicitAgeRestriction:

                null

        population:

            originalPopulation:

                description:

                    "Adultos jovens do sexo masculino"

                country:

                    "Brazil"

                region:

                    "Santa Maria, Rio Grande do Sul"

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

                    "Adultos jovens participantes do estudo original."

                source:

                    "Guedes & Guedes (1991)"

            validationPopulations:

                - description:

                    "Subamostra independente do mesmo estudo original"

                  country:

                    "Brazil"

                  region:

                    "Santa Maria, Rio Grande do Sul"

                  sexCoverage:

                    null

                  ageCoverage:

                    min: 18

                    max: 30

                  sampleSize:

                    41

                  bodyCharacteristicsNotes:

                    null

                  sampleCharacteristics:

                    "Amostra com idade e características físicas semelhantes."

                  source:

                    "Guedes & Guedes (1991)"

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

                citation:

                    "Guedes, D. P.; Guedes, J. E. R. P. (1991).

                     Proposição de equações para predição da quantidade de gordura
                     corporal em adultos jovens. Semina, 12(2), 61–70."

                doi:

                    "10.5433/1679-0367.1991v12n2p61"

                url:

                    "https://pubmed.ncbi.nlm.nih.gov/1845307/"

            population:

                "Adultos jovens; n = 206 no estudo, sendo 110 homens e
                 96 mulheres; 18–30 anos"

            criterionMethod:

                "Densidade corporal determinada pelo método da pesagem hidrostática"

            year:

                1991

            metrics:

                correlation:

                    0.889

                r2:

                    null

                standardError:

                    0.0056

                meanDifference:

                    null

                rmse:

                    null

                otherMetrics:

                    null

        validationStudies:

            - studyReference:

                citation:

                    "Guedes, D. P.; Guedes, J. E. R. P. (1991).

                     Proposição de equações para predição da quantidade de gordura
                     corporal em adultos jovens. Semina, 12(2), 61–70."

                doi:

                    "10.5433/1679-0367.1991v12n2p61"

                url:

                    "https://pubmed.ncbi.nlm.nih.gov/1845307/"

              population:

                "Subamostra independente de 41 sujeitos do mesmo estudo original"

              criterionMethod:

                "Densidade corporal determinada pelo método da pesagem hidrostática"

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

                "Não foram confirmadas métricas específicas da G-M6 para essa
                 subamostra na fonte primária disponível."

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

```text
sexo:

    MALE → compatível

    FEMALE → incompatível

idade:

    população de desenvolvimento = 18–30

    idade NÃO é variável matemática

    faixa populacional NÃO é, por si só, restrição explícita

atleta:

    NOT_DOCUMENTED

trainingLevel:

    NOT_DOCUMENTED

modality:

    NOT_DOCUMENTED

inputs:

    6 skinfolds

output:

    BODY_DENSITY
```

O motor não deve converter a ausência de documentação específica sobre atleta, nível de treinamento ou modalidade em inelegibilidade.

A amostra de 41 participantes deve ser interpretada como evidência de validação do mesmo estudo original, e não como validação cruzada de outro estudo.

---

# 19. Referência

```text
Reference

    citation:

        "Guedes, D. P.; Guedes, J. E. R. P. (1991).

         Proposição de equações para predição da quantidade de gordura corporal
         em adultos jovens. Semina, 12(2), 61–70."

    doi:

        "10.5433/1679-0367.1991v12n2p61"

    url:

        "https://pubmed.ncbi.nlm.nih.gov/1845307/"
```

Fonte institucional adicional para confirmação bibliográfica:

```text
https://ojs.uel.br/revistas/uel/index.php/seminabio/article/view/6946
```

A publicação é assinada por Dartagnan Pinto Guedes e Joana Elisabete R. Pinto Guedes e possui DOI `10.5433/1679-0367.1991v12n2p61`.
