package com.sypay.omp.report.service;

import com.sypay.omp.report.domain.ReportLog;


/**
 * 
 * @author lishun
 *
 * @2015年5月5日
 */
public interface ReportLogService {
    /**
     * 保存日志
     * @param reportChart
     * @return
     */
    public int saveReportLog(ReportLog reportLog);
}
