package memos.tutorials.customvalidation.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.validation.validator.ISO3166CountryCodeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotated String value must be an ISO 3166 country code.
 * <p>
 * {@code letter} defines which alpha code to be used.
 * <p>
 * 2 -> Alpha-2 Code
 * <p>
 * 3 -> Alpha-3 Code
 * <p>
 * Any other value will result in {@code RuntimeException}
 * <p>
 * {@code null} elements are considered valid.
 * <p>
 *
 * @author Memo's Tutorial
 * @since 0.0.1
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ISO3166CountryCodeValidator.class)
public @interface ISO3166CountryCode {
    int letter() default 2;

    String message() default "It must be {letter}-letter ISO 3166 Country Code!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
