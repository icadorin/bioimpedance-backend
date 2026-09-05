# Arquitetura de Implementação — Avaliação Física

Este documento complementa a **Especificação Técnica — Avaliação Física**, a **Especificação Científica — Compatibilidade e Sugestão do Motor de Avaliação Física** e o **Schema Científico — Ficha de Aplicabilidade e Evidência da `EquationVariant`**.

A **Especificação Técnica** define **o que** o sistema faz.

A **Especificação Científica — Compatibilidade e Sugestão** define **como as regras de aplicabilidade, compatibilidade, prontidão e indicação devem ser interpretadas**.

O **Schema Científico** define **quais informações científicas cada `EquationVariant` deve possuir**.

Este documento define **onde cada responsabilidade vive no código e quais fronteiras arquiteturais não podem ser cruzadas**.

Se a especificação for violada, o sistema pode ficar cientificamente incorreto.

Se esta arquitetura for violada, o sistema pode ficar **difícil de manter, auditar, testar e evoluir**, mesmo que continue funcionando.

---

# 0. Regra de ouro

> Nenhuma camada pode fazer o trabalho de outra camada, mesmo que seja "só um caso simples".

Todo desvio começa assim:

```text
if (variant.name === "JP7")
```

"só dessa vez",

ou:

```text
cálculo rápido dentro do controller
```

ou:

```text
sugestão que já salva o resultado
```

A arquitetura existe para impedir que essas exceções se transformem em dependências permanentes.

Regras científicas, validação, aplicabilidade, sugestão, escolha profissional, cálculo, conversão, persistência e auditoria devem permanecer separadas.

---

# 1. Módulos e limites

O pipeline da especificação deve ser refletido em módulos físicos isolados.

```text
/domain

    /validation
        → validação estrutural e semântica dos dados
        → validação de inputs
        → identificação de inconsistências

    /applicability
        → interpretação das regras de aplicabilidade
        → comparação entre dados do cliente e definições científicas

    /eligibility
        → resolução de READY / MISSING_INPUTS / INELIGIBLE / DISABLED

    /suggestion
        → avaliação dos candidatos
        → compatibilidade
        → indicação
        → explicação

        /criteria
            → PopulationMatcher
            → AgeMatcher
            → SexMatcher
            → ContextMatcher
            → EvidenceEvaluator

        /explanation
            → SuggestionExplanationBuilder

        → SuggestionEngine

    /conversion-suggestion
        → avaliação de conversões aplicáveis
        → indicação de conversão
        → explicação

        /criteria
            → ConversionCriteriaEvaluator

        /explanation
            → ConversionSuggestionExplanationBuilder

        → ConversionSuggestionEngine

    /calculation
        → preparação/recebimento de inputs resolvidos
        → execução do motor matemático puro

    /conversion
        → execução do motor de conversão puro

    /config
        → ProfessionalConfiguration
        → SystemConversionPolicy

    /audit
        → AuditSnapshot


/library

    /equations
        → EquationFamily
        → EquationVariant
        → EquationVariantRegistry

    /conversions
        → ConversionDefinition
        → ConversionDefinitionRegistry

    /scientific-rules
        → EquationVariantScientificProfile
        → ApplicabilityDefinition
        → PopulationProfile
        → ValidationEvidence
        → ScientificRestriction
        → MeasurementRequirement
        → VariantLifecycle
        → ScientificRuleRegistry


/domain/contracts
    → contratos e tipos realmente compartilhados entre módulos


/orchestration

    /assessment-flow
        → coordena o pipeline completo
        → carrega dependências externas
        → coordena módulos de /domain
        → coordena acesso a /library e /persistence


/persistence

    /repositories
        → único ponto de acesso a banco
```

A divisão interna do `suggestion` não significa que todas as classes precisem existir como arquivos independentes desde o primeiro commit.

A regra principal é arquitetural:

> A responsabilidade de validar, avaliar aplicabilidade, resolver prontidão, indicar candidatos e construir explicações não deve ficar misturada com cálculo matemático, configuração global ou persistência.

---

# 1.1 Regra de dependência

A arquitetura deve seguir:

