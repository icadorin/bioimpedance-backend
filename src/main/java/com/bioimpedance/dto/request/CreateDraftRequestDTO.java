package com.bioimpedance.dto.request;

import com.bioimpedance.domain.contracts.AssessmentObjective;
import com.bioimpedance.domain.contracts.TrainingLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Cria o rascunho da avaliação (Fase 13 / B3 — DEC-52, doc.md §17.8).
 * Sexo e idade não vêm aqui: são resolvidos do Client (DEC-31).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDraftRequestDTO {

    @NotBlank
    private String clientId;

    @NotNull
    private LocalDate date;

    private AssessmentObjective objective;
    private TrainingLevel trainingLevel;
    private Boolean athlete;

    @Size(max = 80)
    private String modality;
}