package com.report.web.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.report.biz.admin.service.GroupService;
import com.report.biz.admin.service.MemberService;
import com.report.biz.admin.service.RoleService;
import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.admin.entity.dto.Member;
import com.report.common.dal.admin.util.RoleUtil;
import com.report.common.dal.admin.util.SessionUtil;
import com.report.common.dal.common.utils.VerificationUtil;
import com.report.common.repository.RoleRepository;
import com.report.web.admin.SessionStatus;

import lombok.extern.slf4j.Slf4j;

/**
 * 登陆控制器
 * @author lishun
 * @since 2017年3月24日 上午11:04:42
 */
@Slf4j
@Controller
public class UserLoginController {

    @Resource
    private GroupService groupService;
    @Resource
    private RoleService roleService;
    @Resource
    private RoleRepository roleRepository;
    @Autowired
    private MemberService memberService;

    /**
     * 跳转至登陆页面
     * @return
     */
    @RequestMapping(value = "/toLogin.htm")
    public String toLogin() {
    	Subject subject = SecurityUtils.getSubject();
    	if (subject.isAuthenticated()) {
    		return "redirect:main.htm";
    	}
        return "login";
    }

    /**
     * 登陆
     * @param loginName
     * @param password
     * @param request
     * @return
     */
    @RequestMapping(value = "/doLogin.htm")
    public String doLogin(String username, String password, HttpServletRequest request,
    		HttpServletResponse response) {
        if (VerificationUtil.paramIsNull(username, password)) {
        	log.error("用户名或密码为空");
            request.setAttribute("erroMsg", "用户名或密码不能为空");
            return "login";
        }
        
        Subject subject = SecurityUtils.getSubject();
        
        try {
        	subject.login(new UsernamePasswordToken(username, password));
        } catch (AuthenticationException e) {
        	log.error("doLogin AuthenticationException", e);
        	if (e instanceof UnknownAccountException) {
        		request.setAttribute("erroMsg", "未知账号");
        	} else if (e instanceof IncorrectCredentialsException) {
        		request.setAttribute("erroMsg", "账号或密码输入错误");
        	} else {
        		request.setAttribute("erroMsg", "验证用户失败请重新登陆");
        	}
        	return "login";
        } catch (Exception e) {
        	log.error("doLogin Exception", e);
        	request.setAttribute("erroMsg", "验证用户失败请重新登陆");
        	return "login";
		}
        
        subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.setAttribute(Constants.SESSION_STATUS, new SessionStatus());
        Member member = memberService.getMemberByLoginName(username);

        /* 将登录信息存入session中 */
        String groupCode = groupService.getGroupCodeByMemberId(member.getId());
        List<String> roleCodeList = roleRepository.findRoleCodeByMemberId(member.getId());
        session.setAttribute(Constants.SESSION_LOGIN_INFO, member);
        session.setAttribute(Constants.SESSION_LOGIN_MEMBER_NAME, StringUtils.isBlank(member.getName()) ? member.getAccNo() : member.getName());
        session.setAttribute(Constants.SESSION_LOGIN_MEMBER_ID, member.getId());
        session.setAttribute(Constants.SESSION_LOGIN_MEMBER_GROUP_CODE, groupCode);
        session.setAttribute(Constants.SESSION_LOGIN_MEMBER_ROLE_CODE, roleCodeList);
        String imgName = member.getAccNo();
        if (StringUtils.isBlank(imgName)) {
        	imgName = String.valueOf(Math.random());
        }
        session.setAttribute("mailImgName", imgName);
        session.setAttribute(Constants.SESSION_IS_PER_ADMIN, SessionUtil.isPerAdmin());
        
        return "redirect:main.htm";
    }
    
    /**
     * 跳转至报表页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/main.htm", method = RequestMethod.GET)
    public String main(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
    	if (!subject.isAuthenticated()) {
    		request.setAttribute("erroMsg", "请重新登陆");
    		return "login";
    	}
        return "main";
    }

    /**
     * 用户退出
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constants.SESSION_LOGIN_INFO);
        request.getSession().removeAttribute(Constants.SESSION_LOGIN_MEMBER_NAME);
        request.getSession().removeAttribute(Constants.SESSION_LOGIN_MEMBER_ID);
        request.getSession().removeAttribute(Constants.SESSION_LOGIN_MEMBER_GROUP_CODE);
        request.getSession().removeAttribute(Constants.SESSION_IS_PER_ADMIN);
        return "login";
    }
    
    /**
     * 时间转换
     * @param binder
     */
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, null, true));
    }
}
