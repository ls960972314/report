package com.report.common.repository;

import java.util.List;

import com.report.common.dal.admin.entity.dto.Resource;
import com.report.common.dal.admin.entity.vo.ResourceModel;

public interface ResourceRepository {

	public List<Resource> findResourceList(ResourceModel resource);

	public List<Resource> findTreeMenu(ResourceModel model);

	public boolean deleteById(Long id);

	public boolean isResourceExist(ResourceModel resource);

	public List findAllResource();

	public List<Long> findResourceIdsByRoleCode(String roleCode);

	public List<Resource> findResourcesByIds(String resourceIds);
	
	public List<Resource> findResourcesByFlag(String reportFlag);
}
