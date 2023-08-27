package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.validation.ISO3166CountryCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.annotation.Annotation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ISO3166CountryCodeValidatorTest {
    AutoCloseable closeable;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private ISO3166CountryCodeValidator validatorUnderTest;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        validatorUnderTest = new ISO3166CountryCodeValidator();
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void shouldPassWhenGivenCountry2LetterCodeValid() {
        // given
        validatorUnderTest.initialize(createISO3166CountryCodeAnnotation(2));

        // when
        boolean result = validatorUnderTest.isValid("HR", constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldPassWhenGivenCountry3LetterCodeValid() {
        // given
        validatorUnderTest.initialize(createISO3166CountryCodeAnnotation(3));

        // when
        boolean result = validatorUnderTest.isValid("TUR", constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldPassWhenNullValue() {
        // given
        validatorUnderTest.initialize(createISO3166CountryCodeAnnotation(2));

        // when
        boolean result = validatorUnderTest.isValid(null, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldFailWhen2LetterCountryCodeInvalid() {
        // given
        validatorUnderTest.initialize(createISO3166CountryCodeAnnotation(2));

        // when
        boolean result = validatorUnderTest.isValid("AA", constraintValidatorContext);

        // then
        assertThat(result, is(false));
    }

    @Test
    void shouldFailWhen3LetterCountryCodeInvalid() {
        // given
        validatorUnderTest.initialize(createISO3166CountryCodeAnnotation(2));

        // when
        boolean result = validatorUnderTest.isValid("AAA", constraintValidatorContext);

        // then
        assertThat(result, is(false));
    }

    @Test
    void shouldThrowExceptionWhenWrongLetterNumberProvided() {
        // given
        validatorUnderTest.initialize(createISO3166CountryCodeAnnotation(1));

        // then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> validatorUnderTest.isValid("",
                                                                                                           constraintValidatorContext));
        assertThat(exception.getMessage(), is("Only 2 or 3 letter ISO 3166 country code can be validated!"));
    }

    private ISO3166CountryCode createISO3166CountryCodeAnnotation(int letter) {
        return new ISO3166CountryCode() {
            @Override
            public Class<?>[] groups() {
                return new Class<?>[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return new Class[0];
            }

            public int letter() {
                return letter;
            }

            @Override
            public String message() {
                return "It must be {letter}-letter ISO 3166 Country Code!";
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return ISO3166CountryCode.class;
            }
        };
    }
}
