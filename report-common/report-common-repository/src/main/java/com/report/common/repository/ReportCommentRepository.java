package com.report.common.repository;

import java.util.List;

import com.report.common.dal.admin.entity.dto.ReportComment;

/**
 * ReportCommentRepository
 * @author lishun
 * @since 2017年4月15日 下午5:34:41
 */
public interface ReportCommentRepository {

	/**
	 * 新增
	 * @param reportComment
	 */
	public void insert(ReportComment reportComment);
	
	/**
	 * 更新
	 * @param reportComment
	 */
	public void update(ReportComment reportComment);
	
	/**
	 * 删除
	 * @param toolFlag
	 */
	public void delete(String toolFlag);
	
	/**
	 * 根据toolflag查找某个ReportComment
	 * @param toolflag
	 * @return
	 */
	public ReportComment findReportComment(String toolflag);
	
	/**
	 * 根据条件查ReportComment数量
	 * @param toolflag
	 * @return
	 */
	public Long count(String toolflag);
	
	/**
	 * 根据条件ReportComment列表
	 * @param toolflag
	 * @return
	 */
	public List<ReportComment> findReportCommentList(String toolflag);
}
