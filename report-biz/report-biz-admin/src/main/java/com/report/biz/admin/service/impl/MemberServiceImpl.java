package com.report.biz.admin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.report.biz.admin.service.MemberService;
import com.report.common.dal.admin.constant.Constants;
import com.report.common.dal.admin.entity.dto.Member;
import com.report.common.dal.admin.entity.vo.MenuCell;
import com.report.common.dal.admin.entity.vo.PermissionCell;
import com.report.common.model.DataGrid;
import com.report.common.model.MemberQueryReq;
import com.report.common.model.PageHelper;
import com.report.common.model.ShiroUser;
import com.report.common.model.UserInfo;
import com.report.common.repository.GroupRepository;
import com.report.common.repository.MemberRepository;
import com.report.common.repository.ResourceRepository;
import com.report.common.repository.RoleRepository;
import com.report.common.util.MD5;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户service
 * @author lishun
 * @since 2017年4月7日 下午2:36:08
 */
@Slf4j
@Service("memberService")
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberRepository memberRepository;
    
    @Resource
    private GroupRepository groupRepository;
    
    @Resource
    private RoleRepository roleRepository;
    
    @Resource
    private ResourceRepository resourceRepository;

    @Override
	public UserInfo getUserInfo(String accNo) {
    	UserInfo userInfo = new UserInfo();
    	userInfo.setMember(memberRepository.findMemberByAccNo(accNo));
    	userInfo.setGroupCode(groupRepository.getGroupCodeByMemberId(userInfo.getMember().getId()));
    	userInfo.setPermissionCodeSet(resourceRepository.findPermissions(accNo));
    	userInfo.setRoleCodeSet(roleRepository.findRoles(accNo));
    	userInfo.setRoleCodeList(Lists.newArrayList(userInfo.getRoleCodeSet()));
    	userInfo.setMenuList(getMenuList(userInfo.getMember().getId()));
		return userInfo;
	}
    
    @Override
	public ShiroUser findUserModelByAccNo(String accNo) {
		Member member = memberRepository.findMemberByAccNo(accNo);
		if (null != member) {
			ShiroUser userModel = new ShiroUser();
			userModel.setPassword(member.getPassword());
			userModel.setAccNo(member.getAccNo());
			userModel.setUsername(member.getName());
			return userModel;
		}
		log.debug("findUserModelByAccNo result[null]");
		return null;
	}
    
    @Override
    public DataGrid findMemberList(MemberQueryReq memberQueryReq, PageHelper pageHelper) {
        DataGrid dataGrid = new DataGrid();
        dataGrid.setTotal(memberRepository.count(memberQueryReq));
        dataGrid.setRows(memberRepository.findMemberList(memberQueryReq, pageHelper.getPage(), pageHelper.getRows()));
        return dataGrid;
    }
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateMember(MemberQueryReq memberQueryReq, String groupCode, Long currentMemberId) {
        Member member = new Member();
        member.setId(memberQueryReq.getId());
        member.setAccNo(memberQueryReq.getAccNo());
        member.setName(memberQueryReq.getName());
        member.setStatus(memberQueryReq.getStatus());
        memberRepository.update(member); 
        // 判断该会员有没有关联groupCode,如果没有的话,就insert;如果有的话,就update
        if (groupRepository.isAssociatedWithGroup(memberQueryReq.getId())) {
            // 有关联
            groupRepository.updateGroupCodeByMemberId(memberQueryReq.getId(), groupCode);
        } else {
            // 没有关联
            groupRepository.associatedWithGroup(memberQueryReq.getId(), groupCode);
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveMember(MemberQueryReq memberQueryReq, String groupCode, Long currentMemberId) {
    	Member member = new Member();
    	BeanUtils.copyProperties(memberQueryReq, member);
        memberRepository.insert(member);
        if (StringUtils.isNotBlank(groupCode)) {
            groupRepository.associatedWithGroup(member.getId(), groupCode);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteMemberById(Long memberId) {
    	memberRepository.delete(memberId);
    	groupRepository.deleteAssociateWithMember(memberId);
        return true;
    }

    @Override
    public boolean isPasswordRight(Long currentMemberId, String password) {
        return memberRepository.isPasswordRight(currentMemberId, MD5.getMD5String(password));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean resetPassword(Long memberId) {
        return memberRepository.resetPassword(memberId, MD5.getMD5String(Constants.DEFAULT_PASSWORD_FOR_MEMBER));
    }

    @Override
    public boolean isAccNoExists(String accNo) {
        return memberRepository.isAccNoExists(accNo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean changePassword(String password, Long currentMemberId) {
        return memberRepository.changePassword(MD5.getMD5String(password), currentMemberId);
    }

    private List<MenuCell> getMenuList(Long memberId) {
        List<PermissionCell> permissions = findPermissionCellByMemberId(memberId);
        return packSortedMenus(permissions);
    }
    
    private List<PermissionCell> findPermissionCellByMemberId(Long memberId) {
        List<PermissionCell> list = resourceRepository.findPermissionCellByMemberId(memberId);
        return list;
    }

    private List<MenuCell> packSortedMenus(List<PermissionCell> permissions) {
        // 菜单结果
        List<MenuCell> menus = Collections.emptyList();
        if (!permissions.isEmpty()) {
            menus = new ArrayList<MenuCell>();
            for (int i = 0; i < permissions.size(); i++) {
                PermissionCell permission = permissions.get(i);

                // 第一层级菜单
                Long rootId = permission.getpId();
                if (null == rootId || 0 == rootId) {
                    MenuCell rootMenu = new MenuCell();
                    Long id = permission.getId();
                    rootMenu.setId(id);
                    rootMenu.setCode(permission.getResourceCode());
                    rootMenu.setText(permission.getName());
                    rootMenu.setIcon(permission.getIcon());
                    rootMenu.setUrl(permission.getResourceAction());
                    rootMenu.setOrderBy(permission.getOrderBy());
                    rootMenu.setResourceType(permission.getResourceType());
                    rootMenu.setPId(permission.getpId());
                    rootMenu.setDescription(permission.getDescription());
                    rootMenu.setMenuType(permission.getSysCode());
                    // 获取第二层级菜单
                    List<MenuCell> grandsons = getChild(id, permissions);
                    rootMenu.setChildren(grandsons);
                    menus.add(rootMenu);
                }
            }
            if (!menus.isEmpty()) {
                Collections.sort(menus);
            }
        }
        return menus;
    }

    /**
     * 获取子菜单列表
     * 
     * @param parentId
     * @param permissions
     * @return
     */
    private static List<MenuCell> getChild(Long parentId, List<PermissionCell> permissions) {
        List<MenuCell> children = Collections.emptyList();
        if (null == permissions || permissions.isEmpty()) {
            return children;
        }
        children = new ArrayList<MenuCell>();
        for (PermissionCell permission : permissions) {
            if (String.valueOf(parentId).equals(String.valueOf(permission.getpId()))) {
                MenuCell menu = new MenuCell();
                Long menuId = permission.getId();
                menu.setId(menuId);
                menu.setCode(permission.getResourceCode());
                menu.setText(permission.getName());
                menu.setIcon(permission.getIcon());
                menu.setUrl(permission.getResourceAction());
                menu.setOrderBy(permission.getOrderBy());
                menu.setResourceType(permission.getResourceType());
                menu.setPId(permission.getpId());
                menu.setDescription(permission.getDescription());
                menu.setMenuType(permission.getSysCode());
                // 循环查询子菜单列表
                List<MenuCell> grandsons = getChild(menuId, permissions);
                menu.setChildren(grandsons);
                children.add(menu);
            }
        }
        // 根据orderBy字段排序
        Collections.sort(children);
        return children;
    }
}
