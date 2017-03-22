package com.sypay.omp.report.web;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.admin.entity.vo.PageHelper;
import com.report.common.dal.report.util.BeanUtil;
import com.sypay.omp.per.common.ResultCodeConstants;
import com.sypay.omp.per.model.page.AjaxJson;
import com.sypay.omp.per.model.page.DataGrid;
import com.sypay.omp.report.VO.ReportConfigVO;
import com.sypay.omp.report.domain.ReportConfig;
import com.sypay.omp.report.service.ReportConfigService;

/**
 * 报表跑批动态配置管理
 * @author lishun
 *
 */
@Controller
@RequestMapping("/reportConfig")
public class ReportConfigController {

	 private final Log logger = LogFactory.getLog(ReportConfigController.class);
	 
	 @Resource
	 private ReportConfigService reportConfigService;
	 /**
     * 跳转到资源列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/reportConfig.htm")
    public String resource() {
        return "reportConfig/reportConfigList";
    }
    
    /**
     * 查找
     * @param reportConfigVO
     * @param pageHelper
     * @return
     */
    @RequestMapping(value = "/findReportConfigList.htm")
    @ResponseBody
    public DataGrid findReportConfigList(ReportConfigVO reportConfigVO, PageHelper pageHelper) {
    	return reportConfigService.findReportConfigList(reportConfigVO, pageHelper);
    }
    
    /**
     * 新增
     * @param reportConfigVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/addReportConfig.htm")
    @ResponseBody
    public AjaxJson addReportConfig(ReportConfigVO reportConfigVO, HttpServletRequest request) {
        AjaxJson json = new AjaxJson();
        if (StringUtils.isBlank(reportConfigVO.getRptCode()) || StringUtils.isBlank(reportConfigVO.getRptSql())) {
        	json.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return json;
        }
        /* 判断是否存在 */
        List<ReportConfig> list = reportConfigService.findReportConfigList(reportConfigVO.getRptCode());
    	if (list.size() > 0) {
    		json.setErrorNo(ResultCodeConstants.RESULT_RESOURCE_ALREADY_EXISTS);
            return json;
    	}
        
        ReportConfig reportConfig = new ReportConfig();
        BeanUtil.copyProperties(reportConfigVO, reportConfig);
        reportConfig.setCreateTime(new Date());
        reportConfig.setUpdateTime(new Date());
        reportConfig.setUpdateRemark("create");
        try {
        	reportConfigService.saveReportConfig(reportConfig);
        } catch (Exception e) {
            json.setStatus(Constants.OpStatus.FAIL);
            json.setErrorInfo("新增失败！");
            System.out.println(e.getCause().toString());
            return json;
        }
        return json;
    }
    /**
     * 修改
     * @param reportConfigVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateReportConfig.htm")
    @ResponseBody
    public AjaxJson updateReportConfig(ReportConfigVO reportConfigVO, HttpServletRequest request) {
        AjaxJson json = new AjaxJson();
        if (StringUtils.isBlank(reportConfigVO.getRptCode()) || StringUtils.isBlank(reportConfigVO.getRptSql())) {
        	json.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return json;
        }
        ReportConfig reportConfig =  reportConfigService.findReportConfigList(reportConfigVO.getRptCode()).get(0);
        reportConfigVO.setCreateTime(reportConfig.getCreateTime());
        BeanUtil.copyProperties(reportConfigVO, reportConfig);
        reportConfig.setUpdateTime(new Date());
        reportConfig.setUpdateRemark("update");
        try {
        	reportConfigService.updateReportConfig(reportConfig);
		} catch (Exception e) {
	            json.setStatus(Constants.OpStatus.FAIL);
	            json.setErrorInfo("更新失败！");
	            return json;
		}
        return json;
    }
    
    /**
     * 删除
     * @param rptCode
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleteReportConfig.htm")
    @ResponseBody
    public AjaxJson deleteReportConfig(String rptCode, HttpServletRequest request) {
        AjaxJson json = new AjaxJson();
        if (StringUtils.isBlank(rptCode)) {
        	json.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return json;
        }
        try {
        	reportConfigService.deleteReportConfig(rptCode);
		} catch (Exception e) {
	            json.setStatus(Constants.OpStatus.FAIL);
	            json.setErrorInfo("删除失败！");
	            return json;
		}
        return json;
    }
}
