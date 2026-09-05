# EquationFamily — Especificação Formal

## 1. Objetivo

`EquationFamily` representa uma unidade de organização metodológica da biblioteca de equações da plataforma.

Este documento define:

- o que caracteriza uma `EquationFamily`;
- como as `EquationVariant` devem ser agrupadas;
- quais informações pertencem à família e quais pertencem à variante;
- como uma nova variante deve ser incorporada à biblioteca;
- as regras gerais que todas as variantes devem seguir para participar do fluxo de aplicabilidade, prontidão e sugestão;
- o inventário de variantes da V1 que receberá especificação científica individual.

A unidade utilizada para cálculo, aplicabilidade, elegibilidade e sugestão é sempre a:

```text
EquationVariant
```

A `EquationFamily` possui função exclusivamente organizacional e metodológica.

---

# 2. Definição

`EquationFamily` agrupa variantes que compartilham uma mesma linha metodológica de derivação, origem científica e lógica de construção do modelo.

A família é uma **unidade de organização metodológica** e não uma unidade de:

```text
cálculo
aplicabilidade
elegibilidade
sugestão
seleção profissional
```

A unidade matemática efetivamente executável pelo sistema é:

```text
EquationVariant
```

---

# 3. Critérios de agrupamento

Podem justificar o agrupamento em uma mesma família, considerados em conjunto:

```text
- mesma linha metodológica de derivação;
- mesma origem ou programa de pesquisa;
- mesma lógica de construção do modelo.
```

Os seguintes critérios **não definem uma família isoladamente**:

```text
- mesmo autor;
- mesmo país;
- mesmo número de dobras;
- mesma população;
- mesma finalidade.
```

Esses fatores podem fazer parte da documentação científica das variantes, mas não devem ser utilizados isoladamente para determinar a família.

---

# 4. Estrutura de `EquationFamily`

```text
EquationFamily

    familyId: String

    displayName: String

    description: String

    measurementModality: Enum<SKINFOLD>

    originStudyReference: Nullable<Reference>
```

## 4.1 `familyId`

Identificador estável da família.

Exemplos:

```text
jackson-pollock
guedes
petroski
faulkner
```

O identificador não deve ser reutilizado para representar outra família metodológica.

---

## 4.2 `displayName`

Nome apresentado na interface e na documentação.

Exemplos:

```text
Jackson & Pollock
Guedes
Petroski
Faulkner
```

---

## 4.3 `description`

Descrição documental da família.

Pode explicar sua origem ou identidade metodológica.

Exemplo:

```text
Guedes:

Equações específicas desenvolvidas para populações
relativamente homogêneas.

Petroski:

Conjunto de equações desenvolvido a partir de diferentes
combinações de dobras e variáveis adicionais.
```

Esse conteúdo é documental.

Não constitui uma regra estruturada consumida diretamente pelo `SuggestionEngine`.

---

## 4.4 `measurementModality`

Identifica a modalidade geral da família.

Na V1:

```text
SKINFOLD
```

---

## 4.5 `originStudyReference`

Referência do estudo, tese ou programa científico que originou a família, quando identificável.

Pode ser:

```text
null
```

quando a origem ainda não estiver suficientemente documentada.

A ausência dessa referência, por si só, não constitui regra automática de inelegibilidade.

---

# 5. Campos que não pertencem à `EquationFamily`

A família não deve possuir:

```text
applicability
evidence
restrictions
lifecycle
ranking
variantIds
supportedSexes
ageRange
population
requiredInputs
```

Essas informações pertencem à variante e à sua especificação científica.

A família não deve conter informação suficiente para que o backend tome uma decisão científica sobre um cliente.

---

# 6. Relação com `EquationVariant`

Toda `EquationVariant` possui referência para sua família:

```text
EquationVariant

    familyId: String
```

Exemplo:

```text
JP7-M

    familyId = "jackson-pollock"
```

A relação possui uma única fonte de verdade:

```text
EquationVariant
    ↓
familyId
```

A família não mantém uma lista própria de variantes.

Não deve existir:

```text
Family.variantIds
```

como segunda fonte de verdade.

---

# 7. Resolução de variantes

A localização das variantes pertence ao:

```text
EquationVariantRegistry
```

Exemplo:

```text
EquationVariantRegistry

    resolve(variantId)

    findByFamily(familyId)
```

Exemplo:

```text
findByFamily("jackson-pollock")
```

retorna as variantes associadas à família.

O `ScientificRuleRegistry` possui responsabilidade diferente:

```text
ScientificRuleRegistry

    resolve(variantId)
```

