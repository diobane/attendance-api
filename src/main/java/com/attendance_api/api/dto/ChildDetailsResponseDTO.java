package com.attendance_api.api.dto;

import java.util.Set;

public record ChildDetailsResponseDTO(
        Long childId,
        String fullName,
        Integer age,
        String attendsChurch,
        String medicine,
        String dietaryRestriction,
        String observation,
        String guardianName,
        Boolean isAdventist,
        TeamDetails team,
        FamilyDetails family,
        Set<ResponsibleDetails> responsibles
) {
    public record TeamDetails(
            Long teamId,
            String name,
            String color
    ) {}

    public record FamilyDetails(
            Long familyId,
            String familyKey
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
