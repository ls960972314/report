package com.report.common.dal.query.entity.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 报表跑批动态配置
 * @author lishun
 *
 */
@Entity
@Table(name="dyna_rpt_config")
public class ReportConfig implements Serializable{

	private static final long serialVersionUID = 1959944044708830273L;

	/**
	 * 主键
	 */
	private String rptCode;
	
	/**
	 * 配置名
	 */
	private String rptName;
	
	/**
	 * 跑批插入的表名
	 */
	private String rptTableName;
	
	/**
	 * 跑批表时间列
	 */
	private String rptColName;
	
	/**
	 * 跑批统计维度
	 */
	private String rptType;
	
	/**
	 * 跑批脚本
	 */
	private String rptSql;
	
	/**
	 * 是否可用
	 */
	private String rptStatus;
	
	/**
	 * 版本号
	 */
	private Integer rptVer;
	
	/**
	 * 是否修改
	 */
	private String updateRemark;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 修改时间
	 */
	private Date updateTime;

	public ReportConfig() {
		
	}
	
	@Id
	@Column(name="rpt_code")
	public String getRptCode() {
		return rptCode;
	}

	public void setRptCode(String rptCode) {
		this.rptCode = rptCode;
	}
	@Column(name="rpt_name")
	public String getRptName() {
		return rptName;
	}

	public void setRptName(String rptName) {
		this.rptName = rptName;
	}
	@Column(name="rpt_tab_name")
	public String getRptTableName() {
		return rptTableName;
	}

	public void setRptTableName(String rptTableName) {
		this.rptTableName = rptTableName;
	}
	@Column(name="rpt_col_name")
	public String getRptColName() {
		return rptColName;
	}

	public void setRptColName(String rptColName) {
		this.rptColName = rptColName;
	}
	@Column(name="rpt_type")
	public String getRptType() {
		return rptType;
	}

	public void setRptType(String rptType) {
		this.rptType = rptType;
	}
	@Column(name="rpt_sql")
	public String getRptSql() {
		return rptSql;
	}

	public void setRptSql(String rptSql) {
		this.rptSql = rptSql;
	}
	@Column(name="rpt_status")
	public String getRptStatus() {
		return rptStatus;
	}

	public void setRptStatus(String rptStatus) {
		this.rptStatus = rptStatus;
	}
	@Column(name="rpt_ver")
	public Integer getRptVer() {
		return rptVer;
	}

	public void setRptVer(Integer rptVer) {
		this.rptVer = rptVer;
	}
	@Column(name="update_remark")
	public String getUpdateRemark() {
		return updateRemark;
	}

	public void setUpdateRemark(String updateRemark) {
		this.updateRemark = updateRemark;
	}
	@Column(name="create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name="update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
