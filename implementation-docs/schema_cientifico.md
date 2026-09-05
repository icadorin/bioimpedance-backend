# Schema Científico — Ficha de Aplicabilidade e Evidência da `EquationVariant`

Este documento define o **modelo de dados científico**, e não sua implementação, que toda `EquationVariant` deve possuir para que o `SuggestionEngine` consiga avaliá-la sem lógica científica hardcoded no motor.

O schema representa somente informações **documentadas ou observadas na literatura**.

O motor de sugestão utiliza essas informações para determinar a compatibilidade de uma variante com determinado perfil, contexto e conjunto de dados.

```text
EquationVariant
 ├─ mathematicalDefinition   → já definido no trabalho anterior
 ├─ inputs                   → ver Seção 4 (MeasurementRequirement)
 ├─ applicability            → ver Seção 2 (ApplicabilityDefinition)
 ├─ evidence                 → ver Seção 3 (ValidationEvidence)
 ├─ restrictions             → ver Seção 5 (ScientificRestriction)
 └─ lifecycle                → ver Seção 6
```

`mathematicalDefinition` permanece em:

```text
/library/equations
```

e contém a definição matemática da variante:

- fórmula;
- coeficientes;
- constantes;
- entradas matemáticas;
- saída;
- demais elementos necessários ao cálculo.

As informações científicas da variante ficam isoladas em:

```text
/library/scientific-rules
```

e são consumidas pelo `SuggestionEngine`.

O `SuggestionEngine` **não deve reescrever, complementar ou inferir essas informações**.

As propriedades gerais de cada tipo de medida não pertencem à variante. Elas ficam em:

```text
/library/measurements
```

---

# Convenção fundamental

Este schema armazena **dados científicos documentados**.

Ele não armazena resultados derivados da comparação com o cliente.

Portanto, campos como:

```text
ageMatch
populationMatch
sexMatch
contextMatch
evidenceCoverage
READY
WARNING
INELIGIBLE
```

**não pertencem à ficha científica**.

Esses valores são derivados em tempo de execução pelo motor, comparando:

```text
ScientificProfile da variante
        +
Perfil/Contexto do cliente
        +
Dados do Assessment
```

---

# Convenções de tipo

```text
String
    texto livre

Enum<X>
    valor pertencente a um conjunto fechado

Array<X>
    lista de valores X

Number
    número

Range<Number>
    { min, max }
    min ou max podem ser null para representar faixa aberta

Boolean
    true / false

Nullable<X>
    X ou ausente
    ausência significa "não documentado/conhecido"

Reference
    referência bibliográfica estruturada

Date
    data de calendário
```

## Regra de ausência

`Nullable` é utilizado deliberadamente.

O sistema deve distinguir:

```text
null
```

de:

```text
false
```

e de:

```text
"documentado como não aplicável"
```

Essas situações não são semanticamente equivalentes.

---

# 1. Identificação

```text
VariantIdentity
    variantId:   String
    familyId:    String
    displayName: String
    aliasNames:  Array<String>
```

## `variantId`

Identificador estável da variante.

Nunca deve ser reutilizado para representar outra definição matemática ou científica.

## `familyId`

Identifica a família à qual a variante pertence.

Exemplos:

```text
jackson-pollock
guedes
petroski
faulkner
```

## `displayName`

Nome utilizado na interface e relatórios.

Exemplo:

```text
Jackson & Pollock 7 Dobras — Masculino
```

## `aliasNames`

Nomes alternativos encontrados na literatura.

---

# 2. ApplicabilityDefinition

Representa os dados documentados sobre **para quem a variante foi desenvolvida ou validada**.

O motor compara esses dados com o perfil do cliente.

A classificação resultante não é armazenada aqui.

---

## 2.1 Sexo

```text
SexApplicability
    supportedSexes: Array<Enum<MALE|FEMALE>>
```

`supportedSexes` deve representar os sexos efetivamente associados à variante pela documentação científica.

Uma lista vazia não é permitida.

### Regra

```text
supportedSexes
```

descreve a população documentada.

Uma incompatibilidade explícita é tratada posteriormente pelo mecanismo de aplicabilidade/restrição.

---

## 2.2 Idade

```text
AgeApplicability
    originalDevelopmentAgeRange: Range<Number>
    developmentSampleMeanAge:    Nullable<Number>
    validatedAgeRanges:           Array<AgeValidationEntry>
    explicitAgeRestriction:       Nullable<Range<Number>>
```

