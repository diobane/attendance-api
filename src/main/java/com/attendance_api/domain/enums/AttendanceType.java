package com.attendance_api.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AttendanceType {
    CHECKIN('1'),
    CHECKOUT('2');

    private final char code;

    public static AttendanceType fromCode(char code) {
        for (AttendanceType type : values()) {
            if (type.code == code) return type;
        }
        throw new IllegalArgumentException("Unknown attendance type code: " + code);
    }
}
