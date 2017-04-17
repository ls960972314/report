package com.report.web.query.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.report.biz.admin.service.ReportCommentService;
import com.report.common.dal.common.utils.VerificationUtil;
import com.report.common.model.AjaxJson;
import com.report.common.model.GlobalResultStatus;
import com.report.common.model.JsonResult;
import com.report.common.model.ReportCommentVO;
import com.report.facade.entity.PageHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * 报表文本管理
 * 
 * @author lishun
 * @since 2017年4月15日 下午5:41:01
 */
@Slf4j
@Controller
@RequestMapping("/reportComment")
public class ReportCommentController {

	@Resource
	private ReportCommentService reportCommentService;

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "createReportComment")
	public ModelAndView index() {
		ModelAndView view = new ModelAndView();
		view.setViewName("reportComment/createReportComment");
		return view;
	}
	/**
	 * 保存报表文本
	 * @param reportCommentVO
	 * @param pageHelper
	 * @return
	 */
	@RequestMapping(value = "/saveComment")
	@ResponseBody
	public Object saveComment(ReportCommentVO reportCommentVO, PageHelper pageHelper) {
		log.debug("saveComment ReportCommentVO[{}]", JSON.toJSONString(reportCommentVO));
		if (VerificationUtil.paramIsNull(reportCommentVO, reportCommentVO.getToolFlag())) {
			log.debug("getComment param is null");
			return JsonResult.fail(GlobalResultStatus.PARAM_ERROR);
		}
		AjaxJson result = new AjaxJson();
		ReportCommentVO vo = null;
		try {
			vo = reportCommentService.findReportComment(reportCommentVO.getToolFlag());
		} catch (Exception e) {
			log.error("findReportComment Exception", e);
			return JsonResult.fail(GlobalResultStatus.ERROR);
		}
		if (null == vo) {
			reportCommentService.insert(reportCommentVO);
		} else {
			reportCommentService.update(reportCommentVO);
		}
		return result;
	}
	
	/**
	 * 根据报表标志得到comment
	 * @param toolFlag
	 * @param pageHelper
	 * @return
	 */
	@RequestMapping(value = "/getComment")
	@ResponseBody
	public Object getComment(String toolFlag) {
		log.debug("getComment toolFlag[{}]", toolFlag);
		if (StringUtils.isBlank(toolFlag)) {
			log.debug("getComment toolFlag is null");
			return JsonResult.fail(GlobalResultStatus.PARAM_ERROR);
		}
		ReportCommentVO vo = null;
		try {
			vo = reportCommentService.findReportComment(toolFlag);
		} catch (Exception e) {
			log.error("findReportComment Exception", e);
			return JsonResult.fail(GlobalResultStatus.ERROR);
		}
		return JsonResult.success(vo.getComment());
	}

}
