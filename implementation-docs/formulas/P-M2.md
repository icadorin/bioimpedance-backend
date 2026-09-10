# EquationVariantScientificProfile — P-M2

## 1. Identificação

```text
identity:

    variantId:

        "P-M2"

    familyId:

        "petroski"

    displayName:

        "Petroski M2 — Masculino"

    aliasNames:

        [

            "Petroski M2",

            "Petroski 9 Dobras + Idade + Circunferências — Masculino"

        ]
```

Referência principal:

```text
Petroski, Edio Luiz (1995).

Desenvolvimento e validação de equações generalizadas para a estimativa da

densidade corporal em adultos.

Tese de Doutorado, Programa de Pós-Graduação em Ciência do Movimento Humano,

Universidade Federal de Santa Maria, Santa Maria, RS, Brasil.
```

A variante P-M2 corresponde ao segundo modelo masculino do conjunto de equações generalizadas de Petroski (1995), apresentado na Tabela 9.

---

## 2. Definição Matemática

```text
mathematicalDefinition:

    formula:

        D =
            1.08516305
            - 0.00028465 × X9
            + 0.00000026 × X9²
            - 0.00021018 × AGE
            + 0.00173856 × CIRCUMFERENCE_FOREARM
            - 0.00043254 × CIRCUMFERENCE_ABDOMEN

    auxiliaryVariables:

        X9 =
            SKINFOLD_SUBSCAPULAR
            + SKINFOLD_TRICEPS
            + SKINFOLD_BICEPS
            + SKINFOLD_AXILLARY_MID
            + SKINFOLD_PECTORAL
            + SKINFOLD_SUPRAILIAC
            + SKINFOLD_ABDOMEN
            + SKINFOLD_THIGH
            + SKINFOLD_MEDIAL_CALF

    outputType:

        BODY_DENSITY

    computationalForm:

        D =
            1.08516305
            - 0.00028465 * X9
            + 0.00000026 * X9^2
            - 0.00021018 * AGE
            + 0.00173856 * CIRCUMFERENCE_FOREARM
            - 0.00043254 * CIRCUMFERENCE_ABDOMEN
```

A equação M2 utiliza a soma e o quadrado da soma de nove dobras cutâneas, idade, circunferência do antebraço e circunferência do abdômen. A Tabela 9 da tese apresenta para M2 `R = 0,894`, `R² = 0,800` e `EPE = 0,0070 g/ml`.

Na documentação da tese, `X9` corresponde à soma das nove dobras: subescapular, tríceps, bíceps, axilar média, supra-ilíaca, abdominal, coxa e panturrilha medial, conforme a convenção das variáveis apresentada no texto. Para o perfil, os identificadores foram normalizados para a nomenclatura canônica da plataforma.

---

## 3. Aplicabilidade

### 3.1 Sexo

```text
sex:

    supportedSexes:

        - MALE
```

A M2 pertence ao conjunto masculino das equações generalizadas de Petroski.

### 3.2 Idade

```text
age:

    originalDevelopmentAgeRange:

        min: 18

        max: 66

    developmentSampleMeanAge:

        30.17

    validatedAgeRanges:

        - range:

            min: 18

            max: 56

          population:

            "Homens adultos da amostra independente de validação"

          source:

            "Petroski (1995)"

    explicitAgeRestriction:

        null
```

A amostra masculina de desenvolvimento continha 304 homens entre 18 e 66 anos, com média de 30,17 ± 9,78 anos. A amostra independente de validação continha 87 homens entre 18 e 56 anos, com média de 30,68 ± 9,11 anos. A tese define o conjunto masculino de equações para adultos de 18 a 66 anos.

A média de idade da amostra de desenvolvimento é descritiva e não constitui limite de elegibilidade.

---

## 4. Aplicabilidade Populacional

```text
population:

    originalPopulation:

        description:

            "Homens adultos das regiões central do Rio Grande do Sul e litorânea de Santa Catarina"

        country:

            "Brazil"

        region:

            "Região central do Rio Grande do Sul e região litorânea de Santa Catarina"

        sexCoverage:

            - MALE

        ageCoverage:

            min: 18

            max: 66

        sampleSize:

            304

        bodyCharacteristicsNotes:

            "A amostra masculina de desenvolvimento apresentou heterogeneidade em idade e composição corporal. O estudo geral incluiu participantes de diferentes níveis de gordura corporal; as 391 observações masculinas foram divididas entre desenvolvimento e validação."

        sampleCharacteristics:

            "Homens adultos da amostra de desenvolvimento das equações generalizadas de Petroski."

        source:

            "Petroski (1995)"

    validationPopulations:

        - description:

            "Homens adultos da amostra independente de validação"

          country:

            "Brazil"

          region:

            "Região central do Rio Grande do Sul e região litorânea de Santa Catarina"

          sexCoverage:

            - MALE

          ageCoverage:

            min: 18

            max: 56

          sampleSize:

            87

          bodyCharacteristicsNotes:

            "Densidade corporal mensurada média = 1,06282 g/ml; a amostra de validação foi composta por 87 homens."

          sampleCharacteristics:

            "Amostra de calibração independente oriunda da mesma população e utilizada para validação das equações masculinas."

          source:

            "Petroski (1995)"
```

