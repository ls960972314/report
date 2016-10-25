package com.sypay.omp.report.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sypay.omp.per.common.Constants;
import com.sypay.omp.per.common.ResultCodeConstants;
import com.sypay.omp.per.model.page.AjaxJson;
import com.sypay.omp.per.model.page.DataGrid;
import com.sypay.omp.per.model.page.PageHelper;
import com.sypay.omp.report.VO.ChartVO;
import com.sypay.omp.report.domain.ReportChart;
import com.sypay.omp.report.json.JsonResult;
import com.sypay.omp.report.service.ReportChartService;
import com.sypay.omp.report.statuscode.GlobalResultStatus;
import com.sypay.omp.report.util.BeanUtil;

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
	            json.setStatus(Constants.OpStatus.FAIL);
	            json.setErrorInfo("更新失败！");
	            return json;
		}
        return json;
    }
}
