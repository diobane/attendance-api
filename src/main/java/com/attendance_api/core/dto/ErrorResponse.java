package com.attendance_api.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Standard error payload returned by the API on failures.
 *
 * <p>Example (with validation errors):
 * <pre>{@code
 * {
 *   "title": "Bad Request",
 *   "statusCode": "400",
 *   "message": "Invalid fields",
 *   "details": [
 *     { "field": "age", "message": "Field cannot be null" }
 *   ]
 * }
 * }</pre>
 *
 * <p>The {@code details} field is only serialized when it is non-null,
 * i.e. it is omitted for errors that are not related to Bean Validation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String title,
        String statusCode,
        String message,
        List<ErrorDetail> details
) {

    /**
     * Convenience factory for responses without a {@code details} list
     * (e.g. generic 500 errors).
     */
    public static ErrorResponse of(String title, int statusCode, String message) {
        return new ErrorResponse(title, String.valueOf(statusCode), message, null);
    }

    /**
     * Convenience factory for responses that include a {@code details} list
     * (i.e. Bean Validation errors).
     */
    public static ErrorResponse of(String title, int statusCode, String message, List<ErrorDetail> details) {
        return new ErrorResponse(title, String.valueOf(statusCode), message, details);
    }
}
