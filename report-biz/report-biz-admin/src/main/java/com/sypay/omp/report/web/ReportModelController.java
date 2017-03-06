package com.sypay.omp.report.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sypay.omp.per.common.Constants;
import com.sypay.omp.report.VO.ModelUpdateDetail;
import com.sypay.omp.report.VO.ReportModelVO;
import com.sypay.omp.report.VO.ReportPublishVO;
import com.sypay.omp.report.domain.ReportCommonCon;
import com.sypay.omp.report.domain.ReportCondition;
import com.sypay.omp.report.domain.ReportModel;
import com.sypay.omp.report.domain.ReportPublic;
import com.sypay.omp.report.domain.ReportPublishConfig;
import com.sypay.omp.report.json.JsonResult;
import com.sypay.omp.report.service.ReportCommonConService;
import com.sypay.omp.report.service.ReportConditionService;
import com.sypay.omp.report.service.ReportModelService;
import com.sypay.omp.report.service.ReportPublicService;
import com.sypay.omp.report.statuscode.GlobalResultStatus;
import com.sypay.omp.report.util.StringUtil;
/**
 * 报表模板发送类Controller
 * @author 887961
 *
 */
@Controller
@RequestMapping("/reportModel")
public class ReportModelController {

	private final  Logger log = LoggerFactory.getLogger(ReportController.class);
	
	@Autowired
	private ReportModelService reportModelService;
	@Autowired
	private ReportConditionService reportConditionService;
	@Autowired
	private ReportPublicService reportPublicService;
	@Autowired
	private ReportCommonConService reportCommonConService;
	
	/**
	 * 查询ReportModel列表
	 * @return
	 */
	@RequestMapping(value = "queryModelList")
    @ResponseBody
    public Object queryModelList(String flag) {
		List<ReportModel> modelList = new ArrayList<ReportModel>();
		try {
			modelList = reportModelService.queryReportModelList(flag);
		} catch (Exception e) {
			log.error("queryModelList err", e);
			return JsonResult.fail(GlobalResultStatus.UNKNOWN_FAIL, e.getCause().toString());
		}
		log.info(JSON.toJSONString(modelList, SerializerFeature.DisableCircularReferenceDetect));
		if (modelList != null) {
			return JsonResult.success(JSON.toJSONString(modelList, SerializerFeature.DisableCircularReferenceDetect));
		} else {
			return JsonResult.fail(GlobalResultStatus.PARAM_ERROR);
		}
    }
	
	/**
	 * 模板发送页面-查询模板明细
	 * @param modelName
	 * @return
	 */
	@RequestMapping(value = "queryModelDetail")
    @ResponseBody
    public Object queryModelDetail(String modelName) {
		ReportModelVO reportModelVO = new ReportModelVO();
		try {
			reportModelVO = reportModelService.queryReportModelByModelId(modelName);
		} catch (Exception e) {
			log.error("queryModelDetail err", e);
			return JsonResult.fail(GlobalResultStatus.UNKNOWN_FAIL, e.getCause().toString());
		}
		log.info(JSON.toJSONString(reportModelVO, SerializerFeature.DisableCircularReferenceDetect));
		if (reportModelVO != null) {
			return JsonResult.success(JSON.toJSONString(reportModelVO, SerializerFeature.DisableCircularReferenceDetect));
		} else {
			return JsonResult.fail(GlobalResultStatus.PARAM_ERROR);
		}
    }
	
	@RequestMapping(value = "sendMail")
    @ResponseBody
    public Object sendMail(String reportModelVO,String condition,HttpServletRequest request,String titleGS,String contentGS,String mNameGS,String ReceiveAdd, String selfConList){
		String loginName = "";
		try{
			loginName = (String)request.getSession().getAttribute("mailImgName");
		}catch (Exception e) {
			log.error(String.format("发送邮件时从请求中取用户登录名异常:[%s]", e));
		}
		/* 得到项目根路径 */
		String rootPath = "";
		//rootPath.replaceAll("\\\\", "\\\\\\\\");
		
		ReportModelVO reportModelVO1 = JSON.parseObject(reportModelVO, ReportModelVO.class);
		boolean result = true;
		try{
			result = reportModelService.sendMail(reportModelVO1, condition,rootPath,titleGS,contentGS,mNameGS,ReceiveAdd,selfConList,loginName);
		}catch(Exception e){
			log.error("sendMail error", e);
			return JsonResult.fail(GlobalResultStatus.UNKNOWN_FAIL, e.getCause().toString());
		}
		if(result){
			return JsonResult.success(GlobalResultStatus.SUCCESS,"发送邮件失败");
		}else{
			return JsonResult.fail(GlobalResultStatus.ERROR,"发送邮件失败");
		}
	}
	
