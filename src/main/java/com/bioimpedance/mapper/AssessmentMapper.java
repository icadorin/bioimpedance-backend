package com.bioimpedance.mapper;

import com.bioimpedance.dto.request.AssessmentRequestDTO;
import com.bioimpedance.dto.response.AssessmentResponseDTO;
import com.bioimpedance.entity.Assessment;
import com.bioimpedance.entity.AssessmentMeasurement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Mapper de Assessment (Chunk 5 — DEC-45).
 * Os blocos legacy navy/bioimpedance/skinfold foram removidos.
 * O novo fluxo usa AssessmentFlowService + CalculationFlowResponseDTO.
 */
@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AssessmentMapper {

    @Mapping(target = "clientName", ignore = true)
    AssessmentResponseDTO toResponse(Assessment assessment);

    @Mapping(target = "result", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Assessment toEntity(AssessmentRequestDTO dto);

    /**
     * Converte List<AssessmentMeasurement> → Map<inputId, valor>.
     * O MapStruct usa este método automaticamente quando encontra
     * a conversão de List<AssessmentMeasurement> para Map<String, Double>.
     */
    default Map<String, Double> map(List<AssessmentMeasurement> measurements) {
        if (measurements == null || measurements.isEmpty()) {
            return Map.of();
        }
        return measurements.stream()
            .collect(Collectors.toMap(
                AssessmentMeasurement::getInputId,
                AssessmentMeasurement::getValue,
                (existing, replacement) -> replacement
            ));
    }
}