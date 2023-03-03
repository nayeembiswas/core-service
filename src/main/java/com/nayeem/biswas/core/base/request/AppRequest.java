package com.nayeem.biswas.core.base.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nayeem.biswas.core.base.model.MetaModel;

import lombok.Data;

import javax.validation.Valid;

/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppRequest<T> {

    private MetaModel meta;

    @Valid
    private T body;

}