```text
AgeValidationEntry
    range:       Range<Number>
    population:  String
    source:      Reference
```

### `originalDevelopmentAgeRange`

Faixa etária da população que participou do desenvolvimento original da variante.

### `developmentSampleMeanAge`

Média de idade da amostra de desenvolvimento.

É um dado **descritivo**, não normativo.

O motor nunca deve usar:

```text
developmentSampleMeanAge
```

como limite de elegibilidade.

Uma fórmula cuja amostra possui idade média de 30 anos não deve ser interpretada como:

```text
"fórmula para pessoas de 30 anos"
```

### `validatedAgeRanges`

Faixas etárias documentadas em estudos de validação.

Cada entrada deve registrar:

- faixa;
- população correspondente;
- fonte.

Uma variante pode possuir múltiplas entradas.

### `explicitAgeRestriction`

Somente deve ser preenchido quando existir uma restrição explícita de uso documentada na literatura.

Isso é diferente de simplesmente não possuir validação em determinada idade.

---

# 2.3 População

A população deve ser representada separando:

```text
população documentada
```

de:

```text
características da amostra
```

## PopulationApplicability

```text
PopulationApplicability
    originalPopulation:    PopulationProfile
    validationPopulations: Array<PopulationProfile>
```

## PopulationProfile

```text
PopulationProfile
    description:              String
    country:                  Nullable<String>
    region:                   Nullable<String>
    sexCoverage:              Array<Enum<MALE|FEMALE>>
    ageCoverage:              Range<Number>
    sampleSize:               Nullable<Number>
    bodyCharacteristicsNotes: Nullable<String>
    sampleCharacteristics:    Nullable<String>
    source:                   Reference
```

### `description`

Descrição da população.

Exemplo:

```text
Homens adultos
```

### `country`

País associado à população estudada, quando documentado.

### `region`

Região, estado ou área geográfica, quando relevante e documentada.

### `sexCoverage`

Sexo ou sexos presentes na população descrita.

### `ageCoverage`

Faixa etária da população descrita.

### `sampleSize`

Tamanho da amostra, quando conhecido.

### `bodyCharacteristicsNotes`

Características corporais relevantes.

Exemplo:

```text
predominantemente eutróficos
```

### `sampleCharacteristics`

Características específicas da amostra que não devem ser confundidas com a definição geral da população.

Exemplo:

```text
estudantes universitários
```

ou:

```text
adultos fisicamente ativos
```

### `source`

Fonte que sustenta a descrição da população.

---

## Regra sobre país e região

`country` e `region` são **características descritivas da população**.

O motor não deve inferir automaticamente:

```text
país = Brasil
        ↓
população brasileira compatível
```

nem:

```text
fórmula desenvolvida no Brasil
        ↓
melhor para qualquer brasileiro
```

A correspondência populacional deve considerar o conjunto de características documentadas.

---

# 2.4 Atleta

```text
AthleteApplicability
    developedInAthletes:        Nullable<Boolean>
    validatedInAthletes:        Nullable<Boolean>
    developedInNonAthletes:     Nullable<Boolean>
    validatedInNonAthletes:     Nullable<Boolean>
    explicitAthleteRestriction: Nullable<String>
```

## Semântica

```text
true
    documentado como sim

false
    documentado como não

null
    não documentado
```

Essa distinção é obrigatória.

Exemplo:

```text
validatedInAthletes = null
```

significa:

> Não há informação suficiente.

Não significa:

> Não foi validada em atletas.

### Regra

Ausência de evidência específica:

```text
NOT_DOCUMENTED
```

e não:

```text
INELIGIBLE
```

A inelegibilidade somente pode ser produzida por restrição científica explícita.

---

# 2.5 Nível de treinamento

A escala deve ser única em toda a plataforma e corresponder à definição oficial de `Context.trainingLevel`.

Escala adotada:

```text
SEDENTARY
RECREATIONAL
TRAINED
COMPETITIVE
ELITE
```

Estrutura:

```text
TrainingLevelApplicability
    supportedLevels: Array<
        Enum<
            SEDENTARY|
            RECREATIONAL|
            TRAINED|
            COMPETITIVE|
            ELITE
        >
    >
    notes: Nullable<String>
```

Uma lista vazia significa:

```text
não documentado
```

e não:

```text
todos os níveis
```

O nível de treinamento somente deve participar da avaliação quando houver evidência correspondente.

---

