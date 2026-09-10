# EquationVariantScientificProfile — P-M4

## 1. Identificação

```text
identity:

    variantId:

        "P-M4"

    familyId:

        "petroski"

    displayName:

        "Petroski M4 — Masculino"

    aliasNames:

        []
```

Referência principal:

```text
Petroski, Edio Luiz (1995).

Desenvolvimento e validação de equações generalizadas para a estimativa da

densidade corporal em adultos.

Tese de Doutorado, Programa de Pós-Graduação em Ciência do Movimento Humano,

Universidade Federal de Santa Maria, Santa Maria, RS, Brasil.
```

A variante P-M4 corresponde ao modelo masculino M4 das equações generalizadas de Petroski (1995), apresentado na Tabela 9 da tese.

---

## 2. Definição Matemática

```text
mathematicalDefinition:

    formula:

        D =
            1.08566598
            - 0.00032750 × X7
            + 0.00000036 × X7²
            - 0.00017521 × AGE
            + 0.00161816 × CIRCUMFERENCE_FOREARM
            - 0.00041043 × CIRCUMFERENCE_ABDOMEN

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
            1.08566598
            - 0.00032750 * X7
            + 0.00000036 * X7^2
            - 0.00017521 * AGE
            + 0.00161816 * CIRCUMFERENCE_FOREARM
            - 0.00041043 * CIRCUMFERENCE_ABDOMEN
```

A Tabela 9 da tese identifica M4 como um modelo que utiliza sete dobras cutâneas, idade, circunferência do antebraço e circunferência do abdômen. Os coeficientes documentados para M4 são `1.08566598`, `-0.00032750`, `0.00000036`, `-0.00017521`, `0.00161816` e `-0.00041043`.

O `X7` é definido na própria tese como o somatório de sete dobras: subescapular, tríceps, peitoral, axilar média, supra-ilíaca, abdominal e coxa.

Para a ficha, foram aplicadas as nomenclaturas canônicas do produto: `SKINFOLD_PECTORAL`, `SKINFOLD_AXILLARY_MID` e `SKINFOLD_ABDOMEN`.

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

A amostra masculina de desenvolvimento continha 304 homens, com idade entre 18 e 66 anos e média de 30,17 ± 9,78 anos. A amostra masculina de validação continha 87 homens, com idade entre 18 e 56 anos e média de 30,68 ± 9,11 anos.

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

            "Amostra de desenvolvimento com heterogeneidade de idade e gordura corporal. Percentual de gordura corporal de 2,20%–33,16%, média de 16,14 ± 6,86%."

        sampleCharacteristics:

            "Amostra de 304 homens utilizada para o desenvolvimento das equações generalizadas."

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

            "Percentual de gordura corporal de 3,11%–33,12%, média de 15,83 ± 6,72%."

          sampleCharacteristics:

            "Amostra de validação independente oriunda da mesma população de estudo."

          source:

            "Petroski (1995)"
```

O estudo foi realizado com 672 participantes, sendo 391 homens. A amostra masculina foi subdividida em 304 homens para regressão e 87 para validação; a própria tese descreve a segunda como amostra de validação utilizada para validar as equações masculinas.

A Tabela 8 confirma, para os homens, a faixa etária de 18–66 anos na regressão e 18–56 anos na validação, além dos intervalos de gordura corporal correspondentes.

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

A tese descreve uma amostra de voluntários e registra critérios relacionados à adaptação ao meio líquido exigida pela pesagem hidrostática, mas não fornece uma classificação compatível com os campos binários de atleta e não atleta adotados pela plataforma. A ausência dessa classificação permanece `null` e não é transformada em incompatibilidade.

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

A amostra de desenvolvimento foi descrita como heterogênea em idade e gordura corporal, com percentual de gordura de 2,20% a 33,16%. Essa informação é mantida como característica populacional e não é convertida automaticamente em `BodyCharacteristicRule`.

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

        "Homens adultos, n = 304, 18–66 anos, das regiões central do Rio Grande do Sul e litorânea de Santa Catarina."

    criterionMethod:

        "Densidade corporal determinada por pesagem hidrostática."

    year:

        1995

    metrics:

        R:

            0.892

        R2:

            0.795

        standardError:

            0.0071
```

