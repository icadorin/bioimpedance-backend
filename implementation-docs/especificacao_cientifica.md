# Especificação Científica — Compatibilidade e Sugestão do Motor de Avaliação Física

Este documento define **como as variantes de avaliação devem ser analisadas quanto à aplicabilidade, compatibilidade, prontidão e indicação** pelo motor de sugestão da plataforma.

Ele complementa:

- **Especificação Técnica — Avaliação Física**, que define o comportamento funcional do sistema;
- **Arquitetura de Implementação — Avaliação Física**, que define onde cada responsabilidade deve existir no código.

A especificação técnica define **o que o sistema faz**.

A arquitetura define **onde cada responsabilidade vive**.

Este documento define **quais informações e regras científicas devem ser utilizadas para que o sistema consiga verificar e indicar métodos compatíveis com cada cenário**.

O sistema deve atuar como **auxílio ao profissional**, e não como substituto de sua decisão.

---

# 1. Objetivo

O motor de sugestão deve avaliar as variantes disponíveis considerando:

- perfil do cliente;
- contexto da avaliação;
- população de desenvolvimento e validação;
- idade;
- sexo;
- condição de atleta;
- nível de treinamento;
- modalidade, quando documentada;
- objetivo da avaliação, quando relevante;
- características corporais relevantes;
- evidência científica disponível;
- requisitos de entrada;
- validade e consistência dos dados;
- estado de habilitação da variante.

A partir dessas informações, o sistema deve:

1. identificar variantes potencialmente aplicáveis;
2. excluir variantes incompatíveis;
3. identificar variantes que ainda não podem ser executadas;
4. identificar warnings que possam exigir atenção;
5. indicar as variantes compatíveis;
6. explicar os motivos da indicação;
7. registrar as regras e versões utilizadas.

O motor não deve tentar determinar uma "fórmula universalmente melhor".

---

# 2. Princípio Central

O motor de sugestão **não deve transformar validade científica em uma pontuação arbitrária**.

Não deve existir uma interpretação como:

```text
Precisão científica = 87%
```

ou:

```text
Método A = 92 pontos
Método B = 84 pontos
```

com a conclusão de que o primeiro método é cientificamente superior.

A função do motor é verificar:

```text
A variante é aplicável?
        ↓
Os dados permitem sua execução?
        ↓
Existem warnings ou inconsistências?
        ↓
Como a variante se relaciona com o perfil e contexto?
        ↓
O sistema pode indicá-la ao profissional?
```

O processo deve seguir:

```text
Perfil + Contexto
        ↓
Aplicabilidade Científica
        ↓
Validação dos Dados
        ↓
Prontidão
        ↓
Compatibilidade
        ↓
Indicação
        ↓
Explicação
```

A indicação produzida pelo sistema é uma **recomendação operacional baseada nos critérios documentados da variante**.

Ela não representa uma afirmação de precisão absoluta.

---

# 3. Fluxo Geral

O fluxo conceitual do motor é:

```text
Assessment
    ↓
Perfil + Contexto
    ↓
Aplicabilidade Científica
    ↓
Validação dos Dados
    ↓
Prontidão
    ↓
Candidatos
    ↓
Compatibilidade
    ↓
Sugestão
    ↓
Explicação
    ↓
Escolha do Profissional
    ↓
Cálculo
```

A aplicação da sugestão ocorre somente depois que o sistema conhece:

- quais variantes estão habilitadas;
- quais variantes são compatíveis;
- quais variantes possuem dados suficientes;
- quais variantes possuem warnings ou restrições;
- quais variantes estão inelegíveis.

A seleção final continua sendo do profissional.

---

# 4. Perfil e Contexto

A análise de uma variante depende do perfil do cliente e do contexto da avaliação.

O perfil contém dados relativamente estáveis.

O contexto contém informações específicas da avaliação.

---

## 4.1 Sexo

Cada variante deve declarar os sexos para os quais possui aplicação documentada.

Exemplo:

```text
supportedSexes:
    - MALE
```

ou:

```text
supportedSexes:
    - FEMALE
```

ou:

```text
supportedSexes:
    - MALE
    - FEMALE
```

