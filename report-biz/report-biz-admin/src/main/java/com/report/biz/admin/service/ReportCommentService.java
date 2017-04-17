package com.report.biz.admin.service;

import java.util.List;

import com.report.common.model.ReportCommentVO;

/**
 * ReportCommentRepository
 * @author lishun
 * @since 2017年4月15日 下午5:34:41
 */
public interface ReportCommentService {

	/**
	 * 新增
	 * @param reportCommentVO
	 */
	public void insert(ReportCommentVO reportCommentVO);
	
	/**
	 * 更新
	 * @param reportCommentVO
	 */
	public void update(ReportCommentVO reportCommentVO);
	
	/**
	 * 删除
	 * @param toolFlag
	 */
	public void delete(String toolFlag);
	
	/**
	 * 根据toolflag查找某个reportCommentVO
	 * @param toolflag
	 * @return
	 */
	public ReportCommentVO findReportComment(String toolflag);
	
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
	public List<ReportCommentVO> findReportCommentList(String toolflag);
}
