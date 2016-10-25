package com.sypay.omp.per.model;

import java.io.Serializable;
import java.util.Date;

public class RoleModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String roleCode;
	private String name;
	private String description;
	private Date createTime;
	private Date updateTime;
	private String sysCode;// 组编码
	private Integer status;// 0:无效 1:有效
	private String resourceIds; // 资源id 列表 以逗号分隔
	private String createrAccNo;
	private String modifierAccNo;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RoleModel [id=").append(id).append(", roleCode=")
				.append(roleCode).append(", name=").append(name)
				.append(", description=").append(description)
				.append(", createTime=").append(createTime)
				.append(", updateTime=").append(updateTime)
				.append(", sysCode=").append(sysCode).append(", status=")
				.append(status).append(", resourceIds=").append(resourceIds)
				.append(", createrAccNo=").append(createrAccNo)
				.append(", modifierAccNo=").append(modifierAccNo).append("]");
		return builder.toString();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getCreaterAccNo() {
		return createrAccNo;
	}

	public void setCreaterAccNo(String createrAccNo) {
		this.createrAccNo = createrAccNo;
	}

	public String getModifierAccNo() {
		return modifierAccNo;
	}

	public void setModifierAccNo(String modifierAccNo) {
		this.modifierAccNo = modifierAccNo;
	}
}