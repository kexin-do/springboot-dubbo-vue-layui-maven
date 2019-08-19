package com.zrd.wh.core.base.exception;

public class BaseException extends Exception {
	private static final long serialVersionUID = 2851818760957141903L;

	public BaseException() {
	}

	public BaseException(String msg) {
		super(msg);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
