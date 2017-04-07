package com.report.common.repository;

import java.util.List;

import com.report.common.dal.admin.entity.dto.Member;
import com.report.common.dal.admin.entity.vo.MemberModel;
import com.report.common.model.MemberQueryReq;

/**
 * 
 * @author lishun
 * @since 2017年3月24日 下午4:44:14
 */
public interface MemberRepository {

	/**
	 * 根据用户账号查找用户
	 * @param accNo
	 * @return
	 */
	public Member findMemberByAccNo(String accNo);
	
	/**
	 * 查询用户列表数量
	 * @param memberQueryReq
	 * @return
	 */
	public Long count(MemberQueryReq memberQueryReq);
	/**
	 * 查询用户列表
	 * @param memberQueryReq
	 * @param sort
	 * @param order
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<MemberModel> findMemberList(MemberQueryReq memberQueryReq, int page, int rows);
	
	/**
	 * 当前用户密码是否正确
	 * @param currentMemberId
	 * @param encryptedPassword
	 * @return
	 */
	public boolean isPasswordRight(Long currentMemberId, String encryptedPassword);

	/**
	 * 充值密码
	 * @param memberId
	 * @param defaultPasswordForMember
	 * @return
	 */
	public boolean resetPassword(Long memberId, String defaultPasswordForMember);

	/**
	 * 账号是否存在
	 * @param accNo
	 * @return
	 */
	public boolean isAccNoExists(String accNo);

	/**
	 * 修改密码
	 * @param encryptedPassword
	 * @param currentMemberId
	 * @return
	 */
	public boolean changePassword(String encryptedPassword, Long currentMemberId);
	
	/**
	 * 新增用户
	 * @param member
	 * @return 主键
	 */
	public Long insert(Member member);

	/**
	 * 删除用户
	 * @param memberId
	 */
	public void delete(Long memberId);
	
	/**
	 * 更新用户信息
	 * @param member
	 */
	public void update(Member member);
}
