package com.sypay.omp.per.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sypay.omp.per.model.RoleCell;
import com.sypay.omp.per.service.GroupService;
import com.sypay.omp.per.service.PermissionFactoryService;
import com.sypay.omp.per.service.ReloadCacheService;
import com.sypay.omp.per.service.ResourceService;
import com.sypay.omp.per.service.RoleService;
import com.sypay.omp.per.util.RoleUtil;
import com.sypay.omp.per.util.SysCodeResourcesUtil;

@Service("reloadCacheService")
@Transactional
public class ReloadCacheServiceImpl implements ReloadCacheService {
	
	private final Log logger = LogFactory.getLog(ReloadCacheServiceImpl.class);

	
	@Resource
	private ResourceService resourceService;
	@Resource
	private RoleService roleService; 
	@Resource
	private PermissionFactoryService permissionFactoryService; 
	@Resource
	private GroupService groupService; 
	
	private static final String MENU_LIST = "menuList";
	private static final String ROLE_NAMES = "roleNames";
	private static final String USER_NAME = "name";
	private static final String NAVIGATE_LIST = "navigateList";
	
	@Override
	public boolean reloadCache(HttpSession session) {
		session.removeAttribute(MENU_LIST);
		session.removeAttribute(ROLE_NAMES);
		session.removeAttribute(USER_NAME);
		session.removeAttribute(NAVIGATE_LIST);
//		// -- 清空sysCodeResource Map资源
//		SysCodeResourcesUtil.clear();
//		List<Map<String, Object>> resourceList = resourceService.findAllResource();
//		for(Map<String, Object> m: resourceList) {
//			SysCodeResourcesUtil.putResource(m);
//		}
		
		return true;
	}

}