O estudo descreve 672 participantes no total, sendo 391 homens entre 18 e 66 anos. Para os modelos masculinos, 304 homens foram utilizados no desenvolvimento e 87 na validação. A própria tese caracteriza a validação como realizada em uma amostra de calibração independente, oriunda da mesma população.

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

A fonte não fornece classificação suficientemente precisa da população nos campos binários de atleta e não atleta usados pela plataforma. Não deve ser inferida incompatibilidade por ausência dessa informação.

---

## 6. Aplicabilidade por Nível de Treinamento

```text
trainingLevel:

    supportedLevels:

        []

    notes:

        "Não documentado segundo a escala operacional da plataforma."
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

As características corporais da amostra são tratadas como descrição populacional e não convertidas automaticamente em regras de elegibilidade ou restrições científicas.

---

## 9. Evidências de Validação

### 9.1 Evidências de Desenvolvimento

```text
development:

    studyReference:

        citation:

            "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado, Programa de Pós-Graduação em Ciência do Movimento Humano, Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

        doi:

            null

        url:

            "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"

    population:

        "Homens adultos, n = 304, 18–66 anos"

    criterionMethod:

        "Densidade corporal determinada por pesagem hidrostática"

    year:

        1995

    metrics:

        R:

            0.894

        R2:

            0.800

        standardError:

            0.0070
```

As métricas de desenvolvimento da M2 na Tabela 9 são `R = 0,894`, `R² = 0,800` e `EPE = 0,0070 g/ml`.

---

## 10. Validação

```text
validationStudies:

    - studyReference:

        citation:

            "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado, Programa de Pós-Graduação em Ciência do Movimento Humano, Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

        doi:

            null

        url:

            "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"

      population:

        description:

            "Homens adultos da amostra independente de validação"

        country:

            "Brazil"

        region:

            "Região central do Rio Grande do Sul e região litorânea de Santa Catarina"

        sexCoverage:

            - MALE

        ageCoverage:

            min: 18

            max: 56

        sampleSize:

            87

        bodyCharacteristicsNotes:

            "Densidade corporal mensurada média = 1,06282 ± 0,0153 g/ml."

        sampleCharacteristics:

            "Amostra independente de validação oriunda da mesma população do estudo original."

        source:

            "Petroski (1995)"

      criterionMethod:

        "Pesagem hidrostática"

      metrics:

        correlation:

            0.880

        standardError:

            0.0069

        meanDifference:

            -0.0002

        rmse:

            null

        otherMetrics:

            "Densidade média estimada = 1,06263 g/ml; densidade média mensurada = 1,06282 g/ml; t = -0,244; EC = -0,0002 g/ml; ET = 0,0072 g/ml."

      limitations:

        "Validação realizada em amostra de calibração independente oriunda da mesma população do estudo original; não corresponde a validação externa."
```

Na Tabela 10, a M2 apresenta `r = 0,880`, `t = -0,244`, `EC = -0,0002 g/ml`, `ET = 0,0072 g/ml` e `EPE = 0,0069 g/ml`. A densidade média estimada foi 1,06263 g/ml, contra 1,06282 g/ml medida pela pesagem hidrostática.

A amostra de 87 homens é tratada como validação porque pertence ao mesmo estudo/programa de pesquisa e foi definida pela própria tese como amostra de calibração independente da mesma população.

---

## 11. Validação Cruzada

```text
crossValidationStudies:

    []
```

Nenhum resultado específico de validação cruzada da variante P-M2 foi incorporado neste perfil. A validação da própria equação na amostra de 87 homens permanece em `validationStudies`.

A tese, em sua discussão metodológica, distingue a validação das equações desenvolvidas da análise de validação cruzada de equações de outros investigadores.

---

## 12. Validação Externa

```text
externalValidationStudies:

    []
