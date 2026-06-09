package com.codexdei.springboot.app.crud.validation;

import org.springframework.util.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RequiredValidation implements ConstraintValidator<IsRequired,String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        //return (value != null && !value.isEmpty() && !value.isEmpty());
        //hace lo mismo que la linea de codigo anterior
        return StringUtils.hasText(value);
    }

    

}
