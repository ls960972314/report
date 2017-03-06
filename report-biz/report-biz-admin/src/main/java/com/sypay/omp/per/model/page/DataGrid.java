package com.sypay.omp.per.model.page;

import java.util.ArrayList;
import java.util.List;

public class DataGrid {

	// 总行数
	private Long total = 0L;
	// list数据
	private List rows = new ArrayList();

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}
}
