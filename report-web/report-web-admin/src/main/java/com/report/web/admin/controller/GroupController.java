package com.report.web.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.report.biz.admin.service.GroupService;
import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.admin.entity.vo.GroupModel;
import com.report.common.model.AjaxJson;
import com.report.common.model.DataGrid;
import com.report.common.model.PageHelper;
import com.report.common.model.ResultCodeConstants;
import com.report.common.model.SessionUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 组别Controller
 * @author lishun
 * @since 2017年4月8日 上午10:50:53
 */
@Slf4j
@Controller
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/group.htm")
    public String index(HttpServletRequest request) {
        return "group/groupList";
    }

    @RequestMapping(value = "/findGroupList.htm")
    @ResponseBody
    public DataGrid findGroupList(HttpServletRequest request, GroupModel groupModel, PageHelper pageHelper) {
    	log.debug("findGroupList GroupModel[{}], PageHelper[{}]", JSON.toJSONString(groupModel), JSON.toJSONString(pageHelper));
        groupModel.setCurrentMemberGroupCode(SessionUtil.getUserInfo().getGroupCode());
        return groupService.findGroups(pageHelper, groupModel);
    }

    @RequestMapping(value = "/findAllGroupNames.htm")
    @ResponseBody
    public List<Map<String, String>> findAllGroupNames() {
        return groupService.findGroupNamesByCurrentMemberId(SessionUtil.getUserInfo().getMember().getId());
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
        int status = Constants.FAIL;
        if (groupModel.getId() != null) {
            // 更新操作
            if (!groupService.isSameGroupCode(groupModel.getId(), groupModel.getGroupCode())) {
                json.setErrorNo(ResultCodeConstants.RESULT_GROUP_CODE_CANNOT_BE_MODIFIED);
                json.setErrorInfo("组编码不能修改！");
                return json;
            }
            status = groupService.updateGroup(groupModel, SessionUtil.getUserInfo().getMember().getId(), request.getRemoteAddr());
            if (status == Constants.FAIL) {
                json.setStatus(status);
                json.setErrorInfo("更新失败！");
                return json;
            }
        } else {
            if (groupService.isGroupCodeExists(groupModel)) {
                json.setErrorNo(ResultCodeConstants.RESULT_GROUP_IS_EXISTS);
                json.setErrorInfo("组编码已经存在！");
                return json;
            }
            status = groupService.saveGroup(groupModel, SessionUtil.getUserInfo().getMember().getId(), request.getRemoteAddr());
            if (status == Constants.FAIL) {
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
        if (!SessionUtil.getUserInfo().isAdmin()) {
            ajaxJson = new AjaxJson();
            ajaxJson.setErrorNo(ResultCodeConstants.RESULT_PER_ADMIN_HAS_PRIV);
            return ajaxJson;
        }
        return groupService.deleteGroupById(id, SessionUtil.getUserInfo().getMember().getId(), request.getRemoteAddr());
    }

}
