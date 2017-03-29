package com.report.common.dal.admin.entity.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 用户实体表
 * @author lishun
 * @since 2017年3月6日 下午4:31:17
 */
@Data
@Entity
@Table(name = "UC_MEMBER")
public class Member implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/** 主键 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	/** 账号 */
	@Column(name = "ACC_NO", length = 30)
	private String accNo;
	/** 密码 */
	@Column(name = "PASSWORD", length = 32)
	private String password;
	/** 用户名 */
	@Column(name = "NAME", length = 30)
	private String name;
	/** 是否可用 */
	@Column(name = "STATUS", precision = 1, scale = 0)
	private Integer status;
	/** 创建时间 */
	@Column(name = "CREATE_TIME", length = 7)
	private Date createTime;
	/** 修改时间 */
	@Column(name = "UPDATE_TIME", length = 7)
	private Date updateTime;
}