# 2.6 Modalidade

```text
ModalityApplicability
    supportedModalities: Array<String>
    notes:               Nullable<String>
```

A lista deve utilizar um vocabulário controlado quando esse vocabulário for definido pela plataforma.

Exemplo:

```text
SOCCER
BODYBUILDING
SWIMMING
```

Quando não existir documentação específica:

```text
supportedModalities = []
```

significa:

```text
não documentado
```

Não significa incompatibilidade.

---

# 2.7 Características corporais

```text
BodyCharacteristicRule
    characteristic: Enum<
        LOW_BODY_FAT|
        HIGH_BODY_FAT|
        OBESITY|
        HIGH_MUSCLE_MASS|
        EXTREME_SKINFOLD_VALUES|
        EXTREME_SKINFOLD_SUM|
        OTHER
    >

    otherDescription: Nullable<String>
    effect:            Enum<SUPPORTED|WARNING|INELIGIBLE|UNKNOWN>
    description:       String
    source:            Nullable<Reference>
```

```text
BodyCharacteristicApplicability
    rules: Array<BodyCharacteristicRule>
```

O campo `effect` representa o efeito documentado daquela condição específica.

Uma característica somente deve ser adicionada quando houver fundamento científico suficiente.

---

# 3. ValidationEvidence

Representa a evidência científica conhecida para a variante.

---

## 3.1 Desenvolvimento

```text
DevelopmentEvidence
    studyReference:  Reference
    population:      PopulationProfile
    criterionMethod: Nullable<String>
    year:             Nullable<Number>
```

O estudo de desenvolvimento deve identificar, quando disponível:

- referência original;
- população;
- método de referência;
- ano;
- demais informações relevantes.

---

## 3.2 Validação, Cross-validation e Validação Externa

```text
ValidationStudy
    studyReference:  Reference
    population:      PopulationProfile
    criterionMethod: Nullable<String>
    metrics:          Nullable<ValidationMetrics>
    limitations:      Nullable<String>
```

```text
ValidationEvidence
    development:               DevelopmentEvidence
    validationStudies:         Array<ValidationStudy>
    crossValidationStudies:    Array<ValidationStudy>
    externalValidationStudies: Array<ValidationStudy>
    sourceConflict:             Nullable<SourceConflictRecord>
```

### Validation

Estudo de validação da variante em uma população específica.

### Cross-validation

Estudo de validação cruzada.

### External validation

Validação independente da população original.

Essas categorias devem permanecer separadas.

---

## 3.3 ValidationMetrics

As métricas científicas devem permanecer estruturadas, sempre que possível.

Modelo inicial:

```text
ValidationMetrics
    correlation:    Nullable<Number>
    standardError:  Nullable<Number>
    meanDifference: Nullable<Number>
    rmse:             Nullable<Number>
    otherMetrics:     Nullable<String>
```

Esse conjunto é inicial e poderá evoluir conforme as métricas encontradas na literatura.

Quando determinada métrica não estiver disponível:

```text
null
```

e não um valor artificial.

---

## 3.4 Conflito de fontes

Fontes conflitantes devem possuir estado próprio.

```text
SourceConflictRecord
    field:               String
    conflictingValues:   Array<ConflictingValue>
    resolutionStatus:    Enum<
        UNRESOLVED|
        RESOLVED_USING_PRIMARY_SOURCE|
        EXCLUDED_PENDING_SOURCE
    >
    notes:               Nullable<String>
```

```text
ConflictingValue
    value:  String
    source: Reference
```

### `NOT_DOCUMENTED` vs `SOURCE_CONFLICT`

São situações diferentes:

```text
NOT_DOCUMENTED
    informação ausente
```

versus:

```text
SOURCE_CONFLICT
    informações existentes divergem
```

Exemplos de conflito:

```text
coeficiente
constante
faixa etária
local de dobra
sexo
versão da equação
população original
```

Enquanto existir um conflito científico material:

```text
resolutionStatus = UNRESOLVED
```

a variante não deve ser considerada cientificamente confirmada para uso.

---

## 3.5 Evidence Coverage

`evidenceCoverage` não é armazenado na ficha.

É calculado durante a sugestão.

Exemplo conceitual:

```text
Sexo       ✓
Idade      ✓
População  ✓
Atleta     ?
Modalidade ?
```

O motor produz essa visão comparando:

```text
Evidence
    +
Profile
    +
Context
```

---

## 3.6 Reference

