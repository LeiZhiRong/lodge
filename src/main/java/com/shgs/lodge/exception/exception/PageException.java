package com.shgs.lodge.exception.exception;


import com.shgs.lodge.exception.constant.Status;

public class PageException extends BaseException {

	public PageException(Status status) {
		super(status);
	}

	public PageException(Integer code, String message) {
		super(code, message);
	}
}
