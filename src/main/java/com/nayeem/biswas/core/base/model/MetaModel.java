package com.nayeem.biswas.core.base.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaModel {
	private Boolean isAndSearch;
    private Integer page;
    private Integer prevPage;
    private Integer nextPage;
    private Integer limit;
    private Long totalRecords;
    private Integer resultCount;
    private Integer totalPageCount;
    private List<SortModel> sort;
}
