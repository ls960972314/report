package com.report.web.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.report.biz.admin.service.MemberService;
import com.report.common.dal.admin.constant.Constants;
import com.report.common.model.AjaxJson;
import com.report.common.model.MemberQueryReq;
import com.report.common.model.ResultCodeConstants;
import com.report.common.model.SessionUtil;
import com.report.facade.entity.DataGrid;
import com.report.facade.entity.PageHelper;

import lombok.extern.slf4j.Slf4j;


/**
 * 后台管理  用户管理Controller
 * @author lishun
 * @since 2017年4月6日 下午5:28:31
 */
@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 跳转至用户管理页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/member.htm")
    public String findGroupList(HttpServletRequest request) {
        return "member/memberList";
    }

    /**
     * 查询用户列表
     * @param memberQueryReq
     * @param pageHelper
     * @return
     */
    @RequestMapping(value = "/findMemberList.htm")
    @ResponseBody
    public DataGrid findMemberListByCriteria(MemberQueryReq memberQueryReq, PageHelper pageHelper) {
    	log.debug("findMemberListByCriteria MemberQueryReq[{}],PageHelper[{}]", JSON.toJSONString(memberQueryReq), JSON.toJSONString(pageHelper));
        return memberService.findMemberList(memberQueryReq, pageHelper);
    }

    /**
     * 新增/修改用户
     * @param member
     * @param groupCode
     * @param request
     * @return
     */
    @RequestMapping(value = "/addOrUpdateMember.htm")
    @ResponseBody
    public AjaxJson addOrUpdateMember(MemberQueryReq memberQueryReq, String groupCode, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (StringUtils.isBlank(memberQueryReq.getName()) || StringUtils.isBlank(memberQueryReq.getAccNo())) {
            j.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return j;
        }

        if (memberQueryReq.getId() != null) {
            memberService.updateMember(memberQueryReq, groupCode.trim(), SessionUtil.getUserInfo().getMember().getId());
            return j;
        }
        
        if (memberService.isAccNoExists(memberQueryReq.getAccNo())) {
            j.setErrorNo(ResultCodeConstants.RESULT_USER_ALREADY_EXISTS);
            return j;
        }

        if (StringUtils.isBlank(memberQueryReq.getPassword())) {
            j.setErrorNo(ResultCodeConstants.RESULT_PASSWORD_CAN_NOT_BE_NULL);
            return j;
        }
        memberService.saveMember(memberQueryReq, groupCode.trim(), SessionUtil.getUserInfo().getMember().getId());
        return j;
    }

    /**
     * 删除用户
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleteMember.htm")
    @ResponseBody
    public AjaxJson deleteMember(Long id, HttpServletRequest request) {
        AjaxJson json = new AjaxJson();

        // 只有权限管理员能够执行
        if (!SessionUtil.getUserInfo().isAdmin()) {
            json.setErrorNo(ResultCodeConstants.RESULT_PER_ADMIN_HAS_PRIV);
            return json;
        }

        if (id == null) {
            json.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return json;
        }

        memberService.deleteMemberById(id);
        return json;
    }

    /**
     * 用户密码是否正确
     * @param password
     * @return
     */
    @RequestMapping(value = "/isPasswordRight.htm")
    @ResponseBody
    public AjaxJson isPasswordRight(String password) {
        AjaxJson json = new AjaxJson();
        if (StringUtils.isNotBlank(password)) {
            if (memberService.isPasswordRight(SessionUtil.getUserInfo().getMember().getId(), password.trim())) {
                json.setStatus(1);
            } else {
                json.setStatus(0);
            }
        }

        return json;
    }

    /**
     * 重置密码
     * @param memberId
     * @param request
     * @return
     */
    @RequestMapping(value = "/resetPassword.htm")
    @ResponseBody
    public AjaxJson resetPassword(Long memberId, HttpServletRequest request) {
        AjaxJson json = new AjaxJson();
        boolean flag = false;
        if (memberId != null) {
            flag = memberService.resetPassword(memberId);
        }

        if (!flag) {
            json.setErrorNo(ResultCodeConstants.RESULT_RESET_PASSWORD_FAIL);
        }

        return json;
    }

    /**
     * 账号是否存在
     * @param accNo
     * @return
     */
    @RequestMapping(value = "/isAccNoExists.htm")
    @ResponseBody
    public AjaxJson isAccNoExists(String accNo) {
        AjaxJson json = new AjaxJson();
        if (StringUtils.isNotBlank(accNo)) {
            if (memberService.isAccNoExists(accNo.trim())) {
                json.setStatus(Constants.SUCCESS);
            } else {
                json.setStatus(Constants.FAIL);
            }
        }
        return json;
    }

    /**
     * 修改密码
     * @param originPassword
     * @param newPassword
     * @param request
     * @return
     */
    @RequestMapping(value = "/changePassword.htm")
    @ResponseBody
    public AjaxJson changePassword(String originPassword, String newPassword, HttpServletRequest request) {
        AjaxJson json = new AjaxJson();
        if (StringUtils.isNotBlank(originPassword) && StringUtils.isNotBlank(newPassword)) {
            if (memberService.isPasswordRight(SessionUtil.getUserInfo().getMember().getId(), originPassword.trim())) {
                if (memberService.changePassword(newPassword.trim(), SessionUtil.getUserInfo().getMember().getId())) {
                    json.setStatus(Constants.SUCCESS);
                } else {
                    json.setErrorNo(ResultCodeConstants.RESULT_EDIT_PASSWORD_FAIL);
                }
            } else {
                json.setErrorNo(ResultCodeConstants.RESULT_PASSWORD_UNRIGHT);
            }
        }
        return json;
    }
}
