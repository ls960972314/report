package com.sypay.omp.report.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.admin.entity.dto.Member;
import com.report.common.dal.admin.util.SessionUtil;
import com.sypay.omp.report.domain.Progress;
import com.sypay.omp.report.domain.ReportLog;
import com.sypay.omp.report.json.JsonResult;
import com.sypay.omp.report.service.ReportImportOneService;
import com.sypay.omp.report.service.ReportLogService;
import com.sypay.omp.report.statuscode.GlobalResultStatus;
import com.sypay.omp.report.util.JDBCUtils;
@Controller
@RequestMapping("/impExcelFile")
public class ImpExcelController {
	private final Log logger = LogFactory.getLog(FileUpLoad.class);
	@Value("#{propertyConfigurer['jdbc_driverClassName']}")
	private String DB_DRIVER;
	@Value("#{propertyConfigurer['jdbc_url']}")
	private String DB_CONNECTION;
	@Value("#{propertyConfigurer['jdbc_username']}")
	private String DB_USER;
	@Value("#{propertyConfigurer['jdbc_password']}")
	private String DB_PASSWORD;
	

	@Resource
	private ReportImportOneService reportImportOneService;
	
	@Resource
	private ReportLogService reportLogService;
	
	@RequestMapping(value = "/uploadProgress", method = RequestMethod.POST )
	@ResponseBody
	public Object initCreateInfo(HttpServletRequest request, HttpServletResponse response) {
		Long memberId = (Long) request.getSession().getAttribute(Constants.SESSION_LOGIN_MEMBER_ID);
		Progress status = JSON.parseObject((String) request.getSession().getAttribute(memberId + "upload_ps"), Progress.class);
		if(status==null) {
			return "{}";
		}
		if (status.getMaxId() == null) {
			status.setMaxId(reportImportOneService.getRptMaxId());
		}
		status.setImportNum(reportImportOneService.getRptImportCount(status.getMaxId()));
		return JsonResult.success(status);
	}
	
	/**
	 * 运营导入文件     上传/重新上传操作
	 * @param upPath  批次号
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public Object upload2(@RequestParam String batchNo, HttpServletRequest request, HttpServletResponse response)
	{
		Member member = SessionUtil.getLoginInfo();
		Progress status = JSON.parseObject((String) request.getSession().getAttribute(member.getId() + "upload_ps"), Progress.class);
		try {
		ReportLog rptLog = new ReportLog();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String createTime = df.format(new Date());
		
		/* 有该batchNo的数量 */
		int batchNoCount = reportImportOneService.getBatchNoCount(batchNo);
		/* 得到项目根路径 */
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		// 创建一个通用的多部分解析器
		CustomMultipartResolver multipartResolver = new CustomMultipartResolver(
				request.getSession().getServletContext());
		long finaltime = 0;
		long pre = 0;
		int sum = 0;
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 记录上传过程起始时的时间，用来计算上传时间
				pre =  System.currentTimeMillis();
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					/* 如果有该批次号的先删除 */
					if (batchNoCount > 0) {
						reportImportOneService.deleteReportConfig(batchNo);
						rptLog.setOpeAction("重新上传");
					} else {
						rptLog.setOpeAction("上传");
					}
					logger.info(DB_DRIVER+','+DB_CONNECTION+','+DB_USER+','+DB_PASSWORD);
					JDBCUtils jdbc = new JDBCUtils( DB_DRIVER,  DB_CONNECTION,  DB_USER,  DB_PASSWORD);
					sum = jdbc.batchInsert(file,
							"insert into RPT_IMPORT_ONE (ID, BATCH_NO, CREATE_TIME, CFT_ORDER_ID, TRAN_TIME, TYPE, TRAN_TARGET, NAME, IN_MONEY, OUT_MONEY, LEFT_MONEY, TRAN_INFO, MCHT_ORDER_ID) values (SEQ_RPT_IMPORT_ONE.NEXTVAL, '"+ batchNo + "' , '"+ createTime +"', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
							5000, 1);
				}
				// 记录上传该文件后的时间
				finaltime  =  System.currentTimeMillis();
				logger.info("上传文件耗时：" + (finaltime - pre));
			}

		}
		status = JSON.parseObject((String) request.getSession().getAttribute(member.getId() + "upload_ps"), Progress.class);
		
		/* 存储日志 */
		rptLog.setCreateTime(df.parse(createTime));
		rptLog.setOpeId(batchNo);
		rptLog.setUserName(member.getName());
		rptLog.setWasteTime(String.valueOf(finaltime - pre));
		rptLog.setException(String.valueOf(sum));
		reportLogService.saveReportLog(rptLog);
		} catch (Exception e) {
			logger.info("upload Exception" , e);
			return JsonResult.fail(GlobalResultStatus.ERROR);
		}
		return JsonResult.success(status);
	}
}
