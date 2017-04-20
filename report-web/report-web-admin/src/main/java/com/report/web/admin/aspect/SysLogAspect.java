package com.report.web.admin.aspect;

import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.report.biz.admin.service.ReportLogService;
import com.report.common.dal.admin.entity.dto.ReportLog;
import com.report.common.model.SessionUtil;
import com.report.facade.entity.query.ChartVO;
import com.report.facade.entity.query.ConditionVO;
import com.report.facade.entity.query.PublicVO;
import com.report.facade.entity.query.ReportCommonConVO;
import com.report.facade.entity.query.ReportConfigVO;
import com.report.facade.entity.query.ReportModelVO;
import com.report.facade.entity.query.ReportSqlVO;
import com.report.facade.entity.vo.PagerReq;
import com.report.facade.entity.vo.ReportElement;
import com.report.facade.entity.vo.SpObserver;

import lombok.extern.slf4j.Slf4j;

/**
 * 基于系统controller和service注解的aop切入点
 * 
 * @author lishun
 * @since 2017年4月19日 下午7:58:42
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {
	
	@Resource
	private ReportLogService reportLogService;

	@Pointcut("within(@org.springframework.stereotype.Service *)")
	public void cutService() {
	}

	@Pointcut("within(@org.springframework.stereotype.Controller *)")
	public void cutController() {
	}

	@Around("cutService()")
	public Object recordServiceLog(ProceedingJoinPoint point) throws Throwable {
		String strMethodName = point.getSignature().getName();
		String strClassName = point.getTarget().getClass().getName();
		log.info("methodName={},className={}", strMethodName, strClassName);
		Object[] params = point.getArgs();
		StringBuffer bfParams = new StringBuffer();
		Enumeration<String> paraNames = null;
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		MDC.put("ip", IPUtil.getIpAddress(request));
		if (params != null && params.length > 0) {
			paraNames = request.getParameterNames();
			String key;
			String value;
			while (paraNames.hasMoreElements()) {
				key = paraNames.nextElement();
				value = request.getParameter(key);
				bfParams.append(key).append("=").append(value).append("&");
			}
			if (StringUtils.isBlank(bfParams)) {
				bfParams.append(request.getQueryString());
			}
		}
		log.info("SysLogAspect recordServiceLog className={},methodName={},params=[{}]", strClassName, strMethodName,
				bfParams.toString());
		return point.proceed();
	}

    
	@Around("cutController()")
	public Object recordControllerLog(ProceedingJoinPoint point) throws Throwable {
		String strMethodName = point.getSignature().getName();
		String strClassName = point.getTarget().getClass().getName();
		log.info("methodName={},className={}", strMethodName, strClassName);
		Object[] params = point.getArgs();

		StringBuffer bfParams = new StringBuffer();
		Enumeration<String> paraNames = null;
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		MDC.put("ip", IPUtil.getIpAddress(request));
		if (params != null && params.length > 0) {
			paraNames = request.getParameterNames();
			String key;
			String value;
			while (paraNames.hasMoreElements()) {
				key = paraNames.nextElement();
				value = request.getParameter(key);
				bfParams.append(key).append("=").append(value).append("&");
			}
			if (StringUtils.isBlank(bfParams)) {
				bfParams.append(request.getQueryString());
			}
		}
		log.info("SysLogAspect recordControllerLog className={},methodName={},params=[{}]", strClassName, strMethodName,
				bfParams.toString());
    	long time = System.currentTimeMillis();
        Object retVal = point.proceed(); 
        time = System.currentTimeMillis() - time;
        switch (strMethodName) {
	    	/* 查询报表 */
			case "reportShowQueryData":
			/* 新增报表时查询报表 */
			case "reportMakeQueryData":
			/* 导出报表 */
			case "smartExpCsv":
			/* 新增报表 */
			case "saveReport":
			/* 更新图形 */
			case "updateChart":
			/* 更新条件 */
			case "updateCondition":
			/* 更新公共信息 */
			case "updatePublic":
			/* 新增sql */
			case "addReportSql":
			/* 更新sql */
			case "updateReportSql":
			/* 新增批量条件 */
			case "addCommonCondition":
			/* 更新批量条件 */
			case "updateCommonCondition":
			/* 新增报表配置 */
			case "addReportConfig":
			/* 更新报表配置 */
			case "updateReportConfig":
			/* 删除报表配置 */
			case "deleteReportConfig" :
			/* 发送模板邮件 */
			case "sendMail" :
			/* 查询文本 */
			case "getComment" :
			/* 登陆 */
			case "doLogin" :
				operateLog(IPUtil.getIpAddress(request), point.getSignature().getName(), params, time, null);
				break;
			default:
				break;
		}
        return retVal;
	}
	
	@AfterThrowing(throwing="ex", pointcut="cutController()")
    public void afterThrowing(JoinPoint jp, Throwable ex) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
    	operateLog(IPUtil.getIpAddress(request), jp.getSignature().getName(), jp.getArgs(), 0,ex);
    }
	
	private void operateLog(String ip, String operate, Object[] args, long wasteTime, Throwable throwable) {
		try {
			String operateId = "";
			ReportLog rptLog = new ReportLog();
			if (throwable != null) {
				if (throwable.getCause() != null) {
					rptLog.setException(throwable.getCause().toString());
				} else {
					rptLog.setException(throwable.getMessage());
					if (StringUtils.isBlank(throwable.getMessage())) {
						rptLog.setException(JSON.toJSONString(throwable).length() > 4000 ? JSON.toJSONString(throwable).substring(0, 1000) : JSON.toJSONString(throwable));
					}
				}
			}
			rptLog.setIp(ip);
			rptLog.setUserName(SessionUtil.getUserInfo() == null ? null : SessionUtil.getUserInfo().getMember().getAccNo());
			rptLog.setWasteTime(String.valueOf(wasteTime));
			rptLog.setOpeAction(operate);
			if ((operate.equals("reportShowQueryData") || operate.equals("reportMakeQueryData")
					|| operate.equals("smartExpCsv"))) {
				if (args[0] != null) {
					PagerReq req = (PagerReq)args[0];
					operateId = String.valueOf(req.getQid());
					rptLog.setOpeId(operateId);
				}
			} else if (operate.equals("saveReport")) {
				if (args[0] != null) {
					ReportElement reportElement = (ReportElement)args[0];
					operateId = reportElement.getSaveReportFlag();
					rptLog.setOpeId(operateId);
				}
			} else if (operate.equals("updateChart")) {
				if (args[0] != null) {
					ChartVO chartVO = (ChartVO)args[0];
					operateId = chartVO.getToolFlag();
					rptLog.setOpeId(operateId);
				}
			} else if (operate.equals("updateCondition")) {
				if (args[0] != null) {
					ConditionVO conditionVO = (ConditionVO)args[0];
					operateId = conditionVO.getToolFlag();
					rptLog.setOpeId(operateId);
				}
			} else if (operate.equals("updatePublic")) {
				if (args[0] != null) {
					PublicVO publicVO = (PublicVO)args[0];
					operateId = publicVO.getToolFlag();
					rptLog.setOpeId(operateId);
				}
			} else if (operate.equals("addReportSql") || operate.equals("updateReportSql")) {
				if (args[0] != null) {
					ReportSqlVO reportSqlVO = (ReportSqlVO)args[0];
					operateId = String.valueOf(reportSqlVO.getSqlId());
					rptLog.setOpeId(operateId);
				}
			} else if (operate.equals("addCommonCondition") || operate.equals("updateCommonCondition")) {
				if (args[0] != null) {
					ReportCommonConVO reportCommonConVO = (ReportCommonConVO)args[0];
					operateId = String.valueOf(reportCommonConVO.getToolFlag());
					rptLog.setOpeId(operateId);
				}
			}  else if (operate.equals("addReportConfig") || operate.equals("updateReportConfig")) {
				if (args[0] != null) {
					ReportConfigVO reportConfigVO = (ReportConfigVO)args[0];
					operateId = String.valueOf(reportConfigVO.getRptCode());
					rptLog.setOpeId(operateId);	
				}
			} else if (operate.equals("deleteReportConfig")) {
				if (args[0] != null) {
					operateId = String.valueOf(args[0]);
					rptLog.setOpeId(operateId);	
				}
			} else if (operate.equals("sendMail")) {
				if (args[0] != null) {
					ReportModelVO reportModelVO = JSON.parseObject(String.valueOf(args[0]), ReportModelVO.class);;
					rptLog.setOpeId(reportModelVO.getModelName());	
				}
			} else if (operate.equals("getComment")) {
				rptLog.setOpeId(null != args[0] ? String.valueOf(args[0]) : "");	
			} else if (operate.equals("doLogin")) {
				rptLog.setOpeId(null != args[0] ? String.valueOf(args[0]) : "");	
			}
			SpObserver.putSp(SpObserver.defaultDataBase);
			reportLogService.addLog(rptLog);
		} catch (Exception e) {
			log.error("operateLog Exception", e);
		}
	}
}
