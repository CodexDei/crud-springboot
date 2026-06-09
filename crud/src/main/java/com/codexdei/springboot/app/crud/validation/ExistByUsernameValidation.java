package com.codexdei.springboot.app.crud.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codexdei.springboot.app.crud.services.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExistByUsernameValidation implements ConstraintValidator<ExistByUsername,String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {

        if (userService == null) {
            System.out.println("Service: " + userService);
            return true;
        }

        return !userService.existByUsername(username);
    }

    

}
