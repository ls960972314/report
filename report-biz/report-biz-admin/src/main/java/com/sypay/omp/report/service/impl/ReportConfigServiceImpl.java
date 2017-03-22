package com.sypay.omp.report.service.impl;

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

import com.report.common.dal.admin.entity.vo.PageHelper;
import com.report.common.dal.common.BaseDao;
import com.sypay.omp.per.model.page.DataGrid;
import com.sypay.omp.report.VO.ReportConfigVO;
import com.sypay.omp.report.dao.ReportConfigDao;
import com.sypay.omp.report.domain.ReportConfig;
import com.sypay.omp.report.domain.ReportSql;
import com.sypay.omp.report.service.ReportConfigService;

/**
 * 
 * @author lishun
 *
 * @2015年5月7日
 */
@Transactional
@Service(value="reportConfigService")
public class ReportConfigServiceImpl implements ReportConfigService {

    protected final Log log = LogFactory.getLog(ReportConfigServiceImpl.class);
    
    @Autowired
    private BaseDao baseDao;
    
    @Autowired
    private ReportConfigDao reportConfigDao;

	@Override
	public void saveReportConfig(ReportConfig reportConfig) {
		baseDao.saveOrUpdate(reportConfig);
	}

	@Override
	public void updateReportConfig(ReportConfig reportConfig) {
		baseDao.update(reportConfig);
	}

	@Override
	public void deleteReportConfig(String rptCode) {
		reportConfigDao.deleteByReportCode(rptCode);
	}

	/**
	 * 根据主键查找
	 * @param rptCode
	 * @return
	 */
	@Override
	public List<ReportConfig> findReportConfigList(String rptCode) {
		return reportConfigDao.findReportConfigList(rptCode);
	}
	
	@Override
	public DataGrid findReportConfigList(ReportConfigVO reportConfigVo, PageHelper pageHelper) {
		DataGrid dataGrid = new DataGrid();
		String sql = "select rpt_code \"rptCode\",rpt_name \"rptName\",rpt_tab_name \"rptTableName\","+
						 "rpt_col_name \"rptColName\",rpt_type \"rptType\",rpt_sql \"rptSql\",rpt_status \"rptStatus\","+
							 "rpt_ver \"rptVer\",update_remark \"updateRemark\",to_char(create_time,'yyyy-mm-dd hh:mi:ss') \"createTime\",to_char(update_time,'yyyy-mm-dd hh:mi:ss') \"updateTime\" from dyna_rpt_config where 1=1" + constructSqlWhere(reportConfigVo)
							 	+ " order by create_time desc";
		Query query = baseDao.getSqlQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).setFirstResult((pageHelper.getPage() - 1) * pageHelper.getRows()).setMaxResults(pageHelper.getRows());
		Map<String, Object> params = new HashMap<String, Object>();
        
        if (StringUtils.isNotBlank(reportConfigVo.getRptCode())) {
        	query.setParameter("rptCode", reportConfigVo.getRptCode());
        	params.put("rptCode", reportConfigVo.getRptCode());
		}
		if (StringUtils.isNotBlank(reportConfigVo.getRptName())) {
			query.setParameter("rptName", "%" + reportConfigVo.getRptName() + "%");
        	params.put("rptName", "%" + reportConfigVo.getRptName() + "%");
		}
		if (StringUtils.isNotBlank(reportConfigVo.getRptTableName())) {
			query.setParameter("rptTableName", "%" + reportConfigVo.getRptTableName() + "%");
        	params.put("rptTableName", "%" + reportConfigVo.getRptTableName() + "%");
		}
		List<ReportSql> list = (List<ReportSql>)query.list();
		dataGrid.setRows(list);
		String countSql = "select count(1) from dyna_rpt_config t where 1=1" + constructSqlWhere(reportConfigVo);
		dataGrid.setTotal((long)baseDao.countBySql(countSql, params));
		return dataGrid;
	}
	
	/**
	 * 组装条件
	 * @param reportConfigVo
	 * @return
	 */
	private String constructSqlWhere(ReportConfigVO reportConfigVo) {
		String str = "";
		if (StringUtils.isNotBlank(reportConfigVo.getRptCode())) {
			str = str + " and rpt_code = :rptCode";
		}
		if (StringUtils.isNotBlank(reportConfigVo.getRptName())) {
			str = str + " and rpt_name like :rptName";
		}
		if (StringUtils.isNotBlank(reportConfigVo.getRptTableName())) {
			str = str + "and rpt_tab_name like :rptTableName";
		}
		return str;
	}
	
}
