package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import memos.tutorials.customvalidation.controller.validation.ISO3166CountryCode;

import java.util.Locale;

public class ISO3166CountryCodeValidator implements ConstraintValidator<ISO3166CountryCode, String> {

    private int letter;

    @Override
    public void initialize(ISO3166CountryCode constraintAnnotation) {
        letter = constraintAnnotation.letter();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Locale.IsoCountryCode code = switch (letter) {
            case 2 -> Locale.IsoCountryCode.PART1_ALPHA2;
            case 3 -> Locale.IsoCountryCode.PART1_ALPHA3;
            default -> throw new RuntimeException("Only 2 or 3 letter ISO 3166 country code can be validated!");
        };

        return Locale.getISOCountries(code).contains(value.toUpperCase());
    }
}