package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import lombok.Data;
import memos.tutorials.customvalidation.controller.validation.ConditionalMandatory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.annotation.Annotation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConditionalMandatoryConstraintValidatorTest {

    AutoCloseable closeable;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private ConditionalMandatoryConstraintValidator validatorUnderTest;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        validatorUnderTest = new ConditionalMandatoryConstraintValidator();
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void shouldPassWhenConditionalFieldPresent() {
        // given
        validatorUnderTest.initialize(
                createConditionalMandatory("field1", new String[]{"a"}, new String[]{"field2"}));

        TestDTO dto = new TestDTO();
        dto.setField1("a");
        dto.setField2("present");

        // when
        boolean result = validatorUnderTest.isValid(dto, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldPassWhenConditionalFieldsPresent() {
        // given
        validatorUnderTest.initialize(
                createConditionalMandatory("field2", new String[]{"a"}, new String[]{"field3", "field4"}));

        TestDTO dto = new TestDTO();
        dto.setField1("a");
        dto.setField3(TestDTO.Type.TYPE1);
        dto.setField4(0);

        // when
        boolean result = validatorUnderTest.isValid(dto, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldFailWhenConditionalFieldsNotPresent() {
        // given
        validatorUnderTest.initialize(
                createConditionalMandatory("field2", new String[]{"a"}, new String[]{"field3", "field4"}));

        TestDTO dto = new TestDTO();
        dto.setField1("a");
        dto.setField3(TestDTO.Type.TYPE1);

        // when
        boolean result = validatorUnderTest.isValid(dto, constraintValidatorContext);

        // then
        assertThat(result, is(true));
    }

    @Test
    void shouldThrowExceptionWhenWrongVariableNameProvided() {
        // given
        validatorUnderTest.initialize(createConditionalMandatory("wrong", new String[]{"a"}, new String[]{"field3"}));
        TestDTO request = new TestDTO();

        // then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> validatorUnderTest.isValid(request,
                                                                                                           constraintValidatorContext));
        assertThat(exception.getMessage(),
                   is("Invalid field name, type or access while performing ConditionalMandatory validation"));
    }

    private ConditionalMandatory createConditionalMandatory(String field, String[] values, String[] requiredFields) {
        return new ConditionalMandatory() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return ConditionalMandatory.class;
            }

            @Override
            public String field() {
                return field;
            }

            @Override
            public String[] values() {
                return values;
            }

            @Override
            public String[] requires() {
                return requiredFields;
            }

            @Override
            public String message() {
                return null;
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return new Class[0];
            }
        };
    }

    @Data
    private static class TestDTO {
        public enum Type {
            TYPE1, TYPE2
        }

        private String field1;
        private String field2;
        private Type field3;
        private Integer field4;
    }
}