retorna a especificação científica associada à variante.

Portanto:

```text
EquationVariantRegistry
    → localiza variantes

ScientificRuleRegistry
    → localiza especificações científicas
```

---

# 8. Fronteira de uso de `familyId`

`familyId` pode ser utilizado para:

```text
- organização da biblioteca;
- navegação;
- agrupamento visual;
- documentação;
- consulta de variantes por família.
```

`familyId` não deve ser utilizado diretamente como critério científico de:

```text
aplicabilidade
elegibilidade
sugestão
seleção de método
```

O `SuggestionEngine` avalia a variante individualmente.

Conceitualmente:

```text
Assessment
    ↓
EquationVariant
    ↓
Scientific Profile
    ↓
Aplicabilidade
    ↓
Elegibilidade
    ↓
Prontidão
    ↓
Sugestão
```

Não:

```text
Assessment
    ↓
EquationFamily
    ↓
Regra genérica da família
```

---

# 9. Variante como unidade científica

Cada variante deve ser tratada como uma unidade científica própria.

Uma variante não deve herdar automaticamente da família:

```text
sexo
idade
população
faixa etária
atleta
treinamento
modalidade
evidência
restrições
inputs
```

Essas características precisam estar explicitamente documentadas no nível apropriado da variante.

Exemplo proibido:

```text
Family:
    supportedSex = MALE

Variant:
    sexo não informado

→ assumir MALE
```

Essa inferência não é permitida.

---

# 10. Regra geral para todas as variantes

Toda equação que participa da biblioteca deve ser representada como uma:

```text
EquationVariant
```

A variante deve possuir:

```text
identidade própria
+
família
+
definição matemática
+
versão
+
estado
+
especificação científica
```

Conceitualmente:

```text
EquationVariant
        ↓
FormulaDefinition
        +
Scientific Specification
```

A matemática define **como calcular**.

A especificação científica define **em quais condições a variante pode ser considerada**.

---

# 11. Regra de especificação científica

Cada variante deverá possuir uma especificação científica individual.

Essa especificação deverá documentar, conforme disponibilidade e validação das fontes:

```text
- identidade da variante;
- família;
- definição matemática;
- sexo suportado;
- faixa etária original de desenvolvimento;
- idade média da amostra de desenvolvimento, quando disponível;
- população original;
- inputs necessários;
- referências;
- evidências de validação;
- restrições documentadas;
- informações contextuais relevantes;
- estado do ciclo de vida.
```

As informações devem permanecer específicas da variante.

Não se deve criar uma única ficha científica para toda a família quando as variantes possuem diferenças científicas relevantes.

---

# 12. Separação entre matemática e aplicabilidade

A `EquationVariant` não deve decidir se deve ser utilizada.

A matemática deve responder:

> Como calcular esta variante?

A especificação científica deve responder:

> Em quais condições esta variante pode ser considerada?

O motor responde:

> Dado este cliente, contexto e conjunto de dados, esta variante é aplicável, elegível e está pronta?

Portanto:

```text
FormulaDefinition
    → cálculo

Scientific Specification
    → aplicabilidade/restrições/evidência

SuggestionEngine
    → avaliação e sugestão
```

---

# 13. Regra de não inferência

O sistema não deve criar regras científicas por inferência.

Exemplos proibidos:

```text
mesmo autor
    ↓
mesma população
```

```text
mesma família
    ↓
mesmo sexo
```

```text
mesmo número de dobras
    ↓
mesma aplicabilidade
```

```text
mesma família
    ↓
mesma faixa etária
```

```text
mesma família
    ↓
mesma evidência
```

A especificação de cada variante deve ser explícita.

---

# 14. Aplicabilidade da variante

A aplicabilidade deve ser avaliada no nível da `EquationVariant`.

Conforme a documentação científica disponível, podem ser considerados:

```text
sexo
idade
faixa etária
população
características da amostra
treinamento
condição de atleta
modalidade
outras restrições documentadas
```

Esses critérios não devem ser assumidos automaticamente apenas porque duas variantes pertencem à mesma família.

---

# 15. Prontidão da variante

Depois da avaliação de aplicabilidade, o sistema verifica se existem todos os dados exigidos pela variante.

Os estados definidos para o fluxo são:

```text
READY

MISSING_INPUTS

INELIGIBLE

DISABLED
```

### `READY`

A variante é aplicável e possui os dados necessários para execução.

### `MISSING_INPUTS`

A variante pode ser considerada, mas ainda faltam inputs necessários.

### `INELIGIBLE`

Existe condição científica ou regra explícita que impede a utilização da variante naquele contexto.

