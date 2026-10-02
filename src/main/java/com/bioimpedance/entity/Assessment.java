package com.bioimpedance.entity;

import com.bioimpedance.constants.AssessmentMeasurementSource;
import com.bioimpedance.constants.AssessmentStatus;
import com.bioimpedance.constants.Gender;
import com.bioimpedance.domain.contracts.AssessmentObjective;
import com.bioimpedance.domain.contracts.TrainingLevel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "assessments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String userId;
    private String clientId;

    @Column(nullable = false)
    private LocalDate date;

    /**
     * Ciclo de vida (Fase 13 — DEC-52/58/61). Default FINALIZED:
     * todas as avaliações existentes já são salvas (migration manual),
     * e o fluxo legado continua criando avaliações finalizadas.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private AssessmentStatus status = AssessmentStatus.FINALIZED;

    // ── Contexto da avaliação (DEC-52: persistido no rascunho) ──
    @Enumerated(EnumType.STRING)
    @Column(name = "objective")
    private AssessmentObjective objective;

    @Enumerated(EnumType.STRING)
    @Column(name = "training_level")
    private TrainingLevel trainingLevel;

    @Column(name = "athlete")
    private Boolean athlete;

    @Column(name = "modality", length = 80)
    private String modality;

    // ── Colunas fixas legacy (não escritas desde a Fase 12 — DEC-36) ──
    private Double weight;
    private Double height;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Double waist;
    private Double neck;
    private Double hip;
    private Double resistance;
    private Double reactance;
    private String protocol;
    private Double biceps;
    private Double chest;
    private Double midaxillary;
    private Double triceps;
    private Double subscapular;
    private Double abdominal;
    private Double suprailiac;
    private Double thigh;

    @Embedded
    private AssessmentResult result;

    private String observations;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssessmentMeasurement> measurements = new ArrayList<>();

    public void addMeasurement(String inputId, Double value) {
        addMeasurement(inputId, value, AssessmentMeasurementSource.MANUAL);
    }

    /** Upsert em memória (DEC-9/56): mesmo inputId substitui a medida. */
    public void addMeasurement(String inputId, Double value, AssessmentMeasurementSource source) {
        measurements.removeIf(m -> m.getInputId().equals(inputId));
        AssessmentMeasurement m = new AssessmentMeasurement(inputId, value, source);
        m.setAssessment(this);
        measurements.add(m);
    }

    public Map<String, Double> toInputMap() {
        return measurements.stream()
            .collect(Collectors.toMap(
                AssessmentMeasurement::getInputId,
                AssessmentMeasurement::getValue,
                (a, b) -> {
                    throw new IllegalStateException("Medida duplicada para inputId: " + a);
                }
            ));
    }

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}