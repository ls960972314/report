package com.report.facade.entity.vo;

/**
 * 
 * @author lishun
 *
 * @2015年5月5日
 */
public class ReportElement {
	
    private String saveReportFlag;
    private String saveReportTitle;
    private String saveReportCColumn;
    private String saveReportEColumn;
    private String saveReportSqlId;
    private String saveReportToolGather;
    private String saveReportFormatDatas;
    
    private String saveReportCondition;

    private boolean saveChartFlag;
    
    private String saveReportChart;
    
//    private String saveReportChartOption;
//    private String saveReportColumnVsLegend;
    // 0815新增
    private Integer staticRowNum;
    private String staticSql;
    private String staticDataBaseSource;
    private String staticCcolumn;
    
    public boolean getSaveChartFlag() {
        return saveChartFlag;
    }

    public void setSaveChartFlag(boolean saveChartFlag) {
        this.saveChartFlag = saveChartFlag;
    }
    
    public String getSaveReportFlag() {
        return saveReportFlag;
    }

    public void setSaveReportFlag(String saveReportFlag) {
        this.saveReportFlag = saveReportFlag;
    }

    public String getSaveReportTitle() {
        return saveReportTitle;
    }

    public void setSaveReportTitle(String saveReportTitle) {
        this.saveReportTitle = saveReportTitle;
    }

    public String getSaveReportCColumn() {
        return saveReportCColumn;
    }

    public void setSaveReportCColumn(String saveReportCColumn) {
        this.saveReportCColumn = saveReportCColumn;
    }

    public String getSaveReportEColumn() {
        return saveReportEColumn;
    }

    public void setSaveReportEColumn(String saveReportEColumn) {
        this.saveReportEColumn = saveReportEColumn;
    }

    public String getSaveReportSqlId() {
        return saveReportSqlId;
    }

    public void setSaveReportSqlId(String saveReportSqlId) {
        this.saveReportSqlId = saveReportSqlId;
    }



    public String getSaveReportCondition() {
        return saveReportCondition;
    }

    public void setSaveReportCondition(String saveReportCondition) {
        this.saveReportCondition = saveReportCondition;
    }

//    public String getSaveReportChartOption() {
//        return saveReportChartOption;
//    }
//
//    public void setSaveReportChartOption(String saveReportChartOption) {
//        this.saveReportChartOption = saveReportChartOption;
//    }
//
//    public String getSaveReportColumnVsLegend() {
//        return saveReportColumnVsLegend;
//    }
//
//    public void setSaveReportColumnVsLegend(String saveReportColumnVsLegend) {
//        this.saveReportColumnVsLegend = saveReportColumnVsLegend;
//    }

	public String getSaveReportToolGather() {
		return saveReportToolGather;
	}

	public void setSaveReportToolGather(String saveReportToolGather) {
		this.saveReportToolGather = saveReportToolGather;
	}

	public String getSaveReportFormatDatas() {
		return saveReportFormatDatas;
	}

	public void setSaveReportFormatDatas(String saveReportFormatDatas) {
		this.saveReportFormatDatas = saveReportFormatDatas;
	}

	public String getSaveReportChart() {
		return saveReportChart;
	}

	public void setSaveReportChart(String saveReportChart) {
		this.saveReportChart = saveReportChart;
	}

	public Integer getStaticRowNum() {
		return staticRowNum;
	}

	public void setStaticRowNum(Integer staticRowNum) {
		this.staticRowNum = staticRowNum;
	}

	public String getStaticSql() {
		return staticSql;
	}

	public void setStaticSql(String staticSql) {
		this.staticSql = staticSql;
	}

	public String getStaticCcolumn() {
		return staticCcolumn;
	}

	public void setStaticCcolumn(String staticCcolumn) {
		this.staticCcolumn = staticCcolumn;
	}

	public String getStaticDataBaseSource() {
		return staticDataBaseSource;
	}

	public void setStaticDataBaseSource(String staticDataBaseSource) {
		this.staticDataBaseSource = staticDataBaseSource;
	}
}
