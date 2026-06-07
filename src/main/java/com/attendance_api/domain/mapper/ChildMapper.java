package com.attendance_api.domain.mapper;

import com.attendance_api.api.dto.ChildResponseDTO;
import com.attendance_api.api.dto.ResponsibleDTO;
import com.attendance_api.api.dto.TeamDTO;
import com.attendance_api.domain.entity.Child;
import com.attendance_api.domain.entity.Responsible;
import com.attendance_api.domain.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChildMapper {

    @Mapping(source = "family.familyId", target = "familyId")
    @Mapping(source = "family.responsibles", target = "responsibles")
    @Mapping(source = "team", target = "team")
    ChildResponseDTO toDTO(Child child);

    List<ChildResponseDTO> toDTOList(List<Child> children);

    TeamDTO toDTO(Team team);

    ResponsibleDTO toDTO(Responsible responsible);
}
