package com.report.common.dal.admin.util;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.admin.entity.dto.Member;

public class SessionUtil {
	
    public static HttpSession getHttpSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(false);
    }

    public static boolean isPerAdmin() {
        return RoleUtil.getRoleCodes().contains("per_admin");
    }

    public static Member getLoginInfo() {
        if(getHttpSession() == null)
            return null;
        Object result = getHttpSession().getAttribute(Constants.SESSION_LOGIN_INFO);
        return result == null? null:(Member) result; 
    }

    public static Long getCurrentMemberId() {
        if(getHttpSession() == null)
            return null;
        Object result = getHttpSession().getAttribute(Constants.SESSION_LOGIN_MEMBER_ID);
        return result == null? null:(Long)result; 
    }
}
