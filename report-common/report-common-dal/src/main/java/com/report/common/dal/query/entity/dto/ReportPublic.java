package com.report.common.dal.query.entity.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lishun
 *
 * @2015年5月5日
 */
@Entity
@Table(name = "RPTPUB")
public class ReportPublic implements Serializable {

	private static final long serialVersionUID = 727016893684040578L;
	
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

    public ReportPublic() {
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    /*@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_RPTPUB")
    @SequenceGenerator(name="SEQ_RPTPUB",sequenceName="SEQ_RPTPUB",allocationSize=1)*/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name = "TOOLTITLE")
    public String getToolTitle() {
        return toolTitle;
    }

    public void setToolTitle(String toolTitle) {
        this.toolTitle = toolTitle;
    }
    @Column(name = "TOOLFLAG")
    public String getToolFlag() {
        return toolFlag;
    }

    public void setToolFlag(String toolFlag) {
        this.toolFlag = toolFlag;
    }

    @Column(name = "TOOLCCOLUMN")
    public String getToolCColumn() {
        return toolCColumn;
    }

    public void setToolCColumn(String toolCColumn) {
        this.toolCColumn = toolCColumn;
    }

    @Column(name = "TOOLECOLUMN")
    public String getToolEColumn() {
        return toolEColumn;
    }

    public void setToolEColumn(String toolEColumn) {
        this.toolEColumn = toolEColumn;
    }

    @Column(name = "TOOLHSQLID")
    public String getToolHSqlId() {
        return toolHSqlId;
    }

    public void setToolHSqlId(String toolHSqlId) {
        this.toolHSqlId = toolHSqlId;
    }

    @Column(name = "TOOLDSQLID")
    public String getToolDSqlId() {
        return toolDSqlId;
    }

    public void setToolDSqlId(String toolDSqlId) {
        this.toolDSqlId = toolDSqlId;
    }

    @Column(name = "TOOLWSQLID")
    public String getToolWSqlId() {
        return toolWSqlId;
    }

    public void setToolWSqlId(String toolWSqlId) {
        this.toolWSqlId = toolWSqlId;
    }

    @Column(name = "TOOLMSQLID")
    public String getToolMSqlId() {
        return toolMSqlId;
    }

    public void setToolMSqlId(String toolMSqlId) {
        this.toolMSqlId = toolMSqlId;
    }

    @Column(name = "GATHER_COLUMN")
	public String getToolGather() {
		return toolGather;
	}

	public void setToolGather(String toolGather) {
		this.toolGather = toolGather;
	}

	@Column(name = "FORMAT")
	public String getToolFormat() {
		return toolFormat;
	}

	public void setToolFormat(String toolFormat) {
		this.toolFormat = toolFormat;
	}

	@Column(name = "TOOLQSQLID")
	public String getToolQSqlId() {
		return toolQSqlId;
	}

	public void setToolQSqlId(String toolQSqlId) {
		this.toolQSqlId = toolQSqlId;
	}

	@Column(name = "TOOLYSQLID")
	public String getToolYSqlId() {
		return toolYSqlId;
	}

	public void setToolYSqlId(String toolYSqlId) {
		this.toolYSqlId = toolYSqlId;
	}
	@Column(name = "static_rownum")
	public Integer getStaticRowNum() {
		return staticRowNum;
	}

	public void setStaticRowNum(Integer staticRowNum) {
		this.staticRowNum = staticRowNum;
	}
	@Column(name = "static_sql")
	public Long getStaticSql() {
		return staticSql;
	}

	public void setStaticSql(Long staticSql) {
		this.staticSql = staticSql;
	}
	@Column(name = "static_ccolumn")
	public String getStaticCcolumn() {
		return staticCcolumn;
	}

	public void setStaticCcolumn(String staticCcolumn) {
		this.staticCcolumn = staticCcolumn;
	}
    
    
}