```text
orchestration
    → domain
    → library
    → persistence


domain/validation
    → domain/contracts


domain/applicability
    → domain/contracts
    → library/scientific-rules
    → library/equations quando necessário para resolver definições


domain/eligibility
    → domain/contracts
    → sem acesso direto a persistence


domain/suggestion
    → domain/contracts
    → library/scientific-rules
    → seus próprios componentes internos


domain/conversion-suggestion
    → domain/contracts
    → library/scientific-rules quando necessário
    → library/conversions quando necessário
    → seus próprios componentes internos


domain/calculation
    → domain/contracts
    → library/equations


domain/conversion
    → domain/contracts
    → library/conversions


domain/config
    → domain/contracts


domain/audit
    → domain/contracts


domain/*
    → NÃO → persistence/*


library/*
    → NÃO → domain/*
    → NÃO → orchestration/*
    → NÃO → persistence/*
```

A `orchestration` é responsável por montar o fluxo entre módulos, resolver dependências externas e coordenar acesso à `library` e à `persistence`.

Os módulos de domínio podem depender de **contratos compartilhados**, mas não devem depender arbitrariamente de implementações internas uns dos outros.

---

# 1.2 Por que a separação é obrigatória?

Por exemplo:

```text
calculation
```

não pode saber que:

```text
suggestion
```

existe.

Se souber, será possível fazer o motor matemático decidir qual variante utilizar, quebrando:

```text
aplicabilidade
≠
elegibilidade
≠
sugestão
≠
escolha profissional
≠
cálculo
```

Da mesma forma:

```text
suggestion
```

não deve consultar banco diretamente.

Em vez disso:

```text
persistence
    ↓
orchestration
    ↓
domain
```

Os dados devem chegar ao domínio por contratos explícitos.

---

# 1.3 Contratos compartilhados

Tipos realmente comuns entre módulos podem existir em:

```text
/domain/contracts
```

Exemplos:

```text
AssessmentContext
CandidateStatus
ReadinessResult
MatchResult
EvidenceSummary
SuggestionCriteriaSummary
ValidationResult
```

Essa camada deve permanecer pequena.

`contracts` não deve se transformar em um depósito genérico para qualquer classe que não tenha lugar definido.

Um contrato somente deve existir nessa camada quando houver necessidade real de compartilhamento entre módulos.

---

# 1.4 Biblioteca científica é declarativa

A biblioteca científica deve armazenar as informações necessárias para descrever a variante.

Exemplo:

```text
/library/scientific-rules
```

contém:

```text
idade
sexo
população
atleta
treinamento
modalidade
características corporais
evidência
restrições
inputs
ciclo de vida
versão
```

Essas informações são consumidas pelos motores.

A biblioteca não deve:

```text
decidir qual variante sugerir
acessar banco
executar cálculo
escolher pelo profissional
```

A responsabilidade da biblioteca é fornecer definições estáveis.

---

# 1.5 Checagem prática — CI

Adicionar um teste de arquitetura, por exemplo:

```text
ArchUnit
dependency-cruiser
eslint-plugin-boundaries
ou equivalente na stack escolhida
```

que falhe o build se:

- `domain/calculation` importar `domain/suggestion`;
- `domain/calculation` importar `domain/conversion-suggestion`;
- `domain/conversion` importar `domain/suggestion`;
- `domain/*` importar `persistence/*` diretamente;
- `domain/config` depender de detalhes internos de `library/*`;
- `library/*` importar `domain/*`;
- `library/*` importar `orchestration/*`;
- `library/*` importar `persistence/*`;
- `library/scientific-rules` executar lógica de decisão;
- `domain/suggestion` possuir acesso direto a repository;
- `domain/calculation` executar consulta a banco ou serviço externo.

---

# 2. Validação dos dados

A validação dos dados deve ser tratada como responsabilidade própria.

O fluxo conceitual é:

```text
Assessment
    ↓
Validation
    ↓
Applicability
    ↓
Eligibility
    ↓
Readiness
    ↓
Suggestion
```

A validação deve identificar:

```text
valor inválido
valor impossível
unidade incompatível
precisão incompatível
duplicidade
conflito
inconsistência
input obrigatório ausente
```

Essa responsabilidade não pertence ao motor matemático.

---

## 2.1 Validação não define aplicabilidade

A seguinte diferença deve permanecer explícita:

```text
"12 mm é um valor válido?"
```

é validação de dados.

Enquanto:

