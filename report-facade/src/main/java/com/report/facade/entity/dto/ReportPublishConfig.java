package com.report.facade.entity.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 模板实体类
 * @author lishun
 *
 * @2016年3月4日
 */
@Entity
@Table(name="RPTPUBLISHCONFIG")
public class ReportPublishConfig implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6218205910807478374L;
	/**
	 * 主键
	 */
	@Id
    @Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	/*@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_RPTPUBLICCONFIG")
    @SequenceGenerator(name="SEQ_RPTPUBLICCONFIG",sequenceName="SEQ_RPTPUBLICCONFIG",allocationSize=1)*/
	private Integer id;
	
	/**
	 *模板ID
	 */
	@Column(name="MODEL_ID")
	private Long modelId;
	
	/**
	 * 报表FLAG
	 */
	@Column(name="TOOLFLAG")
	private String toolFlag;
	
	/**
	 * 报表名称
	 */
	@Column(name="reportname")
	private String reportName;
	
	/**
	 * 时间维度
	 */
	@Column(name="reporttime")
	private String reportTime;
	
	/**
	 * 报表条件(rptcon)ID
	 */
	@Column(name="RPTCON_ID")
	private String rptConId;
	
	/**
	 * 模板条件名
	 */
	@Column(name="MODELCONNAME")
	private String modelConName;
	
	/**
	 * 条件默认值(无法对应模板条件时)
	 */
	@Column(name="DEFAULT_VALUE")
	private String defaultValue;
	
	
	/**
	 * 数据源SQLID
	 */
	@Column(name="SQL_ID")
	private String sqlId;
	
	/**
	 * 展现时标题
	 */
	@Column(name="TITLE")
	private String rptTitle;
	
	/**
	 * 报表文字说明
	 */
	@Column(name="RPT_COMMENT")
	private String rptComment;
	
	/**
	 * 是否显示图(Y显示 N不显示)
	 */
	@Column(name="CHARTSHOW")
	private String chartShow;
	
	/**
	 * 是否显示表(Y显示 N不显示)
	 */
	@Column(name="TABLESHOW")
	private String tableShow;

	/**
	 * 显示哪张图
	 */
	@Column(name="CHART_ID")
	private Integer chartId;
	
	/**
	 * 显示哪张图
	 */
	@Column(name="CHART_NAME")
	private String chartName;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getToolFlag() {
		return toolFlag;
	}

	public void setToolFlag(String toolFlag) {
		this.toolFlag = toolFlag;
	}

	public String getRptConId() {
		return rptConId;
	}

	public void setRptConId(String rptConId) {
		this.rptConId = rptConId;
	}

	public String getModelConName() {
		return modelConName;
	}

	public void setModelConName(String modelConName) {
		this.modelConName = modelConName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getSqlId() {
		return sqlId;
	}

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	public String getRptComment() {
		return rptComment;
	}

	public void setRptComment(String rptComment) {
		this.rptComment = rptComment;
	}

	public String getChartShow() {
		return chartShow;
	}

	public void setChartShow(String chartShow) {
		this.chartShow = chartShow;
	}

	public String getTableShow() {
		return tableShow;
	}

	public void setTableShow(String tableShow) {
		this.tableShow = tableShow;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public String getRptTitle() {
		return rptTitle;
	}

	public void setRptTitle(String rptTitle) {
		this.rptTitle = rptTitle;
	}

	public Integer getChartId() {
		return chartId;
	}

	public void setChartId(Integer chartId) {
		this.chartId = chartId;
	}

	public String getChartName() {
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}
	
}
