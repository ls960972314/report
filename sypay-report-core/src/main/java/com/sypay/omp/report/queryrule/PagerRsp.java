package com.sypay.omp.report.queryrule;

import java.util.List;

/**
 * 页面返回参数封装
 * @author lishun
 *
 */
public class PagerRsp {
	private Integer page;//当前页
	private Integer total;//总页数
	private Integer records;//总记录数
	private List rows;
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getRecords() {
		return records;
	}
	public void setRecords(Integer records) {
		this.records = records;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
}
