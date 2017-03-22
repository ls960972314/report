package com.sypay.omp.report.service.impl;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.report.common.dal.common.BaseDao;
import com.report.common.dal.report.entity.vo.Condition;
import com.report.common.dal.report.entity.vo.PagerReq;
import com.report.common.dal.report.entity.vo.SpObserver;
import com.sypay.omp.report.VO.FormatVO;
import com.sypay.omp.report.VO.ModelElements;
import com.sypay.omp.report.VO.ReportModelVO;
import com.sypay.omp.report.domain.ReportChart;
import com.sypay.omp.report.domain.ReportCondition;
import com.sypay.omp.report.domain.ReportModel;
import com.sypay.omp.report.domain.ReportPublic;
import com.sypay.omp.report.domain.ReportPublishConfig;
import com.sypay.omp.report.json.JsonResult;
import com.sypay.omp.report.service.ReportChartService;
import com.sypay.omp.report.service.ReportConditionService;
import com.sypay.omp.report.service.ReportModelService;
import com.sypay.omp.report.service.ReportPublicService;
import com.sypay.omp.report.service.ReportService;
import com.sypay.omp.report.service.ReportSqlService;
import com.sypay.omp.report.util.DateCommonUtils;
import com.sypay.omp.report.util.TimeUtil;


/**
 * 
 * @author lishun
 *
 * @2015年5月7日
 */
@Transactional
@Service(value="reportModelService")
public class ReportModelServiceImpl implements ReportModelService {

    protected final Log log = LogFactory.getLog(ReportModelServiceImpl.class);
    
    @Autowired
    private BaseDao baseDao;
    
    @Autowired
    private ReportPublicService reportPublicService;
    
    @Autowired
    private ReportConditionService reportConditionService;
    
    @Autowired
    private ReportChartService reportChartService;
    
    @Autowired
    private ReportService reportService;
    
    @Autowired
    private ReportSqlService reportSqlService;
    
    @Value("#{propertyConfigurer['host']}")
    private Long HOST;
    
    @Value("#{propertyConfigurer['userName']}")
    private Long USER_NAME;
    
    @Value("#{propertyConfigurer['userPassword']}")
    private Long USER_PASSWORD;
    
    @Value("#{propertyConfigurer['imageURL']}")
    private Long IMAGE_URL;
    
    @Value("#{propertyConfigurer['from']}")
    private Long FROM;
    
    @Value("#{propertyConfigurer['port']}")
    private Long PORT;
    
    //报表图片存放位置
    //private static String imgPath = "D:\\workSpace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\sypay-report-webapp\\";
    
