package com.report.common.dal.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.report.common.dal.admin.entity.dto.Member;
import com.report.common.dal.admin.entity.vo.MemberCriteriaModel;
import com.report.common.dal.admin.entity.vo.MemberModel;

/**
 * 
 * @author lishun
 * @since 2017年3月24日 下午4:45:00
 */
public interface MemberDao {

	/**
	 * 根据用户账号查找用户
	 * @param accNo
	 * @return
	 */
	public Member findMemberByAccNo(String accNo);
	
	public Integer countMemberByMemberIdAndPassword(Map<String, Object> params);
	
	public void updatePasswordByMemberId(Map<String, Object> params);
	
	public Integer countMemberByAccNo(String accNo);
	
	public Long countByCriteria(MemberCriteriaModel memberCriteriaModel);
	
	public List<MemberModel> findMemberListByCriteria( Map<String, Object> params, RowBounds rowBounds);
	
	public Integer countAssociationWithGroup(Long memberId);
}
