package com.report.common.model;

import lombok.Data;

/**
 * 用户管理 请求参数
 * @author lishun
 * @since 2017年4月7日 上午10:50:02
 */
@Data
public class MemberQueryReq {
	/** 登陆账号 */
    private String accNo;
    /** 用户名 */
    private String name;
    /** 用户ID */
    private Long id;
    /** 用户密码 */
    private String password;
    /** 用户状态 */
    private Integer status;
}
