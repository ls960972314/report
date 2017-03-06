package com.sypay.omp.report.VO;

public class ReturnCondition {

	private Integer Id;
	
	private String toolFlag;
	
	private String conWhere;
	
	private String conType;
	
	private String conName;
	
	private String conOption;
	
	private String conMuti;

	private Integer orderNum;
	
	private String conDefaultValue;
	
	private Integer chartId;
	
	private Integer rowNum;
	
    private String dataBaseSource;
	/**
	 * 比Condition多出的两列用来判断是否是公共报表中各自的条件
	 */
	private String display;
	
	private String value;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getToolFlag() {
		return toolFlag;
	}

	public void setToolFlag(String toolFlag) {
		this.toolFlag = toolFlag;
	}

	public String getConWhere() {
		return conWhere;
	}

	public void setConWhere(String conWhere) {
		this.conWhere = conWhere;
	}

	public String getConType() {
		return conType;
	}

	public void setConType(String conType) {
		this.conType = conType;
	}

	public String getConName() {
		return conName;
	}

	public void setConName(String conName) {
		this.conName = conName;
	}

	public String getConOption() {
		return conOption;
	}

	public void setConOption(String conOption) {
		this.conOption = conOption;
	}

	public String getConMuti() {
		return conMuti;
	}

	public void setConMuti(String conMuti) {
		this.conMuti = conMuti;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getConDefaultValue() {
		return conDefaultValue;
	}

	public void setConDefaultValue(String conDefaultValue) {
		this.conDefaultValue = conDefaultValue;
	}

	public Integer getChartId() {
		return chartId;
	}

	public void setChartId(Integer chartId) {
		this.chartId = chartId;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDataBaseSource() {
		return dataBaseSource;
	}

	public void setDataBaseSource(String dataBaseSource) {
		this.dataBaseSource = dataBaseSource;
	}

	
}
