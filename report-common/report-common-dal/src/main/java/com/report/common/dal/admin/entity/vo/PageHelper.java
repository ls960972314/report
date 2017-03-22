package com.report.common.dal.admin.entity.vo;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;



public class PageHelper {
	
	// 存储分页总数
	private static final ThreadLocal<Long> count = new ThreadLocal<Long>(); 
	
	public static final int DEFAULT_PAGESIZE = 10;
	
	private int page;// 当前页
	private int rows = DEFAULT_PAGESIZE;// 每页显示记录数
	private String sort;// 排序字段
	private String order;// asc/desc

	public PageHelper() {
	}
	
	public PageHelper(int page, int rows) {
		this();
		this.page = page;
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	/**
	 * 对查询结果进行重新计算
	 */
	public void resetPage(){
		if(0 == rows){
			rows = DEFAULT_PAGESIZE;
		}
		
		if(page <= 0){
			page = 1;
		}
	}

	public static long getCount() {
		long allCount = 0;
		Long threadCount = count.get();
		if(null != threadCount){
			allCount = threadCount;
		}
		return allCount;
	}
	
	public static void setCount(Long count){
		PageHelper.count.set(count);
	}
	
	public PageBean toPageBean(){
		
		// 重新计算分页信息
		this.resetPage();

		// 当前页
		int pageNo = this.getPage();

		// 每页记录数
		int pageSize = this.getRows();

		// 结果集
		PageBean resultPage = new PageBean(pageNo, pageSize, 0);
		return resultPage;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	

}
