package com.sypay.omp.per.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sypay.omp.per.common.Constants;
import com.sypay.omp.per.common.ResultCodeConstants;
import com.sypay.omp.per.model.GroupModel;
import com.sypay.omp.per.model.page.AjaxJson;
import com.sypay.omp.per.model.page.DataGrid;
import com.sypay.omp.per.model.page.PageHelper;
import com.sypay.omp.per.service.GroupService;
import com.sypay.omp.per.service.RoleService;
import com.sypay.omp.per.util.SessionUtil;

/**
 * @Title: Controller
 * @Description: 组
 * @date 2014-10-14 14:10:27
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/group")
public class GroupController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService groupService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/group.htm")
    public String findGroupList(HttpServletRequest request) {
        return "group/groupList";
    }

    @RequestMapping(value = "/findGroupList.htm")
    @ResponseBody
    public DataGrid findGroupList(HttpServletRequest request, PageHelper pageHelper, GroupModel groupModel) {
        groupModel.setCurrentMemberGroupCode((String) request.getSession().getAttribute(Constants.SESSION_LOGIN_MEMBER_GROUP_CODE));
        return groupService.findGroups(pageHelper, groupModel);
    }

    @RequestMapping(value = "/findAllGroupNames.htm")
    @ResponseBody
    public List<Map<String, String>> findAllGroupNames() {
        return groupService.findGroupNamesByCurrentMemberId(SessionUtil.getCurrentMemberId());
    }

    @RequestMapping(value = "/addOrUpdateGroup.htm")
    @ResponseBody
    public AjaxJson addOrUpdateGroup(GroupModel groupModel, HttpServletRequest request) {
        AjaxJson json = new AjaxJson();

        if (StringUtils.isBlank(groupModel.getGroupCode()) || StringUtils.isBlank(groupModel.getGroupName())) {
            json.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return json;
        }

        // 判断当前是更新还是新增
        // 更新：判断当前组编码和数据库中的组编码是否一致
        // 新增：判断组编码是否已经存在

        int status = Constants.OpStatus.FAIL;

        if (groupModel.getId() != null) {
            // 更新操作

            if (!groupService.isSameGroupCode(groupModel.getId(), groupModel.getGroupCode())) {
                json.setErrorNo(ResultCodeConstants.RESULT_GROUP_CODE_CANNOT_BE_MODIFIED);
                json.setErrorInfo("组编码不能修改！");
                return json;
            }

            status = groupService.updateGroup(groupModel, SessionUtil.getCurrentMemberId(), request.getRemoteAddr());
            if (status == Constants.OpStatus.FAIL) {
                json.setStatus(status);
                json.setErrorInfo("更新失败！");
                return json;
            }
        } else {
            // 新增操作

            if (groupService.isGroupCodeExists(groupModel)) {
                json.setErrorNo(ResultCodeConstants.RESULT_GROUP_IS_EXISTS);
                json.setErrorInfo("组编码已经存在！");
                return json;
            }

            status = groupService.saveGroup(groupModel, SessionUtil.getCurrentMemberId(), request.getRemoteAddr());
            if (status == Constants.OpStatus.FAIL) {
                json.setStatus(status);
                json.setErrorInfo("保存失败！");
            }
        }
        return json;
    }

    @RequestMapping(value = "/deleteGroup.htm")
    @ResponseBody
    public AjaxJson deleteGroup(Long id, HttpServletRequest request) {

        AjaxJson ajaxJson = null;

        // 只有权限管理员能够执行
        if (!SessionUtil.isPerAdmin()) {
            ajaxJson = new AjaxJson();
            ajaxJson.setErrorNo(ResultCodeConstants.RESULT_PER_ADMIN_HAS_PRIV);
            return ajaxJson;
        }

        return groupService.deleteGroupById(id, SessionUtil.getCurrentMemberId(), request.getRemoteAddr());
    }

}
