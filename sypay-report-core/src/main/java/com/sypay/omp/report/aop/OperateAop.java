package com.sypay.omp.report.aop;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.sfpay.framework2.core.util.StringUtil;
import com.sypay.omp.per.domain.Member;
import com.sypay.omp.per.util.SessionUtil;
import com.sypay.omp.report.VO.ChartVO;
import com.sypay.omp.report.VO.ConditionVO;
import com.sypay.omp.report.VO.PublicVO;
import com.sypay.omp.report.VO.ReportCommonConVO;
import com.sypay.omp.report.VO.ReportConfigVO;
import com.sypay.omp.report.VO.ReportModelVO;
import com.sypay.omp.report.VO.ReportSqlVO;
import com.sypay.omp.report.dataBase.SpObserver;
import com.sypay.omp.report.domain.ReportElement;
import com.sypay.omp.report.domain.ReportLog;
import com.sypay.omp.report.queryrule.PagerReq;
import com.sypay.omp.report.service.ReportLogService;
/**
 * AOP 主要将对报表的查询，导出，报表配置的增删改查记录插入数据库
 *     查询导出异常的记录也插入数据库
 * @author lishun
 *
 */
public class OperateAop {
	@Autowired
	private ReportLogService reportLogService;
	private void operateLog(String operate, Object[] args, long wasteTime, Throwable throwable) {
		Member member = SessionUtil.getLoginInfo();
		String operateId;
		ReportLog rptLog = new ReportLog();
		rptLog.setCreateTime(new Date());
		if (throwable!= null) {
			if (throwable.getCause() != null) {
				rptLog.setException(throwable.getCause().toString());
			} else {
				rptLog.setException(throwable.getMessage());
				if (StringUtil.isNullOrEmpty(throwable.getMessage())) {
					rptLog.setException(JSON.toJSONString(throwable).length() > 4000 ? JSON.toJSONString(throwable).substring(0, 1000) : JSON.toJSONString(throwable));
				}
			}
		}
		rptLog.setUserName(member.getName());
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
		}
		SpObserver.putSp(SpObserver.defaultDataBase);
		reportLogService.saveReportLog(rptLog);
	}
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    	
    	Object[] args = pjp.getArgs();
    	long time = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        time = System.currentTimeMillis() - time;
        switch (pjp.getSignature().getName()) {
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
			operateLog(pjp.getSignature().getName(), args, time, null);
			break;
		default:
			break;
		}
        return retVal;
    }  
  
    /**
     * 异常处理
     * @param jp
     * @param ex
     */
    public void doThrowing(JoinPoint jp, Throwable ex) {
    	operateLog(jp.getSignature().getName(), jp.getArgs(), 0,ex);
    }
}
