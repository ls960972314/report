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
import com.report.common.dal.common.utils.VerificationUtil;
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
    public String findMemberList(HttpServletRequest request) {
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
    public DataGrid findMemberList(MemberQueryReq memberQueryReq, PageHelper pageHelper) {
    	log.debug("findMemberList MemberQueryReq[{}],PageHelper[{}]", JSON.toJSONString(memberQueryReq), JSON.toJSONString(pageHelper));
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
        AjaxJson result = new AjaxJson();
        // 参数校验
        if (VerificationUtil.paramIsNull(memberQueryReq, memberQueryReq.getName(), memberQueryReq.getAccNo())) {
            result.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return result;
        }
        
        // 修改用户
        if (memberQueryReq.getId() != null) {
            memberService.updateMember(memberQueryReq, groupCode.trim(), SessionUtil.getUserInfo().getMember().getId());
            return result;
        }
        
        // 新增用户
        // 校验用户是否存在
        if (memberService.isAccNoExists(memberQueryReq.getAccNo())) {
        	result.setErrorNo(ResultCodeConstants.RESULT_USER_ALREADY_EXISTS);
        	return result;
        }
        // 校验用户密码是否为空
        if (StringUtils.isBlank(memberQueryReq.getPassword())) {
            result.setErrorNo(ResultCodeConstants.RESULT_PASSWORD_CAN_NOT_BE_NULL);
            return result;
        }
        memberService.saveMember(memberQueryReq, groupCode.trim(), SessionUtil.getUserInfo().getMember().getId());
        return result;
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
        AjaxJson result = new AjaxJson();
        // 校验参数
        if (id == null) {
        	log.debug("deleteMember id is null");
            result.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return result;
        }
        try {
        	memberService.deleteMemberById(id);
		} catch (Exception e) {
			log.error("deleteMember Exception", e);
			result.setErrorNo(ResultCodeConstants.RESULT_FAIL);
		}
        return result;
    }

    /**
     * 用户密码是否正确
     * @param password
     * @return
     */
    @RequestMapping(value = "/isPasswordRight.htm")
    @ResponseBody
    public AjaxJson isPasswordRight(String password) {
        AjaxJson result = new AjaxJson();
        // 校验参数
        if (StringUtils.isBlank(password)) {
        	log.debug("isPasswordRight password is null");
            result.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return result;
        }
        try {
        	if (memberService.isPasswordRight(SessionUtil.getUserInfo().getMember().getId(), password.trim())) {
                result.setStatus(ResultCodeConstants.RESULT_SUCC);
            } else {
            	log.debug("memberId[{}]用户密码不正确", SessionUtil.getUserInfo().getMember().getId());
                result.setStatus(ResultCodeConstants.RESULT_FAIL);
            }
		} catch (Exception e) {
			log.error("isPasswordRight Exception", e);
			result.setErrorNo(ResultCodeConstants.RESULT_FAIL);
		}
        return result;
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
        AjaxJson result = new AjaxJson();
        if (memberId == null) {
        	log.debug("resetPassword memberId is null");
            result.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return result;
        }
        try {
        	memberService.resetPassword(memberId);
		} catch (Exception e) {
			log.error("resetPassword Exception", e);
			result.setErrorNo(ResultCodeConstants.RESULT_RESET_PASSWORD_FAIL);
		}
        return result;
    }

    /**
     * 账号是否存在
     * @param accNo
     * @return
     */
    @RequestMapping(value = "/isAccNoExists.htm")
    @ResponseBody
    public AjaxJson isAccNoExists(String accNo) {
        AjaxJson result = new AjaxJson();
        // 参数校验
        if (StringUtils.isBlank(accNo)) {
        	log.debug("isAccNoExists accNo is null");
        	result.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
        	return result;
        }
        // 账号存在返回success
        try {
        	if (memberService.isAccNoExists(accNo.trim())) {
                result.setStatus(Constants.SUCCESS);
                return result;
            }
		} catch (Exception e) {
			log.error("isAccNoExists Exception", e);
		}
        result.setStatus(Constants.FAIL);
        return result;
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
        AjaxJson result = new AjaxJson();
        // 参数校验
        if (VerificationUtil.paramIsNull(originPassword, newPassword)) {
        	log.debug("changePassword originPassword or newPassword is null");
        	result.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
        	return result;
        }
        // 原始密码是否正确,正确则修改密码为新密码
        try {
        	if (memberService.isPasswordRight(SessionUtil.getUserInfo().getMember().getId(), originPassword.trim())
        			&& memberService.changePassword(newPassword.trim(), SessionUtil.getUserInfo().getMember().getId())) {
                result.setStatus(Constants.SUCCESS);
                return result;
            }
		} catch (Exception e) {
			log.error("changePassword Exception", e);
		}
        result.setErrorNo(ResultCodeConstants.RESULT_PASSWORD_UNRIGHT);
        return result;
    }
}
