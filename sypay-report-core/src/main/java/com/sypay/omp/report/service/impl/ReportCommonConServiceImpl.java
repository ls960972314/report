package com.sypay.omp.report.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sypay.omp.per.model.page.DataGrid;
import com.sypay.omp.per.model.page.PageHelper;
import com.sypay.omp.report.VO.ReportCommonConVO;
import com.sypay.omp.report.dao.BaseDao;
import com.sypay.omp.report.domain.ReportCommonCon;
import com.sypay.omp.report.domain.ReportCondition;
import com.sypay.omp.report.service.ReportCommonConService;
import com.sypay.omp.report.util.StringUtil;

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
        
        if (StringUtil.isNotEmpty(reportCommonCon.getToolFlag())) {
        	query.setParameter("toolflag", reportCommonCon.getToolFlag());
        	params.put("toolflag", reportCommonCon.getToolFlag());
        }
        if (StringUtil.isNotEmpty(reportCommonCon.getConFlag())) {
        	query.setParameter("conflag", reportCommonCon.getConFlag());
        	params.put("conflag", reportCommonCon.getConFlag());
        }
		List<ReportCondition> list = (List<ReportCondition>)query.list();
		dataGrid.setRows(list);
		String countSql = "select count(1) from rptcmcon where 1=1" + constructSqlWhere(reportCommonCon);
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
		if (StringUtil.isNotEmpty(reportCommonCon.getToolFlag())) {
			str = str + " and toolflag = :toolflag";
		}
		 if (StringUtil.isNotEmpty(reportCommonCon.getConFlag())) {
			 str = str + " and conflag = :conflag";
		}
		return str;
	}
	
}
