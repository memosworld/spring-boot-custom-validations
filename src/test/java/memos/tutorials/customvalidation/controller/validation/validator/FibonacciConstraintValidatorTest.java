package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        // when
        boolean result = validatorUnderTest.isValid(2L, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldPassWhenNull() {
        // when
        boolean result = validatorUnderTest.isValid(null, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldFailWhenGivenNumberNotInFibonacci() {
        // when
        boolean result = validatorUnderTest.isValid(7L, constraintValidatorContext);

        // then
        assertThat(result, is(false));
    }
}
