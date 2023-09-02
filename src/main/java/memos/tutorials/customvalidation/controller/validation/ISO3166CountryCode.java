package memos.tutorials.customvalidation.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.validation.validator.ISO3166CountryCodeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotated fields are validated to ensure that their values represent a valid ISO 3166-1 country code.
 * <p>
 * The type of country code to validate (either alpha-2 or alpha-3) can be specified using the {@code type} attribute.
 * <p>
 * The annotation supports two types of ISO 3166-1 country codes:
 * <p>
 * - {@code ALPHA2} is used to represent the ISO3166-1 alpha-2 two-letter country codes.
 * <p>
 * - {@code ALPHA3} is used to represent the ISO3166-1 alpha-3 three-letter country codes.
 * <p>
 * {@code null} elements are considered valid.
 * <p>
 *
 * @author <i> Memo's Tutorial</i>
 * @since 0.0.1
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ISO3166CountryCodeValidator.class)
public @interface ISO3166CountryCode {
    IsoCountryCodeType type() default IsoCountryCodeType.ALPHA2;

    enum IsoCountryCodeType {
        /**
         * ALPHA2 is used to represent the ISO3166-1 alpha-2 two-letter country codes.
         */
        ALPHA2,
        /**
         * ALPHA3 is used to represent the ISO3166-1 alpha-3 three-letter country codes.
         */
        ALPHA3
    }

    String message() default "It must be ISO 3166-1 {type} country code!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
