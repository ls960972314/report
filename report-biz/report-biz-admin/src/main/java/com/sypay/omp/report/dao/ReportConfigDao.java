package com.sypay.omp.report.dao;

import java.util.List;

import com.sypay.omp.report.VO.ReportConfigVO;
import com.sypay.omp.report.domain.ReportConfig;

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
