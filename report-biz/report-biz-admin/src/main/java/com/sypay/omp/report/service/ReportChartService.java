package com.sypay.omp.report.service;

import java.util.List;

import com.sypay.omp.per.model.page.DataGrid;
import com.sypay.omp.per.model.page.PageHelper;
import com.sypay.omp.report.VO.ChartVO;
import com.sypay.omp.report.domain.ReportChart;


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
