package com.report.common.repository;

import java.util.List;

import com.report.common.dal.admin.entity.vo.MemberCriteriaModel;
import com.report.common.dal.admin.entity.vo.MemberModel;
import com.report.common.dal.admin.entity.vo.PageHelper;

public interface MemberRepository {

	public boolean isPasswordRight(Long currentMemberId, String encryptedPassword);

	public boolean resetPassword(Long memberId, String defaultPasswordForMember);

	public boolean isAccNoExists(String accNo);

	public boolean changePassword(String encryptedPassword, Long currentMemberId);

	public Long countByCriteria(MemberCriteriaModel memberCriteria);

	public List<MemberModel> findMemberListByCriteria(MemberCriteriaModel memberCriteria, PageHelper pageHelper);
}
