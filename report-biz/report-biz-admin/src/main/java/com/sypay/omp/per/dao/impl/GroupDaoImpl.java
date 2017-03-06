package com.sypay.omp.per.dao.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.sypay.omp.per.dao.GroupDao;
import com.sypay.omp.per.domain.Group;
import com.sypay.omp.per.model.GroupModel;
import com.sypay.omp.per.model.page.PageHelper;
import com.sypay.omp.per.model.page.PageUtil;
import com.sypay.omp.per.util.MybatisUtil;
import com.sypay.omp.per.util.SessionUtil;
import com.sypay.omp.report.dao.BaseDao;
import com.sypay.omp.report.service.MybatisBaseService;

@Repository
public class GroupDaoImpl implements GroupDao {

    @Resource
    private MybatisBaseService mybatisBaseService;

    @Resource
    private BaseDao baseDao;

    @Override
    public Long count(GroupModel groupModel) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupCode", groupModel.getGroupCode());
        params.put("groupName", groupModel.getGroupName());
        params.put("status", groupModel.getStatus());
        params.put("currentMemberGroupCode", groupModel.getCurrentMemberGroupCode());
        
        return (Long) mybatisBaseService.selectOne("group.countForGroupList", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GroupModel> fingGroupsByPage(PageHelper pageHelper, GroupModel groupModel) {
        handleFiledForSorting(pageHelper);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sort", pageHelper.getSort());
        params.put("order", pageHelper.getOrder());
        params.put("groupCode", groupModel.getGroupCode());
        params.put("groupName", groupModel.getGroupName());
        params.put("status", groupModel.getStatus());
        params.put("currentMemberGroupCode", groupModel.getCurrentMemberGroupCode());

        return mybatisBaseService.selectList("group.findGroupdByPage4SysAdmin", params, PageUtil.paged(pageHelper.getPage() - 1, pageHelper.getRows()));
    }

    private void handleFiledForSorting(PageHelper pageHelper) {
        // 将字段名转换为对应的列名
        if (pageHelper.getSort() != null) {
            pageHelper.setSort(MybatisUtil.propertyName2ColumnName("group.groupModelResultMap", pageHelper.getSort()));
        }
    }

    @Override
    public Group getGroupByGroupCode(String groupCode) {
        return (Group) mybatisBaseService.selectOne("group.getGroupByGroupCode", groupCode);
    }

    @Override
    public void updateGroupCodeByMemberId(Long memberId, String groupCode, String memberIp) {
        Date now = new Date();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupCode", groupCode);
        params.put("updateTime", now);
        params.put("memberId", memberId);

        boolean flag = mybatisBaseService.update("updateGroupCodeByMemberId", params) > 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Group> findAllGroups() {
        return mybatisBaseService.selectList("group.findAllGroups", null);
    }

    // TODO 暂时查询所有的组
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, String>> findGroupNamesByCurrentMemberId(Long currentMemberId) {
        if (SessionUtil.isPerAdmin()) {
            return mybatisBaseService.selectList("group.findGroupNames4SysAdmin", null);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean isGroupCodeExists(GroupModel groupModel) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupCode", groupModel.getGroupCode());
        if (groupModel.getId() != null) {
            params.put("groupId", groupModel.getId());
        }
        return ((Integer) mybatisBaseService.selectOne("group.countGroupByGroupCode", params)) > 0;
    }

    @Override
    public boolean isAssociatedWithGroup(Long memberId) {
        return (Integer) mybatisBaseService.selectOne("group.countGroupByMemberId", memberId) > 0;
    }

    @Override
    public void associatedWithGroup(Long memberId, String groupCode, String memberIp) {
        Date now = new Date();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberId", memberId);
        params.put("groupCode", groupCode);
        params.put("createTime", now);
        params.put("updateTime", now);
        boolean flag = mybatisBaseService.update("group.addMappingGroup2Member", params) > 0;

    }
}
