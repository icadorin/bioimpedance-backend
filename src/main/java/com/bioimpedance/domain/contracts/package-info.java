/**
 * Contratos compartilhados entre módulos de {@code domain.*}.
 * <p>
 * Fonte: {@code architecture.md} §1.3.
 * <p>
 * Regra explícita da arquitetura: este pacote "deve permanecer pequeno"
 * e "não deve se transformar em um depósito genérico para qualquer
 * classe que não tenha lugar definido".
 * <p>
 * Antes de adicionar um tipo novo aqui, perguntar: pelo menos dois
 * módulos de {@code domain.*} precisam dele? Se só um módulo usa, o
 * tipo pertence a esse módulo, não a este pacote.
 * <p>
 * Nenhuma classe deste pacote deve conter lógica de decisão — só
 * formato de dado. Todos os tipos são imutáveis (records).
 *
 * <p>ATENÇÃO: {@code com.bioimpedance.backend} é um placeholder — ajustar
 * para o groupId/pacote-base real do projeto antes de integrar.
 */
package com.bioimpedance.domain.contracts;
