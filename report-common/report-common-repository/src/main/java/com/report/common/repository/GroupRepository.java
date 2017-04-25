package com.report.common.repository;

import java.util.List;
import java.util.Map;

import com.report.common.dal.admin.entity.dto.Group;
import com.report.common.dal.admin.entity.vo.GroupModel;
import com.report.common.model.PageHelper;

public interface GroupRepository {

	public String getGroupCodeByMemberId(Long memberId);
	
	public List<GroupModel> fingGroupsByPage(PageHelper pageHelper, GroupModel groupModel);

	public Long count(GroupModel groupModel);
	
	/**
	 * 更新用户和组的关系
	 * @param memberId
	 * @param groupCode
	 */
	public void updateGroupCodeByMemberId(Long memberId, String groupCode);

	public List<Group> findAllGroups();

	public List<Map<String, String>> findGroupNamesByCurrentMemberId(Long currentMemberId);
	
	public boolean isGroupCodeExists(GroupModel groupModel);
	
	/**
	 * 用户是否有关联的组
	 * @param memberId
	 * @return
	 */
	public boolean isAssociatedWithGroup(Long memberId);
	
	/**
	 * 新增用户和组的关系
	 * @param memberId
	 * @param groupCode
	 */
	public void associatedWithGroup(Long memberId, String groupCode);
	/**
	 * 删除人员和组别的关系
	 * @param memberId
	 */
	public void deleteAssociateWithMember(Long memberId);
}
