package memos.tutorials.customvalidation.controller.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import memos.tutorials.customvalidation.controller.validation.AtLeastOneNotBlank;

import java.lang.reflect.Field;

import static org.springframework.util.StringUtils.hasText;

public class AtLeastOneNotBlankConstraintValidator implements ConstraintValidator<AtLeastOneNotBlank, Object> {

    private String[] fields;

    @Override
    public void initialize(AtLeastOneNotBlank constraintAnnotation) {
        fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        Class<?> clazz = object.getClass();

        for (String variable : fields) {
            try {
                Field field = clazz.getDeclaredField(variable);
                field.setAccessible(true);
                Object value = field.get(object);
                if (hasText((String) value)) {
                    return true;
                }
            } catch (Exception e) {
                throw new RuntimeException("Invalid variable name, type or access: " + variable, e);
            }
        }

        return false;
    }
}