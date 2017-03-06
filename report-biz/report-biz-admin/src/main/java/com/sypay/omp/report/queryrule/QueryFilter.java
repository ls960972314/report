package com.sypay.omp.report.queryrule;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;

import com.alibaba.fastjson.JSON;
import com.sypay.omp.report.util.TimeUtil;

/**
 * 页面封装对象中的条件对象
 * @author lishun
 *
 */
public class QueryFilter {
	private final  Log log = LogFactory.getLog(QueryFilter.class);
	static DefaultConversionService conversionService = new DefaultConversionService();
	static{
		//DateFormatterRegistrar.addDateConverters(conversionService);
		conversionService.addConverter(new StringToDateConverter());
	}
	private QueryFilter other;
    private String groupOp;
    private QueryFilter[] groups;
    private QueryRule[] rules;
	public String getGroupOp() {
		return groupOp;
	}
	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}
	public QueryFilter[] getGroups() {
		return groups;
	}
	public void setGroups(QueryFilter[] groups) {
		this.groups = groups;
	}
	public QueryRule[] getRules() {
		return rules;
	}
	public void setRules(QueryRule[] rules) {
		this.rules = rules;
	}
	
	public QueryFilter getOther() {
		return other;
	}
	public void setOther(QueryFilter other) {
		this.other = other;
	}
	public String toSqlWhere(PagerReq req){
		Map<String, Class> fieldMap = req.getFieldTypeMap();
		String sql="";
		for(int i=0;groups!=null&&i<groups.length;i++){
			QueryFilter qf = (QueryFilter)groups[i];
			String subWhere = qf.toSqlWhere(req);
			sql = sql.equals("")?subWhere:sql+" "+groupOp+" "+subWhere;
		}
		for(int i=0;rules!=null&&i<rules.length;i++){
			QueryRule qr = (QueryRule)rules[i];
            if (qr.getFuncBefore() == null) {
				qr.setFuncBefore("");
			}
            if (qr.getFuncAfter() == null) {
            	qr.setFuncAfter("");
            }
			String paraTag = req.getNextQueryParaTag();
			String subWhere = qr.getField()+qr.getOpt()+qr.getFuncBefore()+":"+paraTag+qr.getFuncAfter();
			Class type = fieldMap==null?null:fieldMap.get(qr.getField());
			type = (type==null?String.class:type);
			Object para = null;
			try{
				para = conversionService.convert(qr.getData(), type);
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
			if(para==null){
				continue;
			}
			req.addQueryPara(paraTag, para);
			sql = sql.equals("")?subWhere:sql+" "+groupOp+" "+subWhere;
		}
		
		
		return sql.equals("")?"":"("+sql+")";
	}
	

	public static class StringToDateConverter implements Converter<String, Date> {   
	    public Date convert(String source) {  
	    	source = source.replace("-", "");
	    	source = source.replace("/", "");
	    	source = source.replace("\\", "");
	    	return TimeUtil.stringToDate(source, TimeUtil.DATE_FORMAT_9);
	    }   
	}  

	
	public static void main(String[] args) throws Exception{
		
		
		/*{"groupOp":"and","rules":[{"field":"a.id","op":"eq","data":"1"},
							{"field":"a.id","op":"eq","data":"2"}],
			"other":[{"groupOp":"OR","rules":[{"field":"c.id","op":"eq","data":"3"},
											{"field":"c.id","op":"eq","data":"4"}],
											"groups":[]}]}
*/		String json="{\"groupOp\":\"and\",\"rules\":[{\"field\":\"a.id\",\"op\":\"eq\"" +
				",\"data\":\"1\"},{\"field\":\"a.id\",\"op\":\"eq\",\"data\":\"2\"}]," +
				"\"other\":[{\"groupOp\":\"OR\",\"rules\":[{\"field\":\"c.id\"," +
				"\"op\":\"eq\",\"data\":\"3\"},{\"field\":\"c.id\"," +
				"\"op\":\"eq\",\"data\":\"4\"}],\"groups\":[]}]}";
		PagerReq pr = new PagerReq();
		pr.addFieldType("a.id", Integer.class);
		QueryFilter qf = JSON.parseObject(json, QueryFilter.class);
		String sqlWhere = qf.toSqlWhere(pr);
		
		String baseSql = "select * from member where 1=1 and {whereclause} {whereclause1} {orderby}";
		String sql = baseSql.replace("{whereclause}", sqlWhere);
		sql = sql.replace("{orderby}", "");
		System.out.println(sql);

        for(String key:pr.getParaMap().keySet()){
        	System.out.println(key);
        	System.out.println(pr.getParaMap().get(key));
        }

        double a = ((double)2)/12;
        System.out.println(a);
        System.out.println(Math.ceil(a));
        
        ;

	}
   
}
