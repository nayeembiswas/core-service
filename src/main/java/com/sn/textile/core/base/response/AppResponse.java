package com.sn.textile.core.base.response;

import org.springframework.http.HttpStatus;

import com.sn.textile.core.base.model.MetaModel;

import java.util.Date;

/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */
@SuppressWarnings("rawtypes")
public class AppResponse<T> {
    
    private Date timestamp;
    private HttpStatus status;
    private String message;
    private T body;
    private MetaModel meta;
    private T header;

    private AppResponse(HttpStatus status) {
        this.status = status;
        this.timestamp=new Date();
    }

    private AppResponse() {
    }

	public static AppResponse build(HttpStatus status) {
        return new AppResponse(status);
    }
    
    public Integer getStatus() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }
    
    

    public AppResponse message(String message) {
        this.message = message;
        return this;
    }

    public T getBody() {
        return body;
    }

    public AppResponse body(T data) {
        this.body = data;
        return this;
    }

    public AppResponse meta(MetaModel meta) {
        this.meta = meta;
        return this;
    }

    public MetaModel getMeta() {
        return meta;
    }

	public AppResponse header(T data) {
        this.header = data;
        return this;
    }

    public T getHeader() {
        return header;
    }

	public void setBody(T body) {
		this.body = body;
	}
    
    
    
    
}