```text
Reference
    citation: String
    doi:      Nullable<String>
    url:      Nullable<String>
```

A referência deve identificar de forma suficiente a fonte utilizada.

Sempre que possível, deve ser dada preferência à fonte primária.

---

# 4. MeasurementRequirement

A variante não redefine as propriedades gerais dos tipos de medida.

Ela apenas referencia os tipos de entrada que utiliza.

```text
MeasurementRequirement
    requiredInputs: Array<InputReference>
    optionalInputs: Array<InputReference>
```

## 4.1 InputReference

```text
InputReference
    inputId: Enum<...>
```

Exemplos:

```text
AGE
SEX
BODY_WEIGHT
HEIGHT
SKINFOLD_CHEST
SKINFOLD_ABDOMEN
SKINFOLD_THIGH
```

A definição completa de cada tipo de input pertence ao catálogo global de medições:

```text
/library/measurements
```

---

## 4.2 Regra de separação

`MeasurementRequirement` descreve:

> **quais tipos de dados a variante exige.**

`InputTypeDefinition` descreve:

> **o que é esse tipo de dado e quais regras gerais ele possui.**

A variante não deve redefinir:

```text
type
unit
precision
plausibleRange
```

de um input já existente no catálogo global.

Isso evita duplicação e divergência entre variantes que utilizam o mesmo tipo de medida.

---

# 5. ScientificRestriction

Representa restrições científicas explicitamente documentadas.

São essas restrições que podem resultar em:

```text
INELIGIBLE
```

quando sua condição for satisfeita.

```text
ScientificRestriction
    type: Enum<
        SEX|
        AGE|
        POPULATION|
        ATHLETE|
        MODALITY|
        BODY_CHARACTERISTIC|
        OTHER
    >

    condition:    String
    severity:     Enum<INELIGIBLE|WARNING>
    description:  String
    source:       Reference
```

Toda restrição deve possuir fonte.

Isso evita transformar:

```text
não documentado
```

em:

```text
não permitido
```

---

# 6. Lifecycle & Versionamento

O ciclo de vida da variante é separado da evidência científica.

```text
VariantLifecycle
    status: Enum<
        DRAFT|
        ACTIVE|
        DEPRECATED|
        RETIRED
    >

    version:       String
    supersedes:    Nullable<String>
    supersededBy:  Nullable<String>
    effectiveFrom: Nullable<Date>
    changeLog:     Array<ChangeLogEntry>
```

```text
ChangeLogEntry
    version: String
    date:    Date
    summary: String
```

## Estados

### DRAFT

Variante ainda em validação.

Não participa do fluxo normal de sugestão.

### ACTIVE

Variante validada e disponível para uso.

### DEPRECATED

Variante preservada para histórico, mas que não deve ser utilizada como opção padrão para novas avaliações.

### RETIRED

Variante retirada do fluxo operacional.

Continua preservada para reprodução histórica.

---

# 6.1 Informações essenciais para `ACTIVE`

Nem todo campo `TBD` deve impedir que uma variante seja `ACTIVE`.

Informações que são essenciais para avaliação básica da variante devem estar preenchidas antes da publicação.

No mínimo:

```text
identity
mathematicalDefinition
supportedSexes
originalDevelopmentAgeRange
originalPopulation
requiredInputs
reference da definição
```

Além disso:

```text
sourceConflict
```

não pode possuir conflito científico material não resolvido.

Portanto:

```text
TBD em informação essencial
    → impede ACTIVE
```

enquanto:

```text
TBD / NOT_DOCUMENTED em informação não essencial
    → não impede ACTIVE
```

Exemplos de informações que podem permanecer não documentadas:

```text
modalidade
nível de treinamento
validação em atletas
validação externa
```

desde que não exista uma restrição científica que dependa dessas informações.

---

# 6.2 Regra de publicação

Uma variante não deve entrar em:

```text
ACTIVE
```

quando:

- a definição matemática ainda não estiver validada;
- o sexo aplicável não estiver definido;
- a população original não estiver definida;
- a faixa etária de desenvolvimento não estiver definida;
- os inputs obrigatórios não estiverem definidos;
- a referência necessária para a definição estiver ausente;
- existir conflito científico material não resolvido.

Isso não significa que todas as informações da variante precisem ser conhecidas.

Informações não documentadas devem permanecer explicitamente como não documentadas.

---

# 7. Catálogo Global de Tipos de Medida

