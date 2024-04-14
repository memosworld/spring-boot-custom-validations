package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import memos.tutorials.customvalidation.controller.validation.ConditionalMandatory;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.springframework.util.StringUtils.hasText;

public class ConditionalMandatoryConstraintValidator implements ConstraintValidator<ConditionalMandatory, Object> {
    private String field;

    private String[] values;

    private String[] requiredFields;

    @Override
    public void initialize(ConditionalMandatory constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.values = constraintAnnotation.values();
        this.requiredFields = constraintAnnotation.requires();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Object conditionalFieldValue = getFieldValue(value, field);
            if (conditionalFieldValue != null && Arrays.asList(values).contains(conditionalFieldValue.toString())) {
                return validateRequiredFields(value, requiredFields);
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Invalid field name, type or access while performing ConditionalMandatory validation", e);
        }
    }

    private Object getFieldValue(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = obj.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    private boolean validateRequiredFields(Object obj, String[] fields) throws NoSuchFieldException, IllegalAccessException {
        for (String field : fields) {
            Object fieldValue = getFieldValue(obj, field);
            if (fieldValue == null || !hasText(fieldValue.toString())) {
                return false;
            }
        }
        return true;
    }
}
