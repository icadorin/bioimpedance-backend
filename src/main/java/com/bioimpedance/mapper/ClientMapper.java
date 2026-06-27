package com.bioimpedance.mapper;

import com.bioimpedance.dto.request.ClientRequestDTO;
import com.bioimpedance.dto.response.ClientResponseDTO;
import com.bioimpedance.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDate;
import java.time.Period;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
        // height, gender, birthDate, name, email, phone, goal, notes → mapeados automaticamente
    Client toEntity(ClientRequestDTO dto);

    // height incluído automaticamente pois existe em ambos
    @Mapping(target = "age", expression = "java(calculateAge(client.getBirthDate()))")
    ClientResponseDTO toResponse(Client client);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget Client client, ClientRequestDTO dto);

    default Integer calculateAge(LocalDate birthDate) {
        if (birthDate == null) return null;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
