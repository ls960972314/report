package com.sypay.omp.per.service;

import java.util.List;

import com.sypay.omp.per.domain.Member;
import com.sypay.omp.per.model.MemberCriteriaModel;
import com.sypay.omp.per.model.page.DataGrid;
import com.sypay.omp.per.model.page.PageHelper;

public interface MemberService {

    /**
     * 查找列表
     * 
     * @param pageHelper 分页对象
     */
    //	DataGrid findMembers(PageHelper pageHelper, Long currentMemberId);

    /**
     * 更新对象
     * 
     * @param member
     */
    boolean updateMember(Member member, String groupCode, String currentMemberIp, Long currentMemberId);

    /**
     * 保存对象
     * 
     * @param member
     */
    void saveMember(Member member, String groupCode, String currentMemberIp, Long currentMemberId);

    /**
     * 删除对象
     * 
     * @param id
     * @return
     */
    boolean deleteMemberById(Long Id, String memberIp);

    //	DataGrid findMemberListByName(String name, PageHelper pageHelper, Long currentMemberId);

    boolean isPasswordRight(Long currentMemberId, String password);

    boolean resetPassword(Long memberId, String memberIp);

    boolean isAccNoExists(String accNo);

    boolean changePassword(String trim, Long currentMemberId, String memberIp);

    DataGrid findMemberListByCriteria(MemberCriteriaModel memberCriteria, PageHelper pageHelper);

    Member getMemberByLoginName(String loginName);
}
