package com.sypay.omp.report.exception;
/**
 * 查看报表异常
 * @author lishun
 *
 */
public class ReportShowException extends Exception {

	private static final long serialVersionUID = -7881378583311717581L;

	public ReportShowException() {
		super();
	}

	public ReportShowException(String message) {
		super(message);
	}

	public ReportShowException(Throwable cause) {
		super(cause);
	}

	public ReportShowException(String message, Throwable cause) {
		super(message, cause);
	}
}