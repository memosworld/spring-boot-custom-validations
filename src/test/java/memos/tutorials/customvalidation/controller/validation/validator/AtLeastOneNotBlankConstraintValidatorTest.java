package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import memos.tutorials.customvalidation.controller.dto.UserRequestDTO;
import memos.tutorials.customvalidation.controller.validation.AtLeastOneNotBlank;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.annotation.Annotation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AtLeastOneNotBlankConstraintValidatorTest {

    AutoCloseable closeable;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private AtLeastOneNotBlankConstraintValidator validatorUnderTest;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        validatorUnderTest = new AtLeastOneNotBlankConstraintValidator();
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void shouldPassWhenAllVariablesPresent() {
        // given
        validatorUnderTest.initialize(createAtLeastOneNotBlankAnnotation("id", "passport", "drivingLicence"));

        UserRequestDTO request = new UserRequestDTO();
        request.setId("ID");
        request.setPassport("Passport");
        request.setDrivingLicence("Driving Licence");

        // when
        boolean result = validatorUnderTest.isValid(request, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldPassWhenOnlyIDPresent() {
        // given
        validatorUnderTest.initialize(createAtLeastOneNotBlankAnnotation("id", "passport", "drivingLicence"));

        UserRequestDTO request = new UserRequestDTO();
        request.setId("ID");

        // when
        boolean result = validatorUnderTest.isValid(request, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldPassWhenOnlyPassportPresent() {
        // given
        validatorUnderTest.initialize(createAtLeastOneNotBlankAnnotation("id", "passport", "drivingLicence"));

        UserRequestDTO request = new UserRequestDTO();
        request.setPassport("Passport");

        // when
        boolean result = validatorUnderTest.isValid(request, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldPassWhenOnlyDrivingLicencePresent() {
        // given
        validatorUnderTest.initialize(createAtLeastOneNotBlankAnnotation("id", "passport", "drivingLicence"));

        UserRequestDTO request = new UserRequestDTO();
        request.setDrivingLicence("Driving Licence");

        // when
        boolean result = validatorUnderTest.isValid(request, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldFailWhenAllFieldsNull() {
        // given
        validatorUnderTest.initialize(createAtLeastOneNotBlankAnnotation("id", "passport", "drivingLicence"));

        UserRequestDTO request = new UserRequestDTO();

        // when
        boolean result = validatorUnderTest.isValid(request, constraintValidatorContext);

        // then
        assertThat(result, is(false));
    }

    @Test
    void shouldFailWhenAllFieldsEmpty() {
        // given
        validatorUnderTest.initialize(createAtLeastOneNotBlankAnnotation("id", "passport", "drivingLicence"));

        UserRequestDTO request = new UserRequestDTO();
        request.setId("");
        request.setPassport("");
        request.setDrivingLicence("");

        // when
        boolean result = validatorUnderTest.isValid(request, constraintValidatorContext);

        // then
        assertThat(result, is(false));
    }

    @Test
    void shouldThrowExceptionWhenWrongVariableNameProvided() {
        // given
        validatorUnderTest.initialize(createAtLeastOneNotBlankAnnotation("wrong"));
        UserRequestDTO request = new UserRequestDTO();

        // then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> validatorUnderTest.isValid(request,
                                                                                                           constraintValidatorContext));
        assertThat(exception.getMessage(), is("Invalid variable name, type or access: wrong"));
    }

    private AtLeastOneNotBlank createAtLeastOneNotBlankAnnotation(String... fields) {
        return new AtLeastOneNotBlank() {
            @Override
            public Class<?>[] groups() {
                return new Class<?>[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return new Class[0];
            }

            public String[] fields() {
                return fields;
            }

            @Override
            public String message() {
                return "At least one of {fields} must be present!";
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return AtLeastOneNotBlank.class;
            }
        };
    }
}
