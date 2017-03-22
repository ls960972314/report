package com.report.common.dal.admin.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.report.common.dal.admin.dao.GroupRoleDao;
import com.report.common.dal.admin.entity.dto.GroupRole;
import com.report.common.dal.admin.entity.vo.GroupModel;
import com.report.common.dal.common.BaseDao;

@Repository
public class GroupRoleDaoImpl implements GroupRoleDao {

    @Resource
    private BaseDao baseDao;

    @Override
    public void addMapping4GroupAndRole(GroupModel groupModel, Long currentMemberId, String memberIp) {
        List<String> list = groupModel.getRoleCodes();
        if (list != null && !list.isEmpty()) {
            for (String roleCode : list) {
                if(StringUtils.isBlank(roleCode)) 
                    continue;
                GroupRole groupRole = new GroupRole();
                groupRole.setGroupCode(groupModel.getGroupCode());
                groupRole.setRoleCode(roleCode);
                baseDao.save(groupRole);
            }
        }
    }

    @Override
    public void removeMapping4GroupAndRole(GroupModel groupModel, Long currentMemberId, String memberIp) {
        String sql = "delete from uc_group_role where group_code = :groupCode";
        SQLQuery query = baseDao.getSqlQuery(sql);
        query.setString("groupCode", groupModel.getGroupCode());
        query.executeUpdate();
    }
}
