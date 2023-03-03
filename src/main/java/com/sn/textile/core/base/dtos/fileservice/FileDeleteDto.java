package com.sn.textile.core.base.dtos.fileservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileDeleteDto {

    @NotBlank(message = "Bucket name must be provided.")
    private String bucketName;

    @NotBlank(message = "File name must be provided.")
    private String fileName;

    public void setBucketName(String bucketName) {
        this.bucketName = (null != bucketName && !bucketName.isEmpty()) ? bucketName.trim() : null;
    }

    public void setFileName(String fileName) {
        this.fileName = (null != fileName && !fileName.isEmpty()) ? fileName.trim() : null;
    }
}
