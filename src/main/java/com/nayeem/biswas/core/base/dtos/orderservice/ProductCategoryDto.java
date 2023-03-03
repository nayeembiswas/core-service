package com.nayeem.biswas.core.base.dtos.orderservice;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.nayeem.biswas.core.base.dtos.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Sir Sammy
 * @Project Order-Service, Textile ERP
 * @Since Feb 09/27, 2023
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCategoryDto extends BaseDto{
	private UUID id;

	private String name;

	@NotNull(message = "Parent ID can't be NULL")
	private UUID parentId;
	
	private ProductCategoryDto parent;

	private Boolean isActive;
}
