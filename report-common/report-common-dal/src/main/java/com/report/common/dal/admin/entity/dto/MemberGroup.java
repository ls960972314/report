package com.report.common.dal.admin.entity.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lishun
 * @since 2017年3月6日 下午4:32:06
 */
@Entity
@Table(name = "UC_MEMBER_GROUP")
public class MemberGroup implements Serializable {

	private static final long serialVersionUID = 9121382053719186564L;
	// id
	private Long id;
	// 会员id
	private Long memberId;
	// 组code
	private String groupCode;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;
	// 状态 0：无效 1:有效
	private Integer status;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
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

	@Column(name = "MEMBER_ID", nullable = false, precision = 11, scale = 0)
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@Column(name = "CREATE_TIME", nullable = true)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_TIME", nullable = true)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "STATUS", nullable = true, precision = 1, scale = 0)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
