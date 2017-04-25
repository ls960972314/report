package com.report.biz.admin.service;

import java.util.List;

import com.report.common.dal.query.entity.dto.ReportCommonCon;
import com.report.common.model.DataGrid;
import com.report.common.model.PageHelper;
import com.report.common.model.query.ReportCommonConVO;


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
