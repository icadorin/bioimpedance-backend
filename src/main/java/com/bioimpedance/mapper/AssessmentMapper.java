package com.bioimpedance.mapper;

import com.bioimpedance.constants.AssessmentMethod;
import com.bioimpedance.dto.request.AssessmentRequestDTO;
import com.bioimpedance.dto.request.CalculateRequestDTO;
import com.bioimpedance.dto.response.*;
import com.bioimpedance.entity.Assessment;
import com.bioimpedance.entity.AssessmentResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AssessmentMapper {

    @Mapping(target = "navy", expression = "java(mapToNavy(assessment))")
    @Mapping(target = "bioimpedance", expression = "java(mapToBioimpedance(assessment))")
    @Mapping(target = "skinfold", expression = "java(mapToSkinfold(assessment))")
    AssessmentResponseDTO toResponse(Assessment assessment);

    @Mapping(target = "result", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Assessment toEntity(AssessmentRequestDTO dto);

    @Mapping(target = "methodDetails", ignore = true)
    @Mapping(target = "protein", ignore = true)
    @Mapping(target = "carbs", ignore = true)
    @Mapping(target = "fat", ignore = true)
    @Mapping(target = "trainingType", ignore = true)
    @Mapping(target = "cardio", ignore = true)
    CalculationResultDTO toCalculationResultDTO(AssessmentResult result);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "observations", ignore = true)
    @Mapping(target = "result", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Assessment toEntityFromCalculate(CalculateRequestDTO dto);

    default NavyDataDTO mapToNavy(Assessment a) {
        if (a.getMethod() != AssessmentMethod.NAVY) return null;
        return NavyDataDTO.builder()
            .waist(a.getWaist())
            .neck(a.getNeck())
            .hip(a.getHip())
            .build();
    }

    default BioimpedanceDataDTO mapToBioimpedance(Assessment a) {
        if (a.getMethod() != AssessmentMethod.BIOIMPEDANCE) return null;
        return BioimpedanceDataDTO.builder()
            .resistance(a.getResistance())
            .reactance(a.getReactance())
            .build();
    }

    default SkinfoldDataDTO mapToSkinfold(Assessment a) {
        if (a.getMethod() != AssessmentMethod.SKINFOLD) return null;
        return SkinfoldDataDTO.builder()
            .protocol(a.getProtocol())
            .biceps(a.getBiceps())
            .chest(a.getChest())
            .midaxillary(a.getMidaxillary())
            .triceps(a.getTriceps())
            .subscapular(a.getSubscapular())
            .abdominal(a.getAbdominal())
            .suprailiac(a.getSuprailiac())
            .thigh(a.getThigh())
            .build();
    }
}
