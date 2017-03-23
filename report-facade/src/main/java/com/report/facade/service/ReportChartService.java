package com.report.facade.service;

import java.util.List;

import com.report.facade.entity.DataGrid;
import com.report.facade.entity.PageHelper;
import com.report.facade.entity.dto.ReportChart;
import com.report.facade.entity.query.ChartVO;


/**
 * 
 * @author lishun
 *
 * @2015年5月5日
 */
public interface ReportChartService {
    /**
     * 保存报表图形
     * @param reportChart
     * @return
     */
    public int saveReportChart(ReportChart reportChart);
    
    /**
     *修改报表图形 
     */
    public int updateReportChart(ReportChart reportChart);
    
    /**
     * 根据reportFlag查询ReportChart
     * @param reportFlag
     * @return
     */
    public List<ReportChart> queryReportChart(String reportFlag);

    /**
     * 查找图列表
     * @param chart
     * @param pageHelper
     * @return
     */
	public DataGrid findChartList(ChartVO chart, PageHelper pageHelper);
	/**
	 * 更新图
	 * @param chart
	 */
	public void updateChart(ReportChart chart);
}
