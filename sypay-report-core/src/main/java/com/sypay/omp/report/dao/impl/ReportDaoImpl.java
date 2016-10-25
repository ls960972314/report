package com.sypay.omp.report.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.sypay.omp.report.dao.BaseDao;
import com.sypay.omp.report.dao.ReportDao;
import com.sypay.omp.report.dataBase.SpObserver;
import com.sypay.omp.report.domain.ReportSql;
import com.sypay.omp.report.queryrule.Condition;
import com.sypay.omp.report.queryrule.PagerReq;
import com.sypay.omp.report.util.StringUtil;

@Repository
public class ReportDaoImpl implements ReportDao {
	private final Log log = LogFactory.getLog(ReportDaoImpl.class);

	@Autowired
	private BaseDao baseDao;
	
    /**
     * 取QID模式的初始化base_sql和base_count_sql
     * @param PagerReq req
	 * @return PagerReq
     */
	@Override
	public PagerReq setupReportSql(PagerReq req) {
		ReportSql rq = baseDao.get(ReportSql.class, req.getQid());
		if (rq == null) {
			return req;
		}
		req.setBaseSql(rq.getBaseSql());
		req.setBaseCountSql(rq.getBaseCountSql());
		return req;
	}
	/**
     * 取QID模式的通过base_sql获取返回结果集
     * @param PagerReq req
	 * @return List
     */
	@Override
	public List getData(PagerReq req) {
		Query query = baseDao.getSqlQuery(req.getSql());
		Map<String, Object> paraMap = req.getParaMap();
		for (String name : paraMap.keySet()) {
			query.setParameter(name, paraMap.get(name));
		}
		query.setFirstResult((req.getPage() - 1) * req.getRows());
		query.setMaxResults(req.getRows());
		return query.list();
	}
	/**
     * 取QID模式的通过base_count_sql获取返回结果集大小
     * @param PagerReq req
	 * @return Integer
     */
	@Override
	public Integer getDataCount(PagerReq req) {
		return baseDao.countBySql(req.getCountSql(), req.getParaMap());
	}
    
	@Override
	public PagerReq setupSmartReportSql(PagerReq req) {
        ReportSql rq = baseDao.get(ReportSql.class, req.getQid());
        if (rq == null) {
            return req;
        }
        req.setBaseSql(rq.getBaseSql());
        req.setBaseCountSql(rq.getBaseCountSql());
        req.setDataBaseSource(rq.getDataBaseSource());
        if (StringUtil.isNotEmpty(rq.getDataBaseSource())) {
        	req.setDataBaseSource(rq.getDataBaseSource());
        } else {
        	req.setDataBaseSource(SpObserver.defaultDataBase);
        }
        return req;
    }
    
    /**
     * 创建报表时查询数据
     */
	@Override
    public List createReportQueryData (PagerReq req) {
        req = updatePagerReq(req);
        SpObserver.getSp();
        List<Condition> conList = JSON.parseArray(req.getCondition(), Condition.class);
        /* 在此处根据传过来的trueSql去找到正确的SQl然后拼条件condition */
        String sql = req.getBaseSql();
    	Query query = null;
        query = baseDao.getSqlQuery(sql);
        for (Condition con : conList) {
            query.setParameter(con.getName(), con.getValue());
        }
        query.setFirstResult((req.getPage() - 1) * req.getRows());
        query.setMaxResults(req.getRows());
        List queryList = null;
        try {
        	queryList = query.list();
		} catch (Exception e) {
			throw e;
		}
        return queryList;
    }
    
