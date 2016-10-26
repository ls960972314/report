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
import com.sypay.omp.report.VO.ChartVO;
import com.sypay.omp.report.dao.BaseDao;
import com.sypay.omp.report.domain.ReportChart;
import com.sypay.omp.report.service.ReportChartService;
import com.sypay.omp.report.util.StringUtil;

/**
 * 
 * @author lishun
 *
 * @2015年5月7日
 */
@Transactional
@Service(value="reportChartService")
public class ReportChartServiceImpl implements ReportChartService {

    protected final Log log = LogFactory.getLog(ReportChartServiceImpl.class);
    
    @Autowired
    private BaseDao baseDao;

    @Override
    public int saveReportChart(ReportChart reportChart) {
        return baseDao.save(reportChart) != null ? 1: 0;
    }

    @Override
    public int updateReportChart(ReportChart reportChart) {
        baseDao.update(reportChart);
        return 1;
    }

    @Override
    public List<ReportChart> queryReportChart(String reportFlag) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("toolFlag", reportFlag);
        return  baseDao.find("from ReportChart where toolFlag = :toolFlag", paramMap);
    }

    /**
     * 查找图表列表
     */
	@Override
	public DataGrid findChartList(ChartVO chart, PageHelper pageHelper) {
		DataGrid dataGrid = new DataGrid();
		String sql = "select id \"id\",chartoption \"chartOption\",datavsle \"dataVsLe\",toolflag \"toolFlag\",order_num \"chartOrder\",name \"chartName\",charttype \"chartType\",datavsx \"dataVsX\",show_rownum \"showRowNum\" from rptchart where 1=1 " + constructSqlWhere(chart)
				+ " order by 5 desc";
		Query query = baseDao.getSqlQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).setFirstResult((pageHelper.getPage() - 1) * pageHelper.getRows()).setMaxResults(pageHelper.getRows());;
		Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(chart.getToolFlag())) {
        	query.setParameter("toolflag", chart.getToolFlag());
        	params.put("toolflag", chart.getToolFlag());
        }
        if (chart.getId() != null) {
        	query.setParameter("id", chart.getId());
        	params.put("id", chart.getId());
        }
        if (StringUtil.isNotEmpty(chart.getChartName())) {
        	query.setParameter("chartname", "%"+chart.getChartName()+"%");
        	params.put("chartname", "%"+chart.getChartName()+"%");
        }
		List<ReportChart> list = (List<ReportChart>)query.list();
		dataGrid.setRows(list);
		String countSql = "select count(1) from rptchart t where 1=1" + constructSqlWhere(chart);
		dataGrid.setTotal((long)baseDao.countBySql(countSql, params));
		return dataGrid;
	}
	
	/**
	 * 更新图表
	 */
	@Override
	public void updateChart(ReportChart chart) {
		baseDao.update(chart);
	}
	
	public String constructSqlWhere (ChartVO chart) {
		String str = "";
		if (StringUtil.isNotEmpty(chart.getToolFlag())) {
			str = str + " and toolflag = :toolflag";
		}
		if (StringUtil.isNotEmpty(chart.getChartName())) {
			str = str + " and name like :chartname";
		}
		if (chart.getId() != null) {
			str = str + " and id = :id";
		}
		return str;
	}
}
