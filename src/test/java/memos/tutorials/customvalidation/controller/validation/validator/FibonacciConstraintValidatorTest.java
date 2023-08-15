package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.validation.Fibonacci;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.annotation.Annotation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FibonacciConstraintValidatorTest {

    AutoCloseable closeable;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private FibonacciConstraintValidator validatorUnderTest;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        validatorUnderTest = new FibonacciConstraintValidator();
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void shouldPassWhenGivenNumberWithinFibonacci() {
        // given
        validatorUnderTest.initialize(createFibonacciAnnotation());

        // when
        boolean result = validatorUnderTest.isValid(2L, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldPassWhenNull() {
        // given
        validatorUnderTest.initialize(createFibonacciAnnotation());

        // when
        boolean result = validatorUnderTest.isValid(null, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldFailWhenGivenNumberExcluded() {
        // given
        validatorUnderTest.initialize(createFibonacciAnnotation());

        // when
        boolean result = validatorUnderTest.isValid(7L, constraintValidatorContext);

        // then
        assertThat(result, is(false));
    }

    private Fibonacci createFibonacciAnnotation() {
        return new Fibonacci() {
            @Override
            public Class<?>[] groups() {
                return new Class<?>[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return new Class[0];
            }

            @Override
            public String message() {
                return "It must be a number within the Fibonacci series!";
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Fibonacci.class;
            }
        };
    }
}
