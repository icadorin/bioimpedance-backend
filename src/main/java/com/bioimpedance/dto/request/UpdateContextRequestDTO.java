package com.bioimpedance.dto.request;

import com.bioimpedance.domain.contracts.AssessmentObjective;
import com.bioimpedance.domain.contracts.TrainingLevel;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Atualiza o contexto do rascunho (PATCH /{id}/context — DEC-52).
 * date nulo = mantém; campos de contexto nulos = limpa (o front
 * sempre envia o contexto completo).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateContextRequestDTO {

    private LocalDate date;
    private AssessmentObjective objective;
    private TrainingLevel trainingLevel;
    private Boolean athlete;

    @Size(max = 80)
    private String modality;
}