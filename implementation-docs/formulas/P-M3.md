# EquationVariantScientificProfile — P-M3

## 1. Identificação

```text
identity:

    variantId:

        "P-M3"

    familyId:

        "petroski"

    displayName:

        "Petroski M3 — 7 Dobras, Masculino"

    aliasNames:

        [

            "Petroski M3",

            "Petroski 7 Dobras — Masculino"

        ]
```

Referência principal:

```text
Petroski, Edio Luiz (1995).

Desenvolvimento e validação de equações generalizadas para a estimativa da

densidade corporal em adultos.

Tese de Doutorado. Universidade Federal de Santa Maria.

Santa Maria, RS, Brasil.
```

A variante P-M3 corresponde à equação generalizada masculina M3 apresentada na Tabela 3 da revisão que reproduz as equações desenvolvidas por Petroski (1995). A equação utiliza sete dobras cutâneas e idade.

---

## 2. Definição Matemática

```text
mathematicalDefinition:

    formula:

        D =
            1.10038145
            - 0.00035804 × X7
            + 0.00000036 × X7²
            - 0.00025154 × AGE

    auxiliaryVariables:

        X7 =
            SKINFOLD_SUBSCAPULAR
            + SKINFOLD_TRICEPS
            + SKINFOLD_PECTORAL
            + SKINFOLD_AXILLARY_MID
            + SKINFOLD_SUPRAILIAC
            + SKINFOLD_ABDOMEN
            + SKINFOLD_THIGH

    outputType:

        BODY_DENSITY

    computationalForm:

        D =
            1.10038145
            - 0.00035804 * X7
            + 0.00000036 * X7^2
            - 0.00025154 * AGE
```

A M3 é o modelo quadrático masculino de sete dobras cutâneas. A definição publicada apresenta `X7` como o somatório de sete dobras: subescapular, tríceps, peitoral, axilar média, supra-ilíaca, abdominal e coxa.

No perfil, os nomes anatômicos foram normalizados para os identificadores canônicos `SKINFOLD_PECTORAL`, `SKINFOLD_AXILLARY_MID` e `SKINFOLD_ABDOMEN`, conforme o padrão operacional da biblioteca.

---

## 3. Aplicabilidade

### 3.1 Sexo

```text
sex:

    supportedSexes:

        - MALE
```

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

A população de desenvolvimento masculina compreendeu 304 homens entre 18 e 66 anos, com média de idade de 30,17 anos. A amostra de validação masculina compreendeu 87 homens entre 18 e 56 anos, conforme a descrição do estudo de Petroski.

A média etária da amostra de desenvolvimento é descritiva e não constitui limite de elegibilidade.

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

            "Amostra heterogênea em idade e gordura corporal; percentual de gordura corporal de 2,20% a 33,16%, com média de 16,14%."

        sampleCharacteristics:

            "Amostra masculina de regressão utilizada no desenvolvimento das equações generalizadas de Petroski."

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

            "Densidade corporal média mensurada de 1,06282 g/ml; percentual de gordura corporal médio de 15,83%."

          sampleCharacteristics:

            "Amostra independente de validação que não participou do desenvolvimento dos modelos."

          source:

            "Petroski (1995)"
```

O estudo de Petroski foi desenvolvido com 672 adultos de ambos os sexos, provenientes das regiões central do Rio Grande do Sul e litorânea de Santa Catarina. Os participantes foram separados em grupo de regressão e grupo de validação; para os homens, foram 304 participantes na regressão e 87 na validação.

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

A fonte não fornece classificação suficientemente precisa para atribuir os campos binários de atleta ou não atleta utilizados pela plataforma. Portanto, nenhuma classificação é inferida.

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

As características de composição corporal observadas nas amostras são mantidas como descrição populacional e não convertidas em regras explícitas de restrição científica.

---

## 9. Evidências de Validação

### 9.1 Evidências de Desenvolvimento

```text
development:

    studyReference:

        citation:

            "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado, Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

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

            0.880

        R2:

            0.780

        standardError:

            0.0073
