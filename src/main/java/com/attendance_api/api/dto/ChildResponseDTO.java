package com.attendance_api.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChildResponseDTO {
    private Long childId;
    private String fullName;
    private Integer age;
    private String attendsChurch;
    private String medicine;
    private String dietaryRestriction;
    private String observation;
    private String guardianName;
    private Boolean isAdventist;
    private Long familyId;
    private TeamDTO team;
    private List<ResponsibleDTO> responsibles;
}
