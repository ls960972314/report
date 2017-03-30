package com.report.web.admin.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.report.biz.admin.service.ResourceService;
import com.report.common.dal.admin.entity.vo.PackageResourceModel;
import com.report.common.dal.admin.entity.vo.ResourceModel;
import com.report.common.model.AjaxJson;
import com.report.common.model.ResultCodeConstants;
import com.report.common.model.SessionUtil;
import com.report.common.util.SysCodeResourcesUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Resource
    private ResourceService resourceService;

    /**
     * 跳转到资源列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/resource.htm")
    public String resource() {
        return "resource/resourceList";
    }

    /**
     * 资源列表
     * 
     * @param request
     * @param resource
     * @param dg
     * @return
     */
    @RequestMapping(value = "/findResourceList.htm")
    @ResponseBody
    public List<PackageResourceModel> findResourceList(HttpServletRequest request, ResourceModel resource) {

        return SysCodeResourcesUtil.findResourceListFromCache(getFormatterSysCodeReourceMap());
    }

    @RequestMapping(value = "/addOrUpdate.htm")
    @ResponseBody
    public AjaxJson addOrUpdate(ResourceModel resource, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (StringUtils.isBlank(resource.getName()) || StringUtils.isBlank(resource.getResourceType())) {
            j.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return j;
        }
        if (resourceService.isResourceExist(resource)) {
            j.setErrorNo(ResultCodeConstants.RESULT_RESOURCE_ALREADY_EXISTS);
            return j;
        }

        if (null != resource.getId()) {
            resourceService.updateResource(resource);
        } else {
            resourceService.saveResource(resource);
        }

        return j;
    }

    @RequestMapping(value = "/del.htm")
    @ResponseBody
    public AjaxJson del(ResourceModel resource, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        boolean isDeleteSucc = false;
        if (null != resource.getId()) {
            isDeleteSucc = resourceService.deleteResource(resource);
        }

        if (!isDeleteSucc) {
            j.setErrorNo(ResultCodeConstants.RESULT_CHECK_RESOURCE_IS_IN_USE);
        }

        return j;
    }

    /**
     * 获取顶级列表
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/findTreeMenu.htm")
    @ResponseBody
    public List<PackageResourceModel> findTreeMenu(HttpServletRequest request, ResourceModel model) {
        return SysCodeResourcesUtil.findResourceListFromCache(getFormatterSysCodeReourceMap());
    }

    /**
     * 获取所有资源
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/getDistriResources.htm")
    @ResponseBody
    public List<PackageResourceModel> getDistriResources(HttpServletRequest request, ResourceModel model, String roleCode) {
        return SysCodeResourcesUtil.findResourceListAndCheckSelectStatus(getFormatterSysCodeReourceMap(), resourceService.findResourceIdsByRoleCode(roleCode));
    }

    /**
     * 获取指定角色所持有的资源
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/getPartialDistriResources.htm")
    @ResponseBody
    public List<PackageResourceModel> getPartialDistriResources(HttpServletRequest request, ResourceModel model, String roleCode) {
        return SysCodeResourcesUtil.findResourceListForPrivAndCheckSelectStatus(getFormatterSysCodeReourceMap(), resourceService.findResourceIdsByRoleCode(roleCode));
    }

    private Map<String, List<PackageResourceModel>> getFormatterSysCodeReourceMap() {
        Session session = SessionUtil.getHttpSession();


        List<Map<String, Object>> resourceList = Collections.emptyList();
        if (SessionUtil.getUserInfo().isAdmin()) {
            resourceList = resourceService.findAllResource();

        } else {
            resourceList = resourceService.findResourceByMemberId(SessionUtil.getUserInfo().getMember().getId());
        }
        Map<String, List<PackageResourceModel>> sysCodeResourcesMap = new HashMap<String, List<PackageResourceModel>>();
        for (Map<String, Object> m : resourceList) {
            SysCodeResourcesUtil.putResource(sysCodeResourcesMap, m);
        }

        return sysCodeResourcesMap;
    }
}
