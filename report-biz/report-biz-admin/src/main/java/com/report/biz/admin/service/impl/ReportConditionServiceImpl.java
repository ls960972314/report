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

import com.report.biz.admin.service.ReportConditionService;
import com.report.common.dal.common.BaseDao;
import com.report.common.dal.query.entity.dto.ReportCondition;
import com.report.common.model.DataGrid;
import com.report.common.model.PageHelper;
import com.report.common.model.query.ConditionVO;

/**
 * 
 * @author lishun
 *
 * @2015年5月7日
 */
@Transactional
@Service(value="reportConditionService")
public class ReportConditionServiceImpl implements ReportConditionService {

    protected final Log log = LogFactory.getLog(ReportConditionServiceImpl.class);
    
    @Autowired
    private BaseDao baseDao;

    @Override
    public void saveReportCondition(ReportCondition reportCondition) {
        baseDao.save(reportCondition);
    }

    @Override
    public void updateReportCondition(ReportCondition reportCondition) {
        baseDao.update(reportCondition);
    }

    @Override
    public List<ReportCondition> queryReportCondition(String reportFlag) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("toolFlag", reportFlag);
        return baseDao.find("from ReportCondition where toolFlag = :toolFlag order by rowNum,orderNum", paramMap);
    }

    /**
     * 查找条件列表
     */
	@Override
	public DataGrid findConditionList(ConditionVO condition,
			PageHelper pageHelper) {
		DataGrid dataGrid = new DataGrid();
		String sql = "select id \"Id\", conmuti \"conMuti\", conname \"conName\", conoption \"conOption\", contype \"conType\", conwhere \"conWhere\", toolflag \"toolFlag\", order_num \"orderNum\", default_value \"conDefaultValue\",database_source \"dataBaseSource\", row_num \"rowNum\" from rptcon where 1=1 "+ constructSqlWhere(condition)
				+ " order by 1 desc" ;
		Query query = baseDao.getSqlQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).setFirstResult((pageHelper.getPage() - 1) * pageHelper.getRows()).setMaxResults(pageHelper.getRows());;
		Map<String, Object> params = new HashMap<String, Object>();
        
        if (StringUtils.isNotBlank(condition.getToolFlag())) {
        	query.setParameter("toolflag", condition.getToolFlag());
        	params.put("toolflag", condition.getToolFlag());
        }
        if (condition.getId() != null) {
        	query.setParameter("id", condition.getId());
        	params.put("id", condition.getId());
        }
		List<ReportCondition> list = (List<ReportCondition>)query.list();
		dataGrid.setRows(list);
		String countSql = "select count(1) from rptcon t where 1=1" + constructSqlWhere(condition);
		dataGrid.setTotal((long)baseDao.countBySql(countSql, params));
		return dataGrid;
	}

	/**
	 * 组装条件
	 * @param condition
	 * @return
	 */
	private String constructSqlWhere(ConditionVO condition) {
		String str = "";
		if (StringUtils.isNotBlank(condition.getToolFlag())) {
			str = str + " and toolflag = :toolflag";
		}
		if (condition.getId() != null) {
			str = str + " and id = :id";
		}
		return str;
	}
}
