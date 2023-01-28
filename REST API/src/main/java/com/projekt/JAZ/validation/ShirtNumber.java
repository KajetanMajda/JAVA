package com.projekt.JAZ.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ShirtNumberValidator.class)

public @interface ShirtNumber {

    String message() default "The shirtNumber is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
    