package com.report.common.dal.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.report.common.dal.query.entity.dto.ReportChart;

/**
 * ReportChartDao
 * @author lishun
 * @since 2017年4月25日 下午5:56:49
 */
public interface ReportChartDao {
	
	/**
	 * 新增图表
	 * @param reportChart
	 */
	public void insert(ReportChart reportChart);
	/**
	 * 更新图表
	 * @param reportChart
	 */
	public void update(ReportChart reportChart);
	/**
	 * 根据toolFlag删除图表
	 * @param reportFlag
	 * @return
	 */
	public void deleteByReportFlag(String reportFlag);
	
	/**
	 * 根据toolFlag查找ReportChart
	 * @param toolFlag
	 * @return
	 */
	public List<ReportChart> findChartListByToolFlag(String toolFlag);
	
	/**
	 * 查找图列表
	 * @param param
	 * @return
	 */
	public List<ReportChart> findChartList(Map<String, Object> params, RowBounds rowBounds);
	
	/**
	 * 查找图数量
	 * @param params
	 * @return
	 */
	public Long findChartCount(Map<String, Object> params);
	
}
