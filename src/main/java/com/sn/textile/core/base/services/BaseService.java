package com.sn.textile.core.base.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sn.textile.core.base.dtos.BaseDto;
import com.sn.textile.core.base.dtos.IdHolderRequestBodyDto;
import com.sn.textile.core.base.dtos.LoggedInUserDto;
import com.sn.textile.core.base.entities.BaseEntity;
import com.sn.textile.core.base.exceptions.ServiceExceptionHolder;
import com.sn.textile.core.base.model.MetaModel;
import com.sn.textile.core.base.model.SortModel;
import com.sn.textile.core.base.repositories.BaseRepository;
import com.sn.textile.core.base.request.AppRequest;
import com.sn.textile.core.base.response.AppResponse;
import com.sn.textile.core.base.util.UtilValidate;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@Data
public abstract class BaseService<E extends BaseEntity, D extends IdHolderRequestBodyDto> {

    @Autowired
    LoggedInUserDto loggedInUserDto;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UtilValidate utilValidate;

    private final BaseRepository<E> repository;
    private final ModelMapper modelMapper;

    private UUID getLoggedInUserId() {
        return null != loggedInUserDto && null != loggedInUserDto.getUserId() ? loggedInUserDto.getUserId() : UUID.randomUUID();
    }
    //CREATE: START-----------------------------------------------------------------------------------------------------------------------------------

    protected E putBaseEntityDetailsForCreate(E entity) {
        entity.setCreatedBy(getLoggedInUserId());
        entity.setCreatedOn(new Date());
        entity.setIsDeleted(false);
        return entity;
    }

    public E getEntityFromDtoForCreate(D dto) {
        return putBaseEntityDetailsForCreate(convertForCreate(dto));
    }

    public E createEntity(D dto) {
        if (!isValidWhileCreate(dto)) throw new ServiceExceptionHolder.ResourceNotFoundDuringWriteRequestException("Sorry, can't create this time.");
        E entity = getEntityFromDtoForCreate(dto);
        E createdEntity = repository.save(entity);
        postCreate(dto, createdEntity);
        return createdEntity;
    }

    public D create(D dto) {
        return convertForRead(createEntity(preCreate(dto)));
    }
    
    public D preCreate(D dto) {
    	return dto;
    }

    public List<D> create(List<D> dtos) {

        List<E> entities = new ArrayList<>();
        E e;
        for (D dto : dtos) {
            if (!isValidWhileCreate(dto)) continue;
            e = getEntityFromDtoForCreate(dto);
            entities.add(e);
        }
        List<E> createdEntities = repository.saveAll(entities);
        postCreateAll(dtos, createdEntities);
        return createdEntities.stream().map(o -> convertForRead(o)).collect(Collectors.toList());
    }

    public List<E> createEntities(List<E> entities) {
        return repository.saveAll(entities.stream().map(e -> putBaseEntityDetailsForCreate(e)).collect(Collectors.toList()));
    }

    protected boolean isValidWhileCreate(D dto) {
        return true;
    }

