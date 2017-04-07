package com.report.web.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.report.biz.admin.service.MemberService;
import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.common.utils.VerificationUtil;
import com.report.common.model.SessionUtil;
import com.report.common.model.UserInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * 登陆Controller
 * @author lishun
 * @since 2017年3月24日 上午11:04:42
 */
@Slf4j
@Controller
public class UserLoginController {

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
    		log.debug("用户已登陆,重定向到首页");
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
    	log.debug("username[{}]用户开始登陆", username);
        if (VerificationUtil.paramIsNull(username, password)) {
        	log.debug("username[{}]用户名或密码为空", username);
            request.setAttribute("erroMsg", "用户名或密码不能为空");
            return "login";
        }
        Subject subject = SecurityUtils.getSubject();
        // 登陆操作
        try {
        	log.debug("username[{}]用户开始登陆", username);
        	subject.login(new UsernamePasswordToken(username, password));
        	log.debug("username[{}]用户登陆成功", username);
        } catch (AuthenticationException e) {
        	log.error("username[{}] doLogin AuthenticationException", username, e);
        	if (e instanceof UnknownAccountException) {
        		request.setAttribute("erroMsg", "未知账号");
        	} else if (e instanceof IncorrectCredentialsException) {
        		request.setAttribute("erroMsg", "账号或密码输入错误");
        	} else {
        		request.setAttribute("erroMsg", "验证用户失败请重新登陆");
        	}
        	return "login";
        } catch (Exception e) {
        	log.error("username[{}] doLogin Exception", username, e);
        	request.setAttribute("erroMsg", "验证用户失败请重新登陆");
        	return "login";
		}
		UserInfo userInfo = memberService.getUserInfo(username);
		log.info("username[{}]登陆成功,查询userInfo结果为[{}]", username, JSON.toJSONString(userInfo));
        // 将登录信息存入shiro session中
		subject.getSession().setAttribute(Constants.SESSION_LOGIN_INFO, userInfo);
		// 管理页面用来展示菜单
		request.getSession().setAttribute("menuList", SessionUtil.getUserInfo().getAdminMenuList());
		// 用户名
		request.getSession().setAttribute("name", SessionUtil.getUserInfo().getMemberName());
        return "redirect:main.htm";
    }
    
    /**
     * 跳转至报表展示页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/main.htm", method = RequestMethod.GET)
    public String main(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
    	if (!subject.isAuthenticated()) {
    		log.debug("用户未登陆或者认证失效,跳转至登陆页面重新登陆");
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
    	log.debug("用户退出");
    	SecurityUtils.getSubject().logout();
        return "login";
    }
    
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, null, true));
    }
}
