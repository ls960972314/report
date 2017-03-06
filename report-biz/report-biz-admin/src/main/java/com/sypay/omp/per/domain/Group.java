package com.sypay.omp.per.domain;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**   
 *
 * @Description: 组
 * @date 2014-10-14 14:10:21
 * @version V1.0   
 *
 */
@Entity
@Table(name = "uc_group")
public class Group implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    
	// id
	private Long id;
	// 组code
	private String groupCode;
	// 组名称
	private String groupName;
	// 组类型 1:对内 2:对外
	private int groupType;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;
	// 状态 0：无效 1:有效
	private Integer status;
	// 创建人id
	private Long createrId;
	// 修改人id
	private Long modifierId;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	/*@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="sequence")
	@SequenceGenerator(name="sequence",sequenceName="SEQ_UC_GROUP",allocationSize=1)*/
	@Column(name ="id",nullable=false,precision=11,scale=0)
	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	@Column(name ="GROUP_CODE",nullable=true,length=24)
	public String getGroupCode(){
		return groupCode;
	}

	public void setGroupCode(String groupCode){
		this.groupCode = groupCode;
	}

	@Column(name ="GROUP_NAME",nullable=true,length=100)
	public String getGroupName(){
		return groupName;
	}

	public void setGroupName(String groupName){
		this.groupName = groupName;
	}

	@Column(name ="GROUP_TYPE")
	public int getGroupType() {
		return groupType;
	}

	public void setGroupType(int groupType) {
		this.groupType = groupType;
	}

	@Column(name ="CREATE_TIME",nullable=true)
	public Date getCreateTime(){
		return createTime;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	@Column(name ="UPDATE_TIME",nullable=true)
	public Date getUpdateTime(){
		return updateTime;
	}

	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}

	@Column(name ="STATUS",nullable=true,precision=1,scale=0)
	public Integer getStatus(){
		return status;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	@Column(name ="CREATER_ID")
	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	@Column(name ="MODIFIER_ID")
	public Long getModifierId() {
		return modifierId;
	}

	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
	}
}
