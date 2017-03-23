package com.report.facade.service;

import com.report.facade.entity.DataGrid;
import com.report.facade.entity.PageHelper;
import com.report.facade.entity.dto.ReportSql;
import com.report.facade.entity.query.ReportSqlVO;

public interface ReportSqlService {
	
	public Long saveReportSql(String baseSql, String sqlName, String dataBaseSource);

	public void updateReportSql(ReportSql reportSql);
	
	public ReportSql findReportSqlById(Long sqlId);

    public String getCountSQL(String baseSql);

    /**
     * 查找报表sql列表
     * @param reportSqlVo
     * @param pageHelper
     * @return
     */
	public DataGrid findReportSqlList(ReportSqlVO reportSqlVo,
			PageHelper pageHelper);

	/**
	 * 更新报表SQL
	 * @param reportSql
	 */
	public void updatePublic(ReportSql reportSql);

	/**
	 * 新增SQL
	 * @param reportSql 
	 */
	public void addReportSql(ReportSql reportSql);

}
