# EquationVariantScientificProfile — G-M5

## 1. Identificação

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

A variante G-M5 pertence ao conjunto específico de equações de Guedes e Guedes para estimativa da densidade corporal em adultos jovens do sexo masculino.

Referência principal:

```text
Guedes, D. P.; Guedes, J. E. R. P. (1991).
Proposição de equações para predição de quantidade de gordura corporal em adultos jovens.
Semina: Ciências Biológicas e da Saúde, 12(2), 61–70.
DOI: 10.5433/1679-0367.1991v12n2p61
```

O estudo teve como objetivo estabelecer equações de regressão para estimar a densidade corporal a partir de espessuras de dobras cutâneas em adultos jovens. A publicação informa uma amostra de 110 homens e 96 mulheres, com idade entre 18 e 30 anos, e utilização da pesagem hidrostática para determinação da densidade corporal. Também informa que as equações foram avaliadas em uma amostra diferente de 41 sujeitos com idade e características físicas semelhantes.

---

## 2. Definição Matemática

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
    = soma das cinco dobras cutâneas utilizadas pela G-M5
```

As cinco dobras utilizadas são:

```text
- abdominal
- tríceps
- supra-ilíaca
- axilar média
- subescapular
```

A fórmula é reproduzida em literatura científica posterior que apresenta a série específica masculina de Guedes e Guedes e identifica a G-M5 como:

```text
Dc = 1.20436 - 0.07848 × log10(DCAB + DCTR + DCSI + DCAM + DCSB)
```

Nessa reprodução, a G-M5 apresenta `r = 0.894` e `EPE = 0.0057 g/ml`, tratados nesta ficha como métricas de desenvolvimento, e não como métricas específicas da amostra independente de 41 sujeitos.

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

A conversão posterior de `BODY_DENSITY` para percentual de gordura corporal permanece uma etapa separada da equação de densidade e não faz parte da definição da `EquationVariant`.

---

## 3. Aplicabilidade

### 3.1 Sexo

```text
sex:
    supportedSexes:
        - MALE
```

A G-M5 é a quinta equação específica masculina da série de Guedes e Guedes. A amostra masculina do estudo original foi composta por 110 homens.

### 3.2 Idade

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
            "Guedes e Guedes (1991)"

    explicitAgeRestriction:
        null
```

A fonte primária informa idade entre 18 e 30 anos para a população do estudo. A média de idade específica dos homens não foi localizada na documentação primária disponível utilizada nesta ficha e, por isso, permanece `null`. A publicação informa que as equações foram avaliadas em uma amostra diferente de 41 sujeitos com idade e características físicas semelhantes, mas não fornece, no resumo disponível, uma faixa etária específica dessa amostra.

Algumas fontes secundárias reproduzem a série de Guedes associando-a à referência de 1985 e indicando outra faixa etária para a população. Essa diferença não é transformada automaticamente em restrição de elegibilidade nesta ficha, porque a fonte primária de 1991 identifica o estudo aqui utilizado e informa 18–30 anos. A existência de versões históricas da família Guedes deve ser tratada na auditoria transversal da biblioteca, sem substituir a fonte de origem dos coeficientes desta variante.

---

## 4. Aplicabilidade Populacional

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
            "Amostra masculina pertencente ao estudo de Guedes e Guedes para adultos jovens."

        source:
            "Guedes e Guedes (1991)"

    validationPopulations:
        - description:
            "Amostra independente utilizada para avaliação das equações"

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
            "Amostra diferente da população de desenvolvimento, descrita como tendo idade e características físicas semelhantes; a composição por sexo não é especificada no resumo disponível."

          source:
            "Guedes e Guedes (1991)"
```

A fonte primária descreve uma população brasileira de adultos jovens, com 110 homens e 96 mulheres entre 18 e 30 anos, utilizando pesagem hidrostática para determinação da densidade corporal. A mesma publicação informa uma amostra diferente de 41 sujeitos para avaliação das equações, descrita como semelhante em idade e características físicas.

A ficha não adiciona região geográfica, características corporais, nível de treinamento ou modalidade esportiva quando esses atributos não estão documentados de forma suficiente para o modelo operacional.

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

A documentação primária consultada não fornece informação suficiente para classificar a população da G-M5, segundo o modelo operacional da plataforma, como atleta ou não atleta. Portanto, não há inferência para esses campos.

---

## 6. Aplicabilidade por Nível de Treinamento

```text
trainingLevel:
    supportedLevels:
        []

    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

