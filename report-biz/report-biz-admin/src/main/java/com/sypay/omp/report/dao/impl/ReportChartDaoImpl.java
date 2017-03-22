package com.sypay.omp.report.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.report.common.dal.common.BaseDao;
import com.sypay.omp.report.dao.ReportChartDao;

@Repository
public class ReportChartDaoImpl implements ReportChartDao {

	@Resource
    private BaseDao baseDao;
	
	@Override
	public boolean deleteByReportFlag(String reportFlag) {
		String sql = "delete from rptchart where toolflag = :reportFlag";
		Query query = baseDao.getSqlQuery(sql);
		query.setParameter("reportFlag", reportFlag);
		return query.executeUpdate() > 0;
	}

}
