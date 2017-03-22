package com.report.common.dal.admin.entity.vo;

import java.util.Date;

public class MemberModel {
	private Long id;
	private String accNo;
	private String name;
	private int status;
	private String groupCode;
	private String groupName;
	private int casType;
	private Date createTime;
	private Date updateTime;
	private String createrAccNo;
	private String modifierAccNo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public int getCasType() {
		return casType;
	}

	public void setCasType(int casType) {
		this.casType = casType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MemberModel [id=").append(id).append(", accNo=")
				.append(accNo).append(", name=").append(name)
				.append(", status=").append(status).append(", groupCode=")
				.append(groupCode).append(", groupName=").append(groupName)
				.append(", casType=").append(casType).append(", createTime=")
				.append(createTime).append(", updateTime=").append(updateTime)
				.append(", createrAccNo=").append(createrAccNo)
				.append(", modifierAccNo=").append(modifierAccNo).append("]");
		return builder.toString();
	}
}
