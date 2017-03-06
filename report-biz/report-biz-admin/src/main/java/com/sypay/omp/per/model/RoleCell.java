package com.sypay.omp.per.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author dumengchao
 * @date 2014-10-14
 */
public class RoleCell implements Serializable {
	private static final long serialVersionUID = 1L;
	public Long id;
	private String name;
	private String roleCode;
	private String description;
	private Date createTime;
	private Date updateTime;
	private String sysCode;
	private Integer status;// 0:无效 1:有效
	private List<PermissionCell> resources;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

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

	public List<PermissionCell> getResources() {
		return resources;
	}

	public void setResources(List<PermissionCell> resources) {
		this.resources = resources;
	}

}