Sexo incompatível constitui uma restrição científica.

Portanto:

```text
sexo incompatível
        ↓
INELIGIBLE
```

Sexo não deve ser tratado como uma simples penalização de ranking.

---

## 4.2 Idade

Cada variante deve possuir informações sobre as faixas etárias relacionadas à população de desenvolvimento e às populações de validação.

Quando possível, devem ser diferenciadas:

```text
originalDevelopmentAgeRange
validatedAgeRanges
```

A idade do cliente deve ser comparada com a evidência documentada da variante.

Possíveis resultados:

```text
EXACT
PARTIAL
OUTSIDE_VALIDATED_RANGE
UNKNOWN
```

### EXACT

A idade do cliente está dentro de uma faixa claramente suportada pela evidência da variante.

### PARTIAL

Existe correspondência parcial ou proximidade com a população documentada.

### OUTSIDE_VALIDATED_RANGE

A idade está fora de uma faixa que possui restrição científica explícita.

O resultado operacional depende da regra da variante e pode ser:

```text
WARNING
```

ou:

```text
INELIGIBLE
```

### UNKNOWN

Não existe informação suficiente para determinar a compatibilidade da idade.

A regra específica deve ser registrada na definição da variante e não inferida pelo motor.

---

## 4.3 População

A população é uma das principais informações para determinar a compatibilidade de uma variante.

Cada variante deve registrar, quando disponível:

- população original;
- populações de validação;
- características da amostra;
- sexo;
- idade;
- país ou região;
- características corporais relevantes;
- nível de treinamento;
- condição de atleta;
- modalidade;
- demais características necessárias para caracterização.

A correspondência populacional pode ser classificada como:

```text
EXACT
HIGH
MODERATE
LOW
UNKNOWN
```

### EXACT

O cenário do cliente corresponde diretamente à população documentada.

### HIGH

Existe forte correspondência com a população estudada, embora não seja idêntica.

### MODERATE

Existe correspondência parcial, com diferenças relevantes.

### LOW

Existem diferenças significativas entre o cenário do cliente e a população documentada.

### UNKNOWN

As informações disponíveis não são suficientes para determinar a correspondência.

Essa classificação representa **correspondência com a população documentada**, não uma medida de precisão científica.

---

## 4.4 Atleta

A condição de atleta não deve selecionar automaticamente uma fórmula.

Quando relevante, cada variante pode registrar:

```text
developedInAthletes
validatedInAthletes
developedInNonAthletes
validatedInNonAthletes
explicitAthleteRestriction
```

Quando o cliente for atleta:

```text
athlete = true
```

o sistema deve verificar a evidência disponível.

Se não houver evidência específica:

```text
NOT_DOCUMENTED
```

Isso não deve ser interpretado automaticamente como:

```text
INELIGIBLE
```

Somente uma restrição científica explícita poderá produzir inelegibilidade.

---

## 4.5 Nível de treinamento

Quando documentado pela evidência, a variante pode apresentar compatibilidade relacionada ao nível de treinamento.

A escala utilizada pelo sistema deve ser única e definida pela especificação de `Context`.

Exemplo conceitual:

```text
SEDENTARY
RECREATIONAL
TRAINED
COMPETITIVE
ELITE
```

A classificação deve ser aplicada somente quando existir suporte científico relevante.

Quando não houver:

```text
trainingLevelMatch = NOT_DOCUMENTED
```

O sistema não deve assumir:

```text
mais treinado → fórmula X
menos treinado → fórmula Y
```

sem evidência correspondente.

---

## 4.6 Modalidade

A modalidade esportiva somente deve participar da avaliação quando houver evidência específica.

Exemplo:

```text
supportedModalities:
    - SOCCER
    - BODYBUILDING
```

Quando a modalidade do cliente não possuir correspondência documentada:

```text
NOT_DOCUMENTED
```

A ausência de evidência específica não constitui automaticamente contraindicação.

Somente uma restrição científica explícita poderá produzir:

```text
INELIGIBLE
```

---

## 4.7 Objetivo

O contexto pode registrar o objetivo da avaliação, por exemplo:

