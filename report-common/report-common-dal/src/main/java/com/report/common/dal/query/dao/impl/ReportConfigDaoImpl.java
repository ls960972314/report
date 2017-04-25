package com.report.common.dal.query.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.report.common.dal.common.BaseDao;
import com.report.common.dal.query.dao.ReportConfigDao;
import com.report.common.dal.query.entity.dto.ReportConfig;

@Repository
public class ReportConfigDaoImpl implements ReportConfigDao {

	@Resource
    private BaseDao baseDao;
	
	@Override
	public boolean deleteByReportCode(String rpt_code) {
		String sql = "delete from dyna_rpt_config where rpt_code = :rpt_code";
		Query query = baseDao.getSqlQuery(sql);
		query.setParameter("rpt_code", rpt_code);
		return query.executeUpdate() > 0;
	}

	/**
	 * 根据主键查找
	 */
	@Override
	public List<ReportConfig> findReportConfigList(String rptCode) {
		String hql = "from ReportConfig where rptCode=:rptCode order by createTime desc";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("rptCode", rptCode);
		return baseDao.find(hql, params);
	}
}
