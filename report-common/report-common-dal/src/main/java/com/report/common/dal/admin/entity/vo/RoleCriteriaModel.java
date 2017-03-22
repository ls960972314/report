package com.report.common.dal.admin.entity.vo;


public class RoleCriteriaModel 
{
	private String roleCode;
	private String roleName;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		if(roleCode != null)
		{
			roleCode = roleCode.trim();
			if(roleCode.length() > 0)
			{
				this.roleCode = roleCode + "%";
			}
		}
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		if(roleName != null)
		{
			roleName = roleName.trim();
			if(roleName.length() > 0)
			{
				this.roleName = roleName + "%";
			}
		}
	}
}
