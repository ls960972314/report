package com.report.common.dal.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.report.common.dal.query.entity.dto.ReportCondition;

/**
 * ReportConditionDao
 * @author lishun
 * @since 2017年4月26日 下午5:09:33
 */
public interface ReportConditionDao {

	/**
	 * 新增控件
	 * @param reportCondition
	 */
	public void insert(ReportCondition reportCondition);
	/**
	 * 更新控件
	 * @param reportCondition
	 */
	public void update(ReportCondition reportCondition);
	/**
	 * 根据报表标志删除控件
	 * @param toolFlag
	 */
	public void deleteByReportFlag(String toolFlag);
	/**
	 * 根据toolFlag查询控件列表
	 * @param toolFlag
	 * @return
	 */
	public List<ReportCondition> findConditionListByToolFlag(String toolFlag);
	/**
	 * 根据toolFlag查询控件列表
	 * @param params
	 * 					toolFlag 报表标志
	 * @param rowBounds
	 * @return
	 */
	public List<ReportCondition> findConditionList(Map<String, Object> params, RowBounds rowBounds);
	/**
	 * 根据toolFlag查询控件列表数量
	 * @param params
	 * 					toolFlag 报表标志
	 * @return
	 */
	public Long findConditionCount(Map<String, Object> params);
}
