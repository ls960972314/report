package com.report.common.dal.admin.entity.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author lishun
 * @since 2017年3月6日 下午4:32:39
 */
@Entity
@Table(name = "uc_role")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String roleCode;
	private String name;
	private String description;
	private Date createTime;
	private Date updateTime;
	private String sysCode;// 组编码
	private Integer status;// 0:无效 1:有效
	private Long createrId;
	private Long modifierId;
	private List<RoleRes> roleRes;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name ="id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "role_code", length = 64)
	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@OneToMany(fetch=FetchType.EAGER, mappedBy = "role", cascade=CascadeType.ALL)
	public List<RoleRes> getRoleRes() {
		return roleRes;
	}

	public void setRoleRes(List<RoleRes> roleRes) {
		this.roleRes = roleRes;
	}

	@Column(name = "sys_code")
	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "CREATER_ID")
	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	@Column(name = "MODIFIER_ID")
	public Long getModifierId() {
		return modifierId;
	}

	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
	}
}