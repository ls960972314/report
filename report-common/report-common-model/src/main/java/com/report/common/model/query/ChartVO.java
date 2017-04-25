package com.report.common.model.query;

import java.io.Serializable;

public class ChartVO implements Serializable {

	private static final long serialVersionUID = -1141034211714835970L;

	private Integer id;
	
	private String toolFlag;
	
	private String dataVsX;
	
	private String dataVsLe;
	
	private String chartOption;
	
	private Integer chartOrder;
	
	private String chartName;
	
	private String chartType;
	
	private Integer showRowNum;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getToolFlag() {
		return toolFlag;
	}

	public void setToolFlag(String toolFlag) {
		this.toolFlag = toolFlag;
	}

	public String getDataVsX() {
		return dataVsX;
	}

	public void setDataVsX(String dataVsX) {
		this.dataVsX = dataVsX;
	}

	public String getDataVsLe() {
		return dataVsLe;
	}

	public void setDataVsLe(String dataVsLe) {
		this.dataVsLe = dataVsLe;
	}

	public String getChartOption() {
		return chartOption;
	}

	public void setChartOption(String chartOption) {
		this.chartOption = chartOption;
	}

	public Integer getChartOrder() {
		return chartOrder;
	}

	public void setChartOrder(Integer chartOrder) {
		this.chartOrder = chartOrder;
	}

	public String getChartName() {
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public Integer getShowRowNum() {
		return showRowNum;
	}

	public void setShowRowNum(Integer showRowNum) {
		this.showRowNum = showRowNum;
	}
	
}
