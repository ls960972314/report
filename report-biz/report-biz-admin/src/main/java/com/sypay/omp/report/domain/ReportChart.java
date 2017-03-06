package com.sypay.omp.report.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 
 * @author lishun
 *
 * @2015年4月24日
 */
@Entity
@Table(name="RPTCHART")
public class ReportChart implements Serializable {
	private static final long serialVersionUID = 1294621282765910837L;
	
	private Integer id;
	private String toolFlag;
	private String dataVsX;
	private String dataVsLe;
	private String chartOption;
	private Integer chartOrder;
	private String chartName;
	private String chartType;
	private Integer showRowNum;

	public ReportChart() {
	}
	
	@Id
    @Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	/*@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_RPTCHART")
    @SequenceGenerator(name="SEQ_RPTCHART",sequenceName="SEQ_RPTCHART",allocationSize=1)*/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name="TOOLFLAG")
    public String getToolFlag() {
        return toolFlag;
    }

    public void setToolFlag(String toolFlag) {
        this.toolFlag = toolFlag;
    }
    @Column(name="DATAVSLE")
    public String getDataVsLe() {
        return dataVsLe;
    }

    public void setDataVsLe(String dataVsLe) {
        this.dataVsLe = dataVsLe;
    }
    @Column(name="CHARTOPTION")
    public String getChartOption() {
        return chartOption;
    }

    public void setChartOption(String chartOption) {
        this.chartOption = chartOption;
    }

    @Column(name="DATAVSX")
	public String getDataVsX() {
		return dataVsX;
	}

	public void setDataVsX(String dataVsX) {
		this.dataVsX = dataVsX;
	}

	@Column(name="order_num")
	public Integer getChartOrder() {
		return chartOrder;
	}

	public void setChartOrder(Integer chartOrder) {
		this.chartOrder = chartOrder;
	}

	@Column(name="name")
	public String getChartName() {
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}
	
	@Column(name="chartType")
	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	@Column(name="show_rownum")
	public Integer getShowRowNum() {
		return showRowNum;
	}

	public void setShowRowNum(Integer showRowNum) {
		this.showRowNum = showRowNum;
	}
    
}
