# EquationVariantScientificProfile — FALK4

## 1. Identificação

```text
identity:
    variantId:
        "FALK4"
    familyId:
        "faulkner"
    displayName:
        "Faulkner 4 Skinfolds"
    aliasNames:
        [
            "Faulkner 4",
            "Faulkner 4D",
            "Equação de Faulkner de 4 dobras"
        ]
```

Referência principal:

```text
Faulkner, J. A. (1968).
Physiology of Swimming and Diving.
In: Falls, H. (Ed.).
Exercise Physiology.
```

A denominação histórica da fórmula é preservada. A literatura posterior questiona a atribuição de autoria e relaciona a origem matemática da expressão a uma modificação das equações de Yuhasz. Essa questão é tratada como informação de proveniência histórica e não como alteração da identidade matemática desta variante.

## 2. Definição Matemática

A equação FALK4 é:

```text
%G =
    5.783
    + 0.153 × SUM4
```

Onde:

```text
SUM4 =
    SKINFOLD_TRICEPS
    + SKINFOLD_SUBSCAPULAR
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_ABDOMEN
```

Saída:

```text
outputType:
    BODY_FAT_PERCENTAGE
```

A equação calcula diretamente o percentual de gordura corporal a partir da soma de quatro dobras cutâneas. As quatro medidas são expressas em milímetros.

A expressão `5.783 + 0.153 × SUM4` deve ser mantida como uma variante matemática própria. A forma feminina reproduzida em parte da literatura, `7.9 + 0.213 × SUM4`, possui coeficientes diferentes e não deve ser fundida com FALK4.

A literatura posterior reproduz FALK4 como uma equação de quatro dobras historicamente atribuída a Faulkner e também discute sua relação matemática com o método de Yuhasz.

## 3. Aplicabilidade

### 3.1 Sexo

```text
sex:
    supportedSexes:
        - MALE
```

A forma `5.783 + 0.153 × SUM4` é mantida nesta biblioteca como variante masculina, conforme a decisão científica adotada para FALK4. Não incluir `FEMALE` nesta variante sem evidência primária suficiente de desenvolvimento ou validação específica para mulheres.

### 3.2 Idade

```text
age:
    originalDevelopmentAgeRange:
        null
    developmentSampleMeanAge:
        null
    validatedAgeRanges:
        []
    explicitAgeRestriction:
        null
```

A documentação histórica disponível não estabelece uma faixa etária formal de desenvolvimento da equação que possa ser registrada como limite de elegibilidade da variante. As idades observadas em grupos históricos associados ao contexto de Faulkner permanecem descritivas e não são transformadas em restrição automática.

## 4. Aplicabilidade Populacional

```text
population:
    originalPopulation:
        description:
            "Homens universitários e nadadores universitários associados ao material histórico de Faulkner."
        country:
            "United States"
        region:
            "University of Michigan"
        sexCoverage:
            - MALE
        ageCoverage:
            null
        sampleSize:
            null
        bodyCharacteristicsNotes:
            "O material histórico apresenta grupos masculinos universitários e nadadores universitários, sem estabelecer uma amostra moderna de desenvolvimento por regressão para a equação."
        sampleCharacteristics:
            "Dados históricos associados à estimativa de gordura corporal por quatro dobras cutâneas."
        source:
            Faulkner (1968)
    validationPopulations:
        []
```

O material histórico de Faulkner apresenta grupos universitários e nadadores universitários, mas não fornece uma estrutura de desenvolvimento e validação equivalente à utilizada nas fichas de Petroski. Essas informações descrevem o contexto histórico da equação e não devem ser reinterpretadas como uma matriz moderna de elegibilidade populacional.

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

O contexto histórico inclui nadadores universitários, mas isso não é suficiente para registrar FALK4 como uma equação desenvolvida ou validada exclusivamente em atletas. A literatura histórica também questiona a interpretação de que a fórmula teria sido criada especificamente para nadadores.

## 6. Aplicabilidade por Nível de Treinamento

```text
trainingLevel:
    supportedLevels:
        []
    notes:
        "Não documentado segundo a escala operacional da plataforma."
```

Não há evidência suficiente para atribuir diretamente uma das categorias `SEDENTARY`, `RECREATIONAL`, `TRAINED`, `COMPETITIVE` ou `ELITE` à variante.

## 7. Aplicabilidade por Modalidade

```text
modality:
    supportedModalities:
        []
    notes:
        "O contexto histórico inclui natação, mas a equação não é tratada como específica de uma modalidade esportiva."
```

A associação histórica com natação não é convertida em restrição de modalidade.

## 8. Características Corporais

```text
bodyCharacteristics:
    rules:
        []
```

Não foi identificada regra explícita de elegibilidade baseada em percentual de gordura, massa corporal, estatura ou outra característica corporal para esta variante.

## 9. Evidências de Validação

