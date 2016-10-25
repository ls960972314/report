package com.sypay.omp.per.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "UC_GROUP_ROLE")
public class GroupRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9121382053719186564L;
	// id
	private Long id;
	// 组code
	private String groupCode;
	// 角色code
	private String roleCode;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	/*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@SequenceGenerator(name = "sequence", sequenceName = "SEQ_GROUP_ROLEID", allocationSize = 1)*/
	@Column(name = "ID", nullable = false, precision = 11, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "GROUP_CODE", nullable = true, length = 64)
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name = "ROLE_CODE", nullable = true, length = 64)
	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}


}
