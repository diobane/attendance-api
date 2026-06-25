package com.attendance_api.api.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class ChildResponseDTO {
    private Long childId;
    private String fullName;
    private TeamDTO team;
    private ChildDetailsResponseDTO.FamilyDetails family;
    private Set<ChildDetailsResponseDTO.ResponsibleDetails> responsibles;
    private LocalDateTime checkinAt;
    private LocalDateTime checkoutAt;
}
