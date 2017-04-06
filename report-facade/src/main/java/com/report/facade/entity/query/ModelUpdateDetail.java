package com.report.facade.entity.query;

import java.io.Serializable;
import java.util.List;

import com.report.facade.entity.dto.ReportModel;

/**
 * 修改、删除模板时返回的详细信息
 * @author lishun
 * @since 2017年4月6日 上午9:13:45
 */
public class ModelUpdateDetail implements Serializable {

	private static final long serialVersionUID = 5378109280509515282L;

	/**
	 * 报表详细元素
	 */
	List<ReportPublishVO> reportPublishList;
	
	/**
	 * 模板
	 */
	ReportModel reportModel;

	public List<ReportPublishVO> getReportPublishList() {
		return reportPublishList;
	}

	public void setReportPublishList(List<ReportPublishVO> reportPublishList) {
		this.reportPublishList = reportPublishList;
	}

	public ReportModel getReportModel() {
		return reportModel;
	}

	public void setReportModel(ReportModel reportModel) {
		this.reportModel = reportModel;
	}
}
