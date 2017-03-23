package com.report.facade.entity.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 模板实体类
 * @author lishun
 *
 * @2016年3月4日
 */
@Entity
@Table(name="RPTMODEL")
public class ReportModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6218205910807478374L;
	/**
	 * 主键
	 */
	@Id
    @Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	/*@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_RPTMODEL")
    @SequenceGenerator(name="SEQ_RPTMODEL",sequenceName="SEQ_RPTMODEL",allocationSize=1)*/
	private Long id;
	
	/**
	 *模板名 
	 */
	@Column(name="modelname")
	private String modelName;
	
	/**
	 * 模板标题
	 */
	@Column(name="modeltitle")
	private String modelTitle;

	/**
	 * 模板条件名(逗号隔开)
	 */
	@Column(name="conname")
	private String conname;
	
	/**
	 * 邮件接收人(逗号隔开)
	 */
	@Column(name="send_usernames")
	private String sendUsernames;
	
	/**
	 * 创建时间
	 */
	@Column(name="create_time")
	private Date createTime; 
	
	/**
	 * 创建类型
	 */
	@Column(name="save_type")
	private String saveType;
	public ReportModel() {
	}
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getConname() {
		return conname;
	}

	public void setConname(String conname) {
		this.conname = conname;
	}

	public String getSendUsernames() {
		return sendUsernames;
	}

	public void setSendUsernames(String sendUsernames) {
		this.sendUsernames = sendUsernames;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModelTitle() {
		return modelTitle;
	}

	public void setModelTitle(String modelTitle) {
		this.modelTitle = modelTitle;
	}

	public String getSaveType() {
		return saveType;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}
    
}