```text
"Esta variante é apropriada para este perfil?"
```

é aplicabilidade.

São problemas diferentes.

---

## 2.2 Dados inválidos

Exemplos:

```text
idade = -5
peso = "abc"
dobra = -5 mm
altura = -20 cm
```

não devem chegar ao cálculo.

---

## 2.3 Duplicidade e conflito

Quando houver múltiplas medições incompatíveis para o mesmo input, o sistema não deve escolher arbitrariamente uma delas.

O resultado deve ser estruturado como:

```text
VALID
WARNING
INVALID
```

conforme o protocolo aplicável.

---

# 3. O motor de cálculo é puro

A execução de uma `FormulaDefinition` deve ser uma função pura:

```text
(inputs, coefficients) → output
```

Sem:

```text
I/O
banco
HTTP
sessão
usuário
controller
side effect
```

Exemplo incorreto:

```text
function calculate(assessmentId) {
    const measurements = db.getMeasurements(assessmentId);

    ...
}
```

Exemplo correto:

```text
function calculate(inputs): PredictionResult {
    // somente matemática
}
```

O fluxo externo deve ser:

```text
persistence
    ↓
orchestration
    ↓
input resolution
    ↓
calculation
```

O motor recebe:

```text
variant definition
+
resolved inputs
```

e produz:

```text
PredictionResult
```

O motor **não decide qual variante utilizar**.

---

# 4. Nenhuma seleção por nome

A seleção de variantes e conversões deve utilizar identificadores opacos.

Exemplos:

```text
variantId
conversionId
```

Nenhuma lógica de negócio deve depender de:

```text
"JP7"
"Petroski"
"Guedes"
"Faulkner"
"Siri"
"Brozek"
```

para decidir comportamento.

Esses nomes podem existir em:

```text
library
fixtures
labels
conteúdo de exibição
```

mas não como condição de negócio espalhada pelo domínio.

---

# 4.1 Registry de variantes

```text
/library/equations

EquationVariantRegistry

    register(variant)
    resolve(variantId)
    getDefinition(variantId)
```

Sua responsabilidade é apenas resolver:

```text
variantId
    ↓
EquationVariant
```

O registry não faz:

```text
aplicabilidade
eligibilidade
sugestão
ranking
cálculo
```

---

# 4.2 Registry científico

```text
/library/scientific-rules

ScientificRuleRegistry

    register(profile)
    resolve(variantId)
    getApplicability(variantId)
    getEvidence(variantId)
    getRestrictions(variantId)
```

Sua responsabilidade é fornecer:

```text
variantId
    ↓
EquationVariantScientificProfile
```

O registry não decide se uma variante é adequada ao cliente.

---

# 4.3 Registry de conversões

```text
/library/conversions

ConversionDefinitionRegistry

    register(conversion)
    resolve(conversionId)
    getDefinition(conversionId)
```

Também não deve conter regras de recomendação.

---

# 4.4 Hardcode de identificadores

Um teste/lint de arquitetura pode detectar literais de variantes e conversões fora das áreas permitidas.

Exemplos:

```text
"JP7"
"Petroski"
"Guedes"
"Faulkner"
"Siri"
"Brozek"
```

Ocorrências em:

```text
domain/
orchestration/
controllers/
```

devem ser tratadas como possíveis hardcodes de regra de negócio.

---

# 5. Versionamento como dado

`EquationVariant`, `ConversionDefinition` e perfis científicos publicados devem ser tratados como dados versionados.

Depois de publicados:

```text
não editar
não sobrescrever
não apagar
```

Nova versão:

```text
v1
 ↓
v2
```

A versão anterior permanece preservada.

Isso permite reproduzir resultados históricos.

---

# 5.1 Ciclo de vida

A variante possui:

```text
DRAFT
ACTIVE
DEPRECATED
RETIRED
```

O ciclo de vida é independente da versão.

Exemplo:

```text
petroski-4d-f-v1
    status = DEPRECATED

petroski-4d-f-v2
    status = ACTIVE
```

---

# 5.2 Conflitos científicos

Uma definição com conflito científico relevante não resolvido não deve ser promovida para:

```text
ACTIVE
```

Exemplo:

```text
sourceConflict = UNRESOLVED
```

deve impedir publicação como variante ativa até que a questão seja resolvida.

---

