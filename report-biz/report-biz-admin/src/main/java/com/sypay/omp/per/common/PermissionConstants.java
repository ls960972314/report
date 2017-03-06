package com.sypay.omp.per.common;

public class PermissionConstants {
	
	public static final String ENCODING_UFT8 = "UTF-8";
	public static final String EMPTY_LIST = "[]";
	
	// 系统编号
	public static String SysCode;

	// 不过滤url
	public static String[] notFilterUrls;

	/**
	 * 开关
	 */
	public static final class InitSwitch {
		// 初始化系统角色开关
		public static String INIT_SYSROLE_SWITCH;
		// 初始化用户临时权限
		public static String INIT_MEMBER_TEMP_PRIV;
		
		public static final String OPEN = "open";
		public static final String CLOSE = "close";
	}

	/**
	 * 资源类型
	 * 
	 * @author nieminjie
	 *
	 */
	public static class ResourceType {
		public static final String MENU = "menu";
		public static final String OP = "op";
	}

	// 客户端（手机）会话Cookie
	public static final String SESSION_COOKIE = "sessionCookies";
	
	// 登录会话Cookie
	public static final String LOGIN_COOKIE = "loginCookies";
	
	/**
	 * 登录状态
	 * @author nieminjie
	 *
	 */
	public static final class LoginStatus{
		
		// 成功
		public static final Integer RESULT_SUCC = 1;
		
		// 账户或密码错误
		public static final Integer ACC_PASS_ERROR = 2;
		
		// 账户不存在
		public static final Integer ACC_NOT_EXISTS = 3;
		
		// 验证码错误
		public static final Integer VERIFY_CODE_ERROR = 4;
		
		// 登录失败
		public static final Integer FAIL_LOGIN_ERROR = 5;
	}
	
	// 登录类型
	public static final class LoginType{
		
		// 前端登录
		public static final Integer FRONTEND = 0;
		
		// 后端登录
		public static final Integer BACKEND = 1;
	}
}
