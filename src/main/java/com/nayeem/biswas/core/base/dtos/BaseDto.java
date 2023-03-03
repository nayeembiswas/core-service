package com.nayeem.biswas.core.base.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseDto implements IdHolderRequestBodyDto {

    @JsonIgnore
    private UUID createdBy;

    @JsonIgnore
    private Date createdOn;

    @JsonIgnore
    private UUID updatedBy;

    @JsonIgnore
    private Date updatedOn;

    @JsonIgnore
    private Boolean isDeleted;
}