    /**
     * 处理请求对象
     * @param req
     * @return
     */
	@Override
    public PagerReq updatePagerReq(PagerReq req) {
        /* 将checkbox中的单个：{1}换成跟条件数量对应的：{2}，：{3}等等 */
        List<Condition> conList = JSON.parseArray(req.getCondition(), Condition.class);
        List<Condition> addList = new ArrayList<Condition>();
        List<Condition> removeList = new ArrayList<Condition>();
        int index = 0;
        for (Condition con : conList) {
            if (con.getType().equals("checkbox")) {
                String conName = con.getName();
                String conValues = con.getValue();
                String[] conValueArrs = conValues.split(",");
                int length = conValueArrs.length;
                String baseSql = req.getBaseSql();
                String baseCountSql = req.getBaseCountSql();
                String replaceSql = "";
                for (int i = 0; i < length; i++,index++) {
                    replaceSql = replaceSql + ":p" + index + ",";
                    Condition c = new Condition();
                    c.setName("p" + index);
                    c.setType("input");
                    c.setValue(conValueArrs[i]);
                    addList.add(c);
                }
                replaceSql = replaceSql.substring(0, replaceSql.length()-1);
                baseSql = baseSql.replace(":" + conName, replaceSql);
                if (StringUtil.isNotEmpty(baseCountSql)) {
                	baseCountSql = baseCountSql.replace(":" + conName, replaceSql);
                }
                req.setBaseSql(baseSql);
                req.setBaseCountSql(baseCountSql);
                removeList.add(con);
            }
        }
        conList.removeAll(removeList);
        conList.addAll(addList);
        /* 如果条件的值没有传,则删掉该条件,替换对应sql中的条件表达式为 1=1 */
        for (Condition con : conList) {
            if (StringUtil.isEmpty(con.getValue())) {
                req.setBaseSql(verifyNullCon(req.getBaseSql(), con.getName()));
                req.setBaseCountSql(verifyNullCon(req.getBaseCountSql(), con.getName()));
                removeList.add(con);
            }
        }
        conList.removeAll(removeList);
        // 0815添加
        // sql中添加该标志，点击列会根据点击的列排序 {orderCol| order by 1 desc}
        if (req.getBaseSql().indexOf("{orderCol") != -1) {
        	int beginIndex = req.getBaseSql().indexOf("{orderCol");
        	int endIndex = req.getBaseSql().indexOf("}", beginIndex);
        	if (StringUtils.isNotEmpty(req.getSidx()) && StringUtils.isNotEmpty(req.getSord())) {
        		String baseSql = req.getBaseSql().substring(0, beginIndex);
        		baseSql = baseSql + " order by " + req.getSidx() + " " + req.getSord();
        		baseSql = baseSql + req.getBaseSql().substring(endIndex+1);
        		req.setBaseSql(baseSql);
        	} else {
        		String baseSql = req.getBaseSql().substring(0, beginIndex);
        		baseSql = baseSql + req.getBaseSql().substring(req.getBaseSql().indexOf("|", beginIndex)+1, req.getBaseSql().indexOf("}", beginIndex));
        		baseSql = baseSql + req.getBaseSql().substring(endIndex+1);
        		req.setBaseSql(baseSql);
        	}
        	String baseCountSql = "";
        	if (req.getBaseCountSql().indexOf("{orderCol") != -1) {
        		baseCountSql = req.getBaseCountSql().substring(0, req.getBaseCountSql().indexOf("{orderCol"));
        		baseCountSql = baseCountSql + req.getBaseCountSql().substring(req.getBaseCountSql().indexOf("}", req.getBaseCountSql().indexOf("{orderCol"))+1);
            	req.setBaseCountSql(baseCountSql);
        	}
        }
        req.setCondition(JSON.toJSONString(conList));
        return req;
    }

