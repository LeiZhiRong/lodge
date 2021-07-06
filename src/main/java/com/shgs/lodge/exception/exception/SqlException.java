package com.shgs.lodge.exception.exception;
import com.shgs.lodge.exception.constant.Status;

public class SqlException extends BaseException {

	public SqlException(Status status) {
		super(status);
	}

	public SqlException(Integer code, String message) {
		super(code, message);
	}



}
