package com.attendance_api.domain.enums;

public enum Role {
    ADMIN,
    MEMBER;

    @Override
    public String toString() {
        return this.name();
    }
}
