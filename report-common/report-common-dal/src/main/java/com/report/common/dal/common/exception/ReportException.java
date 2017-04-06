package com.report.common.dal.common.exception;

import com.report.common.dal.common.enums.ReportExceptionCodes;

/**
 * 业务异常类
 * @author liyang
 *
 */
public class ReportException extends Exception {
	
	public static final long serialVersionUID = 0x01;

	/**
	 * 应用异常码
	 */
	private String code;
	
	private String message;

	public ReportException() {
	}

	public ReportException(String code) {
		super(code);
		this.code = code;
	}
	
	public ReportException(ReportExceptionCodes code) {
		super(code.getCode() + ":" + code.getMessage());
		this.code = code.getCode();
		this.message = code.getMessage();
	}
	
	public ReportException(String code, String message) {
		super(code + ": " + message);
		this.code = code;
		this.message = message;
	}

	public ReportException(String code, String message, Throwable cause) {
		super(code + ": " + message, cause);
		this.code = code;
		this.message = message;
	}

	public ReportException(Throwable cause) {
		super(cause);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String string) {
		code = string;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}