```text
GENERAL_FOLLOW_UP
WEIGHT_LOSS
HYPERTROPHY
PERFORMANCE
SPORT_EVALUATION
```

O objetivo pode ser utilizado quando houver evidência científica ou regra operacional documentada que justifique sua consideração.

O sistema não deve assumir:

```text
hipertrofia → fórmula X
emagrecimento → fórmula Y
```

sem evidência específica.

O objetivo não altera automaticamente a validade matemática de uma variante.

---

## 4.8 Características corporais

Quando relevantes para determinada variante, podem ser registradas características como:

- baixo percentual de gordura;
- alto percentual de gordura;
- obesidade;
- indivíduos muito musculosos;
- distribuição de gordura;
- valores extremos de dobras;
- soma extrema de dobras;
- outras características corporais documentadas pela literatura.

Essas condições podem produzir:

```text
SUPPORTED
WARNING
INELIGIBLE
UNKNOWN
```

conforme as regras específicas da variante.

---

# 5. Aplicabilidade Científica

A aplicabilidade determina:

> **se existe base científica suficiente para considerar uma variante no cenário atual.**

Ela deve ser avaliada antes do ranking ou indicação.

A aplicabilidade pode utilizar:

```text
sexo
idade
população
atleta
treinamento
modalidade
características corporais
restrições científicas
```

O motor não deve inventar critérios de aplicabilidade.

Toda regra científica utilizada deve estar associada à definição da variante e à sua evidência.

---

# 6. Evidência Científica

Cada variante deve possuir metadados estruturados sobre a evidência disponível.

Quando aplicável:

```text
developmentStudies
validationStudies
crossValidationStudies
externalValidationStudies
populationValidation
criterionMethod
sampleSize
ageCoverage
sexCoverage
populationCoverage
validationMetrics
limitations
references
```

A evidência deve ser registrada como informação estruturada.

Não deve ser resumida prematuramente em um único "score científico".

---

## 6.1 Desenvolvimento

Registra a evidência utilizada para desenvolver a variante.

Deve incluir, quando disponível:

- estudo original;
- população;
- tamanho da amostra;
- sexo;
- idade;
- características relevantes;
- método de referência;
- ano;
- referência bibliográfica.

---

## 6.2 Validação

Registra estudos que avaliaram a variante em determinada população.

Devem ser preservados os dados relevantes para determinar:

```text
qual população foi validada
qual sexo
qual idade
qual método de referência
quais métricas
quais limitações
```

---

## 6.3 Cross-validation

Registra evidência de validação cruzada.

Quando disponível, deve informar:

- população;
- amostra;
- procedimento;
- métricas;
- limitações;
- referência.

---

## 6.4 Validação externa

Registra validações independentes da população original.

A informação deve ser mantida separadamente para que o motor possa distinguir:

```text
evidência original
```

de:

```text
evidência independente
```

---

## 6.5 Evidence Coverage

O sistema deve verificar não apenas se uma variante possui estudos, mas **quanto da situação atual está contemplado pela evidência conhecida**.

Exemplo:

```text
Sexo       ✓
Idade      ✓
População  ✓
Atleta     ?
Modalidade ?
```

A cobertura deve considerar, quando relevante:

- sexo;
- idade;
- população;
- atleta;
- treinamento;
- modalidade;
- características corporais;
- demais características específicas da variante.

A ausência de cobertura não implica automaticamente inelegibilidade.

---

## 6.6 Source Conflict

Conflitos entre fontes devem ser tratados separadamente de ausência de documentação.

Exemplo:

```text
NOT_DOCUMENTED
```

significa:

> não há informação suficiente.

Já:

```text
SOURCE_CONFLICT
```

significa:

> existem fontes que apresentam informações divergentes.

O conflito pode ocorrer em:

- coeficientes;
- constantes;
- locais de medida;
- população;
- sexo;
- faixa etária;
- versão da equação;
- referência original;
- demais componentes da definição matemática.

Uma variante com `SOURCE_CONFLICT` não deve ser tratada como definitivamente válida apenas porque uma das fontes apresenta uma versão utilizável.

