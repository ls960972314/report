package com.report.facade.service;

import java.util.List;

import com.report.facade.entity.DataGrid;
import com.report.facade.entity.PageHelper;
import com.report.facade.entity.dto.ReportCondition;
import com.report.facade.entity.query.ConditionVO;


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
