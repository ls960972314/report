package com.sypay.omp.report.service;

public interface ReportImportOneService {

	/**
	 * 得到该批次号的数量
	 * @param batchNo
	 * @return
	 */
	public Integer getBatchNoCount(String batchNo);
	/**
	 * 根据批次号删除
	 * @param batchNo
	 */
	void deleteReportConfig(String batchNo);

	/**
	 * 得到当前上传了多少条
	 * @param maxId
	 * @return
	 */
	Integer getRptImportCount(Integer maxId);

	/**
	 * 得到当前上传运营导入表一的最大ID
	 * @return
	 */
	Integer getRptMaxId();

}