O estado deve ser registrado e a variante deve permanecer sujeita à validação antes de ser considerada uma definição científica definitiva.

---

# 7. Dados Necessários

Cada variante deve declarar explicitamente os dados utilizados pela sua fórmula.

A definição deve separar:

```text
requiredInputs
optionalInputs
```

Além da identificação dos inputs, deve registrar as propriedades necessárias para sua interpretação.

---

## 7.1 Required Inputs

São os dados obrigatórios para executar a variante.

Exemplo:

```text
requiredInputs:
    - AGE
    - SEX
    - SKINFOLD_CHEST
    - SKINFOLD_ABDOMEN
    - SKINFOLD_THIGH
```

A ausência de qualquer input obrigatório impede a execução:

```text
MISSING_INPUTS
```

---

## 7.2 Optional Inputs

São informações que podem ser armazenadas ou utilizadas por outras partes do sistema, mas não são necessárias para executar a variante.

Exemplo:

```text
optionalInputs:
    - BODY_WEIGHT
    - HEIGHT
```

A ausência de inputs opcionais não deve impedir a execução.

---

## 7.3 Precision

Cada input deve declarar, quando necessário:

```text
precision
```

A precisão deve seguir a definição validada da variante.

Exemplo:

```text
triceps:
    type = DECIMAL
    unit = MM
    precision = 1
```

O sistema não deve arredondar ou truncar valores automaticamente sem que essa regra esteja definida para a variante.

Precisão é uma característica do input e não um critério de qualidade científica universal.

---

## 7.4 Units

Cada input deve declarar a unidade esperada.

Exemplo:

```text
SKINFOLD_CHEST
    unit = MM
```

A unidade armazenada deve ser validada antes da execução da fórmula.

Conversões de unidade, quando permitidas, devem ser explícitas e determinísticas.

---

# 8. Validação dos Dados

A validação dos dados deve ocorrer antes da avaliação final de prontidão.

O motor de sugestão não deve ser responsável por aceitar dados evidentemente inválidos apenas porque os campos estão preenchidos.

A validação deve identificar:

- valores inválidos;
- valores impossíveis;
- inconsistências;
- duplicidades;
- conflitos;
- problemas de unidade;
- problemas de precisão;
- problemas de qualidade da medição.

---

## 8.1 Valores inválidos

Exemplos:

```text
idade = -5
peso = "abc"
altura = null em campo obrigatório
dobra = "xyz"
```

Esses valores não devem ser considerados válidos para processamento.

---

## 8.2 Valores impossíveis

Exemplos:

```text
peso = 0
dobra = -5 mm
altura = -20 cm
idade = -3
```

Os limites fisiologicamente plausíveis devem ser definidos de forma explícita.

Esses limites devem ser tratados como validação dos dados, não como regras de ranking.

---

## 8.3 Duplicidade

O sistema deve detectar situações em que existam múltiplas medições incompatíveis para o mesmo input dentro do mesmo contexto de avaliação.

Exemplo:

```text
SKINFOLD_TRICEPS
    12.0 mm
    15.0 mm
```

A resolução deve ser determinada pelo protocolo de coleta.

Possíveis resultados:

```text
VALID
WARNING
INVALID
```

O motor não deve escolher arbitrariamente uma das medições.

---

## 8.4 Conflitos

Conflitos podem ocorrer quando existem dados incompatíveis dentro do mesmo `Assessment`.

Exemplos:

```text
duas medidas incompatíveis
unidades diferentes sem conversão definida
dados contraditórios
metadados incompatíveis
```

O sistema deve identificar o conflito antes da execução.

---

## 8.5 Consistência

Os dados devem respeitar as regras internas do `Assessment`.

Exemplos:

```text
idade derivada incompatível com data de nascimento
sexo incompatível com a variante
input fora do domínio esperado
unidade incompatível
valor fora dos limites definidos
```

A inconsistência deve ser representada de maneira estruturada.

---

## 8.6 Qualidade da medição

Quando existirem metadados suficientes, a qualidade da medição pode ser classificada como:

```text
VALID
WARNING
UNKNOWN
```

### VALID

Os dados atendem aos critérios conhecidos do protocolo.