### 9.1 Evidências de Desenvolvimento

```text
development:
    studyReference:
        citation:
            "Faulkner, J. A. (1968).
             Physiology of Swimming and Diving.
             In: Falls, H. (Ed.).
             Exercise Physiology."
        doi:
            null
        url:
            null
    population:
        "Homens universitários e nadadores universitários associados ao material histórico de Faulkner"
    criterionMethod:
        "Não documentado de forma suficiente para caracterizar uma regressão de desenvolvimento moderna."
    year:
        1968
    metrics:
        R:
            null
        R2:
            null
        standardError:
            null
```

A documentação histórica não apresenta `R`, `R²` ou erro padrão de estimativa como métricas de uma regressão de desenvolvimento da forma exigida para uma ficha moderna. A expressão aparece no contexto histórico da estimativa de gordura corporal por quatro dobras.

A análise histórica posterior conclui que a chamada equação de Faulkner deve ser entendida como uma expressão relacionada às equações de Yuhasz, questionando a atribuição tradicional de autoria. Essa conclusão é registrada como questão de proveniência histórica, sem alterar a expressão matemática de FALK4.

## 10. Validação

```text
validationStudies:
    []
```

Não foi identificada uma subamostra independente do mesmo estudo, explicitamente apresentada como validação da expressão FALK4 com métricas próprias equivalentes às utilizadas nas fichas de Petroski.

Os grupos históricos apresentados no contexto de Faulkner não devem ser classificados automaticamente como uma etapa formal de validação da equação.

## 11. Validação Cruzada

```text
crossValidationStudies:
    []
```

Não foi identificado estudo específico que possa ser classificado como validação cruzada da forma exata `5.783 + 0.153 × SUM4` no material utilizado para esta ficha.

A análise que relaciona FALK4 matematicamente às equações de Yuhasz é uma análise de proveniência e reconstrução histórica, não uma validação cruzada da variante.

## 12. Validação Externa

```text
externalValidationStudies:
    []
```

Não foi identificada validação externa formal da expressão exata de FALK4 suficientemente caracterizada para registro nesta ficha.

Aplicações posteriores em atletas ou em outras populações constituem evidência de uso, mas não devem ser convertidas automaticamente em validação externa.

## 13. Requisitos de Medição

```text
inputs:
    requiredInputs:
        - SKINFOLD_TRICEPS
        - SKINFOLD_SUBSCAPULAR
        - SKINFOLD_SUPRAILIAC
        - SKINFOLD_ABDOMEN
    optionalInputs:
        []
```

Entrada matemática:

```text
SUM4
```

Onde:

```text
SUM4 =
    SKINFOLD_TRICEPS
    + SKINFOLD_SUBSCAPULAR
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_ABDOMEN
```

`AGE` não é variável matemática da equação e, portanto, não faz parte de `requiredInputs`.

`SEX` não é variável matemática e permanece como propriedade de aplicabilidade da variante.

As quatro dobras são expressas em milímetros segundo a forma documentada da equação.

Definições gerais de unidades, precisão e faixas plausíveis permanecem em:

```text
/library/measurements
```

## 14. Restrições Científicas

```text
restrictions:
    []
```

Não foi identificada restrição científica adicional que deva produzir `INELIGIBLE` por si só.

A ausência de uma faixa etária formal de desenvolvimento, de uma regra específica para atletas e de uma restrição de modalidade permanece como ausência documentada, e não como restrição implícita.

O agregado `SUM4` deve ser válido antes do cálculo do percentual de gordura.

## 15. Conflito de Fonte

```text
sourceConflict:
    null
```

Não foi mantido `sourceConflict` porque a diferença entre `5.783 + 0.153 × SUM4` e `7.9 + 0.213 × SUM4` representa conjuntos de coeficientes distintos associados a formas sexuais diferentes na literatura, e não dois valores concorrentes para o mesmo parâmetro da mesma variante.

A questão histórica da autoria também não é tratada como `sourceConflict`, pois corresponde a uma questão de proveniência/nomenclatura, e não a divergência de valores do mesmo parâmetro científico da mesma variante.

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

Checklist de campos essenciais:

```text
identity                       CONFIRMED
mathematicalDefinition         CONFIRMED
supportedSexes                 CONFIRMED
originalDevelopmentAgeRange    DOCUMENTED_AS_NULL
originalPopulation             DOCUMENTED
requiredInputs                 CONFIRMED
definitionReference            CONFIRMED
sourceConflict                 null
```

A variante permanece `ACTIVE` no inventário, com `MALE` como sexo suportado. A proveniência histórica questionada é documentada sem transformar essa questão em conflito estrutural ou em mudança da identidade matemática da variante.

## 17. Ficha Consolidada

