package com.sypay.omp.report.web;

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
import com.sypay.omp.report.VO.ReportSqlVO;
import com.sypay.omp.report.domain.ReportSql;
import com.sypay.omp.report.service.ReportSqlService;
import com.sypay.omp.report.util.BeanUtil;

/**
 * sql管理
 * @author lishun
 *
 */
@Controller
@RequestMapping("/reportSql")
public class ReportSqlController {

	 private final Log logger = LogFactory.getLog(ReportSqlController.class);
	 
	 @Resource
	 private ReportSqlService reportSqlService;
	 /**
     * 跳转到资源列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/reportSql.htm")
    public String resource() {
        return "reportSql/reportSqlList";
    }
    
    @RequestMapping(value = "/findReportSqlList.htm")
    @ResponseBody
    public DataGrid findReportSqlList(ReportSqlVO reportSqlVo, PageHelper pageHelper) {
    	return reportSqlService.findReportSqlList(reportSqlVo, pageHelper);
    }
    
    @RequestMapping(value = "/addReportSql.htm")
    @ResponseBody
    public AjaxJson addReportSql(ReportSqlVO reportSqlVo, HttpServletRequest request) {
        AjaxJson json = new AjaxJson();
        if (StringUtils.isBlank(reportSqlVo.getBaseCountSql()) || StringUtils.isBlank(reportSqlVo.getBaseSql())) {
        	json.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return json;
        }
        ReportSql reportSql = new ReportSql();
        BeanUtil.copyProperties(reportSqlVo, reportSql);
    	reportSqlService.addReportSql(reportSql);
        
        return json;
    }
    /**
     * 修改
     * @param reportSqlVo
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateReportSql.htm")
    @ResponseBody
    public AjaxJson updateReportSql(ReportSqlVO reportSqlVo, HttpServletRequest request) {
        AjaxJson json = new AjaxJson();
        if (reportSqlVo.getSqlId() == null || StringUtils.isBlank(reportSqlVo.getBaseCountSql()) || StringUtils.isBlank(reportSqlVo.getBaseSql())) {
        	json.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return json;
        }
        ReportSql reportSql = new ReportSql();
        BeanUtil.copyProperties(reportSqlVo, reportSql);
        try {
        	reportSqlService.updatePublic(reportSql);
		} catch (Exception e) {
	            json.setStatus(Constants.OpStatus.FAIL);
	            json.setErrorInfo("更新失败！");
	            return json;
		}
        return json;
    }
}
