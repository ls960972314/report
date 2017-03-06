package com.sypay.omp.report.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.sypay.omp.report.dao.BaseDao;
import com.sypay.omp.report.dao.ReportPublicDao;

@Repository
public class ReportPublicDaoImpl implements ReportPublicDao {

	@Resource
    private BaseDao baseDao;
	
	@Override
	public boolean deleteByReportFlag(String reportFlag) {
		String sql = "delete from rptpub where toolflag = :reportFlag";
		Query query = baseDao.getSqlQuery(sql);
		query.setParameter("reportFlag", reportFlag);
		return query.executeUpdate() > 0;
	}

}
