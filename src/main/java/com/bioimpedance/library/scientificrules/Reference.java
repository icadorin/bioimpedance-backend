package com.bioimpedance.library.scientificrules;

/**
 * Fonte: schema_cientifico.md §3.6.
 * A referência deve identificar de forma suficiente a fonte utilizada.
 * Sempre que possível, dar preferência à fonte primária.
 */
public record Reference(
    String citation,
    String doi,
    String url
) {}
