package com.attendance_api.core.exception;

public class FamilyMismatchException extends RuntimeException {
    public FamilyMismatchException(String message) {
        super(message);
    }
}
