package com.sypay.omp.per.web;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sypay.omp.per.common.Constants;
import com.sypay.omp.per.common.ResultCodeConstants;
import com.sypay.omp.per.domain.Role;
import com.sypay.omp.per.model.RoleCriteriaModel;
import com.sypay.omp.per.model.RoleListSysModel;
import com.sypay.omp.per.model.RoleModel;
import com.sypay.omp.per.model.page.AjaxJson;
import com.sypay.omp.per.model.page.DataGrid;
import com.sypay.omp.per.model.page.PageHelper;
import com.sypay.omp.per.service.PermissionFactoryService;
import com.sypay.omp.per.service.RoleService;
import com.sypay.omp.per.util.SessionUtil;
import com.sypay.omp.report.util.StringUtil;

/**
 * 角色管理
 * 
 * @author sypay
 * 
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Resource
    private RoleService roleService;
    @Resource
    private PermissionFactoryService permissionFactoryService;

    @RequestMapping(value = "/role.htm")
    public String role(HttpServletRequest request) {
        return "role/roleList";
    }

    @RequestMapping("/findRoleListByCriteria.htm")
    @ResponseBody
    public DataGrid findRoleListByCriteria(PageHelper pageHelper, RoleCriteriaModel roleCriteriaModel) {
        return roleService.findRoleListByCriteria(pageHelper, roleCriteriaModel);
    }

    @RequestMapping(value = "/addOrUpdate.htm")
    @ResponseBody
    public AjaxJson addOrUpdate(RoleModel model, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (StringUtils.isBlank(model.getRoleCode()) || StringUtils.isBlank(model.getName())) {
            j.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return j;
        }

        // 先判断id是否为空，如果为空的话(即当前操作是保存)，则检查角色编码是否存在；
        // 如果不为空的话(即当前操作为更新)，则拿当前的角色编码和数据库中的角色编码进行比较，如果不一致，则报错

        int result = Constants.OpStatus.FAIL;

        if (StringUtil.isNotEmpty(model.getId())) {

            // 更新操作

            // 检查当前角色编码是否和数据库中的角色编码是否一致
            if (!roleService.isSameRoleCode(model.getId(), model.getRoleCode())) {
                j.setStatus(ResultCodeConstants.RESULT_ROLE_CODE_CANNOT_BE_MODIFIED);
                j.setErrorInfo("角色编码不能修改");
                return j;
            }

            result = roleService.updateRole(model, SessionUtil.getCurrentMemberId(), request.getRemoteAddr());
            j.setStatus(result);
            if (result == Constants.OpStatus.FAIL) {
                j.setErrorInfo("更新失败！");
            }
        } else {

            // 保存操作

            // 检查编码是否存在
            if (roleService.isRoleCodeExists(model)) {
                j.setErrorNo(ResultCodeConstants.RESULT_ROLE_CODE_IS_EXISTS);
                return j;
            }

            result = roleService.saveRole(model, SessionUtil.getCurrentMemberId(), request.getRemoteAddr());
            j.setStatus(result);
            if (result == Constants.OpStatus.FAIL) {
                j.setErrorInfo("保存失败！");
            }
        }

        return j;
    }

    @RequestMapping(value = "/del.htm")
    @ResponseBody
    public AjaxJson del(Role role, HttpServletRequest request) {
        AjaxJson ajaxJson = null;

        if (role.getId() == null) {
            ajaxJson = new AjaxJson();
            ajaxJson.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return ajaxJson;
        }

        return roleService.removeRole(role.getId());
    }

    @RequestMapping("/findAllRoles.htm")
    @ResponseBody
    public List<RoleListSysModel> findAllRole() {
        return roleService.getRoleListBySysCodes(null);
    }

    /**
     * 保存授权
     * 
     * @return
     */
    @RequestMapping(value = "/saveRoleResources.htm")
    @ResponseBody
    public AjaxJson saveRoleResources(RoleModel model, HttpServletRequest request) {
        AjaxJson ajaxJson = new AjaxJson();
        int result = roleService.saveRoleResources(model);

        ajaxJson.setStatus(result);
        return ajaxJson;
    }

    @RequestMapping(value = "/getRoleListByGroupCode.htm")
    @ResponseBody
    public List<RoleListSysModel> getRoleListByGroupCode(String groupCode) {
        if (StringUtils.isNotBlank(groupCode)) {
            List<RoleListSysModel> roleListAll = roleService.getRoleListBySysCodes(null);

            List roleList = roleService.getRoleListByGroupCode(groupCode);

            if (!roleList.isEmpty()) {
                checkRoleList(roleList, roleListAll);
            }
            return roleListAll;
        }

        return Collections.emptyList();
    }

    private void checkRoleList(List roleList, List<RoleListSysModel> roleListAll) {
        if (roleList == null || roleList.isEmpty() || roleListAll == null || roleListAll.isEmpty()) {
            return;
        }

        for (RoleListSysModel modelAll : roleListAll) {

            if (modelAll.getChildren() == null || modelAll.getChildren().size() == 0) continue;

            for (RoleListSysModel roleModel : modelAll.getChildren()) {
                for (Object roleCode : roleList) {
                    if (roleCode != null && String.valueOf(roleCode).equals(roleModel.getId())) {
                        roleModel.setChecked(true);
                    }
                }
            }
        }
    }

    @RequestMapping(value = "/getRoleCodeAndNameList.htm")
    @ResponseBody
    public List<Map<String, Object>> getRoleCodeAndNameList(String groupCode) {
        if (StringUtils.isNotBlank(groupCode)) {
            return roleService.getRoleCodeAndNameList(groupCode);
        }

        return Collections.emptyList();
    }
}
