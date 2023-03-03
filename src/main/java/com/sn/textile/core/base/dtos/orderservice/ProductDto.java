package com.sn.textile.core.base.dtos.orderservice;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.sn.textile.core.base.dtos.BaseDto;
import com.sn.textile.core.base.dtos.hrm.LookUpDetailsDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Sir Sammy
 * @Project Order-Service, Textile ERP
 * @Since Feb 08/27, 2023
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductDto extends BaseDto{
	private UUID id;
	
	@NotNull(message = "Category ID can't be null")
	private UUID categoryId;
	
	private ProductCategoryDto category;

	@NotNull(message = "Name cann't be null")
	private String name;
	
	private String description;
	
	@NotNull(message = "Unit ID can't be null")
	private UUID unitId;
	
	private LookUpDetailsDto unit;

	private Double stock;
	
	private Double numOfSale;
	
	private Double numOfUsed;
	
	private Integer alertStock;
}
