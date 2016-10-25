package com.sypay.omp.report.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.sypay.omp.report.dao.BaseDao;
import com.sypay.omp.report.dao.ReportCommonConDao;

@Repository
public class ReportCommonConDaoImpl implements ReportCommonConDao {

	@Resource
    private BaseDao baseDao;
	
}
