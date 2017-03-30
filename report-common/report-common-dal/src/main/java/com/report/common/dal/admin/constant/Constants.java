package com.report.common.dal.admin.constant;

public class Constants {

    public static class OpStatus {

        public final static int SUCC = 1;
        public final static int FAIL = 0;
    }

    public static class ResourceStatus {

        public final static int VALID = 1;
        public final static int INVALID = 0;
    }
    
    public static final String ADMIN_ACCOUNT = "per_admin";
    public static final String SESSION_STATUS = "SESSION_STATUS";
    // 位于HttpSession中的属性名，该属性用于标识当前用户是否是系统管理员
    public static final String SESSION_ADMIN_FLAG_NAME = "isAdmin";

    // 位于HttpSession中的属性名，该属性用于存储登录用户的信息
    public static final String SESSION_LOGIN_INFO = "loginInfo";
    public static final String MENU_LIST = "menuList";
    public static final String REPORT_MENU_LIST = "reportMenuList";
    public static final String HAS_PRIVILEGE = "hasPrevilege"; // 是否有权限显示权限管理按钮
    public static final String DEFAULT_PASSWORD_FOR_MEMBER = "123456";


    public static class MenuType {
        public static final String REPORT = "omp";
        public static final String PERMISSION = "per";
    }
}
