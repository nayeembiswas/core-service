package com.nayeem.biswas.core.base.dtos.fileservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileDto {
    private String bucketName;
    private String fileName;
    private String fileType;
    private String previewUrl;
    private Integer serial;

    public void setBucketName(String bucketName) {
        this.bucketName = (null != bucketName && !bucketName.isEmpty()) ? bucketName.trim() : null;
    }

    public void setFileName(String fileName) {
        this.fileName = (null != fileName && !fileName.isEmpty()) ? fileName.trim() : null;
    }

    public void setFileType(String fileType) {
        this.fileType = (null != fileType && !fileType.isEmpty()) ? fileType.trim() : null;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = (null != previewUrl && !previewUrl.isEmpty()) ? previewUrl.trim() : null;
    }
}