	@Override
	public ReportModelVO queryReportModelByModelId(String modelName) {
		ReportModelVO reportModelVO = new ReportModelVO();
		List<ModelElements> modelElementsList = new ArrayList<ModelElements>();
		// 模板实体类List
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("modelName", modelName);
        ReportModel reportModel = (ReportModel) baseDao.get("from ReportModel where modelName = :modelName", paramMap);
        if (reportModel != null) {
        	Map<String, Object> paramMap1 = new HashMap<String, Object>();
            paramMap1.put("modelId", reportModel.getId());
            List<ReportPublishConfig> reportPublishConfiglist = baseDao.find("from ReportPublishConfig where modelId = :modelId order by id", paramMap1);
    		// 循环遍历模板实体类List，封装reportModelVO
    		for (int i=0; i < reportPublishConfiglist.size(); i++) {
    			boolean haveBeginTime = false;
    			boolean haveEndTime = false;
    			ReportPublic reportPublic = new ReportPublic();
    			List<ReportChart> reportChartList = new ArrayList<ReportChart>();
    			ReportPublishConfig reportPublishConfig = reportPublishConfiglist.get(i);
    			ModelElements modelElements = new ModelElements();
    			modelElements.setRptTitle(reportPublishConfig.getRptTitle());
    			modelElements.setRptComment(reportPublishConfig.getRptComment());
    			modelElements.setTimeDimension(reportPublishConfig.getReportTime());
    			// 报表标志
    			String reportFlag = reportPublishConfig.getToolFlag();
    			
    			// 模型条件   开始时间,结束时间
    			String modelCon = reportModel.getConname();
    			List<String> modelConList = Arrays.asList(modelCon.split(","));
    			// 模板条件名  开始时间,结束时间,其他类型
    			String modelConName = reportPublishConfig.getModelConName();
    			List<String> modelConNameList = Arrays.asList(modelConName.split(","));
    			// 条件ID  10041,10042
    			String reportConId = reportPublishConfig.getRptConId();
    			List<String> reportConIdList = Arrays.asList(reportConId.split(","));
    			// 隐含条件名
    			String defaultValue = reportPublishConfig.getDefaultValue();
    			List<String> defaultValueList = new ArrayList<String>();
    			if (StringUtils.isNotBlank(defaultValue)) {
    				defaultValueList = Arrays.asList(defaultValue.split(","));
    			}
    			
    			List<String> sameConList = new ArrayList<String>();
    			List<String> selfList = new ArrayList<String>();
    			boolean sameFlag = false;
    			
    			for (int j=0;j<modelConNameList.size();j++) {
    				String mc = modelConNameList.get(j);
    				sameFlag = false;
    				for (String mcn : modelConList) {
    					if (mc.equals(mcn)) {
    						sameFlag = true;
    						break;
    					}
    				}
    				if (sameFlag) {
    					sameConList.add(reportConIdList.get(j));
    				} else {
    					selfList.add(reportConIdList.get(j));
    				}
    			}
    			
    			// 组装ReportCondition
    			List<ReportCondition> reportConditionList = reportConditionService.queryReportCondition(reportFlag);
    			List<ReportCondition> sfList = new ArrayList<ReportCondition>();
    			List<ReportCondition> pubList = new ArrayList<ReportCondition>();
    			
    			for (ReportCondition rc : reportConditionList) {
    				for (int k = 0; k < selfList.size(); k++) {
    					String conId = selfList.get(k);
    					if (rc.getId() == Integer.parseInt(conId)) {
    						// 报表私有的条件要给默认值
    						rc.setConDefaultValue(defaultValueList.get(k));
    						sfList.add(rc);
    					}
    				}
    				for (String conId: sameConList) {
    					if (rc.getId() == Integer.parseInt(conId)) {
    						pubList.add(rc);
    					}
    				}
    				if (rc.getConName().indexOf("开始") != -1 || rc.getConWhere().indexOf("begin") != -1) {
    					haveBeginTime = true;
    				}
    				if (rc.getConName().indexOf("结束") != -1 || rc.getConWhere().indexOf("end") != -1) {
    					haveEndTime = true;
    				}
    			}
    			
    			// 设置报表自己独有的（隐藏的）条件
    			modelElements.setReportConditionList(sfList);
    			// 设置模板公共的条件
    			if (reportModelVO.getReportConditionList() == null || pubList.size() > reportModelVO.getReportConditionList().size()) {
    				reportModelVO.setReportConditionList(pubList);
    			}
    			
    			// 数据源ID
    			String sqlId = reportPublishConfig.getSqlId();
    			// 组装数据源
    			modelElements.setQid(Long.parseLong(sqlId.trim()));
    			// 是否显示图形
    			boolean chartShow = reportPublishConfig.getChartShow().equals("Y")?true:false;
    			modelElements.setChartShow(chartShow);
    			
    			// 是否显示表格
    			boolean tableShow = reportPublishConfig.getTableShow().equals("Y")?true:false;
    			modelElements.setTableShow(tableShow);
    			// 组装ReportPublic
    			modelElements.setReportPublic(reportPublicService.queryReportPublic(reportFlag));
    			// 组装ReportChart
    			reportChartList = reportChartService.queryReportChart(reportFlag);
    			if (reportChartList != null && reportChartList.size() >= 1) {
    				// 0815展示选择的图形
    				for (int k = 0; k < reportChartList.size(); k++) {
        				if (reportPublishConfig.getChartId() != null && reportPublishConfig.getChartId().equals(reportChartList.get(k).getId()) ) {
        					modelElements.setReportChart(reportChartList.get(k));
        				}
    				}
    				if (modelElements.getReportChart() == null) {
    					modelElements.setReportChart(reportChartList.get(0));
    				}
    			}
    			if (!haveBeginTime && haveEndTime) {
    				modelElements.setEndTimeOnly("Y");
    			}
    			if (haveBeginTime && !haveEndTime) {
    				modelElements.setBeginTimeOnly("Y");
    			}
    			modelElementsList.add(modelElements);
    		}
    		reportModelVO.setModelElementsList(modelElementsList);
    		reportModelVO.setModelName(reportModel.getModelName());
    		reportModelVO.setModelTitle(reportModel.getModelTitle());
    		reportModelVO.setReceiveAdd(reportModel.getSendUsernames());
    		return reportModelVO;
        }
		return null;
	}
	
