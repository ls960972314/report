package com.report.common.dal.admin.entity.vo;

import java.util.ArrayList;
import java.util.List;

public class RoleListSysModel {
	// sysCode
	private String id;
	
	// sysName
	private String text;
	
	private boolean checked;
	
	private List<RoleListSysModel> children = new ArrayList<RoleListSysModel>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RoleListSysModel [id=").append(id).append(", text=")
				.append(text).append(", children=").append(getChildren())
				.append("]");
		return builder.toString();
	}

	public List<RoleListSysModel> getChildren() {
		return children;
	}

	public void setChildren(List<RoleListSysModel> children) {
		this.children = children;
	}
}
