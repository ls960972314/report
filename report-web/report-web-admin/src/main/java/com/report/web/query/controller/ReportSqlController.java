package com.report.web.query.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.query.util.BeanUtil;
import com.report.common.model.AjaxJson;
import com.report.common.model.ResultCodeConstants;
import com.report.facade.entity.DataGrid;
import com.report.facade.entity.PageHelper;
import com.report.facade.entity.dto.ReportSql;
import com.report.facade.entity.query.ReportSqlVO;
import com.report.facade.service.ReportSqlService;

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
	            json.setStatus(Constants.FAIL);
	            json.setErrorInfo("更新失败！");
	            return json;
		}
        return json;
    }
}
