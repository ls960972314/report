package com.report.common.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.report.common.dal.admin.constant.Constants.MenuType;
import com.report.common.dal.admin.dao.RoleDao;
import com.report.common.dal.admin.entity.dto.Role;
import com.report.common.dal.admin.entity.vo.RoleCell;
import com.report.common.dal.admin.entity.vo.RoleCriteriaModel;
import com.report.common.dal.admin.entity.vo.RoleListSysModel;
import com.report.common.dal.admin.entity.vo.RoleModel;
import com.report.common.dal.admin.util.MybatisUtil;
import com.report.common.dal.admin.util.PackRoleCell;
import com.report.common.dal.admin.util.PageUtil;
import com.report.common.dal.admin.util.RoleUtil;
import com.report.common.dal.admin.util.SessionUtil;
import com.report.common.dal.common.BaseDao;
import com.report.common.dal.query.util.ObjectUtil;
import com.report.common.repository.RoleRepository;
import com.report.facade.entity.PageHelper;

@Service
public class RoleRepositoryImpl implements RoleRepository {
	@Resource
    private BaseDao baseDao;

    @Resource
    private RoleDao roleDao;

    @Override
    public List<Role> findRoles() {
        return baseDao.find(Role.class);
    }

    @Override
    public RoleCell getByRoleCode(String roleCode) {
        String hql = "from Role r where r.status=1 ";
        Map<String, Object> params = new HashMap<String, Object>();
        hql += " and r.roleCode = :roleCode";
        params.put("roleCode", roleCode);
        
        Role role = (Role) baseDao.get(hql, params);
        return PackRoleCell.packageRoleResource(role);
        	
    }
    @Override
    public List<Role> findCurrentSysRolesBySysCode(String sysCode) {
        String hql = "from Role r where r.status=1 ";
        Map<String, Object> params = new HashMap<String, Object>();
        hql += " and r.sysCode = :sysCode";
        params.put("sysCode", sysCode);
        return baseDao.find(hql, params);
    }

    @Override
    public List<Map<String, String>> findRoleNamesByGroupCode(String groupCode) {
        return roleDao.findRoleNamesByGroupCode(groupCode);
    }

    @Override
    public List getRoleListBySysCodes(Set<String> sysCodes) {
        List<Map<String, Object>> list = null;
        boolean flag = true;
        if (isPerAdmin()) {
            list = roleDao.findRoleList4PerAdmin();
        } else {
            flag = false;
        }

        if (list != null && flag) {
            // key: sysCode
            return toRoleListSysModel(list);
        } else {
            return Collections.emptyList();
        }
    }

    private List<RoleListSysModel> toRoleListSysModel(List<Map<String, Object>> list) {
        Map<String, RoleListSysModel> result = new HashMap<String, RoleListSysModel>();

        RoleListSysModel sysModel = null;
        RoleListSysModel roleModel = null;
        for (Map<String, Object> map : list) {
            String roleCode = ObjectUtil.toString(map.get("roleCode"));
            String roleName = ObjectUtil.toString(map.get("name"));
            String sysCo = ObjectUtil.toString(map.get("sysCode"));
            String sysName = ObjectUtil.toString(map.get("sysName"));

            if ((sysModel = result.get(sysCo)) == null) {
                sysModel = new RoleListSysModel();
                sysModel.setId(sysCo);
                if (StringUtils.isBlank(sysName) && sysCo.equals(MenuType.PERMISSION)) {
                    sysModel.setText("权限");
                } else {
                    sysModel.setText("报表");
                }

                result.put(sysCo, sysModel);
            }

            roleModel = new RoleListSysModel();
            roleModel.setId(roleCode);
            roleModel.setText(roleName);

            sysModel.getChildren().add(roleModel);
        }

        return new ArrayList<RoleListSysModel>(result.values());
    }

    @Override
    public void clearMiddleTableData(Long roleId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("roleId", roleId);
        roleDao.deleteMappingRole2Resource(params);;
    }

    @Override
    public boolean isRoleCodeExists(RoleModel roleModel) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("roleCode", roleModel.getRoleCode());
        params.put("sysCode", roleModel.getSysCode());
        if (roleModel.getId() != null) {
            params.put("targetId", roleModel.getId());
        }
        return roleDao.countRoleByRoleCodeAndSysCode(params) > 0;
    }

    private boolean isPerAdmin() {
        return SessionUtil.isPerAdmin();
    }

    @Override
    public List getRoleListByGroupCode(String groupCode) {
        return roleDao.getRoleListByGroupCode(groupCode);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getRoleCodeAndNameList(String groupCode) {
        return roleDao.getRoleCodeAndNameList(groupCode);
    }

    @Override
    public List<String> findSysCodeByRoleCodes(List<String> roleCodes) {
        Query query = baseDao.getSqlQuery("select sys_code from uc_role t where t.status=1 and t.role_code in(:roleCodes)");
        query.setParameterList("roleCodes", roleCodes);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RoleModel> findRoleListByCriteria(PageHelper pageHelper, RoleCriteriaModel roleCriteriaModel) {
        if (isPerAdmin()) {
            handleFieldForOrdering(pageHelper);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("pageHelper", pageHelper);
            params.put("roleCriteriaModel", roleCriteriaModel);
            return roleDao.findRoleListByCriteria4PerAdmin(params, PageUtil.paged(pageHelper.getPage() - 1, pageHelper.getRows()));
        } else {
            // 如果从角色列表中没有获取到roleCode，那么将当前会员视被赋予临时权限的用户，此时不显示角色列表
            List<String> roleCodes = RoleUtil.getRoleCodes();
            if (roleCodes == null || roleCodes.isEmpty()) {
                return Collections.emptyList();
            }

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("pageHelper", pageHelper);
            params.put("roleCodes", roleCodes);
            return roleDao.findRoleListByRoleCodes(params, PageUtil.paged(pageHelper.getPage() - 1, pageHelper.getRows()));
        }
    }

    @Override
    public Long countRoleByCriteria(RoleCriteriaModel roleCriteriaModel) {
        if (isPerAdmin()) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("roleCriteriaModel", roleCriteriaModel);
            return roleDao.countRoleByCriteria(params);
        } else {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("roleCriteriaModel", roleCriteriaModel);
            params.put("roleCodes", RoleUtil.getRoleCodes());
            return roleDao.countRoleByRoleCodes(params);
        }
    }

    @Override
    public boolean isSameRoleCode(Long roleId, String roleCode) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", roleId);
        params.put("roleCode", roleCode);
        
        return roleDao.isSameRoleCode(params) > 0;
    }

    @Override
    public List<Role> findAllRoles(Long memberId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberId", memberId);
        return roleDao.findAllRoles(memberId);
    }
    @Override
    public List<String> findRoleCodeByMemberId(Long memberId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberId", memberId);
        return roleDao.findRoleCodeByMemberId(memberId);
    }
    
    private void handleFieldForOrdering(PageHelper pageHelper) {
        if (pageHelper.getSort() != null) {
            pageHelper.setSort(MybatisUtil.propertyName2ColumnName("role.roleModelResultMap", pageHelper.getSort()));
        }
    }
}
