package com.report.facade.service;

import java.util.List;

import com.report.facade.entity.DataGrid;
import com.report.facade.entity.PageHelper;
import com.report.facade.entity.dto.ReportConfig;
import com.report.facade.entity.query.ReportConfigVO;


/**
 * 
 * @author lishun
 *
 * @2015年5月5日
 */
public interface ReportConfigService {
    /**
     * 保存
     * @param reportConfig
     * @return
     */
    public void saveReportConfig(ReportConfig reportConfig);
    
    /**
     * 修改
     * @param reportConfig
     */
    public void updateReportConfig (ReportConfig reportConfig);
    
    /**
     * 删除
     * @param rptCode
     */
    public void deleteReportConfig (String rptCode);
    
    /**
     * 查找
     * @param reportSqlVo
     * @param pageHelper
     * @return
     */
	public DataGrid findReportConfigList(ReportConfigVO reportConfigVo,
			PageHelper pageHelper);
	/**
	 * 根据主键查找
	 * @param rptCode
	 * @return
	 */

	public List<ReportConfig> findReportConfigList(String rptCode);
}
