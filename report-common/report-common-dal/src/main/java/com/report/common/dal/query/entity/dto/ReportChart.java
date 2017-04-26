package com.report.common.dal.query.entity.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;


/**
 * 报表图形
 * @author lishun
 *
 * @2015年4月24日
 */
@Data
public class ReportChart implements Serializable {
	
	private static final long serialVersionUID = 1294621282765910837L;
	
	/** 主键 */
	private Integer id;
	/** 报表标志 */
	private String toolFlag;
	/** 图形名称 */
	private String chartName;
	/** 图形类型 */
	private String chartType;
	/** legend数据列 */
	private String dataVsLe;
	/** x轴数据列 */
	private String dataVsX;
	/** 图形option */
	private String chartOption;
	/** 展示数据数量 */
	private Integer showRowNum;
	/** 图形排序 */
	private Integer chartOrder;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
}
