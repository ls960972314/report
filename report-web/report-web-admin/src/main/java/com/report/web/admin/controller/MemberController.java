package com.report.web.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.report.biz.admin.service.GroupService;
import com.report.biz.admin.service.MemberService;
import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.admin.entity.dto.Member;
import com.report.common.dal.admin.entity.vo.MemberCriteriaModel;
import com.report.common.dal.admin.util.SessionUtil;
import com.report.common.model.AjaxJson;
import com.report.common.model.ResultCodeConstants;
import com.report.facade.entity.DataGrid;
import com.report.facade.entity.PageHelper;

/**
 * @Title: Controller
 * @Description: 组
 * @date 2014-10-14 14:10:27
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/member")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberService memberService;
    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/member.htm")
    public String findGroupList(HttpServletRequest request) {
        return "member/memberList";
    }

    @RequestMapping(value = "/findMemberListByCriteria.htm")
    @ResponseBody
    public DataGrid findMemberListByCriteria(MemberCriteriaModel memberCriteria, PageHelper pageHelper) {
        memberCriteria.setMemberId(SessionUtil.getCurrentMemberId());
        return memberService.findMemberListByCriteria(memberCriteria, pageHelper);
    }

    @RequestMapping(value = "/addOrUpdateMember.htm")
    @ResponseBody
    public AjaxJson addOrUpdateMember(Member member, String groupCode, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();

        if (StringUtils.isBlank(member.getName()) || StringUtils.isBlank(member.getAccNo())) {
            j.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return j;
        }

        if (member.getId() != null) {
            memberService.updateMember(member, groupCode.trim(), request.getRemoteAddr(), SessionUtil.getCurrentMemberId());

        } else {
            if (memberService.isAccNoExists(member.getAccNo())) {
                j.setErrorNo(ResultCodeConstants.RESULT_USER_ALREADY_EXISTS);
                return j;
            }

            if (StringUtils.isBlank(member.getPassword())) {
                j.setErrorNo(ResultCodeConstants.RESULT_PASSWORD_CAN_NOT_BE_NULL);
                return j;
            }
            memberService.saveMember(member, groupCode.trim(), request.getRemoteAddr(), SessionUtil.getCurrentMemberId());
        }
        return j;
    }

    @RequestMapping(value = "/deleteMember.htm")
    @ResponseBody
    public AjaxJson deleteMember(Long id, HttpServletRequest request) {
        AjaxJson json = new AjaxJson();

        // 只有权限管理员能够执行
        if (!SessionUtil.isPerAdmin()) {
            json.setErrorNo(ResultCodeConstants.RESULT_PER_ADMIN_HAS_PRIV);
            return json;
        }

        if (id == null) {
            json.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return json;
        }

        memberService.deleteMemberById(id, request.getRemoteAddr());
        return json;
    }

    @RequestMapping(value = "/isPasswordRight.htm")
    @ResponseBody
    public AjaxJson isPasswordRight(String password) {
        AjaxJson json = new AjaxJson();
        if (StringUtils.isNotBlank(password)) {
            if (memberService.isPasswordRight(SessionUtil.getCurrentMemberId(), password.trim())) {
                json.setStatus(Constants.OpStatus.SUCC);
            } else {
                json.setStatus(Constants.OpStatus.FAIL);
            }
        }

        return json;
    }

    @RequestMapping(value = "/resetPassword.htm")
    @ResponseBody
    public AjaxJson resetPassword(Long memberId, HttpServletRequest request) {
        AjaxJson json = new AjaxJson();
        boolean flag = false;
        if (memberId != null) {
            flag = memberService.resetPassword(memberId, request.getRemoteAddr());
        }

        if (!flag) {
            json.setErrorNo(ResultCodeConstants.RESULT_RESET_PASSWORD_FAIL);
        }

        return json;
    }

    @RequestMapping(value = "/isAccNoExists.htm")
    @ResponseBody
    public AjaxJson isAccNoExists(String accNo) {
        AjaxJson json = new AjaxJson();
        if (StringUtils.isNotBlank(accNo)) {
            if (memberService.isAccNoExists(accNo.trim())) {
                json.setStatus(Constants.OpStatus.SUCC);
            } else {
                json.setStatus(Constants.OpStatus.FAIL);
            }
        }
        return json;
    }

    @RequestMapping(value = "/changePassword.htm")
    @ResponseBody
    public AjaxJson changePassword(String originPassword, String newPassword, HttpServletRequest request) {
        AjaxJson json = new AjaxJson();
        if (StringUtils.isNotBlank(originPassword) && StringUtils.isNotBlank(newPassword)) {
            if (memberService.isPasswordRight(SessionUtil.getCurrentMemberId(), originPassword.trim())) {
                if (memberService.changePassword(newPassword.trim(), SessionUtil.getCurrentMemberId(), request.getRemoteAddr())) {
                    json.setStatus(Constants.OpStatus.SUCC);
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
