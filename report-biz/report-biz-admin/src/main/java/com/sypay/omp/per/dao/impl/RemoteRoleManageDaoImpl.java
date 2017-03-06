package com.sypay.omp.per.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.sypay.omp.per.dao.RemoteRoleManageDao;
import com.sypay.omp.per.model.UpdateRoleModel;
import com.sypay.omp.report.dao.BaseDao;
import com.sypay.omp.report.service.MybatisBaseService;

@Repository
public class RemoteRoleManageDaoImpl implements RemoteRoleManageDao {

    @Resource
    private BaseDao baseDao;

    @Resource
    private MybatisBaseService mybatisBaseService;

    @Override
    public boolean updateRole(UpdateRoleModel roleModel) {
        int result = 0;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberId", roleModel.getMemberId());
        params.put("sysCode", roleModel.getSysCode());
        params.put("sourceRoleCode", roleModel.getSourceRoleCode());
        params.put("targetRoleCode", roleModel.getTargetRoleCode());

        String groupCode = null;
        Object value = mybatisBaseService.selectOne("group.queryGroupByRoles", params);
        if (null == value) {
            // 新增组

        } else {
            groupCode = (String) value;
        }
        params = new HashMap<String, Object>();
        params.put("memberId", roleModel.getMemberId());
        params.put("groupCode", groupCode);
        String sql = "UPDATE MemberGroup SET groupCode = :groupCode WHERE  memberId = :memberId ";
        result = baseDao.executeSql(sql, params);
        return result > 0;
    }
}
