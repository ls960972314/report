package com.report.common.model.query;

import java.io.Serializable;
import java.util.List;

import com.report.common.dal.query.entity.dto.ReportCondition;

/**
 * 报表模板类查询时返回的VO
 * @author 887961
 *
 */
public class ReportModelVO implements Serializable {

	private static final long serialVersionUID = 1235224370766425200L;

	/**
	 * 报表List
	 */
	private List<ModelElements> modelElementsList;
	
	/**
	 * 模板条件
	 */
	private List<ReportCondition> reportConditionList;

	/**
	 * 模板名称(报告名称)
	 */
	private String modelName;
	
	private String modelTitle;

	/**
	 * 收件人地址
	 */
	private String receiveAdd;
	
	public List<ModelElements> getModelElementsList() {
		return modelElementsList;
	}

	public void setModelElementsList(List<ModelElements> modelElementsList) {
		this.modelElementsList = modelElementsList;
	}

	public List<ReportCondition> getReportConditionList() {
		return reportConditionList;
	}

	public void setReportConditionList(List<ReportCondition> reportConditionList) {
		this.reportConditionList = reportConditionList;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	public String getModelTitle() {
		return modelTitle;
	}

	public void setModelTitle(String modelTitle) {
		this.modelTitle = modelTitle;
	}

	public String getReceiveAdd() {
		return receiveAdd;
	}

	public void setReceiveAdd(String receiveAdd) {
		this.receiveAdd = receiveAdd;
	}
	
	
}
