package com.attendance_api.domain.mapper;

import com.attendance_api.api.dto.TeamDTO;
import com.attendance_api.domain.entity.Team;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    TeamDTO toDTO(Team team);
    List<TeamDTO> toDTOList(List<Team> teams);
}
