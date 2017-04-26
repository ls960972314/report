package com.report.common.repository;

import java.util.List;
import java.util.Map;

import com.report.common.dal.query.entity.dto.ReportCondition;

/**
 * ReportConditionRepository
 * @author lishun
 * @since 2017年4月26日 下午5:25:33
 */
public interface ReportConditionRepository {
	/**
	 * 保存控件
	 * @param reportCondition
	 */
    public void saveReportCondition(ReportCondition reportCondition);
    
    /**
     * 更新控件
     * @param reportCondition
     */
	public void updateReportCondition(ReportCondition reportCondition);
    
    /**
     * 根据reportFlag查询ReportCondition
     * @param toolFlag
     * @return
     */
    public List<ReportCondition> findConditionList(String toolFlag);

    /**
     * 查找符合条件的控件列表
     * @param params
     * 					toolFlag 报表标志
     * @param page
     * @param rows
     * @return
     */
	public List<ReportCondition> findConditionList(Map<String, Object> params, int page, int rows);
	
	/**
	 * 查找符合条件的控件数量
	 * @param params
	 * 					toolFlag 报表标志
	 * @return
	 */
	public Long findConditionCount(Map<String, Object> params);
}
