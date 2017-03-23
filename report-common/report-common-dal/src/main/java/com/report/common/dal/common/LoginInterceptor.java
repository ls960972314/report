package com.report.common.dal.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.admin.constant.Constants.MenuType;
import com.report.common.dal.admin.entity.dto.Member;
import com.report.common.dal.admin.entity.vo.MenuCell;
import com.report.common.dal.admin.util.SessionUtil;

public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    // 失败跳转
    String failTogo;
    
    public void setFailTogo(String failTogo) {
        this.failTogo = failTogo;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getServletPath().contains("/js/") || request.getServletPath().contains("/css/") || request.getServletPath().contains("/images/") || request.getServletPath().contains("/tpl/")) {
            return true;
        }

        logger.info("拦截路径:" + request.getServletPath());

        Member member = SessionUtil.getLoginInfo();
        if (member != null) {
            setBaseData(member, request);
        } else {
            String url = request.getContextPath() + failTogo;
            logger.info("客户绑定为空 跳转路径到URL:{}", url);
            response.sendRedirect(url);
            return false;
        }
        return true;

    }

    private void setBaseData(Member member, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.SESSION_LOGIN_MEMBER_NAME) == null) {
            if (StringUtils.isNotBlank(member.getName())) {
                session.setAttribute(Constants.SESSION_LOGIN_MEMBER_NAME, member.getName());
            } else {
                session.setAttribute(Constants.SESSION_LOGIN_MEMBER_NAME, member.getAccNo());
            }
        }

        if (session.getAttribute(Constants.SESSION_LOGIN_INFO) == null) {
            session.setAttribute(Constants.SESSION_LOGIN_INFO, member);
        }

        if (session.getAttribute(Constants.SESSION_LOGIN_MEMBER_ID) == null) {
            session.setAttribute(Constants.SESSION_LOGIN_MEMBER_ID, member.getId());
        }

        if (session.getAttribute(Constants.MENU_LIST) == null || session.getAttribute(Constants.REPORT_MENU_LIST) == null) {
            // 根据权限显示菜单
            List<MenuCell> menuList = PermissionUtil.getMenuList();
            if(menuList != null && menuList.size() > 0) {
                for(MenuCell m: menuList) {
                    if(MenuType.PERMISSION.equals(m.getMenuType())) {
                        request.getSession().setAttribute(Constants.MENU_LIST, m.getChildren());
                        request.getSession().setAttribute(Constants.HAS_PRIVILEGE, 1);
                    } else if(MenuType.REPORT.equals(m.getMenuType())) { 
                        List<MenuCell> menuCellList = new ArrayList<MenuCell>();
                        menuCellList.add(m);
                        request.getSession().setAttribute(Constants.REPORT_MENU_LIST, menuCellList);
                    }
                }
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
