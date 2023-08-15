package memos.tutorials.customvalidation.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.validation.validator.ExcludedNumbersConstraintValidator;

import java.lang.annotation.*;

/**
 * The annotated Integer value cannot be any value defined in {@code excludedNumbers}.
 * <p>
 * {@code null} elements are considered valid.
 * <p>
 *
 * @author Memo's Tutorial
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