	@Override
	public boolean sendMail(ReportModelVO reportModelVO,String condition,String rootPath,String titleGS,String contentGS,String mNameGS,String ReceiveAdd, String selfConList,String loginName) throws Exception{
		
		rootPath = getImgUrl();
		
		boolean result = false;
		
		Properties prop = new Properties();
        prop.setProperty("mail.host", getHost());
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.smtp.port", getPort());
        
        //创建session
        Session session = Session.getInstance(prop);
        
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        
        //通过session得到transport对象
        Transport ts = session.getTransport();
        
        //连上邮件服务器，需要发件人提供邮箱的用户名和密码进行验证
       // ts.connect("hqmail.sf.com", "854470", "2835547520sZjjjj");
        log.info(String.format("0523发送邮件，邮箱服务器:[%s],发件人账号:[%s],密码:[%s]", getHost(), getUserName(), getPassWd()));
        try{
        	//创建邮件
        	Message message = createImageMail(session,reportModelVO,condition,rootPath,titleGS,contentGS,mNameGS,ReceiveAdd,selfConList,loginName);
        	ts.connect(getHost(), getUserName(), getPassWd());
        	//发送邮件
        	ts.sendMessage(message, message.getAllRecipients());
        	ts.close();
        	
        }catch(Exception e){
        	log.info("创建、发送邮件异常",e);
        	//调用删除报表图片方法
            deleteimage(reportModelVO,rootPath,loginName);
        	return result;
        }
        
        //调用删除报表图片方法
        deleteimage(reportModelVO,rootPath,loginName);
		return result = true;
	}
	
