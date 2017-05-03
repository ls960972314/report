package com.report.biz.admin.service;

import com.report.common.dal.query.entity.dto.ReportPublic;
import com.report.common.dal.query.entity.vo.ReportElement;
import com.report.common.model.DataGrid;
import com.report.common.model.PageHelper;
import com.report.common.model.query.ReportPublicVO;


/**
 * saveReportPublic
 * @author lishun
 * @since 2017年5月3日 下午8:19:59
 */
public interface ReportPublicService {
    
    /**
     * 保存报表公共信息
     * @param reportPublic
     * @return
     */
    public void saveReportPublic(ReportPublicVO reportPublicVO);
    
    /**
     * 修改报表公共信息
     * @param reportPublic
     * @return
     */
    public void updateReportPublic(ReportPublicVO reportPublicVO);
    
    /**
     * 查询报表公共信息
     * @param toolFlag
     * @return
     */
    public ReportPublic queryReportPublic(String toolFlag);

    /**
     * 保存报表公共信息,条件,图等
     * @param reportElement
     */
	public void saveReport(ReportElement reportElement);

	/**
	 * 查找报表公共信息
	 * @param publicVo
	 * @param pageHelper
	 * @return
	 */
	public DataGrid findPublicList(ReportPublicVO publicVo, PageHelper pageHelper);
}
