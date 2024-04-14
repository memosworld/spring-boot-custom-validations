package memos.tutorials.customvalidation.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.validation.validator.ConditionalMandatoryConstraintValidator;

import java.lang.annotation.*;

/**
 * Annotated classes are validated to unsure that a field is mandatory only under specific conditions based on the value of another field.
 * <p>
 * {@code field} The name of the field whose value determines if other fields are mandatory.
 * {@code values} An array of values for the `field` that trigger the mandatory requirement.
 * {@code requires} An array of field names that become mandatory when the `field` has one of the specified `values`.
 * <p>
 * Usage Example:
 * <pre>
 * {@literal @}ConditionalMandatory(field = "field1", values = {"a", "b"}, requires = {"field2", "field3"})
 * public class Person {
 *     private String field1;
 *     private String field2;
 *     private String field3;
 *     // ...
 * }
 * </pre>
 * </p>
 *
 * @author <i> Memo's Tutorial</i>
 * @since 0.0.1
 */

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ConditionalMandatories.class)
@Constraint(validatedBy = ConditionalMandatoryConstraintValidator.class)
public @interface ConditionalMandatory {

    String field();

    String[] values();

    String[] requires();

    String message() default "{requires} must be present when {field} values are {values}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
