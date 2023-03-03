package com.nayeem.biswas.core.base.validators.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.nayeem.biswas.core.base.validators.implementations.NotSelfReferencingParentValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotSelfReferencingParentValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotSelfReferencingParent {

    String[] value();

    String message() default "Self-referencing parent";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}