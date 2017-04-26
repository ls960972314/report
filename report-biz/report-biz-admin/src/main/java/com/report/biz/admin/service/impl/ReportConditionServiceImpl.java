package com.report.biz.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.report.biz.admin.service.ReportConditionService;
import com.report.common.dal.query.entity.dto.ReportCondition;
import com.report.common.dal.query.util.BeanUtil;
import com.report.common.model.DataGrid;
import com.report.common.model.PageHelper;
import com.report.common.model.query.ReportConditionVO;
import com.report.common.repository.ReportConditionRepository;

/**
 * 
 * @author lishun
 *
 * @2015年5月7日
 */
@Service
public class ReportConditionServiceImpl implements ReportConditionService {

	@Resource
	private ReportConditionRepository reportConditionRepository;
	
	@Transactional(rollbackFor=Exception.class)
    @Override
    public void saveReportCondition(ReportConditionVO conditionVO) {
    	ReportCondition reportCondition = new ReportCondition();
	    BeanUtil.copyProperties(conditionVO, reportCondition);
    	reportConditionRepository.saveReportCondition(reportCondition);
    }

	@Transactional(rollbackFor=Exception.class)
    @Override
    public void updateReportCondition(ReportConditionVO conditionVO) {
    	ReportCondition reportCondition = new ReportCondition();
	    BeanUtil.copyProperties(conditionVO, reportCondition);
    	reportConditionRepository.updateReportCondition(reportCondition);
    }

    @Override
    public List<ReportCondition> findReportCondition(String toolFlag) {
        return reportConditionRepository.findConditionList(toolFlag);
    }

	@Override
	public DataGrid findConditionList(ReportConditionVO condition, PageHelper pageHelper) {
		DataGrid dataGrid = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(condition.getToolFlag())) {
        	params.put("toolFlag", condition.getToolFlag());
        }
		dataGrid.setRows(reportConditionRepository.findConditionList(params, pageHelper.getPage(), pageHelper.getRows()));
		dataGrid.setTotal(reportConditionRepository.findConditionCount(params));
		return dataGrid;
	}
}
