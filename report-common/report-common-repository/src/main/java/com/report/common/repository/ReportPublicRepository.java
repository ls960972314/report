package com.report.common.repository;

import java.util.List;
import java.util.Map;

import com.report.common.dal.query.entity.dto.ReportPublic;

/**
 * ReportPublicRepository
 * @author lishun
 * @since 2017年5月3日 下午8:09:44
 */
public interface ReportPublicRepository {
	/**
     * 保存报表公共信息
     * @param ReportPublic
     * @return
     */
    public void saveReportPublic(ReportPublic reportPublic);
    
    /**
	 * 更新报表公共信息
	 * @param reportPublic
	 */
	public void updateReportPublic(ReportPublic reportPublic);
    
    /**
     * 根据reportFlag查询ReportPublic
     * @param toolFlag
     * @return
     */
    public ReportPublic queryReportPublic(String toolFlag);

    /**
     * 查找报表公共信息列表
     * @param params
     * @param page
     * @param rows
     * @return
     */
	public List<ReportPublic> findPubList(Map<String, Object> params, int page, int rows);
	
	/**
	 * 查找报表公共信息数量
	 * @param params
	 * @return
	 */
	public Long findPubCount(Map<String, Object> params);
}
