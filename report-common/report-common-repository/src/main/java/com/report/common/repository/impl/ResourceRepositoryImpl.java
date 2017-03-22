package com.report.common.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.report.common.dal.admin.dao.ResourceDao;
import com.report.common.dal.admin.entity.dto.Resource;
import com.report.common.dal.admin.entity.vo.ResourceModel;
import com.report.common.dal.admin.util.SessionUtil;
import com.report.common.dal.common.BaseDao;
import com.report.common.repository.ResourceRepository;

@Service
public class ResourceRepositoryImpl implements ResourceRepository {

	@Autowired
    private BaseDao baseDao;

    @Autowired
    private ResourceDao resourceDao;

    @Override
    public List findAllResource() {
        String sql =
                "select t.id , t.resource_action ,t.resource_code, t.name, t.resource_type,t.p_id,t.description,t.create_time,t.update_time,t.order_by,t.sys_code  from uc_resource t where t.status=1 ";
        Query query = baseDao.getSqlQuery(sql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Resource> findResourceList(ResourceModel resource) {
        String hql = "from Resource r where r.parent is null and r.status = 1 and id in() ";
        return baseDao.find(hql);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Resource> findTreeMenu(ResourceModel model) {
        String hql = "from Resource r where  r.parent is null and r.status = 1 and  r.resourceType in ('module','menu')";
        if (isPerAdmin()) {
            hql += " order by r.orderBy";
            return baseDao.find(hql);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean deleteById(Long id) {

        baseDao.delete(baseDao.get(Resource.class, id));
        return true;
    }

    private boolean isPerAdmin() {
        return SessionUtil.isPerAdmin();
    }

    @Override
    public boolean isResourceExist(ResourceModel resource) {
        Map<String, Object> params = new HashMap<String, Object>();
        String sql = "select count(*) from uc_resource t where t.status=1 and t.sys_code=:sysCode and t.resource_action=:resourceAction and t.resource_type=:rt ";
        params.put("sysCode", resource.getSysCode());
        params.put("resourceAction", resource.getResourceAction());
        params.put("rt", resource.getResourceType());

        if (resource.getId() != null) {
            sql += " and t.id <> :id";
            params.put("id", resource.getId());
        }
        return baseDao.countBySql(sql, params) > 0;
    }

    private boolean isAssociatedWithOthers(Long resourceId) {
        boolean flag = false;

        String sql = "select count(*) from uc_role_res rr where rr.resource_id =:resourceId";
        SQLQuery query = baseDao.getSqlQuery(sql);
        query.setLong("resourceId", resourceId);
        flag = Integer.parseInt(String.valueOf(query.uniqueResult())) > 0;
        return flag;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> findResourceIdsByRoleCode(String roleCode) {
        /*String sql = "select t.resource_id \"resId\" from uc_role_res t where t.role_code =:roleCode ";
        Query query = baseDao.getSqlQuery(sql);
        query.setParameter("roleCode", roleCode);
        return query.list();*/

        return resourceDao.getResourceIdsByRoleCode(roleCode);
    }

    @Override
    public List<Resource> findResourcesByIds(String resourceIds) {
        String hql = "from Resource t where t.status=1 and t.id in(:resourceIds) ";
        if (isPerAdmin()) {
            hql += " order by t.orderBy";
            Query query = baseDao.getQuery(hql);
            query.setParameterList("resourceIds", packResourceIdCollection(resourceIds));
            return query.list();
        } else {
            return Collections.emptyList();
        }
    }

    private Collection packResourceIdCollection(String resourceIds) {
        String[] ids = resourceIds.split(",");
        List<Long> list = new ArrayList<Long>();
        for (String str : ids) {
            list.add(Long.valueOf(str));
        }
        return list;
    }
    
    @Override
	public List<Resource> findResourcesByFlag(String reportFlag) {
		String sql = "select t.* from uc_resource t where t.status=1 and t.resource_action like :reportFlag";
		Query query = baseDao.getSqlQuery(sql);
		query.setParameter("reportFlag", "%" +reportFlag + "%");
		return query.list();
	}

}
