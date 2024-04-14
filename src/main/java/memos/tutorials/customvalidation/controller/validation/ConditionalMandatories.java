package memos.tutorials.customvalidation.controller.validation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionalMandatories {

    ConditionalMandatory[] value();
}
