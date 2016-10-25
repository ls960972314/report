package com.sypay.omp.per.service.impl;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sypay.omp.per.common.Constants;
import com.sypay.omp.per.dao.PermissionFactoryDao;
import com.sypay.omp.per.dao.RoleDao;
import com.sypay.omp.per.domain.Role;
import com.sypay.omp.per.model.RoleCell;
import com.sypay.omp.per.service.PermissionFactoryService;
import com.sypay.omp.per.util.PackRoleCell;
import com.sypay.omp.per.util.SessionUtil;
import com.sypay.omp.report.dao.BaseDao;

@Service("permissionFactoryService")
@Transactional
public class PermissionFactoryServiceImpl implements PermissionFactoryService {

    @Autowired
    private BaseDao baseDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionFactoryDao permissionFactoryDao;

    @Override
    public List<String> findPermissionList(Long memberId) {

        List<String> roleCodes = Collections.emptyList();
        roleCodes = permissionFactoryDao.getRoleCodeByMemberId(memberId);
        return roleCodes;
    }

    /**
     * 用户登录获取系统权限信息
     */
    public List<RoleCell> findSysRolePermission() {

        HttpSession session = SessionUtil.getHttpSession();

        List<Role> roleList = roleDao.findAllRoles(Long.valueOf(String.valueOf(session.getAttribute(Constants.SESSION_LOGIN_MEMBER_ID))));

        return PackRoleCell.packageRoleResource(roleList);
    }

    /**
     * 系统初始化与刷新缓存使用
     */
    @Override
    public List<RoleCell> findSysRolePermissionBySysCode(String sysCode) {

        List<Role> roleList = roleDao.findCurrentSysRolesBySysCode(sysCode);

        return PackRoleCell.packageRoleResource(roleList);
    }


}