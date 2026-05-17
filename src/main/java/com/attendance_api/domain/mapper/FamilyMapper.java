package com.attendance_api.domain.mapper;

import com.attendance_api.api.dto.ChildDTO;
import com.attendance_api.api.dto.ContactDTO;
import com.attendance_api.api.dto.FamilyDTO;
import com.attendance_api.api.dto.ResponsibleDTO;
import com.attendance_api.domain.entity.Child;
import com.attendance_api.domain.entity.Contact;
import com.attendance_api.domain.entity.Family;
import com.attendance_api.domain.entity.Responsible;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

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
}
