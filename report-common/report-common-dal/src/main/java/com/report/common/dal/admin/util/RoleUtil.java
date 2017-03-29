package com.report.common.dal.admin.util;

import java.util.Collections;
import java.util.List;

import org.apache.shiro.session.Session;

import com.report.common.dal.admin.constant.Constants;

public class RoleUtil {

    /**
     * 获取session中的角色编号列表
     * 
     * @return
     */
    public static List<String> getRoleCodes() {
    	Session session = SessionUtil.getHttpSession();
        List<String> roleCodeList = (List<String>) session.getAttribute(Constants.SESSION_LOGIN_MEMBER_ROLE_CODE);
        return (List<String>) (roleCodeList == null || roleCodeList.isEmpty() ? Collections.emptyList() : roleCodeList);
    }
}
