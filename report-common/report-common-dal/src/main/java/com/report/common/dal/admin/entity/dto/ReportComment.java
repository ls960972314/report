package com.report.common.dal.admin.entity.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 文本展示表
 * @author lishun
 * @since 2017年4月15日 下午5:09:32
 */
@Data
public class ReportComment implements Serializable {

	private static final long serialVersionUID = 8268199255801638932L;
	/** 主键 */
	private Long id;
	/** 报表标志 */
	private String toolFlag;
	/** 展示内容 */
	private String comment;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
}
