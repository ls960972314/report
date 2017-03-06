
package com.sypay.omp.per.dao;

import java.util.List;
import java.util.Map;

import com.sypay.omp.per.domain.Group;
import com.sypay.omp.per.model.GroupModel;
import com.sypay.omp.per.model.page.PageHelper;

public interface GroupDao {

	List<GroupModel> fingGroupsByPage(PageHelper pageHelper, GroupModel groupModel);

	Long count(GroupModel groupModel);
	
	Group getGroupByGroupCode(String groupCode);
	
	void updateGroupCodeByMemberId(Long memberId, String groupCode, String memberIp);

	List<Group> findAllGroups();

	List<Map<String, String>> findGroupNamesByCurrentMemberId(Long currentMemberId);
	
	boolean isGroupCodeExists(GroupModel groupModel);
	
	boolean isAssociatedWithGroup(Long memberId);
	
	void associatedWithGroup(Long memberId, String groupCode, String memberIp);
}
