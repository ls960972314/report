package com.sypay.omp.report.web;

import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sypay.omp.per.domain.Member;
import com.sypay.omp.per.util.SessionUtil;
import com.sypay.omp.report.dataBase.SpObserver;
import com.sypay.omp.report.queryrule.PagerReq;
import com.sypay.omp.report.queryrule.PagerRsp;
import com.sypay.omp.report.service.ReportService;
import com.sypay.omp.report.util.StringUtil;

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
    
    
    /**
	 * 正常报表导出数据
	 * @param PagerReq
	 * @return 
	 * @throws Exception
	 */
    @Deprecated
    @RequestMapping(value = "expCsv")
    public void exportExcel(PagerReq paras, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	Member member = SessionUtil.getLoginInfo();
    	querylog.info("expCsv memberId:{} memberName:{} condition:{} title:{} fileName:{}", member.getId(), member.getName(), paras.getCondition(), paras.getTitle(), paras.getFileName());
    	try {
    		HSSFWorkbook wb = export(paras);
            String fileName = paras.getFileName();
            response.setHeader("Cache-Control", "private");
            response.setHeader("Pragma", "private");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Type", "application/force-download");
       
            String title = processFileName(request, fileName);
            response.setHeader("Content-disposition", "attachment;filename=" + title + ".xls");
            
            
            OutputStream ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
		} catch (Exception e) {
			querylog.info("expCsv exception {}", e.toString());
		}
    }
    @Deprecated
    public HSSFWorkbook export(PagerReq paras) throws Exception {
        paras.setFilters(paras.getFilters());
        paras.setPage(1);
        paras.setRows(10000);
        PagerRsp rsp = reportService.getReportData(paras);
        String title = paras.getTitle();
        String[] excelHeader = {};
        if (!"".equals(title) && title != null) {
            excelHeader = title.split(",");
        }

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Campaign");
        HSSFRow row = sheet.createRow((int) 0);
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        for (int i = 0; i < excelHeader.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(excelHeader[i]);
            cell.setCellStyle(style);
            sheet.autoSizeColumn(i);
        }
        List<Object[]> rows = rsp.getRows();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //execl行数
        int i = 0;
        if (rows != null) {
            for (Object[] list : rows) {
                row = sheet.createRow(i + 1);
                for (int j = 0; j < list.length; j++) {
                    if (list[j] instanceof Timestamp) {
                        list[j] = df.format((Timestamp) list[j]);
                    }
                    String cel = list[j]==null?"":list[j].toString();
                    row.createCell(j).setCellValue(cel);
                }
                i++;
            }
        }
        
        
        int pages = rsp.getTotal();
        for (int j = 1; j < pages; j++) {
            paras.setPage(j + 1);
            rsp = reportService.getReportData(paras);
            rows = rsp.getRows();
            if (rows != null) {
                for (Object[] list : rows) {
                    row = sheet.createRow(i + 1);
                    for (int k = 0; k < list.length; k++) {
                        if (list[k] instanceof Timestamp) {
                            list[k] = df.format((Timestamp) list[k]);
                        }
                        String cel = list[k]==null?"":list[k].toString();
                        row.createCell(k).setCellValue(cel);
                    }
                    i++;
                }
            }
        }
        
        
        
        return wb;
    }
}
