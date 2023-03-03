package com.nayeem.biswas.core.base.dtos;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */

@Component
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoggedInUserDto {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String profileImg;

    private UUID roleId;
    private String roleName;
    private String roleCode;
    private String designation;


    private String token;

}
