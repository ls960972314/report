package com.sypay.omp.report.service;

import java.util.List;

import com.sypay.omp.per.model.page.DataGrid;
import com.sypay.omp.per.model.page.PageHelper;
import com.sypay.omp.report.VO.ReportCommonConVO;
import com.sypay.omp.report.domain.ReportCommonCon;
import com.sypay.omp.report.domain.ReportCondition;


/**
 * 
 * @author lishun
 *
 * @2015年5月5日
 */
public interface ReportCommonConService {
    /**
     * 保存
     * @param reportChart
     * @return
     */
    public int saveReportCommonCon(ReportCommonCon reportCommonCon);
    
    /**
     * 修改
     * @param reportCondition
     */
	void updateReportCondition(ReportCommonCon reportCondition);
	
    /**
     * 根据toolFlag和conFlag查找List
     */
    public List<ReportCommonCon> findReportCommonConList(String toolFlag, String conFlag);
    
    /**
     * 分页展示
     */
    public DataGrid findReportCommonConList(ReportCommonConVO reportCommonCon,
			PageHelper pageHelper);

}
