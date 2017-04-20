package com.report.biz.admin.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.report.biz.admin.service.ReportLogService;
import com.report.common.dal.admin.dao.ReportLogDao;
import com.report.common.dal.admin.entity.dto.ReportLog;

/**
 * ReportLogServiceImpl
 * @author lishun
 * @since 2017年4月20日 下午4:44:54
 */
@Service
public class ReportLogServiceImpl implements ReportLogService {

	@Resource
	private ReportLogDao reportLogDao;
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void addLog(ReportLog reportLog) {
		reportLogDao.insert(reportLog);
	}

}