A Tabela 9 da tese apresenta para M4 `R = 0,892`, `R² = 0,795` e erro padrão de estimativa de `0,0071 g/ml`.

A densidade corporal de referência foi determinada por pesagem hidrostática. A tese informa que a amostra de regressão masculina continha 304 participantes.

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

            "Percentual de gordura corporal de 3,11%–33,12%, média de 15,83 ± 6,72%."

        sampleCharacteristics:

            "Amostra de validação independente oriunda da mesma população de estudo."

        source:

            "Petroski (1995)"

      criterionMethod:

        "Pesagem hidrostática."

      metrics:

        correlation:

            0.873

        standardError:

            0.0072

        meanDifference:

            -0.0003

        rmse:

            null

        otherMetrics:

            "Densidade média estimada = 1,06251 g/ml; densidade média medida = 1,06282 g/ml; t = -0,386; erro constante (EC) = -0,0003 g/ml; erro total (ET) = 0,0074 g/ml."

      limitations:

        "Validação realizada em amostra independente de 87 homens, oriunda da mesma população do estudo original; não corresponde a validação externa."
```

A Tabela 10 informa para M4 correlação `r = 0,873`, `t = -0,386`, `EC = -0,0003 g/ml`, `ET = 0,0074 g/ml` e `EPE = 0,0072 g/ml`; a densidade média estimada foi 1,06251 g/ml e a densidade média medida foi 1,06282 g/ml.

A classificação como `validationStudies` é a adequada segundo o padrão operacional: a própria tese descreve os 87 homens como uma amostra de validação independente, oriunda da mesma população do estudo, usada para validar as equações masculinas.

Como consequência dessa correção, o valor de `standardError` da validação é mantido em `0.0072`, correspondente ao EPE da Tabela 10; `0.0074` é o ET e não deve ocupar o campo `standardError`.

---

## 11. Validação Cruzada

```text
crossValidationStudies:

    []
```

A tese possui uma seção própria de validação cruzada, mas essa análise testa equações de outros pesquisadores na amostra masculina de Petroski. Ela não constitui um estudo de validação cruzada da própria variante P-M4. Portanto, nenhum resultado externo é atribuído a `P-M4` nesta seção.

---

## 12. Validação Externa

```text
externalValidationStudies:

    []
```

Não foi incorporado neste perfil estudo totalmente independente de validação externa da variante P-M4.

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

        - CIRCUMFERENCE_FOREARM

        - CIRCUMFERENCE_ABDOMEN

    optionalInputs:

        []
```

`SEX` não é input matemático e permanece exclusivamente em `applicability.sex`.

`AGE` é `requiredInput` porque participa diretamente da equação. A tese identifica `ID` como idade em anos e `X7` como o somatório das sete dobras cutâneas. Também identifica `CAT` como circunferência do antebraço em centímetros e `CAB` como circunferência do abdômen em centímetros.

A correspondência canônica utilizada no perfil é:

```text
X7
    → SKINFOLD_SUBSCAPULAR
    → SKINFOLD_TRICEPS
    → SKINFOLD_PECTORAL
    → SKINFOLD_AXILLARY_MID
    → SKINFOLD_SUPRAILIAC
    → SKINFOLD_ABDOMEN
    → SKINFOLD_THIGH

ID
    → AGE

CAT
    → CIRCUMFERENCE_FOREARM

CAB
    → CIRCUMFERENCE_ABDOMEN
```

As unidades da definição original são:

```text
dobras cutâneas → mm
circunferências → cm
idade → anos
densidade corporal → g/ml
```

A tese também informa que as dobras cutâneas foram mensuradas com procedimentos padronizados, no lado direito do corpo, em três repetições sucessivas, utilizando-se a média ou dois valores coincidentes.

---

## 14. Restrições Científicas

```text
restrictions:

    []
```

Não foi identificada restrição científica adicional explicitamente documentada para a M4 além da aplicabilidade ao sexo masculino e das características da população de desenvolvimento e validação.

