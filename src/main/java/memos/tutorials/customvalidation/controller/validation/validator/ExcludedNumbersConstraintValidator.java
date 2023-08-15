package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import memos.tutorials.customvalidation.controller.validation.ExcludedNumbers;

import java.util.stream.IntStream;

public class ExcludedNumbersConstraintValidator implements ConstraintValidator<ExcludedNumbers, Integer> {

    private int[] excludedNumbers;

    @Override
    public void initialize(ExcludedNumbers constraintAnnotation) {
        this.excludedNumbers = constraintAnnotation.excludedNumbers();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return IntStream.of(excludedNumbers).noneMatch(num -> num == value);
    }
}