package com.report.facade.entity.query;

/**
 * 新增模板时用来转换页面传来的报表元素
 * @author 887961
 *
 */
public class ReportPublishVO {

	/**
	 * 页面报表id
	 */
	private String id;
	
	/**
	 * 报表标志
	 */
	private String toolFlag;
	
	/**
	 * 批量报表条件（D类报表）
	 */
	private String conFlag;
	
	/**
	 * 报表名
	 */
	private String reportName;
	
	/**
	 * 报表时间维度 日：d 周：w 月：m
	 */
	private String rptTime;
	
	/**
	 * 报告标题
	 */
	private String rptTitle;
	/**
	 * 报告内容
	 */
	private String rptContent;
	
	/**
	 * 是否显示表格
	 */
	private String tableShow;
	
	/**
	 * 是否显示图形
	 */
	private String chartShow;
	
	/**
	 * 显示哪张图
	 */
	private Integer chartId;
	
	/**
	 * 显示哪张图
	 */
	private String chartName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToolFlag() {
		return toolFlag;
	}

	public void setToolFlag(String toolFlag) {
		this.toolFlag = toolFlag;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getRptTime() {
		return rptTime;
	}

	public void setRptTime(String rptTime) {
		this.rptTime = rptTime;
	}

	public String getRptContent() {
		return rptContent;
	}

	public void setRptContent(String rptContent) {
		this.rptContent = rptContent;
	}

	public String getTableShow() {
		return tableShow;
	}

	public void setTableShow(String tableShow) {
		this.tableShow = tableShow;
	}

	public String getChartShow() {
		return chartShow;
	}

	public void setChartShow(String chartShow) {
		this.chartShow = chartShow;
	}

	public String getRptTitle() {
		return rptTitle;
	}

	public void setRptTitle(String rptTitle) {
		this.rptTitle = rptTitle;
	}

	public String getConFlag() {
		return conFlag;
	}

	public void setConFlag(String conFlag) {
		this.conFlag = conFlag;
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
