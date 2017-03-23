package com.report.biz.admin.service;

import java.util.List;
import java.util.Map;

import com.report.common.dal.admin.entity.vo.PackageResourceModel;
import com.report.common.dal.admin.entity.vo.PermissionCell;
import com.report.common.dal.admin.entity.vo.ResourceModel;

/**
 * 资源service
 * 
 * @author sypay
 * 
 */
public interface ResourceService {
	/**
	 * 返回列表
	 * 
	 * @param resource
	 * @param dg
	 * @return
	 */
	List<PackageResourceModel> findResourceList(ResourceModel resource);

	/**
	 * 更新对象
	 * 
	 * @param tresource
	 */
	void updateResource(ResourceModel tresource);

	/**
	 * 保存对象
	 * 
	 * @param tresource
	 */
	void saveResource(ResourceModel tresource);

	List<PackageResourceModel> findTreeMenu(ResourceModel model);

	/**
	 * 删除byid
	 * 
	 * @param resource
	 */
	boolean deleteResource(ResourceModel resource);

	/**
	 * 授权列表
	 * 
	 * @return
	 */
	List<PackageResourceModel> findPrivilegeBySysCode();

	boolean isResourceExist(ResourceModel resource);

	List<Map<String, Object>> findAllResource();

	List<Long> findResourceIdsByRoleCode(String roleCode);
	
	List<PermissionCell> findPermissionCellByMemberId(Long memberId);

    List<Map<String, Object>> findResourceByMemberId(Long memberId);
}
