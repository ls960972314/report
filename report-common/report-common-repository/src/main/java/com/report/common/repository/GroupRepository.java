package com.report.common.repository;

import java.util.List;
import java.util.Map;

import com.report.common.dal.admin.entity.dto.Group;
import com.report.common.dal.admin.entity.vo.GroupModel;
import com.report.facade.entity.PageHelper;

public interface GroupRepository {

	public String getGroupCodeByMemberId(Long memberId);
	
	public List<GroupModel> fingGroupsByPage(PageHelper pageHelper, GroupModel groupModel);

	public Long count(GroupModel groupModel);
	
	public void updateGroupCodeByMemberId(Long memberId, String groupCode, String memberIp);

	public List<Group> findAllGroups();

	public List<Map<String, String>> findGroupNamesByCurrentMemberId(Long currentMemberId);
	
	public boolean isGroupCodeExists(GroupModel groupModel);
	
	public boolean isAssociatedWithGroup(Long memberId);
	
	public void associatedWithGroup(Long memberId, String groupCode, String memberIp);
}
