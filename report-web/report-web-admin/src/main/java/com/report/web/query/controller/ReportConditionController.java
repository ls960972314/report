package com.report.web.query.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.report.biz.admin.service.ReportConditionService;
import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.common.utils.VerificationUtil;
import com.report.common.model.AjaxJson;
import com.report.common.model.DataGrid;
import com.report.common.model.PageHelper;
import com.report.common.model.ResultCodeConstants;
import com.report.common.model.query.ReportConditionVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 报表控件管理
 * 
 * @author lishun
 * @since 2017年4月26日 下午5:44:51
 */
@Slf4j
@Controller
@RequestMapping("/condition")
public class ReportConditionController {

	@Resource
	private ReportConditionService reportConditionService;

	@RequestMapping(value = "/condition.htm")
	public String index() {
		return "condition/conditionList";
	}

	@RequestMapping(value = "/findConditionList.htm")
	@ResponseBody
	public DataGrid findConditionList(ReportConditionVO condition, PageHelper pageHelper) {
		return reportConditionService.findConditionList(condition, pageHelper);
	}

	/**
	 * 新增报表控件
	 * 
	 * @param conditionVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addCondition.htm")
	@ResponseBody
	public AjaxJson addCondition(ReportConditionVO conditionVO, HttpServletRequest request) {
		AjaxJson json = new AjaxJson();
		if (StringUtils.isBlank(conditionVO.getToolFlag()) || StringUtils.isEmpty(conditionVO.getConName())) {
			json.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
			return json;
		}
		try {
			reportConditionService.saveReportCondition(conditionVO);
		} catch (Exception e) {
			log.error("addCondition Exception", e);
			json.setStatus(Constants.FAIL);
			json.setErrorInfo("新增控件失败！");
			return json;
		}
		return json;
	}

	/**
	 * 更新报表控件
	 * 
	 * @param conditionVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateCondition.htm")
	@ResponseBody
	public AjaxJson updateCondition(ReportConditionVO conditionVO, HttpServletRequest request) {
		AjaxJson json = new AjaxJson();
		if (VerificationUtil.paramIsNull(conditionVO, conditionVO.getId(), conditionVO.getToolFlag(),
				conditionVO.getConName())) {
			json.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
			return json;
		}
		try {
			reportConditionService.updateReportCondition(conditionVO);
		} catch (Exception e) {
			log.error("updateCondition Exception", e);
			json.setStatus(Constants.FAIL);
			json.setErrorInfo("更新控件失败！");
		}
		return json;
	}
}
