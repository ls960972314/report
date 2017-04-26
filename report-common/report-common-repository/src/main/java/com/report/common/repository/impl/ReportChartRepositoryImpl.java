package com.report.common.repository.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.report.common.dal.admin.dao.ReportChartDao;
import com.report.common.dal.admin.util.PageUtil;
import com.report.common.dal.query.entity.dto.ReportChart;
import com.report.common.repository.ReportChartRepository;

/**
 * ReportChartRepositoryImpl
 * @author lishun
 * @since 2017年4月26日 上午11:21:12
 */
@Service
public class ReportChartRepositoryImpl implements ReportChartRepository {

	@Resource
	private ReportChartDao reportChartDao;
	
	@Override
	public void saveReportChart(ReportChart reportChart) {
		reportChartDao.insert(reportChart);
	}
	
	@Override
	public void updateChart(ReportChart reportChart) {
		reportChartDao.update(reportChart);
	}

	@Override
	public List<ReportChart> findChartList(String reportFlag) {
		return reportChartDao.findChartListByToolFlag(reportFlag);
	}

	@Override
	public List<ReportChart> findChartList(Map<String, Object> params, int page, int rows) {
		return reportChartDao.findChartList(params, PageUtil.paged(page - 1, rows));
	}

	@Override
	public Long findChartCount(Map<String, Object> params) {
		return reportChartDao.findChartCount(params);
	}
}