```

Para a M3 masculina, a tabela de equações generalizadas reporta `R = 0,880`, `R² = 0,780` e `EPE = 0,0073`. A população de desenvolvimento masculina foi composta por 304 homens de 18 a 66 anos.

---

## 10. Validação

```text
validationStudies:

    - studyReference:

        citation:

            "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado, Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

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

            "Densidade corporal média estimada de 1,06250 g/ml e densidade corporal média mensurada de 1,06282 g/ml."

        sampleCharacteristics:

            "Amostra independente de validação da mesma pesquisa, separada da amostra de regressão."

        source:

            "Petroski (1995)"

      criterionMethod:

        "Pesagem hidrostática"

      metrics:

        correlation:

            0.870

        standardError:

            0.0078

        meanDifference:

            -0.0003

        rmse:

            0.0075

        otherMetrics:

            "Erro constante (EC) = -0,0003 g/ml; erro total (ET) = 0,0075 g/ml; teste t = -0,397; diferença entre as médias não significativa (p > 0,05)."

      limitations:

        "Validação realizada em amostra independente da mesma pesquisa e da mesma população de origem; não corresponde a validação cruzada nem a validação externa."
```

A amostra masculina de 87 participantes é classificada como `validationStudies`, pois foi utilizada pelo próprio estudo de Petroski para validar as equações desenvolvidas na amostra de regressão. A revisão metodológica descreve explicitamente a divisão entre grupo de regressão e grupo de validação.

Os valores de validação registrados nesta ficha são os resultados atribuídos à M3 na amostra independente de 87 homens.

---

## 11. Validação Cruzada

```text
crossValidationStudies:

    []
```

Não foi incorporado nesta ficha resultado de teste da variante P-M3 contra uma população, dado ou equação originados de outro estudo ou pesquisador.

---

## 12. Validação Externa

```text
externalValidationStudies:

    []
```

Não foi incorporado nesta ficha estudo independente de validação externa da variante P-M3.

---

## 13. Requisitos de Medição

```text
inputs:

    requiredInputs:

        - AGE

        - SKINFOLD_SUBSCAPULAR

        - SKINFOLD_TRICEPS

        - SKINFOLD_PECTORAL

        - SKINFOLD_AXILLARY_MID

        - SKINFOLD_SUPRAILIAC

        - SKINFOLD_ABDOMEN

        - SKINFOLD_THIGH

    optionalInputs:

        []
```

`SEX` não é input matemático e permanece exclusivamente em `applicability.sex`.

`AGE` é `requiredInput` porque participa diretamente da equação.

O `X7` é formado pelas sete dobras:

```text
SKINFOLD_SUBSCAPULAR
SKINFOLD_TRICEPS
SKINFOLD_PECTORAL
SKINFOLD_AXILLARY_MID
SKINFOLD_SUPRAILIAC
SKINFOLD_ABDOMEN
SKINFOLD_THIGH
```

A fonte descreve essas sete dobras como `X7 = ∑7DC`, correspondendo a subescapular, tríceps, peitoral, axilar média, supra-ilíaca, abdômen e coxa.

As unidades documentadas para as variáveis são:

```text
dobras cutâneas → mm
idade → anos
densidade corporal → g/ml
```

---

## 14. Restrições Científicas

```text
restrictions:

    []
```

Não foi atribuída restrição científica adicional além da aplicabilidade documentada ao sexo masculino e da população descrita na fonte.

A faixa de 18–66 anos corresponde à população de desenvolvimento observada e não é convertida automaticamente em uma proibição científica fora desse intervalo.

---

## 15. Conflito de Fonte

```text
evidence:

    sourceConflict:

        null
```

Não foi identificado conflito material entre fontes primárias na definição matemática, nos coeficientes ou nos dados de desenvolvimento registrados para P-M3.

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
identity                    ✓

mathematicalDefinition      ✓

supportedSexes              ✓

originalDevelopmentAgeRange ✓

originalPopulation          ✓

requiredInputs              ✓

definition reference        ✓

sourceConflict              null
```

