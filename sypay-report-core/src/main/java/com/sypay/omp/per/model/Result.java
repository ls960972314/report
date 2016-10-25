package com.sypay.omp.per.model;

/**
 * 结果类
 * @author nieminjie
 *
 */
public class Result {
	
	/**
	 * 结果编号
	 */
	private String code;
	
	/**
	 * 结果信息
	 */
	private String message;
	
	/**
	 * 成功状态
	 */
	private boolean success = false;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