Não deve ser inferido mapeamento para `SEDENTARY`, `RECREATIONAL`, `TRAINED`, `COMPETITIVE` ou `ELITE`.

---

## 7. Aplicabilidade por Modalidade

```text
modality:
    supportedModalities:
        []

    notes:
        "Não documentado em termos de modalidades esportivas específicas."
```

Não foi identificada modalidade esportiva específica que deva ser cadastrada como condição de suporte da variante.

---

## 8. Características Corporais

```text
bodyCharacteristics:
    rules:
        []
```

Não foi identificada, na documentação consultada, regra explícita que justifique cadastrar `LOW_BODY_FAT`, `HIGH_BODY_FAT`, `OBESITY`, `HIGH_MUSCLE_MASS`, `EXTREME_SKINFOLD_VALUES` ou `EXTREME_SKINFOLD_SUM` como condição de suporte, alerta ou inelegibilidade.

---

## 9. Evidências de Validação

### 9.1 Evidências de Desenvolvimento

```text
development:
    studyReference:
        citation:
            "Guedes, D. P.; Guedes, J. E. R. P. (1991). Proposição de equações para predição de quantidade de gordura corporal em adultos jovens. Semina: Ciências Biológicas e da Saúde, 12(2), 61–70."

        doi:
            "10.5433/1679-0367.1991v12n2p61"

        url:
            "https://pubmed.ncbi.nlm.nih.gov/1845307/"

    population:
        "Homens adultos jovens, n = 110, 18–30 anos"

    criterionMethod:
        "Densidade corporal determinada por pesagem hidrostática"

    year:
        1991

    metrics:
        correlation:
            0.894

        rSquared:
            null

        standardError:
            0.0057

        meanDifference:
            null

        rmse:
            null

        otherMetrics:
            "A literatura científica posterior que reproduz a tabela das equações apresenta r = 0.894 e EPE = 0.0057 g/ml para a G-M5."
```

A publicação primária informa que as equações foram derivadas para estimativa da densidade corporal a partir de espessuras de dobras cutâneas em adultos jovens e que a densidade corporal de referência foi determinada por pesagem hidrostática. A G-M5 e suas métricas de desenvolvimento são reproduzidas em publicação científica posterior como `r = 0.894` e `EPE = 0.0057 g/ml`.

O valor de `rSquared` permanece `null` porque a fonte consultada apresenta `r²` como não informado para essa tabela, e não é apropriado calcular ou inferir uma métrica adicional apenas a partir do material secundário.

---

## 10. Validação

```text
validationStudies:
    - studyReference:
        citation:
            "Guedes, D. P.; Guedes, J. E. R. P. (1991). Proposição de equações para predição de quantidade de gordura corporal em adultos jovens. Semina: Ciências Biológicas e da Saúde, 12(2), 61–70."

        doi:
            "10.5433/1679-0367.1991v12n2p61"

        url:
            "https://pubmed.ncbi.nlm.nih.gov/1845307/"

      population:
        description:
            "Amostra diferente da população de desenvolvimento, utilizada para avaliação das equações"

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
            "Idade e características físicas descritas como semelhantes às da amostra original; composição por sexo não especificada no resumo disponível."

        source:
            "Guedes e Guedes (1991)"

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
            "A publicação informa a avaliação das equações em uma amostra diferente de 41 sujeitos, mas o resumo disponível não apresenta métricas específicas da G-M5 para essa amostra."

      limitations:
        "A composição por sexo, a faixa etária específica e as métricas individuais da G-M5 na amostra de 41 sujeitos não foram identificadas no resumo primário disponível."
```

A amostra de 41 sujeitos permanece em `validationStudies` porque a fonte primária pertence ao mesmo estudo que originou a equação. O resumo do PubMed utiliza a expressão “cross validated”, mas, para a classificação desta biblioteca, prevalece a relação metodológica definida no padrão: evidência do mesmo estudo original é registrada como validação, enquanto `crossValidationStudies` fica reservado para teste contra estudo, pesquisador ou população de outro trabalho.

