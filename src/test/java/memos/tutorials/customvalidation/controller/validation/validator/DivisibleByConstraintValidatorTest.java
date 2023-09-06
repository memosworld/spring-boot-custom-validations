package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.validation.DivisibleBy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.annotation.Annotation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DivisibleByConstraintValidatorTest {

    AutoCloseable closeable;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private DivisibleByConstraintValidator validatorUnderTest;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        validatorUnderTest = new DivisibleByConstraintValidator();
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void shouldPassWhenGivenNumberIsDivisibleBy() {
        // given
        validatorUnderTest.initialize(createDivisibleByAnnotation(100));

        // when
        boolean result = validatorUnderTest.isValid(200, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldPassWhenNull() {
        // given
        validatorUnderTest.initialize(createDivisibleByAnnotation(100));

        // when
        boolean result = validatorUnderTest.isValid(null, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldFailWhenGivenNumberIsNotDivisibleBy() {
        // given
        validatorUnderTest.initialize(createDivisibleByAnnotation(10));

        // when
        boolean result = validatorUnderTest.isValid(7, constraintValidatorContext);

        // then
        assertThat(result, is(false));
    }

    private DivisibleBy createDivisibleByAnnotation(int divider) {
        return new DivisibleBy() {
            @Override
            public Class<?>[] groups() {
                return new Class<?>[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return new Class[0];
            }

            @Override
            public int divider() {
                return divider;
            }

            @Override
            public String message() {
                return "It must be divisible by {divider}!";
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return DivisibleBy.class;
            }
        };
    }
}
