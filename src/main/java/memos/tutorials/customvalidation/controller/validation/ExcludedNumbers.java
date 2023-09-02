package memos.tutorials.customvalidation.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.validation.validator.ExcludedNumbersConstraintValidator;

import java.lang.annotation.*;

/**
 * Annotated fields are validated to ensure that their values are not one of the specified excluded numbers.
 * <p>
 * Excluded numbers are specified as an array of integers using the {@code excludedNumbers} attribute.
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
@Constraint(validatedBy = ExcludedNumbersConstraintValidator.class)
public @interface ExcludedNumbers {
    int[] excludedNumbers();

    String message() default "It cannot be one of {excludedNumbers}!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
