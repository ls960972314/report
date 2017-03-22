package com.sypay.omp.report.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.report.common.dal.common.BaseDao;
import com.sypay.omp.report.dao.ReportPublishConfigDao;

@Repository
public class ReportPublishConfigDaoImpl implements ReportPublishConfigDao {

	@Resource
    private BaseDao baseDao;
	

}
