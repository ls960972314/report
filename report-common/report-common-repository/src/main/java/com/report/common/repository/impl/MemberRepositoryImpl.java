package com.report.common.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.report.common.dal.admin.dao.MemberDao;
import com.report.common.dal.admin.entity.dto.Member;
import com.report.common.dal.admin.entity.vo.MemberCriteriaModel;
import com.report.common.dal.admin.entity.vo.MemberModel;
import com.report.common.dal.admin.util.MybatisUtil;
import com.report.common.dal.admin.util.PageUtil;
import com.report.common.repository.MemberRepository;
import com.report.facade.entity.PageHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author lishun
 * @since 2017年3月24日 下午4:45:52
 */
@Slf4j
@Service
public class MemberRepositoryImpl implements MemberRepository {
	@Resource
    private MemberDao memberDao;

	@Override
	public Member findMemberByAccNo(String accNo) {
		return memberDao.findMemberByAccNo(accNo);
	}
	
    @Override
    public boolean isPasswordRight(Long currentMemberId, String encryptedPassword) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("currentMemberId", currentMemberId);
        params.put("encryptedPassword", encryptedPassword);
        return memberDao.countMemberByMemberIdAndPassword(params) > 0;
    }

    @Override
    public boolean resetPassword(Long memberId, String defaultEncryptedPassword) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", memberId);
        params.put("password", defaultEncryptedPassword);
        memberDao.updatePasswordByMemberId(params);
        return true;
    }

    @Override
    public boolean isAccNoExists(String accNo) {
        return memberDao.countMemberByAccNo(accNo) > 0;
    }

    @Override
    public boolean changePassword(String encryptedPassword, Long currentMemberId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("password", encryptedPassword);
        params.put("id", currentMemberId);
        memberDao.updatePasswordByMemberId(params);
        return true;
    }

    @Override
    public Long countByCriteria(MemberCriteriaModel memberCriteriaModel) {
        return memberDao.countByCriteria(memberCriteriaModel);
    }

    private void handleFieldForSorting(PageHelper pageHelper) {
        if (pageHelper.getSort() != null) {
            pageHelper.setSort(MybatisUtil.propertyName2ColumnName("member.memberModelResultMap", pageHelper.getSort()));
        }
    }

    @Override
    public List<MemberModel> findMemberListByCriteria(MemberCriteriaModel memberCriteriaModel, PageHelper pageHelper) {
        handleFieldForSorting(pageHelper);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageHelper", pageHelper);
        params.put("memberCriteriaModel", memberCriteriaModel);
        return memberDao.findMemberListByCriteria(params, PageUtil.paged(pageHelper.getPage() - 1, pageHelper.getRows()));
    }
}
