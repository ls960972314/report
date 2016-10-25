package com.sypay.omp.per.model.page;

import com.sypay.omp.per.common.Constants.OpStatus;
import com.sypay.omp.per.util.ResponseMsgUtil;

/**
 * $.ajax后需要接受的JSON
 * 
 * @author
 * 
 */
public class AjaxJson {

	// 提示编码
	private int errorNo;
	// 提示信息
	private String errorInfo;
	// 状态
	private int status = OpStatus.SUCC;

	public int getErrorNo() {
		return errorNo;
	}

	public void setErrorNo(int errorNo) {
		this.errorNo = errorNo;
		this.errorInfo = ResponseMsgUtil.returnMsg(errorNo);
		this.status = OpStatus.FAIL;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
		this.status = OpStatus.FAIL;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
