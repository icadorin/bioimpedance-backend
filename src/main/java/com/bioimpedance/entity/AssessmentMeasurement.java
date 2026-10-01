package com.bioimpedance.entity;

import com.bioimpedance.constants.AssessmentMeasurementSource;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * DEC-9: medida individual de uma avaliação, no formato inputId → valor.
 * A unique constraint (assessment_id, input_id) é integridade, não
 * otimização — retry/double-submit não pode criar duplicata.
 *
 * DEC-12: coluna física "measurement_value" — "value" é palavra reservada
 * em vários dialetos SQL (H2 incluso). O campo Java continua "value".
 *
 * DEC-57: origem da medida (MANUAL | AVALIACAO_ANTERIOR), default MANUAL.
 */
@Entity
@Table(
    name = "assessment_measurements",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_assessment_measurement_assessment_input",
        columnNames = {"assessment_id", "input_id"}
    )
)
@Getter
@Setter
@NoArgsConstructor
public class AssessmentMeasurement {

    @Id
    private String id = UUID.randomUUID().toString();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

    /** inputId canônico do InputTypeCatalog (ex.: SKINFOLD_TRICEPS). */
    @Column(name = "input_id", nullable = false, length = 64)
    private String inputId;

    /** DEC-12: coluna física measurement_value (VALUE é reservado em SQL). */
    @Column(name = "measurement_value", nullable = false)
    private Double value;

    /** DEC-57: origem da medida. */
    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false, length = 32)
    private AssessmentMeasurementSource source = AssessmentMeasurementSource.MANUAL;

    public AssessmentMeasurement(String inputId, Double value) {
        this(inputId, value, AssessmentMeasurementSource.MANUAL);
    }

    public AssessmentMeasurement(String inputId, Double value, AssessmentMeasurementSource source) {
        this.inputId = inputId;
        this.value = value;
        this.source = source;
    }
}