package com.report.common.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.report.common.dal.admin.entity.dto.Role;
import com.report.common.dal.admin.entity.vo.RoleCell;
import com.report.common.dal.admin.entity.vo.RoleCriteriaModel;
import com.report.common.dal.admin.entity.vo.RoleListSysModel;
import com.report.common.dal.admin.entity.vo.RoleModel;
import com.report.facade.entity.PageHelper;

public interface RoleRepository {

	/**
	 * 根据用户账号查找角色集合
	 * @param accNo
	 * @return
	 */
	public Set<String> findRoles(String accNo);
	
	List<Role> findRoles();

	List<Map<String, String>> findRoleNamesByGroupCode(String groupCode);
	
	List<RoleListSysModel> getRoleListBySysCodes(Set<String> sysCodes);
	
	void clearMiddleTableData(Long id);
	
	boolean isRoleCodeExists(RoleModel roleModel);
	
	List getRoleListByGroupCode(String groupCode);
	
	List<Map<String, Object>> getRoleCodeAndNameList(String groupCode);

	List<String> findSysCodeByRoleCodes(List<String> roleCodes);

	List<RoleModel> findRoleListByCriteria(PageHelper pageHelper, RoleCriteriaModel roleCriteriaModel);

	Long countRoleByCriteria(RoleCriteriaModel roleCriteriaModel);

	List<Role> findCurrentSysRolesBySysCode(String sysCode);

	boolean isSameRoleCode(Long roleId, String roleCode);

	List<Role> findAllRoles(Long memberId);

    RoleCell getByRoleCode(String roleCode);

    List<String> findRoleCodeByMemberId(Long memberId);
}
