package com.nayeem.biswas.core.base.validators.annotations;


import javax.validation.Constraint;
import javax.validation.Payload;

import com.nayeem.biswas.core.base.entities.BaseEntity;
import com.nayeem.biswas.core.base.validators.implementations.EntityOidValidityCheckerExtended;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EntityOidValidityCheckerExtended.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEntityOid {

    Class<? extends BaseEntity> value();
    String message() default "invalid oid provided";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}