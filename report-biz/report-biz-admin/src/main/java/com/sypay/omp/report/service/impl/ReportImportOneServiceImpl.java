package com.sypay.omp.report.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.report.common.dal.common.BaseDao;
import com.sypay.omp.report.service.ReportImportOneService;

/**
 * 运营导入表一ServiceImpl
 * @author lishun
 *
 * @2015年11月26日
 */
@Transactional
@Service(value="reportImportOneService")
public class ReportImportOneServiceImpl implements ReportImportOneService {

    protected final Log log = LogFactory.getLog(ReportImportOneServiceImpl.class);
    
    @Autowired
    private BaseDao baseDao;
    
    
    /* (non-Javadoc)
	 * @see com.sypay.omp.report.service.impl.ReportImportOneService#getBatchNoCount(java.lang.String)
	 */
    public Integer getBatchNoCount(String batchNo) {
    	String sql = "select count(1) from rpt_import_one where batch_no = :batchNo";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("batchNo", batchNo);
		return baseDao.countBySql(sql, params);
    }
    
	/* (non-Javadoc)
	 * @see com.sypay.omp.report.service.impl.ReportImportOneService#deleteReportConfig(java.lang.String)
	 */
	@Override
	public void deleteReportConfig(String batchNo) {
		String sql = "delete from rpt_import_one where batch_no = :batchNo";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("batchNo", batchNo);
		baseDao.executeSql(sql, params);
	}

	/* (non-Javadoc)
	 * @see com.sypay.omp.report.service.impl.ReportImportOneService#getRptImportCount(java.lang.Integer)
	 */
	@Override
	public Integer getRptImportCount(Integer maxId) {
		String sql = "select count(1) from rpt_import_one where id > :maxId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("maxId", maxId);
		return baseDao.countBySql(sql, params);
	}
	
	/* (non-Javadoc)
	 * @see com.sypay.omp.report.service.impl.ReportImportOneService#getRptMaxId()
	 */
	@Override
	public Integer getRptMaxId() {
		String sql = "select max(id) from rpt_import_one";
		BigDecimal result = (BigDecimal ) baseDao.getBySql(sql);
		if (null != result) {
			return 0;
		}
		return result.intValue();
	}
}
