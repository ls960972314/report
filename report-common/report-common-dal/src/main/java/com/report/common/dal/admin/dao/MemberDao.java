package com.report.common.dal.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.report.common.dal.admin.entity.vo.MemberCriteriaModel;
import com.report.common.dal.admin.entity.vo.MemberModel;

public interface MemberDao {

	public Integer countMemberByMemberIdAndPassword(Map<String, Object> params);
	
	public void updatePasswordByMemberId(Map<String, Object> params);
	
	public Integer countMemberByAccNo(String accNo);
	
	public Long countByCriteria(MemberCriteriaModel memberCriteriaModel);
	
	public List<MemberModel> findMemberListByCriteria( Map<String, Object> params, RowBounds rowBounds);
	
	public Integer countAssociationWithGroup(Long memberId);
}