Não são atribuídas à G-M5 métricas de validação que não tenham sido identificadas especificamente para essa variante.

---

## 11. Validação Cruzada

```text
crossValidationStudies:
    []
```

Não há, nesta ficha, estudo de outro pesquisador ou outra publicação utilizado especificamente para a classificação de validação cruzada segundo a regra operacional adotada pela biblioteca.

---

## 12. Validação Externa

```text
externalValidationStudies:
    []
```

Nenhum estudo de validação externa independente foi incorporado a esta ficha.

---

## 13. Requisitos de Medição

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

A G-M5 utiliza exclusivamente a soma de cinco dobras cutâneas: abdominal, tríceps, supra-ilíaca, axilar média e subescapular. A fórmula reproduzida na literatura utiliza as abreviações DCAB, DCTR, DCSI, DCAM e DCSB para esses cinco sítios.

`AGE` não é variável matemática da equação e, portanto, não pertence a `requiredInputs`. Sua faixa documentada permanece em `applicability.age`.

`SEX` também não é variável matemática da equação e não pertence a `requiredInputs`; ele participa exclusivamente da seleção da variante por meio de `applicability.sex`.

A normalização utilizada pela plataforma é:

```text
DCAB → SKINFOLD_ABDOMEN
DCTR → SKINFOLD_TRICEPS
DCSI → SKINFOLD_SUPRAILIAC
DCAM → SKINFOLD_AXILLARY_MID
DCSB → SKINFOLD_SUBSCAPULAR
```

---

## 14. Restrições Científicas

```text
restrictions:
    []
```

A faixa de 18–30 anos da população de desenvolvimento permanece registrada como evidência de aplicabilidade e não é transformada, por si só, em uma `ScientificRestriction` adicional.

Não foi identificada restrição científica adicional suficientemente documentada para justificar `INELIGIBLE` ou `WARNING` com base em características de atleta, nível de treinamento, modalidade ou composição corporal.

---

## 15. Conflito de Fonte

```text
sourceConflict:
    null
```

Não foi identificado, para a G-M5 desta ficha, conflito material entre fontes primárias quanto aos coeficientes da equação utilizada.

Existe uma diferença entre a terminologia do resumo do estudo e a classificação operacional deste schema: o PubMed descreve a avaliação dos 41 sujeitos como “cross validated”, enquanto esta biblioteca os registra em `validationStudies` porque a amostra pertence ao mesmo estudo que originou as equações. Essa diferença é de classificação metodológica do schema, não de conteúdo científico conflitante.

Também há fontes secundárias que associam a série específica de Guedes e Guedes à referência de 1985, enquanto a publicação de 1991 constitui a fonte primária utilizada nesta ficha para a variante e sua população de desenvolvimento. Essa diferença não é tratada automaticamente como `sourceConflict`; a auditoria transversal deve determinar, para cada conjunto de coeficientes, a publicação efetivamente originária.

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

Os campos essenciais exigidos para `status: ACTIVE` estão documentados:

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

As informações não documentadas permanecem explicitamente como `null` ou listas vazias, sem inferência.

---