### WARNING

Existem fatores que exigem atenção, mas que não impedem necessariamente o uso.

### UNKNOWN

Não existem informações suficientes para avaliar completamente a qualidade.

A qualidade da medição não deve ser utilizada para inventar uma medida de "precisão científica".

Ela existe principalmente para:

- evitar processamento inadequado;
- informar o profissional;
- gerar warnings;
- melhorar a auditabilidade.

---

# 9. Prontidão

Depois da aplicabilidade e da validação dos dados, cada variante deve receber um estado operacional.

Estados:

```text
READY
MISSING_INPUTS
INELIGIBLE
DISABLED
```

Uma variante também pode possuir warnings independentes do estado:

```text
READY
+ WARNING
```

---

## READY

A variante é aplicável, está habilitada e possui os dados necessários para execução.

```text
status = READY
```

Ela pode ser indicada ao profissional.

---

## READY + WARNING

A variante pode ser executada, mas existem condições que exigem atenção.

Exemplo:

```text
status = READY

warnings:
    - AGE_NEAR_VALIDATED_LIMIT
    - EVIDENCE_LIMITED
```

O warning não torna automaticamente a variante inelegível.

---

## MISSING_INPUTS

A variante pode ser aplicável, mas um ou mais inputs obrigatórios estão ausentes.

```text
status = MISSING_INPUTS
```

Ela não pode ser executada até que os inputs necessários estejam disponíveis.

---

## INELIGIBLE

Existe incompatibilidade científica ou outra restrição explícita.

Exemplo:

```text
sexo incompatível
```

ou:

```text
faixa etária explicitamente incompatível
```

ou:

```text
restrição populacional explícita
```

Nesse estado, a variante não deve ser executada normalmente.

---

## DISABLED

A variante está desabilitada pela configuração do sistema ou do profissional.

```text
status = DISABLED
```

A variante pode continuar existindo na biblioteca global, mas não deve participar da sugestão ativa.

---

# 10. Regras de Exclusão

A exclusão ocorre antes da indicação.

Uma variante pode ser excluída quando existir:

```text
sexo incompatível
idade explicitamente incompatível
população explicitamente incompatível
restrição corporal explícita
restrição de aplicação explícita
dados inválidos impeditivos
variante desabilitada
definição científica não validada para uso
```

A exclusão deve ser explicável.

Exemplo:

```text
status = INELIGIBLE

reason:
    SEX_NOT_SUPPORTED
```

ou:

```text
status = INELIGIBLE

reason:
    AGE_OUTSIDE_VALIDATED_RANGE
```

O sistema não deve converter uma incompatibilidade científica em simples perda de prioridade.

---

# 11. Sugestão

A sugestão representa a indicação das variantes compatíveis com o cenário atual.

O sistema não deve tentar "descobrir" uma fórmula universalmente superior.

A indicação deve ser baseada nas informações documentadas da variante.

---

## 11.1 Candidatos

São candidatas as variantes que:

- estão habilitadas;
- não foram excluídas por aplicabilidade;
- possuem definição válida;
- podem ser consideradas para o cenário atual.

As candidatas podem estar em diferentes estados:

```text
READY
READY + WARNING
MISSING_INPUTS
```

As variantes:

```text
INELIGIBLE
DISABLED
```

não devem participar da indicação normal.

---

## 11.2 Compatibilidade

A compatibilidade é determinada pela combinação das informações já avaliadas.

Podem ser consideradas:

```text
sexMatch
ageMatch
populationMatch
contextMatch
evidenceCoverage
dataAvailability
measurementQuality
```

Essas informações devem servir para **explicar por que uma variante é indicada ou não**.

Não devem ser tratadas automaticamente como uma pontuação científica.

---

## 11.3 Indicação

Quando existirem variantes `READY`, o sistema deve indicar as opções consideradas mais compatíveis segundo as regras documentadas.

A indicação deve preservar a hierarquia dos critérios científicos definidos para a variante.

Quando houver várias variantes igualmente compatíveis e não existir regra válida para diferenciá-las:

```text
indicar as variantes
```

em vez de inventar uma preferência.

