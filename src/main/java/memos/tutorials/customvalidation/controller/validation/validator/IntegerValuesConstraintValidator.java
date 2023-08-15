package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import memos.tutorials.customvalidation.controller.validation.IntegerValues;

import java.util.Arrays;

public class IntegerValuesConstraintValidator implements ConstraintValidator<IntegerValues, Integer> {

    private int[] values;

    @Override
    public void initialize(IntegerValues constraintAnnotation) {
        values = constraintAnnotation.values();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value == null || Arrays.stream(values).anyMatch(value::equals);
    }
}
