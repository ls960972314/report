package com.report.biz.admin.service;

import java.util.List;

import com.report.common.dal.query.entity.dto.ReportCondition;
import com.report.common.model.DataGrid;
import com.report.common.model.PageHelper;
import com.report.common.model.query.ConditionVO;


/**
 * 
 * @author lishun
 *
 * @2015年5月5日
 */
public interface ReportConditionService {
    /**
     * 保存报表条件
     * @param reportChart
     * @return
     */
    public void saveReportCondition(ReportCondition reportCondition);
    
    /**
     * 修改报表条件
     * @param reportCondition
     * @return
     */
    public void updateReportCondition(ReportCondition reportCondition);
    
    /**
     * 根据reportFlag查询ReportCondition
     * @param reportFlag
     * @return
     */
    public List<ReportCondition> queryReportCondition(String reportFlag);

	public DataGrid findConditionList(ConditionVO condition,
			PageHelper pageHelper);

}
