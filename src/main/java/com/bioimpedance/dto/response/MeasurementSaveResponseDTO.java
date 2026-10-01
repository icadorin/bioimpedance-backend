package com.bioimpedance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Resposta do autosave (doc.md §17.8): issues de validação (avisos
 * não bloqueantes, DEC-56) + painel recalculado (doc.md §13, 9–11).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeasurementSaveResponseDTO {
    private List<ValidationIssueDTO> issues;
    private AssessmentPanelDTO panel;
}