package com.sypay.omp.per.model;

/**
 * 位于HttpSession中的属性名，该属性用于存储登录用户的信息
 * @author lishun
 *
 */
public class UserSession implements java.io.Serializable {

	private static final long serialVersionUID = 7822263077678335329L;

	/**
	 * 登录用户名
	 */
	private Object name;
	
	/**
	 * 登录用户对象
	 */
	private Object loginInfo;
	
	/**
	 * 登录用户ID
	 */
	private Object loginMemberId;
	
	/**
	 * 登录用户拥有的组Code
	 */
	private Object groupCode;
	
	/**
	 * 是否是管理员权限
	 */
	private Object isPerAdmin;
	
	/**
	 * 登录用户拥有的角色Code
	 */
	private Object roleCode;
	
	private Object menuList;
	
	private Object reportMenuList;
	
	private Object hasPrevilege;

	public Object getName() {
		return name;
	}

	public void setName(Object name) {
		this.name = name;
	}

	public Object getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(Object loginInfo) {
		this.loginInfo = loginInfo;
	}

	public Object getLoginMemberId() {
		return loginMemberId;
	}

	public void setLoginMemberId(Object loginMemberId) {
		this.loginMemberId = loginMemberId;
	}

	public Object getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(Object groupCode) {
		this.groupCode = groupCode;
	}

	public Object getIsPerAdmin() {
		return isPerAdmin;
	}

	public void setIsPerAdmin(Object isPerAdmin) {
		this.isPerAdmin = isPerAdmin;
	}

	public Object getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(Object roleCode) {
		this.roleCode = roleCode;
	}

	public Object getMenuList() {
		return menuList;
	}

	public void setMenuList(Object menuList) {
		this.menuList = menuList;
	}

	public Object getReportMenuList() {
		return reportMenuList;
	}

	public void setReportMenuList(Object reportMenuList) {
		this.reportMenuList = reportMenuList;
	}

	public Object getHasPrevilege() {
		return hasPrevilege;
	}

	public void setHasPrevilege(Object hasPrevilege) {
		this.hasPrevilege = hasPrevilege;
	}
}