As propriedades gerais de um input são definidas uma única vez.

Essa informação fica separada das variantes.

```text
/library/measurements

    InputTypeDefinition
    InputTypeCatalog
```

---

## 7.1 InputTypeDefinition

```text
InputTypeDefinition
    inputId:                   Enum<...>
    label:                     String
    valueType:                 Enum<DECIMAL|INTEGER|ENUM|DATE>
    unit:                      Enum<MM|KG|CM|YEARS>
    precision:                 Number
    plausibleRange:            Range<Number>
    measurementProtocolNotes:  Nullable<String>
```

### `inputId`

Identificador estável do tipo de dado.

Exemplos:

```text
AGE
BODY_WEIGHT
HEIGHT
SKINFOLD_CHEST
SKINFOLD_TRICEPS
SKINFOLD_ABDOMEN
```

### `label`

Nome legível para interface e relatórios.

### `valueType`

Tipo do valor armazenado.

### `unit`

Unidade esperada.

### `precision`

Precisão esperada para representação do valor.

### `plausibleRange`

Faixa fisiologicamente plausível para o tipo de medida.

Esse campo representa **sanidade do dado**, não aplicabilidade científica da fórmula.

### `measurementProtocolNotes`

Observações gerais sobre coleta e protocolo.

---

# 7.2 InputTypeCatalog

```text
InputTypeCatalog
    register(inputType)
    resolve(inputId)
    getDefinition(inputId)
```

Sua responsabilidade é fornecer:

```text
inputId
    ↓
InputTypeDefinition
```

O catálogo não deve decidir:

```text
qual fórmula usar
```

nem:

```text
qual cliente é compatível com uma fórmula
```

---

# 7.3 Limites fisiológicos pertencem ao tipo de medida

A seguinte regra é global:

```text
SKINFOLD_TRICEPS
    plausibleRange = [min, max]
```

e vale para qualquer variante que consuma:

```text
SKINFOLD_TRICEPS
```

Portanto:

```text
dobra tricipital = -5 mm
```

é um dado inválido independentemente da fórmula.

Esses limites não devem ser duplicados em:

```text
JP7
Petroski
Guedes
Faulkner
```

---

# 7.4 Separação entre sanidade e ciência

A arquitetura deve preservar:

```text
/library/measurements
    ↓
sanidade geral do dado
```

e:

```text
/library/scientific-rules
    ↓
aplicabilidade científica da variante
```

Exemplo:

```text
SKINFOLD_CHEST = -5 mm
```

é problema de:

```text
Measurement Validation
```

Enquanto:

```text
cliente fora da população documentada
```

é problema de:

```text
Scientific Applicability
```

---

# 8. Regras de Interpretação do Schema

O schema contém informações científicas.

O motor de sugestão é responsável por interpretar essas informações em relação ao cliente.

Fluxo:

```text
Scientific Profile
        +
Client Profile
        +
Assessment Context
        +
Assessment Data
        ↓
Validation
        ↓
Applicability Evaluation
        ↓
Eligibility
        ↓
Readiness
        ↓
Suggestion
```

O schema **não deve conter**:

```text
ageMatch
populationMatch
contextMatch
evidenceCoverage
READY
WARNING
INELIGIBLE
```

Esses resultados são derivados.

---

# 9. Ficha Completa

A estrutura composta da ficha científica é:

```text
EquationVariantScientificProfile

    identity:
        VariantIdentity

    applicability:
        sex:
            SexApplicability

        age:
            AgeApplicability

        population:
            PopulationApplicability

        athlete:
            AthleteApplicability

        trainingLevel:
            TrainingLevelApplicability

        modality:
            ModalityApplicability

        bodyCharacteristics:
            BodyCharacteristicApplicability

    evidence:
        ValidationEvidence

    inputs:
        MeasurementRequirement

    restrictions:
        Array<ScientificRestriction>

    lifecycle:
        VariantLifecycle
```

Os inputs referenciados pela variante são resolvidos no:

```text
InputTypeCatalog
```

---

# 10. Exemplo Ilustrativo

Os valores abaixo são somente placeholders.

**Não representam dados científicos confirmados.**

