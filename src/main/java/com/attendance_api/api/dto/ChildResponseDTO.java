package com.attendance_api.api.dto;

import lombok.Data;

@Data
public class ChildResponseDTO {
    private Long childId;
    private String fullName;
    private TeamDTO team;
}