	/**
	 * 模板保存页面-保存模板
	 * @param bgName 模板名称
	 * @param bgTitle 模板标题
	 * @param modelCon 查询条件 （开始条件,结束条件）
	 * @param emailAddrs 邮箱地址
	 * @param modelRptList 报表配置Json字符串
	 * @return
	 */
	@RequestMapping(value = "saveModelDetail")
    @ResponseBody
    public Object saveModelDetail(String saveOrUpdate, Long modelId, String saveType, String bgName, String bgTitle, String modelCon, String emailAddrs, String modelRptList) {
		List<ReportPublishVO> reportPublishList = new ArrayList<ReportPublishVO>();
		
		/* 保存报告 */
		ReportModel reportModel = new ReportModel();
		reportModel.setConname(modelCon);
		reportModel.setCreateTime(new Date());
		reportModel.setModelName(bgName);
		reportModel.setModelTitle(bgTitle);
		reportModel.setSendUsernames(emailAddrs);
		reportModel.setSaveType(saveType);
		try {
			/* 保存模板 */
			if (saveOrUpdate.equals("save")) {
				reportModelService.saveReportModel(reportModel);
			} else if(saveOrUpdate.equals("update")) {/* 修改模板 */
				reportModel.setId(modelId);
				reportModelService.updateReportModel(reportModel);
			}
			
		} catch (Exception e) {
			log.error("saveReportModel or updateReportModel error", e);
			return JsonResult.fail(GlobalResultStatus.ERROR);
		}
		/* 保存报表配置List */
		reportPublishList = JSON.parseArray(modelRptList, ReportPublishVO.class);
		try {
			if (saveOrUpdate.equals("update")) {
				reportModelService.deleteAllReportPublishConfigByModelId(modelId);
			}
			for (ReportPublishVO rptPublish: reportPublishList) {
				String toolFlag = rptPublish.getToolFlag();
				/* 组装reportConId */
				List<ReportCondition> conList = reportConditionService.queryReportCondition(toolFlag);
				String rptConIdBegin = "";
				String rptConIdEnd = "";
				String rptConNameBegin = "";
				String rptConNameEnd = "";
				Map<String, String> selfConIdMap = new HashMap<String, String>();
				Map<String, String> selfConNameMap = new HashMap<String, String>();
				for (ReportCondition rc : conList) {
					if (rc.getConName().indexOf("开始") != -1 || rc.getConWhere().indexOf("begintime") != -1 || rc.getConWhere().indexOf("begin_time") != -1 || rc.getConWhere().indexOf("beginTime") != -1) {
						rptConIdBegin = String.valueOf(rc.getId());
						rptConNameBegin = "开始时间";
					}
					if (rc.getConName().indexOf("结束") != -1 || rc.getConWhere().indexOf("endtime") != -1 || rc.getConWhere().indexOf("end_time") != -1 || rc.getConWhere().indexOf("endTime") != -1) {
						rptConIdEnd = String.valueOf(rc.getId());
						rptConNameEnd = "结束时间";
					}
					selfConIdMap.put(rc.getConWhere(), String.valueOf(rc.getId()));
					selfConNameMap.put(rc.getConWhere(), rc.getConName());
				}
				/* D类报表隐藏条件处理*/
				String conFlag = rptPublish.getConFlag();
				String selfConIds = "";
				String selfConNames = "";
				String selfConValues = "";
				if (StringUtils.isNotBlank(conFlag)) {
					List<ReportCommonCon> rptComConList = reportCommonConService.findReportCommonConList(toolFlag, conFlag);
					for (ReportCommonCon rc : rptComConList) {
						selfConIds = selfConIdMap.get(rc.getConWhere()) + ",";
						selfConNames = selfConNameMap.get(rc.getConWhere())+ ",";
						selfConValues = rc.getConValue() + ",";
					}
				}
				if (StringUtils.isNotEmpty(selfConIds)) {
					selfConIds = selfConIds.substring(0, selfConIds.length()-1);
				}
				if (StringUtils.isNotEmpty(selfConNames)) {
					selfConNames = selfConNames.substring(0, selfConNames.length()-1);
				}
				if (StringUtils.isNotEmpty(selfConValues)) {
					selfConValues = selfConValues.substring(0, selfConValues.length()-1);
				}
				
				
				
				/* 查找SQLID */
				ReportPublic reportPublic = reportPublicService.queryReportPublic(toolFlag);
				String sqlId = "";
				if (rptPublish.getRptTime().equals("d")) {
					sqlId = reportPublic.getToolDSqlId();
				} else if (rptPublish.getRptTime().equals("w")) {
					sqlId = reportPublic.getToolWSqlId();
				} else if (rptPublish.getRptTime().equals("m")) {
					sqlId = reportPublic.getToolMSqlId();
				}
				ReportPublishConfig reportPublishConfig = new ReportPublishConfig();
				reportPublishConfig.setChartShow(rptPublish.getChartShow());
				reportPublishConfig.setTableShow(rptPublish.getTableShow());
				if (rptPublish.getChartShow().equals("Y")) {
					reportPublishConfig.setChartId(rptPublish.getChartId());
					reportPublishConfig.setChartName(rptPublish.getChartName());
				}
				if (StringUtils.isNotEmpty(selfConNames)) {
					reportPublishConfig.setModelConName((StringUtil.isEmpty(rptConNameBegin)? "":(rptConNameBegin  + ",")) + rptConNameEnd + "," + selfConNames);
				} else {
					reportPublishConfig.setModelConName((StringUtil.isEmpty(rptConNameBegin)? "":(rptConNameBegin  + ",")) + rptConNameEnd);
				}
				if (StringUtils.isNotEmpty(selfConIds)) {
					reportPublishConfig.setRptConId(  (StringUtil.isEmpty(rptConIdBegin)? "":(rptConIdBegin  + ",")) + rptConIdEnd + "," + selfConIds);
				} else {
					reportPublishConfig.setRptConId((StringUtil.isEmpty(rptConIdBegin)? "":(rptConIdBegin  + ",")) + rptConIdEnd);
				}
				reportPublishConfig.setDefaultValue(selfConValues);
				
				reportPublishConfig.setRptComment(rptPublish.getRptContent());
				reportPublishConfig.setSqlId(sqlId);
				reportPublishConfig.setToolFlag(toolFlag);
				reportPublishConfig.setModelId(reportModel.getId());
				reportPublishConfig.setReportName(rptPublish.getReportName());
				reportPublishConfig.setReportTime(rptPublish.getRptTime());
				reportPublishConfig.setRptTitle(rptPublish.getRptTitle());
				
				
				/* 保存报表信息(修改时先删除完再添加) */
				reportModelService.saveReportPublishConfig(reportPublishConfig);
			}
		} catch (Exception e) {
			log.error("saveModelDetail or updateReportPublishConfig error", e);
			return JsonResult.fail(GlobalResultStatus.ERROR);
		}
		return JsonResult.success();
    }
	