### `DISABLED`

A variante não está habilitada na configuração daquele profissional.

Esses estados não devem ser confundidos.

---

# 16. `MISSING_INPUTS` não significa inelegível

Uma variante pode ser cientificamente aplicável e ainda não estar pronta para cálculo.

Exemplo:

```text
Variant
    ↓
aplicável
    ↓
faltam medidas
    ↓
MISSING_INPUTS
```

Quando os inputs forem fornecidos:

```text
MISSING_INPUTS
    ↓
READY
```

O estado representa disponibilidade dos dados, não qualidade científica da variante.

---

# 17. `NOT_DOCUMENTED`

A ausência de documentação não deve automaticamente ser transformada em:

```text
INELIGIBLE
```

Quando uma propriedade científica não estiver documentada, ela deve permanecer representada como ausência de documentação.

Exemplo:

```text
athleteEvidence = null
```

significa:

```text
não documentado
```

e não:

```text
não aplicável
```

nem:

```text
proibido
```

A interpretação operacional depende da regra específica do critério.

---

# 18. Regra de sugestão

O `SuggestionEngine` não deve conhecer nominalmente cada equação.

Não deve existir comportamento como:

```text
if variantId == "JP7-M"
```

para implementar a ciência da variante.

A avaliação deve ocorrer genericamente:

```text
variantId
    ↓
ScientificRuleRegistry
    ↓
Scientific Profile
    ↓
Criteria Evaluation
    ↓
Suggestion
```

Assim, a adição de uma nova variante não exige alteração estrutural no motor.

---

# 19. Critérios de sugestão

A sugestão deve ser consequência dos critérios científicos e das regras de produto documentadas.

O sistema não deve inventar uma falsa precisão científica por meio de pesos arbitrários destinados a representar "qualidade científica".

Quando não houver critério documentado que diferencie duas variantes igualmente compatíveis, o sistema pode apresentar ambas como alternativas.

A sugestão continua sendo uma indicação auxiliar.

A decisão final pertence ao profissional.

---

# 20. Sugestão e escolha profissional

O sistema deve distinguir:

```text
SuggestedVariant
```

de:

```text
ProfessionalSelection
```

Exemplo:

```text
SuggestedVariant = JP7-M

ProfessionalSelection = P-M7
```

Nesse caso:

```text
override = true
```

A escolha profissional não altera retroativamente o que o sistema havia sugerido.

---

# 21. Regra de cálculo

O cálculo sempre aponta para uma variante específica:

```text
EquationVariant
    ↓
FormulaDefinition
    ↓
CalculationRun
    ↓
PredictionResult
```

A `EquationFamily` nunca é executada diretamente.

Exemplo:

```text
Petroski
```

não é suficiente para executar um cálculo.

É necessário:

```text
P-M7
```

ou outra `EquationVariant` específica.

---

# 22. Outputs das variantes V1

Na biblioteca atual:

As variantes Jackson & Pollock, Guedes e Petroski possuem saída:

```text
BODY_DENSITY
```

A variante Faulkner possui saída:

```text
BODY_FAT_PERCENTAGE
```

Portanto:

```text
Jackson & Pollock
Guedes
Petroski
    ↓
BODY_DENSITY
```

e:

```text
Faulkner
    ↓
BODY_FAT_PERCENTAGE
```

---

# 23. Conversões

Conversões não são variantes.

A biblioteca atual possui:

```text
Siri
Brozek
```

como `ConversionDefinition`.

Elas trabalham sobre:

```text
BODY_DENSITY
```

e podem produzir:

```text
BODY_FAT_PERCENTAGE
```

A cadeia conceitual é:

```text
EquationVariant
    ↓
PredictionResult
    ↓
ConversionDefinition
    ↓
Resultado final
```

Não devem ser criadas variantes artificiais como:

```text
JP7 + Siri
JP7 + Brozek
Petroski + Siri
Petroski + Brozek
```

A escolha da conversão ocorre separadamente da escolha da variante.

---

# 24. Regra de manutenção da biblioteca

Adicionar uma nova variante deve exigir:

```text
1. criar a EquationVariant;

2. associar o familyId;

3. definir a FormulaDefinition;

4. criar sua especificação científica;

5. registrar a variante;

6. validar sua documentação;

7. definir seu lifecycle.
```

O `SuggestionEngine` não deve precisar receber uma regra especial para reconhecer a nova variante.

Da mesma forma, remover ou desabilitar uma variante não deve exigir alteração estrutural do motor.

---

# 25. Versionamento

O versionamento científico e matemático pertence à variante.

Exemplo:

