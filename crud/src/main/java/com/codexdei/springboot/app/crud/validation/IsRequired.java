package com.codexdei.springboot.app.crud.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

//Vincula la clase de validacion RequiredValidation que creamos
@Constraint(validatedBy = RequiredValidation.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface IsRequired {

    String message() default "++Required using annotations++";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
