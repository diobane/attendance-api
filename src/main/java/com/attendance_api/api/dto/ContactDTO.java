package com.attendance_api.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object representing contact information")
public class ContactDTO {

    @Schema(description = "Internal database ID for the contact", example = "202")
    private Long contactId;

    @Email(message = "Please provide a valid email address")
    @Schema(description = "Primary email address", requiredMode = Schema.RequiredMode.REQUIRED, example = "paizinho@email.com")
    private String email;

    @NotBlank(message = "Primary phone number is required")
    @Schema(description = "Primary phone number (preferably WhatsApp)", requiredMode = Schema.RequiredMode.REQUIRED, example = "11976788390")
    private String phone1;

    @Schema(description = "Secondary or alternative phone number", example = "11954225031")
    private String phone2;
}
