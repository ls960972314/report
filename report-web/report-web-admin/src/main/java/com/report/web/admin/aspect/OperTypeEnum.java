package com.report.web.admin.aspect;

import lombok.Getter;

/**
 * 操作类型枚举类
 * @author lishun
 * @since 2017年4月20日 上午10:00:42
 */
@Getter
public enum OperTypeEnum {

	QUERY("查询", "01"),
	ADD("新增", "02"),
	MODIFY("修改", "03"),
	DELETE("删除", "04")
	;
	private final String code;
	
	private final String value;
	
	OperTypeEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}
}
