package com.attendance_api.api.dto;

import com.attendance_api.core.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;

    @NotNull
    private Role role;
}
