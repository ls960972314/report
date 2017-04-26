package com.report.common.repository.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.report.common.dal.admin.dao.ReportConditionDao;
import com.report.common.dal.admin.util.PageUtil;
import com.report.common.dal.query.entity.dto.ReportCondition;
import com.report.common.repository.ReportConditionRepository;

/**
 * 
 * @author lishun
 * @since 2017年4月26日 下午5:28:02
 */
@Repository
public class ReportConditionRepositoryImpl implements ReportConditionRepository {

	@Resource
	private ReportConditionDao reportConditionDao;
	
	@Override
	public void saveReportCondition(ReportCondition reportCondition) {
		reportConditionDao.insert(reportCondition);
	}

	@Override
	public void updateReportCondition(ReportCondition reportCondition) {
		reportConditionDao.update(reportCondition);
	}

	@Override
	public List<ReportCondition> findConditionList(String toolFlag) {
		return reportConditionDao.findConditionListByToolFlag(toolFlag);
	}

	@Override
	public List<ReportCondition> findConditionList(Map<String, Object> params, int page, int rows) {
		return reportConditionDao.findConditionList(params, PageUtil.paged(page - 1, rows));
	}

	@Override
	public Long findConditionCount(Map<String, Object> params) {
		return reportConditionDao.findConditionCount(params);
	}


}
