package com.sypay.omp.per.dao;

import com.sypay.omp.per.model.GroupModel;

public interface GroupRoleDao {
	void addMapping4GroupAndRole(GroupModel groupModel, Long currentMemberId, String memberIp);
	
	void removeMapping4GroupAndRole(GroupModel groupModel, Long currentMemberId, String memberIp);
}
