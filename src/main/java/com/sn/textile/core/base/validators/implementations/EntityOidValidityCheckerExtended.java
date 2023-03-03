package com.sn.textile.core.base.validators.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.sn.textile.core.base.entities.BaseEntity;
import com.sn.textile.core.base.repositories.RepositoryFactoryComponent;
import com.sn.textile.core.base.validators.annotations.ValidEntityOid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

@Component
@RequestScope
@RequiredArgsConstructor
public class EntityOidValidityCheckerExtended implements ConstraintValidator<ValidEntityOid, UUID> {

    private final RepositoryFactoryComponent repositoryFactoryComponent;

    private Class<? extends BaseEntity> entityClass;

    @Override
    public void initialize(ValidEntityOid annotation) {
        entityClass = annotation.value();
    }

    @Override
    public boolean isValid(UUID id, ConstraintValidatorContext constraintValidatorContext) {
        return id == null || repositoryFactoryComponent.getRepository(entityClass).findByIdAndIsDeleted(id, false).isPresent();
    }

}
