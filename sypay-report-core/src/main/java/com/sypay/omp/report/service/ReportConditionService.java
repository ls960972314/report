package com.sypay.omp.report.service;

import java.util.List;

import com.sypay.omp.per.model.page.DataGrid;
import com.sypay.omp.per.model.page.PageHelper;
import com.sypay.omp.report.VO.ConditionVO;
import com.sypay.omp.report.domain.ReportCondition;


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