	public  MimeMessage createImageMail(Session session,ReportModelVO reportModelVO,String condition,String imagePath,String titleGS,String contentGS,String mNameGS,String ReceiveAdd,String selfConList,String loginName) throws Exception{

		log.info("存储的报表图片路径为:"+imagePath);
		Date dates = new Date();
		@SuppressWarnings("deprecation")
		String date = (dates.getYear()+1900)+ "年"+(dates.getMonth()+1)+"月"+dates.getDate()+"日";
		String to[] = ReceiveAdd.split(";");
		List<FormatVO> mNameFormat = JSON.parseArray(mNameGS, FormatVO.class);
		List<FormatVO> titleFormat = JSON.parseArray(titleGS, FormatVO.class);
		List<FormatVO> contentFormat = JSON.parseArray(contentGS, FormatVO.class);
		List<String> selfCons = JSON.parseArray(selfConList, String.class);
		PagerReq req = new PagerReq();
		req.setCondition(condition);
    	req.setPage(1);
    	req.setRows(1000);
    	
    	//正文标题字体大小以及字体加粗
    	String fontSize1 = mNameFormat.get(0).getFontSize();
    	String frontWeight1 = mNameFormat.get(0).getFontWeight();
    	
    	//报表说明字体大小以及字体是否加粗
    	String fontSize2 = "22";
    	String frontWeight2 = "bold";
    	
    	//设置标题和说明字体格式
    	StringBuffer h1 = new StringBuffer();
    	StringBuffer title1 = new StringBuffer();
    	h1.append("h1{font-size:").append(fontSize1).append(";font-weight:").append(frontWeight1).append(";}");
		title1.append(" .title1 {	font-size:").append(fontSize2).append(";font-weight:").append(frontWeight2).append(";} 	</style>   <body>");
    	//邮件标题
    	String title = mNameFormat.get(0).getText();
    	
    	//收件人
    	//to[]={"zhanghuanmin@sf-express.com","lishun1@sf-express.com","sunzhijia2@sf-express.com"}; 
    	String toList = getMailList(to);  
    	log.info(String.format("0523收件人邮箱:[%s]", toList));
        InternetAddress[] iaToList = new InternetAddress().parse(toList); 
    	
		// 创建邮件
		MimeMessage message = new MimeMessage(session);

		// 设置邮件的基本信息
		// 发件人
		log.info(String.format("0523发件人邮箱:[%s]", getFrom()));
		message.setFrom(new InternetAddress(getFrom()));

		// 设置收件人
//		message.setRecipient(Message.RecipientType.TO, new InternetAddress("lishun1@sf-express.com"));
		message.setRecipients(Message.RecipientType.TO, iaToList);
		
		// 设置邮件标题
		message.setSubject(title);
		
		//报表表格List
		List<ModelElements> modelElementsList = reportModelVO.getModelElementsList();
		
		// 准备邮件数据
		// 准备邮件正文数据
		MimeBodyPart text = new MimeBodyPart();
		StringBuffer content = new StringBuffer();
		// 准备图片数据
		List<MimeBodyPart> images = new ArrayList<MimeBodyPart>();
		
		//html-head
		 String htmlhead = "<html> <head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />   <title>test</title>    </head>     <style type='text/css'>       body {    font: normal 11px auto 'Trebuchet MS', Verdana, Arial, Helvetica, sans-serif;    color: #00000;      }    a{ color:#333; text-decoration:none;} a:hover{ color:#F00; text-decoration:underline;} a:active{ color:#30F;}    .mytable {    width: 700px;    padding: 0;    margin: 0;    }      caption {    padding: 0 0 5px 0;    			   width: 700px;    			   font: italic 11px 'Trebuchet MS', Verdana, Arial, Helvetica, sans-serif;   			   text-align: right;    }       	th {    font: bold 11px 'Trebuchet MS', Verdana, Arial, Helvetica, sans-serif;    background: #E6EAE9;			color: #4f6b72;    	border-left: 1px solid #C1DAD7;		border-right: 1px solid #C1DAD7;    			border-bottom: 1px solid #C1DAD7;    			border-top: 1px solid #C1DAD7;    			letter-spacing: 2px;    			text-transform: uppercase;    			text-align: left;    			padding: 6px 6px 6px 12px;    b			ackground: #CAE8EA  no-repeat;    }  				th.nobg {    border-top: 0;   				 border-left: 0;   				 border-right: 1px solid #C1DAD7;  				 background: none;    }       	td {  border-left: 1px solid #C1DAD7;  border-right: 1px solid #C1DAD7;    			border-bottom: 1px solid #C1DAD7;   			background: #fff;    			font-size:11px;   			padding: 6px 6px 6px 12px;    			color: #4f6b72;    }  	td.alt {    background: #F5FAFA;    color: #797268;    }       	th.spec {    border-left: 1px solid #C1DAD7;   				 border-top: 0;    				 background: #fff no-repeat;    				 font: bold 10px 'Trebuchet MS', Verdana, Arial, Helvetica, sans-serif;    }       	th.specalt {    border-left: 1px solid #C1DAD7;    					border-top: 0;    					background: #f5fafa no-repeat;    					font: bold 10px 'Trebuchet MS', Verdana, Arial, Helvetica, sans-serif;    					color: #797268;    }	html>body td{ font-size:11px;}    	body,td,th {    font-family: 微软雅黑, Arial;    font-size: 12px;    }   "
				           + h1.toString() + title1.toString();
		 
		 //html-end
		 String htmlend = "<div style='text-align:left;font-size: 14;font-weight:bold;margin-top: 15'>数据仅为内部指定范围知悉，禁止转发或外传！</div><div style='text-align:left;font-size: 14;font-weight:bold;margin-top: 5;'>其他数据查询可点击<a href='http://rs.sf-pay.com/report/toLogin.htm'>“报表平台”</a></div><div style='text-align:left;font-size: 18;font-weight:bold;margin-top: 15;'>运营支持处</div><div style='text-align:left;font-size: 18;font-weight:bold;'>"+date+"</div></body>   </html> ";
		 
		 //邮件正文标题
		 String mailTitle = "<h1 style='text-align:center'>"+title+"</h1>";
		 
		int index = 0;
		int n=0;
		for(ModelElements me : modelElementsList) {
			
			MimeBodyPart image = new MimeBodyPart();
			//设置报表描述
			content.append("<div style='font-size:"+titleFormat.get(index).getFontSize()+"; font-weight:"+titleFormat.get(index).getFontWeight()+"; margin-top: 10px;margin-bottom: 10px'>").append(titleFormat.get(index).getText()).append("</div>");
			content.append("<div style='font-size:"+contentFormat.get(index).getFontSize()+"; font-weight:"+contentFormat.get(index).getFontWeight()+"; margin-top: 10px;margin-bottom: 10px; word-wrap: break-word; word-break: break-all;width:100% '>").append(contentFormat.get(index).getText()).append("</div>");
			//设置报表图片
			if(me.isChartShow()) {
				content.append("<div style='text-align:center; margin-top: 10px;margin-bottom: 10px'><div><img src='cid:"+index+".png'>").append("</div></div>");
//				content.append("<h4>图表为:</h4><img src='cid:" + index + ".png'>");
				DataHandler dh = new DataHandler(new FileDataSource(imagePath+ loginName + index+".png"));
				image.setDataHandler(dh);
				image.setContentID(index+".png");
				images.add(n, image);
				n++;
			}
			
			if(me.isTableShow()) {
				req.setCondition(condition);
				// 转换时间维度
				if (me.getTimeDimension().equals("w")) {
					List<Condition> conList = JSON.parseArray(req.getCondition(), Condition.class);
					for (int i = 0; i < conList.size(); i++) {
						if (conList.get(i).getName().indexOf("endtime") != -1 || conList.get(i).getName().indexOf("end_time") != -1 || conList.get(i).getName().indexOf("endTime") != -1) {
							conList.get(i).setValue(DateCommonUtils.getWeekEndDay(conList.get(i).getValue()));
						} else if (conList.get(i).getName().indexOf("begintime") != -1 || conList.get(i).getName().indexOf("begin_time") != -1 || conList.get(i).getName().indexOf("beginTime	") != -1) {
							conList.get(i).setValue(DateCommonUtils.getWeekBeginDay(conList.get(i).getValue()));
						}
					}
					req.setCondition(JSON.toJSONString(conList));
				} else if (me.getTimeDimension().equals("m")) {
					List<Condition> conList = JSON.parseArray(req.getCondition(), Condition.class);
					for (int i = 0; i < conList.size(); i++) {
						if (conList.get(i).getName().indexOf("endtime") != -1 || conList.get(i).getName().indexOf("end_time") != -1 || conList.get(i).getName().indexOf("endTime") != -1) {
							conList.get(i).setValue(DateCommonUtils.getMonthDate(conList.get(i).getValue()));
						} else if (conList.get(i).getName().indexOf("begintime") != -1 || conList.get(i).getName().indexOf("begin_time") != -1 || conList.get(i).getName().indexOf("beginTime	") != -1) {
							conList.get(i).setValue(DateCommonUtils.getMonthDate(conList.get(i).getValue()));
						}
					}
					req.setCondition(JSON.toJSONString(conList));
				}
				
				if (null != selfCons.get(index) && !selfCons.get(index).equals("")) {
					String newCon = req.getCondition().substring(0, req.getCondition().length()-1)+",";
					newCon = newCon + selfCons.get(index).substring(1, selfCons.get(index).length()-1) + "]";
					req.setCondition(newCon);
				}
				
				if (me.getBeginTimeOnly() != null && me.getBeginTimeOnly().equals("Y")) {
					List<Condition> conList = JSON.parseArray(req.getCondition(), Condition.class);
					for (int i = 0; i < conList.size(); i++) {
						if (conList.get(i).getName().indexOf("endtime") != -1 || conList.get(i).getName().indexOf("end_time") != -1 || conList.get(i).getName().indexOf("endTime") != -1) {
							conList.remove(conList.get(i));
						}
					}
					req.setCondition(JSON.toJSONString(conList));
				}
				
				if (me.getEndTimeOnly() != null && me.getEndTimeOnly().equals("Y")) {
					List<Condition> conList = JSON.parseArray(req.getCondition(), Condition.class);
					for (int i = 0; i < conList.size(); i++) {
						if (conList.get(i).getName().indexOf("begintime") != -1 || conList.get(i).getName().indexOf("begin_time") != -1 || conList.get(i).getName().indexOf("beginTime	") != -1) {
							conList.remove(conList.get(i));
						}
					}
					req.setCondition(JSON.toJSONString(conList));
				}
				
				content.append("<div style='text-align:center; margin-top: 10px;margin-bottom: 10px'><div><table class='mytable' cellspacing='0' style='margin:0 auto'> ");
				req.setQid(me.getQid());
				req = reportService.setupSmartReportSql(req);
		        req = reportService.updatePagerReq(req);
		        /* 查询数据时切换数据源 */
		        SpObserver.putSp(req.getDataBaseSource());
		        List table = reportService.getMutiReportQueryData(req);
		        /* 查询完后再切换到主数据源 */
	            SpObserver.putSp(SpObserver.defaultDataBase);
		        String tabltHeader = me.getReportPublic().getToolCColumn();
		        // 设置表头，分一级、二级表头
		        String[] excelHeader = tabltHeader.split(",");
		        List<String> hbrow = new ArrayList<String>();
				List<String> oneHeader = new ArrayList<String>();
				List<String> twoHeader = new ArrayList<String>();
				List<String> header = Arrays.asList(excelHeader);
				List<String> excelTitle = new ArrayList<String>();
				int hbnum = 0;
				for (int i=0; i<header.size(); i++) {
					String tmpString = header.get(i);
					if (tmpString.indexOf(":") != -1) {
						/* 添加到twoHeader中的column */
						String[] tmpStr1 = tmpString.substring(tmpString.indexOf("{")+1,tmpString.indexOf("}")).split("\\|");
						hbrow.add(hbnum + "-" + (tmpStr1.length+hbnum-1));
						oneHeader.add(tmpString.substring(0 , tmpString.indexOf(":")));
						for (int j=0; j<tmpStr1.length-1; j++) {
							
						}
						twoHeader.addAll(Arrays.asList(tmpStr1));
						excelTitle.addAll(Arrays.asList(tmpStr1));
						hbnum = hbnum + tmpStr1.length;
					} else {
						hbrow.add("0");
						oneHeader.add(tmpString);
						excelTitle.add(tmpString);
						hbnum++;	
					}
				}
				for (int i=0; i<hbrow.size(); i++) {
					String str = hbrow.get(i);
					if (str.indexOf("-") != -1) {
						String strs[] = str.split("-");
						hbrow.set(i, String.valueOf(Integer.parseInt(strs[1]) - Integer.parseInt(strs[0]) +1));
					}
				}
				
				content.append("<tr>");
				//hbrow["0","2","0","3"] oneHeader["日期","二级表头1","呵呵","二级表头2"] twoHeader ["金额","笔数","a","b","c"]
				if(twoHeader.size() != 0){
					for(int i=0; i< hbrow.size(); i++){
						if("0".equals(hbrow.get(i))){
							content.append("<th style='text-align:center;' rowspan='2'>"+oneHeader.get(i)+"</th>");
						}
						else{
							content.append("<th style='text-align:center;' colspan='" + hbrow.get(i) + "'>" + oneHeader.get(i) + "</th>");
						}
					}
				}else{
					for(int i=0; i< hbrow.size(); i++){
						if("0".equals(hbrow.get(i))){
							content.append("<th style='text-align:center;' rowspan='1'>"+oneHeader.get(i)+"</th>");
						}
						else{
							content.append("<th style='text-align:center;' colspan='" + hbrow.get(i) + "'>" + oneHeader.get(i) + "</th>");
						}
					}
				}
				content.append("</tr>");
				
				if(twoHeader.size() != 0){
					content.append("<tr>");
					for(int i =0; i < twoHeader.size(); i++){
						content.append("<th style='text-align:center;'>" + twoHeader.get(i) + "</th>");
					}
					content.append("</tr>");
				}
		        //格式化  当日总笔数:正整数,成功总笔数:正整数
				Map<Integer, String> formatMap = new HashMap<>();
				String toolFormat = me.getReportPublic().getToolFormat();
				if (StringUtils.isNotBlank(toolFormat)) {
					String[] toolFormats = toolFormat.split(",");
					for (int i = 0; i < toolFormats.length; i++) {
						String key = toolFormats[i].substring(0, toolFormats[i].indexOf(":"));
						if (excelTitle.indexOf(key) != -1) {
							formatMap.put(excelTitle.indexOf(key), toolFormats[i].substring(toolFormats[i].indexOf(":")+1));
						}
					}
				}
				
				//设置数据
				for (Object obj : table) {
					content.append("<tr>");
		            String str = JSON.toJSONString(JSONObject.parseArray(JsonResult.reportSuccess(obj, TimeUtil.DATE_FORMAT_2), String.class));
		            //去掉[]
		            String substr = str.substring(1, str.length()-1);
		            String[] datas = substr.split(",");
		            for(int i = 0; i < datas.length; i++) {
		            	String data = datas[i];
		            	content.append("<td>");
		            	//去掉""
		            	String subdata = data.substring(1, data.length()-1);
		            	
		            	subdata = formatData(subdata, formatMap, i);
		            	content.append(subdata);
		            	content.append("</td>");
		            }
					content.append("</tr>");
 		        }
				
				content.append("</table></div></div>");
				
			}
			
			index++;
			
		}

		log.info(htmlhead+mailTitle+content.toString()+htmlend);
		text.setContent(htmlhead+mailTitle+content.toString()+htmlend,"text/html;charset=UTF-8");
		

		// 描述数据关系
		MimeMultipart mm = new MimeMultipart();
		
		mm.addBodyPart(text);
		for(MimeBodyPart mbp : images){
			mm.addBodyPart(mbp);
		}
		mm.setSubType("related");
		
		message.setContent(mm);
		message.saveChanges();
		// 将创建好的邮件写入到E盘以文件的形式进行保存
		//message.writeTo(new FileOutputStream("E:\\ImageMail.eml"));
		// 返回创建好的邮件
		return message;
	}
	
