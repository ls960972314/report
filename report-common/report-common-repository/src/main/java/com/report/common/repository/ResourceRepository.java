package com.report.common.repository;

import java.util.List;
import java.util.Set;

import com.report.common.dal.admin.entity.dto.Resource;
import com.report.common.dal.admin.entity.vo.ResourceModel;

/**
 * 
 * @author lishun
 * @since 2017年3月24日 下午5:27:49
 */
public interface ResourceRepository {

	/**
	 * 根据用户账号查找权限集合
	 * @param accNo
	 * @return
	 */
	public Set<String> findPermissions(String accNo);
	
	public List<Resource> findResourceList(ResourceModel resource);

	public List<Resource> findTreeMenu(ResourceModel model);

	public boolean deleteById(Long id);

	public boolean isResourceExist(ResourceModel resource);

	public List findAllResource();

	public List<Long> findResourceIdsByRoleCode(String roleCode);

	public List<Resource> findResourcesByIds(String resourceIds);
	
	public List<Resource> findResourcesByFlag(String reportFlag);
}
