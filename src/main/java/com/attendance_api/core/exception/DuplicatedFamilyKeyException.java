package com.attendance_api.core.exception;

public class DuplicatedFamilyKeyException extends RuntimeException {
    public DuplicatedFamilyKeyException(String message) {
        super(message);
    }
}
