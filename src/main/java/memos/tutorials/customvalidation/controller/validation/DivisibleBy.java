package memos.tutorials.customvalidation.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.validation.validator.DivisibleByConstraintValidator;

import java.lang.annotation.*;

/**
 * Annotated fields are validated to ensure that their values are divisible by specified integer value.
 * <p>
 * The divider integer value is specified as an integer value using the {@code divider} attribute.
 * <p>
 * {@code null} elements are considered valid.
 * <p>
 *
 * @author <i> Memo's Tutorial</i>
 * @since 0.0.1
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = DivisibleByConstraintValidator.class)
public @interface DivisibleBy {
    int divider();

    String message() default "It must be divisible by {divider}!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
