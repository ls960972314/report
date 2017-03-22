package com.report.common.dal.admin.entity.vo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GroupModel {

    private Long id;
    // 组code
    private String groupCode;
    // 当前人拥有组
    private String currentMemberGroupCode;
    // 组名称
    private String groupName;
    // 状态 0：无效 1:有效
    private Integer status;
    private List<Map<String, String>> roleNames = new ArrayList<Map<String, String>>();
    private List<String> roleCodes = new LinkedList<String>();;
    private String createTime;
    private String updateTime;
    private String createrAccNo;
    private String modifierAccNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getCurrentMemberGroupCode() {
        return currentMemberGroupCode;
    }

    public void setCurrentMemberGroupCode(String currentMemberGroupCode) {
        this.currentMemberGroupCode = currentMemberGroupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    public List<Map<String, String>> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<Map<String, String>> roleNames) {
        this.roleNames = roleNames;
        for (Map<String, String> map : roleNames) {
            this.roleCodes.add(map.get("roleCode"));
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GroupModel [id=").append(id).append(", groupCode=").append(groupCode).append(", groupName=").append(groupName).append(", status=").append(status).append(", roleNames=")
                .append(roleNames).append(", roleCodes=").append(roleCodes).append(", createTime=").append(createTime).append(", updateTime=").append(updateTime).append(", createrAccNo=")
                .append(createrAccNo).append(", modifierAccNo=").append(modifierAccNo).append("]");
        return builder.toString();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreaterAccNo() {
        return createrAccNo;
    }

    public void setCreaterAccNo(String createrAccNo) {
        this.createrAccNo = createrAccNo;
    }

    public String getModifierAccNo() {
        return modifierAccNo;
    }

    public void setModifierAccNo(String modifierAccNo) {
        this.modifierAccNo = modifierAccNo;
    }
}
