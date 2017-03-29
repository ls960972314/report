package com.report.common.dal.admin.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.admin.entity.dto.Member;

public class SessionUtil {
	
    public static Session getHttpSession() {
    	Session session = SecurityUtils.getSubject().getSession();
        return session;
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
