package com.sn.textile.core.base.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SortModel {

    private String order;
    private String field;
}
