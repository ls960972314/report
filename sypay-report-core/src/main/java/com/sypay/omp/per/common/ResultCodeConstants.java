package com.sypay.omp.per.common;

public class ResultCodeConstants {
	// 失败
	public static final int RESULT_FAIL = 0;
	// 成功
	public static final int RESULT_SUCC = 1;
	// 数据不完整
	public static final int RESULT_INCOMPLETE = 2;
	// 无法删除，请检查该角色是否正在使用
	public static final int RESULT_ROLE_IS_IN_USE = 3;
	// 只有权限管理员能够执行此操作！
	public static final int RESULT_PER_ADMIN_HAS_PRIV = 4;
	// 无法删除，请检查该组是否仍被使用
	public static final int RESULT_CHECK_GROUP_IS_IN_USE = 5;
	// 用户已存在
	public static final int RESULT_USER_ALREADY_EXISTS = 6;
	// 密码不能为空
	public static final int RESULT_PASSWORD_CAN_NOT_BE_NULL = 7;
	// 重置密码失败
	public static final int RESULT_RESET_PASSWORD_FAIL = 8;
	// 修改密码失败
	public static final int RESULT_EDIT_PASSWORD_FAIL = 9;
	// 密码不正确
	public static final int RESULT_PASSWORD_UNRIGHT = 10;
	// 资源已存在
	public static final int RESULT_RESOURCE_ALREADY_EXISTS = 11;
	// 无法删除，请检查该资源是否正在被使用
	public static final int RESULT_CHECK_RESOURCE_IS_IN_USE = 12;
	// 请选择资源
	public static final int RESULT_TEMP_RESOURCE_IS_ILLEGAL = 13;
	// 分配临时权限失败
	public static final int RESULT_DISTRI_TEMP_PRIV_FAIL = 14;
	// 删除临时权限失败
	public static final int RESULT_DELETE_TEMP_PRIV_FAIL = 15;
	// 角色编码已存在
	public static final int RESULT_ROLE_CODE_IS_EXISTS = 16;
	// 组编码已存在
	public static final int RESULT_GROUP_IS_EXISTS = 17;
	// 组编码不能被修改
	public static final int RESULT_GROUP_CODE_CANNOT_BE_MODIFIED = 18;
	// 角色编码不能被修改
	public static final int RESULT_ROLE_CODE_CANNOT_BE_MODIFIED = 19;
}
