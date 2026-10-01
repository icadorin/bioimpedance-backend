package com.bioimpedance.dto.request;

import com.bioimpedance.constants.AssessmentMeasurementSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Autosave por medida (PUT /{id}/measurements/{inputId} — DEC-56/57).
 * value nulo remove a medida; source nulo = MANUAL.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpsertMeasurementRequestDTO {

    private Double value;
    private AssessmentMeasurementSource source;
}