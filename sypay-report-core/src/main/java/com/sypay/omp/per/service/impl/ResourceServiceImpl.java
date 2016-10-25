package com.sypay.omp.per.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sypay.omp.per.common.Constants;
import com.sypay.omp.per.dao.ResourceDao;
import com.sypay.omp.per.domain.Resource;
import com.sypay.omp.per.model.PackageResourceModel;
import com.sypay.omp.per.model.PermissionCell;
import com.sypay.omp.per.model.ResourceModel;
import com.sypay.omp.per.service.PermissionFactoryService;
import com.sypay.omp.per.service.ResourceService;
import com.sypay.omp.report.dao.BaseDao;
import com.sypay.omp.report.dao.ReportChartDao;
import com.sypay.omp.report.dao.ReportConditionDao;
import com.sypay.omp.report.dao.ReportPublicDao;
import com.sypay.omp.report.service.MybatisBaseService;
import com.sypay.omp.report.util.StringUtil;

@Service("resourceService")
@Transactional
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private PermissionFactoryService permissionFactoryService;
    @Autowired
    private MybatisBaseService mybatisBaseService;
    @Autowired
    private ReportChartDao reportChartDao;
    
    @Autowired
    private ReportConditionDao reportConditionDao;
    
    @Autowired
    private ReportPublicDao reportPublicDao;
    /**
     * 获取资源列表
     */
    public List<PackageResourceModel> findResourceList(ResourceModel resource) {
        List<Resource> resourceList = resourceDao.findResourceList(resource);
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
        if (StringUtil.isNotEmpty(model.getpId())) {
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
        resource.setStatus(Constants.ResourceStatus.VALID);
        resource.setResourceCode(model.getResourceCode());
        Date now = new Date();
        resource.setUpdateTime(now);
        resource.setCreateTime(now);
        // 如果存在父目录保存父信息
        if (StringUtil.isNotEmpty(model.getpId())) {
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
        if (StringUtil.isNotEmpty(targetResource.getResourceAction())) {
        	actionUrl = targetResource.getResourceAction().replaceAll("\\s", "");
        }
        if (StringUtil.isNotEmpty(actionUrl) && actionUrl.indexOf("reportFlag") != -1) {
        	String reportFlag = "";
        	/* 查看有多少个 */
        	if (actionUrl.indexOf("|") != -1) {
        		reportFlag = actionUrl.substring(actionUrl.indexOf("reportFlag") + 11, actionUrl.indexOf("|"));
        	} else {
        		reportFlag = actionUrl.substring(actionUrl.indexOf("reportFlag") + 11);
        	}
        	List<Resource> list = resourceDao.findResourcesByFlag(reportFlag);
        	/* 由于时日周月报等公用下面三个表，所以当只有一个可用目录时才将其删除 */
        	if (list.size() == 1) {
    			reportChartDao.deleteByReportFlag(reportFlag);
    	        reportPublicDao.deleteByReportFlag(reportFlag);
    	        reportConditionDao.deleteByReportFlag(reportFlag);
        	}
        } else {
        }
        
        return flag;
    }

    /**
     * 获取资源列表
     */

    public List<PackageResourceModel> findPrivilegeBySysCode() {
        List<Resource> resourceList = resourceDao.findResourceList(null);
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
        List<Resource> resourceList = resourceDao.findTreeMenu(model);
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
        return resourceDao.isResourceExist(resource);
    }

    @Override
    public List<Map<String, Object>> findAllResource() {
        return resourceDao.findAllResource();
    }

    @Override
    public List<Long> findResourceIdsByRoleCode(String roleCode) {
        return resourceDao.findResourceIdsByRoleCode(roleCode);
    }

    @Override
    public List<PermissionCell> findPermissionCellByMemberId(Long memberId) {
        List<PermissionCell> list = mybatisBaseService.selectList("resource.findPermissionCellByMemberId", memberId);

        return (List<PermissionCell>) (list == null || list.isEmpty() ? Collections.emptyList() : list);
    }

    @Override
    public List<Map<String, Object>> findResourceByMemberId(Long memberId) {
        List list = mybatisBaseService.selectList("resource.findResourceByMemberId", memberId);

        return (List) (list == null || list.isEmpty() ? Collections.emptyList() : list);

    }
}
