package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import lombok.Data;
import memos.tutorials.customvalidation.controller.dto.UserRequestDTO;
import memos.tutorials.customvalidation.controller.validation.AtLeastOneNotBlank;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("validDTOProvider")
    void shouldPass(TestDTO dto) {
        validatorUnderTest.initialize(createAtLeastOneNotBlankAnnotation("field1", "field2", "field3"));

        // when
        boolean result = validatorUnderTest.isValid(dto, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @ParameterizedTest
    @MethodSource("invalidDTOProvider")
    void shouldFail(TestDTO dto) {
        validatorUnderTest.initialize(createAtLeastOneNotBlankAnnotation("field1", "field2", "field3"));

        // when
        boolean result = validatorUnderTest.isValid(dto, constraintValidatorContext);

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

    private static Stream<TestDTO> validDTOProvider() {
        List<TestDTO> list = new ArrayList<>();

        // when all variables present
        TestDTO dto = new TestDTO();
        dto.setField1("field1");
        dto.setField2("field2");
        dto.setField3("field3");
        list.add(dto);

        // when only field1 present
        dto = new TestDTO();
        dto.setField1("field1");
        list.add(dto);

        // when only field2 present
        dto = new TestDTO();
        dto.setField2("field2");
        list.add(dto);

        // when only field3 present
        dto = new TestDTO();
        dto.setField3("field3");
        list.add(dto);

        return list.stream();
    }

    private static Stream<TestDTO> invalidDTOProvider() {
        List<TestDTO> list = new ArrayList<>();

        // when all fields empty
        TestDTO dto = new TestDTO();
        dto.setField1("");
        dto.setField2("");
        dto.setField3("");
        list.add(dto);

        // when all fields null
        dto = new TestDTO();
        dto.setField1(null);
        dto.setField2(null);
        dto.setField3(null);
        list.add(dto);

        return list.stream();
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

    @Data
    private static class TestDTO {
        private String field1;
        private String field2;
        private String field3;
    }
}