    @Override
    public List showReportQueryData(PagerReq req) {
        Query query = null;
        List<Condition> conList = JSON.parseArray(req.getCondition(), Condition.class);
        /* 在此处根据传过来的trueSql去找到正确的SQl然后拼条件condition */
        String sql = req.getBaseSql();
        query = baseDao.getSqlQuery(sql);
        for (Condition con : conList) {
            query.setParameter(con.getName(), con.getValue());
        }
        query.setFirstResult((req.getPage() - 1) * req.getRows());
        query.setMaxResults(req.getRows());
        return query.list();
    }
    /**
     * 导出数据，用scroll执行，否则大量分页数据会有重复
     * @param dataIndex 行数
     * @param row HSSFRow
     * @param sheet HSSFSheet
     * @param req PagerReq
     */
    @Override
    public void expReportQueryData(PagerReq req, int dataIndex, XSSFRow row, XSSFSheet sheet) {
        Query query = null;
        List<Condition> conList = JSON.parseArray(req.getCondition(), Condition.class);
        /* 在此处根据传过来的trueSql去找到正确的SQl然后拼条件condition */
        String sql = req.getBaseSql();
        query = baseDao.getSqlQuery(sql);
        for (Condition con : conList) {
            query.setParameter(con.getName(), con.getValue());
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        /* 格式化百分号 */
        String[] findex = getFIndex(req);
        int i = dataIndex;
        ScrollableResults scrollableResults  = query.scroll();
        while (scrollableResults.next()) {
        	Object[] list = scrollableResults.get();
        	row = sheet.createRow(i);
            for (int j = 0; j < list.length; j++) {
                if (list[j] instanceof Timestamp) {
                    list[j] = df.format((Timestamp) list[j]);
                }
                String cel = getCelValue (list[j], findex, j);
                row.createCell(j).setCellValue(cel);
            }
            i++;
            //if (i > 65535) 
            	//break;
        }
    }
    
    
    @Override
    public Integer showReportQueryDataCount(PagerReq req) {
        Query query = null;
        List<Condition> conList = JSON.parseArray(req.getCondition(), Condition.class);
        //在此处根据传过来的trueSql去找到正确的SQl然后拼条件condition
        String sql = req.getBaseCountSql();
        query = baseDao.getSqlQuery(sql);
        for (Condition con : conList) {
            query.setParameter(con.getName(), con.getValue());
        }
        BigInteger cnt = (BigInteger) query.list().get(0);
        return  cnt.intValue();
    }

	@Override
    public List getConValue(String sql) {
        Query query =  baseDao.getSqlQuery(sql);
        return query.list();
    }

    /* 将为空的值置为1=1 */
    private String verifyNullCon (String str, String indexStr) {
    	/* where rp_date >= replace(:{1},'-','') and rp_date <= replace(:{2},'-','') order by 1 desc */
    	if (StringUtil.isNotEmpty(str)) {
    		int index = str.indexOf(indexStr);
            int begin = str.lastIndexOf("and", index) == -1?str.lastIndexOf("where", index)+5:str.lastIndexOf("and", index)+3;
            int end = 0;
            /* 将rp_date >= replace(:{1},'-','') 替换为1=1 */
            int beginIndex = str.lastIndexOf("and", index);
            if (beginIndex == -1) {
            	beginIndex = str.lastIndexOf("where", index) +5;
            } else {
            	beginIndex = beginIndex + 3;
            }
            /* 看{1} 之前有几个括号 */
            String s = str.substring(beginIndex, index);
            char[] ss = s.toCharArray();
            int num = 0;
            for (char i : ss) {
                if (i == '(') {
                    num ++;
                }
            }
            //从{1} 往后找num个)
            int endIndex = index;
            while (num-- != 0) {
                endIndex = str.indexOf(")", endIndex+1);
            }
            if (endIndex != index) {
                end = endIndex + 1;
            } else {
                end = str.indexOf("}", index) + 1;
            }
            String strn = str.substring(0, begin) + " 1=1 " + str.substring(end);
            return strn;
    	}
    	return null;
    }
    /**
     * 导出加上百分号格式化，其余格式化暂时不加
     * @param o
     * @param findex
     * @param j
     * @return
     */
    private String getCelValue (Object o, String[] findexs, int j) {
    	String cel = "";
    	if (o != null) {
    		cel = o.toString();
    		if (findexs != null) {
    			for (int i=0; i<findexs.length; i++) {
        			Integer findex = Integer.parseInt(findexs[i]);
        			if (findex == j) {
        				cel = o.toString() + "%";
        			}
        		}
    		}
    	}
    	return cel;
    }
    
	private String[] getFIndex(PagerReq req) {
		StringBuffer strBuf = new StringBuffer();
        String[] cols = req.getTitle().split(",");
        String[] formatCols = req.getFormatCols().split(",");
    	for (int i=0;i<formatCols.length;i++) {
        	if (formatCols[i].indexOf("百分比") != -1) {
        		String tmpStr = formatCols[i].substring(0, formatCols[i].indexOf(":"));
        		for (int j=0;j<cols.length;j++) {
        			if (cols[j].equals(tmpStr)) {
        				strBuf.append(j).append(",");
        			}
        		}
        	}
        }
    	if (strBuf.indexOf(",") != -1) {
    		String tmpStr = strBuf.toString().substring(0,strBuf.toString().length()-1);
    		String[] findex = tmpStr.split(",");
    		return findex;
    	}
    	
    	
        
		return null;
	}
    public static void main (String args[]) {
    	//select count(1) from (select rp_date,amt,cnt from (select '20150809' rp_date, '1000' amt, '100' cnt from dual union all select '20150810' rp_date, '1010' amt, '101' cnt from dual union all select '20150811' rp_date, '1020' amt, '102' cnt from dual union all select '20150812' rp_date, '1030' amt, '103' cnt from dual union all select '20150813' rp_date, '1040' amt, '104' cnt from dual ) where rp_date >= replace(:{1},'-','') and rp_date <= replace(:{2},'-','') )
        //String str = "select count(1) from (select rp_date,amt,cnt from (select '20150809' rp_date, '1000' amt, '100' cnt from dual union all select '20150810' rp_date, '1010' amt, '101' cnt from dual union all select '20150811' rp_date, '1020' amt, '102' cnt from dual union all select '20150812' rp_date, '1030' amt, '103' cnt from dual union all select '20150813' rp_date, '1040' amt, '104' cnt from dual ) where rp_date >= replace(:{1},'-','') and rp_date <= replace(:{2},'-','') )";
        //System.out.println(verifyNullCon(str, "{2}"));
//        String str1 = "select rp_date,user_name,action,ope_id from ( select to_char(create_time,'yyyy-mm-dd hh24:mi:ss') rp_date, user_name, decode(ope_action,'updateChart','更新图表', 'updateCondition','更新条件', 'updatePublic','更新报表公共信息', 'addReportSql','新增SQL', 'updateReportSql','更新SQL', 'addCommonCondition','新增批量报表条件', 'updateCommonCondition','更新批量报表条件', 'addReportConfig','新增跑批动态配置', 'updateReportConfig','更新跑批动态配置', 'deleteReportConfig','删除跑批动态配置') action, ope_id from rptlog where ope_action in ('updateChart','updateCondition','updatePublic','addReportSql','updateReportSql','addCommonCondition', 'updateCommonCondition','addReportConfig','updateReportConfig','deleteReportConfig') and create_time >= to_date(:begintime,'yyyy-mm-dd') and create_time < (to_date(:endtime,'yyyy-mm-dd')+1)) where user_name=:user_name and action=:action and ope_id=(:opeId) order by 1 desc";
//        System.out.println(verifyNullCon(str1, "opeId"));
    	
    	/*String[] cols = new String("a,b,c,d").split(",");
        String[] formatCols = new String("a:正整数,b:百分比,c:百分比.d:小数").split(",");
        for (int i=0;i<formatCols.length;i++) {
        	if (formatCols[i].indexOf("百分比") != -1) {
        		String tmpStr = formatCols[i].substring(0, formatCols[i].indexOf(":"));
        		for (int j=0;j<cols.length;j++) {
        			if (cols[j].equals(tmpStr)) {
        				System.out.println(j);
        			}
        		}
        	}
        }*/
    	String str = "abc {orderCol| order by 1 desc}";
    	int index = str.indexOf("{orderCol");
    	int endIndex = str.indexOf("}", index);
    	String str1 = str.substring(0, index);
    	System.out.println(index);
    	System.out.println(str1);
    }
}
