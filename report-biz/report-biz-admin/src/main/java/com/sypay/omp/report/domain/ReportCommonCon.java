package com.sypay.omp.report.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 共用报表差异条件
 * @author lishun
 *
 */
@Entity
@Table(name="RPTCMCON")
public class ReportCommonCon implements Serializable {

	private static final long serialVersionUID = -3144383697166932978L;
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 报表标志
	 */
	private String toolFlag;
	/**
	 * 条件标志
	 */
	private String conFlag;
	/**
	 * 匹配sql条件
	 */
	private String conWhere;
	/**
	 * 参数值
	 */
	private String conValue;
	
	public ReportCommonCon() {
	}
	
	@Id
    @Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	/*@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_RPTCMCON")
    @SequenceGenerator(name="SEQ_RPTCMCON",sequenceName="SEQ_RPTCMCON",allocationSize=1)*/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="toolflag")
	public String getToolFlag() {
		return toolFlag;
	}

	public void setToolFlag(String toolFlag) {
		this.toolFlag = toolFlag;
	}
	@Column(name="conflag")
	public String getConFlag() {
		return conFlag;
	}

	public void setConFlag(String conFlag) {
		this.conFlag = conFlag;
	}
	@Column(name="conwhere")
	public String getConWhere() {
		return conWhere;
	}

	public void setConWhere(String conWhere) {
		this.conWhere = conWhere;
	}
	@Column(name="convalue")
	public String getConValue() {
		return conValue;
	}

	public void setConValue(String conValue) {
		this.conValue = conValue;
	}
}
