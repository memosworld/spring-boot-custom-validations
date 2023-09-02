package memos.tutorials.customvalidation.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.validation.validator.AtLeastOneNotBlankConstraintValidator;

import java.lang.annotation.*;

/**
 * Annotated classes are validated to ensure that at least one of the designated fields contains a non-blank (non-empty) value.
 * Fields are specified as an array of field names using the {@code fields} attribute.
 * <p>
 * Usage Example:
 * <pre>
 * {@literal @}AtLeastOneNotBlank(fields = {"firstName", "lastName"})
 * public class Person {
 *     private String firstName;
 *     private String lastName;
 *     // ...
 * }
 * </pre>
 * Throws a {@code RuntimeException} if the specified field names are invalid (non-existent, inaccessible, or not of type String).
 * <p>
 * @author <i> Memo's Tutorial</i>
 * @since 0.0.1
 */

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneNotBlankConstraintValidator.class)
public @interface AtLeastOneNotBlank {

    /**
     * Specifies the array of field names to be validated.
     *
     * @return The array of field names.
     */
    String[] fields();

    String message() default "At least one of {fields} must be present!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