A variante permanece com `status: ACTIVE` porque os campos essenciais estão documentados e não há conflito de fonte não resolvido neste perfil.

---

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile

    identity:

        variantId:

            "P-M3"

        familyId:

            "petroski"

        displayName:

            "Petroski M3 — 7 Dobras, Masculino"

        aliasNames:

            - "Petroski M3"

            - "Petroski 7 Dobras — Masculino"

    mathematicalDefinition:

        formula:

            "D = 1.10038145 - 0.00035804 × X7 + 0.00000036 × X7² - 0.00025154 × AGE"

        auxiliaryVariables:

            X7:

                "SKINFOLD_SUBSCAPULAR + SKINFOLD_TRICEPS + SKINFOLD_PECTORAL + SKINFOLD_AXILLARY_MID + SKINFOLD_SUPRAILIAC + SKINFOLD_ABDOMEN + SKINFOLD_THIGH"

        outputType:

            BODY_DENSITY

    applicability:

        sex:

            supportedSexes:

                - MALE

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

                "Percentual de gordura corporal de 2,20% a 33,16%, com média de 16,14%."

            sampleCharacteristics:

                "Amostra masculina de regressão do estudo de Petroski."

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

                "Densidade corporal média mensurada de 1,06282 g/ml; percentual de gordura corporal médio de 15,83%."

              sampleCharacteristics:

                "Amostra independente de validação."

              source:

                "Petroski (1995)"

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

                    "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado, Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

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

                    0.880

                R2:

                    0.780

                standardError:

                    0.0073

        validationStudies:

            - studyReference:

                citation:

                    "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado, Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

                doi:

                    null

                url:

                    "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"

              population:

                "Homens adultos, n = 87, 18–56 anos"

              criterionMethod:

                "Pesagem hidrostática"

              metrics:

                correlation:

                    0.870

                standardError:

                    0.0078

                meanDifference:

                    -0.0003

                rmse:

                    0.0075

                otherMetrics:

                    "EC = -0,0003 g/ml; ET = 0,0075 g/ml; t = -0,397; p > 0,05."

              limitations:

                "Validação independente da mesma pesquisa e população de origem."

        crossValidationStudies:

            []

        externalValidationStudies:

            []

        sourceConflict:

            null

    inputs:

        requiredInputs:

            - AGE

            - SKINFOLD_SUBSCAPULAR

            - SKINFOLD_TRICEPS

            - SKINFOLD_PECTORAL

            - SKINFOLD_AXILLARY_MID

            - SKINFOLD_SUPRAILIAC

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

A ficha consolidada reúne os dados das seções 1–16 e não adiciona campos derivados de runtime.

---

## 18. Interpretação para o SuggestionEngine

```text
sex:

    MALE → compatível com a aplicabilidade documentada

    FEMALE → incompatível com esta variante masculina

age:

    faixa da população de desenvolvimento = 18–66

    faixa da população de validação = 18–56

    média da amostra de desenvolvimento = 30.17

    média não é limite de elegibilidade

athlete:

    NOT_DOCUMENTED

trainingLevel:

    NOT_DOCUMENTED

modality:

    NOT_DOCUMENTED

required mathematical data:

    AGE

    7 skinfolds

output:

    BODY_DENSITY
```

O motor não deve inferir incompatibilidade com atleta, nível de treinamento ou modalidade esportiva pela ausência de documentação específica.

A faixa etária observada no desenvolvimento também não deve ser transformada automaticamente em `INELIGIBLE` fora de 18–66 anos, pois `explicitAgeRestriction` permanece `null`.

A P-M3 é matematicamente distinta das variantes que utilizam seis, quatro ou outras quantidades de dobras: sua definição utiliza sete dobras cutâneas e idade, sem circunferências adicionais. A tabela de desenvolvimento de Petroski identifica M3 como o modelo quadrático de sete dobras para homens de 18 a 66 anos.

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

A referência digital corresponde à cópia da tese utilizada para a rastreabilidade documental desta ficha. A revisão de 2007 também reproduz a população masculina de Petroski, a divisão entre regressão e validação e as equações generalizadas masculinas.