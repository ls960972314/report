package com.report.common.dal.admin.dao;

import java.util.List;
import java.util.Map;

import com.report.common.dal.admin.entity.vo.PermissionCell;

public interface ResourceDao {

	public List<Long> getResourceIdsByRoleCode(String roleCode);
	
	public List<PermissionCell> findPermissionCellByMemberId(Long memberId);
	
	public List<Map<String, Object>> findResourceByMemberId(Long memberId);
}
