package com.zrd.wh.core.base.exception;

public class SysException extends BaseException {
	private static final long serialVersionUID = -8892815755225021897L;

	public SysException() {
	}

	public SysException(String msg) {
		super(msg);
	}

	public SysException(Throwable cause) {
		super(cause);
	}

	public SysException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
