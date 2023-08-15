package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import memos.tutorials.customvalidation.controller.validation.Fibonacci;

public class FibonacciConstraintValidator implements ConstraintValidator<Fibonacci, Long> {
    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return isFibonacci(value, 0, 1);
    }

    private boolean isFibonacci(Long value, long a, long b) {

        if (value == a || value == b) {
            return true;
        }

        long nextFibonacci = a + b;
        if (nextFibonacci > value) {
            return false;
        }

        return isFibonacci(value, nextFibonacci, nextFibonacci + b);
    }
}