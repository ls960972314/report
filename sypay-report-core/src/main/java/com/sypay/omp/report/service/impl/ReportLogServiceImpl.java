package com.sypay.omp.report.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sypay.omp.report.dao.BaseDao;
import com.sypay.omp.report.domain.ReportLog;
import com.sypay.omp.report.service.ReportLogService;

/**
 * 
 * @author lishun
 *
 * @2015年5月7日
 */
@Transactional
@Service(value="reportLogService")
public class ReportLogServiceImpl implements ReportLogService {

    protected final Log log = LogFactory.getLog(ReportLogServiceImpl.class);
    
    @Autowired
    private BaseDao baseDao;

	@Override
	public int saveReportLog(ReportLog reportLog) {
		return (Integer) baseDao.save(reportLog);
	}
}
