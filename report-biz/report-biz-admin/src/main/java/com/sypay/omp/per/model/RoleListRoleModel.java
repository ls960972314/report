package com.sypay.omp.per.model;

public class RoleListRoleModel {
	// roleCode
	private String id;
	
	// roleName
	private String text;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RoleListRoleModel [id=").append(id).append(", text=")
				.append(text).append("]");
		return builder.toString();
	}
}
