package com.sypay.omp.per.model;

public class ResourceFactoryModel {
	// 用户id
	private String memberId;
	// 系统code
	private String sysCode;
	// 1：系统 2:会员
	private int queryPermissionType = 1;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public int getQueryPermissionType() {
		return queryPermissionType;
	}

	public void setQueryPermissionType(int queryPermissionType) {
		this.queryPermissionType = queryPermissionType;
	}

}
