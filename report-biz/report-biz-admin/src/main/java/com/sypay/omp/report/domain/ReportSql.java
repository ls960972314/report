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
 * @2015年4月24日
 */
@Entity
@Table(name="rp_report_sql")
public class ReportSql implements Serializable {
	private static final long serialVersionUID = -851241265421334092L;
	
	private Long sqlId;
	private String baseSql;
	private String baseCountSql;
	private String rptname;
	private String dataBaseSource;

	public ReportSql() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	/*@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="mseq")
	@SequenceGenerator(name="mseq",sequenceName="SEQ_RP_REPORT_SQL",allocationSize=1)*/
	@Column(name="sql_id")
	public Long getSqlId() {
		return this.sqlId;
	}

	public void setSqlId(Long sqlId) {
		this.sqlId = sqlId;
	}

	@Column(name="base_sql")
	public String getBaseSql() {
		return baseSql;
	}


	public void setBaseSql(String baseSql) {
		this.baseSql = baseSql;
	}

	@Column(name="base_count_sql")
	public String getBaseCountSql() {
		return baseCountSql;
	}


	public void setBaseCountSql(String baseCountSql) {
		this.baseCountSql = baseCountSql;
	}
	@Column(name="rptname")
	public void setRptname(String rptname) {
		this.rptname = rptname;
	}


	public String getRptname() {
		return rptname;
	}

	@Column(name="database_source")
	public String getDataBaseSource() {
		return dataBaseSource;
	}

	public void setDataBaseSource(String dataBaseSource) {
		this.dataBaseSource = dataBaseSource;
	}

}