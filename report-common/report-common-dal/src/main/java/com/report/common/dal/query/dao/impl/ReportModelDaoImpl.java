package com.report.common.dal.query.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.report.common.dal.common.BaseDao;
import com.report.common.dal.query.dao.ReportModelDao;

@Repository
public class ReportModelDaoImpl implements ReportModelDao {

	@Resource
    private BaseDao baseDao;
	

}