```text
EquationVariantScientificProfile
    identity:
        variantId:
            "FALK4"
        familyId:
            "faulkner"
        displayName:
            "Faulkner 4 Skinfolds"
        aliasNames:
            [
                "Faulkner 4",
                "Faulkner 4D",
                "Equação de Faulkner de 4 dobras"
            ]
    applicability:
        sex:
            supportedSexes:
                - MALE
        age:
            originalDevelopmentAgeRange:
                null
            developmentSampleMeanAge:
                null
            validatedAgeRanges:
                []
            explicitAgeRestriction:
                null
        population:
            originalPopulation:
                description:
                    "Homens universitários e nadadores universitários associados ao material histórico de Faulkner."
                country:
                    "United States"
                region:
                    "University of Michigan"
                sexCoverage:
                    - MALE
                ageCoverage:
                    null
                sampleSize:
                    null
                bodyCharacteristicsNotes:
                    "O material histórico apresenta grupos masculinos universitários e nadadores universitários, sem estabelecer uma amostra moderna de desenvolvimento por regressão para a equação."
                sampleCharacteristics:
                    "Dados históricos associados à estimativa de gordura corporal por quatro dobras cutâneas."
                source:
                    Faulkner (1968)
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
                "O contexto histórico inclui natação, mas não estabelece uma restrição de modalidade."
        bodyCharacteristics:
            rules:
                []
    evidence:
        development:
            studyReference:
                citation:
                    "Faulkner, J. A. (1968).
                     Physiology of Swimming and Diving.
                     In: Falls, H. (Ed.).
                     Exercise Physiology."
                doi:
                    null
                url:
                    null
            population:
                "Homens universitários e nadadores universitários associados ao material histórico de Faulkner"
            criterionMethod:
                "Não documentado de forma suficiente para caracterizar uma regressão de desenvolvimento moderna."
            year:
                1968
            metrics:
                R:
                    null
                R2:
                    null
                standardError:
                    null
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
            - SKINFOLD_TRICEPS
            - SKINFOLD_SUBSCAPULAR
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

## 18. Interpretação para o SuggestionEngine

O perfil científico permite avaliar em runtime:

```text
sex:
    MALE → compatível com o sexo documentado

age:
    sem faixa etária formal de desenvolvimento documentada

athlete:
    NOT_DOCUMENTED

trainingLevel:
    NOT_DOCUMENTED

modality:
    NOT_DOCUMENTED

bodyCharacteristics:
    no explicit rule

inputs:
    4 skinfolds

output:
    BODY_FAT_PERCENTAGE
```

FALK4 é uma fórmula de saída direta de `BODY_FAT_PERCENTAGE`:

```text
4 skinfolds
    ↓
FALK4
    ↓
BODY_FAT_PERCENTAGE
```

Não há etapa de conversão de `BODY_DENSITY` por Siri ou Brozek dentro da própria variante.

A expressão exata a ser executada é:

```text
%G = 5.783 + 0.153 × SUM4
```

com:

```text
SUM4 =
    SKINFOLD_TRICEPS
    + SKINFOLD_SUBSCAPULAR
    + SKINFOLD_SUPRAILIAC
    + SKINFOLD_ABDOMEN
```

O `SuggestionEngine` deve manter FALK4 matematicamente distinta da forma feminina `7.9 + 0.213 × SUM4`. As duas expressões não são a mesma `EquationVariant`.

A associação histórica com nadadores não deve produzir preferência automática por modalidade ou nível de treinamento.

A principal informação de runtime é o conjunto exato das quatro entradas, a saída direta em percentual de gordura e a aplicabilidade ao sexo `MALE` definida para esta variante.

A denominação “Faulkner” permanece disponível por compatibilidade histórica, mas a documentação científica preserva a ressalva de que a origem matemática da expressão é questionada e relacionada às equações de Yuhasz.

## 19. Referência

```text
Reference
    citation:
        "Faulkner, J. A. (1968).
         Physiology of Swimming and Diving.
         In: Falls, H. (Ed.).
         Exercise Physiology."
    doi:
        null
    url:
        null
```

Fonte bibliográfica adicional:

```text
Reference
    citation:
        "Pires Neto, C. S.; Glaner, M. F. (2007).
         “Equação de Faulkner” para predizer a gordura corporal: o fim de um mito.
         Revista Brasileira de Cineantropometria & Desempenho Humano, 9(2), 207–213."
    doi:
        null
    url:
        "https://periodicos.ufsc.br/index.php/rbcdh/article/download/4065/3440/12342"
```

Fonte bibliográfica adicional de aplicação e contextualização:

```text
Reference
    citation:
        "Dimitrijevic, A.; et al. (2022).
         Body Fat Evaluation in Male Athletes from Combat Sports by Comparing Anthropometric, Bioimpedance, and Dual-Energy X-Ray Absorptiometry Measurements."
    doi:
        "10.1155/2022/3456958"
    url:
        "https://onlinelibrary.wiley.com/doi/10.1155/2022/3456958"
```