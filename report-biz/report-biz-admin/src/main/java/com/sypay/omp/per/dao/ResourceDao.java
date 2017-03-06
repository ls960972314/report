
package com.sypay.omp.per.dao;

import java.util.List;

import com.sypay.omp.per.domain.Resource;
import com.sypay.omp.per.model.ResourceModel;



public interface ResourceDao {

	List<Resource> findResourceList(ResourceModel resource);

	List<Resource> findTreeMenu(ResourceModel model);

	boolean deleteById(Long id);

	boolean isResourceExist(ResourceModel resource);

	List findAllResource();

	List<Long> findResourceIdsByRoleCode(String roleCode);

	List<Resource> findResourcesByIds(String resourceIds);
	
	List<Resource> findResourcesByFlag(String reportFlag);
}