A escolha final permanece com o profissional.

---

## 11.4 Explicação

A indicação deve possuir justificativas estruturadas.

Exemplo:

```text
reasons:
    - "Sexo compatível com a variante."
    - "Idade dentro da população validada."
    - "População com correspondência documentada."
    - "Todos os inputs obrigatórios estão disponíveis."
```

Warnings:

```text
warnings:
    - "Não há evidência específica para a condição de atleta."
    - "Qualidade do protocolo não informada."
```

A explicação deve ser produzida a partir dos dados estruturados de aplicabilidade, evidência e validação.

O `SuggestionEngine` não deve inventar justificativas.

---

# 12. Conflitos e Estados Extremos

O comportamento do motor deve ser definido também quando não existir uma situação ideal.

---

## 12.1 Nenhum elegível

Se todas as variantes forem:

```text
INELIGIBLE
```

o sistema não deve forçar uma recomendação.

Resultado conceitual:

```text
SuggestionResult
    status = NO_ELIGIBLE_METHOD
    suggestion = none
```

A resposta deve informar os motivos de exclusão encontrados.

---

## 12.2 Nenhum READY

Se não existir nenhuma variante `READY`, o sistema não deve escolher uma variante incompleta como se estivesse pronta.

Deve diferenciar:

```text
NO_READY_METHOD
```

de:

```text
NO_ELIGIBLE_METHOD
```

Caso existam variantes `MISSING_INPUTS`, o sistema pode informar quais dados estão faltando para que essas variantes possam ser executadas.

---

## 12.3 Apenas MISSING_INPUTS

Se todas as variantes aplicáveis estiverem em:

```text
MISSING_INPUTS
```

o sistema deve indicar a ausência dos dados necessários.

Exemplo:

```text
missingInputs:
    - SKINFOLD_CHEST
    - SKINFOLD_THIGH
```

Quando possível, pode informar que esses dados são necessários para desbloquear determinadas variantes.

O sistema não deve executar uma fórmula incompleta.

---

## 12.4 Apenas DISABLED

Se todas as variantes disponíveis estiverem desabilitadas:

```text
NO_ENABLED_METHOD
```

O sistema não deve habilitar uma variante automaticamente.

---

## 12.5 SOURCE_CONFLICT

Quando existir conflito entre fontes que definem a mesma variante:

```text
SOURCE_CONFLICT
```

deve ser preservado como estado de evidência.

A variante não deve ser tratada como cientificamente confirmada enquanto o conflito não tiver sido resolvido.

A resolução deve ocorrer na biblioteca científica, não por heurística do motor.

---

## 12.6 Warnings

Warnings representam condições que exigem atenção, mas não necessariamente impedem a execução.

Exemplo:

```text
status = READY

warnings:
    - EVIDENCE_LIMITED
    - PROTOCOL_UNKNOWN
```

O warning deve permanecer explícito no resultado.

O profissional continua responsável pela decisão.

---

# 13. Preferência do Profissional

A preferência profissional deve permanecer separada da compatibilidade científica.

Exemplo:

```text
ScientificCompatibility:
    JP7-M
    Petroski-M8

ProfessionalPreference:
    Petroski-M8
```

A preferência pode determinar a escolha quando a variante:

- está habilitada;
- é elegível;
- possui os dados necessários.

A preferência profissional não deve transformar:

```text
INELIGIBLE
```

em:

```text
READY
```

nem corrigir uma definição científica inválida.

---

# 14. Override

O profissional pode escolher manualmente uma variante diferente daquela indicada pelo sistema quando a variante estiver em condição permitida para uso.

Exemplo:

```text
Suggestion:
    JP7-M

ProfessionalSelection:
    Petroski-M8
```

Quando existir:

```text
READY + WARNING
```

o profissional poderá prosseguir conforme as regras da plataforma.

Quando houver uma situação que exija justificativa:

```text
overrideReason
```

deve ser registrado.

Uma variante:

```text
INELIGIBLE
```

não deve ser executada simplesmente por meio de override.

O override altera a escolha do profissional, não a aplicabilidade científica da variante.

---

# 15. Resultado Estruturado

