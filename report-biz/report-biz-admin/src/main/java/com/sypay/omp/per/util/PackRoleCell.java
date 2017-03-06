package com.sypay.omp.per.util;

import java.util.ArrayList;
import java.util.List;

import com.sypay.omp.per.domain.Resource;
import com.sypay.omp.per.domain.Role;
import com.sypay.omp.per.domain.RoleRes;
import com.sypay.omp.per.model.PermissionCell;
import com.sypay.omp.per.model.RoleCell;

public class PackRoleCell {

    public static RoleCell packageRoleResource(Role role) {
        if (role == null) return null;
        List<Role> roleList = new ArrayList<Role>();
        roleList.add(role);
        List<RoleCell> roleCellList = packageRoleResource(roleList);
        return roleCellList != null && !roleCellList.isEmpty() ? roleCellList.get(0) : null;
    }

    public static List<RoleCell> packageRoleResource(List<Role> roleList) {
        List<RoleCell> roleCellList = new ArrayList<RoleCell>();

        if (roleList == null || roleList.isEmpty()) {
            return roleCellList;
        }

        RoleCell roleCell = null;
        for (Role role : roleList) {
            roleCell = new RoleCell();
            copyRoleProperties(role, roleCell);
            List<PermissionCell> permissionCellList = new ArrayList<PermissionCell>();
            PermissionCell permissionCell = null;
            if (role != null && role.getRoleRes() != null && !role.getRoleRes().isEmpty()) {
                for (RoleRes rr : role.getRoleRes()) {
                    //  || ResourceType.MODULE.equals(rr.getResource().getResourceType())
                    if (rr.getResource() == null && rr.getResource().getStatus().intValue() == 0) {
                        continue;
                    }
                    permissionCell = new PermissionCell();
                    copyResourceProperties(rr.getResource(), permissionCell);
                    permissionCellList.add(permissionCell);
                }
                roleCell.setResources(permissionCellList);
            }

            roleCellList.add(roleCell);
        }
        return roleCellList;
    }

    private static void copyResourceProperties(Resource resource, PermissionCell permissionCell) {
        permissionCell.setId(resource.getId());
        permissionCell.setResourceCode(resource.getResourceCode());
        permissionCell.setName(resource.getName());
        permissionCell.setResourceAction(resource.getResourceAction());
        permissionCell.setResourceType(resource.getResourceType());
        permissionCell.setIcon(resource.getIcon());
        permissionCell.setOrderBy(resource.getOrderBy());
        permissionCell.setStatus(resource.getStatus());
        permissionCell.setSysCode(resource.getSysCode());
        permissionCell.setCreateTime(resource.getCreateTime());
        permissionCell.setUpdateTime(resource.getUpdateTime());
        permissionCell.setDescription(resource.getDescription());
        //  && !ResourceType.MODULE.equals(resource.getParent().getResourceType())
        if (resource.getParent() != null) {
            permissionCell.setpId(resource.getParent().getId());
        }

    }

    private static void copyRoleProperties(Role role, RoleCell roleCell) {
        roleCell.setId(role.getId());
        roleCell.setRoleCode(role.getRoleCode());
        roleCell.setName(role.getName());
        roleCell.setSysCode(role.getSysCode());
        roleCell.setDescription(role.getDescription());
        roleCell.setCreateTime(role.getCreateTime());
        roleCell.setUpdateTime(role.getUpdateTime());
    }
}