```

Nenhum estudo externo independente foi incorporado a esta ficha.

---

## 13. Requisitos de Medição

```text
inputs:

    requiredInputs:

        - AGE

        - SKINFOLD_SUBSCAPULAR

        - SKINFOLD_TRICEPS

        - SKINFOLD_BICEPS

        - SKINFOLD_AXILLARY_MID

        - SKINFOLD_PECTORAL

        - SKINFOLD_SUPRAILIAC

        - SKINFOLD_ABDOMEN

        - SKINFOLD_THIGH

        - SKINFOLD_MEDIAL_CALF

        - CIRCUMFERENCE_FOREARM

        - CIRCUMFERENCE_ABDOMEN

    optionalInputs:

        []
```

`SEX` não é input matemático e permanece exclusivamente em `applicability.sex`.

`AGE` é `requiredInput` porque participa diretamente da equação.

Os nove componentes de `X9` são:

```text
SKINFOLD_SUBSCAPULAR
SKINFOLD_TRICEPS
SKINFOLD_BICEPS
SKINFOLD_AXILLARY_MID
SKINFOLD_PECTORAL
SKINFOLD_SUPRAILIAC
SKINFOLD_ABDOMEN
SKINFOLD_THIGH
SKINFOLD_MEDIAL_CALF
```

A convenção original da tese identifica as nove dobras por `SE`, `TR`, `BI`, `AM`, `SI`, `AB`, `CX` e `PM`, e define `X9` como seu somatório; os identificadores acima representam a normalização canônica da plataforma.

As circunferências requeridas são `CIRCUMFERENCE_FOREARM` e `CIRCUMFERENCE_ABDOMEN`. A tese define a circunferência do antebraço como a maior circunferência da região proximal do antebraço e a circunferência abdominal em plano horizontal, dois centímetros acima da cicatriz umbilical.

As unidades originais informadas pela tese são dobras cutâneas em milímetros, circunferências em centímetros e idade em anos.

---

## 14. Restrições Científicas

```text
restrictions:

    []
```

Não foi cadastrada restrição científica explícita adicional além da aplicabilidade populacional documentada. A faixa de 18–66 anos representa a faixa da população masculina de desenvolvimento e não é transformada automaticamente em uma proibição formal fora desse intervalo.

---

## 15. Conflito de Fonte

```text
sourceConflict:

    null