# 6. AuditSnapshot é append-only

O `AuditSnapshot` deve possuir somente operação de criação e leitura.

```text
create()
read()
```

Não deve existir:

```text
update()
delete()
```

para auditorias históricas.

A auditoria deve preservar:

```text
Assessment
Context
dados utilizados
candidateVariants
applicabilityResults
validationResults
suggestionResult
professionalSelection
engineVersion
rulesVersion
equationVersion
evidenceVersion
```

---

# 7. Biblioteca global x configuração profissional

A biblioteca científica é global.

A configuração do profissional é um overlay.

Fluxo:

```text
library
    ↓
ProfessionalConfiguration
    ↓
enabled / disabled
    ↓
SuggestionEngine
```

O profissional nunca altera:

```text
/library/*
```

para representar sua preferência.

---

# 7.1 Configuração de variantes

Exemplo:

```text
enabledVariants:
    - variantId-A
    - variantId-B
```

A biblioteca continua contendo a totalidade das variantes publicadas.

A configuração somente determina quais estão disponíveis para aquele profissional.

---

# 7.2 Configuração de conversões

Da mesma forma:

```text
enabledConversions
preferredConversionId
```

pertencem à configuração profissional.

A biblioteca permanece global.

---

# 7.3 Política padrão de conversão

A política de conversão padrão da plataforma é um dado configurável/versionável:

```text
SystemConversionPolicy

    defaultConversionId
```

Atualmente:

```text
defaultConversionId = siri
```

Isso significa:

```text
Siri = default operacional
```

e não:

```text
Siri = cientificamente superior
```

A resolução deve ocorrer:

```text
SystemConversionPolicy
    ↓
defaultConversionId
    ↓
ConversionDefinitionRegistry
    ↓
ConversionDefinition
```

Nunca:

```text
if (conversion.name == "Siri")
```

dentro do motor.

---

# 7.4 Restaurar padrão

Restaurar o padrão significa remover ou zerar a preferência personalizada.

Exemplo:

```text
preferredConversionId = null
```

A biblioteca não é alterada.

O sistema volta a utilizar a política padrão quando aplicável.

---

# 8. Separação das responsabilidades

Os seguintes conceitos não devem ser combinados:

```text
Validation
Applicability
Eligibility
Readiness
Suggestion
ProfessionalSelection
Calculation
Conversion
Audit
```

Exemplo:

```text
Validation
```

não decide qual fórmula é melhor.

```text
Applicability
```

não executa fórmula.

```text
Suggestion
```

não calcula.

```text
Calculation
```

não escolhe fórmula.

```text
ProfessionalSelection
```

não altera evidência científica.

---

# 9. Applicability

O módulo de `applicability` interpreta:

```text
EquationVariantScientificProfile
```

contra:

```text
Client Profile
Context
```

Ele pode avaliar:

```text
sexo
idade
população
atleta
treinamento
modalidade
características corporais
restrições
```

O resultado é uma avaliação derivada.

A definição científica original continua na:

```text
/library/scientific-rules
```

---

# 9.1 Não inferir regras ausentes

O avaliador não deve transformar ausência de documentação em incompatibilidade.

Exemplo:

```text
validatedInAthletes = null
```

significa:

```text
NOT_DOCUMENTED
```

e não:

```text
INELIGIBLE
```

---

# 10. Eligibility

`Eligibility` transforma os resultados de aplicabilidade e demais condições impeditivas em estado operacional.

Estados:

```text
READY
MISSING_INPUTS
INELIGIBLE
DISABLED
```

O módulo não deve criar novas categorias sem necessidade.

---

# 10.1 Warnings

Warnings não devem virar um estado principal adicional.

Uma variante pode ser:

```text
READY
```

com:

```text
warnings[]
```

Exemplo:

```text
status = READY

warnings:
    - EVIDENCE_LIMITED
    - PROTOCOL_UNKNOWN
```

---

# 10.2 Não tratar estados como sequência linear

Não existe uma transição obrigatória:

```text
DISABLED
    ↓
INELIGIBLE
    ↓
MISSING_INPUTS
    ↓
READY
```

São condições diferentes.

A resolução deve observar as condições atuais da variante.

---

# 11. SuggestionEngine

O `SuggestionEngine` é responsável por indicar variantes compatíveis.

