package com.attendance_api.domain.mapper;

import com.attendance_api.api.dto.*;
import com.attendance_api.domain.entity.Child;
import com.attendance_api.domain.entity.Contact;
import com.attendance_api.domain.entity.Family;
import com.attendance_api.domain.entity.Responsible;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface FamilyMapper {
    Family toEntity(FamilyDTO dto);

    Child toEntity(ChildDTO dto);

    Responsible toEntity(ResponsibleDTO dto);

    Contact toEntity(ContactDTO dto);

    FamilyDTO toDto(Family entity);

    ChildDTO toDto(Child entity);

    ResponsibleDTO toDto(Responsible entity);

    ContactDTO toDto(Contact entity);

    @AfterMapping
    default void linkRelations(@MappingTarget Family family) {
        if (family.getChildren() != null) {
            family.getChildren().forEach(child -> child.setFamily(family));
        }
        if (family.getResponsibles() != null) {
            family.getResponsibles().forEach(responsible -> responsible.setFamily(family));
        }
    }

    FamilySearchResponseDTO toDTO(Family family);

    List<FamilySearchResponseDTO.ChildDetails> toChildDetailsList(List<Child> children);

    FamilySearchResponseDTO.ChildDetails toChildDetails(Child child);

    Set<FamilySearchResponseDTO.ResponsibleDetails> toResponsibleDetailsSet(Set<Responsible> responsibles);

    FamilySearchResponseDTO.ResponsibleDetails toResponsibleDetails(Responsible responsible);

    FamilySearchResponseDTO.ResponsibleDetails.ContactDetails toContactDetails(Contact contact);
}
