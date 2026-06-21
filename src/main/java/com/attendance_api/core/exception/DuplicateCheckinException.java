package com.attendance_api.core.exception;

public class DuplicateCheckinException extends RuntimeException {
    public DuplicateCheckinException(String message) {
        super(message);
    }
}