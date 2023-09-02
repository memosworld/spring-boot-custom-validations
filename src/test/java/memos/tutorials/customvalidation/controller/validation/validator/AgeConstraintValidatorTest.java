package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.validation.Age;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.annotation.Annotation;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AgeConstraintValidatorTest {

    AutoCloseable closeable;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private AgeConstraintValidator validatorUnderTest;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        validatorUnderTest = new AgeConstraintValidator();
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void shouldPassWhenAgeBetweenMinAndMax() {
        // given
        validatorUnderTest.initialize(createAgeAnnotation(18, 65));

        // when
        boolean result = validatorUnderTest.isValid(LocalDate.of(1990, 1, 1), constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldPassWhenAgeEqualToMin() {
        // given
        validatorUnderTest.initialize(createAgeAnnotation(18, 65));
        LocalDate minAge = LocalDate.now().minusYears(18);

        // when
        boolean result = validatorUnderTest.isValid(minAge, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldPassWhenNull() {
        // given
        validatorUnderTest.initialize(createAgeAnnotation(18, 65));

        // when
        boolean result = validatorUnderTest.isValid(null, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldFailWhenAgeLessThanMin() {
        // given
        validatorUnderTest.initialize(createAgeAnnotation(18, 65));

        // when
        boolean result = validatorUnderTest.isValid(LocalDate.now(), constraintValidatorContext);

        // then
        assertThat(result, is(false));
    }

    @Test
    void shouldFailWhenAgeGreaterThanOrEqualMax() {
        // given
        validatorUnderTest.initialize(createAgeAnnotation(18, 65));

        // when
        boolean result = validatorUnderTest.isValid(LocalDate.of(1900, 1, 1), constraintValidatorContext);

        // then
        assertThat(result, is(false));

        // given
        validatorUnderTest.initialize(createAgeAnnotation(18, 65));
        LocalDate maxAge = LocalDate.now().minusYears(65);

        // when
        result = validatorUnderTest.isValid(maxAge, constraintValidatorContext);

        // then
        assertThat(result, is(false));
    }

    @Test
    void shouldThrowExceptionWhenMinGreaterThanMax() {
        // given
        validatorUnderTest.initialize(createAgeAnnotation(18, 17));

        // then
        RuntimeException exception = assertThrows(RuntimeException.class,
                                                  () -> validatorUnderTest.isValid(LocalDate.now(),
                                                                                   constraintValidatorContext));
        assertThat(exception.getMessage(),
                   is("Invalid @Age annotation configuration: min and max must be non-negative, and min must be less than or equal to max."));
    }

    @Test
    void shouldThrowExceptionWhenMinOrMaxNegative() {
        // given
        validatorUnderTest.initialize(createAgeAnnotation(-1, 17));

        // then
        RuntimeException exception = assertThrows(RuntimeException.class,
                                                  () -> validatorUnderTest.isValid(LocalDate.now(),
                                                                                   constraintValidatorContext));
        assertThat(exception.getMessage(),
                   is("Invalid @Age annotation configuration: min and max must be non-negative, and min must be less than or equal to max."));

        // given
        validatorUnderTest.initialize(createAgeAnnotation(-3, -1));

        // then
        exception = assertThrows(RuntimeException.class,
                                 () -> validatorUnderTest.isValid(LocalDate.now(),
                                                                  constraintValidatorContext));
        assertThat(exception.getMessage(),
                   is("Invalid @Age annotation configuration: min and max must be non-negative, and min must be less than or equal to max."));
    }

    private Age createAgeAnnotation(int min, int max) {
        return new Age() {
            @Override
            public Class<?>[] groups() {
                return new Class<?>[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return new Class[0];
            }

            public int min() {
                return min;
            }

            public int max() {
                return max;
            }

            @Override
            public String message() {
                return "It must be between {min} (inclusive) and {max} (exclusive)";
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Age.class;
            }
        };
    }
}