```text
JP7-M-v1
JP7-M-v2
```

A família:

```text
Jackson & Pollock
```

permanece como agrupador.

A versão relevante para:

```text
cálculo
aplicabilidade
sugestão
auditoria
```

deve ser identificável no nível da variante e dos artefatos científicos associados.

---

# 26. Lifecycle

O ciclo de vida pertence à variante.

Estados previstos:

```text
DRAFT
ACTIVE
DEPRECATED
RETIRED
```

Uma variante cuja documentação científica ainda não esteja suficientemente consolidada poderá permanecer:

```text
DRAFT
```

sem alterar a definição da família.

---

# 27. Faulkner

`Faulkner` permanece como família da V1.

A situação documental específica de uma variante deve permanecer no nível dessa variante.

Na V1:

```text
FALK4
```

possui observação documental relacionada à aplicabilidade sexual, conforme a própria especificação técnica:

```text
M/F*
```

com indicação:

```text
(confirmar)
```

Essa incerteza não deve criar um atributo especial na família.

Ela pertence à especificação científica da variante.

---

# 28. Inventário oficial de variantes da V1

Este é o inventário que deverá receber as especificações científicas individuais.

## 28.1 Jackson & Pollock

```text
JP7-M
JP7-F
JP3-M
JP3-F
```

Total:

```text
4 variantes
```

---

## 28.2 Guedes

```text
G-M3
G-M4
G-M5
G-M6
G-M7
G-M8

G-F3
G-F4
G-F5
G-F6
G-F7
G-F8
```

Total:

```text
12 variantes
```

---

## 28.3 Petroski

```text
P-M1
P-M2
P-M3
P-M4
P-M5
P-M6
P-M7
P-M8
P-M9
P-M10
P-M11
P-M12
P-M13
P-M14

P-F1
P-F2
P-F3
P-F4
P-F5
P-F6
P-F7
P-F8
P-F9
P-F10
P-F11
P-F12
P-F13
P-F14
P-F15
P-F16
```

Total:

```text
30 variantes
```

---

## 28.4 Faulkner

```text
FALK4
```

Total:

```text
1 variante
```

---

# 29. Total da V1

```text
Jackson & Pollock → 4
Guedes            → 12
Petroski          → 30
Faulkner          → 1

TOTAL             → 47 variantes
```

Esse é o inventário oficial considerado para a V1.

A existência da variante no inventário não significa automaticamente:

```text
ACTIVE
```

Seu estado dependerá da especificação e validação científica correspondente.

---

# 30. Relação entre família e inventário

A estrutura conceitual da V1 é:

```text
Jackson & Pollock
    ├── JP7-M
    ├── JP7-F
    ├── JP3-M
    └── JP3-F

Guedes
    ├── G-M3
    ├── G-M4
    ├── G-M5
    ├── G-M6
    ├── G-M7
    ├── G-M8
    ├── G-F3
    ├── G-F4
    ├── G-F5
    ├── G-F6
    ├── G-F7
    └── G-F8

Petroski
    ├── P-M1 ... P-M14
    └── P-F1 ... P-F16

Faulkner
    └── FALK4
```

A família somente organiza esse conjunto.

Cada variante possui sua própria definição matemática e especificação científica.

---

# 31. Relação com as 47 especificações científicas

O próximo nível de documentação deverá produzir uma ficha individual para cada:

```text
EquationVariant
```

Portanto:

```text
47 variantes
    ↓
47 especificações científicas individuais
```

Cada ficha deverá seguir o mesmo padrão estrutural.

Isso permite que:

```text
uma nova variante
```

seja adicionada sem mudar:

```text
EquationFamily
SuggestionEngine
```

ou a arquitetura central.

---

# 32. Fonte da verdade

A divisão de responsabilidade fica:

```text
EquationFamily
    → identidade e agrupamento metodológico

EquationVariant
    → identidade da variante

FormulaDefinition
    → matemática executável

Scientific Specification
    → aplicabilidade, evidência, população,
      restrições e requisitos científicos

EquationVariantRegistry
    → localização das variantes

ScientificRuleRegistry
    → localização das especificações científicas

SuggestionEngine
    → avaliação das variantes

ProfessionalSelection
    → decisão efetivamente realizada pelo profissional
```

Nenhuma camada deve assumir informações que pertencem à outra.

---

# 33. Regra de extensibilidade

A arquitetura deve ser aberta para novas variantes.

Adicionar:

```text
Nova EquationVariant
```

não deve exigir:

```text
novo if no SuggestionEngine
```

nem:

```text
novo case
```

nem:

