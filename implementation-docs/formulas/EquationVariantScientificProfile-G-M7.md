# EquationVariantScientificProfile — G-M7

## 1. Identification

```text
identity:

    variantId:
        "G-M7"

    familyId:
        "guedes"

    displayName:
        "Guedes 7 Dobras — Masculino"

    aliasNames:
        [
            "Guedes GM7",
            "GM7"
        ]
```

A variante G-M7 pertence ao conjunto de equações específicas de Guedes & Guedes para estimativa da densidade corporal em homens jovens.

Referência bibliográfica utilizada para a família:

```text
Guedes, D. P.; Guedes, J. E. R. P. (trabalho original, década de 1980;
frequentemente citado na literatura como Guedes, 1985).
Equações específicas para estimativa da densidade corporal a partir de
espessuras de dobras cutâneas em adultos jovens.
```

A literatura secundária reproduz a G-M7 como a sétima equação masculina do conjunto de Guedes.

---

# 2. Mathematical Definition

A definição matemática é:

```text
D =
    1.22098
    - 0.08214 × log10(Σ7)
```

Onde:

```text
D
    = BODY_DENSITY

Σ7
    = soma das sete dobras cutâneas utilizadas pela G-M7
```

A soma é composta por:

```text
Σ7 =
    SKINFOLD_ABDOMEN
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_AXILLARY_MID
    + SKINFOLD_SUBSCAPULAR
    + SKINFOLD_THIGH
    + SKINFOLD_MEDIAL_CALF
```

A forma da equação é reproduzida em literatura brasileira que apresenta a tabela original das oito equações específicas masculinas de Guedes.

### Output

```text
outputType = BODY_DENSITY
```

### Forma computacional

```text
sum7 =
    SKINFOLD_ABDOMEN
    + SKINFOLD_TRICEPS
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_AXILLARY_MID
    + SKINFOLD_SUBSCAPULAR
    + SKINFOLD_THIGH
    + SKINFOLD_MEDIAL_CALF

bodyDensity =
    1.22098
    - 0.08214 × log10(sum7)
```

A conversão posterior de densidade corporal para percentual de gordura não faz parte da definição matemática desta variante.

---

# 3. Applicability

## 3.1 Sexo

```text
sex:

    supportedSexes:
        - MALE
```

A G-M7 pertence ao conjunto masculino das equações específicas de Guedes.

---

## 3.2 Idade

As fontes secundárias que reproduzem a tabela original atribuem às equações GM1–GM8 uma amostra masculina de **17–27 anos**, n = 110. Outras fontes que discutem o trabalho original descrevem a população como adultos jovens da Universidade Federal de Santa Maria.

```text
age:

    originalDevelopmentAgeRange:
        min: 17
        max: 27

    developmentSampleMeanAge:
        null

    validatedAgeRanges:
        []

    explicitAgeRestriction:
        null
```

### Observação sobre divergência bibliográfica

Há fontes secundárias que descrevem a população de Guedes como **18–30 anos**, enquanto outras reproduzem explicitamente **17–27 anos**. Como a tabela original reproduzida em literatura secundária identifica as oito equações masculinas como n = 110 e 17–27 anos, esse intervalo é utilizado nesta ficha como o dado de desenvolvimento atualmente mais diretamente recuperável.

A divergência de faixa etária deve permanecer registrada como questão documental a ser revisitada caso a fonte primária seja recuperada em versão verificável.

---

# 4. Population Applicability

```text
population:

    originalPopulation:

        description:
            "Homens jovens / adultos jovens"

        country:
            "Brazil"

        region:
            "Rio Grande do Sul"

        sexCoverage:
            - MALE

        ageCoverage:
            min: 17
            max: 27

        sampleSize:
            110

        bodyCharacteristicsNotes:
            "Amostra de adultos jovens submetidos à avaliação de composição
             corporal por pesagem hidrostática."

        sampleCharacteristics:
            "Estudantes da Universidade Federal de Santa Maria (RS),
             conforme fontes secundárias que reproduzem a descrição da
             amostra original."

        source:
            Guedes & Guedes

    validationPopulations:
        []
```

