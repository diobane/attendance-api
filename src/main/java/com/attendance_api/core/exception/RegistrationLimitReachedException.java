package com.attendance_api.core.exception;

public class RegistrationLimitReachedException extends RuntimeException {
    public RegistrationLimitReachedException(String message) {
        super(message);
    }
}
