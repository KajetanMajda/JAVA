package com.projekt.JAZ.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class ShirtNumberValidator implements ConstraintValidator<ShirtNumber, Integer> {

    
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) return false;
        if(value > 0 && value < 100)
        return true;
        else return false;
    }

}