package com.sypay.omp.per.common;

public class Constants {

    public static class OpStatus {

        public final static int SUCC = 1;
        public final static int FAIL = 0;
    }

    public static class GroupStatus {

        public final static int VALID = 1;
        public final static int INVALID = 0;
    }

    public static class RoleStatus {

        public final static int VALID = 1;
        public final static int INVALID = 0;
    }

    public static class ResourceStatus {

        public final static int VALID = 1;
        public final static int INVALID = 0;
    }

    public static class MemberStatus {

        public final static int VALID = 1;
        public final static int INVALID = 0;
    }

    // 查询资源类型
    public static class QueryPermissionType {

        public final static int SYS = 1;
        public final static int MEMBER = 2;
    }

    // 资源管理类型 
    public static class ResourceType {

        // module:系统模块 menu:菜单 op：操作 select: 页面下拉页面 button:按钮
        public final static String MODULE = "module";
        public final static String MENU = "menu";
        public final static String OP = "op";
        public final static String SELECT = "select";
        public final static String BUTTON = "button";
    }

    // 1：内部用户 2:外部用户3.临时用户
    public static class MemberCasType {

        public final static int INTERNAL = 1;
        public final static int EXTERNAL = 2;
        public final static int TEMP = 3;
    }

    // 自服务改变组类型
    public static class ChangeGroupType {

        // 转换组 1：转成财务组 2：转成普通组
        public final static Integer TO_FINANCE = 1;
        public final static Integer TO_COMMON = 2;
    }

    // 位于HttpSession中的属性名，该属性用于标识当前用户是否是系统管理员
    public static final String SESSION_ADMIN_FLAG_NAME = "isAdmin";

    // 位于HttpSession中的属性名，该属性用于存储登录用户的信息
    public static final String SESSION_LOGIN_INFO = "loginInfo";
    public static final String SESSION_LOGIN_MEMBER_ID = "loginMemberId";
    public static final String SESSION_LOGIN_MEMBER_NAME = "name";
    public static final String SESSION_LOGIN_MEMBER_GROUP_CODE = "groupCode";
    public static final String SESSION_IS_PER_ADMIN = "isPerAdmin";
    public static final String MENU_LIST = "menuList";
    public static final String REPORT_MENU_LIST = "reportMenuList";
    public static final String HAS_PRIVILEGE = "hasPrevilege"; // 是否有权限显示权限管理按钮
    public static final String SESSION_LOGIN_MEMBER_ROLE_CODE = "roleCode";
    public static final String DEFAULT_PASSWORD_FOR_MEMBER = "123456";


    public static class OperationType {

        /*
         *  1xx: 与人员管理相关
         *  2xx：与组别管理相关
         *  3xx：与资源管理相关
         *  4xx：与角色管理相关
         */

        public static final int EDIT_MEMBER_INFO = 101; // 编辑人员信息
        public static final int NEW_MEMBER_INFO = 102; // 新建人员信息
        public static final int DELETE_MEMBER_PHYSICALLY = 103; // 物理删除人员
        public static final int DELETE_MEMBER_LOGICALLY = 104; // 逻辑删除人员
        public static final int RESET_PASSWORD_FOR_MEMBER = 105; // 重置人员的密码

        public static final int UPDATE_GROUP_INFO = 201; // 更新组别信息
        public static final int NEW_GROUP_INFO = 202; // 新增组别信息
        public static final int DELETE_GROUP_PHYSICALLY = 203; // 物理删除组别信息
        public static final int DELETE_GROUP_LOGICALLY = 204; // 逻辑删除组别信息
        public static final int UPDATE_ASSOCIATION_BETWEEN_GROUP_AND_MEMBER = 205; // 更新组别和人员的关联
        public static final int ADD_ASSOCIATION_BETWEEN_GROUP_AND_MEMBER = 206; // 添加组别和人员的关联
        public static final int REMOVE_ASSOCIATION_BETWEEN_GROUP_AND_ROLE = 207; // 移除组别和角色的关联
        public static final int ADD_ASSOCIATION_BETWEEN_GROUP_AND_ROLE = 208; // 添加组别和角色的关联
    }

    public static class OperationStatus {

        public static final int SUCC = 1;
        public static final int FAIL = 0;
    }

    public static class MenuType {

        public static final String REPORT = "omp";
        public static final String PERMISSION = "per";
    }
}