```text
novo fluxo especial
```

A variante deve entrar na biblioteca por configuração e registro.

O motor avalia a variante por meio de sua especificação científica.

---

# 34. Regra central

A regra definitiva é:

```text
EquationFamily
    ↓
organiza metodologias

EquationVariant
    ↓
define a unidade específica de cálculo

Scientific Specification
    ↓
define como a variante deve ser interpretada cientificamente

SuggestionEngine
    ↓
avalia a variante

Professional
    ↓
decide
```

Portanto:

> **A família organiza.**
>
> **A variante define.**
>
> **A especificação científica documenta.**
>
> **O motor avalia.**
>
> **O profissional decide.**

---

# 35. Regra final para a V1

A V1 será construída sobre:

```text
4 famílias

47 EquationVariant

2 ConversionDefinition
```

Famílias:

```text
Jackson & Pollock
Guedes
Petroski
Faulkner
```

Variantes:

```text
JP7-M
JP7-F
JP3-M
JP3-F

G-M3
G-M4
G-M5
G-M6
G-M7
G-M8
G-F3
G-F4
G-F5
G-F6
G-F7
G-F8

P-M1 ... P-M14
P-F1 ... P-F16

FALK4
```

Conversões:

```text
SIRI
BROZEK
```

As conversões permanecem fora do conceito de `EquationFamily` e fora do inventário de 47 `EquationVariant`.

---

# 36. Resumo arquitetural

A arquitetura deve representar dois fluxos distintos que se encontram somente quando uma `EquationVariant` produz um `PredictionResult` que pode ser convertido.

```text
                              LIBRARY
                                 │
                 ┌───────────────┴────────────────┐
                 │                                │
          Equation Library                  Conversion Library
                 │                                │
                 ▼                                ▼
         EquationFamily                  ConversionDefinition
                 │
                 ▼
         EquationVariant
                 │
          ┌──────┴───────┐
          │              │
          ▼              ▼
 FormulaDefinition   Scientific Specification
          │              │
          └──────┬───────┘
                 ▼
          SuggestionEngine
                 │
                 ▼
      ProfessionalSelection
                 │
                 ▼
             Calculation
                 │
                 ▼
         PredictionResult
                 │
                 ▼
     Conversion Applicability
                 │
                 ▼
   ConversionSuggestionEngine
                 │
                 ▼
      ConversionSelection
                 │
                 ▼
       ConversionDefinition
                 │
                 ▼
          ConversionResult
                 │
                 ▼
            Final Result
                 │
                 ▼
           AuditSnapshot
```

A relação entre os componentes é:

```text
EquationFamily
    ↓
organiza EquationVariant

EquationVariant
    ├── FormulaDefinition
    └── Scientific Specification

EquationVariant
    ↓
SuggestionEngine
    ↓
ProfessionalSelection
    ↓
Calculation
    ↓
PredictionResult

PredictionResult
    ↓
Conversion Applicability
    ↓
ConversionSuggestionEngine
    ↓
ConversionSelection
    ↓
ConversionDefinition
    ↓
ConversionResult
    ↓
Final Result
    ↓
AuditSnapshot
```

As responsabilidades permanecem separadas:

```text
EquationFamily
    → agrupamento metodológico

EquationVariant
    → unidade matemática específica

FormulaDefinition
    → definição matemática executável

Scientific Specification
    → aplicabilidade, evidência, população,
      requisitos e restrições da variante

SuggestionEngine
    → avaliação e sugestão da variante

ProfessionalSelection
    → escolha profissional da variante

Calculation
    → execução da variante

PredictionResult
    → resultado produzido pela variante

ConversionDefinition
    → transformação de um PredictionResult

ConversionSuggestionEngine
    → avaliação e sugestão da conversão

ConversionSelection
    → escolha profissional da conversão

ConversionResult
    → resultado produzido pela conversão

AuditSnapshot
    → registro imutável da execução completa
```

A regra central do fluxo é:

```text
EquationVariant
    ↓
PredictionResult
    ↓
[quando aplicável]
ConversionDefinition
    ↓
Final Result
    ↓
AuditSnapshot
```

Portanto, `ConversionDefinition` **não pertence à árvore de `EquationFamily`** e não é uma característica da `EquationVariant`.

Ela pertence à biblioteca de conversões e é utilizada em um segundo estágio do processamento, iniciado somente após a existência de um `PredictionResult` compatível.

A regra de projeto é:

> **Nenhuma decisão científica deve ser herdada implicitamente da família. Toda variante deve possuir sua própria especificação científica e ser avaliável individualmente pelo motor.**
