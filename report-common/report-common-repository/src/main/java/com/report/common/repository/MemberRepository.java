package com.report.common.repository;

import java.util.List;

import com.report.common.dal.admin.entity.dto.Member;
import com.report.common.dal.admin.entity.vo.MemberCriteriaModel;
import com.report.common.dal.admin.entity.vo.MemberModel;
import com.report.facade.entity.PageHelper;

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
	
	public boolean isPasswordRight(Long currentMemberId, String encryptedPassword);

	public boolean resetPassword(Long memberId, String defaultPasswordForMember);

	public boolean isAccNoExists(String accNo);

	public boolean changePassword(String encryptedPassword, Long currentMemberId);

	public Long countByCriteria(MemberCriteriaModel memberCriteria);

	public List<MemberModel> findMemberListByCriteria(MemberCriteriaModel memberCriteria, PageHelper pageHelper);
}
