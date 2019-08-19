package com.zrd.wh.core.base.exception;

public class DBException extends BaseException {
	private static final long serialVersionUID = -1715959367555239120L;

	public DBException() {
		super();
	}

	public DBException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DBException(String msg) {
		super(msg);
	}

	public DBException(Throwable cause) {
		super(cause);
	}

}
