package com.attendance_api.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Data Transfer Object representing the filters used to search for one or more families")
public class FamilyFilterDTO {
    @Schema(description = "The ID of the family.", example = "98f53271-c215-2185-9116-f004271644a4")
    private String familyId;
}
