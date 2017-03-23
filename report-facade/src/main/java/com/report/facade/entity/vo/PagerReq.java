package com.report.facade.entity.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 页面请求参数封装
 * @author lishun
 * 
 */
public class PagerReq implements Serializable {
	private static final long serialVersionUID = -22015516931474161L;
	private Long qid;
	private Integer page;//当前页
	private Integer rows;
	private String filters;
	private String sidx;
	private String sord;
	private String baseSql;
	private String baseCountSql;
	private String title;
	private String fileName;
	private String para;
	private Integer trueSql;
	private String condition;
	private String formatCols;
	private String dataBaseSource;
	private Map<String, Class> fieldTypeMap= new HashMap<String, Class>();//字段类型map
	private Map<String, Object> paraMap = new HashMap<String, Object>();//查询参数Map
	
	public void addQueryPara(String name,Object value){
		paraMap.put(name, value);
	}
	
	public void addFieldType(String name,Class type){
		fieldTypeMap.put(name, type);
	}
	
	private int i=0;
	public String getNextQueryParaTag(){
		return "p"+(i++);
	}
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public String getFilters() {
		return filters;
	}
	public void setFilters(String filters) {
		this.filters = filters;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public Integer getRows() {
		rows = rows<=0?20:rows;
		rows = rows>=10000?10000:rows;
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public String getBaseSql() {
		return baseSql;
	}
	public void setBaseSql(String baseSql) {
		this.baseSql = baseSql;
	}
	public String getBaseCountSql() {
		return baseCountSql;
	}
	public void setBaseCountSql(String baseCountSql) {
		this.baseCountSql = baseCountSql;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
    
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Map<String, Class> getFieldTypeMap() {
		return fieldTypeMap;
	}
    /**
     * 得到参数MAP
     * @return Map<String, Object>
     */
	public Map<String, Object> getParaMap() {
		return paraMap;
	}
	/**
     * 设置参数MAP
     * @return
     */
	public void setParaMap(Map<String, Object> paraMap) {
		this.paraMap = paraMap;
	}
	
	public Long getQid() {
		return qid;
	}

	public void setQid(Long qid) {
		this.qid = qid;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public Integer getTrueSql() {
		return trueSql;
	}

	public void setTrueSql(Integer trueSql) {
		this.trueSql = trueSql;
	}
	
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getFormatCols() {
		return formatCols;
	}

	public void setFormatCols(String formatCols) {
		this.formatCols = formatCols;
	}

	public String getDataBaseSource() {
		return dataBaseSource;
	}

	public void setDataBaseSource(String dataBaseSource) {
		this.dataBaseSource = dataBaseSource;
	}
	
}
