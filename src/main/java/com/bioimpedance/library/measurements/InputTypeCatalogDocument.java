package com.bioimpedance.library.measurements;

import java.util.List;

/** Wrapper do arquivo input-types.yaml. */
public record InputTypeCatalogDocument(List<InputTypeDefinition> inputTypes) {
    public InputTypeCatalogDocument {
        inputTypes = List.copyOf(inputTypes);
    }
}