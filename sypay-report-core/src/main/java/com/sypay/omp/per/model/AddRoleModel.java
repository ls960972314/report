package com.sypay.omp.per.model;

/**
 * 添加角色
 * 
 * @author dumengchao
 *
 * @date 2014年10月22日
 */
public class AddRoleModel {

	private String memberId;
	private String sysCode;
	private String roleCode;
	private String roleName;

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

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
