package com.sypay.omp.per.util;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sypay.omp.per.common.Constants;

public class RoleUtil {

    private static final Logger logger = LoggerFactory.getLogger(RoleUtil.class);

    // session中的role的key
    public static final String SESSION_ROLE = "sessionRole";

    // 获取系统角色url
    private static String getRolesUrl;

    // 操作会员角色url
    private static String operMemberRoleUrl;

    // 系统角色资源映射缓存
    //    private static final Map<String, RoleCell> rolesCache = new ConcurrentHashMap<String, RoleCell>();

    //更换组url
    private static String changeGroupUrl = "";

    /**
     * 获取session中的角色编号列表
     * 
     * @return
     */
    public static List<String> getRoleCodes() {

        HttpSession session = SessionUtil.getHttpSession();

        List<String> roleCodeList = (List<String>) session.getAttribute(Constants.SESSION_LOGIN_MEMBER_ROLE_CODE);

        return (List<String>) (roleCodeList == null || roleCodeList.isEmpty() ? Collections.emptyList() : roleCodeList);
    }

    public static String getGetRolesUrl() {
        return getRolesUrl;
    }

    public static void setGetRolesUrl(String getRolesUrl) {
        RoleUtil.getRolesUrl = getRolesUrl;
    }

    public static String getOperMemberRoleUrl() {
        return operMemberRoleUrl;
    }

    public static void setOperMemberRoleUrl(String operMemberRoleUrl) {
        RoleUtil.operMemberRoleUrl = operMemberRoleUrl;
    }

    public static String getChangeGroupUrl() {
        return changeGroupUrl;
    }

    public static void setChangeGroupUrl(String changeGroupUrl) {
        RoleUtil.changeGroupUrl = changeGroupUrl;
    }

}
