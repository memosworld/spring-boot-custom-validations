package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.validation.ExcludedNumbers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.annotation.Annotation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ExcludedNumbersConstraintValidatorTest {

    AutoCloseable closeable;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private ExcludedNumbersConstraintValidator validatorUnderTest;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        validatorUnderTest = new ExcludedNumbersConstraintValidator();
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void shouldPassWhenGivenNumberNotExcluded() {
        // given
        validatorUnderTest.initialize(createExcludedNumbersAnnotation(1, 2, 3));

        // when
        boolean result = validatorUnderTest.isValid(7, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldPassWhenNull() {
        // given
        validatorUnderTest.initialize(createExcludedNumbersAnnotation(1, 2, 3));

        // when
        boolean result = validatorUnderTest.isValid(null, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldFailWhenGivenNumberExcluded() {
        // given
        validatorUnderTest.initialize(createExcludedNumbersAnnotation(1, 2, 3));

        // when
        boolean result = validatorUnderTest.isValid(2, constraintValidatorContext);

        // then
        assertThat(result, is(false));
    }

    private ExcludedNumbers createExcludedNumbersAnnotation(int... excludedNumbers) {
        return new ExcludedNumbers() {
            @Override
            public Class<?>[] groups() {
                return new Class<?>[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return new Class[0];
            }

            public int[] excludedNumbers() {
                return excludedNumbers;
            }

            @Override
            public String message() {
                return "It cannot be one of {excludedNumbers}!";
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return ExcludedNumbers.class;
            }
        };
    }
}
