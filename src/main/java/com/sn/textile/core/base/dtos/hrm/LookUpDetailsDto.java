package com.sn.textile.core.base.dtos.hrm;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.sn.textile.core.base.dtos.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Abu Talib
 * @Project Textile ERP
 * @Since Feb 09, 2023
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class LookUpDetailsDto extends BaseDto{
	private UUID id;

	private String name;

	private String code;
	
	private LookUpMasterDto master;
	
	@NotNull(message = "Master can't be null")
	private UUID masterId;
	
	private LookUpDetailsDto parent;
	
	private UUID parentId;

	private String key;

	private Boolean isActive;
}