	/**
	 * 修改、删除报表模板时查询出详细信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "queryUpdateModelDetail")
    @ResponseBody
    public Object queryUpdateModelDetail(Long id) {
		List<ReportPublishVO> reportPublishList = new ArrayList<ReportPublishVO>();
		ReportModel reportModel = new ReportModel();
		ModelUpdateDetail modelUpdateDetail = new ModelUpdateDetail();
		
		List<ReportPublishConfig> reportPublishConfigList = new ArrayList<ReportPublishConfig>();
		try {
			reportModel = reportModelService.queryReportModelByModelId(id);
			reportPublishConfigList = reportModelService.queryReportPublishConfigListByModelId(id);
		} catch (Exception e) {
			log.error("queryUpdateModelDetail err", e);
			return JsonResult.fail(GlobalResultStatus.ERROR);
		}
		for (ReportPublishConfig rpc : reportPublishConfigList) {
			ReportPublishVO reportPublishVO = new ReportPublishVO();
			reportPublishVO.setId(String.valueOf(rpc.getId()));
			reportPublishVO.setChartShow(rpc.getChartShow());
			reportPublishVO.setReportName(rpc.getReportName());
			reportPublishVO.setRptContent(rpc.getRptComment());
			reportPublishVO.setRptTime(rpc.getReportTime());
			reportPublishVO.setTableShow(rpc.getTableShow());
			reportPublishVO.setToolFlag(rpc.getToolFlag());
			reportPublishVO.setRptTitle(rpc.getRptTitle());
			reportPublishVO.setConFlag("");
			reportPublishVO.setChartId(rpc.getChartId());
			reportPublishVO.setChartName(rpc.getChartName());
			reportPublishList.add(reportPublishVO);
		}
		
		modelUpdateDetail.setReportModel(reportModel);
		modelUpdateDetail.setReportPublishList(reportPublishList);
		return JsonResult.success(modelUpdateDetail);
	}
    
    /**
	 * 删除报表模板及报表信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteModelDetail")
    @ResponseBody
    public Object deleteModelDetail(Long modelId) {
		try {
			reportModelService.deleteModelDetailByModelId(modelId);
		} catch (Exception e) {
			log.error("deleteModelDetail error", e);
			return JsonResult.fail(GlobalResultStatus.ERROR);
		}
		return JsonResult.success();
	}
	
}
