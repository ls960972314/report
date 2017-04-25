package com.report.web.query.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.report.biz.admin.service.ReportCommonConService;
import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.query.entity.dto.ReportCommonCon;
import com.report.common.dal.query.util.BeanUtil;
import com.report.common.model.AjaxJson;
import com.report.common.model.DataGrid;
import com.report.common.model.PageHelper;
import com.report.common.model.ResultCodeConstants;
import com.report.common.model.query.ReportCommonConVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 共用报表差异条件管理
 * 
 * @author lishun
 *
 */
@Controller
@RequestMapping("/commonCondition")
public class ReportCommonConController {


	@Resource
	private ReportCommonConService reportCommonConService;

	/**
	 * 跳转到资源列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/commonCondition.htm")
	public String resource() {
		return "commonCondition/commonCondition";
	}
	/**
	 * 根据条件查找 
	 * @param reportCommonCon
	 * @param pageHelper
	 * @return
	 */
	@RequestMapping(value = "/findCommonConditionList.htm")
	@ResponseBody
	public DataGrid findCommonConditionList(ReportCommonConVO reportCommonCon, PageHelper pageHelper) {
		return reportCommonConService.findReportCommonConList(reportCommonCon, pageHelper);
	}

	/**
	 * 增加
	 * @param reportCommonConVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addCommonCondition.htm")
	@ResponseBody
	public AjaxJson addCommonCondition(ReportCommonConVO reportCommonConVO, HttpServletRequest request) {
		AjaxJson json = new AjaxJson();
		if (StringUtils.isBlank(reportCommonConVO.getToolFlag())
				|| StringUtils.isEmpty(reportCommonConVO.getConFlag())) {
			json.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
			return json;
		}
		ReportCommonCon reportCommonCon = new ReportCommonCon();
		BeanUtil.copyProperties(reportCommonConVO, reportCommonCon);
		reportCommonConService.saveReportCommonCon(reportCommonCon);
		return json;
	}
	
	/**
	 * 修改
	 * @param reportCommonConVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateCommonCondition.htm")
	@ResponseBody
	public AjaxJson updateCommonCondition(ReportCommonConVO reportCommonConVO, HttpServletRequest request) {
		AjaxJson json = new AjaxJson();
		if (reportCommonConVO.getId() == null || StringUtils.isBlank(reportCommonConVO.getToolFlag())
				|| StringUtils.isEmpty(reportCommonConVO.getConFlag())) {
			json.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
			return json;
		}
		ReportCommonCon reportCommonCon = new ReportCommonCon();
		BeanUtil.copyProperties(reportCommonConVO, reportCommonCon);
		try {
			reportCommonConService.updateReportCondition(reportCommonCon);
		} catch (Exception e) {
			json.setStatus(Constants.FAIL);
			json.setErrorInfo("更新失败！");
			return json;
		}
		
		return json;
	}
}
