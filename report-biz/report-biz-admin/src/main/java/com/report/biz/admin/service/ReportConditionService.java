package com.report.biz.admin.service;

import java.util.List;

import com.report.common.dal.query.entity.dto.ReportCondition;
import com.report.common.model.DataGrid;
import com.report.common.model.PageHelper;
import com.report.common.model.query.ReportConditionVO;


/**
 * ReportConditionService
 * @author lishun
 * @since 2017年4月26日 下午5:41:28
 */
public interface ReportConditionService {
    /**
     * 保存报表控件
     * @param reportChart
     * @return
     */
    public void saveReportCondition(ReportConditionVO conditionVO);
    
    /**
     * 更新报表空间
     * @param reportCondition
     * @return
     */
    public void updateReportCondition(ReportConditionVO conditionVO);
    
    /**
     * 根据toolFlag查询控件列表
     * @param toolFlag
     * @return
     */
    public List<ReportCondition> findReportCondition(String toolFlag);

    /**
     * 分页查询控件列表
     * @param condition
     * @param pageHelper
     * @return
     */
	public DataGrid findConditionList(ReportConditionVO conditionVO, PageHelper pageHelper);

}
