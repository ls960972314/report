package com.report.web.query.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.report.biz.admin.service.ReportChartService;
import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.query.entity.dto.ReportChart;
import com.report.common.dal.query.util.BeanUtil;
import com.report.common.model.AjaxJson;
import com.report.common.model.DataGrid;
import com.report.common.model.GlobalResultStatus;
import com.report.common.model.JsonResult;
import com.report.common.model.PageHelper;
import com.report.common.model.ResultCodeConstants;
import com.report.common.model.query.ChartVO;

/**
 * 图形管理
 * @author lishun
 *
 */
@Controller
@RequestMapping("/chart")
public class ReportChartController {

	 private final Log logger = LogFactory.getLog(ReportChartController.class);
	 
	 @Resource
	 private ReportChartService reportChartService;
	 /**
     * 跳转到资源列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/chart.htm")
    public String resource() {
        return "chart/chartList";
    }
    
    @RequestMapping(value = "/findchartList.htm")
    @ResponseBody
    public DataGrid findchartList(ChartVO chart, PageHelper pageHelper) {
    	return reportChartService.findChartList(chart, pageHelper);
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
    	
    	List<ReportChart> list = null;
    	try {
    		list = reportChartService.queryReportChart(reportFlag);
		} catch (Exception e) {
			logger.error("findChartNameList Exception", e);
			return JsonResult.fail(GlobalResultStatus.ERROR);
		}
    	return JsonResult.success(list);
    }
    
    @RequestMapping(value = "/updateChart.htm")
    @ResponseBody
    public AjaxJson updateChart(ChartVO chart, HttpServletRequest request) {
        AjaxJson json = new AjaxJson();
        if (chart.getId() == null || StringUtils.isBlank(chart.getToolFlag()) || StringUtils.isEmpty(chart.getChartName())) {
        	json.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return json;
        }
        ReportChart reportChart = new ReportChart();
        BeanUtil.copyProperties(chart, reportChart);
        try {
        	reportChartService.updateChart(reportChart);
		} catch (Exception e) {
	            json.setStatus(Constants.FAIL);
	            json.setErrorInfo("更新失败！");
	            return json;
		}
        return json;
    }
}