Fluxo:

```text
candidatos
    ↓
aplicabilidade
    ↓
eligibility
    ↓
readiness
    ↓
critérios de compatibilidade
    ↓
SuggestionResult
```

Os critérios podem incluir:

```text
PopulationMatch
AgeMatch
SexMatch
ContextMatch
EvidenceCoverage
DataCompleteness
MeasurementQuality
```

O objetivo não é produzir uma pontuação científica.

O objetivo é indicar os métodos que apresentam correspondência documentada.

---

# 11.1 O que o SuggestionEngine não faz

Não deve:

```text
executar fórmula
executar conversão
acessar banco
inventar regra científica
alterar Measurement
transformar INELIGIBLE em READY
tratar score como precisão
decidir pelo profissional
```

---

# 11.2 Empate

Quando não existir diferença suficientemente definida pelos critérios documentados:

```text
não inventar nova regra
```

O sistema pode indicar mais de uma variante.

O profissional decide.

Não existe necessidade de um `SuggestionTieBreaker` como mecanismo científico obrigatório.

---

# 11.3 Explicação

A explicação deve ser construída com base nos resultados dos avaliadores.

Exemplo:

```text
SuggestionExplanationBuilder
```

recebe:

```text
criteria
status
warnings
evidence
applicability
```

e produz uma explicação estruturada.

Não deve criar justificativas que não estejam presentes nos resultados.

---

# 12. ConversionSuggestionEngine

O `ConversionSuggestionEngine` possui responsabilidade própria.

Fluxo:

```text
PredictionResult
    ↓
Conversion Applicability
    ↓
Eligibility
    ↓
Professional Configuration
    ↓
System Policy
    ↓
ConversionSuggestionResult
    ↓
Professional Selection
    ↓
Conversion
```

Ele não executa a conversão.

---

# 12.1 Default Siri

O default operacional é obtido por:

```text
SystemConversionPolicy
```

Nunca por:

```text
if (name === "Siri")
```

A preferência profissional pode substituir o default quando a conversão estiver disponível e elegível.

---

# 12.2 O que o ConversionSuggestionEngine não faz

Não deve:

```text
executar conversão
alterar PredictionResult
criar ConversionResult
alterar biblioteca
forçar conversão inelegível
interpretar Siri como cientificamente superior
```

---

# 13. Máquina de cálculo

O fluxo de cálculo é:

```text
EquationVariant
    ↓
resolvedInputs
    ↓
Calculation Engine
    ↓
PredictionResult
```

A seleção já deve ter ocorrido antes.

O cálculo não pode voltar para o módulo de sugestão.

---

# 14. Máquina de conversão

O fluxo de conversão é:

```text
PredictionResult
    ↓
ConversionDefinition
    ↓
Conversion Engine
    ↓
ConversionResult
```

A conversão é independente da seleção da `EquationVariant`.

---

# 15. Nenhuma entidade composta método + conversão

Não criar:

```text
JP7Siri
JP7Brozek
PetroskiSiri
PetroskiBrozek
```

Uma `EquationVariant` e uma `ConversionDefinition` são entidades independentes.

Fluxo:

```text
EquationVariant
    ↓
PredictionResult
    ↓
ConversionDefinition
    ↓
FinalResult
```

Quando necessário, a execução pode ser representada por:

```text
CalculationRun
    variantId
    predictionResult
    conversionId?
    conversionResult?
```

---

# 16. ScientificRuleRegistry

A `ScientificRuleRegistry` é o ponto de acesso às definições científicas versionadas da biblioteca.

Exemplo:

```text
variantId
    ↓
EquationVariantScientificProfile
```

O perfil pode conter:

```text
identity
applicability
evidence
inputs
restrictions
lifecycle
```

A registry não interpreta o cliente.

Ela apenas fornece a definição científica.

---

# 17. Onde ficam as regras ajustáveis

As regras científicas específicas da variante devem ficar isoladas em:

```text
/library/scientific-rules
```

Isso permite:

```text
adicionar variante
corrigir variante
adicionar validação
adicionar estudo
adicionar população
adicionar restrição
alterar lifecycle
depreciar variante
```

sem espalhar regras pelo `SuggestionEngine`.

O princípio é:

```text
Regra científica
    ↓
dados declarativos

Motor
    ↓
interpretação desses dados
```

