package com.report.common.repository.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.report.common.dal.admin.dao.ReportCommentDao;
import com.report.common.dal.admin.entity.dto.ReportComment;
import com.report.common.repository.ReportCommentRepository;

/**
 * ReportCommentRepositoryImpl
 * @author lishun
 * @since 2017年4月15日 下午5:36:41
 */
@Service
public class ReportCommentRepositoryImpl implements ReportCommentRepository {

	@Resource
	private ReportCommentDao reportCommentDao;
	
	@Override
	public void insert(ReportComment reportComment) {
		reportCommentDao.insert(reportComment);
	}

	@Override
	public void update(ReportComment reportComment) {
		reportCommentDao.update(reportComment);
	}

	@Override
	public void delete(String toolFlag) {
		reportCommentDao.delete(toolFlag);
	}

	@Override
	public ReportComment findReportComment(String toolflag) {
		return reportCommentDao.findReportComment(toolflag);
	}

	@Override
	public Long count(String toolflag) {
		return reportCommentDao.count(toolflag);
	}

	@Override
	public List<ReportComment> findReportCommentList(String toolflag) {
		return reportCommentDao.findReportCommentList(toolflag);
	}

}
