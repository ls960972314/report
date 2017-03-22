package com.sypay.omp.report.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.report.common.dal.common.BaseDao;
import com.sypay.omp.report.dao.ReportLogDao;

@Repository
public class ReportLogDaoImpl implements ReportLogDao {

	@Resource
    private BaseDao baseDao;
	
}
