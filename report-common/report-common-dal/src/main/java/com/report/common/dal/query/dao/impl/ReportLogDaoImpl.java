package com.report.common.dal.query.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.report.common.dal.common.BaseDao;
import com.report.common.dal.query.dao.ReportLogDao;
import com.report.facade.entity.dto.ReportLog;

@Repository
public class ReportLogDaoImpl implements ReportLogDao {

	@Resource
    private BaseDao baseDao;

	@Override
	public int saveReportLog(ReportLog reportLog) {
		return (Integer) baseDao.save(reportLog);
	}
	
}