A faixa de 18–66 anos corresponde à faixa etária observada na amostra de desenvolvimento, mas não é convertida automaticamente em `ScientificRestriction`. O perfil mantém `explicitAgeRestriction: null`, em conformidade com o padrão operacional.

---

## 15. Conflito de Fonte

```text
sourceConflict:

    null
```

Não foi identificado conflito material entre fontes primárias para os coeficientes, a definição matemática ou as métricas de desenvolvimento e validação da variante P-M4 nas fontes examinadas. A definição matemática da M4 e seus indicadores de desenvolvimento aparecem de forma consistente na tese.

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

A variante possui os elementos essenciais necessários para permanecer com `status: ACTIVE`.

---

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile:

    identity:

        variantId:

            "P-M4"

        familyId:

            "petroski"

        displayName:

            "Petroski M4 — Masculino"

        aliasNames:

            []

    mathematicalDefinition:

        formula:

            "D = 1.08566598 - 0.00032750 × X7 + 0.00000036 × X7² - 0.00017521 × AGE + 0.00161816 × CIRCUMFERENCE_FOREARM - 0.00041043 × CIRCUMFERENCE_ABDOMEN"

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

                "Percentual de gordura corporal de 2,20%–33,16%, média de 16,14 ± 6,86%."

            sampleCharacteristics:

                "Amostra de homens adultos utilizada para o desenvolvimento das equações generalizadas."

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

                "Percentual de gordura corporal de 3,11%–33,12%, média de 15,83 ± 6,72%."

              sampleCharacteristics:

                "Amostra de validação independente oriunda da mesma população de estudo."

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

                    "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado, Programa de Pós-Graduação em Ciência do Movimento Humano, Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

                doi:

                    null

                url:

                    "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"

            population:

                "Homens adultos, n = 304, 18–66 anos"

            criterionMethod:

                "Densidade corporal determinada por pesagem hidrostática."

            year:

                1995

            metrics:

                R:

                    0.892

                R2:

                    0.795

                standardError:

                    0.0071

        validationStudies:

            - studyReference:

                citation:

                    "Petroski, E. L. (1995). Desenvolvimento e validação de equações generalizadas para a estimativa da densidade corporal em adultos. Tese de Doutorado, Programa de Pós-Graduação em Ciência do Movimento Humano, Universidade Federal de Santa Maria, Santa Maria, RS, Brasil."

                doi:

                    null

                url:

                    "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"

              population:

                "Homens adultos, n = 87, 18–56 anos"

              criterionMethod:

                "Pesagem hidrostática."

              metrics:

                correlation:

                    0.873

                standardError:

                    0.0072

                meanDifference:

                    -0.0003

                rmse:

                    null

                otherMetrics:

                    "Densidade média estimada = 1,06251 g/ml; densidade média medida = 1,06282 g/ml; t = -0,386; EC = -0,0003 g/ml; ET = 0,0074 g/ml."

              limitations:

                "Validação interna em amostra independente oriunda da mesma população do estudo original."

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

            - CIRCUMFERENCE_FOREARM

            - CIRCUMFERENCE_ABDOMEN

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

A ficha consolidada preserva a estrutura científica das seções anteriores e corrige, em particular, a representação de `studyReference`, que permanece como objeto `Reference` completo.

---

## 18. Interpretação para o SuggestionEngine

```text
sex:

    MALE → aplicabilidade documentada

    FEMALE → incompatível com esta variante específica para homens

age:

    faixa de desenvolvimento = 18–66

    faixa de validação = 18–56

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

    forearm circumference

    abdominal circumference

output:

    BODY_DENSITY
```

O motor não deve inferir incompatibilidade com atletas, níveis de treinamento ou modalidades esportivas pela ausência de documentação específica.

A faixa de 18–66 anos deve ser tratada como faixa da população de desenvolvimento, não como proibição automática de uso fora dela, porque `explicitAgeRestriction` permanece `null`.

Da mesma forma, `developmentSampleMeanAge = 30.17` é apenas descritivo.

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
