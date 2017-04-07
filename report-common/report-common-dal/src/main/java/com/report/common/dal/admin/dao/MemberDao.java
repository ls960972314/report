package com.report.common.dal.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.report.common.dal.admin.entity.dto.Member;
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
	
	public Long count(Map<String, Object> params);
	
	public List<MemberModel> findMemberList(Map<String, Object> params, RowBounds rowBounds);
	
	public Integer countMemberByMemberIdAndPassword(Map<String, Object> params);
	
	public void updatePasswordByMemberId(Map<String, Object> params);
	
	/**
	 * 根据用户登陆名查找总个数
	 * @param accNo
	 * @return
	 */
	public Integer countMemberByAccNo(String accNo);
	
	public Integer countAssociationWithGroup(Long memberId);

	/**
	 * 更新用户信息
	 * @param member
	 */
	public void update(Member member);

	/**
	 * 新增用户
	 * @param member
	 * @return
	 */
	public Long insert(Member member);

	/**
	 * 删除用户
	 * @param memberId
	 */
	public void delete(Long memberId);
}
