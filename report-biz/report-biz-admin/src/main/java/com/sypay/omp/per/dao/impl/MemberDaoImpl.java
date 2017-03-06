package com.sypay.omp.per.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.sypay.omp.per.dao.MemberDao;
import com.sypay.omp.per.model.MemberCriteriaModel;
import com.sypay.omp.per.model.MemberModel;
import com.sypay.omp.per.model.page.PageHelper;
import com.sypay.omp.per.model.page.PageUtil;
import com.sypay.omp.per.util.MybatisUtil;
import com.sypay.omp.report.dao.BaseDao;
import com.sypay.omp.report.service.MybatisBaseService;

@Repository
public class MemberDaoImpl implements MemberDao {

    @Resource
    private MybatisBaseService mybatisBaseService;

    @Resource
    private BaseDao baseDao;

    @Override
    public boolean isPasswordRight(Long currentMemberId, String encryptedPassword) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("currentMemberId", currentMemberId);
        params.put("encryptedPassword", encryptedPassword);
        return ((Integer) mybatisBaseService.selectOne("member.countMemberByMemberIdAndPassword", params)) > 0;
    }

    @Override
    public boolean resetPassword(Long memberId, String defaultEncryptedPassword) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", memberId);
        params.put("password", defaultEncryptedPassword);
        return mybatisBaseService.update("member.updatePasswordByMemberId", params) > 0;
    }

    @Override
    public boolean isAccNoExists(String accNo) {
        return ((Integer) mybatisBaseService.selectOne("member.countMemberByAccNo", accNo)) > 0;
    }

    @Override
    public boolean changePassword(String encryptedPassword, Long currentMemberId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("password", encryptedPassword);
        params.put("id", currentMemberId);
        return mybatisBaseService.update("member.updatePasswordByMemberId", params) > 0;
    }

    @Override
    public Long countByCriteria(MemberCriteriaModel memberCriteria) {
        return (Long) mybatisBaseService.selectOne("member.countByCriteria", memberCriteria);
    }

    private void handleFieldForSorting(PageHelper pageHelper) {
        if (pageHelper.getSort() != null) {
            pageHelper.setSort(MybatisUtil.propertyName2ColumnName("member.memberModelResultMap", pageHelper.getSort()));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MemberModel> findMemberListByCriteria(MemberCriteriaModel memberCriteriaModel, PageHelper pageHelper) {
        handleFieldForSorting(pageHelper);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageHelper", pageHelper);
        params.put("memberCriteriaModel", memberCriteriaModel);

        return mybatisBaseService.selectList("member.findMemberListByCriteria", params, PageUtil.paged(pageHelper.getPage() - 1, pageHelper.getRows()));
    }
}
