package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import memos.tutorials.customvalidation.controller.validation.ISO3166CountryCode;

import java.util.Locale;

public class ISO3166CountryCodeValidator implements ConstraintValidator<ISO3166CountryCode, String> {

    private ISO3166CountryCode.IsoCountryCodeType type;

    @Override
    public void initialize(ISO3166CountryCode constraintAnnotation) {
        type = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Locale.IsoCountryCode code = switch (type) {
            case ALPHA2 -> Locale.IsoCountryCode.PART1_ALPHA2;
            case ALPHA3 -> Locale.IsoCountryCode.PART1_ALPHA3;
        };

        return Locale.getISOCountries(code).contains(value.toUpperCase());
    }
}