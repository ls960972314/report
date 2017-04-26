package com.report.web.query.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.report.biz.admin.service.ReportChartService;
import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.common.utils.VerificationUtil;
import com.report.common.dal.query.entity.dto.ReportChart;
import com.report.common.model.AjaxJson;
import com.report.common.model.GlobalResultStatus;
import com.report.common.model.JsonResult;
import com.report.common.model.PageHelper;
import com.report.common.model.ResultCodeConstants;
import com.report.common.model.query.ReportChartVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 图形管理
 * @author lishun
 * @since 2017年4月26日 下午2:10:29
 */
@Slf4j
@Controller
@RequestMapping("/chart")
public class ReportChartController {

	 @Resource
	 private ReportChartService reportChartService;
	 /**
     * 跳转到资源列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/chart.htm")
    public String index() {
        return "chart/chartList";
    }
    
    /**
     * 查找图形列表
     * @param chart
     * @param pageHelper
     * @return
     */
    @RequestMapping(value = "/findchartList.htm")
    @ResponseBody
    public Object findchartList(ReportChartVO chartVO, PageHelper pageHelper) {
    	return reportChartService.findChartList(chartVO, pageHelper);
    }
    
    /**
     * 根据reportFlag查找所有图形
     * @param reportFlag
     * @return
     */
    @RequestMapping(value = "/findChartNameList.htm")
    @ResponseBody
    public Object findChartNameList(String reportFlag) {
    	if (StringUtils.isBlank(reportFlag)) {
    		return JsonResult.fail(GlobalResultStatus.PARAM_ERROR);
    	}
    	List<ReportChart> list = Lists.newArrayList();
    	try {
    		list = reportChartService.findChartList(reportFlag);
		} catch (Exception e) {
			log.error("findChartNameList Exception", e);
			return JsonResult.fail(GlobalResultStatus.ERROR);
		}
    	return JsonResult.success(list);
    }
    
    /**
     * 更新图形
     * @param chart
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateChart.htm")
    @ResponseBody
    public Object updateChart(ReportChartVO chartVO, HttpServletRequest request) {
    	log.debug("updateChart ChartVO[{}]", JSON.toJSONString(chartVO));
        AjaxJson json = new AjaxJson();
        if (VerificationUtil.paramIsNull(chartVO, chartVO.getId(), chartVO.getToolFlag(), chartVO.getChartName())) {
        	json.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return json;
        }
        try {
        	reportChartService.updateReportChart(chartVO);
		} catch (Exception e) {
			log.error("updateChart Exception", e);
            json.setStatus(Constants.FAIL);
            json.setErrorInfo("更新图形失败！");
		}
        return json;
    }
}
