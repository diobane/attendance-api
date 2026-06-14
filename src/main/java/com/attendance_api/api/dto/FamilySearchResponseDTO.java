package com.attendance_api.api.dto;

import java.util.List;
import java.util.Set;

public record FamilySearchResponseDTO(
        Long familyId,
        String familyKey,
        List<ChildDetails> children,
        Set<ResponsibleDetails> responsibles
) {
    public record ChildDetails(
            Long childId,
            String fullName
    ) {}

    public record ResponsibleDetails(
            Long responsibleId,
            String fullName,
            ContactDetails contact
    ) {
        public record ContactDetails(
                Long contactId,
                String phone1,
                String phone2,
                String email
        ) {}
    }
}
