package com.report.biz.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.report.biz.admin.service.ReportCommonConService;
import com.report.common.dal.common.BaseDao;
import com.report.common.dal.query.entity.dto.ReportCommonCon;
import com.report.common.dal.query.entity.dto.ReportCondition;
import com.report.common.model.DataGrid;
import com.report.common.model.PageHelper;
import com.report.common.model.query.ReportCommonConVO;

/**
 * 
 * @author lishun
 *
 * @2015年5月7日
 */
@Transactional
@Service(value = "reportCommonConService")
public class ReportCommonConServiceImpl implements ReportCommonConService {

	protected final Log log = LogFactory.getLog(ReportCommonConServiceImpl.class);

	@Autowired
	private BaseDao baseDao;

	/**
	 * 保存
	 */
	@Override
	public int saveReportCommonCon(ReportCommonCon reportCommonCon) {
		return (Integer) baseDao.save(reportCommonCon);
	}

	/**
	 * 修改
	 * @param reportCondition
	 */
	@Override
    public void updateReportCondition(ReportCommonCon reportCommonCon) {
        baseDao.update(reportCommonCon);
    }
	/**
	 * 根据toolFlag和conFlag查找List
	 */
	@Override
	public List<ReportCommonCon> findReportCommonConList(String toolFlag, String conFlag) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("toolFlag", toolFlag);
		paramMap.put("conFlag", conFlag);
		return baseDao.find("from ReportCommonCon where toolFlag = :toolFlag and conFlag = :conFlag", paramMap);

	}

	/**
	 * 分页展示
	 */
	@Override
	public DataGrid findReportCommonConList(ReportCommonConVO reportCommonCon, PageHelper pageHelper) {
		DataGrid dataGrid = new DataGrid();
		String sql = "select id \"id\", toolflag \"toolFlag\", conflag \"conFlag\", conwhere \"conWhere\", convalue \"conValue\" from rptcmcon where 1=1 "+ constructSqlWhere(reportCommonCon)
				+ " order by 1 desc" ;
		Query query = baseDao.getSqlQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).setFirstResult((pageHelper.getPage() - 1) * pageHelper.getRows()).setMaxResults(pageHelper.getRows());;
		Map<String, Object> params = new HashMap<String, Object>();
        
        if (StringUtils.isNotBlank(reportCommonCon.getToolFlag())) {
        	query.setParameter("toolflag", reportCommonCon.getToolFlag());
        	params.put("toolflag", reportCommonCon.getToolFlag());
        }
        if (StringUtils.isNotBlank(reportCommonCon.getConFlag())) {
        	query.setParameter("conflag", reportCommonCon.getConFlag());
        	params.put("conflag", reportCommonCon.getConFlag());
        }
		List<ReportCondition> list = (List<ReportCondition>)query.list();
		dataGrid.setRows(list);
		String countSql = "select count(1) from rptcmcon t where 1=1" + constructSqlWhere(reportCommonCon);
		dataGrid.setTotal((long)baseDao.countBySql(countSql, params));
		return dataGrid;
	}
	
	/**
	 * 组装条件
	 * @param condition
	 * @return
	 */
	private String constructSqlWhere(ReportCommonConVO reportCommonCon) {
		String str = "";
		if (StringUtils.isNotBlank(reportCommonCon.getToolFlag())) {
			str = str + " and toolflag = :toolflag";
		}
		 if (StringUtils.isNotBlank(reportCommonCon.getConFlag())) {
			 str = str + " and conflag = :conflag";
		}
		return str;
	}
	
}
