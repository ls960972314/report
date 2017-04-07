package com.report.facade.entity;

import java.io.Serializable;

import lombok.Data;
/**
 * 分页辅助类
 * @author lishun
 * @since 2017年4月6日 下午5:42:05
 */
@Data
public class PageHelper implements Serializable {
	
	private static final long serialVersionUID = -8626409684017807986L;

	private int page;// 当前页
	private int rows = 10;// 每页显示记录数
	private String sort;// 排序字段
	private String order;// asc/desc

	public PageHelper() {
	}
	
	public PageHelper(int page, int rows) {
		this();
		this.page = page;
		this.rows = rows;
	}


}
