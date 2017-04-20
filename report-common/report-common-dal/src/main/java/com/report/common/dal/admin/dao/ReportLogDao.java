package com.report.common.dal.admin.dao;

import com.report.common.dal.admin.entity.dto.ReportLog;

/**
 * ReportLogDao
 * @author lishun
 * @since 2017年4月20日 下午4:35:35
 */
public interface ReportLogDao {

	/**
	 * 新增报表日志
	 * @param reportLog
	 */
	public void insert(ReportLog reportLog);
}
