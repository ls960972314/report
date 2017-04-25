package com.report.web.query.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.report.biz.admin.service.ReportChartService;
import com.report.biz.admin.service.ReportCommonConService;
import com.report.biz.admin.service.ReportConditionService;
import com.report.biz.admin.service.ReportPublicService;
import com.report.biz.admin.service.ReportService;
import com.report.biz.admin.service.ReportSqlService;
import com.report.common.dal.common.utils.StringUtil;
import com.report.common.dal.common.utils.VerificationUtil;
import com.report.common.dal.query.entity.dto.ReportChart;
import com.report.common.dal.query.entity.dto.ReportCommonCon;
import com.report.common.dal.query.entity.dto.ReportCondition;
import com.report.common.dal.query.entity.dto.ReportPublic;
import com.report.common.dal.query.entity.dto.ReportSql;
import com.report.common.dal.query.entity.vo.PagerReq;
import com.report.common.dal.query.entity.vo.ReportElement;
import com.report.common.dal.query.entity.vo.SpObserver;
import com.report.common.dal.query.util.BeanUtil;
import com.report.common.model.GlobalResultStatus;
import com.report.common.model.JsonResult;
import com.report.common.model.PagerRsp;
import com.report.common.model.SessionUtil;
import com.report.common.model.query.ReturnCondition;
import com.report.common.util.TimeUtil;

import sun.misc.BASE64Decoder;

/**
 * 报表入口
 * @author lishun
 *
 */
@Controller
@RequestMapping("/report")
public class ReportController {
	
    private final  Logger log = LoggerFactory.getLogger(ReportController.class);
    
    /*  querylog中专门存放跟报表访问和导出相关的log，可以从该log中分析出报表使用的频率和异常 */
    private final  Logger querylog = LoggerFactory.getLogger("reportQuery");
    
    @Autowired
    ReportService reportService;

    @Autowired
    private ReportChartService reportChartService;
    
    @Autowired
    private ReportPublicService reportPublicService;
    
    @Autowired
    private ReportConditionService reportConditionService;
    
    @Autowired
    private ReportSqlService reportSqlService;
    
    @Autowired
    private ReportCommonConService reportCommonConService;
    
    @Value("#{propertyConfigurer['imageURL']}")
    private Long IMAGE_URL;
    
    /**
     * smartReport展示时拉取数据
     * @param Model
     * @param PagerReq
     * @return Object
     */
    @RequestMapping(value="reportShowQueryData")
    @ResponseBody
    public Object reportShowQueryData(PagerReq req, HttpServletRequest request) {
    	querylog.info("reportShowQueryData sqlId:{} condition:{}", req.getQid(), req.getCondition());
    	PagerRsp response = new PagerRsp();
    	try {
        	req = reportService.setupSmartReportSql(req);
            req = reportService.updatePagerReq(req);
            /* 查询数据时切换数据源 */
    		SpObserver.putSp(req.getDataBaseSource());
            List list = reportService.showReportQueryData(req);
            response.setRecords(reportService.showReportQueryDataCount(req));
            /* 查询完后再切换到主数据源 */
            SpObserver.putSp(SpObserver.defaultDataBase);
            response.setPage(req.getPage());
            if (list.size() == 0) {
            	response.setRows(null);
            } else
            	response.setRows(list);
            if (response.getRecords() == 0) {
            	response.setTotal(0);
            } else 
            	response.setTotal((int)Math.ceil((double)response.getRecords() / req.getRows()));
		} catch (Exception e) {
			if (e.getCause() == null) {
				querylog.info("reportShowQueryData sqlId:{} condition:{} exception:{}", req.getQid(), req.getCondition(), e.getMessage());
			} else {
				querylog.info("reportShowQueryData sqlId:{} condition:{} exception:{}", req.getQid(), req.getCondition(), e.getCause().toString());
			}
			throw e;
		}
    	return JsonResult.reportSuccess(response, TimeUtil.DATE_FORMAT_2);
    }
    
    /**
     * smartReport创建报表时拉取数据
     * @param model
     * @param req
     * @return
     */
    @RequestMapping(value="reportMakeQueryData")
    @ResponseBody
    public Object reportMakeQueryData(PagerReq req) {
    	log.info("reportMakeQueryData sqlId:{} condition:{}", req.getQid(), req.getCondition());
    	PagerRsp response = new PagerRsp();
    	try {
    		/* 查询数据时切换数据源 */
    		SpObserver.putSp(req.getDataBaseSource());
    		List list = reportService.createReportQueryData(req);
    		/* 查询完后再切换到主数据源 */
            SpObserver.putSp(SpObserver.defaultDataBase);
            response.setPage(1);
            response.setRows(list);
            response.setRecords(list.size());
            response.setTotal(1);
		} catch (Exception e) {
			if (e.getCause() != null) {
				log.info("reportMakeQueryData sqlId:{} condition:{} exception {}", req.getQid(), req.getCondition(), e.getCause().toString());
				return JsonResult.fail(GlobalResultStatus.PARAM_ERROR, e.getCause().toString());
			} else {
				log.info("reportMakeQueryData sqlId:{} condition:{} exception {}", req.getQid(), req.getCondition(), e.getMessage());
				return JsonResult.fail(GlobalResultStatus.PARAM_ERROR, e.getMessage());
			}
		}
        
        return JsonResult.reportSuccess(response, TimeUtil.DATE_FORMAT_2);
    }
    
