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
import com.report.facade.entity.dto.ReportPublic;
import com.report.facade.entity.query.PublicVO;
import com.report.facade.service.ReportPublicService;

/**
 * 图形管理
 * @author lishun
 *
 */
@Controller
@RequestMapping("/public")
public class ReportPublicController {

	 private final Log logger = LogFactory.getLog(ReportPublicController.class);
	 
	 @Resource
	 private ReportPublicService reportPublicService;
	 /**
     * 跳转到资源列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/public.htm")
    public String resource() {
        return "public/publicList";
    }
    
    @RequestMapping(value = "/findPublicList.htm")
    @ResponseBody
    public DataGrid findPublicList(PublicVO publicVo, PageHelper pageHelper) {
    	return reportPublicService.findPublicList(publicVo, pageHelper);
    }
    
    @RequestMapping(value = "/updatePublic.htm")
    @ResponseBody
    public AjaxJson updatePublic(PublicVO publicVo, HttpServletRequest request) {
        AjaxJson json = new AjaxJson();
        if (publicVo.getId() == null || StringUtils.isBlank(publicVo.getToolFlag())) {
        	json.setErrorNo(ResultCodeConstants.RESULT_INCOMPLETE);
            return json;
        }
        ReportPublic reportpublic = new ReportPublic();
        BeanUtil.copyProperties(publicVo, reportpublic);
        try {
        	reportPublicService.updatePublic(reportpublic);
		} catch (Exception e) {
	            json.setStatus(Constants.FAIL);
	            json.setErrorInfo("更新失败！");
	            return json;
		}
        return json;
    }
}
