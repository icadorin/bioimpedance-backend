**# EquationVariantScientificProfile — P-F3**

**## 1. Identification**

\`\`\`text

identity:

    variantId:

        "P-F3"

    familyId:

        "petroski"

    displayName:

        "Petroski F3 — Feminino"

    aliasNames:

        [

            "Petroski F3",

            "Equação F3 de Petroski"

        ]

\`\`\`

Referência principal:

\`\`\`text

Petroski, E. L. (1995).

Desenvolvimento e validação de equações generalizadas para a estimativa

da densidade corporal em adultos.

Tese de Doutorado.

Universidade Federal de Santa Maria (UFSM),

Santa Maria, RS, Brasil.

\`\`\`

**## 2. Definição Matemática**

A equação P-F3 é:

\`\`\`text

D =

    1.22219652

    - 0.06681170 × LOG10(X9)

    - 0.00035407 × AGE

    - 0.00041834 × CIRCUMFERENCE\_THIGH

\`\`\`

Onde:

\`\`\`text

X9 =

    SKINFOLD\_SUBSCAPULAR

    + SKINFOLD\_TRICEPS

    + SKINFOLD\_BICEPS

    + SKINFOLD\_PECTORAL

    + SKINFOLD\_AXILLARY\_MID

    + SKINFOLD\_SUPRAILIAC

    + SKINFOLD\_ABDOMEN

    + SKINFOLD\_THIGH

    + SKINFOLD\_MEDIAL\_CALF

\`\`\`

E:

\`\`\`text

CIRCUMFERENCE\_THIGH =

    circumferência da coxa

\`\`\`

Saída:

\`\`\`text

outputType:

    BODY\_DENSITY

\`\`\`

A P-F3 utiliza nove dobras cutâneas, idade e circunferência da coxa, com a soma das nove dobras entrando no modelo por meio de \`LOG10(X9)\`.

**## 3. Aplicabilidade**

**### 3.1 Sex**

\`\`\`text

sex:

    supportedSexes:

        - FEMALE

\`\`\`

**### 3.2 Age**

\`\`\`text

age:

    originalDevelopmentAgeRange:

        min: 18

        max: 51

    developmentSampleMeanAge:

        27.46

    validatedAgeRanges:

        - range:

            min: 18

            max: 43

          population:

            "Mulheres adultas da amostra independente de validação"

          source:

            Petroski (1995)

    explicitAgeRestriction:

        null

\`\`\`

A amostra feminina de desenvolvimento continha 213 mulheres entre 18 e 51 anos, com média de idade de 27,46 anos. A amostra independente de validação continha 68 mulheres entre 18 e 43 anos, com média de idade de 27,18 anos.

As médias de idade são descritivas e não devem ser convertidas em limites de elegibilidade em runtime.

**## 4. Aplicabilidade Populacional**

\`\`\`text

population:

    originalPopulation:

        description:

            "Mulheres adultas"

        country:

            "Brazil"

        region:

            "Região central do Rio Grande do Sul e região litorânea de Santa Catarina"

        sexCoverage:

            - FEMALE

        ageCoverage:

            min: 18

            max: 51

        sampleSize:

            213

        bodyCharacteristicsNotes:

            "Amostra feminina heterogênea em idade e gordura corporal."

        sampleCharacteristics:

            "Amostra feminina de regressão utilizada no desenvolvimento

             das equações generalizadas de Petroski."

        source:

            Petroski (1995)

    validationPopulations:

        - description:

            "Mulheres adultas da amostra independente de validação"

          country:

            "Brazil"

          region:

            "Região central do Rio Grande do Sul e região litorânea de Santa Catarina"

          sexCoverage:

            - FEMALE

          ageCoverage:

            min: 18

            max: 43

          sampleSize:

            68

          bodyCharacteristicsNotes:

            null

          sampleCharacteristics:

            "Amostra independente utilizada para validar as equações generalizadas femininas."

          source:

            Petroski (1995)

\`\`\`

O estudo contou com 281 mulheres no total, divididas em uma amostra de regressão com 213 participantes e uma amostra de validação com 68 participantes. A amostra de regressão abrangia 18–51 anos, 43,80–87,40 kg de massa corporal, 143,00–177,10 cm de estatura e 11,11–36,18% de gordura corporal.

**## 5. Aplicabilidade em Atletas**

\`\`\`text

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

\`\`\`

Não há classificação suficientemente explícita de atleta/não atleta, compatível com o modelo booleano da plataforma, atribuída às amostras de desenvolvimento ou validação da P-F3.

**## 6. Aplicabilidade por Nível de Treinamento**

\`\`\`text

trainingLevel:

    supportedLevels:

        []

    notes:

        "Não documentado segundo a escala operacional da plataforma."

\`\`\`

**## 7. Aplicabilidade por Modalidade**

\`\`\`text

modality:

    supportedModalities:

        []

    notes:

        "Não documentado em termos de modalidades esportivas específicas."

\`\`\`

**## 8. Características Corporais**

\`\`\`text

bodyCharacteristics:

    rules:

        []

\`\`\`

No explicit P-F3 body-characteristic eligibility rule is documented.

As faixas observadas no estudo descrevem a população de origem e não são tratadas como restrições rígidas de runtime.

**## 9. Evidências de Validação**

**### 9.1 Evidências de Desenvolvimento**

\`\`\`text

development:

    studyReference:

        citation:

            "Petroski, E. L. (1995).

             Desenvolvimento e validação de equações generalizadas

             para a estimativa da densidade corporal em adultos.

             Tese de Doutorado.

             Universidade Federal de Santa Maria."

        doi:

            null

        url:

            "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"

    population:

        "Mulheres adultas, n = 213, 18–51 anos"

    criterionMethod:

        "Densidade corporal determinada por pesagem hidrostática"

    year:

        1995

    metrics:

        R:

            0.840

        R2:

            0.706

        standardError:

            0.0069

\`\`\`

For P-F3, Petroski reported \`R = 0.840\`, \`R² = 0.706\` and \`EPE = 0.0069 g/ml\`.

**## 10. Validation**

A amostra independente de validação foi:

\`\`\`text

n:

    68 women

age:

    18–43 years

\`\`\`

Criterion method:

\`\`\`text

hydrostatic weighing

\`\`\`

For P-F3:

\`\`\`text

correlation:

    0.729

constantError:

    -0.00002 g/ml

totalError:

    0.0072 g/ml

standardErrorOfEstimate:

    0.0070 g/ml

\`\`\`

A tabela de validação apresenta densidade estimada média de \`1,046366 ± 0,0095 g/ml\`, correlação \`r = 0,729\`, \`t = -0,022\`, \`EC = -0,00002 g/ml\`, \`ET = 0,0072 g/ml\` e \`EPE = 0,0070 g/ml\`.

**## 11. Validação Cruzada**

\`\`\`text

crossValidationStudies:

    []

\`\`\`

A amostra independente de 68 mulheres está registrada em \`validationStudies\`, não em \`crossValidationStudies\`.

A tese contém análises separadas de validação cruzada, mas essas análises se referem a equações de outros investigadores e não constituem um estudo adicional de validação cruzada específico da P-F3.

**## 12. Validação Externa**

\`\`\`text

externalValidationStudies:

    []

\`\`\`

No external validation study of P-F3 outside the original Petroski investigation is included in this profile.

**## 13. Requisitos de Medição**

\`\`\`text

inputs:

    requiredInputs:

        - AGE

        - SKINFOLD\_SUBSCAPULAR

        - SKINFOLD\_TRICEPS

        - SKINFOLD\_BICEPS

        - SKINFOLD\_PECTORAL

        - SKINFOLD\_AXILLARY\_MID

        - SKINFOLD\_SUPRAILIAC

        - SKINFOLD\_ABDOMEN

        - SKINFOLD\_THIGH

        - SKINFOLD\_MEDIAL\_CALF

        - CIRCUMFERENCE\_THIGH

    optionalInputs:

        []

\`\`\`

Mathematical inputs:

\`\`\`text

AGE

X9

CIRCUMFERENCE\_THIGH

\`\`\`

where:

\`\`\`text

X9 =

    SKINFOLD\_SUBSCAPULAR

    + SKINFOLD\_TRICEPS

    + SKINFOLD\_BICEPS

    + SKINFOLD\_PECTORAL

    + SKINFOLD\_AXILLARY\_MID

    + SKINFOLD\_SUPRAILIAC

    + SKINFOLD\_ABDOMEN

    + SKINFOLD\_THIGH

    + SKINFOLD\_MEDIAL\_CALF

\`\`\`

and:

\`\`\`text

LOG10(X9)

\`\`\`

is used in the mathematical equation.

\`SEX\` is not included in \`requiredInputs\`; it determines applicability to the female variant.

Segundo a notação da tese, \`CCX\` corresponde à circunferência da coxa e é expressa em centímetros; as dobras cutâneas são expressas em milímetros e a idade em anos.

General definitions of units, precision and plausible ranges remain in:

\`\`\`text

/library/measurements

\`\`\`

**## 14. Restrições Científicas**

\`\`\`text

restrictions:

    []

\`\`\`

Não foi identificada restrição científica adicional explicitamente formulada para produzir \`INELIGIBLE\`.

As faixas observadas de idade, massa corporal, estatura e percentual de gordura são características descritivas da amostra, e não regras explícitas de elegibilidade.

**## 15. Conflito de Fonte**

\`\`\`text

sourceConflict:

    null

\`\`\`

Não foi identificado conflito material para a definição matemática da P-F3 ou para as estatísticas reportadas de desenvolvimento e validação independente na fonte utilizada.

**## 16. Ciclo de Vida**

\`\`\`text

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

\`\`\`

Informações essenciais:

\`\`\`text

identity                      CONFIRMADO

mathematicalDefinition        CONFIRMADO

supportedSexes                CONFIRMADO

originalDevelopmentAgeRange   CONFIRMADO

originalPopulation            CONFIRMADO

requiredInputs                CONFIRMADO

definitionReference           CONFIRMADO

sourceConflict                null

\`\`\`

**## 17. Ficha Consolidada**

\`\`\`text

EquationVariantScientificProfile

    identity:

        variantId:

            "P-F3"

        familyId:

            "petroski"

        displayName:

            "Petroski F3 — Feminino"

        aliasNames:

            [

                "Petroski F3",

                "Equação F3 de Petroski"

            ]

    applicability:

        sex:

            supportedSexes:

                - FEMALE

        age:

            originalDevelopmentAgeRange:

                min: 18

                max: 51

            developmentSampleMeanAge:

                27.46

            validatedAgeRanges:

                - range:

                    min: 18

                    max: 43

                  population:

                    "Mulheres adultas da amostra independente de validação"

                  source:

                    Petroski (1995)

            explicitAgeRestriction:

                null

        population:

            originalPopulation:

                description:

                    "Mulheres adultas"

                country:

                    "Brazil"

                region:

                    "Região central do Rio Grande do Sul e região litorânea de Santa Catarina"

                sexCoverage:

                    - FEMALE

                ageCoverage:

                    min: 18

                    max: 51

                sampleSize:

                    213

                bodyCharacteristicsNotes:

                    "Amostra feminina heterogênea em idade e gordura corporal."

                sampleCharacteristics:

                    "Amostra feminina de regressão utilizada no desenvolvimento

                     das equações generalizadas."

                source:

                    Petroski (1995)

            validationPopulations:

                - description:

                    "Mulheres adultas da amostra independente de validação"

                  country: "Brasil"

                  region:

                    "Região central do Rio Grande do Sul e região litorânea de Santa Catarina"

                  sexCoverage:

                      - FEMALE

                  ageCoverage:

                      min: 18

                      max: 43

                  sampleSize: 68

                  bodyCharacteristicsNotes: null

                  sampleCharacteristics:

                      "Amostra independente utilizada para validar as equações."

                  source:

                      Petroski (1995)

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

                Petroski (1995)

            population:

                "Mulheres adultas, n = 213, 18–51 anos"

            criterionMethod:

                "Pesagem hidrostática"

            year:

                1995

            metrics:

                R: 0.840

                R2: 0.706

                standardError: 0.0069

        validationStudies:

            - studyReference:

                Petroski (1995)

              population:

                "Mulheres adultas, n = 68, 18–43 anos"

              criterionMethod:

                "Pesagem hidrostática"

              metrics:

                correlation:

                    0.729

                standardError:

                    0.0070

                meanDifference:

                    -0.00002

                rmse:

                    null

                otherMetrics:

                    "EC = -0.00002 g/ml; ET = 0.0072 g/ml;

                     EPE = 0.0070 g/ml."

                limitations:

                    null

        crossValidationStudies:

            []

        externalValidationStudies:

            []

        sourceConflict:

            null

    inputs:

        requiredInputs:

            - AGE

            - SKINFOLD\_SUBSCAPULAR

            - SKINFOLD\_TRICEPS

            - SKINFOLD\_BICEPS

            - SKINFOLD\_PECTORAL

            - SKINFOLD\_AXILLARY\_MID

            - SKINFOLD\_SUPRAILIAC

            - SKINFOLD\_ABDOMEN

            - SKINFOLD\_THIGH

            - SKINFOLD\_MEDIAL\_CALF

            - CIRCUMFERENCE\_THIGH

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

\`\`\`

**## 18. Interpretação para o SuggestionEngine**

O perfil científico permite a avaliação em runtime de:

\`\`\`text

sex:

    FEMALE → compatível com o sexo documentado

age:

    comparar com a evidência populacional documentada

    sem utilizar 27,46 como limite de elegibilidade

athlete:

    NOT\_DOCUMENTED

trainingLevel:

    NOT\_DOCUMENTED

modality:

    NOT\_DOCUMENTED

bodyCharacteristics:

    nenhuma regra explícita

inputs:

    AGE

    + 9 dobras cutâneas

    + circunferência da coxa

output:

    BODY\_DENSITY

\`\`\`

O motor não deve transformar a média de idade do desenvolvimento em limite de elegibilidade, e a ausência de evidência sobre atleta, nível de treinamento ou modalidade não deve resultar em \`INELIGIBLE\`.

A P-F3 é o **\*\*modelo logarítmico de nove dobras com idade e circunferência da coxa\*\***. Em relação à P-F2, o único preditor adicional é \`CIRCUMFERENCE\_THIGH\`; a estrutura \`LOG10(X9)\` é mantida.

**## 19. Reference**

\`\`\`text

Reference

    citation:

        "Petroski, E. L. (1995).

         Desenvolvimento e validação de equações generalizadas

         para a estimativa da densidade corporal em adultos.

         Tese de Doutorado.

         Universidade Federal de Santa Maria,

         Santa Maria, RS, Brasil."

    doi:

        null

    url:

        "https://pt.scribd.com/document/8955761/Tese-Edio-Petroski"

\`\`\`

Fonte bibliográfica adicional:

\`\`\`text

"Centro Esportivo Virtual — Desenvolvimento e Validação de Equações

Generalizadas Para a Estimativa da Densidade Corporal em Adultos."

url:

    "https://www.cev.org.br/biblioteca/desenvolvimento-validacao-equacoes-generalizadas-para-estimativa-densidade-corporal-adultos/"

\`\`\`