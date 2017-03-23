package com.report.facade.service;

import com.report.facade.entity.DataGrid;
import com.report.facade.entity.PageHelper;
import com.report.facade.entity.dto.ReportPublic;
import com.report.facade.entity.query.PublicVO;
import com.report.facade.entity.vo.ReportElement;


/**
 * 
 * @author lishun
 *
 * @2015年5月5日
 */
public interface ReportPublicService {
    
    /**
     * 保存报表公共信息
     * @param reportChart
     * @return
     */
    public void saveReportPublic(ReportPublic reportPublic);
    
    /**
     * 修改报表公共信息
     * @param reportPublic
     * @return
     */
    public void updateReportPublic(ReportPublic reportPublic);
    
    /**
     * 查询报表公共信息
     * @param reportFlag
     * @return
     */
    public ReportPublic queryReportPublic(String reportFlag);

    /**
     * 保存报表公共信息，条件，图等
     * @param reportElement
     */
	public void saveReport(ReportElement reportElement)  throws Exception;

	/**
	 * 查找报表公共信息
	 * @param publicVo
	 * @param pageHelper
	 * @return
	 */
	public DataGrid findPublicList(PublicVO publicVo, PageHelper pageHelper);

	/**
	 * 更新报表公共信息
	 * @param reportpublic
	 */
	public void updatePublic(ReportPublic reportpublic);
    
}
