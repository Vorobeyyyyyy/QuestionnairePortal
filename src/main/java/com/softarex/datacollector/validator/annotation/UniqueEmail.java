package com.softarex.datacollector.validator.annotation;

import com.softarex.datacollector.validator.EmailUniqueValidator;
import org.springframework.messaging.handler.annotation.Payload;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EmailUniqueValidator.class})
public @interface UniqueEmail {
    String message() default "User with such email already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
