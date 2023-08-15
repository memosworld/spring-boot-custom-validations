package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.validation.IntegerValues;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.annotation.Annotation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class IntegerValuesConstraintValidatorTest {

    AutoCloseable closeable;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private IntegerValuesConstraintValidator validatorUnderTest;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        validatorUnderTest = new IntegerValuesConstraintValidator();
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void shouldPassWhenGivenNumberNotExcluded() {
        // given
        validatorUnderTest.initialize(createIntegerValuesAnnotation(1, 2, 3));

        // when
        boolean result = validatorUnderTest.isValid(2, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldPassWhenNull() {
        // given
        validatorUnderTest.initialize(createIntegerValuesAnnotation(1, 2, 3));

        // when
        boolean result = validatorUnderTest.isValid(null, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldFailWhenGivenNumberExcluded() {
        // given
        validatorUnderTest.initialize(createIntegerValuesAnnotation(1, 2, 3));

        // when
        boolean result = validatorUnderTest.isValid(4, constraintValidatorContext);

        // then
        assertThat(result, is(false));
    }

    private IntegerValues createIntegerValuesAnnotation(int... values) {
        return new IntegerValues() {
            @Override
            public Class<?>[] groups() {
                return new Class<?>[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return new Class[0];
            }

            public int[] values() {
                return values;
            }

            @Override
            public String message() {
                return "It must be one of {values}!";
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return IntegerValues.class;
            }
        };
    }
}
