package com.report.biz.admin.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

import com.alibaba.fastjson.JSON;
import com.report.biz.admin.service.ReportPublicService;
import com.report.biz.admin.service.ReportSqlService;
import com.report.common.dal.common.BaseDao;
import com.report.common.dal.query.entity.dto.ReportChart;
import com.report.common.dal.query.entity.dto.ReportCondition;
import com.report.common.dal.query.entity.dto.ReportPublic;
import com.report.common.dal.query.entity.vo.Condition;
import com.report.common.dal.query.entity.vo.ReportElement;
import com.report.common.model.DataGrid;
import com.report.common.model.PageHelper;
import com.report.common.model.query.ReportChartVO;
import com.report.common.model.query.PublicVO;

/**
 * 
 * @author lishun
 *
 * @2015年5月7日
 */
@Service(value="reportPublicService")
@Transactional
public class ReportPublicServiceImpl implements ReportPublicService {

    protected final Log log = LogFactory.getLog(ReportPublicServiceImpl.class);
    
    @Autowired
    private BaseDao baseDao;
    
    @Autowired
    private ReportSqlService reportSqlService;

    
    @Override
    public void saveReportPublic(ReportPublic reportPublic) {
        baseDao.save(reportPublic);
    }

    @Override
    public void updateReportPublic(ReportPublic reportPublic) {
        baseDao.update(reportPublic);
    }

