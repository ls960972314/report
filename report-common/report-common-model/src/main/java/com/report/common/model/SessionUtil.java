package com.report.common.model;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.report.common.dal.admin.constant.Constants;

public class SessionUtil {
	
    public static Session getHttpSession() {
    	Session session = SecurityUtils.getSubject().getSession();
        return session;
    }
    
    public static UserInfo getUserInfo() {
    	return (UserInfo) getHttpSession().getAttribute(Constants.SESSION_LOGIN_INFO);
    }
}
