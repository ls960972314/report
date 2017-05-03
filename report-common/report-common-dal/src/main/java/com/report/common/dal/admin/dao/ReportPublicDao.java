package com.report.common.dal.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.report.common.dal.query.entity.dto.ReportPublic;

/**
 * ReportPublicDao
 * @author lishun
 * @since 2017年5月3日 下午7:58:19
 */
public interface ReportPublicDao {
	
	/**
	 * 新增报表公共信息
	 * @param reportPublic
	 */
	public void insert(ReportPublic reportPublic);
	/**
	 * 更新报表公共信息
	 * @param reportChart
	 */
	public void update(ReportPublic reportPublic);
	/**
	 * 根据toolFlag删除报表公共信息
	 * @param toolFlag
	 * @return
	 */
	public void deleteByReportFlag(String toolFlag);
	
	/**
	 * 根据toolFlag查找ReportChart
	 * @param toolFlag
	 * @return
	 */
	public ReportPublic findPubListByToolFlag(String toolFlag);
	
	/**
	 * 查找报表公共信息列表
	 * @param params
	 * @param rowBounds
	 * @return
	 */
	public List<ReportPublic> findPubList(Map<String, Object> params, RowBounds rowBounds);
	
	/**
	 * 查找报表公共信息数量
	 * @param params
	 * @return
	 */
	public Long findPubCount(Map<String, Object> params);
	
}
