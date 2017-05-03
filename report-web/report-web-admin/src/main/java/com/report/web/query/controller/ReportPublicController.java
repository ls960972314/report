package com.report.web.query.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.report.biz.admin.service.ReportPublicService;
import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.common.utils.VerificationUtil;
import com.report.common.model.AjaxJson;
import com.report.common.model.DataGrid;
import com.report.common.model.PageHelper;
import com.report.common.model.ResultCodeConstants;
import com.report.common.model.query.ReportPublicVO;

/**
 * 报表公共信息管理
 * @author lishun
 * @since 2017年5月3日 下午8:25:13
 */
@Controller
@RequestMapping("/public")
public class ReportPublicController {

	@Resource
	private ReportPublicService reportPublicService;
	
    @RequestMapping(value = "/public.htm")
    public String resource() {
        return "public/publicList";
    }
    
    @RequestMapping(value = "/findPublicList.htm")
    @ResponseBody
    public DataGrid findPublicList(ReportPublicVO publicVo, PageHelper pageHelper) {
    	return reportPublicService.findPublicList(publicVo, pageHelper);
    }
    
    @RequestMapping(value = "/updatePublic.htm")
    @ResponseBody
    public AjaxJson updatePublic(ReportPublicVO reportPublicVO, HttpServletRequest request) {
        AjaxJson json = new AjaxJson();
        
        if (VerificationUtil.paramIsNull(reportPublicVO, reportPublicVO.getId(),reportPublicVO.getToolFlag() )) {
        	json.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return json;
        }
        
        try {
        	reportPublicService.updateReportPublic(reportPublicVO);
		} catch (Exception e) {
            json.setStatus(Constants.FAIL);
            json.setErrorInfo("更新报表公共信息失败！");
		}
        
        return json;
    }
}
