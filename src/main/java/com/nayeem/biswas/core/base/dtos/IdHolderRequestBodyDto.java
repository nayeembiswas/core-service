package com.nayeem.biswas.core.base.dtos;

import java.util.UUID;

public interface IdHolderRequestBodyDto extends EmptyRequestBodyDto {

    UUID getId();

    void setId(UUID id);

}
