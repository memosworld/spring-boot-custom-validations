package memos.tutorials.customvalidation.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.validation.validator.IntegerValuesConstraintValidator;

import java.lang.annotation.*;

/**
 * The annotated Integer value must be contained by the provided {@code values}.
 * <p>
 * {@code null} elements are considered valid.
 * <p>
 *
 * @author Memo's Tutorial
 * @since 0.0.1
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Constraint(validatedBy = IntegerValuesConstraintValidator.class)
public @interface IntegerValues {
    int[] values();

    String message() default "It must be one of {values}!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
