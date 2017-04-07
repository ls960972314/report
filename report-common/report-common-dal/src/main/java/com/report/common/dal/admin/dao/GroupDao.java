package com.report.common.dal.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.report.common.dal.admin.entity.dto.Group;
import com.report.common.dal.admin.entity.vo.GroupModel;


public interface GroupDao {
	
	public List<Group> findAllGroups();
	
	public String queryGroupByRoles(Map<String, Object> params);
	
	public List<GroupModel> findGroupdByPage4SysAdmin(Map<String, Object> params, RowBounds row);

	public Long countForGroupList(Map<String, Object> params);
	
	public List<Map<String, String>> findGroupNames4SysAdmin();
	
	public Group getGroupByGroupCode(String groupCode);
	
	public void updateGroupCodeByMemberId(Map<String, Object> params);
	
	public void deleteGroup(Map<String, Object> params);
	
	public Integer countMemberByGroupCode(String groupCode);
	
	public Integer countRolebyGroupCode(String groupCode);
	
	public Integer countGroupByGroupCode(Map<String, Object> params);
	
	public Integer countGroupByMemberId(Long memberId);
	
	public void addMappingGroup2Member(Map<String, Object> memberGroup);
	
	public String getGroupCodeByMemberId(Long memberId);
	
	public Integer isSameGroupCode(Map<String, Object> params);

	/**
	 * 删除用户和组的关系
	 * @param memberId
	 */
	public void deleteAssociateWithMember(Long memberId);
}
