package com.sypay.omp.report.dao;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.sypay.omp.report.queryrule.PagerReq;



/**
 * ReportDao
 * @author lishun
 *
 */
public interface ReportDao {
	/**
     * 取QID模式的通过base_sql获取返回结果集
     * @param PagerReq req
	 * @return List
     */
	public List getData(PagerReq req );
	/**
     * 取QID模式的通过base_count_sql获取返回结果集
     * @param PagerReq req
	 * @return Integer
     */
	public Integer getDataCount(PagerReq req);
	/**
     * 取QID模式的初始化base_sql和base_count_sql
     * @param PagerReq req
	 * @return PagerReq
     */
	public PagerReq setupReportSql(PagerReq req);
	
    public PagerReq setupSmartReportSql(PagerReq req);
    
    public List createReportQueryData (PagerReq req);
    
    public List showReportQueryData(PagerReq req);
    
    /**
     * 导出数据，用scroll执行，否则大量分页数据会有重复
     * @param dataIndex 行数
     * @param row HSSFRow
     * @param sheet HSSFSheet
     * @param req PagerReq
     */
    public void expReportQueryData(PagerReq req, int dataIndex, XSSFRow row, XSSFSheet sheet);
    
    public Integer showReportQueryDataCount(PagerReq req);

    public List getConValue(String sql);

    public PagerReq updatePagerReq(PagerReq req);
}
