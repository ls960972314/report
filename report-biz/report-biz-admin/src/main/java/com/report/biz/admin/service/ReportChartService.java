package com.report.biz.admin.service;

import java.util.List;

import com.report.common.dal.query.entity.dto.ReportChart;
import com.report.common.model.DataGrid;
import com.report.common.model.PageHelper;
import com.report.common.model.query.ChartVO;


/**
 * ReportChartService
 * @author lishun
 * @since 2017年4月26日 下午1:57:45
 */
public interface ReportChartService {
    /**
     * 保存报表图形
     * @param reportChart
     * @return
     */
    public void saveReportChart(ChartVO reportChart);
    
    /**
     * 更新报表图形 
     */
    public void updateReportChart(ChartVO reportChart);
    /**
     * 根据reportFlag查询ReportChart
     * @param reportFlag
     * @return
     */
    public List<ReportChart> findChartList(String reportFlag);

    /**
     * 查找图列表
     * @param chart
     * @param pageHelper
     * @return
     */
	public DataGrid findChartList(ChartVO chart, PageHelper pageHelper);
}
