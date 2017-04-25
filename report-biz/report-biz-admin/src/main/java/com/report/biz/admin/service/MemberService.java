package com.report.biz.admin.service;

import java.util.Set;

import com.report.common.dal.admin.entity.dto.Member;
import com.report.common.model.DataGrid;
import com.report.common.model.MemberQueryReq;
import com.report.common.model.PageHelper;
import com.report.common.model.ShiroUser;
import com.report.common.model.UserInfo;

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
	
	public DataGrid findMemberList(MemberQueryReq memberQueryReq, PageHelper pageHelper);
	
	public boolean updateMember(MemberQueryReq memberQueryReq, String groupCode, Long currentMemberId);

	public void saveMember(MemberQueryReq memberQueryReq, String groupCode, Long currentMemberId);

	public boolean deleteMemberById(Long memberId);

	public boolean isPasswordRight(Long currentMemberId, String password);


	public boolean isAccNoExists(String accNo);

	public boolean changePassword(String trim, Long currentMemberId);


	public boolean resetPassword(Long memberId);
}
