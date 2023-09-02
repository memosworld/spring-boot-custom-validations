package memos.tutorials.customvalidation.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.validation.validator.IntegerValuesConstraintValidator;

import java.lang.annotation.*;

/**
 * Annotated fields are validated to ensure that their values are one of the specified integer values.
 * <p>
 * The allowed integer values are specified as an array of integers using the {@code values} attribute.
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
@Constraint(validatedBy = IntegerValuesConstraintValidator.class)
public @interface IntegerValues {
    int[] values();

    String message() default "It must be one of {values}!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
