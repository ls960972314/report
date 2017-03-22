package com.report.common.dal.admin.dao;

import com.report.common.dal.admin.entity.vo.GroupModel;

public interface GroupRoleDao {
	
	public void addMapping4GroupAndRole(GroupModel groupModel, Long currentMemberId, String memberIp);
	
	public void removeMapping4GroupAndRole(GroupModel groupModel, Long currentMemberId, String memberIp);
}
