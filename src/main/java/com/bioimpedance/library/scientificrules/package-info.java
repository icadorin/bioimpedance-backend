/**
 * Modelo de domínio da EquationVariantScientificProfile.
 * <p>
 * Fonte: schema_cientifico.md (schema completo).
 * <p>
 * Espelha o schema documentado nas 44 fichas científicas. Este pacote
 * é puro dado — nenhuma lógica de decisão. A interpretação desses
 * dados (aplicabilidade, elegibilidade, sugestão) pertence a
 * {@code domain.*}, nunca a este pacote.
 * <p>
 * Regra do schema (Seção "Convenção fundamental"): campos derivados em
 * runtime (ageMatch, populationMatch, READY, WARNING, INELIGIBLE etc.)
 * NUNCA pertencem a este modelo — eles são calculados por
 * {@code domain.applicability} e {@code domain.eligibility} a partir
 * destes dados, nunca armazenados aqui.
 * <p>
 * NOTA DE FRONTEIRA: por regra de arquitetura (architecture.md §1.1,
 * "library" não pode depender de "domain"), este pacote define seus
 * próprios tipos de Sexo/TrainingLevel em vez de reusar
 * {@code domain.contracts.Sex}/{@code TrainingLevel}. Isso duplica os
 * dois enums entre library e domain. Alternativa possível: criar um
 * pacote "shared-kernel" abaixo de library para conceitos realmente
 * universais (Sex, TrainingLevel) e eliminar a duplicação — decisão em
 * aberto, não tomada aqui.
 */
package com.bioimpedance.library.scientificrules;