    @Override
    public ReportPublic queryReportPublic(String reportFlag) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("toolFlag", reportFlag);
        return (ReportPublic) baseDao.get("from ReportPublic where toolFlag = :toolFlag", params);
    }

    /**
     * 保存报表公共信息，条件，图等
     * @param reportElement
     */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void saveReport(ReportElement reportElement) throws Exception {
		try {
			/* 公共信息 */
	        ReportPublic reportPublic = new ReportPublic();
	        reportPublic.setToolCColumn(reportElement.getSaveReportCColumn());
	        reportPublic.setToolTitle(reportElement.getSaveReportTitle());
	        reportPublic.setToolEColumn(reportElement.getSaveReportEColumn());
	        reportPublic.setToolFlag(reportElement.getSaveReportFlag());
	        reportPublic.setToolGather(reportElement.getSaveReportToolGather());
	        reportPublic.setToolFormat(reportElement.getSaveReportFormatDatas());
	        //保存统计sql
			if (StringUtils.isNotBlank(reportElement.getStaticSql())
					&& StringUtils.isNotBlank(reportElement.getStaticCcolumn())
					&& StringUtils.isNotBlank(reportElement.getStaticDataBaseSource())
					&& reportElement.getStaticRowNum() != null) {
				reportPublic.setStaticCcolumn(reportElement.getStaticCcolumn());
		        reportPublic.setStaticRowNum(reportElement.getStaticRowNum());
				reportElement.setStaticSql(reportElement.getStaticSql().replace("SELECT", "select"));
		        reportElement.setStaticSql(reportElement.getStaticSql().replace("WITH", "with"));
		        reportElement.setStaticSql(reportElement.getStaticSql().replace("ORDER", "order"));
		        reportElement.setStaticSql(reportElement.getStaticSql().replace("FROM", "from"));
				Long staticSqlId = reportSqlService.saveReportSql(reportElement.getStaticSql(),
						reportElement.getSaveReportFlag() + "统计SQL", reportElement.getStaticDataBaseSource());
				if (staticSqlId == null) {
					throw new Exception("saveReportSql Exception");
				}
				reportPublic.setStaticSql(staticSqlId);
			}
	        
	        reportPublic = updateSql(reportPublic, reportElement.getSaveReportSqlId());//{"时":"2000","日":"2001","周":"2001","月":"2001"}
	        baseDao.save(reportPublic);
	        /* 存条件 */
	        List<Condition> conList = JSON.parseArray(reportElement.getSaveReportCondition(), Condition.class);
	        for (Condition con : conList) {
	            ReportCondition reportCondition = new ReportCondition();
	            reportCondition.setConMuti(con.getConValue());
	            reportCondition.setConName(con.getName());
	            reportCondition.setConOption(con.getOption());
	            reportCondition.setConType(con.getType());
	            reportCondition.setConWhere(con.getWhere());
	            reportCondition.setToolFlag(reportElement.getSaveReportFlag());
	            reportCondition.setOrderNum(con.getOrderNum());
	            reportCondition.setRowNum(con.getRowNum());
	            reportCondition.setConDefaultValue(con.getConDefaultValue());
	            reportCondition.setDataBaseSource(con.getDataBaseSource());
	            //reportCondition.setChartId("");
	            baseDao.save(reportCondition);
	        }
	        
	        /* 存图 */
	        List<ReportChartVO> chartList = JSON.parseArray(reportElement.getSaveReportChart(), ReportChartVO.class);
	        if (reportElement.getSaveChartFlag()) {
	        	for (ReportChartVO chart : chartList) {
	        		ReportChart reportChart = new ReportChart();
	                reportChart.setChartName(chart.getChartName());
	                reportChart.setChartOption(chart.getChartOption());
	                reportChart.setChartOrder(chart.getChartOrder());
	                reportChart.setChartType(chart.getChartType());
	                reportChart.setDataVsLe(chart.getDataVsLe());
	                reportChart.setDataVsX(chart.getDataVsX());
	                reportChart.setShowRowNum(chart.getShowRowNum());
	                reportChart.setToolFlag(reportElement.getSaveReportFlag());
	                baseDao.save(reportChart);
	        	}
	        }
		} catch (Exception e) {
			log.error("saveReport Exception", e);
			throw e;
		}
	}
	
	public ReportPublic updateSql(ReportPublic reportPublic , String originalStr) {
        /* String originalStr = "{\"时\":\"2000\",\"日\":\"2001\",\"周\":\"2001\",\"月\":\"2001\"}"; */
        originalStr = originalStr.substring(1, originalStr.length()-1);
        String [] strs = originalStr.split(","); //["时":"2000","时":"2000","时":"2000"]
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("\"时\"", "setToolHSqlId");
        map.put("\"日\"", "setToolDSqlId");
        map.put("\"周\"", "setToolWSqlId");
        map.put("\"月\"", "setToolMSqlId");
        map.put("\"季\"", "setToolQSqlId");
        map.put("\"年\"", "setToolYSqlId");
        Class<?> r = ReportPublic.class;
        for (String str : strs) {
        	String[] s = str.split(":");
        	Method m;
			try {
				m = r.getMethod(map.get(s[0]).toString() , String.class);
				m.invoke(reportPublic, s[1].replace("\"", ""));
			} catch (NoSuchMethodException e) {
				log.info("reflect exception : {}" + e);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
        }
        return reportPublic;
    }

	/**
	 * 查找公共信息列表
	 */
	@Override
	public DataGrid findPublicList(PublicVO publicVo, PageHelper pageHelper) {
		DataGrid dataGrid = new DataGrid();
		String sql = "select id \"id\", toolccolumn \"toolCColumn\", tooldsqlid \"toolDSqlId\", toolecolumn \"toolEColumn\", toolflag \"toolFlag\", toolhsqlid \"toolHSqlId\", toolmsqlid \"toolMSqlId\", tooltitle \"toolTitle\", toolwsqlid \"toolWSqlId\" , toolqsqlid \"toolQSqlId\", toolysqlid \"toolYSqlId\", gather_column \"toolGather\", format \"toolFormat\" , static_rownum \"staticRowNum\", static_ccolumn \"staticCcolumn\",static_sql \"staticSql\" from rptpub where 1=1 "+ constructSqlWhere(publicVo)
				+ " order by 1 desc" ;
		Query query = baseDao.getSqlQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).setFirstResult((pageHelper.getPage() - 1) * pageHelper.getRows()).setMaxResults(pageHelper.getRows());;
		Map<String, Object> params = new HashMap<String, Object>();
        
        if (StringUtils.isNotBlank(publicVo.getToolFlag())) {
        	query.setParameter("toolflag", publicVo.getToolFlag());
        	params.put("toolflag", publicVo.getToolFlag());
        }
        if (publicVo.getId() != null) {
        	query.setParameter("id", publicVo.getId());
        	params.put("id", publicVo.getId());
        }
        if (StringUtils.isNotBlank(publicVo.getToolTitle())) {
        	query.setParameter("tooltitle", "%" + publicVo.getToolTitle() + "%");
        	params.put("tooltitle", "%" + publicVo.getToolTitle() + "%");
        }
		
		List<ReportPublic> list = (List<ReportPublic>)query.list();
		dataGrid.setRows(list);
		String countSql = "select count(1) from rptpub t where 1=1" + constructSqlWhere(publicVo);
		dataGrid.setTotal((long)baseDao.countBySql(countSql, params));
		return dataGrid;
	}

	/**
	 * 组装条件
	 * @param publicVo
	 * @return
	 */
	private String constructSqlWhere(PublicVO publicVo) {
		String str = "";
		if (StringUtils.isNotBlank(publicVo.getToolFlag())) {
			str = str + " and toolflag = :toolflag";
		}
		if (publicVo.getId() != null) {
			str = str + " and id = :id";
		}
		if (StringUtils.isNotBlank(publicVo.getToolTitle())) {
			str = str + " and tooltitle like :tooltitle";
		}
		return str;
	}
	/**
	 * 更新公共信息
	 */
	@Override
	public void updatePublic(ReportPublic reportpublic) {
		try {
			baseDao.update(reportpublic);
		} catch (Exception e) {
			log.error("updatePublic Exception", e);
		}
		
	}
}
