package com.attendance_api.core.exception;

import com.attendance_api.core.dto.ErrorDetail;
import com.attendance_api.core.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ErrorDetail> details = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    String field = (error instanceof FieldError fieldError)
                            ? fieldError.getField()
                            : error.getObjectName();
                    return new ErrorDetail(field, error.getDefaultMessage());
                })
                .toList();

        ErrorResponse body = ErrorResponse.of(
                "Bad Request",
                HttpStatus.BAD_REQUEST.value(),
                "Invalid fields",
                details
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        List<ErrorDetail> details = ex.getConstraintViolations()
                .stream()
                .map(violation -> {
                    // PropertyPath format: "methodName.paramName" — keep only the last segment
                    String path = violation.getPropertyPath().toString();
                    String field = path.contains(".")
                            ? path.substring(path.lastIndexOf('.') + 1)
                            : path;

                    return new ErrorDetail(field, violation.getMessage());
                })
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.of(
                        "Bad Request",
                        HttpStatus.BAD_REQUEST.value(),
                        "Invalid fields",
                        details
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ErrorResponse.of(
                        "Internal Server Error",
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "An unexpected error occurred. Please try again later or contact the administrator."
                )
        );
    }

    @ExceptionHandler(DuplicatedFamilyKeyException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedFamilyKeyException(DuplicatedFamilyKeyException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.of(
                        "Bad Request",
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage()
                )
        );
    }

    @ExceptionHandler(DuplicateCheckinException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCheckin(DuplicateCheckinException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ErrorResponse.of("Conflict", HttpStatus.CONFLICT.value(), ex.getMessage())
        );
    }

    @ExceptionHandler(FamilyMismatchException.class)
    public ResponseEntity<ErrorResponse> handleFamilyMismatchException(FamilyMismatchException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(
                ErrorResponse.of("Unprocessable Content", HttpStatus.UNPROCESSABLE_CONTENT.value(), ex.getMessage())
        );
    }

    @ExceptionHandler(RegistrationLimitReachedException.class)
    public ResponseEntity<ErrorResponse> handleRegistrationLimitReached(RegistrationLimitReachedException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(
                ErrorResponse.of("Unprocessable Content", HttpStatus.UNPROCESSABLE_CONTENT.value(), ex.getMessage())
        );
    }
}
