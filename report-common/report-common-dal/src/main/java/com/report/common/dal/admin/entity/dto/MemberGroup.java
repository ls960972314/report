package com.report.common.dal.admin.entity.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 用户与组的关系
 * @author lishun
 * @since 2017年3月6日 下午4:32:06
 */
@Data
@Entity
@Table(name = "UC_MEMBER_GROUP")
public class MemberGroup implements Serializable {

	private static final long serialVersionUID = 9121382053719186564L;
	/** id */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name = "ID", nullable = false, precision = 11, scale = 0)
	private Long id;
	/** 用户id */
	@Column(name = "MEMBER_ID", nullable = false, precision = 11, scale = 0)
	private Long memberId;
	/** 组code */
	@Column(name = "GROUP_CODE", nullable = true, length = 64)
	private String groupCode;
}
