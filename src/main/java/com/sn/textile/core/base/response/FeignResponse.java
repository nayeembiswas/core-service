package com.sn.textile.core.base.response;

import java.util.Date;

import com.sn.textile.core.base.model.MetaModel;

/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */

public class FeignResponse<T> {

    private Date timestamp;
    private Integer status;
    private String message;
    private T body;
    private MetaModel meta;

    private FeignResponse(Integer status) {
        this.status = status;
        this.timestamp = new Date();
    }

    private FeignResponse() {
    }

    public static FeignResponse build(Integer status) {
        return new FeignResponse(status);
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }


    public FeignResponse message(String message) {
        this.message = message;
        return this;
    }

    public T getBody() {
        return body;
    }

    public FeignResponse body(T data) {
        this.body = data;
        return this;
    }

    public FeignResponse meta(MetaModel meta) {
        this.meta = meta;
        return this;
    }

    public MetaModel getMeta() {
        return meta;
    }
}
