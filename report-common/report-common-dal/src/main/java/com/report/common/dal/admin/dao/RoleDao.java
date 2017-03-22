package com.report.common.dal.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.report.common.dal.admin.entity.dto.Role;
import com.report.common.dal.admin.entity.vo.RoleModel;

public interface RoleDao {
	
	public  List<Map<String, Object>> findRoleList4PerAdmin();
	
	public List<RoleModel> findRoleListByCriteria4SysAdmin(Map<String, Object> params);
	
	public List<RoleModel> findRoleListByCriteria4PerAdmin(Map<String, Object> params, RowBounds rowBounds);
	
	public List<RoleModel> findRoleListByRoleCodes(Map<String, Object> params, RowBounds rowBounds);
	
	public Long countRoleByCriteria(Map<String, Object> params);
	
	public Long countRoleByRoleCodes(Map<String, Object> params);
	
	public void deleteRoleLogically(Map<String, Object> params);
	
	public void deleteRolePhysically(Map<String, Object> params);
	
	public String getRoleCodeByRoleId(Long id);
	
	public void deleteMappingRole2Resource(Map<String, Object> params);
	
	public Integer countRoleByRoleCodeAndSysCode(Map<String, Object> params);
	
	public Integer countResourceByRoleCode(String roleCode);
	
	public Integer countGroupByRoleCode(String roleCode);
	
	public List<Map<String, String>> findRoleNamesByGroupCode(String groupCode);
	
	public List getRoleListByGroupCode(String groupCode);
	
	public List<Map<String, Object>> getRoleCodeAndNameList(String groupCode);
	
	public Integer isSameRoleCode(Map<String, Object> params);
	
	public List<Role> findAllRoles(Long memberId);
	
	public List<String> findRoleCodeByMemberId(Long memberId);
}