	private String formatData(String cell, Map<Integer, String> formatMap, Integer index) {
		if (StringUtils.isNotBlank(formatMap.get(index))) {
			if (formatMap.get(index).equals("正整数")) {
				return fmtMicrometer(cell);
			} else if (formatMap.get(index).equals("百分比")) {
				return cell + "%";
			} else if (formatMap.get(index).equals("负数")) {
				return "-" + cell;
			} else if (formatMap.get(index).equals("小数")) {
				return fmtMicrometer(cell);
			}
		}
		return cell;
	}
	private String fmtMicrometer(String text) {
		DecimalFormat df = null;
		if (text.indexOf(".") > 0) {
			if (text.length() - text.indexOf(".") - 1 == 0) {
				df = new DecimalFormat("###,##0.");
			} else if (text.length() - text.indexOf(".") - 1 == 1) {
				df = new DecimalFormat("###,##0.0");
			} else {
				df = new DecimalFormat("###,##0.00");
			}
		} else {
			df = new DecimalFormat("###,##0");
		}
		double number = 0.0;
		try {
			number = Double.parseDouble(text);
		} catch (Exception e) {
			number = 0.0;
		}
		return df.format(number);
	}

	@Override
	public void saveReportModel(ReportModel reportModel) throws Exception{
		try {
			baseDao.save(reportModel);
		} catch (Exception e) {
			log.error("sql exception", e);
			throw e;
		}
		
	}


