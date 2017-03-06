package com.sypay.omp.report.VO;

import java.util.List;

import com.sypay.omp.report.domain.ReportModel;

/**
 * 修改、删除模板时返回的详细信息
 * @author 887961
 *
 */
public class ModelUpdateDetail {

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
