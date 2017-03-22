package com.sypay.omp.per.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.admin.dao.GroupDao;
import com.report.common.dal.admin.dao.GroupRoleDao;
import com.report.common.dal.admin.entity.dto.Group;
import com.report.common.dal.admin.entity.vo.GroupModel;
import com.report.common.dal.admin.entity.vo.PageHelper;
import com.report.common.dal.admin.util.SessionUtil;
import com.report.common.dal.common.BaseDao;
import com.report.common.repository.GroupRepository;
import com.report.common.repository.RoleRepository;
import com.sypay.omp.per.common.ResultCodeConstants;
import com.sypay.omp.per.model.page.AjaxJson;
import com.sypay.omp.per.model.page.DataGrid;
import com.sypay.omp.per.service.GroupService;

/**
 * 
 * @Description: 组服务实现类
 * @date 2014-10-14 14:10:24
 * @version V1.0
 * 
 */
@Service("groupService")
@Transactional
public class GroupServiceImpl implements GroupService {

    private final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Resource
    private BaseDao baseDao;
    @Resource
    private GroupRepository groupRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private GroupRoleDao groupRoleDao;
    @Resource
    private GroupDao groupDao;
    /**
     * 分页查询数据列表
     * 
     * @param model
     * @param pageHelper
     * @return DataGrid
     */
    @Override
    public DataGrid findGroups(PageHelper pageHelper, GroupModel groupModel) {
        DataGrid dataGrid = new DataGrid();
        if(SessionUtil.isPerAdmin()) {
            groupModel.setCurrentMemberGroupCode(null);
        }
        dataGrid.setTotal(groupRepository.count(groupModel));
        dataGrid.setRows(groupRepository.fingGroupsByPage(pageHelper, groupModel));
        return dataGrid;
    }

    /**
     * 更新信息
     */
    @Override
    public int updateGroup(GroupModel groupModel, Long currentMemberId, String memberIp) {
        Date now = new Date();
        Group target = (Group) baseDao.get(Group.class, groupModel.getId());
        target.setUpdateTime(now);
        target.setGroupName(groupModel.getGroupName());
        target.setStatus(groupModel.getStatus());
        target.setModifierId(currentMemberId);
        baseDao.update(target);

        // 删除组和角色的关联关系
        groupRoleDao.removeMapping4GroupAndRole(groupModel, currentMemberId, memberIp);

        // 添加新的组和角色的关联关系
        groupRoleDao.addMapping4GroupAndRole(groupModel, currentMemberId, memberIp);

        return Constants.OpStatus.SUCC;
    }

    /**
     * 新增信息
     */
    @Override
    public int saveGroup(GroupModel groupModel, Long currentMemberId, String memberIp) {
        groupModel.setGroupName(groupModel.getGroupName().trim());
        groupModel.setGroupCode(groupModel.getGroupCode().trim());

        Group group = new Group();
        Date date = new Date();
        group.setCreateTime(date);
        group.setUpdateTime(date);
        group.setStatus(groupModel.getStatus());
        group.setGroupCode(groupModel.getGroupCode());
        group.setGroupName(groupModel.getGroupName());
        group.setCreaterId(currentMemberId);
        group.setModifierId(currentMemberId);
        group.setCreaterId(SessionUtil.getCurrentMemberId());
        baseDao.save(group);

        groupRoleDao.addMapping4GroupAndRole(groupModel, currentMemberId, memberIp);
        return Constants.OpStatus.SUCC;
    }

    /**
     * 删除信息
     */
    @Override
    public AjaxJson deleteGroupById(Long id, Long currentMemberId, String memberIp) {
        AjaxJson ajaxJson = new AjaxJson();
        Object obj = baseDao.get(Group.class, id);
        if (obj != null) {
            /*
             * 判断该组有没有关联人员。如果有的话，就将其状态置为无效，并保留组和人员之间的关联关系；如果没有的话，就将其物理删除
             */
            Group group = (Group) obj;
            boolean isAssociatedWithOthers = isAssociatedWithOthers(group.getGroupCode());
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", id);

            if (isAssociatedWithOthers) {
                // 有关联人员，逻辑删除组
            	groupDao.deleteGroupLogically(params);
                ajaxJson.setErrorNo(ResultCodeConstants.RESULT_CHECK_GROUP_IS_IN_USE);
                ajaxJson.setErrorInfo("无法删除，请检查该组是否仍被使用");
            } else {
                // 没有关联人员，物理删除组
            	groupDao.deleteGroupPhysically(params);
            }

        }

        return ajaxJson;
    }

    public List<Group> findAllGroups() {
        return groupRepository.findAllGroups();
    }

    @Override
    public List<Map<String, String>> findGroupNamesByCurrentMemberId(Long currentMemberId) {
        return groupRepository.findGroupNamesByCurrentMemberId(currentMemberId);
    }

    @Override
    public boolean isGroupCodeExists(GroupModel groupModel) {
        return groupRepository.isGroupCodeExists(groupModel);
    }

    @Override
    public String getGroupCodeByMemberId(Long memberId) {
        return groupDao.getGroupCodeByMemberId(memberId);
    }

    @Override
    public boolean isSameGroupCode(Long id, String groupCode) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupId", id);
        params.put("groupCode", groupCode);
        return groupDao.isSameGroupCode(params) > 0;
    }

    public boolean isAssociatedWithOthers(String groupCode) {
        boolean flag = false;
        flag =  groupDao.countMemberByGroupCode(groupCode) > 0;
        if (!flag) {
            flag = groupDao.countRolebyGroupCode(groupCode) > 0;
        }
        return flag;
    }
}