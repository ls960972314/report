package com.sypay.omp.per.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sypay.omp.per.common.Constants;
import com.sypay.omp.per.dao.RoleDao;
import com.sypay.omp.per.domain.Member;
import com.sypay.omp.per.service.GroupService;
import com.sypay.omp.per.service.MemberService;
import com.sypay.omp.per.service.RoleService;
import com.sypay.omp.per.util.RoleUtil;
import com.sypay.omp.per.util.SessionUtil;
import com.sypay.omp.report.util.JDBCUtils;
import com.sypay.omp.report.util.MD5;

/**
 * 登录成功入口
 * @author 887961 
 * @Date 2016年10月26日 下午2:42:04
 */
@Controller
public class UserLoginController {

    private final Log logger = LogFactory.getLog(UserLoginController.class);
    @Resource
    private GroupService groupService;
    @Resource
    private RoleService roleService;
    @Resource
    private RoleDao roleDao;
    @Autowired
    private MemberService memberService;
   
    
    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));

        binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, null, true));
    }

    @RequestMapping(value = "/main.htm", method = RequestMethod.GET)
    public String main(HttpServletRequest request) {
        Long memberId = SessionUtil.getCurrentMemberId();
        
        if (memberId == null) {
            request.setAttribute("erroMsg", "当前用户没有权限，需要登录");
            logger.debug("当前用户没有权限，需要登录");
            return "login";
        }

        return "main";
    }

    @RequestMapping(value = "/doLogin.htm")
    public String doLogin(String loginName, String password, HttpServletRequest request) {
		
        if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {
            request.setAttribute("erroMsg", "用户名或密码不能为空");
            return "login";
        }
        Member member = memberService.getMemberByLoginName(loginName);
        
		/* 验证超级管理员 */
        if (member!= null && loginName.equals("perAdmin") && !MD5.getMD5String(password).equals(member.getPassword())) {
        	request.setAttribute("erroMsg", "用户名或密码有误");
            request.setAttribute("loginName", loginName);
            return "login";
        }
		
        if (member == null || !MD5.getMD5String(password).equals(member.getPassword())) {
            request.setAttribute("erroMsg", "用户名或密码有误");
            request.setAttribute("loginName", loginName);
            return "login";
        }

        /* 将登录信息存入session中 */
        String groupCode = groupService.getGroupCodeByMemberId(member.getId());
        List<String> roleCodeList = roleDao.findRoleCodeByMemberId(member.getId());
        request.getSession().setAttribute(Constants.SESSION_LOGIN_INFO, member);
        request.getSession().setAttribute(Constants.SESSION_LOGIN_MEMBER_NAME, StringUtils.isBlank(member.getName()) ? member.getAccNo() : member.getName());
        request.getSession().setAttribute(Constants.SESSION_LOGIN_MEMBER_ID, member.getId());
        request.getSession().setAttribute(Constants.SESSION_LOGIN_MEMBER_GROUP_CODE, groupCode);
        request.getSession().setAttribute(Constants.SESSION_LOGIN_MEMBER_ROLE_CODE, roleCodeList);
        String imgName = member.getAccNo();
        if (StringUtils.isBlank(imgName)) {
        	imgName = String.valueOf(Math.random());
        }
        request.getSession().setAttribute("mailImgName", imgName);
        request.getSession().setAttribute(Constants.SESSION_IS_PER_ADMIN, SessionUtil.isPerAdmin());
        
        /* 将登录信息存入redis中 */
        return "redirect:main.htm";
    }
    

    @RequestMapping(value = "/toLogin.htm")
    public String toLogin() {

        return "login";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constants.SESSION_LOGIN_INFO);
        request.getSession().removeAttribute(Constants.SESSION_LOGIN_MEMBER_NAME);
        request.getSession().removeAttribute(Constants.SESSION_LOGIN_MEMBER_ID);
        request.getSession().removeAttribute(RoleUtil.SESSION_ROLE);
        request.getSession().removeAttribute(Constants.SESSION_LOGIN_MEMBER_GROUP_CODE);
        request.getSession().removeAttribute(Constants.SESSION_IS_PER_ADMIN);
        request.getSession().removeAttribute(Constants.MENU_LIST);
        request.getSession().removeAttribute(Constants.REPORT_MENU_LIST);
        request.getSession().removeAttribute(Constants.HAS_PRIVILEGE);

//        request.getSession().invalidate();
        return "login";
    }
}
