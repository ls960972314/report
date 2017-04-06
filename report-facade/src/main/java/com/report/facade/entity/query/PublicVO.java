package com.report.facade.entity.query;

import java.io.Serializable;

/**
 * 
 * @author lishun
 *
 */
public class PublicVO implements Serializable {

	private static final long serialVersionUID = 1106029269074628231L;
	
	private Integer id;
	private String toolFlag;
	private String toolTitle;
	private String toolCColumn;
	private String toolEColumn;
	private String toolGather;
	private String toolFormat;
	private String toolHSqlId;
	private String toolDSqlId;
	private String toolWSqlId;
	private String toolMSqlId;
	private String toolQSqlId;
	private String toolYSqlId;
	// 0815新增
    private Integer staticRowNum;
    private Long staticSql;
    private String staticCcolumn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getToolTitle() {
		return toolTitle;
	}

	public void setToolTitle(String toolTitle) {
		this.toolTitle = toolTitle;
	}

	public String getToolFlag() {
		return toolFlag;
	}

	public void setToolFlag(String toolFlag) {
		this.toolFlag = toolFlag;
	}

	public String getToolCColumn() {
		return toolCColumn;
	}

	public void setToolCColumn(String toolCColumn) {
		this.toolCColumn = toolCColumn;
	}

	public String getToolEColumn() {
		return toolEColumn;
	}

	public void setToolEColumn(String toolEColumn) {
		this.toolEColumn = toolEColumn;
	}

	public String getToolHSqlId() {
		return toolHSqlId;
	}

	public void setToolHSqlId(String toolHSqlId) {
		this.toolHSqlId = toolHSqlId;
	}

	public String getToolDSqlId() {
		return toolDSqlId;
	}

	public void setToolDSqlId(String toolDSqlId) {
		this.toolDSqlId = toolDSqlId;
	}

	public String getToolWSqlId() {
		return toolWSqlId;
	}

	public void setToolWSqlId(String toolWSqlId) {
		this.toolWSqlId = toolWSqlId;
	}

	public String getToolMSqlId() {
		return toolMSqlId;
	}

	public void setToolMSqlId(String toolMSqlId) {
		this.toolMSqlId = toolMSqlId;
	}

	public String getToolGather() {
		return toolGather;
	}

	public void setToolGather(String toolGather) {
		this.toolGather = toolGather;
	}

	public String getToolFormat() {
		return toolFormat;
	}

	public void setToolFormat(String toolFormat) {
		this.toolFormat = toolFormat;
	}

	public String getToolQSqlId() {
		return toolQSqlId;
	}

	public void setToolQSqlId(String toolQSqlId) {
		this.toolQSqlId = toolQSqlId;
	}

	public String getToolYSqlId() {
		return toolYSqlId;
	}

	public void setToolYSqlId(String toolYSqlId) {
		this.toolYSqlId = toolYSqlId;
	}

	public Integer getStaticRowNum() {
		return staticRowNum;
	}

	public void setStaticRowNum(Integer staticRowNum) {
		this.staticRowNum = staticRowNum;
	}

	public Long getStaticSql() {
		return staticSql;
	}

	public void setStaticSql(Long staticSql) {
		this.staticSql = staticSql;
	}

	public String getStaticCcolumn() {
		return staticCcolumn;
	}

	public void setStaticCcolumn(String staticCcolumn) {
		this.staticCcolumn = staticCcolumn;
	}
	
}
