package com.attendance_api.api.dto;

import java.time.LocalDateTime;

/** Um registro de presença (entrada/saída) de uma criança. */
public record AttendanceResponseDTO(
        LocalDateTime createdAt,
        String type
) {
}
