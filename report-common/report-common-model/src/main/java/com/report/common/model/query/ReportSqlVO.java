package com.report.common.model.query;

import java.io.Serializable;

/**
 * 
 * @author lishun
 *
 */
public class ReportSqlVO implements Serializable {
	
	private static final long serialVersionUID = 2400306242755795781L;
	
	private Long sqlId;
	private String baseSql;
	private String baseCountSql;
	private String rptname;
	private String dataBaseSource;

	public Long getSqlId() {
		return this.sqlId;
	}

	public void setSqlId(Long sqlId) {
		this.sqlId = sqlId;
	}

	public String getBaseSql() {
		return baseSql;
	}

	public void setBaseSql(String baseSql) {
		this.baseSql = baseSql;
	}

	public String getBaseCountSql() {
		return baseCountSql;
	}

	public void setBaseCountSql(String baseCountSql) {
		this.baseCountSql = baseCountSql;
	}

	public void setRptname(String rptname) {
		this.rptname = rptname;
	}

	public String getRptname() {
		return rptname;
	}

	public String getDataBaseSource() {
		return dataBaseSource;
	}

	public void setDataBaseSource(String dataBaseSource) {
		this.dataBaseSource = dataBaseSource;
	}
	
}
