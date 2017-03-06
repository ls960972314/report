package com.sypay.omp.report.VO;

import java.util.Date;

public class ReportConfigVO {
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

	public String getRptCode() {
		return rptCode;
	}

	public void setRptCode(String rptCode) {
		this.rptCode = rptCode;
	}

	public String getRptName() {
		return rptName;
	}

	public void setRptName(String rptName) {
		this.rptName = rptName;
	}

	public String getRptTableName() {
		return rptTableName;
	}

	public void setRptTableName(String rptTableName) {
		this.rptTableName = rptTableName;
	}

	public String getRptColName() {
		return rptColName;
	}

	public void setRptColName(String rptColName) {
		this.rptColName = rptColName;
	}

	public String getRptType() {
		return rptType;
	}

	public void setRptType(String rptType) {
		this.rptType = rptType;
	}

	public String getRptSql() {
		return rptSql;
	}

	public void setRptSql(String rptSql) {
		this.rptSql = rptSql;
	}

	public String getRptStatus() {
		return rptStatus;
	}

	public void setRptStatus(String rptStatus) {
		this.rptStatus = rptStatus;
	}

	public Integer getRptVer() {
		return rptVer;
	}

	public void setRptVer(Integer rptVer) {
		this.rptVer = rptVer;
	}

	public String getUpdateRemark() {
		return updateRemark;
	}

	public void setUpdateRemark(String updateRemark) {
		this.updateRemark = updateRemark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
