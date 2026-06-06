package com.attendance_api.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object representing a family unit")
public class FamilyDTO {

    @Schema(description = "Internal database ID (generated automatically)", example = "1")
    private Long familyId;

    @NotBlank
    @Schema(description = "Unique alphanumeric business key for the family", requiredMode = Schema.RequiredMode.REQUIRED, example = "4d6c9e98-2962-47d7-8251-baac45225d28")
    private String familyKey;

    //@NotNull
    @Schema(description = "Flag used to check if the user saved the wahtsapp phone number")
    private Boolean addedWhatsForInfo;

    //@NotNull
    @Schema(description = "Flag used to check if the user agree with the info about the obligatory use of the badge by the child")
    private Boolean badgeAcknowledgment;

    //@NotNull
    @Schema(description = "Flag used to check if the user agree with the terms and policies")
    private Boolean userInfoAcknowledgment;

    @Valid
    private List<ChildDTO> children;

    @Valid
    private List<ResponsibleDTO> responsibles;
}