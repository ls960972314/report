package com.report.facade.entity.vo;

/**
 * 自动化报表查询条件
 * @author lishun
 *
 */
public class Condition {

    private String name;
    private String value;
    private String type;
    private String where;
    private String conValue;
    private String conDefaultValue;
    private String option;
    private Integer orderNum;
    private Integer rowNum;
    private String toolFlag;
    private String dataBaseSource;
    
    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getConValue() {
        return conValue;
    }

    public void setConValue(String conValue) {
        this.conValue = conValue;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public String getToolFlag() {
		return toolFlag;
	}

	public void setToolFlag(String toolFlag) {
		this.toolFlag = toolFlag;
	}

	public String getDataBaseSource() {
		return dataBaseSource;
	}

	public void setDataBaseSource(String dataBaseSource) {
		this.dataBaseSource = dataBaseSource;
	}
	
}
