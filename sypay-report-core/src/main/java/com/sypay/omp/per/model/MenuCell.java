package com.sypay.omp.per.model;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单数据类
 * 
 * @author nieminjie
 *
 */
public class MenuCell implements Comparable<MenuCell>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7966280606913591115L;
    private Long id;// ID
    private String code;// 资源编码
    private String text; // 资源名称
    private String url;// 资源url
    private String resourceType;// 资源类型
    private String icon; // 图标
    private Integer orderBy;// 排序
    private String description;// 描述
    private Long pId;// 父id
    private List<MenuCell> children;
    private String menuType; // 菜单类型 omp：报表 per：权限

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public List<MenuCell> getChildren() {
        return children;
    }

    public void setChildren(List<MenuCell> children) {
        this.children = children;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPId() {
        return pId;
    }

    public void setPId(Long pId) {
        this.pId = pId;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public int compareTo(MenuCell o) {
        if (o == null || o.getOrderBy() == null) {
            return 1;
        }

        if (this.orderBy == null) {
            return -1;
        }

        return this.orderBy - o.getOrderBy();
    }

}
