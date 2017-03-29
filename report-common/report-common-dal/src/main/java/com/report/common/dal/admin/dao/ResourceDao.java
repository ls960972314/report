package com.report.common.dal.admin.dao;

import java.util.List;
import java.util.Map;

import com.report.common.dal.admin.entity.vo.PermissionCell;

/**
 * 
 * @author lishun
 * @since 2017年3月24日 下午5:42:51
 */
public interface ResourceDao {
	
	/**
	 * 根据用户账号查找权限集合
	 * @param accNo
	 * @return
	 */
	public List<String> findPermissions(String accNo);

	public List<Long> getResourceIdsByRoleCode(String roleCode);
	
	public List<PermissionCell> findPermissionCellByMemberId(Long memberId);
	
	public List<Map<String, Object>> findResourceByMemberId(Long memberId);
}
