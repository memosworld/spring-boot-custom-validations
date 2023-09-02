package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import memos.tutorials.customvalidation.controller.validation.Age;

import java.time.LocalDate;
import java.time.Period;

public class AgeConstraintValidator implements ConstraintValidator<Age, LocalDate> {
    int min;

    int max;

    @Override
    public void initialize(Age constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        if (min < 0 || max < 0 || min > max) {
            throw new RuntimeException(
                    "Invalid @Age annotation configuration: min and max must be non-negative, and min must be less than or equal to max.");
        }

        int age = Period.between(value, LocalDate.now()).getYears();

        return age >= min && age < max;
    }
}
