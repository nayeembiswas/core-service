package com.sn.textile.core.base.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.UUID;
/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */

@Component
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageSenderDto {
    private UUID userId;
    private String firstName;
    private String lastName;
    private UUID roleId;
    private String roleName;
}