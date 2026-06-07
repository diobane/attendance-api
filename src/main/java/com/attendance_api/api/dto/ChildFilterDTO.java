package com.attendance_api.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Data Transfer Object representing the filters used to search for one or more children")
public class ChildFilterDTO {
    @Schema(description = "The name of the child. Can be the full name or a chunk of it", example = "John Doe")
    private String fullName;
}