e não:

```text
Regra científica
    ↓
if/switch espalhado pelo código
```

---

# 18. Validação global x regra da variante

Algumas regras são globais.

Exemplo:

```text
dobra não pode ser negativa
idade não pode ser negativa
unidade deve ser válida
```

Essas regras pertencem ao mecanismo global de validação.

Já:

```text
esta variante utiliza peitoral
esta variante foi validada em determinada população
esta variante restringe determinada faixa etária
```

pertencem ao perfil científico da variante.

Isso evita duplicação.

---

# 19. Golden Tests

Os testes devem permanecer independentes por responsabilidade:

```text
cálculo
validação
aplicabilidade
eligibilidade
sugestão
conversão
seleção
```

Estrutura sugerida:

```text
/tests

    /golden

        /calculation
        /validation
        /applicability
        /eligibility
        /suggestion
        /conversion
        /selection
```

O runner deve ser genérico quando possível.

---

# 19.1 Golden test de cálculo

```text
{
    "id": "GOLDEN-JP7-F-001",

    "variantId": "jp7-female-v1",

    "input": {
        "sex": "F",
        "age": 44,
        "sum7mm": 60
    },

    "expected": {
        "type": "BODY_DENSITY",
        "value": 1.06518908
    }
}
```

---

# 19.2 Golden test de aplicabilidade

O teste pode verificar:

```text
cliente
+
ScientificProfile
    ↓
ApplicabilityResult
```

Exemplo conceitual:

```text
{
    "variantId": "jp7-female-v1",

    "profile": {
        "sex": "F",
        "age": 44
    },

    "expected": {
        "sexMatch": "MATCH",
        "ageMatch": "WITHIN_VALIDATED_RANGE"
    }
}
```

---

# 19.3 Golden test de sugestão

O teste deve verificar a indicação com base nos critérios disponíveis.

Exemplo:

```text
{
    "variantId": "jp7-female-v1",

    "expected": {
        "status": "READY",
        "suggested": true
    }
}
```

Não deve exigir um score científico arbitrário.

---

# 19.4 Golden test de exceção

Devem existir fixtures para casos como:

```text
sexo incompatível
idade fora de restrição
input ausente
dado inválido
variante desabilitada
source conflict
nenhuma variante elegível
nenhuma variante READY
somente MISSING_INPUTS
somente DISABLED
READY + WARNING
```

---

# 20. Determinismo

O sistema deve ser determinístico dentro da mesma versão das regras.

Para:

```text
mesmo Assessment
+
mesmo Context
+
mesma configuração
+
mesmas versões
```

o sistema deve produzir o mesmo resultado.

Isso deve valer para:

```text
validação
aplicabilidade
eligibility
sugestão
cálculo
conversão
```

Diferenças de resultado devem ser explicáveis por alteração de:

```text
dados
configuração
biblioteca
regra
versão
```

---

# 21. Performance e cache

Performance é uma preocupação de infraestrutura, não uma regra científica.

O motor pode ser otimizado com cache quando apropriado.

O cache não pode alterar o resultado.

Uma chave de cache deve ser baseada em todos os dados relevantes para o resultado, incluindo as versões necessárias.

Exemplo conceitual:

```text
Assessment fingerprint
+
Context fingerprint
+
Configuration fingerprint
+
Rules version
+
Engine version
```

O mesmo input lógico deve produzir o mesmo resultado, independentemente de o resultado ter vindo de cache ou de uma execução nova.

---

# 22. Auditoria e reprodução

Uma execução histórica deve poder ser reconstruída sem depender da versão atual das regras.

O `AuditSnapshot` deve preservar os identificadores/versionamentos necessários:

```text
variantId
equationVersion
scientificRulesVersion
evidenceVersion
suggestionEngineVersion
configurationVersion
conversionId
conversionVersion
```

A alteração futura da biblioteca não deve mudar retroativamente o significado de uma avaliação histórica.

---

# 23. Responsabilidades da orchestration

A `orchestration` deve coordenar:

```text
1. carregar Assessment
2. carregar Context
3. carregar configuração
4. carregar EquationVariants
5. carregar ScientificProfiles
6. executar validação
7. executar aplicabilidade
8. resolver eligibility/readiness
9. executar SuggestionEngine
10. receber ProfessionalSelection
11. executar cálculo
12. executar ConversionSuggestionEngine quando aplicável
13. receber ConversionSelection
14. executar conversão
15. montar resultado final
16. registrar auditoria
```

