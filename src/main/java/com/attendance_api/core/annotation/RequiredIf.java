package com.attendance_api.core.annotation;

import com.attendance_api.core.validator.RequiredIfValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = RequiredIfValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RequiredIf.List.class)
public @interface RequiredIf {

    String message() default "Field is required";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /** Campo que será obrigado a ter valor */
    String field();

    /** Campo que será avaliado como condição */
    String dependsOn();

    /** Operador de comparação */
    Operator operator();

    /** Valor de referência para comparação (como String, convertido internamente) */
    String value();

    enum Operator {
        LESS_THAN,
        LESS_THAN_OR_EQUAL,
        GREATER_THAN,
        GREATER_THAN_OR_EQUAL,
        EQUAL,
        NOT_EQUAL
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        RequiredIf[] value();
    }
}