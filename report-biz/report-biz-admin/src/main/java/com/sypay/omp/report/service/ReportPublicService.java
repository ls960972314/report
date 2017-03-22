package com.sypay.omp.report.service;

import com.report.common.dal.admin.entity.vo.PageHelper;
import com.sypay.omp.per.model.page.DataGrid;
import com.sypay.omp.report.VO.PublicVO;
import com.sypay.omp.report.domain.ReportElement;
import com.sypay.omp.report.domain.ReportPublic;


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
