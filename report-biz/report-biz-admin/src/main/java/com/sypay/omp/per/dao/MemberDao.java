
package com.sypay.omp.per.dao;

import java.util.List;

import com.sypay.omp.per.model.MemberCriteriaModel;
import com.sypay.omp.per.model.MemberModel;
import com.sypay.omp.per.model.page.PageHelper;


public interface MemberDao {
	boolean isPasswordRight(Long currentMemberId, String encryptedPassword);

	boolean resetPassword(Long memberId, String defaultPasswordForMember);

	boolean isAccNoExists(String accNo);

	boolean changePassword(String encryptedPassword, Long currentMemberId);

	Long countByCriteria(MemberCriteriaModel memberCriteria);

	List<MemberModel> findMemberListByCriteria(MemberCriteriaModel memberCriteria, PageHelper pageHelper);
}
