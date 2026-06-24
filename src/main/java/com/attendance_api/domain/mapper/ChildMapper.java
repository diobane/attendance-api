package com.attendance_api.domain.mapper;

import com.attendance_api.api.dto.ChildDetailsResponseDTO;
import com.attendance_api.api.dto.ChildResponseDTO;
import com.attendance_api.api.dto.ResponsibleDTO;
import com.attendance_api.api.dto.TeamDTO;
import com.attendance_api.domain.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ChildMapper {
    //Search
    @Mapping(source = "team", target = "team")
    @Mapping(target = "responsiblePhone", expression = "java(firstResponsiblePhone(child))")
    ChildResponseDTO toDTO(Child child);

    List<ChildResponseDTO> toDTOList(List<Child> children);

    TeamDTO toDTO(Team team);

    ResponsibleDTO toDTO(Responsible responsible);

    //Child Details
    @Mapping(source = "family.responsibles", target = "responsibles")
    ChildDetailsResponseDTO toDetailsDTO(Child child);

    ChildDetailsResponseDTO.TeamDetails toTeamDetails(Team team);

    ChildDetailsResponseDTO.FamilyDetails toFamilyDetails(Family family);

    Set<ChildDetailsResponseDTO.ResponsibleDetails> toResponsibleDetailsSet(Set<Responsible> responsibles);

    ChildDetailsResponseDTO.ResponsibleDetails toResponsibleDetails(Responsible responsible);

    ChildDetailsResponseDTO.ResponsibleDetails.ContactDetails toContactDetails(Contact contact);

    default String firstResponsiblePhone(Child child) {
        if (child.getFamily() == null) return null;
        Set<Responsible> responsibles = child.getFamily().getResponsibles();
        if (responsibles == null || responsibles.isEmpty()) return null;
        Contact contact = responsibles.stream().toList().get(0).getContact();
        return contact != null ? contact.getPhone1() : null;
    }
}
