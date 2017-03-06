package com.sypay.omp.per.dao;

import java.util.List;


public interface PermissionFactoryDao {

	public List<String> getRoleCodeByMemberId(Long memberId);
}
