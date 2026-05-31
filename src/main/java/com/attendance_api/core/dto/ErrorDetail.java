package com.attendance_api.core.dto;

/**
 * Represents a single field validation error detail.
 * Used inside the {@link ErrorResponse} payload when Bean Validation errors occur.
 */
public record ErrorDetail(
        String field,
        String message
) {}
