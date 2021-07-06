package com.shgs.lodge.exception.exception;
import com.shgs.lodge.exception.constant.Status;

public class JsonException extends BaseException {

	public JsonException(Status status) {
		super(status);
	}

	public JsonException(Integer code, String message) {
		super(code, message);
	}



}
