package com.report.biz.admin.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.report.biz.admin.service.ReportPublicService;
import com.report.biz.admin.service.ReportSqlService;
import com.report.common.dal.query.entity.dto.ReportChart;
import com.report.common.dal.query.entity.dto.ReportCondition;
import com.report.common.dal.query.entity.dto.ReportPublic;
import com.report.common.dal.query.entity.vo.Condition;
import com.report.common.dal.query.entity.vo.ReportElement;
import com.report.common.dal.query.util.BeanUtil;
import com.report.common.model.DataGrid;
import com.report.common.model.PageHelper;
import com.report.common.model.query.ReportPublicVO;
import com.report.common.model.query.ReportChartVO;
import com.report.common.repository.ReportChartRepository;
import com.report.common.repository.ReportConditionRepository;
import com.report.common.repository.ReportPublicRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * ReportPublicServiceImpl
 * @author lishun
 * @since 2017年5月3日 下午8:13:22
 */
@Slf4j
@Service
public class ReportPublicServiceImpl implements ReportPublicService {

    @Resource
    private ReportPublicRepository reportPublicRepository;
    
    @Resource
    private ReportConditionRepository reportConditionRepository;
    
    @Resource
    private ReportChartRepository reportChartRepository;
    
    @Resource
    private ReportSqlService reportSqlService;

    
    @Override
    @Transactional(rollbackFor=Exception.class)
    public void saveReportPublic(ReportPublicVO reportPublicVO) {
    	ReportPublic reportPublic = new ReportPublic();
	    BeanUtil.copyProperties(reportPublicVO, reportPublic);
    	reportPublicRepository.saveReportPublic(reportPublic);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void updateReportPublic(ReportPublicVO reportPublicVO) {
    	ReportPublic reportPublic = new ReportPublic();
	    BeanUtil.copyProperties(reportPublicVO, reportPublic);
    	reportPublicRepository.updateReportPublic(reportPublic);
    }

    @Override
    public ReportPublic queryReportPublic(String toolFlag) {
        return reportPublicRepository.queryReportPublic(toolFlag);
    }

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void saveReport(ReportElement reportElement) {
		try {
			/* 公共信息 */
	        ReportPublic reportPublic = new ReportPublic();
	        reportPublic.setToolCColumn(reportElement.getSaveReportCColumn());
	        reportPublic.setToolTitle(reportElement.getSaveReportTitle());
	        reportPublic.setToolEColumn(reportElement.getSaveReportEColumn());
	        reportPublic.setToolFlag(reportElement.getSaveReportFlag());
	        reportPublic.setToolGather(reportElement.getSaveReportToolGather());
	        reportPublic.setToolFormat(reportElement.getSaveReportFormatDatas());
	        //保存统计sql
			if (StringUtils.isNotBlank(reportElement.getStaticSql())
					&& StringUtils.isNotBlank(reportElement.getStaticCcolumn())
					&& StringUtils.isNotBlank(reportElement.getStaticDataBaseSource())
					&& reportElement.getStaticRowNum() != null) {
				reportPublic.setStaticCcolumn(reportElement.getStaticCcolumn());
		        reportPublic.setStaticRowNum(reportElement.getStaticRowNum());
				reportElement.setStaticSql(reportElement.getStaticSql().replace("SELECT", "select"));
		        reportElement.setStaticSql(reportElement.getStaticSql().replace("WITH", "with"));
		        reportElement.setStaticSql(reportElement.getStaticSql().replace("ORDER", "order"));
		        reportElement.setStaticSql(reportElement.getStaticSql().replace("FROM", "from"));
				Long staticSqlId = reportSqlService.saveReportSql(reportElement.getStaticSql(),
						reportElement.getSaveReportFlag() + "统计SQL", reportElement.getStaticDataBaseSource());
				if (staticSqlId == null) {
					throw new Exception("saveReportSql Exception");
				}
				reportPublic.setStaticSql(staticSqlId);
			}
	        
	        reportPublic = updateSql(reportPublic, reportElement.getSaveReportSqlId());//{"时":"2000","日":"2001","周":"2001","月":"2001"}
	        reportPublicRepository.saveReportPublic(reportPublic);
	        /* 存条件 */
	        List<Condition> conList = JSON.parseArray(reportElement.getSaveReportCondition(), Condition.class);
	        for (Condition con : conList) {
	            ReportCondition reportCondition = new ReportCondition();
	            reportCondition.setConMuti(con.getConValue());
	            reportCondition.setConName(con.getName());
	            reportCondition.setConOption(con.getOption());
	            reportCondition.setConType(con.getType());
	            reportCondition.setConWhere(con.getWhere());
	            reportCondition.setToolFlag(reportElement.getSaveReportFlag());
	            reportCondition.setOrderNum(con.getOrderNum());
	            reportCondition.setRowNum(con.getRowNum());
	            reportCondition.setConDefaultValue(con.getConDefaultValue());
	            reportCondition.setDataBaseSource(con.getDataBaseSource());
	            reportConditionRepository.saveReportCondition(reportCondition);
	        }
	        
	        /* 存图 */
	        List<ReportChartVO> chartList = JSON.parseArray(reportElement.getSaveReportChart(), ReportChartVO.class);
	        if (reportElement.getSaveChartFlag()) {
	        	for (ReportChartVO chart : chartList) {
	        		ReportChart reportChart = new ReportChart();
	                reportChart.setChartName(chart.getChartName());
	                reportChart.setChartOption(chart.getChartOption());
	                reportChart.setChartOrder(chart.getChartOrder());
	                reportChart.setChartType(chart.getChartType());
	                reportChart.setDataVsLe(chart.getDataVsLe());
	                reportChart.setDataVsX(chart.getDataVsX());
	                reportChart.setShowRowNum(chart.getShowRowNum());
	                reportChart.setToolFlag(reportElement.getSaveReportFlag());
	                reportChartRepository.saveReportChart(reportChart);
	        	}
	        }
		} catch (Exception e) {
			log.error("saveReport Exception", e);
		}
	}
	
	public ReportPublic updateSql(ReportPublic reportPublic , String originalStr) {
        /* String originalStr = "{\"时\":\"2000\",\"日\":\"2001\",\"周\":\"2001\",\"月\":\"2001\"}"; */
        originalStr = originalStr.substring(1, originalStr.length()-1);
        String [] strs = originalStr.split(","); //["时":"2000","时":"2000","时":"2000"]
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("\"时\"", "setToolHSqlId");
        map.put("\"日\"", "setToolDSqlId");
        map.put("\"周\"", "setToolWSqlId");
        map.put("\"月\"", "setToolMSqlId");
        map.put("\"季\"", "setToolQSqlId");
        map.put("\"年\"", "setToolYSqlId");
        Class<?> r = ReportPublic.class;
        for (String str : strs) {
        	String[] s = str.split(":");
        	Method m;
			try {
				m = r.getMethod(map.get(s[0]).toString() , String.class);
				m.invoke(reportPublic, s[1].replace("\"", ""));
			} catch (NoSuchMethodException e) {
				log.info("reflect exception : {}" + e);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
        }
        return reportPublic;
    }

	@Override
	public DataGrid findPublicList(ReportPublicVO publicVo, PageHelper pageHelper) {
		DataGrid dataGrid = new DataGrid();
		Map<String, Object> params = new HashMap<String, Object>();
        
        if (StringUtils.isNotBlank(publicVo.getToolFlag())) {
        	params.put("toolFlag", publicVo.getToolFlag());
        }
        if (StringUtils.isNotBlank(publicVo.getToolTitle())) {
        	params.put("toolTitle", publicVo.getToolTitle());
        }
		dataGrid.setRows(reportPublicRepository.findPubList(params, pageHelper.getPage(), pageHelper.getRows()));
		dataGrid.setTotal(reportPublicRepository.findPubCount(params));
		return dataGrid;
	}
}
