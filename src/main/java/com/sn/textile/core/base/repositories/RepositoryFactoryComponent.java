package com.sn.textile.core.base.repositories;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.sn.textile.core.base.entities.BaseEntity;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */

@Component
@RequiredArgsConstructor
@SuppressWarnings("rawtypes")
public class RepositoryFactoryComponent {

	private final List<BaseRepository> serviceRepositories;

    @SuppressWarnings("unchecked")
	public <E extends BaseEntity> BaseRepository<E> getRepository(Class<E> entityClass) {
        //noinspection unchecked
        return (BaseRepository<E>) serviceRepositories.stream()
                .filter(sr -> isMatchingType(sr, entityClass))
                .findFirst()
                .orElse(null);
    }

    private boolean isMatchingType(BaseRepository repository, Class clazz) {
        return ((ParameterizedType) ((Class) repository.getClass()
                .getGenericInterfaces()[0])
                .getGenericInterfaces()[0])
                .getActualTypeArguments()[0]
                .getTypeName().equals(clazz.getTypeName());
    }
}