package memos.tutorials.customvalidation.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.validation.validator.AgeConstraintValidator;

import java.lang.annotation.*;

/**
 * The calculated age using annotated {@code LocalDate} must be between <b>min</b> (inclusive)
 * and <b>max</b> (exclusive).
 * <p><p>
 * <p>
 * The {@code min} attribute represents the minimum age value that is considered valid.
 * <p>
 * The {@code max} attribute represents the upper bound (exclusive) age value that is considered valid.
 * <p><p>
 * <p>
 * Annotated fields must satisfy the following conditions: <p>
 * - The {@code min} value must be less than or equal to the {@code max} value. <p>
 * - Both {@code min} and {@code max} values must be non-negative.
 * <p>
 * Throws a {@code RuntimeException}, if the annotated field does not meet these conditions.
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
@Constraint(validatedBy = AgeConstraintValidator.class)
public @interface Age {

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String message() default "It must be between {min} (inclusive) and {max} (exclusive)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
