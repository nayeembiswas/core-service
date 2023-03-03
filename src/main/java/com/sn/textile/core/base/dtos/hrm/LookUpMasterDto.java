package com.sn.textile.core.base.dtos.hrm;

import java.util.UUID;

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
public class LookUpMasterDto extends BaseDto{
	private UUID id;

	private String name;

	private String code;
	
	private UUID parentId;
	
	private LookUpMasterDto parent;

	private String key;

	private Boolean isActive;
}