A literatura secundária identifica a amostra original masculina como **110 participantes**, com idade de **17–27 anos**, vinculados à Universidade Federal de Santa Maria, no Rio Grande do Sul.

A densidade corporal de referência foi obtida por **pesagem hidrostática**.

Não foi localizada nesta etapa uma descrição suficientemente detalhada de composição corporal da amostra que justifique criar regras específicas de aplicabilidade corporal.

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

Não deve ser inferido que estudantes universitários eram atletas ou não atletas apenas a partir da descrição da amostra.

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
        "Não documentado."
```

---

# 8. Body Characteristics

```text
bodyCharacteristics:

    rules:
        []
```

A faixa corporal observada na amostra não foi transformada em restrição de uso.

---

# 9. Validation Evidence

## 9.1 Development Evidence

```text
development:

    studyReference:

        citation:
            "Guedes & Guedes — equações específicas de densidade corporal
             para adultos jovens."

        doi:
            null

    population:
        "Homens jovens, n = 110, 17–27 anos"

    criterionMethod:
        "Pesagem hidrostática"

    year:
        1985
```

A publicação da família é frequentemente citada na literatura como **Guedes (1985)**. A forma bibliográfica exata e a distinção entre o trabalho acadêmico original e publicações posteriores da mesma pesquisa devem ser preservadas como parte do controle bibliográfico.

---

# 10. Development Metrics

Para a G-M7, as fontes que reproduzem a tabela das equações específicas masculinas apresentam:

```text
correlation (r):
    0.904

standardError / EPE:
    0.0054 g/ml

r2:
    não apresentado na mesma tabela
```

No modelo atual do schema:

```text
metrics:

    correlation:
        0.904

    standardError:
        0.0054

    meanDifference:
        null

    rmse:
        null

    otherMetrics:
        null
```

Essas métricas correspondem à equação GM7 na tabela recuperada da literatura secundária que reproduz as equações de Guedes.

---

# 11. Validation Studies

```text
validationStudies:
    []
```

Não foi cadastrada nesta ficha uma validação externa específica da G-M7 cuja população, amostra e métricas possam ser atribuídas inequivocamente à variante.

---

# 12. Cross-validation

```text
crossValidationStudies:
    []
```

As fontes consultadas nesta etapa indicam a existência de validação cruzada do conjunto de equações de Guedes em amostra independente, mas não fornecem dados suficientemente específicos para atribuir métricas e população exclusivamente à G-M7.

Não distribuir métricas agregadas entre variantes por inferência.

---

# 13. External Validation

```text
externalValidationStudies:
    []
```

Uma validação posterior masculina publicada na literatura avaliou as equações específicas e generalizadas de Guedes em estudantes de Educação Física. Entretanto, essa validação compara várias equações simultaneamente e deve ser registrada somente quando os resultados individuais da G-M7 puderem ser atribuídos sem ambiguidade.

---

# 14. Measurement Requirements

```text
inputs:

    requiredInputs:

        - SKINFOLD_ABDOMEN
        - SKINFOLD_TRICEPS
        - SKINFOLD_SUPRAILIAC
        - SKINFOLD_AXILLARY_MID
        - SKINFOLD_SUBSCAPULAR
        - SKINFOLD_THIGH
        - SKINFOLD_MEDIAL_CALF

    optionalInputs:
        []
```

A equação não contém idade como variável matemática.

Portanto:

```text
SEX
    → Applicability

AGE
    → Applicability / population comparison

7 skinfolds
    → Formula inputs
```

Não incluir `AGE` em `requiredInputs` apenas porque a população original possui uma faixa etária documentada.

---

# 15. Scientific Restrictions

```text
restrictions:
    []
