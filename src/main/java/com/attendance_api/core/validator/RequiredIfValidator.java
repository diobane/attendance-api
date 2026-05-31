package com.attendance_api.core.validator;

import com.attendance_api.core.annotation.RequiredIf;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class RequiredIfValidator implements ConstraintValidator<RequiredIf, Object> {

    private String field;
    private String dependsOn;
    private RequiredIf.Operator operator;
    private String value;
    private String message;

    @Override
    public void initialize(RequiredIf annotation) {
        this.field = annotation.field();
        this.dependsOn = annotation.dependsOn();
        this.operator = annotation.operator();
        this.value = annotation.value();
        this.message = annotation.message();
    }

    @Override
    public boolean isValid(Object target, ConstraintValidatorContext context) {
        try {
            Object dependsOnValue = getFieldValue(target, dependsOn);
            Object fieldValue = getFieldValue(target, field);

            if (conditionMatches(dependsOnValue) && isEmpty(fieldValue)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message)
                        .addPropertyNode(field)
                        .addConstraintViolation();
                return false;
            }

            return true;

        } catch (Exception e) {
            throw new RuntimeException("Error evaluating @RequiredIf on field '%s'".formatted(field), e);
        }
    }

    private Object getFieldValue(Object target, String fieldName) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(target);
    }

    private boolean conditionMatches(Object dependsOnValue) {
        if (dependsOnValue == null) return false;

        if (dependsOnValue instanceof Number number) {
            double actual = number.doubleValue();
            double reference = Double.parseDouble(value);
            return switch (operator) {
                case LESS_THAN             -> actual < reference;
                case LESS_THAN_OR_EQUAL    -> actual <= reference;
                case GREATER_THAN          -> actual > reference;
                case GREATER_THAN_OR_EQUAL -> actual >= reference;
                case EQUAL                 -> actual == reference;
                case NOT_EQUAL             -> actual != reference;
            };
        }

        if (dependsOnValue instanceof String str) {
            return switch (operator) {
                case EQUAL     -> str.equals(value);
                case NOT_EQUAL -> !str.equals(value);
                default -> throw new IllegalArgumentException(
                        "Operator %s is not supported for String fields".formatted(operator));
            };
        }

        if (dependsOnValue instanceof Boolean bool) {
            return switch (operator) {
                case EQUAL     -> bool == Boolean.parseBoolean(value);
                case NOT_EQUAL -> bool != Boolean.parseBoolean(value);
                default -> throw new IllegalArgumentException(
                        "Operator %s is not supported for Boolean fields".formatted(operator));
            };
        }

        throw new IllegalArgumentException(
                "Unsupported field type: %s".formatted(dependsOnValue.getClass().getSimpleName()));
    }

    private boolean isEmpty(Object value) {
        if (value == null) return true;
        if (value instanceof String str) return str.isBlank();
        return false;
    }
}