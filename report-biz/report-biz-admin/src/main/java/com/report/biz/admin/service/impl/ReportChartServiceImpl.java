package com.report.biz.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.report.biz.admin.service.ReportChartService;
import com.report.common.dal.query.entity.dto.ReportChart;
import com.report.common.dal.query.util.BeanUtil;
import com.report.common.model.DataGrid;
import com.report.common.model.PageHelper;
import com.report.common.model.query.ChartVO;
import com.report.common.repository.ReportChartRepository;

/**
 * ReportChartServiceImpl
 * @author lishun
 * @since 2017年4月26日 下午2:04:45
 */
@Service
public class ReportChartServiceImpl implements ReportChartService {

    @Resource
    private ReportChartRepository reportChartRepository;

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void saveReportChart(ChartVO chartVO) {
		ReportChart reportChart = new ReportChart();
	    BeanUtil.copyProperties(chartVO, reportChart);
    	reportChartRepository.saveReportChart(reportChart);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void updateReportChart(ChartVO chartVO) {
    	ReportChart reportChart = new ReportChart();
	    BeanUtil.copyProperties(chartVO, reportChart);
    	reportChartRepository.updateChart(reportChart);
    }
    
    @Override
    public List<ReportChart> findChartList(String toolFlag) {
        return  reportChartRepository.findChartList(toolFlag);
    }

	@Override
	public DataGrid findChartList(ChartVO chart, PageHelper pageHelper) {
		DataGrid dataGrid = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(chart.getToolFlag())) {
        	params.put("toolFlag", chart.getToolFlag());
        }
        if (StringUtils.isNotBlank(chart.getChartName())) {
        	params.put("chartName", chart.getChartName());
        }
		dataGrid.setRows(reportChartRepository.findChartList(params, pageHelper.getPage(), pageHelper.getRows()));
		dataGrid.setTotal(reportChartRepository.findChartCount(params));
		return dataGrid;
	}
}