O resultado deve possuir informações suficientes para permitir:

- exibição ao profissional;
- análise de compatibilidade;
- explicação;
- auditoria;
- reprodução do comportamento do motor.

Estrutura conceitual:

```text
SuggestionResult

status

suggestedVariants[]

candidateVariants[]

excludedVariants[]

warnings[]

reasons[]

engineVersion
```

Cada variante candidata pode conter:

```text
variantId

status

sexMatch

ageMatch

populationMatch

contextMatch

evidenceCoverage

measurementQuality

dataCompleteness

reasons[]

warnings[]
```

Os nomes exatos dos campos podem ser ajustados na implementação.

---

# 16. Versionamento

A definição matemática não é a única informação que precisa ser versionada.

Também devem possuir versão:

```text
EquationVariant
ApplicabilityRules
EvidenceClassification
SuggestionPolicy
SuggestionEngine
```

O objetivo é garantir que uma avaliação histórica possa responder:

> Quais regras estavam vigentes quando esta indicação foi produzida?

Uma alteração de regra não deve modificar retroativamente uma decisão histórica.

---

# 17. Ciclo de Vida da Variante

Uma `EquationVariant` deve possuir ciclo de vida próprio.

Estados sugeridos:

```text
DRAFT
ACTIVE
DEPRECATED
RETIRED
```

### DRAFT

A variante ainda está sendo validada e não deve ser utilizada como regra científica definitiva.

### ACTIVE

A variante está validada e disponível para uso.

### DEPRECATED

A variante continua preservada para histórico, mas não deve ser utilizada como opção preferencial para novas avaliações.

### RETIRED

A variante não deve mais participar do fluxo operacional, mas sua definição histórica permanece preservada.

O versionamento e o ciclo de vida são conceitos diferentes.

Exemplo:

```text
Petroski-4D-F-v1
    status = DEPRECATED

Petroski-4D-F-v2
    status = ACTIVE
```

A versão anterior continua disponível para reprodução histórica.

---

# 18. Auditoria

A decisão do motor deve ser auditável.

O sistema deve ser capaz de registrar:

```text
Assessment
Context
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

A auditoria deve permitir reconstruir:

```text
quais dados existiam
        ↓
quais variantes estavam disponíveis
        ↓
quais regras estavam vigentes
        ↓
quais variantes foram excluídas
        ↓
quais variantes foram indicadas
        ↓
qual escolha o profissional realizou
```

A auditoria deve ser append-only e preservar o estado histórico utilizado na decisão.

---

# 19. Princípios que Não Devem ser Implementados

O motor não deve assumir:

```text
mais dobras = melhor fórmula
```

nem:

```text
fórmula mais nova = melhor fórmula
```

nem:

```text
fórmula brasileira = sempre melhor para brasileiros
```

nem:

```text
atleta = fórmula específica automaticamente
```

nem:

```text
hipertrofia = fórmula específica automaticamente
```

nem:

```text
maior quantidade de inputs = maior precisão científica
```

nem:

```text
mais estudos = automaticamente melhor para qualquer cliente
```

nem:

```text
resultado numérico de uma fórmula = prova de superioridade sobre outra
```

nem:

```text
preferência profissional = justificativa científica
```

nem:

```text
ausência de evidência específica = incompatibilidade automática
```

O sistema deve utilizar somente critérios que possam ser relacionados à definição, aplicabilidade ou evidência documentada da variante.

---

# Encerramento

O objetivo deste motor é transformar informações científicas previamente documentadas em um processo operacional consistente:

```text
Perfil
   +
Contexto
   +
Evidência
   +
Regras de aplicabilidade
   +
Dados válidos
        ↓
Avaliação da variante
        ↓
Elegibilidade / Prontidão
        ↓
Indicação
        ↓
Explicação
        ↓
Decisão do profissional
```

O sistema **indica**.

O profissional **decide**.

A biblioteca científica define **o que é válido**.

A validação protege **a integridade dos dados**.

A auditoria preserva **o histórico da decisão**.

O motor de sugestão não deve criar novas verdades científicas; deve executar de forma determinística e auditável as regras que foram previamente definidas e validadas.