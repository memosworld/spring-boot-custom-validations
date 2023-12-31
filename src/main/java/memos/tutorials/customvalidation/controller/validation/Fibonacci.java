package memos.tutorials.customvalidation.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.validation.validator.FibonacciConstraintValidator;

import java.lang.annotation.*;

/**
 * Annotated fields are validated to ensure that their values represent numbers that are part of the <b>Fibonacci</b> series.
 * <p>
 * {@code null} elements are considered valid.
 * <p>
 *
 * @author <i> Memo's Tutorial</i>
 * @since 0.0.1
 */

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FibonacciConstraintValidator.class)
public @interface Fibonacci {
    String message() default "It must be a number within the Fibonacci series!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
