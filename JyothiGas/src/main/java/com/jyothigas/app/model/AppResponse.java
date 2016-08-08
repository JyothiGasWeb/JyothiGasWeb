package com.jyothigas.app.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AppResponse implements Serializable {

	private static final long serialVersionUID = 2542911871310426635L;

	private String status;
	private String message;
	private Object id;
	private Object result;
	public Object getId() {
		return id;
	}

	public Object getResult() {
		return result;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	private Integer httpErrorCode;

	private String oauth2ErrorCode;


	public Integer getHttpErrorCode() {
		return httpErrorCode;
	}

	public void setHttpErrorCode(Integer httpErrorCode) {
		this.httpErrorCode = httpErrorCode;
	}

	public String getOauth2ErrorCode() {
		return oauth2ErrorCode;
	}

	public void setOauth2ErrorCode(String oauth2ErrorCode) {
		this.oauth2ErrorCode = oauth2ErrorCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
