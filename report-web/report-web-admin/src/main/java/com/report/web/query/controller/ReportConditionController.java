package com.report.web.query.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.query.util.BeanUtil;
import com.report.common.model.AjaxJson;
import com.report.common.model.ResultCodeConstants;
import com.report.facade.entity.DataGrid;
import com.report.facade.entity.PageHelper;
import com.report.facade.entity.dto.ReportCondition;
import com.report.facade.entity.query.ConditionVO;
import com.report.facade.service.ReportConditionService;

import lombok.extern.slf4j.Slf4j;

/**
 * 图形管理
 * @author lishun
 *
 */
@Slf4j
@Controller
@RequestMapping("/condition")
public class ReportConditionController {

	 @Resource
	 private ReportConditionService reportConditionService;
	 /**
     * 跳转到资源列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/condition.htm")
    public String resource() {
        return "condition/conditionList";
    }
    
    @RequestMapping(value = "/findConditionList.htm")
    @ResponseBody
    public DataGrid findConditionList(ConditionVO condition, PageHelper pageHelper) {
    	return reportConditionService.findConditionList(condition, pageHelper);
    }
    
    /**
     * 新增报表条件
     * @param condition
     * @param request
     * @return
     */
    @RequestMapping(value = "/addCondition.htm")
    @ResponseBody
    public AjaxJson addCondition(ConditionVO condition, HttpServletRequest request) {
        AjaxJson json = new AjaxJson();
        if (StringUtils.isBlank(condition.getToolFlag()) || StringUtils.isEmpty(condition.getConName())) {
        	json.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return json;
        }
        ReportCondition reportCondition = new ReportCondition();
        BeanUtil.copyProperties(condition, reportCondition);
        try {
        	reportConditionService.saveReportCondition(reportCondition);
		} catch (Exception e) {
	            json.setStatus(Constants.OpStatus.FAIL);
	            json.setErrorInfo("更新失败！");
	            return json;
		}
        return json;
    }
    
    /**
     * 更新报表条件
     * @param condition
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateCondition.htm")
    @ResponseBody
    public AjaxJson updateCondition(ConditionVO condition, HttpServletRequest request) {
        AjaxJson json = new AjaxJson();
        if (condition.getId() == null || StringUtils.isBlank(condition.getToolFlag()) || StringUtils.isEmpty(condition.getConName())) {
        	json.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return json;
        }
        ReportCondition reportCondition = new ReportCondition();
        BeanUtil.copyProperties(condition, reportCondition);
        try {
        	reportConditionService.updateReportCondition(reportCondition);
		} catch (Exception e) {
	            json.setStatus(Constants.OpStatus.FAIL);
	            json.setErrorInfo("更新失败！");
	            return json;
		}
        return json;
    }
}