```

Não foi identificada uma restrição científica explícita adicional que justifique `INELIGIBLE`.

A faixa de desenvolvimento de 17–27 anos representa a população documentada, mas não deve ser transformada automaticamente em uma `ScientificRestriction` sem uma formulação explícita de restrição de uso.

---

# 16. Source Conflict

```text
evidence:

    sourceConflict:
        null
```

### Observação documental

Existe divergência secundária quanto à faixa etária atribuída ao estudo de Guedes:

```text
17–27 anos
```

versus:

```text
18–30 anos
```

Nesta ficha, a divergência é tratada como **observação bibliográfica pendente de verificação da fonte primária**, e não como conflito entre definições matemáticas da G-M7.

Caso a fonte primária recuperada posteriormente confirme valores incompatíveis para a mesma informação, o registro deverá ser promovido a:

```text
SourceConflictRecord
```

com as respectivas fontes conflitantes.

---

# 17. Lifecycle

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

A variante possui a definição matemática, sexo, população, inputs e referência suficientes para permanecer cadastrada na biblioteca. As lacunas documentais não essenciais permanecem explicitamente como não documentadas.

---

# 18. Ficha consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:
            "G-M7"

        familyId:
            "guedes"

        displayName:
            "Guedes 7 Dobras — Masculino"

        aliasNames:
            [
                "Guedes GM7",
                "GM7"
            ]

    applicability:

        sex:

            supportedSexes:
                - MALE

        age:

            originalDevelopmentAgeRange:
                min: 17
                max: 27

            developmentSampleMeanAge:
                null

            validatedAgeRanges:
                []

            explicitAgeRestriction:
                null

        population:

            originalPopulation:

                description:
                    "Homens jovens / adultos jovens"

                country:
                    "Brazil"

                region:
                    "Rio Grande do Sul"

                sexCoverage:
                    - MALE

                ageCoverage:
                    min: 17
                    max: 27

                sampleSize:
                    110

                bodyCharacteristicsNotes:
                    "Amostra avaliada por pesagem hidrostática."

                sampleCharacteristics:
                    "Estudantes da Universidade Federal de Santa Maria (RS)."

                source:
                    "Guedes & Guedes"

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
                "Guedes & Guedes — equações específicas de densidade corporal"

            population:
                "Homens jovens, n = 110, 17–27 anos"

            criterionMethod:
                "Pesagem hidrostática"

            year:
                1985

        validationStudies:
            []

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
            - SKINFOLD_MEDIAL_CALF

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

# 19. Referência principal

```text
Reference

    citation:
        "Guedes & Guedes — equações específicas para estimativa da
         densidade corporal em adultos jovens, frequentemente citadas
         como Guedes (1985)."

    doi:
        null

    url:
        null
```

### Fontes secundárias consultadas para confirmação da equação

```text
- Revista Brasileira de Educação Física e Esporte — artigo de validação
  de equações antropométricas específicas e generalizadas.

- Revista Brasileira de Cineantropometria & Desempenho Humano — tabelas
  que reproduzem as equações de Guedes e seus parâmetros de desenvolvimento.
```

As fontes secundárias confirmam a forma da GM7:

```text
Dc = 1.22098 - 0.08214 × log10(
     AB + TR + SI + AX + SE + CXM + PM
)
```

com:

```text
r = 0.904
EPE = 0.0054 g/ml
n = 110
idade = 17–27 anos
```

---

# 20. Observações para o SuggestionEngine

A interpretação deve permanecer baseada exclusivamente nos dados acima:

```text
sexo:
    MALE → população documentada
    FEMALE → incompatibilidade de sexo

idade:
    comparar com evidência populacional documentada
    não usar idade média inexistente como limite

atleta:
    NOT_DOCUMENTED

trainingLevel:
    NOT_DOCUMENTED

modality:
    NOT_DOCUMENTED

inputs:
    7 skinfolds

output:
    BODY_DENSITY
```

A existência de uma população de desenvolvimento jovem não transforma automaticamente indivíduos fora da faixa documentada em `INELIGIBLE`; a decisão deve ser derivada pelas regras gerais do motor e pelas restrições científicas explícitas, quando existirem.
