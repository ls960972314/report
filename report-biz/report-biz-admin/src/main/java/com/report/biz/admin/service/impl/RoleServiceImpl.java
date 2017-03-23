package com.report.biz.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.report.biz.admin.service.RoleService;
import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.admin.constant.Constants.OpStatus;
import com.report.common.dal.admin.dao.RoleDao;
import com.report.common.dal.admin.entity.dto.Resource;
import com.report.common.dal.admin.entity.dto.Role;
import com.report.common.dal.admin.entity.dto.RoleRes;
import com.report.common.dal.admin.entity.vo.RoleCriteriaModel;
import com.report.common.dal.admin.entity.vo.RoleModel;
import com.report.common.dal.common.BaseDao;
import com.report.common.model.AjaxJson;
import com.report.common.model.ResultCodeConstants;
import com.report.common.repository.RoleRepository;
import com.report.facade.entity.DataGrid;
import com.report.facade.entity.PageHelper;

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private RoleDao roleDao;
    
    @Override
    public DataGrid findRoleListByCriteria(PageHelper pageHelper, RoleCriteriaModel roleCriteriaModel) {
        DataGrid dataGrid = new DataGrid();
        dataGrid.setTotal(roleRepository.countRoleByCriteria(roleCriteriaModel));
        List<RoleModel> roleModelList = roleRepository.findRoleListByCriteria(pageHelper, roleCriteriaModel);
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
                roleRepository.clearMiddleTableData(role.getId());
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
        String roleCode = roleDao.getRoleCodeByRoleId(id);
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
                roleDao.deleteRoleLogically(params);
                ajaxJson.setErrorNo(ResultCodeConstants.RESULT_ROLE_IS_IN_USE);
                ajaxJson.setErrorInfo("无法删除，请检查该角色是否正在使用");
            } else {
                // 没有关联资源

                // 物理删除角色
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("id", id);
                roleDao.deleteRolePhysically(params);
            }
        }

        return ajaxJson;
    }

    public boolean isAssociatedWithOthers(String roleCode) {
        boolean flag = roleDao.countResourceByRoleCode(roleCode) > 0;
        if (!flag) {
            flag = roleDao.countGroupByRoleCode(roleCode) > 0;
        }
        return flag;
    }

    @Override
    public List getRoleListBySysCodes(Set<String> sysCodes) {
        return roleRepository.getRoleListBySysCodes(sysCodes);
    }

    @Override
    public boolean isRoleCodeExists(RoleModel roleModel) {
        return roleRepository.isRoleCodeExists(roleModel);
    }

    @Override
    public List getRoleListByGroupCode(String groupCode) {
        return roleRepository.getRoleListByGroupCode(groupCode);
    }

    @Override
    public List<Map<String, Object>> getRoleCodeAndNameList(String groupCode) {
        return roleRepository.getRoleCodeAndNameList(groupCode);
    }

    @Override
    public List<String> findSysCodeByRoleCodes(List<String> roleCodes) {
        return roleRepository.findSysCodeByRoleCodes(roleCodes);
    }

    @Override
    public boolean isSameRoleCode(Long roleId, String roleCode) {
        return roleRepository.isSameRoleCode(roleId, roleCode);
    }
}
