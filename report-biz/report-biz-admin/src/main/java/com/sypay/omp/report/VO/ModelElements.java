package com.sypay.omp.report.VO;

import java.util.List;

import com.sypay.omp.report.domain.ReportChart;
import com.sypay.omp.report.domain.ReportCondition;
import com.sypay.omp.report.domain.ReportPublic;

/**
 * 一张报表所包含的所有元素
 * @author lishun
 *
 */
public class ModelElements {

	/**
	 * 报表公共信息
	 */
	private ReportPublic reportPublic;
	/**
	 * 报表私有条件
	 */
	private List<ReportCondition> reportConditionList;
	/**
	 * 报表图形
	 */
	private ReportChart reportChart;
	/**
	 * 数据源qid
	 */
	private Long qid;
	/**
	 * 是否展示图
	 */
	private boolean chartShow;
	/**
	 * 是否展示表格
	 */
	private boolean tableShow;
	/**
	 * 报表名称
	 */
	private String rptTitle;
	/**
	 * 报表名称
	 */
	private String rptComment;
	/**
	 * 时间维度
	 */
	private String timeDimension;
	/**
	 * 是否只有开始时间
	 * Y:是 N:否
	 */
	private String beginTimeOnly;
	/**
	 * 是否只有结束时间
	 * Y:是 N:否
	 */
	private String endTimeOnly;
	
	public ReportPublic getReportPublic() {
		return reportPublic;
	}

	public void setReportPublic(ReportPublic reportPublic) {
		this.reportPublic = reportPublic;
	}

	public List<ReportCondition> getReportConditionList() {
		return reportConditionList;
	}

	public void setReportConditionList(List<ReportCondition> reportConditionList) {
		this.reportConditionList = reportConditionList;
	}

	public ReportChart getReportChart() {
		return reportChart;
	}

	public void setReportChart(ReportChart reportChart) {
		this.reportChart = reportChart;
	}

	public Long getQid() {
		return qid;
	}

	public void setQid(Long qid) {
		this.qid = qid;
	}

	public boolean isChartShow() {
		return chartShow;
	}

	public void setChartShow(boolean chartShow) {
		this.chartShow = chartShow;
	}

	public boolean isTableShow() {
		return tableShow;
	}

	public void setTableShow(boolean tableShow) {
		this.tableShow = tableShow;
	}

	public String getRptComment() {
		return rptComment;
	}

	public void setRptComment(String rptComment) {
		this.rptComment = rptComment;
	}

	public String getRptTitle() {
		return rptTitle;
	}

	public void setRptTitle(String rptTitle) {
		this.rptTitle = rptTitle;
	}

	public String getTimeDimension() {
		return timeDimension;
	}

	public void setTimeDimension(String timeDimension) {
		this.timeDimension = timeDimension;
	}

	public String getBeginTimeOnly() {
		return beginTimeOnly;
	}

	public void setBeginTimeOnly(String beginTimeOnly) {
		this.beginTimeOnly = beginTimeOnly;
	}

	public String getEndTimeOnly() {
		return endTimeOnly;
	}

	public void setEndTimeOnly(String endTimeOnly) {
		this.endTimeOnly = endTimeOnly;
	}
}