## 17. Ficha Consolidada

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

    mathematicalDefinition:
        formula:
            "D = 1.20436 - 0.07848 × log10(Σ5)"

        outputType:
            BODY_DENSITY

        computationalForm:
            "bodyDensity = 1.20436 - 0.07848 × log10(SKINFOLD_ABDOMEN + SKINFOLD_TRICEPS + SKINFOLD_SUPRAILIAC + SKINFOLD_AXILLARY_MID + SKINFOLD_SUBSCAPULAR)"

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
                    "Amostra independente de validação, com idade descrita como semelhante à amostra original"
                  source:
                    "Guedes e Guedes (1991)"

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
                "Amostra masculina pertencente ao estudo de Guedes e Guedes para adultos jovens."

            source:
                "Guedes e Guedes (1991)"

        validationPopulations:
            - description:
                "Amostra diferente da população de desenvolvimento, utilizada para avaliação das equações"

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
                "Idade e características físicas descritas como semelhantes às da amostra original; composição por sexo não especificada no resumo disponível."

              source:
                "Guedes e Guedes (1991)"

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
                    "Guedes, D. P.; Guedes, J. E. R. P. (1991). Proposição de equações para predição de quantidade de gordura corporal em adultos jovens. Semina: Ciências Biológicas e da Saúde, 12(2), 61–70."

                doi:
                    "10.5433/1679-0367.1991v12n2p61"

                url:
                    "https://pubmed.ncbi.nlm.nih.gov/1845307/"

            population:
                "Homens adultos jovens, n = 110, 18–30 anos"

            criterionMethod:
                "Densidade corporal determinada por pesagem hidrostática"

            year:
                1991

            metrics:
                correlation:
                    0.894

                rSquared:
                    null

                standardError:
                    0.0057

                meanDifference:
                    null

                rmse:
                    null

                otherMetrics:
                    "r = 0.894 e EPE = 0.0057 g/ml, conforme reprodução científica posterior da tabela das equações."

        validationStudies:
            - studyReference:
                citation:
                    "Guedes, D. P.; Guedes, J. E. R. P. (1991). Proposição de equações para predição de quantidade de gordura corporal em adultos jovens. Semina: Ciências Biológicas e da Saúde, 12(2), 61–70."

                doi:
                    "10.5433/1679-0367.1991v12n2p61"

                url:
                    "https://pubmed.ncbi.nlm.nih.gov/1845307/"

              population:
                "Amostra diferente da população de desenvolvimento, n = 41"

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
                    "A publicação informa a avaliação das equações em uma amostra diferente de 41 sujeitos, mas o resumo disponível não apresenta métricas específicas da G-M5."

              limitations:
                "Composição por sexo, faixa etária específica e métricas individuais da G-M5 não especificadas no resumo primário disponível."

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

A ficha consolidada reúne as informações das seções 1–16 sem acrescentar campos derivados de runtime. Em particular, `ageMatch`, `populationMatch`, `sexMatch`, `contextMatch`, `evidenceCoverage`, `READY`, `WARNING` e `INELIGIBLE` não fazem parte do registro científico persistido.

---

## 18. Interpretação para o SuggestionEngine

A ficha permite a seguinte interpretação em runtime:

```text
sexo:
    MALE   → compatível
    FEMALE → incompatível

idade:
    população de desenvolvimento documentada = 18–30
    média específica do desenvolvimento = NOT_DOCUMENTED
    faixa específica da amostra de validação = NOT_DOCUMENTED

athlete:
    NOT_DOCUMENTED

trainingLevel:
    NOT_DOCUMENTED

modality:
    NOT_DOCUMENTED

bodyCharacteristics:
    NOT_DOCUMENTED

inputs:
    5 skinfolds

output:
    BODY_DENSITY
```

A interpretação deve preservar a distinção entre ausência de documentação e inelegibilidade:

```text
NOT_DOCUMENTED
    ≠
INELIGIBLE
```

A ausência de informação sobre atleta, nível de treinamento, modalidade ou composição corporal não deve, por si só, produzir uma restrição ou um estado de inelegibilidade. Da mesma forma, a média de idade não é limite de elegibilidade.

---

## 19. Referência

```text
Reference:
    citation:
        "Guedes, D. P.; Guedes, J. E. R. P. (1991). Proposição de equações para predição de quantidade de gordura corporal em adultos jovens. Semina: Ciências Biológicas e da Saúde, 12(2), 61–70."

    doi:
        "10.5433/1679-0367.1991v12n2p61"

    url:
        "https://pubmed.ncbi.nlm.nih.gov/1845307/"
```

Fonte primária: artigo de Guedes e Guedes publicado no volume 12, número 2 da *Semina: Ciências Biológicas e da Saúde*, páginas 61–70, com DOI `10.5433/1679-0367.1991v12n2p61`. A página oficial da Universidade Estadual de Londrina identifica os autores, o DOI, a edição de 1991 e o texto do resumo; o PubMed registra a publicação como artigo de junho de 1991.

A URL foi registrada como string pura, sem sintaxe Markdown e sem marcadores internos de pesquisa.
