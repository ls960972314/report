package com.report.biz.admin.service;

import com.report.common.dal.admin.entity.dto.ReportLog;

/**
 * ReportLogService
 * @author lishun
 * @since 2017年4月20日 下午4:44:38
 */
public interface ReportLogService {

	/**
	 * 新增日志
	 * @param reportLog
	 */
	public void addLog(ReportLog reportLog);
}
