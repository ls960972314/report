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

	public ReportException() {
	}

	public ReportException(String code) {
		super(code);
		this.code = code;
	}
	
	public ReportException(ReportExceptionCodes code) {
		super(code.getCode() + ":" + code.getMessage());
		this.code = code.getCode();
	}
	
	public ReportException(String code, String msg) {
		super(code + ": " + msg);
		this.code = code;
	}

	public ReportException(String code, String msg, Throwable cause) {
		super(code + ": " + msg, cause);
		this.code = code;
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

}