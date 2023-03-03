package com.sn.textile.core.base.validators.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.sn.textile.core.base.validators.annotations.NotSelfReferencingParent;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@Component
@RequestScope
@RequiredArgsConstructor
public class NotSelfReferencingParentValidator implements ConstraintValidator<NotSelfReferencingParent, Object> {

    private static final SpelExpressionParser PARSER = new SpelExpressionParser();
    private String[] fields;

    @Override
    public void initialize(NotSelfReferencingParent constraintAnnotation) {
        fields = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object oid = PARSER.parseExpression(fields[0]).getValue(value);
        Object parentOid = PARSER.parseExpression(fields[1]).getValue(value);
        if (oid != null && parentOid != null) {
            if (oid.equals(parentOid)) return false;
            else return true;
        } else return true;
    }
}