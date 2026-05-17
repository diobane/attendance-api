package com.attendance_api.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object representing a child")
public class ChildDTO {

    @Schema(description = "Internal database ID for the child", example = "42")
    private Long childId;

    @NotBlank(message = "Child's full name is required")
    @Schema(description = "Full name of the child", requiredMode = Schema.RequiredMode.REQUIRED, example = "Fulaninho da Cunha Rocha")
    private String fullName;

    @PositiveOrZero(message = "Age must be a positive number or zero")
    @Schema(description = "Age of the child", example = "8")
    private Integer age;

    @Schema(description = "Name of the church the child attends, if any", example = "IASD Campo de Fora")
    private String attendsChurch;

    @Schema(description = "Medical or medication details if needed", example = "Takes dipyrone 2x a day for stomach pain")
    private String medicine;

    @Schema(description = "Any dietary restrictions or allergies", example = "Lactose intolerant. Cannot eat dairy products.")
    private String dietaryRestriction;

    @Schema(description = "General observations (e.g., behavioral or special needs)", example = "Autism spectrum disorder level 1")
    private String observation;
}