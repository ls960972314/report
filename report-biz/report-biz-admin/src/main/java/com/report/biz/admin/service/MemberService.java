package com.report.biz.admin.service;

import java.util.Set;

import com.report.common.dal.admin.entity.dto.Member;
import com.report.common.dal.admin.entity.vo.MemberCriteriaModel;
import com.report.common.model.ShiroUser;
import com.report.common.model.UserInfo;
import com.report.facade.entity.DataGrid;
import com.report.facade.entity.PageHelper;

/**
 * 用户service
 * @author lishun
 * @since 2017年3月24日 下午4:34:08
 */
public interface MemberService {
	
	/**
	 * 根据用户账号查找UserModel
	 * @param username
	 * @return
	 */
	public ShiroUser findUserModelByAccNo(String accNo);
	
	/**
	 * 根据登陆账号查找用户信息
	 * @param accNo
	 * @return
	 */
	public UserInfo getUserInfo(String accNo);

    boolean updateMember(Member member, String groupCode, String currentMemberIp, Long currentMemberId);

    void saveMember(Member member, String groupCode, String currentMemberIp, Long currentMemberId);

    boolean deleteMemberById(Long Id, String memberIp);

    boolean isPasswordRight(Long currentMemberId, String password);

    boolean resetPassword(Long memberId, String memberIp);

    boolean isAccNoExists(String accNo);

    boolean changePassword(String trim, Long currentMemberId, String memberIp);

    DataGrid findMemberListByCriteria(MemberCriteriaModel memberCriteria, PageHelper pageHelper);

}
