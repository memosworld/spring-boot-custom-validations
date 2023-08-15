package memos.tutorials.customvalidation.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.validation.validator.AtLeastOneNotBlankConstraintValidator;

import java.lang.annotation.*;

/**
 * The annotated Integer value must be contained by the provided {@code fields}.
 * <p>
 * {@code null} elements are considered valid.
 * <p>
 *
 * @author Memo's Tutorial
 * @since 0.0.1
 */

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneNotBlankConstraintValidator.class)
public @interface AtLeastOneNotBlank {

    String[] fields();

    String message() default "At least one of {fields} must be present!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
