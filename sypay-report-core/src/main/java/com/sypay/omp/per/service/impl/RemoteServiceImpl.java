package com.sypay.omp.per.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sypay.omp.per.dao.RemoteRoleManageDao;
import com.sypay.omp.per.model.Result;
import com.sypay.omp.per.model.UpdateRoleModel;
import com.sypay.omp.per.service.RemoteRoleService;

@Service("remoteRoleService")
@Transactional
public class RemoteServiceImpl implements RemoteRoleService {
	
	private RemoteRoleManageDao remoteRoleManageDao;

	@Override
	public Result updateRole(UpdateRoleModel roleModel) {
		Result result = new Result();
		result.setSuccess(remoteRoleManageDao.updateRole(roleModel));
		return result;
	}

}