    protected E convertForCreate(D d) {
        E e = null;
        try {
            e = getEntityClass().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
        BeanUtils.copyProperties(d, e);
        return e;
    }

    protected void postCreate(D dto, E entity) {

    }

    protected void postCreateAll(List<D> dtos, List<E> entities) {

    }
    //CREATE: END-----------------------------------------------------------------------------------------------------------------------------------

    //UPDATE: START-----------------------------------------------------------------------------------------------------------------------------------

    protected E putBaseEntityDetailsForUpdate(E entity) {
        entity.setUpdatedBy(getLoggedInUserId());
        entity.setUpdatedOn(new Date());
        entity.setIsDeleted(false);
        return entity;
    }

    public E getEntityFromDtoForUpdate(D dto, E entity) {
        return putBaseEntityDetailsForUpdate(convertForUpdate(dto, entity));
    }
    
    protected D preUpdate(D dto) {
    	return dto;
    }

    public D update(D dto) {
        if (!isValidWhileUpdate(dto)) throw new ServiceExceptionHolder.ResourceNotFoundDuringWriteRequestException("Sorry, nothing found to be updated.");;
        E entity = putBaseEntityDetailsForUpdate(convertForUpdate(preUpdate(dto), getEntityById(dto.getId())));
        E updatedEntity = repository.save(entity);
        postUpdate(dto, updatedEntity);
        return convertForRead(updatedEntity);
    }

    public List<D> update(List<D> dtos) {
        List<E> updatedEntities = new ArrayList<>();
        E e;
        for (D dto : dtos) {
            if (!isValidWhileUpdate(dto)) continue;
            e = putBaseEntityDetailsForUpdate(convertForUpdate(dto, getEntityById(dto.getId())));
            updatedEntities.add(e);
        }
        updatedEntities = repository.saveAll(updatedEntities);
        postUpdateAll(dtos, updatedEntities);
        return updatedEntities.stream().map(o -> convertForRead(o)).collect(Collectors.toList());
    }

    public E updateEntity(E entity) {
        return repository.save(putBaseEntityDetailsForUpdate(entity));
    }

    public List<E> updateEntities(List<E> entities) {
        return repository.saveAll(entities.stream().map(e -> putBaseEntityDetailsForUpdate(e)).collect(Collectors.toList()));
    }

    protected boolean isValidWhileUpdate(D dto) {
        if (dto.getId() == null) return false;
        return true;
    }

    protected E convertForUpdate(D d, E e) {
        copyNonNullProperties(d, e);
        //BeanUtils.copyProperties(d, e);
        return e;
    }

    protected void postUpdateAll(List<D> dtos, List<E> entities) {
    }

    protected void postUpdate(D dto, E entity) {
    }

    //UPDATE: END-----------------------------------------------------------------------------------------------------------------------------------


    //DELETE: START---------------------------------------------------------------------------------------------------------------------------------

    public D delete(E entity) {
        return convertForRead(repository.save(putBaseEntityDetailsForDelete(entity)));
    }

    public D delete(UUID uuid) {
        return delete(getEntityById(uuid));
    }

    public List<D> delete(List<E> entities) {
        entities = entities.stream().map(e -> putBaseEntityDetailsForDelete(e)).collect(Collectors.toList());
        return convertForRead(repository.saveAll(entities));
    }

    public List<D> delete(Set<UUID> uuidSet) {
        return delete(getListOfEntitiesByIdSet(uuidSet));
    }

    public E deleteEntity(E entity) {
        return repository.save(putBaseEntityDetailsForDelete(entity));
    }

    public List<E> deleteEntities(List<E> entities) {
        entities = entities.stream().map(e -> putBaseEntityDetailsForDelete(e)).collect(Collectors.toList());
        return repository.saveAll(entities);
    }

    protected E putBaseEntityDetailsForDelete(E entity) {
        entity.setUpdatedBy(getLoggedInUserId());
        entity.setUpdatedOn(new Date());
        entity.setIsDeleted(true);
        return entity;
    }
    
    public void hardDelete(E entity) {
    	repository.delete(entity);
    }

    public void hardDelete(UUID uuid) {
        repository.deleteById(uuid);
    }

    public void hardDelete(List<E> entities) {
        repository.deleteAll(entities);
    }

    public void hardDelete(Set<UUID> uuidSet) {
        repository.deleteAllById(uuidSet);
    }


    //DELETE: END---------------------------------------------------------------------------------------------------------------------------------


    //GET: START---------------------------------------------------------------------------------------------------------------------------------

    public D get(UUID id) {
        return convertForRead(getEntityById(id));
    }

    public List<D> get(Set<UUID> uuidSet) {
        return convertForRead(getListOfEntitiesByIdSet(uuidSet));
    }

    protected List<E> getListOfEntitiesByIdSet(Set<UUID> uuidSet) {
        return repository.findAllById(uuidSet)
                .stream()
                .filter(e -> !e.getIsDeleted())
                .collect(Collectors.toList());
    }

    public Optional<E> getOptionalEntity(@NonNull UUID id) {
        return repository.findByIdAndIsDeleted(id, false);
    }

    protected E getEntityById(@NonNull UUID id) {
        return getOptionalEntity(id).orElseThrow(() -> new RuntimeException("Nothing found with id=" + id));
    }

    public AppResponse<List<D>> getList(AppRequest<D> requestDTO) {
        boolean hasMetaData = requestDTO.getMeta() != null && requestDTO.getMeta().getLimit() != null && requestDTO.getMeta().getPage() != null;
        D filter = null;
        try {
            filter = (null != requestDTO.getBody()) ? requestDTO.getBody() : getDtoClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (hasMetaData) {
        	if(!utilValidate.noData(requestDTO.getMeta().getIsAndSearch()) && requestDTO.getMeta().getIsAndSearch()) {
        		Page<E> page = repository.findAll(findByAndCriteria(filter), getPageable(requestDTO.getMeta()));
        		return AppResponse.build(HttpStatus.OK).meta(getMeta(requestDTO.getMeta(), page)).body(convertForRead(page.getContent()));        		
        	}else {
        		Page<E> page = repository.findAll(findByOrCriteria(filter), getPageable(requestDTO.getMeta()));
        		return AppResponse.build(HttpStatus.OK).meta(getMeta(requestDTO.getMeta(), page)).body(convertForRead(page.getContent())); 
        	}
        }
        List<E> results = repository.findAll(findByOrCriteria(filter));
        return AppResponse.build(HttpStatus.OK).body(convertForRead(results));
    }

    public MetaModel getMeta(MetaModel meta, Page page) {
        if (page.hasContent()) {
            meta.setTotalRecords(page.getTotalElements());
            meta.setTotalPageCount(page.getTotalPages());
            meta.setResultCount(page.getNumberOfElements());
        }
        Integer currentPage = meta.getPage();
        if (null != currentPage) {
            Integer prevPage = currentPage - 1;
            Integer nextPage = currentPage + 1;
            if (prevPage < 0) prevPage = 0;
            if (nextPage == page.getTotalPages()) nextPage -= 1;
            if (page.getTotalElements() == 0) nextPage = 0;
            meta.setPrevPage(prevPage);
            meta.setNextPage(nextPage);
        }
        return meta;
    }

    public Pageable getPageable(MetaModel meta) {
        if (meta == null || meta.getPage() == null || meta.getLimit() == null) return null;
        // has sorted properties inside meta
        if (null != meta.getSort() && meta.getSort().size() > 0)
            return PageRequest.of(meta.getPage(), meta.getLimit(), Sort.by(getSortOrders(meta.getSort())));
        // has no sorted properties inside meta
        return PageRequest.of(meta.getPage(), meta.getLimit());
    }

    public List<Sort.Order> getSortOrders(List<SortModel> sortModels) {
        List<Sort.Order> orders = new ArrayList<>();
        if (null != sortModels && sortModels.size() > 0)
            sortModels.stream().forEach(model -> {
                if (null != model.getField() && null != model.getOrder()
                        && !model.getField().isEmpty() && !model.getOrder().isEmpty()) {
                    orders.add(new Sort.Order(getDirection(model.getOrder()), model.getField()));
                }
            });
        orders.add(new Sort.Order(Sort.Direction.DESC, "createdOn"));
        return orders;
    }

    private Sort.Direction getDirection(String order) {
        return null != order && order.equalsIgnoreCase(Sort.Direction.DESC.toString()) ? Sort.Direction.DESC : Sort.Direction.ASC;
    }

    protected Specification<E> findByAndCriteria(D dto) {
        return new Specification<E>() {

            @Override
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) throws IllegalAccessError {
                List<Predicate> predicates = new ArrayList<>();
                // get all available fields from the dto class and it's parents
                Field[] fields = FieldUtils.getAllFields(dto.getClass());
                if (fields == null || fields.length == 0)
                    throw new ServiceExceptionHolder.BadRequestException("DTO does not contain any field");

                // iterate fields and prepare search criteria
                for (Field field : fields) {
                    try {
                        String fieldName = field.getName();
                        String fieldValue = extractFieldValue(field, dto);
                        if (null == fieldValue) continue;

                        // add search criteria depending on field type
                        if (field.getType() == UUID.class)
                            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(fieldName), UUID.fromString(fieldValue))));
                        else if (field.getType() == Integer.class)
                            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(fieldName), Integer.valueOf(fieldValue))));
                        else if (field.getType() == Boolean.class)
                        	predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(fieldName), Boolean.valueOf(fieldValue))));
                        else if (field.getType() == Float.class)
                            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(fieldName), Float.valueOf(fieldValue))));
                        else if (field.getType() == Double.class)
                            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(fieldName), Double.valueOf(fieldValue))));
                        else if (field.getType() == String.class)
                            predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldName)), "%" + fieldValue.toLowerCase() + "%")));
                        else if (field.getType() == java.sql.Date.class)
                            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(fieldName), java.sql.Date.valueOf(fieldValue))));
                        else if (field.getType() == Date.class)
                            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(fieldName), FieldUtils.readField(dto, fieldName, true))));
                        else if (field.getType() == java.sql.Timestamp.class)
                            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(fieldName), java.sql.Timestamp.valueOf(fieldValue))));
                        else if (field.getType().getGenericSuperclass() == BaseDto.class) {
                            Join<E, ?> sts = root.join(fieldName);
                            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(sts.get("id"), UUID.fromString(fieldValue))));
                        } else
                            throw new ServiceExceptionHolder.BadRequestException("Invalid type for dto search field " + field.getType());
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        throw new ServiceExceptionHolder.BadRequestException(e.getMessage());
                    }
                }
                // by default deleted data will not appears
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("isDeleted"), false)));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
    
    
    protected Specification<E> findByOrCriteria(D dto) {
        return new Specification<E>() {

            @Override
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) throws IllegalAccessError {
                List<Predicate> predicates = new ArrayList<>();
                // get all available fields from the dto class and it's parents
                Field[] fields = FieldUtils.getAllFields(dto.getClass());
                if (fields == null || fields.length == 0)
                    throw new ServiceExceptionHolder.BadRequestException("DTO does not contain any field");

                // iterate fields and prepare search criteria
                for (Field field : fields) {
                    try {
                        String fieldName = field.getName();
                        String fieldValue = extractFieldValue(field, dto);
                        if (null == fieldValue) continue;

                        // add search criteria depending on field type
                        if (field.getType() == UUID.class)
                            predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get(fieldName), UUID.fromString(fieldValue))));
                        else if (field.getType() == Integer.class)
                            predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get(fieldName), Integer.valueOf(fieldValue))));
                        else if (field.getType() == Boolean.class)
                        	predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get(fieldName), Boolean.valueOf(fieldValue))));
                        else if (field.getType() == Float.class)
                            predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get(fieldName), Float.valueOf(fieldValue))));
                        else if (field.getType() == Double.class)
                            predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get(fieldName), Double.valueOf(fieldValue))));
                        else if (field.getType() == String.class)
                            predicates.add(criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldName)), "%" + fieldValue.toLowerCase() + "%")));
                        else if (field.getType() == java.sql.Date.class)
                            predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get(fieldName), java.sql.Date.valueOf(fieldValue))));
                        else if (field.getType() == Date.class)
                            predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get(fieldName), FieldUtils.readField(dto, fieldName, true))));
                        else if (field.getType() == java.sql.Timestamp.class)
                            predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get(fieldName), java.sql.Timestamp.valueOf(fieldValue))));
                        else if (field.getType().getGenericSuperclass() == BaseDto.class) {
                            Join<E, ?> sts = root.join(fieldName);
                            predicates.add(criteriaBuilder.or(criteriaBuilder.equal(sts.get("id"), UUID.fromString(fieldValue))));
                        } else
                            throw new ServiceExceptionHolder.BadRequestException("Invalid type for dto search field " + field.getType());
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        throw new ServiceExceptionHolder.BadRequestException(e.getMessage());
                    }
                }
                // by default deleted data will not appears
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("isDeleted"), false)));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    public String extractFieldValue(Field field, D dto) {
        String fieldName = field.getName();
        String fieldValue = null;
        Gson gson;
        try {
            if (field.getType().getGenericSuperclass() == BaseDto.class) {
                gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
                if (objectMapper.readTree(gson.toJson(dto)).get(fieldName) == null) return null;
                JsonNode node = objectMapper.readTree(gson.toJson(dto)).get(fieldName).get("id");
                if (node != null) fieldValue = node.toString().replace("\"", "");
            } else fieldValue = FieldUtils.readField(dto, fieldName, true) + "";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // skip field if field is transient or has no field value
        if ((utilValidate.noData(fieldValue)) || utilValidate.isTransient(dto.getClass(), fieldName)) return null;

        return fieldValue;
    }

    protected Predicate like(CriteriaBuilder criteriaBuilder, Expression<String> expression, String value) {
        return criteriaBuilder.like(criteriaBuilder.upper(expression), "%" + value.toUpperCase() + "%");
    }

    protected D convertForRead(E e) {
        return modelMapper.map(e, getDtoClass());
    }

    protected List<D> convertForRead(List<E> e) {
        return e.stream().map(this::convertForRead).collect(Collectors.toList());
    }

    //GET: END---------------------------------------------------------------------------------------------------------------------------------


    @SuppressWarnings("unchecked")
    private Class<E> getEntityClass() {
        return (Class<E>) (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @SuppressWarnings("unchecked")
    private Class<D> getDtoClass() {
        return (Class<D>) (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
    }

    /**
     * Copies properties from one object to another
     * @param source
     * @destination
     * @return
     */
    public void copyNonNullProperties(Object source, Object destination) {
        BeanUtils.copyProperties(source, destination, getNullPropertyNames(source));
    }

    /**
     * Returns an array of null properties of an object
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
