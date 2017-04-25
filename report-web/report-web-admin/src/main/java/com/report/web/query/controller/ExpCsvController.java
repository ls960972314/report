package com.report.web.query.controller;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.report.biz.admin.service.ReportService;
import com.report.common.dal.query.entity.vo.PagerReq;
import com.report.common.dal.query.entity.vo.SpObserver;

import lombok.extern.slf4j.Slf4j;

/**
 * 导出数据
 * @author lishun
 *
 */
@Slf4j
@Controller
@RequestMapping("/exp")
public class ExpCsvController {
	
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
    	log.info("smartExpCsv condition:{} title:{} fileName:{}", paras.getCondition(), paras.getTitle(), paras.getFileName());
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
			log.error("smartExpCsv exception", e);
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