A orchestration **coordena**, mas não implementa as regras científicas.

Não deve conter:

```text
if idade > X
if sexo == Y
if athlete == true
if variant == JP7
```

como lógica de aplicabilidade.

---

# 24. Regra de evolução

Uma nova variante deve ser adicionada sem modificar a arquitetura central.

O fluxo deve ser:

```text
Nova EquationVariant
        ↓
ScientificProfile
        ↓
Registry
        ↓
SuggestionEngine
```

O motor não deve precisar conhecer previamente o nome ou a família da nova variante.

---

# 25. Checklist de revisão de PR

Antes de aprovar qualquer PR nesse domínio:

- [ ] Nenhum método é selecionado por nome fora de registry, fixtures ou conteúdo de exibição.
- [ ] O cálculo não faz I/O.
- [ ] A conversão não faz I/O.
- [ ] Nenhum módulo de domínio acessa persistence diretamente.
- [ ] A biblioteca não depende de domain, orchestration ou persistence.
- [ ] A biblioteca científica permanece declarativa.
- [ ] Regras científicas novas foram colocadas em `library/scientific-rules`.
- [ ] Não foi adicionada lógica científica hardcoded ao `SuggestionEngine`.
- [ ] Validação de dados permanece separada de aplicabilidade.
- [ ] Aplicabilidade permanece separada de eligibility.
- [ ] Eligibility permanece separada de sugestão.
- [ ] Sugestão permanece separada de cálculo.
- [ ] Escolha profissional permanece separada da recomendação.
- [ ] `INELIGIBLE` não pode ser transformado em elegível por preferência profissional.
- [ ] `MISSING_INPUTS` não é tratado como `READY`.
- [ ] Warnings permanecem separados do estado principal.
- [ ] Não foi criado score representando precisão científica.
- [ ] Não foi criada lógica de comparação numérica entre resultados de métodos.
- [ ] Não foi criada consistência longitudinal como regra do motor.
- [ ] Não foi criada combinação automática de métodos.
- [ ] Não foram criados `RecommendationTier`/`TieBreaker` desnecessários.
- [ ] Siri continua sendo default por política/configuração, e não por nome hardcoded.
- [ ] Preferência profissional de conversão só pode selecionar conversão elegível.
- [ ] Variantes e conversões continuam versionadas.
- [ ] Definições publicadas permanecem imutáveis.
- [ ] `AuditSnapshot` continua append-only.
- [ ] Alterações de regras produzem versionamento apropriado.
- [ ] Casos extremos possuem testes.
- [ ] O comportamento permanece determinístico para a mesma entrada/versionamento.

---

# 26. Modelo arquitetural final

O fluxo de avaliação de método é:

```text
Assessment
    ↓
Validation
    ↓
Applicability
    ↓
Eligibility
    ↓
Readiness
    ↓
Suggestion Criteria
    ├── Sex
    ├── Age
    ├── Population
    ├── Context
    ├── Evidence
    ├── Data Availability
    └── Measurement Validation
    ↓
SuggestionResult
    ↓
ProfessionalSelection
    ↓
Calculation
    ↓
PredictionResult
```

E, quando houver conversão:

```text
PredictionResult
    ↓
Conversion Applicability
    ↓
Conversion Eligibility
    ↓
Professional Configuration
    ↓
System Conversion Policy
    ↓
ConversionSuggestionResult
    ↓
ProfessionalConversionSelection
    ↓
Conversion
    ↓
FinalResult
```

A separação fundamental permanece:

```text
CIÊNCIA

ScientificRuleRegistry
    ↓
ScientificProfile
    ↓
Applicability
    ↓
Eligibility


SUGESTÃO

Compatibility
    ↓
SuggestionResult


DECISÃO

ProfessionalSelection


CÁLCULO

EquationVariant
    ↓
PredictionResult
    ↓
ConversionDefinition
    ↓
FinalResult


INFRAESTRUTURA

Orchestration
    ↓
Persistence
    ↓
Audit
```

Nenhuma dessas responsabilidades deve ser artificialmente incorporada à outra.
