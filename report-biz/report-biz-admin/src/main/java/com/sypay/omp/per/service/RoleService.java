package com.sypay.omp.per.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sypay.omp.per.model.RoleCriteriaModel;
import com.sypay.omp.per.model.RoleListSysModel;
import com.sypay.omp.per.model.RoleModel;
import com.sypay.omp.per.model.page.AjaxJson;
import com.sypay.omp.per.model.page.DataGrid;
import com.sypay.omp.per.model.page.PageHelper;

public interface RoleService {
	DataGrid findRoleListByCriteria(PageHelper pageHelper, RoleCriteriaModel roleCriteriaModel);

	/**
	 * 更新角色
	 * @param role
	 * @param currentMemberIp 
	 * @param currentMemberId 
	 * @return 更新成功返回1，否则返回0
	 */
	int updateRole(RoleModel role, Long currentMemberId, String currentMemberIp);

	/**
	 * 保存角色
	 * @param role
	 * @param string 
	 * @param long1 
	 */
	int saveRole(RoleModel role, Long currentMemberId, String currentMemberIp);

	/**
	 * 保存对角色授予的资源
	 * @param model
	 * @return 
	 */
	int saveRoleResources(RoleModel model);

	AjaxJson removeRole(Long id);
	
	List<RoleListSysModel> getRoleListBySysCodes(Set<String> sysCodes);

	boolean isRoleCodeExists(RoleModel roleModel);

	List getRoleListByGroupCode(String groupCode);
	
	List<Map<String, Object>> getRoleCodeAndNameList(String groupCode);

	List<String> findSysCodeByRoleCodes(List<String> roleCodes);
	
	boolean isSameRoleCode(Long roleId, String roleCode);
}
