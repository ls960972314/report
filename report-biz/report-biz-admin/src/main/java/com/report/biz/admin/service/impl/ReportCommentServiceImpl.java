package com.report.biz.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.report.biz.admin.service.ReportCommentService;
import com.report.common.dal.admin.entity.dto.ReportComment;
import com.report.common.model.ReportCommentVO;
import com.report.common.repository.ReportCommentRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * ReportCommentServiceImpl
 * @author lishun
 * @since 2017年4月15日 下午5:36:41
 */
@Slf4j
@Service
public class ReportCommentServiceImpl implements ReportCommentService {

	@Resource
	private ReportCommentRepository reportCommentRepository;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insert(ReportCommentVO reportCommentVO) {
		ReportComment reportComment = new ReportComment();
		BeanUtils.copyProperties(reportCommentVO, reportComment);
		try {
			reportCommentRepository.insert(reportComment);
		} catch (Exception e) {
			log.error("ReportComment insert Exception", e);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void update(ReportCommentVO reportCommentVO) {
		ReportComment reportComment = new ReportComment();
		BeanUtils.copyProperties(reportCommentVO, reportComment);
		reportCommentRepository.update(reportComment);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void delete(String toolFlag) {
		reportCommentRepository.delete(toolFlag);
	}

	@Override
	public ReportCommentVO findReportComment(String toolflag) {
		ReportComment reportComment = reportCommentRepository.findReportComment(toolflag);
		if (null != reportComment) {
			ReportCommentVO reportCommentVO = new ReportCommentVO();
			BeanUtils.copyProperties(reportComment, reportCommentVO);
			return reportCommentVO;
		}
		return null;
	}

	@Override
	public Long count(String toolflag) {
		return reportCommentRepository.count(toolflag);
	}

	@Override
	public List<ReportCommentVO> findReportCommentList(String toolflag) {
		return null;
	}

}
