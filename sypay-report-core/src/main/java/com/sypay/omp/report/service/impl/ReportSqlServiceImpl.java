package com.sypay.omp.report.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sypay.omp.per.model.page.DataGrid;
import com.sypay.omp.per.model.page.PageHelper;
import com.sypay.omp.report.VO.ReportSqlVO;
import com.sypay.omp.report.dao.BaseDao;
import com.sypay.omp.report.domain.ReportSql;
import com.sypay.omp.report.service.ReportSqlService;

/**
 * @author lishun
 *
 */
@Transactional
@Service(value="reportSqlService")
public class ReportSqlServiceImpl implements ReportSqlService{
	
	@Autowired
	private BaseDao baseDao;
	
  
	@Override
	public Long saveReportSql(String baseSql, String sqlName, String dataBaseSource) {
    	String baseCountSql = getCountSQL(baseSql);
    	ReportSql reportSql = new ReportSql();
    	reportSql.setBaseSql(baseSql);
    	reportSql.setBaseCountSql(baseCountSql);
    	reportSql.setRptname(sqlName);
    	reportSql.setDataBaseSource(dataBaseSource);
    	baseDao.save(reportSql);
    	return reportSql.getSqlId();
	}

	@Override
	public void updateReportSql(ReportSql reportSql) {
	    baseDao.update(reportSql);
		
	}

	@Override
	public ReportSql findReportSqlById(Long sqlId) {
		ReportSql reportSql = baseDao.get(ReportSql.class, sqlId);
		return reportSql;
	}
	
	
	/**
	 * 得到count的SQL
	 */
	public String getCountSQL (String baseSql) {
//	    String baseSql = "with () select count(*) over(order by) from dual";
	    int firstSelectIndex = getFirstSelectIndex(baseSql);
	    String countSql = baseSql.substring(0, firstSelectIndex) + " select count(1) from (" + baseSql.subSequence(firstSelectIndex, baseSql.length());
	    int lastOrderIndex = countSql.lastIndexOf("order");
	    int lastFromIndex = countSql.lastIndexOf("from");
	    if (lastFromIndex < lastOrderIndex && countSql.indexOf("{orderCol") == -1) {
	        countSql = countSql.substring(0, lastOrderIndex) + ")";
	    } else {
	        countSql = countSql + ") vvv";
	    }
	    return countSql;
	}
	
	public int getFirstSelectIndex (String str) {
//	    String str = "select 1 from dual order by 1 desc";
        int firstWithIndex = str.indexOf("with");
        int firstSelectIndex = str.indexOf("select");
        //如果(和)在str中出现的次数不相等
        while (firstWithIndex !=-1 && firstWithIndex < firstSelectIndex && !isSameCnt(str.substring(firstWithIndex, firstSelectIndex),'(',')')) {
            firstSelectIndex = str.indexOf("select", firstSelectIndex+1);
            if (firstSelectIndex == -1) {
                System.out.println("没找到");
                return -1;
            }
        }
        return firstSelectIndex;
	}
	
	//判断两个字符a,b在一个字符串str中出现的次数是否相同
	public boolean isSameCnt (String str, char a, char b) {
	    int anum = getCharCnt(str, a);
	    int bnum = getCharCnt(str, b);
	    if (anum == bnum) {
	        return true;
	    }
	    return false;
	}
	
	//得到某个字符在某个字符串中的个数
	public int getCharCnt (String str,char targetStr) {
	    char[] strs = str.toCharArray();
	    int cnt = 0;
	    for (int i=0;i<strs.length;i++) {
	        if (strs[i] == targetStr) {
	            cnt ++;
	        }
	    }
	    return cnt;
	}

	@Override
	public DataGrid findReportSqlList(ReportSqlVO reportSqlVo,
			PageHelper pageHelper) {
		DataGrid dataGrid = new DataGrid();
		String sql = "select sql_id \"sqlId\",database_source \"dataBaseSource\", base_sql \"baseSql\", base_count_sql \"baseCountSql\", rptname \"rptname\" from rp_report_sql where 1=1 "+ constructSqlWhere(reportSqlVo)
				+ " order by 1 desc" ;
		Query query = baseDao.getSqlQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).setFirstResult((pageHelper.getPage() - 1) * pageHelper.getRows()).setMaxResults(pageHelper.getRows());
		Map<String, Object> params = new HashMap<String, Object>();
        
        if (reportSqlVo.getSqlId() != null) {
        	query.setParameter("sqlId", reportSqlVo.getSqlId());
        	params.put("sqlId", reportSqlVo.getSqlId());
        }
        
        if (StringUtils.isNotBlank(reportSqlVo.getRptname())) {
        	query.setParameter("rptname", "%" + reportSqlVo.getRptname() + "%");
        	params.put("rptname", "%" +reportSqlVo.getRptname() + "%");
        }
		
		List<ReportSql> list = (List<ReportSql>)query.list();
		dataGrid.setRows(list);
		String countSql = "select count(1) from rp_report_sql where 1=1" + constructSqlWhere(reportSqlVo);
		dataGrid.setTotal((long)baseDao.countBySql(countSql, params));
		return dataGrid;
	}
	
	/**
	 * 组装条件
	 * @param publicVo
	 * @return
	 */
	private String constructSqlWhere(ReportSqlVO reportSqlVo) {
		String str = "";
		if (reportSqlVo.getSqlId() != null) {
			str = str + " and sql_id = :sqlId";
		}
		if (StringUtils.isNotBlank(reportSqlVo.getRptname())) {
			str = str + " and rptname like :rptname";
		}
		return str;
	}

	/**
	 * 更新报表SQL
	 */
	@Override
	public void updatePublic(ReportSql reportSql) {
		baseDao.update(reportSql);
	}

	/**
	 * 新增报表SQL
	 */
	@Override
	public void addReportSql(ReportSql reportSql) {
		baseDao.save(reportSql);
	}

}
