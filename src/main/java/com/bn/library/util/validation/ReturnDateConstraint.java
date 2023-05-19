package com.bn.library.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReturnDateValidator.class)
public @interface ReturnDateConstraint {
    String message() default "Invalid return date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int daysFromNow() default 7;
}
