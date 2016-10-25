package com.sypay.omp.report.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.sypay.omp.report.queryrule.PagerReq;
import com.sypay.omp.report.queryrule.PagerRsp;


/** 
 * Business service interface for the user management.
 *
 * @author lishun
 */
public interface ReportService {
	
	/**
	 * 导出报表
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> smartReportExport(PagerReq paras) throws Exception;
	/**
     * 根据条件获取数据的入口
     * @param PagerReq req
     * @return PagerRsp
     */
    public PagerRsp getReportData(PagerReq req );
	
	public PagerReq setupSmartReportSql(PagerReq req);
	    
    public List createReportQueryData (PagerReq req) ;
    
    public List showReportQueryData (PagerReq req) ;
    
    public void expReportQueryData(PagerReq req, int dataIndex, XSSFRow row, XSSFSheet sheet);
    
    public Integer showReportQueryDataCount (PagerReq req);

    public String getConValue(String sql);

    public PagerReq updatePagerReq(PagerReq req);
    /**
     * 多数据源查询数据，会openSession查询完后再将Session close
     * @param req
     * @return
     */
    public List getMutiReportQueryData(PagerReq req);

}
