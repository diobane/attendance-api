package com.attendance_api.core.enums;

public enum Role {
    ADMIN,
    MEMBER;

    @Override
    public String toString() {
        return this.name();
    }
}
