package com.sypay.omp.report.web;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.report.common.dal.admin.entity.dto.Member;
import com.report.common.dal.admin.util.SessionUtil;
import com.report.common.dal.report.entity.vo.PagerReq;
import com.report.common.dal.report.entity.vo.SpObserver;
import com.sypay.omp.report.service.ReportService;

/**
 * 导出数据
 * @author lishun
 *
 */
@Controller
@RequestMapping("/exp")
public class ExpCsvController {
	
	private final  Logger log = LoggerFactory.getLogger(ExpCsvController.class);
	
	/*  querylog中专门存放跟报表访问和导出相关的log，可以从该log中分析出报表使用的频率和异常 */
	private final  Logger querylog = LoggerFactory.getLogger("reportQuery");
	
	@Autowired
	ReportService reportService;
	
    /**
     * smartReport导出数据
     * @param PagerReq
     * @return 
     * @throws Exception
     */
    @RequestMapping(value = "smartExpCsv")
    public void smartExpCsv(PagerReq paras, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	Member member = SessionUtil.getLoginInfo();
    	querylog.info("smartExpCsv memberId:{} memberName:{} condition:{} title:{} fileName:{}", member.getId(), member.getName(), paras.getCondition(), paras.getTitle(), paras.getFileName());
    	try {
            Map<String, Object> map = reportService.smartReportExport(paras);
            XSSFWorkbook wb = (XSSFWorkbook) map.get("wb");
            /* 查询数据时切换数据源 */
    		SpObserver.putSp(paras.getDataBaseSource());
            reportService.expReportQueryData((PagerReq)map.get("paras"), (int) map.get("dataIndex"), (XSSFRow)map.get("row"), (XSSFSheet)map.get("sheet"));
            /* 查询完后再切换到主数据源 */
            SpObserver.putSp(SpObserver.defaultDataBase);
            String fileName = paras.getFileName();
            
            response.setHeader("Cache-Control", "private");
            response.setHeader("Pragma", "private");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Type", "application/force-download");
         
            String title = processFileName(request, fileName);
            response.setHeader("Content-disposition", "attachment;filename=" + title + ".xlsx");
            
            OutputStream ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
		} catch (Exception e) {
			querylog.info("smartExpCsv exception {}", e.toString());
			throw e;
		}
    }
    
    
	/**
	 * ie,chrom,firfox下处理文件名显示乱码
	 * @param request
	 * @param fileNames
	 * @return
	 */
    public static String processFileName(HttpServletRequest request, String fileNames) {
        String codedfilename = null;
        try {
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && -1 != agent.indexOf("MSIE") || null != agent && -1 != agent.indexOf("Trident")) {
            	/* ie */
                String name = java.net.URLEncoder.encode(fileNames, "UTF8");
                codedfilename = name;
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
            	/* 火狐,chrome等 */
                codedfilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codedfilename;
    }
}