	@Override
	public void saveReportPublishConfig(ReportPublishConfig reportPublishConfig)  throws Exception{
		try {
			baseDao.save(reportPublishConfig);
		} catch (Exception e) {
			log.error("sql exception", e);
			throw e;
		}
	}


	@Override
	public List<ReportModel> queryReportModelList(String flag) throws Exception{
		List<ReportModel> list = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("saveType", flag);
			list = baseDao.find("from ReportModel where saveType ='all' or saveType = :saveType", paramMap);
		} catch (Exception e) {
			log.error("db error", e);
			throw e;
		}
		return list;
	}


	@Override
	public ReportModel queryReportModelByModelId(Long id) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        ReportModel reportModel = null;
        try {
        	reportModel = (ReportModel) baseDao.get("from ReportModel where id = :id", paramMap);
		} catch (Exception e) {
			log.error("db error", e);
			throw e;
		}
        return reportModel;
	}


	@Override
	public List<ReportPublishConfig> queryReportPublishConfigListByModelId(Long modelId) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("modelId", modelId);
        List<ReportPublishConfig> list = null;
        try {
        	list = baseDao.find("from ReportPublishConfig where modelId = :modelId order by id", paramMap);
		} catch (Exception e) {
			log.error("db error", e);
			throw e;
		}
        return list;
	}


	@Override
	public void updateReportModel(ReportModel reportModel) throws Exception {
		try {
			baseDao.update(reportModel);
		} catch (Exception e) {
			log.error("db error", e);
			throw e;
		}
	}


	@Override
	public void updateReportPublishConfig(ReportPublishConfig reportPublishConfig) throws Exception {
		try {
			baseDao.update(reportPublishConfig);
		} catch (Exception e) {
			log.error("db error", e);
			throw e;
		}
	}


	@Override
	public void deleteAllReportPublishConfigByModelId(Long modelId) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("modelId", modelId);
        try {
        	baseDao.executeHql("delete ReportPublishConfig where modelId = :modelId", paramMap);
		} catch (Exception e) {
			log.error("db error", e);
			throw e;
		}
	}


	@Override
	@Transactional
	public void deleteModelDetailByModelId(Long modelId) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("modelId", modelId);
        try {
        	baseDao.executeHql("delete ReportPublishConfig where modelId = :modelId", paramMap);
        	baseDao.executeHql("delete ReportModel where id = :modelId", paramMap);
		} catch (Exception e) {
			log.error("db error", e);
			throw e;
		}
	}


	
	//转换收件人格式
	public String getMailList(String[] mailArray){  
        
        StringBuffer toList = new StringBuffer();  
        int length = mailArray.length;  
        if(mailArray!=null && length <2){  
             toList.append(mailArray[0]);  
        }else{  
             for(int i=0;i<length;i++){  
                     toList.append(mailArray[i]);  
                     if(i!=(length-1)){  
                         toList.append(",");  
                     }  
             }  
         }  
        return toList.toString();  
	}
	
	public void deleteimage(ReportModelVO reportModelVO,String imagepath,String loginName){
		//报表表格List
      	List<ModelElements> modelElementsList = reportModelVO.getModelElementsList();
    	for(int i = 0; i < modelElementsList.size(); i++){
    		if(modelElementsList.get(i).isChartShow()){
    			String filename = loginName+i+".png";
    			if(deleteFile(filename, imagepath)){
    				log.info("删除"+loginName+i+".png图片成功");
    			}else{
    				log.error("删除"+loginName+i+".png图片失败");
    			}
    		}
    	}
	}
	
	public boolean deleteFile(String filename,String path){
		String filePath = path  + filename;
		File file = new File(filePath);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		} else {
			file.delete();
			return true;
		}
	}
	/**
	 * 得到图片存放临时地址
	 * @return
	 */
	private String getImgUrl() {
		return reportSqlService.findReportSqlById(IMAGE_URL).getBaseSql();
	}
	/**
	 * 得到用户名
	 * @return
	 */
	private String getUserName() {
		return reportSqlService.findReportSqlById(USER_NAME).getBaseSql();
	}
	/**
	 * 得到Passwd
	 * @return
	 */
	private String getPassWd() {
		return reportSqlService.findReportSqlById(USER_PASSWORD).getBaseSql();
	}
	/**
	 * 得到host
	 * @return
	 */
	private String getHost() {
		return reportSqlService.findReportSqlById(HOST).getBaseSql();
	}
	/**
	 * 得到发件人
	 * @return
	 */
	private String getFrom() {
		return reportSqlService.findReportSqlById(FROM).getBaseSql();
	}
	/**
	 * 得到邮箱端口
	 * @return
	 */
	private String getPort() {
		return reportSqlService.findReportSqlById(PORT).getBaseSql();
	}
	
	
}
