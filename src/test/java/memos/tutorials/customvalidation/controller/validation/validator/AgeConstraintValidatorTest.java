package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.validation.Age;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("validDateProvider")
    void shouldPass(LocalDate date) {
        // given
        validatorUnderTest.initialize(createAgeAnnotation(18, 65));

        // when
        boolean result = validatorUnderTest.isValid(date, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @ParameterizedTest
    @MethodSource("invalidDateProvider")
    void shouldFail(LocalDate date) {
        // given
        validatorUnderTest.initialize(createAgeAnnotation(18, 65));

        // when
        boolean result = validatorUnderTest.isValid(date, constraintValidatorContext);

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

    private static Stream<LocalDate> validDateProvider() {
        List<LocalDate> list = new ArrayList<>();

        // between min and max
        list.add(LocalDate.now().minusYears(40));

        // min age
        list.add(LocalDate.now().minusYears(18));

        // null
        list.add(null);

        return list.stream();
    }

    private static Stream<LocalDate> invalidDateProvider() {
        List<LocalDate> list = new ArrayList<>();

        // max age
        list.add(LocalDate.now().minusYears(65));

        // older
        list.add(LocalDate.now().minusYears(100));

        // younger
        list.add(LocalDate.now());

        return list.stream();
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
