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
	/**
	 * 根据map查找用户数
	 * @param params
	 * @return
	 */
	public Long count(Map<String, Object> params);
	/**
	 * 根据map分页查找用户信息
	 * @param params
	 * @param rowBounds
	 * @return
	 */
	public List<MemberModel> findMemberList(Map<String, Object> params, RowBounds rowBounds);
	/**
	 * 根据map查找符合条件的个数
	 * @param params
	 * @return
	 */
	public Integer countMemberByMemberIdAndPassword(Map<String, Object> params);
	/**
	 * 根据map更新用户信息
	 * @param params
	 */
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
