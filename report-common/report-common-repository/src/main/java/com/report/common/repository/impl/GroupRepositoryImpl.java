package com.report.common.repository.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.report.common.dal.admin.dao.GroupDao;
import com.report.common.dal.admin.entity.dto.Group;
import com.report.common.dal.admin.entity.vo.GroupModel;
import com.report.common.dal.admin.util.PageUtil;
import com.report.common.model.PageHelper;
import com.report.common.model.SessionUtil;
import com.report.common.repository.GroupRepository;

@Service
public class GroupRepositoryImpl implements GroupRepository {
	@Resource
    private GroupDao groupDao;

	
	@Override
	public String getGroupCodeByMemberId(Long memberId) {
		return groupDao.getGroupCodeByMemberId(memberId);
	}
	
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
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sort", pageHelper.getSort());
        params.put("order", pageHelper.getOrder());
        params.put("groupCode", groupModel.getGroupCode());
        params.put("groupName", groupModel.getGroupName());
        params.put("status", groupModel.getStatus());
        params.put("currentMemberGroupCode", groupModel.getCurrentMemberGroupCode());
        return groupDao.findGroupdByPage4SysAdmin(params, PageUtil.paged(pageHelper.getPage() - 1, pageHelper.getRows()));
    }

    @Override
    public void updateGroupCodeByMemberId(Long memberId, String groupCode) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupCode", groupCode);
        params.put("memberId", memberId);
        groupDao.updateGroupCodeByMemberId(params);
    }

    @Override
    public List<Group> findAllGroups() {
    	return groupDao.findAllGroups();
    }

    @Override
    public List<Map<String, String>> findGroupNamesByCurrentMemberId(Long currentMemberId) {
        if (SessionUtil.getUserInfo().isAdmin()) {
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
    public void associatedWithGroup(Long memberId, String groupCode) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("memberId", memberId);
        params.put("groupCode", groupCode);
        groupDao.addMappingGroup2Member(params);

    }

	@Override
	public void deleteAssociateWithMember(Long memberId) {
		groupDao.deleteAssociateWithMember(memberId);
	}
}
