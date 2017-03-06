package com.sypay.omp.per.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.sypay.omp.per.dao.PermissionFactoryDao;
import com.sypay.omp.report.dao.BaseDao;

@Repository
public class PermitFactoryDaoImpl implements PermissionFactoryDao {

	@Resource
	private BaseDao baseDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getRoleCodeByMemberId(Long memberId) {
		String sql = "select gr.role_code from uc_group_role gr where gr.group_code = "
				+ "	(select mg.group_code from uc_member_group mg where mg.status=1 and mg.member_id=:memberId)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberId", memberId);
		return baseDao.findBySql(sql, params);
	}

}
