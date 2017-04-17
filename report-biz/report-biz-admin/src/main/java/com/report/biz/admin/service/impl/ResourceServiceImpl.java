package com.report.biz.admin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.report.biz.admin.service.ResourceService;
import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.admin.dao.ResourceDao;
import com.report.common.dal.admin.entity.dto.Resource;
import com.report.common.dal.admin.entity.vo.PackageResourceModel;
import com.report.common.dal.admin.entity.vo.PermissionCell;
import com.report.common.dal.admin.entity.vo.ResourceModel;
import com.report.common.dal.common.BaseDao;
import com.report.common.repository.ResourceRepository;

@Service("resourceService")
@Transactional
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private ResourceDao resourceDao;

    @Override
    public void updateResource(ResourceModel model) {
        // 查询数据库资源信息
        Resource resource = (Resource) baseDao.get(Resource.class, model.getId());
        // 赋值客户端传值
        resource.setSysCode(model.getSysCode());
        resource.setName(model.getName());
        resource.setResourceAction(model.getResourceAction());
        resource.setResourceType(model.getResourceType());
        resource.setDescription(model.getDescription());
        resource.setIcon(model.getIcon());
        resource.setOrderBy(model.getOrderBy() == null ? resource.getOrderBy() : model.getOrderBy());
        resource.setStatus(model.getStatus() == null ? resource.getStatus() : model.getStatus());
        resource.setUpdateTime(new Date());
        resource.setResourceCode(model.getResourceCode());
        // 如果存在父目录保存父信息
        if (null != model.getpId()) {
            resource.setParent((Resource) baseDao.get(Resource.class, model.getpId()));
        }
        baseDao.update(resource);
    }

    @Override
    public void saveResource(ResourceModel model) {

        Resource resource = new Resource();
        // 赋值客户端传值
        resource.setSysCode(model.getSysCode());
        resource.setName(model.getName());
        resource.setResourceAction(model.getResourceAction());
        resource.setResourceType(model.getResourceType());
        resource.setDescription(model.getDescription());
        resource.setIcon(model.getIcon());
        resource.setOrderBy(model.getOrderBy() == null ? resource.getOrderBy() : model.getOrderBy());
        resource.setStatus(Constants.SUCCESS);
        resource.setResourceCode(model.getResourceCode());
        Date now = new Date();
        resource.setUpdateTime(now);
        resource.setCreateTime(now);
        // 如果存在父目录保存父信息
        if (null != model.getpId()) {
            resource.setParent((Resource) baseDao.get(Resource.class, model.getpId()));
        }
        baseDao.save(resource);

    }

    public boolean deleteResource(ResourceModel model) {
    	Resource targetResource = baseDao.get(Resource.class, model.getId());
        targetResource.setStatus(0);
        baseDao.update(targetResource);
        Resource resource = new Resource();
        resource.setId(model.getId());
        SQLQuery sqlQuery = baseDao.getSqlQuery("delete from uc_role_res t where t.RESOURCE_ID=:resId");
        sqlQuery.setLong("resId", targetResource.getId());
        boolean flag = sqlQuery.executeUpdate() > 0;
        
        /* 删除报表中对应的rptpub ,rptcon, rptchart */
        String actionUrl = "";
        if (StringUtils.isNotBlank(targetResource.getResourceAction())) {
        	actionUrl = targetResource.getResourceAction().replaceAll("\\s", "");
        }
        if (StringUtils.isNotBlank(actionUrl) && actionUrl.indexOf("reportFlag") != -1) {
        	String reportFlag = "";
        	/* 查看有多少个 */
        	if (actionUrl.indexOf("|") != -1) {
        		reportFlag = actionUrl.substring(actionUrl.indexOf("reportFlag") + 11, actionUrl.indexOf("|"));
        	} else {
        		reportFlag = actionUrl.substring(actionUrl.indexOf("reportFlag") + 11);
        	}
        	List<Resource> list = resourceRepository.findResourcesByFlag(reportFlag);
        	/* 由于时日周月报等公用下面三个表，所以当只有一个可用目录时才将其删除 */
        	if (list.size() == 1) {
//    			reportChartDao.deleteByReportFlag(reportFlag);
//    	        reportPublicDao.deleteByReportFlag(reportFlag);
//    	        reportConditionDao.deleteByReportFlag(reportFlag);
        	}
        } else {
        }
        
        return flag;
    }

    /**
     * 获取资源列表
     */

    public List<PackageResourceModel> findPrivilegeBySysCode() {
        List<Resource> resourceList = resourceRepository.findResourceList(null);
        List<PackageResourceModel> tempResourceList = new ArrayList<PackageResourceModel>();
        PackageResourceModel temp = null;
        for (Resource r : resourceList) {
            temp = new PackageResourceModel();
            setResourceListToTemp(r, temp);
            tempResourceList.add(temp);
        }

        return tempResourceList;
    }

    /**
     * 把不需要客户端显示的信息设置为null
     * 
     * @param source
     */
    private void setResourceListToTemp(Resource source, PackageResourceModel target) {

        setResource(source, target);
        if (source.getChildren() != null) {
            List<PackageResourceModel> children = new ArrayList<PackageResourceModel>();
            for (Resource t : source.getChildren()) {
                PackageResourceModel temp = new PackageResourceModel();
                setResource(source, target);
                children.add(temp);
                setResourceListToTemp(t, temp);
            }

            target.setChildren(children);
        }
    }

    private void setResource(Resource source, PackageResourceModel target) {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setText(source.getName());
        target.setResourceAction(source.getResourceAction());
        target.setResourceType(source.getResourceType());
        target.setCreateTime(source.getCreateTime());
        target.setUpdateTime(source.getUpdateTime());
        target.setDescription(source.getDescription());
        target.setSysCode(source.getSysCode());
        if (source.getParent() != null) {
            target.setpId(source.getParent().getId());
        }
    }

    @Override
    public List<PackageResourceModel> findTreeMenu(ResourceModel model) {
        List<Resource> resourceList = resourceRepository.findTreeMenu(model);
        List<PackageResourceModel> tempResourceList = new ArrayList<PackageResourceModel>();
        PackageResourceModel temp = null;
        for (Resource r : resourceList) {
            temp = new PackageResourceModel();
            setResourceListToTemp(r, temp);
            tempResourceList.add(temp);
        }

        return tempResourceList;
    }

    @Override
    public boolean isResourceExist(ResourceModel resource) {
        return resourceRepository.isResourceExist(resource);
    }

    @Override
    public List<Map<String, Object>> findAllResource() {
        return resourceRepository.findAllResource();
    }

    @Override
    public List<Long> findResourceIdsByRoleCode(String roleCode) {
        return resourceRepository.findResourceIdsByRoleCode(roleCode);
    }

    @Override
    public List<PermissionCell> findPermissionCellByMemberId(Long memberId) {
        List<PermissionCell> list = resourceRepository.findPermissionCellByMemberId(memberId);
        return (List<PermissionCell>) (list == null || list.isEmpty() ? Collections.emptyList() : list);
    }
    
    @Override
    public List<Map<String, Object>> findResourceByMemberId(Long memberId) {
	    List list = resourceDao.findResourceByMemberId(memberId);
	    return (List) (list == null || list.isEmpty() ? Collections.emptyList() : list);
    }

}
