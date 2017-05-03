package com.report.common.repository.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.report.common.dal.admin.dao.ReportPublicDao;
import com.report.common.dal.admin.util.PageUtil;
import com.report.common.dal.query.entity.dto.ReportPublic;

/**
 * ReportPublicRepository
 * @author lishun
 * @since 2017年5月3日 下午8:10:51
 */
@Repository
public class ReportPublicRepositoryImpl implements com.report.common.repository.ReportPublicRepository {

	@Resource
	private ReportPublicDao reportPublicDao;
	
	@Override
	public void saveReportPublic(ReportPublic reportPublic) {
		reportPublicDao.insert(reportPublic);
	}

	@Override
	public void updateReportPublic(ReportPublic reportPublic) {
		reportPublicDao.update(reportPublic);
	}

	@Override
	public ReportPublic queryReportPublic(String toolFlag) {
		return reportPublicDao.findPubListByToolFlag(toolFlag);
	}

	@Override
	public List<ReportPublic> findPubList(Map<String, Object> params, int page, int rows) {
		return reportPublicDao.findPubList(params, PageUtil.paged(page - 1, rows));
	}

	@Override
	public Long findPubCount(Map<String, Object> params) {
		return reportPublicDao.findPubCount(params);
	}

}
