package com.report.common.dal.admin.entity.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PackageResourceModel implements Comparable<PackageResourceModel>, Serializable {

    private static final long serialVersionUID = 1L;

    public Long id;
    private String resourceAction;
    private String resourceCode;
    private String name;
    private String text;
    private String description;
    private String resourceType;
    private Date createTime;
    private Date updateTime;
    private Long pId;
    private String state = "closed";
    private boolean checked;
    private String sysCode;
    private Integer orderBy;
    private List<PackageResourceModel> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceAction() {
        return resourceAction;
    }

    public void setResourceAction(String resourceAction) {
        this.resourceAction = resourceAction;
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

    public List<PackageResourceModel> getChildren() {
        return children;
    }

    public void setChildren(List<PackageResourceModel> children) {
        this.children = children;
        if (this.getChildren().size() == 0) {
            this.state = "";
        }
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public int compareTo(PackageResourceModel o) {
        if (o == null || o.getOrderBy() == null) {
            return 1;
        }
        if (this.orderBy == null) {
            return -1;
        }
        return this.orderBy - o.getOrderBy();
    }

}