    /**
     * smartReport将reportFlag相关的图，条件，公共信息返回到页面拼装报表
     * @param model
     * @param reportFlag
     * @return
     */
    @RequestMapping(value="queryAll")
    @ResponseBody
    public Object queryAll(String reportFlag, String conFlag) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
    		List<ReturnCondition> returnConList = new ArrayList<ReturnCondition>();
    		List<ReportCondition> reportConditionList = reportConditionService.queryReportCondition(reportFlag);
			List<ReportCommonCon> commonConList = reportCommonConService.findReportCommonConList(reportFlag, conFlag);
			for (int i=0;i<reportConditionList.size();i++) {
				ReportCondition rptCondition = reportConditionList.get(i);
				ReturnCondition returnCon = new ReturnCondition();
				BeanUtil.copyProperties(rptCondition, returnCon);
				returnCon.setDisplay("Y");
				returnCon.setValue("");
				for (int j=0;j<commonConList.size();j++) {
					ReportCommonCon rptComCon = commonConList.get(j);
					if (rptCondition.getConWhere().equals(rptComCon.getConWhere())) {
						returnCon.setDisplay("N");
						returnCon.setValue(rptComCon.getConValue());
						break;
					}
				}
				returnConList.add(returnCon);
			}
    		ReportPublic reportPublic = reportPublicService.queryReportPublic(reportFlag);
            List<ReportChart> reportChartList = reportChartService.queryReportChart(reportFlag);
            
