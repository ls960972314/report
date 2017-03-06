package com.sypay.omp.report.domain;

import java.io.Serializable;
import java.util.Date;

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
 */
@Entity
@Table(name="RPTLOG")
public class ReportLog implements Serializable {

	private static final long serialVersionUID = 4983452255950323046L;
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 操作动作
	 */
	private String opeAction;
	/**
	 * 执行的SQLID或者toolFlag
	 */
	private String opeId;
	/**
	 * 消耗时间
	 */
	private String wasteTime;
	/**
	 * 异常信息
	 */
	private String exception;
	/**
	 * 创建时间
	 */
	private Date createTime;
	public ReportLog() {
	}
	
	@Id
    @Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	/*@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_RPTLOG")
    @SequenceGenerator(name="SEQ_RPTLOG",sequenceName="SEQ_RPTLOG",allocationSize=1)*/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name="user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name="ope_action")
	public String getOpeAction() {
		return opeAction;
	}

	public void setOpeAction(String opeAction) {
		this.opeAction = opeAction;
	}
	@Column(name="ope_id")
	public String getOpeId() {
		return opeId;
	}
	
	public void setOpeId(String opeId) {
		this.opeId = opeId;
	}
	@Column(name="waste_time")
	public String getWasteTime() {
		return wasteTime;
	}

	public void setWasteTime(String wasteTime) {
		this.wasteTime = wasteTime;
	}
	@Column(name="exception")
	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}
	@Column(name="create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
