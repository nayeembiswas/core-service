package com.nayeem.biswas.core.base.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReadUnreadDto {

    @NonNull
    private UUID id;

    @NonNull
    private Boolean isMarkedAsRead;

}