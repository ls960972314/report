package com.report.biz.admin.service;

import java.util.Set;

import com.report.common.dal.admin.entity.dto.Member;
import com.report.common.dal.admin.entity.vo.MemberCriteriaModel;
import com.report.common.model.UserModel;
import com.report.facade.entity.DataGrid;
import com.report.facade.entity.PageHelper;

/**
 * 用户service
 * @author lishun
 * @since 2017年3月24日 下午4:34:08
 */
public interface MemberService {
	
	/**
	 * 根据用户账号查找角色集合
	 * @param accNo
	 * @return
	 */
	public Set<String> findRoles(String accNo);
	/**
	 * 根据用户账号查找权限集合
	 * @param accNo
	 * @return
	 */
	public Set<String> findPermissions(String accNo);
	/**
	 * 根据用户账号查找UserModel
	 * @param username
	 * @return
	 */
	public UserModel findUserModelByAccNo(String accNo);

    boolean updateMember(Member member, String groupCode, String currentMemberIp, Long currentMemberId);

    void saveMember(Member member, String groupCode, String currentMemberIp, Long currentMemberId);

    boolean deleteMemberById(Long Id, String memberIp);

    boolean isPasswordRight(Long currentMemberId, String password);

    boolean resetPassword(Long memberId, String memberIp);

    boolean isAccNoExists(String accNo);

    boolean changePassword(String trim, Long currentMemberId, String memberIp);

    DataGrid findMemberListByCriteria(MemberCriteriaModel memberCriteria, PageHelper pageHelper);

    Member getMemberByLoginName(String loginName);

	
}
