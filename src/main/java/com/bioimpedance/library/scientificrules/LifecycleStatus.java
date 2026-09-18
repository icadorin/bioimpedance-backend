package com.bioimpedance.backend.library.scientificrules;

/**
 * Fonte: schema_cientifico.md §6.
 * <ul>
 *   <li>DRAFT — ainda em validação, não participa do fluxo normal de sugestão</li>
 *   <li>ACTIVE — validada e disponível para uso</li>
 *   <li>DEPRECATED — preservada para histórico, não é opção padrão para novas avaliações</li>
 *   <li>RETIRED — retirada do fluxo operacional, preservada para reprodução histórica</li>
 * </ul>
 */
public enum LifecycleStatus {
    DRAFT,
    ACTIVE,
    DEPRECATED,
    RETIRED
}
