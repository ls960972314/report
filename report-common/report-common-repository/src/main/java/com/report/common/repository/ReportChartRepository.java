package com.report.common.repository;

import java.util.List;
import java.util.Map;

import com.report.common.dal.query.entity.dto.ReportChart;

/**
 * ReportChartRepository
 * @author lishun
 * @since 2017年4月26日 上午9:37:00
 */
public interface ReportChartRepository {
	/**
     * 保存报表图形
     * @param reportChart
     * @return
     */
    public void saveReportChart(ReportChart reportChart);
    
    /**
	 * 更新图
	 * @param chart
	 */
	public void updateChart(ReportChart chart);
    
    /**
     * 根据reportFlag查询ReportChart
     * @param reportFlag
     * @return
     */
    public List<ReportChart> findChartList(String reportFlag);

    /**
     * 查找图列表
     * @param params
     * @param page
     * @param rows
     * @return
     */
	public List<ReportChart> findChartList(Map<String, Object> params, int page, int rows);
	
	/**
	 * 查找图数量
	 * @param params
	 * @return
	 */
	public Long findChartCount(Map<String, Object> params);
}
