package com.sypay.omp.per.model;

import java.io.Serializable;
import java.security.acl.Permission;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * 资源
 * 
 * @author dumengchao
 * @date 2014-10-14
 */
public class PermissionCell implements Serializable, Comparable<PermissionCell> {

    private static final long serialVersionUID = 1L;
    public Long id;
    private String resourceCode;// 资源编码
    private String name; //资源名称
    private String resourceAction;// 资源action
    private String resourceType;// 资源类型 :menu：菜单 op：操作
    private String icon; // 图标
    private String description;
    private Integer orderBy;// 排序
    private Long pId; //父id
    private Date createTime;
    private Date updateTime;
    private String sysCode; // 系统编码
    private Integer status; // 1:有效 0:无效

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceAction() {
        return resourceAction;
    }

    public void setResourceAction(String resourceAction) {
        this.resourceAction = resourceAction;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
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

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    @Override
    public int compareTo(PermissionCell o) {
        if (o == null) {
            return 1;
        }
        if (this.orderBy == null) {
            return 0;
        }

        if (o.getOrderBy() == null) {
            return 1;
        }
        int result = this.orderBy - o.getOrderBy();
        return result;
    }

}