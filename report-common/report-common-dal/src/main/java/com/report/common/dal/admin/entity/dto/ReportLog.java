package com.report.common.dal.admin.entity.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;


/**
 * 报表操作查询日志
 * @author lishun
 * @since 2017年4月20日 下午4:27:57
 */
@Data
public class ReportLog implements Serializable {

	private static final long serialVersionUID = 4983452255950323046L;
	/** 主键 */
	private Integer id;
	/** 用户名 */
	private String userName;
	/** 操作动作 */
	private String opeAction;
	/** 执行的SQLID或者toolFlag */
	private String opeId;
	/** 消耗时间 */
	private String wasteTime;
	/** 异常信息 */
	private String exception;
	/** 操作IP */
	private String ip;
	/** 创建时间 */
	private Date createTime;
}
