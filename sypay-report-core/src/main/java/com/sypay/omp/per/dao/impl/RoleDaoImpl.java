package com.sypay.omp.per.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.sypay.omp.per.common.Constants.MenuType;
import com.sypay.omp.per.dao.RoleDao;
import com.sypay.omp.per.domain.Role;
import com.sypay.omp.per.model.RoleCell;
import com.sypay.omp.per.model.RoleCriteriaModel;
import com.sypay.omp.per.model.RoleListSysModel;
import com.sypay.omp.per.model.RoleModel;
import com.sypay.omp.per.model.page.PageHelper;
import com.sypay.omp.per.model.page.PageUtil;
import com.sypay.omp.per.util.MybatisUtil;
import com.sypay.omp.per.util.PackRoleCell;
import com.sypay.omp.per.util.RoleUtil;
import com.sypay.omp.per.util.SessionUtil;
import com.sypay.omp.report.dao.BaseDao;
import com.sypay.omp.report.service.MybatisBaseService;
import com.sypay.omp.report.util.ObjectUtil;

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {

    @Resource
    private BaseDao baseDao;

    @Resource
    private MybatisBaseService mybatisBaseService;

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

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, String>> findRoleNamesByGroupCode(String groupCode) {
        return mybatisBaseService.selectList("role.findRoleNamesByGroupCode", groupCode);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List getRoleListBySysCodes(Set<String> sysCodes) {
        List<Map<String, Object>> list = null;
        boolean flag = true;
        if (isPerAdmin()) {
            list = mybatisBaseService.selectList("role.findRoleList4PerAdmin", null);
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
        mybatisBaseService.update("role.deleteMappingRole2Resource", params);
    }

    @Override
    public boolean isRoleCodeExists(RoleModel roleModel) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("roleCode", roleModel.getRoleCode());
        params.put("sysCode", roleModel.getSysCode());
        if (roleModel.getId() != null) {
            params.put("targetId", roleModel.getId());
        }
        return ((Integer) mybatisBaseService.selectOne("role.countRoleByRoleCodeAndSysCode", params)) > 0;
    }

    private boolean isPerAdmin() {
        return SessionUtil.isPerAdmin();
    }

    @Override
    public List getRoleListByGroupCode(String groupCode) {
        return mybatisBaseService.selectList("role.getRoleListByGroupCode", groupCode);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getRoleCodeAndNameList(String groupCode) {
        return mybatisBaseService.selectList("role.getRoleCodeAndNameList", groupCode);
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

            return mybatisBaseService.selectList("role.findRoleListByCriteria4PerAdmin", params, PageUtil.paged(pageHelper.getPage() - 1, pageHelper.getRows()));
        } else {
            // 如果从角色列表中没有获取到roleCode，那么将当前会员视被赋予临时权限的用户，此时不显示角色列表
            List<String> roleCodes = RoleUtil.getRoleCodes();
            if (roleCodes == null || roleCodes.isEmpty()) {
                return Collections.emptyList();
            }

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("pageHelper", pageHelper);
            params.put("roleCodes", roleCodes);

            return mybatisBaseService.selectList("role.findRoleListByRoleCodes", params, PageUtil.paged(pageHelper.getPage() - 1, pageHelper.getRows()));
        }
    }

    // 将Javabean的属性名转换为数据库表中的列名
    private void handleFieldForOrdering(PageHelper pageHelper) {
        if (pageHelper.getSort() != null) {
            pageHelper.setSort(MybatisUtil.propertyName2ColumnName("role.roleModelResultMap", pageHelper.getSort()));
        }
    }

    @Override
    public Long countRoleByCriteria(RoleCriteriaModel roleCriteriaModel) {
        if (isPerAdmin()) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("roleCriteriaModel", roleCriteriaModel);
            return (Long) mybatisBaseService.selectOne("role.countRoleByCriteria", params);
        } else {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("roleCriteriaModel", roleCriteriaModel);
            params.put("roleCodes", RoleUtil.getRoleCodes());
            return (Long) mybatisBaseService.selectOne("role.countRoleByRoleCodes", params);
        }
    }

    @Override
    public boolean isSameRoleCode(Long roleId, String roleCode) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", roleId);
        params.put("roleCode", roleCode);

        return ((Integer) mybatisBaseService.selectOne("role.isSameRoleCode", params)).intValue() > 0;
    }

    @Override
    public List<Role> findAllRoles(Long memberId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberId", memberId);
        return mybatisBaseService.selectList("role.findAllRoles", params);
    }
    @Override
    public List<String> findRoleCodeByMemberId(Long memberId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberId", memberId);
        return mybatisBaseService.selectList("role.findRoleCodeByMemberId", params);
    }

}
