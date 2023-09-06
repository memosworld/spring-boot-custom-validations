package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import memos.tutorials.customvalidation.controller.validation.DivisibleBy;

public class DivisibleByConstraintValidator implements ConstraintValidator<DivisibleBy, Integer> {
    int divider;

    @Override
    public void initialize(DivisibleBy constraintAnnotation) {
        this.divider = constraintAnnotation.divider();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        return value % divider == 0;
    }
}
