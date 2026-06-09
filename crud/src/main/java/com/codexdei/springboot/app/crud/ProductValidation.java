package com.codexdei.springboot.app.crud;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.codexdei.springboot.app.crud.entities.Product;

@Component
public class ProductValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {

        return Product.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Product product = (Product) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", null /*"NotBlank.product.name"*/,"must not be blank.");

        if (product.getPrice() == null) {
            
            errors.rejectValue("price", null /*"NotNull.product.price" */,"must not be null.");
        
        }else if (product.getPrice() < 0) {
            
            errors.rejectValue("price", null /* "PositiveOrZero.product.price" */, "price=must be positive or zero.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", null /* "NotBlank.product.description"*/, "must not be blank");

        if (product.getDescription() == null || product.getDescription().length() < 3 || product.getDescription().length() > 5) {
         
            errors.rejectValue("description", null /*"Size.product.description"*/, "must be between {3} and {5} characters long.");
        }

    }

}
