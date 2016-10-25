package com.sypay.omp.per.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 资源
 * 
 * @author dumengchao
 * @date 2014-10-14
 */
@Entity
@Table(name = "uc_resource")
public class Resource implements Serializable {
	private static final long serialVersionUID = 1L;
	public Long id;
	private String resourceCode;
	private String name;
	private String resourceAction; //资源地址
	private String resourceType;  //资源类型 module:系统模块 menu:菜单 op：操作 select: 页面下拉页面 button:按钮
	private String icon;
	private String description;
	private Integer orderBy;
	private Resource parent;
	private List<Resource> children;
	private Date createTime;
	private Date updateTime;
	private String sysCode; // 所属系统编码
	private Integer status; // 1:有效 0:无效

	public Resource() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	/*@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="sequence")
	@SequenceGenerator(name="sequence", sequenceName="seq_uc_resource_id", allocationSize=1)*/
	@Column(name ="id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "RESOURCE_CODE")
	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "RESOURCE_ACTION")
	public String getResourceAction() {
		return resourceAction;
	}

	public void setResourceAction(String resourceAction) {
		this.resourceAction = resourceAction;
	}

	@Column(name = "SYS_CODE")
	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "order_by")
	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "p_id")
	public Resource getParent() {
		return parent;
	}

	public void setParent(Resource parent) {
		this.parent = parent;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	@OrderBy("orderBy")
	public List<Resource> getChildren() {
		return children;
	}

	public void setChildren(List<Resource> children) {
		this.children = children;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "icon")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "resource_type")
	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}