            map.put("reportPublic", reportPublic);
            map.put("reportChartList", reportChartList);
            map.put("reportConditionList", returnConList);
		} catch (Exception e) {
			log.info("queryAll {} exception {}", reportFlag, e.toString());
		}
        return JsonResult.success(map);
    }
    
    /**
     * smartReport创建报表时保存 公共信息、条件、图
     * @param model
     * @param reportElement
     * @return
     */
    @RequestMapping(value="saveReport")
    @ResponseBody
    public Object saveReport(ReportElement reportElement) {
    	if (StringUtils.isEmpty(reportElement.getSaveReportCColumn()) || StringUtils.isBlank(reportElement.getSaveReportEColumn()) ||
    			StringUtils.isBlank(reportElement.getSaveReportCondition()) || StringUtils.isBlank(reportElement.getSaveReportSqlId()) ||
    			StringUtils.isBlank(reportElement.getSaveReportTitle())) {
    		log.info("saveReport  缺少参数");
    		return JsonResult.fail(GlobalResultStatus.PARAM_MISSING);
    	}
    	ReportPublic reportPublic = null;
    	try {
    		reportPublic = reportPublicService.queryReportPublic(reportElement.getSaveReportFlag());
		} catch (Exception e) {
			log.error("queryReportPublic Exception", e);
			return JsonResult.fail(GlobalResultStatus.UNKNOWN_FAIL);
		}
    	
    	if (reportPublic != null) {
    		log.info("saveReport  报表已存在");
    		return JsonResult.fail(GlobalResultStatus.REPORT_EXIST);
    	}
        
        try {
        	/* 异常回滚，三个对象必须都没问题才能存入 */
        	reportPublicService.saveReport(reportElement);
		} catch (Exception e) {
			log.info("saveReport  未知错误");
			return JsonResult.fail(GlobalResultStatus.UNKNOWN_FAIL);
		}
        
        return JsonResult.success();
    }


    /**
     * smartReport创建报表时根据baseSql生成baseCountSql并保存
     * @param baseSql sql内容
     * @param timeSelect 时间维度
     * @param sqlIds sqlId
     * @param sqlName sql名称
     * @param databaseSource 数据源
     * @return
     */
    @RequestMapping(value="saveReportSql")
    @ResponseBody
    public Object saveReportSql(@RequestParam String baseSql, @RequestParam String timeSelect, @RequestParam String sqlIds,
    		@RequestParam String sqlName, @RequestParam String dataBaseSource) {
    	if (StringUtils.isBlank(baseSql) || StringUtils.isBlank(timeSelect) || StringUtils.isBlank(sqlName)) {
    		log.info("saveReportSql  缺少参数");
    		return JsonResult.fail(GlobalResultStatus.PARAM_MISSING);
    	}
    	
    	if (baseSql.indexOf("select") == -1 || baseSql.indexOf("from") == -1) {
    		log.info("saveReportSql  参数错误");
    		return JsonResult.fail(GlobalResultStatus.PARAM_ERROR);
    	}
    	//关键字转换成小写
    	baseSql = baseSql.replace("SELECT", "select");
    	baseSql = baseSql.replace("WITH", "with");
    	baseSql = baseSql.replace("ORDER", "order");
    	baseSql = baseSql.replace("FROM", "from");
        Long sqlId = null;
        /* 根据sqlIds判断是否已经插入过。没有就新增， 有了就更新 */
        if (sqlIds.indexOf(timeSelect) == -1) {
            sqlId = reportSqlService.saveReportSql(baseSql, sqlName, dataBaseSource);
            log.info("saveReportSql rp_report_sql表中没有存在，返回插入的sql_id");
        } else {
        	/* "月":"1026" */ 
            String str = sqlIds.substring(sqlIds.indexOf(timeSelect)+4);
            sqlId = Long.valueOf(str.substring(0, str.indexOf("\"")));
            ReportSql reportSql = reportSqlService.findReportSqlById(sqlId);
            if (null != reportSql) {
                String baseCountSql = reportSqlService.getCountSQL(baseSql);
                reportSql.setBaseSql(baseSql);
                reportSql.setBaseCountSql(baseCountSql);
                reportSql.setRptname(sqlName);
                reportSqlService.updateReportSql(reportSql);
            }
            log.info("saveReportSql rp_report_sql表中已存在，更新表并返回sql_id");
        }
        
        /* 返回新增/更新的sqlid */
        return JsonResult.success(sqlId);
    }
    
    /**
     * smartReport展示报表获取动态条件(为select或者checkBox时)
     * @param selectSql
     * @return String
     */
    @RequestMapping(value="getConValue")
    @ResponseBody
    public Object getConValue(String selectSql, String dataBaseSource) {
    	if (StringUtils.isBlank(selectSql)) {
    		log.info("getConValue  缺少参数");
    		return JsonResult.fail(GlobalResultStatus.PARAM_MISSING);
    	}
    	String conValue = "";
    	/* 查询数据时切换数据源*/
    	if (StringUtils.isBlank(dataBaseSource)) {
    		SpObserver.putSp(SpObserver.defaultDataBase);
    	} else {
    		SpObserver.putSp(dataBaseSource);
    	}
    	try {
    		conValue = reportService.getConValue(selectSql);
    		log.info("getConValue selectSql: {} result: {}", selectSql, conValue);
		} catch (Exception e) {
			log.info("getConValue selectSql: {} exception: {}", selectSql, e.toString());
		}
    	/* 查询完后再切换到主数据源*/
        SpObserver.putSp(SpObserver.defaultDataBase); 
        return JsonResult.success(conValue);
    }
    
    @RequestMapping(value="saveChartImage")
    @ResponseBody
    public Object saveChartImage(@RequestParam String url,@RequestParam String index, HttpServletRequest request) {
    	String imagePath = SessionUtil.getUserInfo().getImagePath();
    	/* 得到项目根路径 */
    	String rootPath = getImgUrl();
    	if (VerificationUtil.paramIsNull(url, index)) {
    		log.info("username[{}] saveChartImage  缺少参数", SessionUtil.getUserInfo().getMember().getAccNo());
    		return JsonResult.fail(GlobalResultStatus.PARAM_MISSING);
    	}
    	
    	try {
    		String []imgurl = url.split(",");	
    		String u = imgurl[1];
	    	byte[] b = new BASE64Decoder().decodeBuffer(u);
	    	OutputStream out = new FileOutputStream(rootPath +  new File(StringUtil.combinationString(imagePath, index, ".png")));
	        out.write(b);
	        out.flush();
	        out.close();
	        log.info("保存图表成功，保存路径为:rootPath[{}]", rootPath);
	        return JsonResult.success(GlobalResultStatus.SUCCESS);
    	}catch (Exception e) {
            e.printStackTrace();
			log.info("保存图表失败，未知错误,保存路径为:"+rootPath);
			return JsonResult.fail(GlobalResultStatus.UNKNOWN_FAIL);
        }
    }
    
    @RequestMapping(value = "/smartReport", method = RequestMethod.GET)
    public String main(HttpServletRequest request) {
        return "reportPage/smartReport";
    }
    
    /**
	 * 得到图片存放临时地址
	 * @return
	 */
	private String getImgUrl() {
		return reportSqlService.findReportSqlById(IMAGE_URL).getBaseSql();
	}

    
}
