package com.shgs.lodge.exception.exception;

import com.shgs.lodge.exception.constant.Status;
import groovy.transform.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {
	private Integer code;
	private String message;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BaseException() {
		super();
	}

	public BaseException(Status status) {
		super(status.getMessage());
		this.code = status.getCode();
		this.message = status.getMessage();
	}

	public BaseException(Integer code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}
}
