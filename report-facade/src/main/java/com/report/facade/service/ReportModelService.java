package com.report.facade.service;

import java.util.List;

import com.report.facade.entity.dto.ReportModel;
import com.report.facade.entity.dto.ReportPublishConfig;
import com.report.facade.entity.query.ReportModelVO;


/** 
 * 报表模板功能Service
 * @author lishun
 */
public interface ReportModelService {
	
	/**
	 * 根据modelId组装返回给页面的ReportModelVO  List
	 * @param modelId
	 * @return
	 */
    public ReportModelVO queryReportModelByModelId(String modelName);

    
    /**
     * 根据模板发送邮件
     * @param reportModelVO
     * @param selfConList 
     */
    public boolean sendMail(ReportModelVO reportModelVO,String condition,String rootPath,String titleGS,String contentGS,String mNameGS,String ReceiveAdd, String selfConList,String loginName) throws Exception;


    /**
     * 保存模板
     * @param reportModel
     */
    public void saveReportModel(ReportModel reportModel) throws Exception;
    
    /**
     * 保存报表配置
     * @param reportPublishConfig
     */
    public void saveReportPublishConfig(ReportPublishConfig reportPublishConfig) throws Exception;

    /**
     * 查询ReportModel 列表
     * @return
     */
	public List<ReportModel> queryReportModelList(String flag) throws Exception;

	/**
	 * 根据modelId查找ReportModel
	 * @param id
	 */
	public ReportModel queryReportModelByModelId(Long id) throws Exception;

	/**
	 * 根据modelId查找ReportPublishConfig 列表
	 * @param id
	 */
	public List<ReportPublishConfig> queryReportPublishConfigListByModelId(Long modelId) throws Exception;

	/**
	 * 修改模板
	 * @param reportModel
	 */
	public void updateReportModel(ReportModel reportModel) throws Exception;

	/**
	 * 修改报表信息
	 * @param reportPublishConfig
	 * @throws Exception
	 */
	public void updateReportPublishConfig(ReportPublishConfig reportPublishConfig) throws Exception;

	/**
	 * 根据modelID删除对应的所有ReportPublishConfig
	 * @param modelId
	 */
	public void deleteAllReportPublishConfigByModelId(Long modelId) throws Exception;

	/**
	 * 根据modelId删除ReportModel及ReportPublishConfig
	 * @param modelId
	 */
	public void deleteModelDetailByModelId(Long modelId) throws Exception;
}