```text
identity:
    variantId: "jp7-male-v1"
    familyId: "jackson-pollock"
    displayName: "Jackson & Pollock 7 Dobras — Masculino"

applicability:

    sex:
        supportedSexes:
            - MALE

    age:
        originalDevelopmentAgeRange:
            min: TBD
            max: TBD

        developmentSampleMeanAge:
            TBD

        validatedAgeRanges:
            - TBD

        explicitAgeRestriction:
            null

    population:

        originalPopulation:
            description: TBD
            country: TBD
            region: TBD

            sexCoverage:
                - MALE

            ageCoverage:
                min: TBD
                max: TBD

            sampleSize: TBD

            bodyCharacteristicsNotes: TBD
            sampleCharacteristics: TBD

            source: TBD

        validationPopulations:
            - TBD

    athlete:
        developedInAthletes: null
        validatedInAthletes: null
        developedInNonAthletes: null
        validatedInNonAthletes: null
        explicitAthleteRestriction: null

    trainingLevel:
        supportedLevels: []
        notes: "Não documentado"

    modality:
        supportedModalities: []
        notes: "Não documentado"

    bodyCharacteristics:
        rules: []

evidence:

    development:
        studyReference: TBD
        population: TBD
        criterionMethod: TBD
        year: TBD

    validationStudies:
        - TBD

    crossValidationStudies:
        - TBD

    externalValidationStudies:
        - TBD

    sourceConflict:
        null

inputs:

    requiredInputs:
        - AGE
        - SEX
        - SKINFOLD_CHEST
        - SKINFOLD_AXILLARY_MID
        - SKINFOLD_TRICEPS
        - SKINFOLD_SUBSCAPULAR
        - SKINFOLD_ABDOMEN
        - SKINFOLD_SUPRAILIAC
        - SKINFOLD_THIGH

    optionalInputs:
        []

restrictions:
    []

lifecycle:

    status: DRAFT

    version: "jp7-male-v1"

    supersedes: null
    supersededBy: null
    effectiveFrom: null

    changeLog:
        []
```

As propriedades desses inputs não são repetidas na ficha.

Por exemplo:

```text
SKINFOLD_TRICEPS
```

é resolvido pelo:

```text
InputTypeCatalog
```

que fornece sua definição global.

---

# 11. Regra de preenchimento das variantes

A ficha científica deve ser preenchida **por variante**, utilizando somente informações documentadas.

Cada estudo deve preservar sua própria população e sua própria faixa etária.

Não colapsar:

```text
originalDevelopmentAgeRange
```

com:

```text
validatedAgeRanges[]
```

Exemplo conceitual:

```text
Development
    18–61
    população A
    estudo original

Validation
    18–61
    população B
    estudo 1

Validation
    20–50
    população C
    estudo 2
```

Cada estudo mantém:

```text
faixa
população
amostra
fonte
```

de maneira independente.

---

# 12. Estados de preenchimento

Durante a construção da biblioteca científica, utilizar:

```text
CONFIRMADO
TBD
SOURCE_CONFLICT
```

### CONFIRMADO

Informação sustentada pela fonte consultada.

### TBD

Informação ainda não confirmada ou não localizada.

### SOURCE_CONFLICT

Existem fontes divergentes para a mesma informação.

Esses estados são estados de **documentação científica**, não estados de execução do motor.

---

# 13. Próximos Passos

O schema deve ser considerado a **estrutura de referência** para o cadastro científico das variantes.

A próxima etapa é preencher, uma por uma, as variantes da V1.

Para cada variante devem ser determinados:

```text
1. Identificação
2. Definição matemática
3. População original
4. Características da amostra
5. Faixa etária da população de desenvolvimento
6. Idade média da amostra
7. Sexo
8. Atleta / não atleta
9. Nível de treinamento
10. Modalidade
11. Características corporais relevantes
12. Estudos de desenvolvimento
13. Estudos de validação
14. Cross-validation
15. Validação externa
16. Métricas disponíveis
17. Limitações
18. Restrições explícitas
19. Inputs obrigatórios
20. Inputs opcionais
21. Referências
22. Conflitos de fonte
23. Lifecycle
24. Versionamento
```

As propriedades globais dos inputs são obtidas separadamente pelo:

```text
InputTypeCatalog
```

Quando uma informação não puder ser confirmada:

```text
TBD
```

deve ser utilizado.

Não devem ser preenchidas lacunas por inferência.

Uma variante somente deverá ser considerada:

```text
ACTIVE
```

quando sua definição matemática e as informações científicas essenciais estiverem suficientemente validadas para permitir avaliação e execução seguras.

Informações contextuais não documentadas, como modalidade, nível de treinamento ou validação específica em atletas, não impedem `ACTIVE` por si só.
