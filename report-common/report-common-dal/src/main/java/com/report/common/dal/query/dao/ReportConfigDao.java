package com.report.common.dal.query.dao;

import java.util.List;

import com.report.common.dal.query.entity.dto.ReportConfig;

/**
 * 
 * @author lishun
 *
 */
public interface ReportConfigDao {

	public boolean deleteByReportCode(String rptCode);

	/**
	 * 根据主键查找
	 * @param rptCode
	 * @return
	 */
	public List<ReportConfig> findReportConfigList(String rptCode);
}
