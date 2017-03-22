package com.sypay.omp.report.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lishun
 *
 * @2015年5月5日
 */
@Entity
@Table(name = "RPTCON")
public class ReportCondition implements Serializable {
    
	private static final long serialVersionUID = -1408519196396021992L;
	
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
    
    public ReportCondition() {

    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    /*@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_RPTCON")
    @SequenceGenerator(name="SEQ_RPTCON",sequenceName="SEQ_RPTCON",allocationSize=1)*/
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    @Column(name = "TOOLFLAG")
    public String getToolFlag() {
        return toolFlag;
    }

    public void setToolFlag(String toolFlag) {
        this.toolFlag = toolFlag;
    }

    @Column(name = "CONWHERE")
    public String getConWhere() {
        return conWhere;
    }

    public void setConWhere(String conWhere) {
        this.conWhere = conWhere;
    }

    @Column(name = "CONTYPE")
    public String getConType() {
        return conType;
    }

    public void setConType(String conType) {
        this.conType = conType;
    }

    @Column(name = "CONNAME")
    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    @Column(name = "CONOPTION")
    public String getConOption() {
        return conOption;
    }

    public void setConOption(String conOption) {
        this.conOption = conOption;
    }

    @Column(name = "CONMUTI")
    public String getConMuti() {
        return conMuti;
    }

    public void setConMuti(String conMuti) {
        this.conMuti = conMuti;
    }

    @Column(name = "ORDER_NUM")
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	@Column(name = "DEFAULT_VALUE")
	public String getConDefaultValue() {
		return conDefaultValue;
	}

	public void setConDefaultValue(String conDefaultValue) {
		this.conDefaultValue = conDefaultValue;
	}

	@Column(name = "CHARTID")
	public Integer getChartId() {
		return chartId;
	}

	public void setChartId(Integer chartId) {
		this.chartId = chartId;
	}

	@Column(name = "ROW_NUM")
	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	@Column(name = "database_source")
	public String getDataBaseSource() {
		return dataBaseSource;
	}

	public void setDataBaseSource(String dataBaseSource) {
		this.dataBaseSource = dataBaseSource;
	}
	
	
    
    

}