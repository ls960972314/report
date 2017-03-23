package com.report.common.repository.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.report.common.dal.admin.dao.GroupDao;
import com.report.common.dal.admin.entity.dto.Group;
import com.report.common.dal.admin.entity.vo.GroupModel;
import com.report.common.dal.admin.util.MybatisUtil;
import com.report.common.dal.admin.util.PageUtil;
import com.report.common.dal.admin.util.SessionUtil;
import com.report.common.repository.GroupRepository;
import com.report.facade.entity.PageHelper;

@Service
public class GroupRepositoryImpl implements GroupRepository {
	@Resource
    private GroupDao groupDao;

    @Override
    public Long count(GroupModel groupModel) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupCode", groupModel.getGroupCode());
        params.put("groupName", groupModel.getGroupName());
        params.put("status", groupModel.getStatus());
        params.put("currentMemberGroupCode", groupModel.getCurrentMemberGroupCode());
        
        return groupDao.countForGroupList(params);
    }

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
        return groupDao.findGroupdByPage4SysAdmin(params, PageUtil.paged(pageHelper.getPage() - 1, pageHelper.getRows()));
    }

    private void handleFiledForSorting(PageHelper pageHelper) {
        // 将字段名转换为对应的列名
        if (pageHelper.getSort() != null) {
            pageHelper.setSort(MybatisUtil.propertyName2ColumnName("group.groupModelResultMap", pageHelper.getSort()));
        }
    }

    @Override
    public void updateGroupCodeByMemberId(Long memberId, String groupCode, String memberIp) {
        Date now = new Date();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupCode", groupCode);
        params.put("updateTime", now);
        params.put("memberId", memberId);
        groupDao.updateGroupCodeByMemberId(params);
    }

    @Override
    public List<Group> findAllGroups() {
    	return groupDao.findAllGroups();
    }

    // TODO 暂时查询所有的组
    @Override
    public List<Map<String, String>> findGroupNamesByCurrentMemberId(Long currentMemberId) {
        if (SessionUtil.isPerAdmin()) {
        	return groupDao.findGroupNames4SysAdmin();
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
        return groupDao.countGroupByGroupCode(params) > 0;
    }

    @Override
    public boolean isAssociatedWithGroup(Long memberId) {
        return groupDao.countGroupByMemberId(memberId) > 0;
    }

    @Override
    public void associatedWithGroup(Long memberId, String groupCode, String memberIp) {
        Date now = new Date();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberId", memberId);
        params.put("groupCode", groupCode);
        params.put("createTime", now);
        params.put("updateTime", now);
        groupDao.addMappingGroup2Member(params);

    }
}
