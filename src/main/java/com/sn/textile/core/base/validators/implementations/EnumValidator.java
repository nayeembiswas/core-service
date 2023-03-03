package com.sn.textile.core.base.validators.implementations;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.sn.textile.core.base.validators.annotations.ValidEnum;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumValidator implements ConstraintValidator<ValidEnum, CharSequence> {

    private List<String> acceptedValues;

    @Override
    public void initialize(ValidEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return acceptedValues.contains(value.toString());
    }

}