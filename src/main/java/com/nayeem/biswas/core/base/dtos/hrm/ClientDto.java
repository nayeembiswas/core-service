package com.nayeem.biswas.core.base.dtos.hrm;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.nayeem.biswas.core.base.dtos.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Sir Sammy
 * @author Abu Talib
 * @Project HRM-Service, Textile-ERP
 * @Since Feb 07, 2023
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientDto extends BaseDto{
	private UUID id;
	
	@NotNull(message = "Category ID can't be null")
	private UUID categoryId;
	
	private LookUpDetailsDto category;
	
	private String name;
	
	private String address;
	
	private String website;
	
	@NotNull(message = "Currency ID can't be null")
	private UUID currencyId;

	private LookUpDetailsDto currency;
	
	private String currencyCode;
	
	private String clientId;
}
