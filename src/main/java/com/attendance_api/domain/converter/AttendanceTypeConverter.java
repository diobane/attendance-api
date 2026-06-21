package com.attendance_api.domain.converter;

import com.attendance_api.domain.enums.AttendanceType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AttendanceTypeConverter implements AttributeConverter<AttendanceType, String> {
    @Override
    public String convertToDatabaseColumn(AttendanceType attribute) {
        return attribute == null ? null : String.valueOf(attribute.getCode());
    }

    @Override
    public AttendanceType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : AttendanceType.fromCode(dbData.charAt(0));
    }
}