package com.sypay.omp.per.model;

/**
 * 修改角色
 * 
 * @author dumengchao
 *
 * @date 2014年10月22日
 */
public class UpdateRoleModel {
	private String memberId;
	private String sysCode;
	private String sourceRoleCode;
	private String targetRoleCode;

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

	public String getSourceRoleCode() {
		return sourceRoleCode;
	}

	public void setSourceRoleCode(String sourceRoleCode) {
		this.sourceRoleCode = sourceRoleCode;
	}

	public String getTargetRoleCode() {
		return targetRoleCode;
	}

	public void setTargetRoleCode(String targetRoleCode) {
		this.targetRoleCode = targetRoleCode;
	}

	@Override
	public String toString() {
		return "UpdateRoleModel [memberId=" + memberId + ", sourceRoleCode="
				+ sourceRoleCode + ", targetRoleCode=" + targetRoleCode + "]";
	}

}