```

Não foi identificado conflito material entre fontes primárias para a definição matemática e as métricas da P-M2 na tese de Petroski (1995). A origem dos coeficientes, a composição de `X9` e os resultados de validação são apresentados de modo consistente nas tabelas correspondentes.

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

Os campos essenciais para `ACTIVE` estão presentes: identidade, definição matemática, sexo suportado, faixa etária de desenvolvimento, população original, inputs obrigatórios e referência da definição.

Checklist:

```text
identity                    ✓
mathematicalDefinition      ✓
supportedSexes              ✓
originalDevelopmentAgeRange ✓
originalPopulation          ✓
requiredInputs              ✓
definition reference        ✓
sourceConflict              null
```

---

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile:

    identity:

        variantId: "P-M2"

        familyId: "petroski"

        displayName: "Petroski M2 — Masculino"

        aliasNames:

            - "Petroski M2"

            - "Petroski 9 Dobras + Idade + Circunferências — Masculino"

    mathematicalDefinition:

        formula: "D = 1.08516305 - 0.00028465 × X9 + 0.00000026 × X9² - 0.00021018 × AGE + 0.00173856 × CIRCUMFERENCE_FOREARM - 0.00043254 × CIRCUMFERENCE_ABDOMEN"

        auxiliaryVariables:

            X9: "SKINFOLD_SUBSCAPULAR + SKINFOLD_TRICEPS + SKINFOLD_BICEPS + SKINFOLD_AXILLARY_MID + SKINFOLD_PECTORAL + SKINFOLD_SUPRAILIAC + SKINFOLD_ABDOMEN + SKINFOLD_THIGH + SKINFOLD_MEDIAL_CALF"

        outputType: BODY_DENSITY

    applicability:

        sex:

            supportedSexes:

                - MALE

        age:

            originalDevelopmentAgeRange:

                min: 18

                max: 66

            developmentSampleMeanAge: 30.17

            validatedAgeRanges:

                - range:

                    min: 18

                    max: 56

                  population: "Homens adultos da amostra independente de validação"

                  source: "Petroski (1995)"

            explicitAgeRestriction: null

    population:

        originalPopulation:

            description: "Homens adultos das regiões central do Rio Grande do Sul e litorânea de Santa Catarina"

            country: "Brazil"

            region: "Região central do Rio Grande do Sul e região litorânea de Santa Catarina"

            sexCoverage:

                - MALE

            ageCoverage:

                min: 18

                max: 66

            sampleSize: 304

            bodyCharacteristicsNotes: "Amostra masculina de desenvolvimento com heterogeneidade em idade e composição corporal."

            sampleCharacteristics: "Homens adultos da amostra de desenvolvimento de Petroski."

            source: "Petroski (1995)"

        validationPopulations:

            - description: "Homens adultos da amostra independente de validação"

              country: "Brazil"

              region: "Região central do Rio Grande do Sul e região litorânea de Santa Catarina"

              sexCoverage:

                  - MALE

              ageCoverage:

                  min: 18

                  max: 56

              sampleSize: 87

              bodyCharacteristicsNotes: "Densidade corporal mensurada média = 1,06282 ± 0,0153 g/ml."

              sampleCharacteristics: "Amostra independente de validação oriunda da mesma população."

              source: "Petroski (1995)"

    athlete:

        developedInAthletes: null

        validatedInAthletes: null

        developedInNonAthletes: null

        validatedInNonAthletes: null

        explicitAthleteRestriction: null

    trainingLevel:

        supportedLevels: []

        notes: "Não documentado segundo a escala operacional da plataforma."

    modality:

        supportedModalities: []

        notes: "Não documentado em termos de modalidades esportivas específicas."

    bodyCharacteristics:

        rules: []

    evidence:

        development:

            studyReference:

                citation: "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado, Programa de Pós-Graduação em Ciência do Movimento Humano, Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

                doi: null

                url: "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"

            population: "Homens adultos, n = 304, 18–66 anos"

            criterionMethod: "Densidade corporal determinada por pesagem hidrostática"

            year: 1995

            metrics:

                R: 0.894

                R2: 0.800

                standardError: 0.0070

        validationStudies:

            - studyReference:

                citation: "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado, Programa de Pós-Graduação em Ciência do Movimento Humano, Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

                doi: null

                url: "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"

              population: "Homens adultos, n = 87, 18–56 anos"

              criterionMethod: "Pesagem hidrostática"

              metrics:

                  correlation: 0.880

                  standardError: 0.0069

                  meanDifference: -0.0002

                  rmse: null

                  otherMetrics: "t = -0,244; ET = 0,0072 g/ml."

              limitations: "Validação independente oriunda da mesma população do estudo original."

        crossValidationStudies: []

        externalValidationStudies: []

        sourceConflict: null

    inputs:

        requiredInputs:

            - AGE

            - SKINFOLD_SUBSCAPULAR

            - SKINFOLD_TRICEPS

            - SKINFOLD_BICEPS

            - SKINFOLD_AXILLARY_MID

            - SKINFOLD_PECTORAL

            - SKINFOLD_SUPRAILIAC

            - SKINFOLD_ABDOMEN

            - SKINFOLD_THIGH

            - SKINFOLD_MEDIAL_CALF

            - CIRCUMFERENCE_FOREARM

            - CIRCUMFERENCE_ABDOMEN

        optionalInputs: []

    restrictions: []

    lifecycle:

        status: ACTIVE

        version: "1"

        supersedes: null

        supersededBy: null

        effectiveFrom: null

        changeLog: []
```

---

## 18. Interpretação para o SuggestionEngine

```text
sex:

    MALE → compatível

    FEMALE → incompatível com a variante masculina

age:

    faixa de desenvolvimento = 18–66 anos

    faixa de validação = 18–56 anos

    média de desenvolvimento = 30.17

    média não é limite de elegibilidade

athlete:

    NOT_DOCUMENTED

trainingLevel:

    NOT_DOCUMENTED

modality:

    NOT_DOCUMENTED

required mathematical data:

    AGE

    9 skinfolds

    forearm circumference

    abdominal circumference

output:

    BODY_DENSITY
```

O motor não deve inferir incompatibilidade com atletas, níveis de treinamento ou modalidades esportivas a partir da ausência de documentação. Também não deve transformar automaticamente a faixa observada da amostra em uma restrição científica formal.

A M2 distingue-se da M1 pela inclusão de `CIRCUMFERENCE_FOREARM` e `CIRCUMFERENCE_ABDOMEN` na definição matemática, além das nove dobras e da idade.

---

## 19. Referência

```text
Reference:

    citation:

        "Petroski, Edio Luiz (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado, Programa de Pós-Graduação em Ciência do Movimento Humano, Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

    doi:

        null

    url:

        "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"
```

Fonte bibliográfica principal da variante: tese de doutorado de Edio Luiz Petroski, Universidade Federal de Santa Maria, 1995. O registro bibliográfico da obra também é corroborado por referências acadêmicas posteriores que citam a tese como trabalho de 1995 da UFSM.
