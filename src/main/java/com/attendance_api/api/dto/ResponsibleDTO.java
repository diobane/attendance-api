package com.attendance_api.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object representing a parent or guardian")
public class ResponsibleDTO {

    @Schema(description = "Internal database ID for the responsible person", example = "101")
    private Long responsibleId;

    @NotBlank(message = "Responsible's full name is required")
    @Schema(description = "Full name of the parent or guardian", requiredMode = Schema.RequiredMode.REQUIRED, example = "Paizinho da Cunha Velasquez")
    private String fullName;

    @Valid
    @Schema(description = "Contact details of the responsible person")
    private ContactDTO contact;
}
