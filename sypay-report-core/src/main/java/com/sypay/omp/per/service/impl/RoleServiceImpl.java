package com.sypay.omp.per.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sypay.omp.per.common.Constants;
import com.sypay.omp.per.common.Constants.OpStatus;
import com.sypay.omp.per.common.ResultCodeConstants;
import com.sypay.omp.per.dao.RoleDao;
import com.sypay.omp.per.domain.Resource;
import com.sypay.omp.per.domain.Role;
import com.sypay.omp.per.domain.RoleRes;
import com.sypay.omp.per.model.RoleCriteriaModel;
import com.sypay.omp.per.model.RoleModel;
import com.sypay.omp.per.model.page.AjaxJson;
import com.sypay.omp.per.model.page.DataGrid;
import com.sypay.omp.per.model.page.PageHelper;
import com.sypay.omp.per.service.RoleService;
import com.sypay.omp.report.dao.BaseDao;
import com.sypay.omp.report.service.MybatisBaseService;

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

    @javax.annotation.Resource
    private RoleDao roleDao;

    @javax.annotation.Resource
    private BaseDao baseDao;

    @javax.annotation.Resource
    private MybatisBaseService mybatisBaseService;

    @Override
    public DataGrid findRoleListByCriteria(PageHelper pageHelper, RoleCriteriaModel roleCriteriaModel) {
        DataGrid dataGrid = new DataGrid();
        dataGrid.setTotal(roleDao.countRoleByCriteria(roleCriteriaModel));
        List<RoleModel> roleModelList = roleDao.findRoleListByCriteria(pageHelper, roleCriteriaModel);
        dataGrid.setRows(roleModelList);
        return dataGrid;
    }

    @Override
    public int updateRole(RoleModel model, Long currentMemberId, String currentMemberIp) {
        Role role = (Role) baseDao.get(Role.class, model.getId());
        role.setSysCode(model.getSysCode());
        role.setName(model.getName());
        role.setDescription(model.getDescription());
        role.setStatus(model.getStatus() == null ? role.getStatus() : model.getStatus());
        role.setUpdateTime(new Date());
        role.setModifierId(currentMemberId);
        baseDao.update(role);

        return Constants.OpStatus.SUCC;
    }

    @Override
    public int saveRole(RoleModel model, Long currentMemberId, String currentMemberIp) {
        Role role = new Role();
        Date now = new Date();

        role.setSysCode(model.getSysCode());
        role.setName(model.getName().trim());
        role.setRoleCode(model.getRoleCode());
        role.setDescription(model.getDescription());
        role.setStatus(model.getStatus());
        role.setCreateTime(now);
        role.setUpdateTime(now);
        role.setCreaterId(currentMemberId);
        role.setModifierId(currentMemberId);

        baseDao.save(role);

        return Constants.OpStatus.SUCC;
    }

    @Override
    public int saveRoleResources(RoleModel model) {
        try {
            // 取得当前角色
            Role role = (Role) baseDao.get(Role.class, model.getId());
            // 清空中间表数据
            if (role.getRoleRes() != null && role.getRoleRes().size() > 0) {
                roleDao.clearMiddleTableData(role.getId());
            }

            if (StringUtils.isNotBlank(model.getResourceIds())) {
                String[] idArray = model.getResourceIds().split(",");
                List<RoleRes> roleResList = new ArrayList<RoleRes>();
                RoleRes roleRes = null;
                Resource resource = null;
                // 查出分配资源
                for (String id : idArray) {
                    resource = (Resource) baseDao.get(Resource.class, Long.valueOf(id));
                    roleRes = new RoleRes();
                    roleRes.setRoleCode(role.getRoleCode());
                    roleRes.setSysCode(role.getSysCode());
                    roleRes.setResource(resource);
                    roleRes.setRole(role);
                    roleResList.add(roleRes);
                }

                role.setRoleRes(roleResList);
                role.setUpdateTime(new Date());
                baseDao.saveOrUpdate(role);
            } else {
                role.setRoleRes(null);
                baseDao.saveOrUpdate(role);
            }
        } catch (Exception e) {
        	e.printStackTrace();
            return OpStatus.FAIL;
        }
        return OpStatus.SUCC;
    }

    @Override
    public AjaxJson removeRole(Long id) {
        AjaxJson ajaxJson = new AjaxJson();
        String roleCode = (String) mybatisBaseService.selectOne("role.getRoleCodeByRoleId", id);
        if (roleCode != null) {
            /*
             * 判断该角色是否有关联资源，如果没有的话，就直接物理删除该角色；
             * 如果该角色有关联资源，那么只需将该角色的状态置为无效，并保留它与资源
             * 的关联关系
             */

            if (isAssociatedWithOthers(roleCode)) {
                // 有关联资源

                // 逻辑删除角色
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("id", id);
                mybatisBaseService.update("role.deleteRoleLogically", params);
                ajaxJson.setErrorNo(ResultCodeConstants.RESULT_ROLE_IS_IN_USE);
                ajaxJson.setErrorInfo("无法删除，请检查该角色是否正在使用");
            } else {
                // 没有关联资源

                // 物理删除角色
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("id", id);
                mybatisBaseService.update("role.deleteRolePhysically", params);
            }
        }

        return ajaxJson;
    }

    public boolean isAssociatedWithOthers(String roleCode) {
        boolean flag = ((Integer) mybatisBaseService.selectOne("role.countResourceByRoleCode", roleCode)) > 0;

        if (!flag) {
            flag = ((Integer) mybatisBaseService.selectOne("role.countGroupByRoleCode", roleCode)) > 0;
        }

        return flag;
    }

    @Override
    public List getRoleListBySysCodes(Set<String> sysCodes) {
        return roleDao.getRoleListBySysCodes(sysCodes);
    }

    @Override
    public boolean isRoleCodeExists(RoleModel roleModel) {
        return roleDao.isRoleCodeExists(roleModel);
    }

    @Override
    public List getRoleListByGroupCode(String groupCode) {
        return roleDao.getRoleListByGroupCode(groupCode);
    }

    @Override
    public List<Map<String, Object>> getRoleCodeAndNameList(String groupCode) {
        return roleDao.getRoleCodeAndNameList(groupCode);
    }

    @Override
    public List<String> findSysCodeByRoleCodes(List<String> roleCodes) {
        return roleDao.findSysCodeByRoleCodes(roleCodes);
    }

    @Override
    public boolean isSameRoleCode(Long roleId, String roleCode) {
        return roleDao.isSameRoleCode(roleId, roleCode);
    }
}
