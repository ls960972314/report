package com.sypay.omp.per.service;

import java.util.List;

import com.sypay.omp.per.model.RoleCell;

public interface PermissionFactoryService {

    List<String> findPermissionList(Long memberId);

	List<RoleCell> findSysRolePermission();

	List<RoleCell> findSysRolePermissionBySysCode(String sysCode);

}
